package com.finalVariant.OnlineStore.model.dao.exception;

public class FieldNotPresent extends Exception{
    @Override
    public String getMessage() {
        return "The field doesn't exist";
    }
}
