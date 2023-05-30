package me.fixca.clipstretcher.component;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import me.fixca.clipstretcher.Clip;
import me.fixca.clipstretcher.storage.ClipRepository;

public class ClipCheckBox extends CheckBox implements EventHandler<ActionEvent> {

    private Clip clip;

    public ClipCheckBox(Clip clip) {
        super();
        super.setOnAction(this);
        super.setAlignment(Pos.CENTER);
        this.clip = clip;
    }

    @Override
    public void handle(ActionEvent event) {
        if(isSelected()) {
            System.out.println(clip.getFileName() + " is selected");
            ClipRepository.addSelectedClip(clip);
        }
        else {
            System.out.println(clip.getFileName() + " is unselected");
            ClipRepository.removeSelectedClip(clip);
        }
    }
}
