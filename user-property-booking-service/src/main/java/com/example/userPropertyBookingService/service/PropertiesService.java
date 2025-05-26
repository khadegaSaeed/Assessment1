package com.example.userPropertyBookingService.service;

import com.example.userPropertyBookingService.dtos.PropertyDto;

import java.util.List;


public interface PropertiesService {


    List<PropertyDto> getAll(Integer pageNumber, Integer pageSize);

    PropertyDto getById(Long id);

    void deleteById(Long id);


    PropertyDto create(PropertyDto PropertyDto);


    PropertyDto updateById(PropertyDto PropertyDto, Long id);

}