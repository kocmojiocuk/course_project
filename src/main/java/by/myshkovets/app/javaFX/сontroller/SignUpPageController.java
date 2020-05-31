package by.myshkovets.app.javaFX.сontroller;

import by.myshkovets.app.javaFX.client.connection.Connection;
import by.myshkovets.app.javaFX.entity.account.Account;
import by.myshkovets.app.javaFX.entity.account.Role;
import by.myshkovets.app.javaFX.message.MessageType;
import by.myshkovets.app.javaFX.validator.ActionValidator;
import by.myshkovets.app.javaFX.validator.MessageValidator;
import by.myshkovets.app.javaFX.сontroller.notification.SignUpNotificationController;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SignUpPageController implements Initializable {

    private Stage stage;

    @FXML
    private JFXTextField nameField;

    @FXML
    private JFXTextField surnameField;

    @FXML
    private JFXTextField mailField;

    @FXML
    private JFXTextField loginField;

    @FXML
    private JFXTextField passwordField;

    @FXML
    private Label labelLoginInfo;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void close(){
        this.stage.close();
    }

    @FXML
    void registrationOnAction(ActionEvent event) throws IOException {


        Account fullAccount = Account.builder()
                .name(nameField.getText())
                .surname(surnameField.getText())
                .mail(mailField.getText())
                .login(loginField.getText())
                .password(passwordField.getText())
                .role(Role.USER)
                .build();

        if(MessageValidator.registrationIsRight(fullAccount)){

            Connection.getInstance().sendMessage(MessageType.SIGN_UP,fullAccount);
            JSONObject message = Connection.getInstance().readMessage();

            if(message.getBoolean(MessageType.SIGN_UP_CONFIRMATION.toString())){
                signUpNotification();
                close();
            }
            else{
                labelLoginInfo.setTextFill(Color.RED);
                labelLoginInfo.setText("Данный логин уже существует");
            }
        }


    }

    public void signUpNotification() throws IOException {
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/sign_up_notification.fxml"));
        stage.setScene(new Scene(loader.load()));
        stage.initModality(Modality.APPLICATION_MODAL);

        SignUpNotificationController controller = loader.getController();
        controller.setStage(stage);
        stage.setResizable(false);
        stage.showAndWait();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        ActionValidator validator = new ActionValidator();
        validator.regexCyrillicValidatorOnAction(nameField);
        validator.regexCyrillicValidatorOnAction(surnameField);
        validator.regexMailValidatorOnAction(mailField);
        validator.regexLatinValidatorOnAction(loginField);
        validator.regexLatinValidatorOnAction(passwordField);


    }
}
