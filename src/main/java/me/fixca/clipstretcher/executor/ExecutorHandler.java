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

public class ExecutorHandler {

    @Getter
    @Setter
    private static boolean executing = false;
    private static int originalAmount = -1;
    private static DoubleProperty property;

    public static void start() {
        if (executing) {
            return;
        }

        executing = true;
        property = new SimpleDoubleProperty();
        originalAmount = ClipRepository.getAllSelectedClips().size();

        property.addListener((observable, oldValue, newValue) -> {
            MainController.getInstance().getProgressBar().progressProperty().bind(property);
        });

        for (Clip clip : ClipRepository.getAllSelectedClips()) {
            CompletableFuture<Integer> completableFuture = CommandExecutor.executeStretch(clip);
            completableFuture.thenRunAsync(() -> {
                property.add(1 / originalAmount);
                ClipRepository.removeSelectedClip(clip);
                if (ClipRepository.getAllSelectedClips().size() == 0) {
                    executing = false;
                    LineCollector.reset();
                    Platform.runLater(() -> {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setContentText("완료");
                        alert.showAndWait();
                        MainController.getInstance().updateView();
                        MainController.getInstance().getProgressBar().setProgress(0);
                        MainController.getInstance().enableButtons();
                    });
                }
            });
        }

    }

}
