package com.example.userPropertyBookingService.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1l;
    private String RecourseName;
    private String FieldName;
    private Object FieldValue;

    public String getRecourseName() {
        return RecourseName;
    }

    public String getFieldName() {
        return FieldName;
    }

    public Object getFieldValue() {
        return FieldValue;
    }

    public ResourceNotFoundException(String recourseName, String fieldName, Object fieldValue) {
        super(String.format("%s not found with %s:'%s'", recourseName, fieldName, fieldValue));
        this.RecourseName = recourseName;
        this.FieldName = fieldName;
        this.FieldValue = fieldValue;


    }
}