package by.myshkovets.app.javaFX.client;


import by.myshkovets.app.javaFX.сontroller.StartPageController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Launcher extends Application {

    public static void main(String[] args) {
        launch(args);
    }
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/start_page.fxml"));
        Parent root = loader.load();
        StartPageController startPageController = loader.getController();
        startPageController.setStage(primaryStage);
        primaryStage.setScene(new Scene(root));
        primaryStage.setTitle("Авторизация");
        primaryStage.show();



    }
}


