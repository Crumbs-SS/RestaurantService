package com.crumbs.fss.ExceptionHandling;

public class DuplicateFieldException extends RuntimeException{
    String duplicates;
    public DuplicateFieldException(String duplicates) {
        this.duplicates = duplicates;
    }
}
