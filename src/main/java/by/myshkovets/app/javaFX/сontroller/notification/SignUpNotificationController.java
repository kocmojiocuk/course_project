package by.myshkovets.app.javaFX.сontroller.notification;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.stage.Stage;

public class SignUpNotificationController {

    private Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    public void confirmationOnAction(ActionEvent event) {
        stage.close();
    }
}
