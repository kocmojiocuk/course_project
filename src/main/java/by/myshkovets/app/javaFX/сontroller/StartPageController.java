package by.myshkovets.app.javaFX.сontroller;

import by.myshkovets.app.javaFX.client.connection.Connection;
import by.myshkovets.app.javaFX.entity.account.Account;
import by.myshkovets.app.javaFX.entity.account.Role;
import by.myshkovets.app.javaFX.message.MessageType;
import com.google.gson.Gson;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDecorator;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class StartPageController implements Initializable {

    private Stage stage;

    @FXML
    private JFXTextField passwordField;

    @FXML
    private JFXTextField loginField;


    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void close(){
        this.stage.close();
    }



    public void makeAdminPage(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/admin_page.fxml"));
        Stage primaryStage = new Stage();
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        JFXDecorator decorator = new JFXDecorator(primaryStage, root);
        Scene scene = new Scene(decorator);
        scene.getStylesheets().add(getClass().getResource("/css/admin.css").toExternalForm());

        primaryStage.setScene(scene);
        //primaryStage.setResizable(false);
        primaryStage.setTitle("Table");
        primaryStage.showAndWait();
    }

    public void makeExpertPage(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/expert_page.fxml"));
        Stage primaryStage = new Stage();
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        JFXDecorator decorator = new JFXDecorator(primaryStage, root);
        Scene scene = new Scene(decorator);
        scene.getStylesheets().add(getClass().getResource("/css/expert.css").toExternalForm());

        primaryStage.setScene(scene);
        //primaryStage.setResizable(false);
        primaryStage.setTitle("Table");
        primaryStage.showAndWait();
    }

    @FXML
    void signUpAction(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/sign_up_page.fxml"));

        stage.setScene(new Scene(loader.load()));
        SignUpPageController signUpPageController = loader.getController();
        signUpPageController.setStage(stage);
        stage.setOnCloseRequest(e -> {
                    signUpPageController.close();
                });
        stage.setResizable(false);
        stage.setTitle("Регистрация");
        stage.showAndWait();
    }

    @FXML
    void singInAction(ActionEvent event){
        Account account = Account.builder()
                .login(loginField.getText())
                .password(passwordField.getText())
                .build();


        Connection.getInstance().sendMessage(MessageType.SIGN_IN, account);
        JSONObject response = Connection.getInstance().readMessage();
        boolean accountIsExist = response.getBoolean(MessageType.IS_EXIST.toString());
        if(accountIsExist){
            Gson gson = new Gson();
            Account fullAccount = gson.fromJson((String)response.get(MessageType.ACCOUNT.toString()), Account.class);
            if(fullAccount.getRole().equals(Role.ADMIN)){
                makeAdminPage();
            }
            else{
                makeExpertPage();
            }
        }

    }

    @FXML
    private JFXButton but;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}