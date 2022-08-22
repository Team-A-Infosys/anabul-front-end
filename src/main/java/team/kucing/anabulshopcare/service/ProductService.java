package team.kucing.anabulshopcare.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;
import team.kucing.anabulshopcare.dto.request.ProductRequest;
import team.kucing.anabulshopcare.dto.request.UpdateProduct;
import team.kucing.anabulshopcare.dto.response.ProductResponse;
import team.kucing.anabulshopcare.entity.Product;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

public interface ProductService {
    ProductResponse createProduct(ProductRequest productRequest, MultipartFile file);

    Page<Product> listProducts(int page, int size);

    ResponseEntity<Object> updateProduct(UpdateProduct productRequest, MultipartFile file, UUID id);

    Product findById(UUID id);

    Page<Product> listMyProduct(int page, int size, Authentication authentication);

    Page<Product> filterProductByName(String name, int page, int size);

    Page<Product> filterProductByNameForSeller(String name, int page, int size, String email);

    List<ProductResponse> filterProductsByLocation(String location, Pageable pageable);

    List<ProductResponse> filterProductByPrice(double startPrice, double endPrice, Pageable pageable);

    void deleteProduct(UUID id);

    ProductResponse publishedStatus(UUID id);

    ProductResponse archivedStatus(UUID id);

    List<ProductResponse> filterUnpublishedProduct(Pageable pageable);

    ProductResponse getProduct(UUID id);
}