package me.fixca.clipstretcher.executor;

import lombok.Getter;
import org.apache.commons.exec.PumpStreamHandler;

import java.io.ByteArrayOutputStream;

public class LineCollector {

    @Getter
    private static ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

    @Getter
    private static PumpStreamHandler streamHandler = new PumpStreamHandler(outputStream);

    public static void reset() {
        outputStream = new ByteArrayOutputStream();
        streamHandler = new PumpStreamHandler(outputStream);
    }
}
