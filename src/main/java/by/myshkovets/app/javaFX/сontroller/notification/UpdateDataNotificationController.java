package by.myshkovets.app.javaFX.сontroller.notification;

import by.myshkovets.app.javaFX.сontroller.AdminPageController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class UpdateDataNotificationController implements Initializable {
    private Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    private Label text;

    @FXML
    public void confirmationOnOnAction(ActionEvent event) {
        stage.close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        text.setText(AdminPageController.getNotification());
    }
}
