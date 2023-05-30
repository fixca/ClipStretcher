package me.fixca.clipstretcher;

import lombok.Data;

import java.io.File;

@Data
public class Clip {

    private File file;
    private String fileName;

    public Clip(File file) {
        this.file = file;
        this.fileName = file.getName();
    }

}
