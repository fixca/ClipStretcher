package me.fixca.clipstretcher.gui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import me.fixca.clipstretcher.Main;

public class TestScene {


    public TestScene() {
        try {
            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("main.fxml"));
            Scene scene = new Scene(root);
            Main.getPrimaryStage().setTitle("Test");
            Main.getPrimaryStage().setScene(scene);
            Main.getPrimaryStage().show();
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }

}
