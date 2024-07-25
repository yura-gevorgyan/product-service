package org.example.productservice.service;

import org.example.productservice.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

public interface ProductService {
    Page<Product> getAll(String index);

    Product getById(String idStr);

    Product save(Product product, MultipartFile multipartFile);

    void delete(String id);

    Product update(String id, Product product);
}
