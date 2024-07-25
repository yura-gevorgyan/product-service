package org.example.productservice.endpoint;

import lombok.RequiredArgsConstructor;
import org.example.productservice.entity.Product;
import org.example.productservice.service.ProductService;
import org.example.productservice.util.FileUtil;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
@CrossOrigin
public class ProductEndpoint {

    private final ProductService productService;

    @GetMapping("/{index}")
    @ResponseStatus(HttpStatus.OK)
    @CrossOrigin("*")
    private ResponseEntity<Page<Product>> getAllProducts(@PathVariable String index) {
        return ResponseEntity.ok(productService.getAll(index));
    }

    @GetMapping("/item/{idStr}")
    @ResponseStatus(HttpStatus.FOUND)
    @CrossOrigin("*")
    private ResponseEntity<Product> getProductById(@PathVariable String idStr) {
        return ResponseEntity.ok(productService.getById(idStr));
    }

    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @ResponseStatus(HttpStatus.CREATED)
    @CrossOrigin("*")
    private ResponseEntity<Product> saveProduct(@RequestPart Product product,
                                                @RequestPart(value = "picture", required = false) MultipartFile multipartFile) {
        return ResponseEntity.ok(productService.save(product, multipartFile));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @CrossOrigin("*")
    private ResponseEntity<Void> deleteProduct(@PathVariable String id) {
        productService.delete(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @CrossOrigin("*")
    private ResponseEntity<Product> updateProduct(@PathVariable String id,
                                                  @RequestBody Product product) {
        return ResponseEntity.ok(productService.update(id, product));
    }

    @GetMapping(value = "/getImage/{picName}")
    @CrossOrigin("*")
    private ResponseEntity<byte[]> getImage(@PathVariable String picName) {
        byte[] picture = FileUtil.getPicture(picName);
        return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(picture);
    }

}
