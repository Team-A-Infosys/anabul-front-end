package team.kucing.anabulshopcare.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import team.kucing.anabulshopcare.dto.request.ProductRequest;
import team.kucing.anabulshopcare.dto.request.UpdateProduct;
import team.kucing.anabulshopcare.dto.response.ProductResponse;
import team.kucing.anabulshopcare.entity.Category;
import team.kucing.anabulshopcare.entity.Product;
import team.kucing.anabulshopcare.entity.UserApp;
import team.kucing.anabulshopcare.exception.BadRequestException;
import team.kucing.anabulshopcare.exception.ResourceNotFoundException;
import team.kucing.anabulshopcare.handler.ResponseHandler;
import team.kucing.anabulshopcare.repository.*;
import team.kucing.anabulshopcare.service.CategoryService;
import team.kucing.anabulshopcare.service.ProductService;
import team.kucing.anabulshopcare.service.uploadimg.ImageProductService;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    private final CategoryRepository categoryRepository;

    private final UserAppRepository userAppRepository;

    private CategoryService categoryService;

    private final ImageProductService imageProductService;
    private final WishlistRepository wishlistRepository;

    private final CartRepository cartRepository;

    @Override
    public ProductResponse createProduct(ProductRequest productRequest, MultipartFile file) {

        String mimetype = "image/png";

        if ((!mimetype.equals(file.getContentType()))){
            log.error("File must be image/png, error :" + HttpStatus.BAD_REQUEST);
            throw new BadRequestException("File must be image/png");
        }


        String fileName = imageProductService.storeFile(file);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/images-prod/" + fileName)
                .toUriString();

        Optional<Category> category = this.categoryRepository.findByCategoryName(productRequest.getCategory().getName());

        Product product = new Product();
        product.setName(productRequest.getProductName());
        product.setDescription(productRequest.getDescription());

        if (category.isEmpty()){
            Category newCategory = this.categoryService.createCategory(productRequest.getCategory());
            product.setCategory(newCategory);
            log.info("Creating new category " + productRequest.getCategory());
        } else {
            product.setCategory(category.get());
        }
        UserApp userApp = this.userAppRepository.findByEmail(productRequest.getEmailUser());

        product.setUserApp(userApp);
        product.setLocation(userApp.getAddress().getKota().getNama());
        product.setStock(productRequest.getStock());
        product.setPrice(productRequest.getPrice());

        product.setImageUrl(fileDownloadUri);

        this.productRepository.save(product);

        log.info("Success create product " + product.convertToResponse().toString());
        return product.convertToResponse();
    }

    @Override
    public Page<Product> listProducts(int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<Product> listProducts = this.productRepository.findAll(pageable);


        log.info("Success retrieve all products");
        return listProducts;
    }

    @Override
    public Page<Product> filterProductByName(String name, int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<Product> getProduct = this.productRepository.findByNameContaining(name, pageable);

        log.info("Success search product by name: " + name);
        return getProduct;
    }
    @Override
        public List<ProductResponse> filterProductsByLocation(String location, Pageable pageable) {
        Page<Product> getProduct = this.productRepository.findByLocation(location, pageable);

        List<ProductResponse> response = getProduct.stream().map(Product::convertToResponse).toList();

        if (getProduct.getTotalPages() == 0){
            log.error("Failed to get Product with Location: " + location);
            throw new ResourceNotFoundException("Sorry, There are no product in " + location + " area...");
        }

        log.info("Success search product by location: " + response);
        return response;
    }

    @Override
    public List<ProductResponse> filterProductByPrice(double startPrice, double endPrice, Pageable pageable) {
        Page<Product> getProduct = this.productRepository.findByPriceBetween(startPrice, endPrice, pageable);

        List<ProductResponse> response = getProduct.stream().map(Product::convertToResponse).toList();

        if (getProduct.getTotalPages() == 0){
            log.error("Failed to get Product with price range " + startPrice + " to " + endPrice );
            throw new ResourceNotFoundException("Sorry, There are no product in that price range");
        }

        log.info("Success Search Product by price " + response);
        return response;
    }

    @Override
    public List<ProductResponse> filterUnpublishedProduct(Pageable pageable){
        Page<Product> product = this.productRepository.findByIsPublished(Boolean.FALSE, pageable);

        List<ProductResponse> response = product.stream().map(Product::convertToResponse).toList();

        if (product.getTotalPages() == 0){
            log.error("There are no products to be unpublished");
            throw new ResourceNotFoundException("There are no product to unpublished");
        }

        log.info("Success get unpublished product: " + response);
        return response;
    }

    @Override
    public Product findById(UUID id) {
        Optional<Product> optionalProduct = productRepository.findById(id);

        if(optionalProduct.isEmpty()){
            log.error("Failed to get product with ID: " + id);
            throw new ResourceNotFoundException("Product with ID "+id+" Is Not Found");
        }
        log.info("Success get product with ID: " + id);
        return optionalProduct.get();
    }

    @Override
    public ResponseEntity<Object> updateProduct(UpdateProduct productRequest, MultipartFile file, UUID id) {
        Product updateProduct = this.findById(id);
        if (productRequest.getProductName() != null){
            updateProduct.setName(productRequest.getProductName());
            this.productRepository.save(updateProduct);
            log.info("Success update name of product ID: " + updateProduct.getId());
        }

        if (productRequest.getDescription() != null){
            updateProduct.setDescription(productRequest.getDescription());
            this.productRepository.save(updateProduct);
            log.info("Success update description of product ID: " + updateProduct.getId());
        }

        if (productRequest.getCategory() != null){
            Optional<Category> findCategory = this.categoryRepository.findByCategoryName(productRequest.getCategory().getName());
            if (findCategory.isEmpty()){
                Category newCategory = this.categoryService.createCategory(productRequest.getCategory());
                updateProduct.setCategory(newCategory);
                this.productRepository.save(updateProduct);
                log.info("Success update category of product ID: " + updateProduct.getId());
            }
            updateProduct.setCategory(findCategory.get());
            this.productRepository.save(updateProduct);
            log.info("Success update category of product ID: " + updateProduct.getId());
        }

        if (productRequest.getStock() != null){
            updateProduct.setStock(productRequest.getStock());
            this.productRepository.save(updateProduct);
            log.info("Success update stock of product ID: " + updateProduct.getId());
        }

        if (productRequest.getPrice() != 0){
            updateProduct.setPrice(productRequest.getPrice());
            this.productRepository.save(updateProduct);
            log.info("Success update price of product ID: " + updateProduct.getId());
        }

        if (!(file.isEmpty())){
            String fileName = imageProductService.storeFile(file);
            String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path(fileName).toUriString();
            updateProduct.setImageUrl(fileDownloadUri);
            this.productRepository.save(updateProduct);
            log.info("Success update image of product ID: " + updateProduct.getId());
        }

        ProductResponse response = updateProduct.convertToResponse();
        log.info("Success update product ID: " + updateProduct.getId());
        return ResponseHandler.generateResponse("Success update the product", HttpStatus.OK, response);
    }

    @Override
    public void deleteProduct(UUID id) {
        Optional<Product> optionalProduct = productRepository.findById(id);

        if(optionalProduct.isEmpty()){
            log.error("Failed to delete product with id : " + id);
            throw new ResourceNotFoundException("Product not exist with id : "+id);
        }

        Product product = productRepository.getReferenceById(id);
        this.productRepository.delete(product);
        this.wishlistRepository.deleteByProductId(id);
        this.cartRepository.deleteByProductId(id);

        log.info("Success delete product with id : " + id);

    }

    @Override
    public ProductResponse publishedStatus(UUID id) {
        Product getProduct = this.findById(id);

        if (!getProduct.getIsPublished()){
            getProduct.setIsPublished(Boolean.TRUE);
        }

        Product publishedProduct = this.productRepository.save(getProduct);
        ProductResponse response = publishedProduct.convertToResponse();

        log.info("Success publish the product with ID: " + id);
        return response;
    }

    @Override
    public ProductResponse archivedStatus(UUID id) {
        Product getProduct = this.findById(id);
        if (getProduct.getIsPublished()){
            getProduct.setIsPublished(Boolean.FALSE);
        }

        Product archivedProduct = this.productRepository.save(getProduct);
        ProductResponse response = archivedProduct.convertToResponse();

        log.info("Success archived the product with ID: " + id);
        return response;
    }

    @Override
    public ProductResponse getProduct(UUID id){
        Product product = this.findById(id);
        return product.convertToResponse();
    }

}