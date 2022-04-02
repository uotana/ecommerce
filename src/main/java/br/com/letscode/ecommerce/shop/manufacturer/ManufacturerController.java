package br.com.letscode.ecommerce.shop.manufacturer;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/manufacturer")
@RequiredArgsConstructor
public class ManufacturerController {

    private final ManufacturerService service;

    @PostMapping
    public ResponseEntity<ManufacturerEntity>save(@RequestBody ManufacturerRequest manufacturer){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(manufacturer));
    }

}
