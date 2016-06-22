package finalproject;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Reflection;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextBoundsType;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author qqq4343
 */
public class LoginProject extends Application {

    //order - pickup or delivery
    //If pickup, only full name, phone number are required.
    //If delivery, address is also required
    public static String loginId = null;
    public static String loginName = null;

    //valuable
    private Label laId, laName, laResultLogin;
    private RadioButton raAdmin, raMember;
    private TextField tfId, tfPassField;

    private double initX;
    private double initY;

    private ArrayList<Employee> listEmp = new ArrayList<Employee>();

    private static Stage LOGIN_STAGE = new Stage();

    @Override
    public void start(Stage primaryStage) {
        LOGIN_STAGE = primaryStage;
        LogInHandler hanLogin = new LogInHandler();

        Group rootGroup = new Group();
        Circle loginIn = new Circle(200, 200, 220);
        loginIn.setFill(new RadialGradient(-0.3, 135, 0.5, 0.5, 1,
                true, CycleMethod.NO_CYCLE, new Stop[]{
                    new Stop(0, Color.TOMATO),
                    new Stop(1, Color.LIGHTCORAL)}));

        rootGroup.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                initX = t.getScreenX() - primaryStage.getX();
                initY = t.getScreenY() - primaryStage.getY();
            }
        });
        rootGroup.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                primaryStage.setX(t.getScreenX() - initX);
                primaryStage.setY(t.getScreenY() - initY);
            }
        });
        //Set dropShadow effect
        DropShadow shadowEffect = new DropShadow();
        shadowEffect.setOffsetX(5.0);
        shadowEffect.setOffsetY(2);
        shadowEffect.setRadius(3);
        shadowEffect.setSpread(2);
        shadowEffect.setColor(Color.MEDIUMSEAGREEN);
        //Set reflection effect
        Reflection reflection = new Reflection();
        reflection.setBottomOpacity(2);
        reflection.setFraction(0.8);
        reflection.setTopOffset(-11);
        //Radio button distinuish admin and member
        ToggleGroup userGroup = new ToggleGroup();
        raAdmin = new RadioButton("Admin");
        raMember = new RadioButton("Member");
        raAdmin.setToggleGroup(userGroup);
        raMember.setToggleGroup(userGroup);
        raMember.setSelected(true);
        raMember.requestFocus();

        HBox hradio = new HBox();
        hradio.setSpacing(20);
        hradio.setPadding(new Insets(0, 0, 10, 20));
        hradio.setAlignment(Pos.TOP_CENTER);
        hradio.getChildren().addAll(raAdmin, raMember);

        Text Title = new Text("Welcome Oh! My Pizza");
        Title.setFill(Color.WHITESMOKE);
        Title.setEffect(reflection);
        Title.setEffect(shadowEffect);
        Title.setBoundsType(TextBoundsType.VISUAL);
        Title.setFont(Font.font(Font.getDefault().getFamily(), FontWeight.BOLD, 30));
        //ID
        laId = new Label("MEMBER ID");
        tfId = new TextField();
        tfId.setPromptText("Enter you ID.");
        Tooltip idTooltip = new Tooltip();
        idTooltip.setText("\n Must select either Admin/Member");
        tfId.setTooltip(idTooltip);
        //Password
        laName = new Label("          NAME");
        tfPassField = new PasswordField();
        tfPassField.setPromptText("Enter you Name.");
        Tooltip passTooltip = new Tooltip();
        passTooltip.setText("\n Must be enter proper name");
        tfPassField.setTooltip(passTooltip);
        //The result when you log in 
        laResultLogin = new Label("");
        laResultLogin.setAlignment(Pos.BOTTOM_RIGHT);
        tfPassField.setOnAction(hanLogin);
        //HBox for ID
        HBox hBoxId = new HBox();
        hBoxId.setSpacing(10);
        hBoxId.setPadding(new Insets(10, 0, 0, 0));
        hBoxId.getChildren().addAll(laId, tfId);
        //HBox for Password
        HBox hBoxPassword = new HBox();
        hBoxPassword.setSpacing(10);
        hBoxPassword.setPadding(new Insets(10, 0, 0, 0));
        hBoxPassword.getChildren().addAll(laName, tfPassField);
        //VBox for ID, Password, Result
        VBox vBoxlogin = new VBox();
        vBoxlogin.getChildren().addAll(hBoxId, hBoxPassword, laResultLogin);
        //Button for login
        Button bnLogin = new Button("Log In");
        bnLogin.setPrefSize(100, 70);
        bnLogin.setOnAction(hanLogin);
        //
        HBox hBoxLogin = new HBox();
        hBoxLogin.setSpacing(10);
        hBoxLogin.getChildren().addAll(vBoxlogin, bnLogin);
        //Button on bottom
        Button bnSearchPassword = new Button("Forgot your password?");
        bnSearchPassword.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
//                    new findPw();
                    //LOGIN_STAGE.setIconified(true);                  
                    Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Information Dialog");
                    alert.setHeaderText(null);
                    alert.setContentText("Not Complete this page \nNow Implementing, Developing ");
                    alert.showAndWait();
                } catch (Exception e) {

                }
            }
        });
        Button bnJoin = new Button("Sign Up");
        bnJoin.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Information Dialog");
                alert.setHeaderText(null);
                alert.setContentText("Same page like admin to add new employee");
                alert.showAndWait();
                
                JoinInputForm adminPizza = new JoinInputForm();
                Stage adminStage = new Stage();
                adminPizza.start(adminStage);
            }
        });
        HBox hBoxButton = new HBox();
        hBoxButton.setSpacing(20);
        hBoxButton.setPadding(new Insets(20, 0, 0, 50));
        hBoxButton.getChildren().addAll(bnSearchPassword, bnJoin);
        //
        VBox totalVbox = new VBox();
        totalVbox.setSpacing(10);
        totalVbox.setPadding(new Insets(70, 0, 0, 30));
        totalVbox.setAlignment(Pos.TOP_CENTER);
        totalVbox.getChildren().addAll(hradio, Title, hBoxLogin, hBoxButton);
        //Add the rootGroup
        rootGroup.getChildren().addAll(loginIn, totalVbox);
        //Scene
        Scene scene = new Scene(rootGroup, 400, 400, Color.TRANSPARENT);
        scene.getStylesheets().add(OrderPizza.class.getResource("orderPizza.css").toExternalForm());
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.setTitle("Login In");
        primaryStage.setScene(scene);
        primaryStage.centerOnScreen();
        primaryStage.show();
    }

    private class LogInHandler implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {
            if (raAdmin.isSelected() && tfId.getText().equalsIgnoreCase("admin")
                    && tfPassField.getText().equals("1234")) {
                LOGIN_STAGE.setIconified(true);
                //Move to Admin page
                JoinInputForm adminPizza = new JoinInputForm();
                Stage adminStage = new Stage();
                adminPizza.start(adminStage);
            } else if (raMember.isSelected()) {
                //Guest page   
                ObjectInputStream ois = null;
                boolean isFound = false;
                //Define the file.
                File myDir = new File("MyFiles");
                File file = new File(myDir.getName() + "\\logins.dat");
                try {
                    ois = new ObjectInputStream(new FileInputStream(file));
                    listEmp = (ArrayList<Employee>) ois.readObject();
                    for (int i = 0; i < listEmp.size(); i++) {
                        //Match the name and id
                        if (listEmp.get(i).getId().equals(tfId.getText().trim())
                                && listEmp.get(i).getName().equals(tfPassField.getText().trim())) {
                            isFound = true;
                            LOGIN_STAGE.setIconified(true);
//                            Alert alert = new Alert(AlertType.INFORMATION);
//                            alert.setTitle("Information Dialog");
//                            alert.setHeaderText(null);
//                            alert.setContentText("ID : " + listEmp.get(i).getId()
//                                    + " Name : " + listEmp.get(i).getName());
//
//                            alert.showAndWait();
//                            LOGIN_STAGE.hide();
                            loginId = tfId.getText();
                            loginName = tfPassField.getText();
                            OrderPizza orderPizza = new OrderPizza();
                            Stage orderStage = new Stage();
                            orderPizza.start(orderStage);
                        }
                    }
                    if (!isFound) {
                        Alert alert = new Alert(AlertType.WARNING);
                        alert.setTitle("Warning Dialog");
                        alert.setHeaderText("Look, a Warning Dialog");
                        alert.setContentText("ID : " + tfId.getText()
                                + "   Name : " + tfPassField.getText() + "\n Invalid password!!! \n Please Try Again ~~^^ ");
                        alert.showAndWait();
                        laResultLogin.setText("Please Try Again");
                        laResultLogin.setPrefSize(200, 40);
                        laResultLogin.setAlignment(Pos.BOTTOM_RIGHT);
                        laResultLogin.setStyle(
                                "-fx-font-size: 15px;"
                                + "-fx-font-weight: bold;"
                                + "-fx-text-fill: rgb(201, 39, 30)"
                        );
                        tfId.setText("");
                        tfId.requestFocus();
                        tfPassField.setText("");
//                                 resultLogin.setTextFill(Color.rgb(201, 39, 30));

                        ois.close();
                    }
                } catch (Exception e) {
                }
                //  new OrderPizza();
            }

        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
