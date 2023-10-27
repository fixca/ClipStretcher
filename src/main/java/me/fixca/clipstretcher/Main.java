package me.fixca.clipstretcher;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.Getter;
import me.fixca.clipstretcher.executor.FFmpegController;

public class Main extends Application {

    @Getter
    private static Stage primaryStage;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        primaryStage = stage;

        primaryStage.setOnCloseRequest(e -> {
            stopProcess();
        });

        try {
            FFmpegController.initFFmpeg();
            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("main.fxml"));
            Scene scene = new Scene(root);
            primaryStage.setTitle("Clip Stretcher");
            primaryStage.setScene(scene);
            primaryStage.show();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void stopProcess() {
        Platform.exit();
        System.exit(0);
    }
}
