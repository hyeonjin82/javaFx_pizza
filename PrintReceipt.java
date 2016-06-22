package finalproject;

import static finalproject.OrderPizza.customAddress;
import static finalproject.OrderPizza.customEmail;
import static finalproject.OrderPizza.customName;
import static finalproject.OrderPizza.customPhone;
import static finalproject.OrderPizza.printReceiptOrder;
import static finalproject.OrderPizza.printReceiptPrice;
import java.awt.Toolkit;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Optional;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 *
 * @author qqq4343
 */
public class PrintReceipt extends Application {

    //Variables in print receipt
    private String[] strTitle = {"      Order Number : ", "    Company Name : ", "Company Address : ",
        "   Company Phone : ", "   Employee Name : ", "   Customer Name : ",
        "   Customer Phone : ", "Customer Address : ", "    Customer Email : ", "           Order Time : ",
        "      Expected Time : "};
    private Label mainLabelRightUp, mainLabelRightDown, mainLabelLeft;
    private StringBuilder sbPrintReceiptRight = new StringBuilder();
    //A 3 digit unique order number.
    private String orderNumber = "100";
    //The pizza store's name, address and phone number
    private String pizzaStoreName = "Oh! My Pizza";
    private String pizzaStoreAddress = "1577 Fiarview Rd Mississauga, ON L5A4W8";
    private String pizzaStoreNumber = "(416) 997-2222";
    //The name of the employee who took the order.
    String empName = LoginProject.loginName;
    //The clients info
    String cusName = OrderPizza.customName;
    String cusPhone = OrderPizza.customPhone;
    String cusAddress = OrderPizza.customAddress;
    String cusEmail = OrderPizza.customEmail;
    //The time the order was placed - system time/ expected completion time ( 30 minutes later)
    DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    Calendar cal = Calendar.getInstance();
    String orderTime = dateFormat.format(cal.getTime());
    String expectedTime = null;
    //Contents for reciept
    private String[] strContent = {orderNumber, pizzaStoreName, pizzaStoreAddress,
        pizzaStoreNumber, empName, cusName, cusPhone, cusAddress,
        cusEmail, orderTime, expectedTime};

    final int screenw = Toolkit.getDefaultToolkit().getScreenSize().width - 400;
    final int screenh = Toolkit.getDefaultToolkit().getScreenSize().height - 70;

    @Override
    public void start(Stage primaryStage) {

        //Title for Customer Information
        Text cusTitle = new Text("Print Receipt");
        cusTitle.setFont(Font.font("Arial Black", 30));
        cusTitle.setFill(Color.web("#BBBBBB"));
        cusTitle.setId("title-text");
        //Set Order Number
        int orderNum = getOrderNum();//Get the order number
        strContent[0] = String.valueOf(orderNum);
        //Set expected completion time(30 minutes later.
        cal = Calendar.getInstance();
        cal.add(Calendar.MINUTE, 30);
        expectedTime = dateFormat.format(cal.getTime());
        strContent[10] = expectedTime;
        //Append to StringBuilder
        for (int i = 0; i < strTitle.length; i++) {
            if ( strContent[i] == null || strContent[i].length() < 1 || strContent[i] == "" ) {
                //Skip print
            } else {
//                if (strTitle[i].length() < 20) {
//                    System.out.println("ss" + strTitle[i].length());
//                    int gap = 20 - strTitle[i].length();
//                    System.out.println("gap" + gap);
//                    StringBuilder gapTitle = new StringBuilder();
//                    for (int j = 0; j < gap; j++) {
//                        gapTitle.append(" ");
//                    }
//                    strTitle[i] = gapTitle.toString().concat(strTitle[i]);
//                }
                sbPrintReceiptRight.append(strTitle[i])
                        .append(strContent[i])
                        .append("\n");
            }
        }
        //Title for Receipt Information
        Text receiptTitle = new Text("Receipt Info");
        receiptTitle.setId("little-title-text");
        //Set content to Right_Up Label
        mainLabelRightUp = new Label();//Right Center Label
        mainLabelRightUp.setPrefSize(screenh - 20, screenh - 100);
        mainLabelRightUp.setAlignment(Pos.TOP_LEFT);
        mainLabelRightUp.setPadding(new Insets(5));
        mainLabelRightUp.setStyle("-fx-background-color: rgb(128,179,179);"
                + " -fx-border-style: solid;"
                + " -fx-border-color: mediumvioletred;"
                + " -fx-border-radius: 10;"
                + " -fx-background-insets: 3;"
                + " -fx-border-insets: 3;");
        mainLabelRightUp.setText(sbPrintReceiptRight.toString());
        mainLabelRightUp.setTextFill(Color.NAVY);
        mainLabelRightUp.setFont(Font.font("Arial", FontWeight.BOLD, FontPosture.REGULAR, 12));
        //Vbox for receipt info
        VBox vreceiptInfo = new VBox();
        vreceiptInfo.getChildren().addAll(receiptTitle, mainLabelRightUp);
        //Title for Receipt Information
        Text totalPriceTitle = new Text("Total Price");
        totalPriceTitle.setId("little-title-text");
        //Set content to Right_Down Label
        mainLabelRightDown = new Label();//Right Center Label
        mainLabelRightDown.setPrefSize(screenh - 20, screenh - 100);
        mainLabelRightDown.setAlignment(Pos.TOP_LEFT);
        mainLabelRightDown.setPadding(new Insets(5));
        mainLabelRightDown.setStyle("-fx-background-color: rgb(128,179,179);"
                + " -fx-border-style: solid;"
                + " -fx-border-color: mediumvioletred;"
                + " -fx-border-radius: 10;"
                + " -fx-background-insets: 3;"
                + " -fx-border-insets: 3;");
        mainLabelRightDown.setText(printReceiptPrice.toString());
        mainLabelRightDown.setTextFill(Color.NAVY);
        mainLabelRightDown.setFont(Font.font("Arial", FontWeight.BOLD, FontPosture.REGULAR, 12));
        //Vbox for receipt info
        VBox vpriceInfo = new VBox();
        vpriceInfo.getChildren().addAll(totalPriceTitle, mainLabelRightDown);
        //VBox for Right content
        VBox vRightLabel = new VBox();
        vRightLabel.setSpacing(5);
        vRightLabel.getChildren().addAll(vreceiptInfo, vpriceInfo);
        //Title for Receipt Information
        Text pizzaInfoTitle = new Text("Pizza Info");
        pizzaInfoTitle.setId("little-title-text");
        //Left Center Label
        mainLabelLeft = new Label();
        mainLabelLeft.setPrefSize(screenh - 20, screenh - 100);
        mainLabelLeft.setAlignment(Pos.TOP_LEFT);
        mainLabelLeft.setPadding(new Insets(5));
        mainLabelLeft.setStyle("-fx-background-color: rgb(128,179,179);"
                + " -fx-border-style: solid;"
                + " -fx-border-color: mediumvioletred;"
                + " -fx-border-radius: 10;"
                + " -fx-background-insets: 3;"
                + " -fx-border-insets: 3;");
        mainLabelLeft.setText(printReceiptOrder.toString());
        mainLabelLeft.setTextFill(Color.NAVY);
        mainLabelLeft.setFont(Font.font("Arial", FontWeight.BOLD, FontPosture.REGULAR, 12));
        VBox vpizzaInfo = new VBox();
        vpizzaInfo.getChildren().addAll(pizzaInfoTitle, mainLabelLeft);
        //Hbox for Cetner Labels
        HBox hmainLabel = new HBox();
        hmainLabel.getChildren().addAll(vpizzaInfo, vRightLabel);
        //Button 
        Button btnSaveOrder = new Button("Save Order");
        btnSaveOrder.setPrefSize(150, 30);
        btnSaveOrder.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Start Save Order");
                //Define the file directory for the files
                File dir = new File("Receipts");
                //Define the file.
                File file = new File(dir.getName() + "\\ORDER" + orderNum + ".dat");
                //Open the file to write to
                FileWriter fw = null;
                BufferedWriter bw = null;
                try {
                    fw = new FileWriter(file.getAbsoluteFile());
                    bw = new BufferedWriter(fw);
                    //Write to the file.
                    sbPrintReceiptRight.append(printReceiptOrder)
                                      .append(printReceiptPrice);
                    bw.write(sbPrintReceiptRight.toString());
                    //Close the file
                    bw.close();
                } catch (IOException ex) {
                    System.out.println("IO Exception");
                }
                System.out.println("alert");
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Dialog");
                alert.setHeaderText("Your order file was saved successfully");
                alert.setContentText("Do you want to finish this application?");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    // User chose OK
                    primaryStage.close();
                } else {
                    // User chose CANCEL or closed the dialog
                }
            }
        });
        //VBox for contents
        VBox vBoxCusInfo = new VBox();
        vBoxCusInfo.setPadding(new Insets(5));
        vBoxCusInfo.setSpacing(5);
        vBoxCusInfo.getChildren().addAll(cusTitle, hmainLabel);
        //HBox for Button
        HBox bntSave = new HBox();
        bntSave.setPrefSize(screenw, 30);
        bntSave.setPadding(new Insets(0, 0, 15, screenw / 2.5));
        bntSave.getChildren().add(btnSaveOrder);
        //Center Pane
        BorderPane root = new BorderPane();
        root.setId("vsp");
        root.setCenter(vBoxCusInfo);
        root.setBottom(bntSave);
        //Create Scene
        Scene scene = new Scene(root, screenw, screenh);
        scene.getStylesheets().add(OrderPizza.class.getResource("orderPizza.css").toExternalForm());
        primaryStage.setTitle("Print Receipt");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public int getOrderNum() {
        System.out.println("Start order number");
        ObjectOutputStream oos = null;
        ObjectInputStream ois = null;
        int ordernum = 100;
        boolean isFound = false;

        //Create a custom directory for the files
        File myDir = new File("Receipts");
        if (!myDir.exists()) {
            myDir.mkdir();
        }
        while (!isFound) {
            //Define the file.
            File file = new File(myDir.getName() + "\\ORDER" + ordernum + ".dat");
            //Create the file if it doesn't exist
            if (file.exists()) {
                ordernum++;
            } else {
                isFound = true;
                try {
                    file.createNewFile();
                } catch (IOException ex) {
                    System.out.println("IO exception");
                }
            }
        }
        return ordernum;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
