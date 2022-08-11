package team.kucing.anabulshopcare.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import team.kucing.anabulshopcare.dto.request.WishlistRequest;
import team.kucing.anabulshopcare.dto.response.WishlistResponse;
import team.kucing.anabulshopcare.entity.Product;
import team.kucing.anabulshopcare.entity.UserApp;
import team.kucing.anabulshopcare.entity.Wishlist;
import team.kucing.anabulshopcare.exception.BadRequestException;
import team.kucing.anabulshopcare.exception.ResourceNotFoundException;
import team.kucing.anabulshopcare.handler.ResponseHandler;
import team.kucing.anabulshopcare.repository.ProductRepository;
import team.kucing.anabulshopcare.repository.UserAppRepository;
import team.kucing.anabulshopcare.repository.WishlistRepository;
import team.kucing.anabulshopcare.service.WishlistService;

import javax.transaction.Transactional;
import java.util.*;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class WishlistServiceImpl implements WishlistService {

    private final WishlistRepository wishlistRepository;

    private UserAppRepository userAppRepository;

    private final ProductRepository productRepository;

    @Override
    public WishlistResponse createWishlist(WishlistRequest wishlistRequest) {
        Optional<Product> product = this.productRepository.findById(wishlistRequest.getProductId());

        UserApp userApp = this.userAppRepository.findByEmail(wishlistRequest.getEmailUser());

        if(product.isEmpty()){
            throw new ResourceNotFoundException("Product is not found");
        }

        Product getProduct = product.get();
        if(getProduct.getIsPublished() == Boolean.FALSE){
            throw new ResourceNotFoundException("Product is not found");
        }
        var obj = userApp.getWishlist().stream().map(Wishlist::getProduct).toList();

        for (Product o : obj) {
            if (o == getProduct){
                throw new BadRequestException("It's already in your wishlist");
            }
        }

        Wishlist wishlist = new Wishlist();
        wishlist.setProduct(getProduct);
        wishlist.setUserApp(userApp);
        Wishlist saveWishlist = this.wishlistRepository.save(wishlist);

        List<Wishlist> wishlists = userApp.getWishlist();
        wishlists.add(wishlist);

        this.userAppRepository.save(userApp);

        List<Wishlist> wishlistsProduct = getProduct.getWishlist();
        wishlistsProduct.add(wishlist);

        this.productRepository.save(getProduct);

        log.info(userApp.getEmail() + " success add product" + getProduct.getId() + " to their wishlist");
        return saveWishlist.convertToResponse();
    }

    @Override
    public void deleteWishlist(Long id){
        Optional<Wishlist> wishlist = wishlistRepository.findById(id);

        if(wishlist.isEmpty()){
            log.error("Wishlist Not Found");
            throw new ResourceNotFoundException("Wishlist Not Found");
        }
        log.info("Your Wishlist is Deleted");
        this.wishlistRepository.delete(wishlist.get());
    }

    @Override
    public void deleteWishlistCustom(Product product, UserApp userApp){
        List<Wishlist> findWishlist = this.wishlistRepository.findByProductAndUserApp(product, userApp);
        this.wishlistRepository.deleteAll(findWishlist);
    }
}