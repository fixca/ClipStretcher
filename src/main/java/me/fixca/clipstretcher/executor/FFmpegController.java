package me.fixca.clipstretcher.executor;

import lombok.Getter;
import me.fixca.clipstretcher.Clip;
import net.bramp.ffmpeg.FFmpeg;
import net.bramp.ffmpeg.FFprobe;
import net.bramp.ffmpeg.probe.FFmpegProbeResult;
import net.bramp.ffmpeg.probe.FFmpegStream;

import java.io.IOException;

public class FFmpegController {

    @Getter
    private static FFmpeg fFmpeg;

    @Getter
    private static FFprobe fFprobe;

    public static void initFFmpeg() throws IOException {
        fFmpeg = new FFmpeg("./ffmpeg");
        fFprobe = new FFprobe("./ffprobe");
    }

    public static FFmpegStream getVideoMetadata(Clip clip) throws IOException {
        FFmpegProbeResult probeResult = fFprobe.probe(clip.getFile().getAbsolutePath());
        return probeResult.getStreams().get(0);
    }

}
