package br.com.letscode.ecommerce.shop.manufacturer;

import br.com.letscode.ecommerce.shop.exception.ManufacturerConflictException;
import br.com.letscode.ecommerce.shop.exception.ManufacturerNotFoundException;
import br.com.letscode.ecommerce.shop.exception.ProductNotFoundException;
import br.com.letscode.ecommerce.shop.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ManufacturerService {

    private final ManufacturerRepository repository;
    private final ProductRepository productRepository;

    public ManufacturerEntity save(ManufacturerRequest manufacturerRequest) {
        if (repository.existsByName(manufacturerRequest.getName())) {
            throw new ManufacturerConflictException("Manufacturer already exists.");
        } else {

            ManufacturerEntity manufacturer = new ManufacturerEntity();
            manufacturer.setName(manufacturerRequest.getName());
            manufacturer.setCreationDate(ZonedDateTime.now());
            manufacturer.setUpdateDate(ZonedDateTime.now());

            return repository.save(manufacturer);
        }
    }

    public void delete(Long manufacturerId) {
        Optional<ManufacturerEntity> manufacturerEntityOptional = repository.findById(manufacturerId);
        ManufacturerEntity manufacturer = manufacturerEntityOptional.orElseThrow(
                () -> new ManufacturerNotFoundException("Manufacturer with id " + manufacturerId + " not found."));

        repository.delete(manufacturer);
    }

    public Page<ManufacturerEntity> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }
}
