package me.fixca.clipstretcher.storage;

import lombok.Getter;
import me.fixca.clipstretcher.Clip;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.LinkedBlockingDeque;

public class ClipRepository {

    @Getter
    private static List<Clip> clips = Collections.synchronizedList(new LinkedList<>());

    @Getter
    private static LinkedBlockingDeque<Clip> selectedClips = new LinkedBlockingDeque<>();

    public static void wipe() {
        clips.clear();
        selectedClips.clear();
    }

}
