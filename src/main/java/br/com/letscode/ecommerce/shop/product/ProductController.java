package br.com.letscode.ecommerce.shop.product;


import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@AllArgsConstructor
@RequestMapping("/v1/products")
public class ProductController {

    private ProductService service;

    @PostMapping
    public ResponseEntity<Object> save(@RequestBody ProductRequest product) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(product));
    }

    @GetMapping
    public ResponseEntity<Page<ProductEntity>> getAll(
            @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable,
            @RequestParam(name = "name", required = false) String name,
//            @RequestParam(name = "category", required = false) String category,
            @RequestParam(name = "minvalue", required = false) BigDecimal minValue,
            @RequestParam(name = "maxvalue", required = false) BigDecimal maxValue) {

        ProductFilter filter = new ProductFilter();
        filter.setName(name);
        filter.setMaxValue(minValue);
        filter.setMinValue(maxValue);

        return ResponseEntity.status(HttpStatus.OK).body(service.findAll(filter, pageable));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductEntity> update(@PathVariable(value = "id") Long id,
                                                @RequestBody ProductRequest product) {
        return ResponseEntity.status(HttpStatus.OK).body(service.update(id, product));

    }

    @DeleteMapping("/{id}")
    ResponseEntity<Object> delete(@PathVariable(value = "id") Long id) {

        return ResponseEntity.ok(service.delete(id));
    }
}

