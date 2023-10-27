package me.fixca.clipstretcher;

import lombok.Data;
import me.fixca.clipstretcher.executor.FFmpegController;
import net.bramp.ffmpeg.probe.FFmpegStream;

import java.io.File;
import java.text.MessageFormat;

@Data
public class Clip {

    private File file;
    private String fileName;

    public Clip(File file) {
        this.file = file;
        this.fileName = file.getName();
    }

    public Resolution getResolution() {
        try {
            FFmpegStream stream = FFmpegController.getVideoMetadata(this);
            int width = stream.width;
            int height = stream.height;
            double _169height = ((double) width * 9) / 16;
            double _43height = ((double) width * 3) / 4;
            if(height == _169height) {
                return Resolution._16_9;
            }
            if(height == _43height) {
                return Resolution._4_3;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return Resolution.UN_SUPPORTED;
    }

}
