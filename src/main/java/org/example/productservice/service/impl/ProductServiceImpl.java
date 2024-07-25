package org.example.productservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.example.productservice.entity.Product;
import org.example.productservice.feignClient.FeignClientOrder;
import org.example.productservice.repository.ProductRepository;
import org.example.productservice.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final FeignClientOrder feignClientOrder;

    @Override
    public Page<Product> getAll(String indexStr) {
        try {
            int index = Integer.parseInt(indexStr);

            if (index <= 0) {
                return productRepository.findAll(PageRequest.of(0, 8).withSort(Sort.by("id")));
            }
            Page<Product> products = productRepository.findAll(PageRequest.of(index - 1, 8));

            if (products.getTotalPages() < index) {
                return productRepository.findAll(PageRequest.of(0, 8));
            }

            return products;

        } catch (NumberFormatException e) {
            return productRepository.findAll(PageRequest.of(0, 8));
        }
    }

    @Override
    public Product getById(String idStr) {
        try {
            int id = Integer.parseInt(idStr);

            Optional<Product> byId = productRepository.findById(id);

            return byId.orElse(null);


        } catch (NumberFormatException e) {
            return null;
        }
    }

    @Override
    @SneakyThrows
    public Product save(Product product, MultipartFile multipartFile) {
        if (multipartFile != null && !multipartFile.isEmpty()) {
            String picName = System.currentTimeMillis() + "_" + multipartFile.getOriginalFilename();
            File file = new File("C:/Users/Yura/IdeaProjects/project/product-service/images", picName);
            multipartFile.transferTo(file);
            product.setPicName(picName);
        }
        return productRepository.save(product);
    }

    @Override
    public void delete(String id) {
        Product byId = getById(id);

        if (byId != null) {
            feignClientOrder.deleteOrder(byId.getId());
            productRepository.delete(byId);
        }
    }

    @Override
    public Product update(String id, Product product) {
        Product byId = getById(id);
        if (byId != null) {
            if (product.getName() != null) {
                byId.setName(product.getName());
            }
            if (product.getPrice() != 0) {
                byId.setPrice(product.getPrice());
            }
            if (product.getCount() > 0) {
                byId.setCount(product.getCount());
            }
            return productRepository.save(byId);
        }
        return null;
    }
}
