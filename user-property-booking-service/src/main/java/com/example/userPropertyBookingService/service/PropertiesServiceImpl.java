package com.example.userPropertyBookingService.service;

import com.example.userPropertyBookingService.dtos.PropertyDto;
import com.example.userPropertyBookingService.entities.Property;
import com.example.userPropertyBookingService.exception.ResourceNotFoundException;
import com.example.userPropertyBookingService.repository.PropertyRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class PropertiesServiceImpl implements PropertiesService {

    @Autowired
    PropertyRepository propertyRepository;

    @Autowired
    ModelMapper modelMapper;


    @Override
    public List<PropertyDto> getAll(Integer pageNumber, Integer pageSize) {

        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        Page<Property> returnedProperty =
                propertyRepository.findAll(pageable);
        if (!returnedProperty.isEmpty()) {
            return returnedProperty.stream()
                    .map(entity -> modelMapper.map(entity, PropertyDto.class))
                    .collect(Collectors.toList());
        } else {
            throw new ResourceNotFoundException("Property is Empty", " ", null);
        }
    }


    @Override
    @Cacheable(value = "property", key = "#id")
    public PropertyDto getById(Long id) {
        Optional<Property> returnedProperty = propertyRepository.findById(id);
        if (returnedProperty.isPresent()) {
            return modelMapper.map(returnedProperty.get(),
                    PropertyDto.class);
        } else {
            throw new ResourceNotFoundException("Property", "ID", id);
        }
    }


    @Override
    @CacheEvict(value = "property", key = "#id")
    public void deleteById(Long id) {
        propertyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Property", "id", id));
        propertyRepository.deleteById(id);
    }

    @Override
    @CachePut(value = "property", key = "#result.id")
    public PropertyDto create(PropertyDto propertydto) {
        Property property = modelMapper.map(propertydto,
                Property.class);
        Property returnedProperty =
                propertyRepository.save(property);
        return modelMapper.map(returnedProperty,
                PropertyDto.class);
    }

    @Override
    @CachePut(value = "property", key = "#id")
    public PropertyDto updateById(
            PropertyDto propertyDTO, Long id) {
        Property property =
                modelMapper.map(propertyDTO, Property.class);

        propertyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Property", "id", id));

        property.setId(id);

        Property reTurnedProperty =
                propertyRepository.save(property);

        return modelMapper.map(reTurnedProperty,
                PropertyDto.class);
    }
}
