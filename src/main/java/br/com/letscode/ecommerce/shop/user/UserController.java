package br.com.letscode.ecommerce.shop.user;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("v1/users")
public class UserController {

    private final UserService service;

    @PostMapping
    public ResponseEntity<Object> save(@RequestBody UserRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(request));
    }

    @GetMapping
    public ResponseEntity<Page<UserEntity>> getAll(
            @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable,
            @RequestParam(value = "name", required = false) String name
    ) {
        UserFilter filter = new UserFilter(name);
        return ResponseEntity.status(HttpStatus.OK).body(service.findAll(filter, pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserEntity> getOne(@PathVariable(value = "id") Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(service.find(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserEntity> update(@PathVariable(value = "id") Long id,
                                             @RequestBody UserRequest request) {
        return ResponseEntity.status(HttpStatus.OK).body(service.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable(value = "id") Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(service.delete(id));
    }
}
