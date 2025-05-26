package com.example.userPropertyBookingService.controller;

import com.example.userPropertyBookingService.dtos.PropertyDto;
import com.example.userPropertyBookingService.service.PropertiesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/property")
public class PropertyController {
    @Autowired
    PropertiesService service;

    @GetMapping(path = "/{id}")
    public ResponseEntity<PropertyDto> getPropertiesById(@PathVariable Long id)
    {
        PropertyDto properties = service.getById(id);
        return new ResponseEntity<>(properties, HttpStatus.OK);
    }

    @GetMapping(path = "/")
    public ResponseEntity<List<PropertyDto>> getAll(@RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
                                                    @RequestParam(value = "pageSize", defaultValue = "10", required = false) Integer pageSize)
    {
        List<PropertyDto> allProperties = service.getAll(pageNumber, pageSize);
        if (!allProperties.isEmpty())
            return new ResponseEntity<>(allProperties, HttpStatus.OK);
        else
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @PostMapping(path = "/")
    public ResponseEntity<PropertyDto> addProperties(@RequestBody PropertyDto propertiesDTO)
    {
        PropertyDto properties = service.create(propertiesDTO);

        if (properties != null)
            return new ResponseEntity<>(properties, HttpStatus.OK);
        else
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<PropertyDto> updateProperties(@PathVariable Long id, @RequestBody PropertyDto newProperties)
    {
        PropertyDto properties = service.updateById(newProperties,id);

        if (properties != null)
            return new ResponseEntity<>(properties, HttpStatus.OK);
        else
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity deletePropertyById(@PathVariable Long id)
    {
        service.deleteById(id);
        return ResponseEntity.ok("Deleted Successfully");
    }
}