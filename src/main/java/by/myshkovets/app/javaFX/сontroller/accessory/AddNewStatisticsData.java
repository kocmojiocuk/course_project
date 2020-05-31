package by.myshkovets.app.javaFX.сontroller.accessory;


import by.myshkovets.app.javaFX.client.connection.Connection;
import by.myshkovets.app.javaFX.entity.tables.StatisticsData;
import by.myshkovets.app.javaFX.entity.tables.StatisticsDataProperty;
import by.myshkovets.app.javaFX.message.MessageType;
import by.myshkovets.app.javaFX.validator.ActionValidator;
import by.myshkovets.app.javaFX.validator.MessageValidator;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.stage.Stage;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class AddNewStatisticsData implements Initializable {

    private Stage stage;
    private ObservableList<StatisticsDataProperty> dataList;


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

    public void setDataList(ObservableList<StatisticsDataProperty> dataList) {
        this.dataList = dataList;
    }
    public void setStage(Stage stage) {
        this.stage = stage;
    }





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
    public void mainButtonOnAction() {
        String operation = operationField.getText();
        String content = contentField.getText();
        String quantity = quantityField.getText();
        String cash = cashField.getText();
        LocalDate date = dateField.getValue();

        StatisticsDataProperty dataProperty = new StatisticsDataProperty(operation, content, quantity, cash, date);
        StatisticsData data = new StatisticsData(operation, content, quantity, cash, date);

        if(MessageValidator.statisticsDateIsRight(dataProperty)){

            Connection.getInstance().sendMessage(MessageType.ADD_NEW_STATISTICS_DATA, data);
            int id = (Integer) Connection.getInstance().readMessageObject();

            dataProperty.setId(id);
            dataList.add(dataProperty);
            operationField.setText(null);
            contentField.setText(null);
            quantityField.setText(null);
            cashField.setText(null);
            dateField.getEditor().setText(null);
        }



    }





    @FXML
    void close(ActionEvent event) {stage.close();}



    @FXML
    public void initialize(URL location, ResourceBundle resources) {

        ActionValidator validator = new ActionValidator();
        validator.installationValidation(this);

    }


}


