package by.myshkovets.app.javaFX.сontroller.accessory;

import by.myshkovets.app.javaFX.client.connection.Connection;
import by.myshkovets.app.javaFX.entity.tables.StatisticsData;
import by.myshkovets.app.javaFX.entity.tables.StatisticsDataProperty;
import by.myshkovets.app.javaFX.message.MessageType;
import by.myshkovets.app.javaFX.validator.ActionValidator;
import by.myshkovets.app.javaFX.validator.MessageValidator;
import by.myshkovets.app.javaFX.сontroller.AdminPageController;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class UpdateStatisticsData implements Initializable {

    private Stage stage;
    private ObservableList<StatisticsDataProperty> dataList;
    private StatisticsDataProperty updateData;

    @FXML
    private JFXTextField operationField;

    @FXML
    private JFXTextField contentField;

    @FXML
    private JFXTextField quantityField;

    @FXML
    private JFXTextField cashField;

    @FXML
    private JFXDatePicker dateField;

    @FXML
    private JFXButton mainButton;

    public void setDataList(ObservableList<StatisticsDataProperty> dataList) {this.dataList = dataList; }
    public void setStage(Stage stage) {
        this.stage = stage;
    }
    public Stage getStage() {return stage;}

    public JFXTextField getOperationField() {
        return operationField;
    }

    public JFXTextField getContentField() {
        return contentField;
    }

    public JFXTextField getQuantityField() {
        return quantityField;
    }

    public JFXTextField getCashField() {
        return cashField;
    }


    @FXML
    public void mainButtonOnAction()  {
        updateData.setOperation(operationField.getText());
        updateData.setContent(contentField.getText());
        updateData.setQuantity(quantityField.getText());
        updateData.setCash(cashField.getText());
        if(dateField.getValue() != null) {
            updateData.setDateString(dateField.getValue().toString());
        }



        if(MessageValidator.statisticsDateIsRight(updateData)) {
            StatisticsData data = new StatisticsData(updateData);
            Connection.getInstance().sendMessage(MessageType.UPDATE_STATISTICS_DATA, data);
        }


    }




    @FXML
    void close(ActionEvent event) {
        stage.close();
    }




    @FXML
    public void initialize(URL location, ResourceBundle resources) {
        updateData = AdminPageController.getCurrentStatisticsChoice();
        operationField.setText(updateData.getOperation());
        contentField.setText(updateData.getContent());
        quantityField.setText(updateData.getQuantity());
        cashField.setText(updateData.getCash());
        dateField.getEditor().setText(updateData.getDateToString());

        ActionValidator validator = new ActionValidator();
        validator.installationValidation(this);


    }



}


