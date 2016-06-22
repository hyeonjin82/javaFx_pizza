package finalproject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 *
 * @author qqq4343
 */
public class JoinInputForm extends Application {

    private final Label[] la = new Label[2]; //Label for Customer Information
    private final TextField[] tf = new TextField[2]; //TextField for Customer Information
    private final HBox[] hinfo = new HBox[2]; //HBox for Customer Information
    private final String[] str = {"Member ID : ", "Member Name : "};
    private TableView<Employee> tableEmployee;

    @Override
    public void start(Stage primaryStage) {
        //Title for Customer Information
        Text joinTitle = new Text("Customer Information");
        joinTitle.setFont(Font.font("Arial Black", 30));
        joinTitle.setFill(Color.web("#BBBBBB"));
        joinTitle.setId("title-text");
        for (int i = 0; i < la.length; i++) {
            la[i] = new Label();
            la[i].setText(str[i]);
            la[i].setPrefSize(100, 10);
            la[i].setFont(Font.font("Arial", FontWeight.BOLD, 12));
            la[i].setAlignment(Pos.CENTER_RIGHT);
            tf[i] = new TextField();
            tf[i].setPrefColumnCount(25);
            tf[i].setAlignment(Pos.CENTER_LEFT);
            hinfo[i] = new HBox();
            hinfo[i].setAlignment(Pos.CENTER_LEFT);
            hinfo[i].setSpacing(5);
            hinfo[i].setPadding(new Insets(1));
            hinfo[i].setPrefSize(500, 30);
            hinfo[i].getChildren().addAll(la[i], tf[i]);
        }

//        //Table for employee list
//        tableEmployee = new TableView<Employee>();
//        tableEmployee.setEditable(false);
//        tableEmployee.getColumns().addAll(Employee.getColum(tableEmployee));
//        tableEmployee.setItems(getEmployeeItems());
        //Button for confirm
        Button btnJoin = new Button("Confirm");
        btnJoin.setAlignment(Pos.CENTER_RIGHT);
        btnJoin.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                System.out.println("Start join");
                ObjectOutputStream oos = null;
                ObjectInputStream ois = null;

                //Create a custom directory for the files
                File myDir = new File("MyFiles");
                if (!myDir.exists()) {
                    myDir.mkdir();
                }
                //Define the file.
                File file = new File(myDir.getName() + "\\logins.dat");
                //Create the file if it doesn't exist
                if (!file.exists()) {
                    try {
                        file.createNewFile();
                    } catch (IOException ex) {
                        System.out.println("IO exception");
                    }
                }
                ArrayList<Employee> employeeArray = new ArrayList<>();
                try {
                    ois = new ObjectInputStream(new FileInputStream(file));
                    employeeArray = (ArrayList<Employee>) ois.readObject();
                    Employee employee = new Employee();
                    employee.setId(tf[0].getText());
                    employee.setName(tf[1].getText());
                    employeeArray.add(employee);
                    oos = new ObjectOutputStream(new FileOutputStream(file));
                    oos.writeObject(employeeArray);
                    oos.reset();
                } catch (Exception e) {
                    System.out.println(e);
                } finally {
                    try {
                        ois.close();
                        oos.close();
                    } catch (IOException ex) {
                        System.out.println(ex);
                    }
                }
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Information Dialog");
                alert.setHeaderText(null);
                alert.setContentText("Successfully Sign Up");
                alert.showAndWait();
            }
        });
        HBox bntComfirm = new HBox();
        bntComfirm.setPrefSize(500, 30);
        bntComfirm.setPadding(new Insets(0, 0, 15, 200));
        bntComfirm.getChildren().add(btnJoin);
        //Vbox for contents
        VBox vBoxEmInfo = new VBox();
        vBoxEmInfo.setPadding(new Insets(5));
        vBoxEmInfo.setSpacing(5);
        vBoxEmInfo.getChildren().add(joinTitle);
        vBoxEmInfo.getChildren().addAll(Arrays.asList(hinfo));
        vBoxEmInfo.getChildren().addAll(bntComfirm);
        //Main Pane
        GridPane root = new GridPane();
        root.getChildren().add(vBoxEmInfo);
        //Create scene
        Scene scene = new Scene(root, 510, 450);
        primaryStage.setTitle("JoinForm");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static ObservableList<Employee> getEmployeeItems() {//table
        ObservableList<Employee> data = FXCollections.observableArrayList();
        data.addAll(new Employee("emp", "Jin"), new Employee("emp", "Jon"),
                new Employee("emp", "han"));
        return data;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
