package by.myshkovets.app.javaFX.сontroller.accessory;

import by.myshkovets.app.javaFX.client.connection.Connection;
import by.myshkovets.app.javaFX.entity.tables.Goal;
import by.myshkovets.app.javaFX.message.MessageType;
import by.myshkovets.app.javaFX.сontroller.AdminPageController;
import com.jfoenix.controls.JFXTextArea;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class UpdateGoalData implements Initializable {

    private Stage stage;


    private Label label;
    private Goal goal;

    public Stage getStage() {
        return stage;
    }

    public Label getLabel() {
        return label;
    }

    public void setLabel(Label label) {
        this.label = label;
    }

    public Goal getGoal() {
        return goal;
    }

    public void setGoal(Goal goal) {
        this.goal = goal;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }



    @FXML
    private JFXTextArea textField;

    @FXML
    void close(ActionEvent event) {
        stage.close();
    }

    @FXML
    void save(ActionEvent event) {
        label.setId(" ");
        label.setText(textField.getText());
        goal.setGoal(textField.getText());
        Connection.getInstance().sendMessage(MessageType.UPDATE_GOAL_DATA);
        Connection.getInstance().readMessage();
        Connection.getInstance().sendMessage(goal);
        stage.close();


   }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        goal = AdminPageController.getCurrentGoalChoice();
        label = AdminPageController.getCurrentLabelChoice();
        textField.setText(label.getText());
    }
}