package me.fixca.clipstretcher;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;

import java.io.File;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        File inputFolder = new File("./Inputs");
        if (!inputFolder.exists()) {
            System.out.println("Please create 'Inputs' directory in where this file exists!");
            return;
        }
        if (!inputFolder.isDirectory()) {
            System.out.println("'Inputs' should be a directory!");
            return;
        }

        File outputFolder = new File("./Outputs");
        if (!outputFolder.exists()) {
            System.out.println("Please create 'Outputs' directory in where this file exists!");
            return;
        }
        if (!outputFolder.isDirectory()) {
            System.out.println("'Outputs' should be a directory!");
            return;
        }

        List<String> outputVideos = Arrays.stream(outputFolder.list()).filter(s -> s.endsWith(".mp4")).toList();

        List<String> videos = Arrays.stream(inputFolder.list()).filter(s -> s.endsWith(".mp4")).filter(s -> !outputVideos.contains(s)).toList();

        if (videos.isEmpty()) {
            System.out.println("There is nothing to convert.");
            return;
        }

        for (String video : videos) {
            String command = MessageFormat.format("ffmpeg.exe -i ./Inputs/{0} -vf \"scale=1920:1080, setsar=1\" ./Outputs/{1}", video, video);

            DefaultExecutor executor = new DefaultExecutor();

            try {
                CommandLine cmdLine = CommandLine.parse(command);
                executor.execute(cmdLine);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
