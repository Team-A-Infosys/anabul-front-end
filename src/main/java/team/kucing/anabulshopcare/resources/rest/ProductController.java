//package team.kucing.anabulshopcare.resources.rest;
//
//import io.swagger.v3.oas.annotations.tags.Tag;
//import lombok.AllArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springdoc.api.annotations.ParameterObject;
//import org.springframework.data.domain.Pageable;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//import team.kucing.anabulshopcare.dto.request.ProductRequest;
//import team.kucing.anabulshopcare.dto.request.UpdateProduct;
//import team.kucing.anabulshopcare.dto.response.ProductResponse;
//import team.kucing.anabulshopcare.handler.ResponseHandler;
//import team.kucing.anabulshopcare.service.ProductService;
//
//import javax.validation.Valid;
//import java.util.List;
//import java.util.UUID;
//
//@RestController
//@AllArgsConstructor
//@Slf4j
//@Tag(name = "02. Product Controller")
//public class ProductController {
//    private final ProductService productService;
//
//    @GetMapping("/products")
//    public ResponseEntity<Object> getAllProducts(@ParameterObject Pageable pageable){
//        List<ProductResponse> response = this.productService.listProducts(pageable);
//        return ResponseHandler.generateResponse("Success retrieve all products", HttpStatus.OK, response);
//    }
//
//    @PostMapping(value = "/product/create",consumes = {MediaType.APPLICATION_OCTET_STREAM_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
//    public ResponseEntity<Object> addProduct(@RequestPart MultipartFile file, @RequestPart @Valid ProductRequest product){
//        ProductResponse response = this.productService.createProduct(product,file);
//        return ResponseHandler.generateResponse("Success create product", HttpStatus.CREATED, response);
//    }
//
//    @PutMapping(value = "/product/{id}/update")
//    public ResponseEntity<Object> updateProduct(@PathVariable(value = "id") UUID id, @RequestPart MultipartFile file, @RequestPart @Valid UpdateProduct productRequest){
//        return this.productService.updateProduct(productRequest, file, id);
//    }
//
//    @GetMapping("/products/search/name")
//    public ResponseEntity<Object> findByProductName(@RequestParam(value = "productName") String name, @ParameterObject Pageable pageable) {
//        List<ProductResponse> response = this.productService.filterProductByName(name,pageable);
//        return ResponseHandler.generateResponse("Success get product", HttpStatus.OK, response);
//    }
//
//    @GetMapping("/products/search/location")
//    public ResponseEntity<Object> filterProductsByLocation(@RequestParam(value = "location", required = false) String location, @ParameterObject  Pageable pageable){
//        List<ProductResponse> response = this.productService.filterProductsByLocation(location, pageable);
//        return ResponseHandler.generateResponse("Success get product", HttpStatus.OK, response);
//    }
//
//    @GetMapping("/products/search/price")
//    public ResponseEntity<Object> filterProductsByPrice(@RequestParam(value = "start", required = false)double startPrice, @RequestParam(value = "end", required = false)double endPrice, @ParameterObject  Pageable pageable){
//        List<ProductResponse> response = this.productService.filterProductByPrice(startPrice, endPrice, pageable);
//        return ResponseHandler.generateResponse("Success get product", HttpStatus.OK, response);
//    }
//
//    @GetMapping("/products/search/unpublished")
//    public ResponseEntity<Object> unpublished(@ParameterObject Pageable pageable){
//        List<ProductResponse> response = this.productService.filterUnpublishedProduct(pageable);
//        return ResponseHandler.generateResponse("Success get product", HttpStatus.OK, response);
//    }
//    @DeleteMapping("/product/{id}/delet")
//    public ResponseEntity<Object> deleteProduct(@PathVariable UUID id){
//        this.productService.deleteProduct(id);
//        return ResponseHandler.generateResponse("Success delete the product", HttpStatus.OK, null);
//    }
//
//    @PutMapping(value = "/product/{id}/setPublished")
//    public ResponseEntity<Object> setPublished(@PathVariable(value = "id") UUID id){
//        ProductResponse response = this.productService.publishedStatus(id);
//        return ResponseHandler.generateResponse("Success published product", HttpStatus.OK, response);
//    }
//
//    @PutMapping(value = "/product/{id}/setArchived")
//    public ResponseEntity<Object> setArchived(@PathVariable(value = "id") UUID id){
//        ProductResponse response = this.productService.archivedStatus(id);
//        return ResponseHandler.generateResponse("Success archived product", HttpStatus.OK, response);
//    }
//}