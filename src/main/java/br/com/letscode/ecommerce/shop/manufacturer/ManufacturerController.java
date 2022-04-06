package br.com.letscode.ecommerce.shop.manufacturer;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1/manufacturers")
@RequiredArgsConstructor
public class ManufacturerController {

    private final ManufacturerService service;

    @PostMapping
    public ResponseEntity<ManufacturerEntity>save(@RequestBody ManufacturerRequest manufacturer){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(manufacturer));
    }

    @GetMapping
    public ResponseEntity<Page<ManufacturerEntity>>getAll(@PageableDefault(
            page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable){
        return ResponseEntity.status(HttpStatus.OK).body(service.findAll(pageable));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable(value = "id") Long manufacturerId){
       service.delete(manufacturerId);
        return ResponseEntity.ok("Successfully deleted!");
    }

}
