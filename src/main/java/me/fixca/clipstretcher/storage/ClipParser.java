package me.fixca.clipstretcher.storage;

import me.fixca.clipstretcher.Clip;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ClipParser {

    public static void parse() {

        File inputFolder = new File("Inputs");
        File outputFolder = new File("Outputs");

        List<File> outputVideos = Arrays.stream(outputFolder.listFiles())
                .filter(File::isFile)
                .filter(file -> file.getName().endsWith(".mp4"))
                .collect(Collectors.toList());

        List<File> videos = Arrays.stream(inputFolder.listFiles())
                .filter(File::isFile)
                .filter(file -> file.getName().endsWith(".mp4"))
                .filter(file -> outputVideos.stream().noneMatch(file1 -> file1.getName().equals(file.getName())))
                .collect(Collectors.toList());

        for (File video : videos) {
            ClipRepository.getClips().add(new Clip(video));
        }

    }

}
