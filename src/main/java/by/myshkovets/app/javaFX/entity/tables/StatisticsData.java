package by.myshkovets.app.javaFX.entity.tables;

import by.myshkovets.app.javaFX.parser.dateParser.DateParser;
import by.myshkovets.app.javaFX.entity.Entity;

import java.time.LocalDate;

public class StatisticsData extends Entity {
    private String operation;
    private String content;
    private String quantity;
    private String cash;
    private LocalDate date;

    public StatisticsData(int id, String operation, String content, String quantity, String cash, LocalDate date) {
        super(id);
        this.operation = operation;
        this.content = content;
        this.quantity = quantity;
        this.cash = cash;
        this.date = date;
    }

    public StatisticsData(StatisticsDataProperty data) {
        super(data.getId());
        this.operation = data.getOperation();
        this.content = data.getContent();
        this.quantity = data.getQuantity();
        this.cash = data.getCash();
        this.date = data.getDate();
    }


    public StatisticsData(String operation, String content, String quantity, String cash, LocalDate date) {
        super(0);
        this.operation = operation;
        this.content = content;
        this.quantity = quantity;
        this.cash = cash;
        this.date = date;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getCash() {
        return cash;
    }

    public void setCash(String cash) {
        this.cash = cash;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
    public String getDateToString() {
        return DateParser.format(date);
    }

    @Override
    public String toString() {
        return "StatisticsData{" +
                "operation='" + operation + '\'' +
                ", content='" + content + '\'' +
                ", quantity='" + quantity + '\'' +
                ", cash='" + cash + '\'' +
                ", date=" + date +
                '}';
    }
}
