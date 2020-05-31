package by.myshkovets.app.javaFX.сontroller;

import by.myshkovets.app.javaFX.comparator.StatisticsDataPropertyComparator;
import by.myshkovets.app.javaFX.datapool.DataPool;
import by.myshkovets.app.javaFX.entity.tables.Goal;
import by.myshkovets.app.javaFX.entity.tables.StatisticsDataProperty;
import com.jfoenix.controls.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class ExpertPageController  implements Initializable {


    private ObservableList<Goal> observableList;
    private List<Goal> observableListChoice = new ArrayList<>();
    private ObservableList<StatisticsDataProperty> observableStatisticsList;
    List<JFXRadioButton> radioButtonList;
    private TextField[][] buttons;
    private int countOfExperts;
    private int countOfGoals = 0;
    private ToggleGroup group = new ToggleGroup();
    private int bufferCount = 0;





    @FXML
    private JFXListView<Goal> listViewAnalysis;

    @FXML
    private JFXListView<Goal> listViewExpert;

    @FXML
    private LineChart<String, Number> LineChartAnalysis;

    @FXML
    private PieChart pieChartAnalysis;

    @FXML
    private JFXListView<Goal> listViewChoise_1;

    @FXML
    private TableView<StatisticsDataProperty> statisticsTable;

    @FXML
    private JFXListView<Goal> listViewChoice_2;

    @FXML
    private TableColumn<StatisticsDataProperty, String> operation;

    @FXML
    private TableColumn<StatisticsDataProperty, String> content;

    @FXML
    private TableColumn<StatisticsDataProperty, String> quantity;

    @FXML
    private TableColumn<StatisticsDataProperty, String> cash;

    @FXML
    private TableColumn<StatisticsDataProperty, String> date;

    @FXML
    private JFXTextField expertCountText;

    @FXML
    private JFXComboBox<String> choiceCount;

    @FXML
    private JFXTextField additionalTextField;

    @FXML
    private JFXButton additionalButton;

    @FXML
    private GridPane gridPane1;

    @FXML
    private GridPane gridPane2;

    @FXML
    private JFXTabPane tabPane;

    @FXML
    private Label label1;
    @FXML
    private Label label2_1;
    @FXML
    private Label label2_2;
    @FXML
    private Label label3;
    @FXML
    private Label removeArrow;
    @FXML
    private Label addArrow;
    @FXML
    private Label expertImage;
    @FXML
    private Label updateChart;
    @FXML
    private Label label_1;
    @FXML
    private Label label_2;
    @FXML
    private Label label_3;
    @FXML
    void additionalButtonAction(ActionEvent event) {
        countOfExperts = Integer.valueOf(additionalTextField.getText());
        String number = null;
        switch (countOfExperts) {
            case 11:
                number = "одиннадцать";
                break;
            case 12:
                number = "двенадцать";
                break;
            case 13:
                number = "тринадцать";
                break;
            case 14:
                number = "четырнадцать";
                break;
            case 15:
                number = "пятнадцать";
                break;
            case 16:
                number = "шестнадцать";
                break;
            case 17:
                number = "семнадцать";
                break;
            case 18:
                number = "восемнадцать";
                break;
            case 19:
                number = "девятнадцать";
                break;
            case 20:
                number = "двадцать";
                break;
        }

        choiceCount.setValue(number);
        additionalButton.setVisible(false);
        additionalTextField.setVisible(false);

    }


    void addArrowAction() {
        listViewChoice_2.getItems().addAll(listViewChoise_1.getSelectionModel().getSelectedItems());
        listViewChoise_1.getSelectionModel().clearSelection();
        countOfGoals++;

    }

    void removeArrowAction() {
        if (!listViewChoice_2.getItems().isEmpty()) {
            listViewChoice_2.getItems().remove(listViewChoice_2.getItems().size() - 1);
            countOfGoals--;
        }

    }


    @FXML
    void choiceAction(ActionEvent event) {
        String countString = choiceCount.getValue();
        List<String> list = choiceCount.getItems();
        AtomicInteger i = new AtomicInteger(0);
        list.stream().peek(e -> i.incrementAndGet()).filter(e -> e.equals(countString)).findAny();
        if (i.get() == 10) {
            additionalTextField.setVisible(true);
            additionalButton.setVisible(true);
        }
        countOfExperts = i.get();
        countOfGoals = listViewChoice_2.getItems().size() + 1;
    }

    void choiceCountAction() {
        makeGradPane();
    }


    void makeGradPane() {
        ToggleGroup group = new ToggleGroup();
        List<JFXRadioButton> radioButtonList = new ArrayList<>();

        for (int i = 0; i < countOfExperts; ++i) {
            radioButtonList.add(new JFXRadioButton("Эксперт " + (i + 1)));
            radioButtonList.get(i).setUserData(i);
            radioButtonList.get(i).setToggleGroup(group);

            gridPane1.add(radioButtonList.get(i), i, 0);
        }
        this.group = group;
        this.radioButtonList = radioButtonList;

        TextField[][] buttons = new TextField[countOfExperts][countOfGoals - 1];
        for (int i = 0; i < countOfExperts; ++i) {
            for (int j = 1; j < countOfGoals; ++j) {
                buttons[i][j - 1] = new TextField(("0"));
                gridPane1.add(buttons[i][j - 1], i, j);
            }
        }

        this.buttons = buttons;






        int i = 1;
        ObservableList<Goal> listRefactor = listViewChoice_2.getItems();
        for(Goal goal: listRefactor){
            String line = "a" + i + ":  " + goal.getGoal();
           line = line.substring(0, 35) + "\n" + line.substring(35, line.length());
            goal.setGoal(line);
            i++;
        }
        listViewExpert.setItems(listRefactor);
    }


    @FXML
    void calculateAction(ActionEvent event) {
        gridPane1.setVisible(false);
        gridPane2.setVisible(true);
        int[][] array = new int[countOfExperts][countOfGoals - 1];


        for (int i = 0; i < countOfExperts; ++i) {
            for (int j = 0; j < countOfGoals - 1; ++j) {
                array[i][j] = Integer.valueOf(buttons[i][j].getText().substring(8));
            }
        }
        int value;
        int[][] newArray = new int[countOfGoals][countOfGoals];
        for (int i = 0; i < countOfExperts; ++i) {
            for (int j = 0; j < countOfGoals - 1; ++j) {
                value = array[i][j];
                for(int k = j + 1; k < countOfGoals - 1; ++k){

                    newArray[value][array[i][k]]++;
                }
            }
        }


        gridPane2.add(new TextField("m ik"), 0, 0);
        for(int i = 1; i < countOfGoals; i++){
            gridPane2.add(new TextField("а" + i), i, 0);
        }
        for(int i = 1; i < countOfGoals; i++){
            gridPane2.add(new TextField("a" + i), 0, i);
        }
        for(int i = 1; i < countOfGoals; ++i){
            for(int j = 1; j < countOfGoals; ++j){
                gridPane2.add(new TextField(newArray[i][j] + ""), j, i);
            }
        }
        gridPane2.setVisible(true);

        int maxValue = 0, bufferMax = 0, colum = 0;
        for(int i = 1; i < countOfGoals; ++i){
            for(int j = 1; j < countOfGoals; ++j){
                maxValue += newArray[i][j];
            }
            if(maxValue > bufferMax){
                bufferMax = maxValue;
                colum = i;
            }
            maxValue = 0;
        }
        gridPane2.getChildren().get(colum + countOfGoals - 1).setStyle("-fx-background-color: #B16796;");
        for(int i = (countOfGoals - 1) * (1 + colum) + 1; i < (countOfGoals - 1)* (1 + colum) + countOfGoals; ++i){
            gridPane2.getChildren().get(i).setStyle("-fx-background-color: #B16796;");

        }
        String a = ((TextField)gridPane2.getChildren().get(colum + countOfGoals - 1)).getText().trim();
        for(Goal goal: listViewExpert.getItems()){
            if(goal.getGoal().contains(a)){
                a = goal.getGoal().trim().substring(4);
            }
        }
        label_3.setText(a);

        label_1.setVisible(true);
        label_2.setVisible(true);
        label_3.setVisible(true);



    }

    ImageView getImage(String filename){
        ImageView image = new ImageView(filename);
        image.setFitHeight(20);
        image.setFitWidth(20);
        return image;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        DataPool dataPool = new DataPool();
        observableList = dataPool.set2List();
        observableStatisticsList = dataPool.setList();


        label_1.setVisible(false);
        label_2.setVisible(false);
        label_3.setVisible(false);
        additionalButton.setVisible(false);
        additionalTextField.setVisible(false);


        label1.setGraphic(getImage("/image/next.png"));
        label2_1.setGraphic(getImage("/image/back.png"));
        label2_2.setGraphic(getImage("/image/next.png"));
        label3.setGraphic(getImage("/image/back.png"));
        addArrow.setGraphic(getImage("/image/up.png"));
        removeArrow.setGraphic(getImage("/image/down.png"));
        ImageView image1 = new ImageView("/image/refresh.png");
        image1.setFitHeight(40);
        image1.setFitWidth(40);
        updateChart.setGraphic(image1);


        ImageView image = new ImageView("/image/boss.png");
        image.setFitHeight(170);
        image.setFitWidth(160);
        expertImage.setGraphic(image);


        label1.setOnMouseClicked(event -> {
            tabPane.getSelectionModel().selectNext();
        });
        label2_1.setOnMouseClicked(event -> {
            tabPane.getSelectionModel().selectPrevious();

        });
        label2_2.setOnMouseClicked(event -> {
            gridPane1.setVisible(true);
            tabPane.getSelectionModel().selectNext();
            choiceCountAction();

        });
        label3.setOnMouseClicked(event -> {
            tabPane.getSelectionModel().selectPrevious();
            gridPane2.setVisible(false);
            gridPane1.setVisible(true);

            int size1 = gridPane1.getChildren().size(), size2 = gridPane2.getChildren().size();
            for(int i = 0; i < size1; ++i){
                gridPane1.getChildren().remove(0);
            }
            for(int i = 0; i < size2; ++i){
                gridPane2.getChildren().remove(0);
            }

        });
        addArrow.setOnMouseClicked(event -> {
            addArrowAction();
        });
        removeArrow.setOnMouseClicked(event -> {
            removeArrowAction();
        });
        updateChart.setOnMouseClicked(event -> {
            int size = LineChartAnalysis.getData().size();
            for(int i = 0; i < size; ++i){
                LineChartAnalysis.getData().remove(0);

            }
        });

        ObservableList<String> choiceCountList = FXCollections.observableArrayList();
        choiceCountList.addAll("один", "два", "три", "четыре");
        choiceCountList.addAll("пять", "шесть", "семь", "восемь");
        choiceCountList.addAll("девять", "другое");

        choiceCount.getItems().setAll(choiceCountList);





        operation.setCellValueFactory(cellData -> cellData.getValue().operationProperty());
        content.setCellValueFactory(cellData -> cellData.getValue().contentProperty());
        quantity.setCellValueFactory(cellData -> cellData.getValue().quantityProperty());
        cash.setCellValueFactory(cellData -> cellData.getValue().cashProperty());
        date.setCellValueFactory(cellData -> cellData.getValue().getDateToStringProperty());

        statisticsTable.setItems(observableStatisticsList);


        listViewChoise_1.setItems(observableList);
        listViewChoise_1.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);




        listViewChoise_1.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (observable != null && observable.getValue() != null) {
                observableStatisticsList.clear();
                observableStatisticsList.addAll(observable.getValue().getStatisticsList());
            }
        });

        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
        for (Goal goal : observableList) {
            int sum = 0;
            for (StatisticsDataProperty data : goal.getStatisticsList()) {
                sum += Integer.valueOf(data.getCash());
            }

            pieChartData.add(new PieChart.Data(goal.getGoal(), sum));

        }

        pieChartAnalysis.setData(pieChartData);
        pieChartAnalysis.setTitle("Денежные средства");

        listViewAnalysis.setItems(observableList);
        listViewAnalysis.setOnMouseClicked(event -> {
            XYChart.Series<String, Number> series = new XYChart.Series<>();
            List<StatisticsDataProperty> list = listViewAnalysis.getSelectionModel().getSelectedItem().getStatisticsList();
            list.sort(new StatisticsDataPropertyComparator());
            for (StatisticsDataProperty data : list) {
                series.getData().add(new XYChart.Data<String, Number>(data.getDate().toString(), Integer.valueOf(data.getQuantity())));
            }
            LineChartAnalysis.getData().add(series);
        });
        LineChartAnalysis.setTitle("Количественный прогресс");
                listViewExpert.setOnMouseClicked(event -> {

                    if (group.getSelectedToggle() != null) {

                        int number = Integer.valueOf(group.getSelectedToggle().getUserData().toString());

                        String line = "       " + listViewExpert.getSelectionModel().getSelectedItem().getGoal().substring(0, 2);
                        buttons[number][bufferCount].setText(line);
                        bufferCount++;
                        if(bufferCount + 1 == countOfGoals){
                            bufferCount = 0;
                        }


                    }
                });



    }


}
