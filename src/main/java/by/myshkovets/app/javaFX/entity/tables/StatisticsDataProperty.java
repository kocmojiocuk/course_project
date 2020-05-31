package by.myshkovets.app.javaFX.entity.tables;

import by.myshkovets.app.javaFX.entity.Entity;
import by.myshkovets.app.javaFX.parser.dateParser.DateParser;
import com.jfoenix.controls.JFXCheckBox;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import java.time.LocalDate;


public class StatisticsDataProperty extends Entity {
        private StringProperty operation;
        private StringProperty content;
        private StringProperty quantity;
        private StringProperty cash;
        private StringProperty dateString;
        private LocalDate date;
        transient public  JFXCheckBox checker;

    public String getDateString() {
        return dateString.get();
    }

    public StringProperty dateStringProperty() {
        return dateString;
    }

    public void setDateString(String dateString) {

        this.dateString.set(dateString.replace('-', '.'));
    }


    public JFXCheckBox getChecker() {
        return checker;
    }

    public void setChecker(JFXCheckBox checker) {
        this.checker = checker;
    }

    public StatisticsDataProperty(String operation, String content, String quantity, String cash, LocalDate date) {
        super(0);
        this.operation = new SimpleStringProperty(operation);
        this.content = new SimpleStringProperty(content);
        this.quantity = new SimpleStringProperty(quantity);
        this.cash = new SimpleStringProperty(cash);
        this.date = date;
        this.dateString = new SimpleStringProperty(date.toString().replace('-', '.'));
        this.checker = new JFXCheckBox();
    }
    public StatisticsDataProperty(int id, String operation, String content, String quantity, String cash, LocalDate date) {
        super(id);
        this.operation = new SimpleStringProperty(operation);
        this.content = new SimpleStringProperty(content);
        this.quantity = new SimpleStringProperty(quantity);
        this.cash = new SimpleStringProperty(cash);
        this.date = date;
        this.dateString = new SimpleStringProperty(date.toString().replace('-', '.'));

        //this.checker = new JFXCheckBox();
    }

    public StatisticsDataProperty(StatisticsData data) {
        super(data.getId());
        this.operation = new SimpleStringProperty(data.getOperation());
        this.content = new SimpleStringProperty(data.getContent());
        this.quantity = new SimpleStringProperty(data.getQuantity());
        this.cash = new SimpleStringProperty(data.getCash());
        this.date = data.getDate();
        this.dateString = new SimpleStringProperty(date.toString().replace('-', '.'));
        this.checker = new JFXCheckBox();
    }

    public String getOperation() {
        return operation.get();
    }

    public StringProperty operationProperty() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation.set(operation);
    }

    public String getContent() {
        return content.get();
    }

    public StringProperty contentProperty() {
        return content;
    }

    public void setContent(String content) {
        this.content.set(content);
    }

    public String getQuantity() {
        return quantity.get();
    }

    public StringProperty quantityProperty() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity.set(quantity);
    }

    public String getCash() {
        return cash.get();
    }

    public StringProperty cashProperty() {
        return cash;
    }

    public void setCash(String cash) {
        this.cash.set(cash);
    }


    public StringProperty getDateToStringProperty() {
        return dateString;

    }

    public String getDateToString() {
        return DateParser.format(date);
    }

    public LocalDate getDate(){
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "StatisticsDataProperty{" +
                "operation=" + operation +
                ", content=" + content +
                ", quantity=" + quantity +
                ", cash=" + cash +
                ", date=" + date +
                '}';
    }


}