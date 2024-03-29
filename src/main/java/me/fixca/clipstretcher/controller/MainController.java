package me.fixca.clipstretcher.controller;

import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
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
    @Getter
    private CheckBox allCheckBox;

    @FXML
    @Getter
    private ProgressBar progressBar;

    @FXML
    private ListView<ClipCheckBox> elementClickListView;

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

    @FXML
    private ScrollPane commandScrollPane;

    @FXML
    @Getter
    private CheckMenuItem checkGPUAccelItem;

    @FXML
    @Getter
    private CheckMenuItem checkMultiThreadItem;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        instance = this;
        updateView();

        executeButton.setOnAction(e -> {
            if(ClipRepository.getSelectedClips().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Please select some clips to start the progress!");
                alert.show();
                return;
            }
            if(!ExecutorHandler.isExecuting()) {
                disableButtons();
                ExecutorHandler.start();
            }
        });

        refreshButton.setOnAction(e -> {
            updateView();
        });

        allCheckBox.setOnAction(e -> {
            if(allCheckBox.isSelected()) {
                for (ClipCheckBox checkBox : elementClickListView.getItems()) {
                    if(!checkBox.isSelected()) {
                        checkBox.select();
                    }
                }
            }
            else {
                for (ClipCheckBox checkBox : elementClickListView.getItems()) {
                    if(checkBox.isSelected()) {
                        checkBox.remove();
                    }
                }
            }
        });

        service.scheduleAtFixedRate(() -> {
            Platform.runLater(() -> {
                try {
                    commandLabel.setText(LineCollector.getOutputStream().toString("utf-8"));
                    ObservableValue value = commandLabel.heightProperty();
                    if(value != null) {
                        commandScrollPane.vvalueProperty().bind(value);
                    }

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
        List<ClipCheckBox> checkBoxList = new LinkedList<>();
        for (Clip clip : ClipRepository.getClips()) {
            fileList.add(clip.getFileName());
            ClipCheckBox checkBox = new ClipCheckBox(clip);
            checkBoxList.add(checkBox);
        }
        elementFileListView.setItems(FXCollections.observableList(fileList));
        elementClickListView.setItems(FXCollections.observableList(checkBoxList));
    }

    public void disableButtons() {
        executeButton.setDisable(true);
        refreshButton.setDisable(true);
        checkGPUAccelItem.setDisable(true);
        checkMultiThreadItem.setDisable(true);
        allCheckBox.setDisable(true);
        for (ClipCheckBox clipCheckBox : elementClickListView.getItems()) {
            clipCheckBox.setDisable(true);
        }
    }

    public void enableButtons() {
        executeButton.setDisable(false);
        refreshButton.setDisable(false);
        checkGPUAccelItem.setDisable(false);
        checkMultiThreadItem.setDisable(false);
        allCheckBox.setDisable(false);
    }
}
