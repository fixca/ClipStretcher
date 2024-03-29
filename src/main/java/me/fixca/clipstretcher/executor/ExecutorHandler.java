package me.fixca.clipstretcher.executor;

import javafx.application.Platform;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.control.Alert;
import lombok.Getter;
import lombok.Setter;
import me.fixca.clipstretcher.Clip;
import me.fixca.clipstretcher.controller.MainController;
import me.fixca.clipstretcher.storage.ClipRepository;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;

public class ExecutorHandler {

    @Getter
    @Setter
    private static boolean executing = false;

    private static DoubleProperty property;

    private static void postRendering(Clip clip) {
        ClipRepository.getClips().remove(clip);
        if (ClipRepository.getSelectedClips().isEmpty()) {
            executing = false;
            LineCollector.reset();
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("완료");
                alert.showAndWait();
                MainController.getInstance().updateView();
                MainController.getInstance().getProgressBar().setProgress(0);
                MainController.getInstance().enableButtons();
                MainController.getInstance().getAllCheckBox().setSelected(false);
            });
        }
    }

    public static void start() {
        if (executing) {
            return;
        }

        executing = true;
        property = new SimpleDoubleProperty();

        property.addListener((observable, oldValue, newValue) -> {
            MainController.getInstance().getProgressBar().progressProperty().bind(property);
        });

        Executors.newSingleThreadExecutor().execute(() -> {
            int size = ClipRepository.getSelectedClips().size();
            for (int i = 0; i < size; i++) {
                Clip clip = ClipRepository.getSelectedClips().pollFirst();
                if(MainController.getInstance().getCheckMultiThreadItem().isSelected()) {
                    CompletableFuture<Integer> completableFuture = CommandExecutor.executeStretchAsync(clip);
                    completableFuture.thenRunAsync(() -> {
                        postRendering(clip);
                    });
                }
                else {
                    CommandExecutor.executeStretch(clip);
                    postRendering(clip);
                }
            }
        });
    }

}
