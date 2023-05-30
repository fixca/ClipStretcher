package me.fixca.clipstretcher.gui.controller;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import lombok.Getter;
import me.fixca.clipstretcher.Clip;
import me.fixca.clipstretcher.component.ClipCheckBox;
import me.fixca.clipstretcher.executor.ExecutorHandler;
import me.fixca.clipstretcher.executor.LineCollector;
import me.fixca.clipstretcher.storage.ClipParser;
import me.fixca.clipstretcher.storage.ClipRepository;

import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MainController implements Initializable {

    private static ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();

    @Getter
    private static MainController instance;

    @FXML
    private CheckBox allCheckBox;

    @FXML
    @Getter
    private ProgressBar progressBar;

    @FXML
    private ListView<CheckBox> elementClickListView;

    @FXML
    private ListView<String> elementFileListView;

    @FXML
    @Getter
    private Button executeButton;

    @FXML
    @Getter
    private Button refreshButton;

    @FXML
    @Getter
    private Label commandLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        instance = this;
        updateView();
        executeButton.setOnAction(e -> {
            if(ClipRepository.getAllSelectedClips().size() == 0) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Please select some clips to start the progress!");
                alert.show();
                return;
            }
            if(!ExecutorHandler.isExecuting()) {
                executeButton.setDisable(true);
                ExecutorHandler.start();
            }
        });
        refreshButton.setOnAction(e -> {
            updateView();
        });
        service.scheduleAtFixedRate(() -> {
            Platform.runLater(() -> {
                try {
                    MainController.getInstance().getCommandLabel().setText(LineCollector.getOutputStream().toString("utf-8"));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }, 0, 50, TimeUnit.MILLISECONDS);
    }

    public void updateView() {
        ClipRepository.wipe();
        ClipParser.parse();
        List<String> fileList = new LinkedList<>();
        List<CheckBox> checkBoxList = new LinkedList<>();
        for (Clip clip : ClipRepository.getAllClips()) {
            fileList.add(clip.getFileName());
            ClipCheckBox checkBox = new ClipCheckBox(clip);
            checkBoxList.add(checkBox);
        }
        elementFileListView.setItems(FXCollections.observableList(fileList));
        elementClickListView.setItems(FXCollections.observableList(checkBoxList));
    }
}
