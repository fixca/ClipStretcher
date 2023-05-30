package me.fixca.clipstretcher.exception;

import lombok.Getter;

public class FolderNotFoundException extends Exception {

    @Getter
    private String fileName;

    public FolderNotFoundException(String fileName) {
        super(String.format("Please create '%s' directory in where the program exists!"));
        this.fileName = fileName;
    }

}
