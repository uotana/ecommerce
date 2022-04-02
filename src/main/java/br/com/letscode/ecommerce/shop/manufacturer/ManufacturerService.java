package br.com.letscode.ecommerce.shop.manufacturer;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;

@Service
@RequiredArgsConstructor
public class ManufacturerService {

    private final ManufacturerRepository repository;

    public ManufacturerEntity save(ManufacturerRequest manufacturerRequest) {
        if (repository.existsByName(manufacturerRequest.getName())) {
            throw new RuntimeException("Conflict");
        } else {

            ManufacturerEntity manufacturer = new ManufacturerEntity();
            manufacturer.setName(manufacturerRequest.getName());
            manufacturer.setCreationDate(ZonedDateTime.now());
            manufacturer.setUpdateDate(ZonedDateTime.now());

            return repository.save(manufacturer);
        }

    }
}
