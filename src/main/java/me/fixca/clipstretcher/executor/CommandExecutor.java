package me.fixca.clipstretcher.executor;

import me.fixca.clipstretcher.Clip;
import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;

import java.text.MessageFormat;
import java.util.concurrent.CompletableFuture;

public class CommandExecutor {

    public static CompletableFuture<Integer> executeStretchAsync(Clip clip) {
        return CompletableFuture.supplyAsync(() -> executeStretch(clip));
    }

    public static Integer executeStretch(Clip clip) {
        String command = MessageFormat.format("ffmpeg.exe -i ./Inputs/{0} -vf \"crop=1440:1080 ,scale=1920:1080 ,setsar=1\" ./Outputs/{1}", clip.getFileName(), clip.getFileName());

        DefaultExecutor executor = new DefaultExecutor();
        executor.setStreamHandler(LineCollector.getStreamHandler());

        try {
            CommandLine cmdLine = CommandLine.parse(command);
            return executor.execute(cmdLine);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
