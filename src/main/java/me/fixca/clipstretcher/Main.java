package me.fixca.clipstretcher;

import javafx.application.Application;
import javafx.stage.Stage;
import lombok.Getter;
import me.fixca.clipstretcher.gui.TestScene;

public class Main extends Application {

    @Getter
    private static Stage primaryStage;

    public static void main(String[] args) {

        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        primaryStage = stage;
        new TestScene();
    }
}
