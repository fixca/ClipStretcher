package me.fixca.clipstretcher.storage;

import me.fixca.clipstretcher.Clip;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.LinkedBlockingDeque;

public class ClipRepository {

    private static List<Clip> clips = Collections.synchronizedList(new LinkedList<>());
    private static List<Clip> selectedClips = Collections.synchronizedList(new LinkedList<>());

    public static void addClip(Clip clip) {
        clips.add(clip);
    }

    public static List<Clip> getAllClips() {
        return clips;
    }

    public static void addSelectedClip(Clip clip) {
        if(selectedClips.contains(clip)) {
            return;
        }
        selectedClips.add(clip);
    }

    public static void removeSelectedClip(Clip clip) {
        if(!selectedClips.contains(clip)) {
            return;
        }
        selectedClips.remove(clip);
    }

    public static List<Clip> getAllSelectedClips() {
        return selectedClips;
    }

    public static void wipe() {
        clips.clear();
        selectedClips.clear();
    }

}
