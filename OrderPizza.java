package finalproject;

import java.awt.Toolkit;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.controlsfx.control.CheckListView;
import org.controlsfx.control.SegmentedButton;

/**
 *
 * @author qqq4343
 */
public class OrderPizza extends Application {

    //Variables in customer information
    public static String customName = null;
    public static String customPhone = null;
    public static String customAddress = null;
    public static String customEmail = null;

    private Label[] laTitle = new Label[5]; //Title Label for Customer Information
    private Label[] laWarning = new Label[5]; //Require Field for Customer Information
    private TextField[] tfCustomer = new TextField[5]; //TextField for Customer Information
    private HBox[] hinfo = new HBox[5]; //HBox for Customer Information
    private String[] str = {"First Name : ", "Last Name : ", "Phone Number : ", "Address : ", "Email : "};
    private String[] strWarning = {"* First Name : ", "* Last Name : ", "* Phone Number : ", "* Address : ", "*Email : "};
    private String[] strRequired = {"You need to fill the first name", "You need to fill the last name",
        "You need to fill the phone number", "You need to fill the address",
        "You need to fill the email"};
    //Variables in order pizza
    private ToggleButton deliveryPiz, pickUpPiz, plainPiz, wholeWheatPiz,
            stuffedCrustPiz, smallPiz, mediumPiz, largePiz,
            exLargePiz, wholePiz, rightPiz, leftPiz, extraPiz;
    private Label laEmpID, laEmpName, laEmpIdField, laEmpNameField, laSize,
            laCrust, laOption, laOrderType, descripTopping, descripSideOrder,
            descripDrinks, laTotalPrice, laOrderDetail, laSideOrder, laSideOrderNumber,
            laDrink, laDrinkNumber, laAddPizzaNumber;
    private TextArea txtOrderDetail;

    private StringBuilder toppingItems = new StringBuilder(" My Toppings Selected: \n ");
    private StringBuilder sideOrderItems = new StringBuilder(" My Side Order Selected: \n ");
    private StringBuilder drinkItems = new StringBuilder(" My Drink Selected: \n ");
    private StringBuilder addPizza = new StringBuilder();
    private StringBuilder addTotalPrice = new StringBuilder();
    public static StringBuilder printReceiptOrder = new StringBuilder();
    public static StringBuilder printReceiptPrice = new StringBuilder();

    private ObservableMap<String, Double> listSideOrder = null;
    private ObservableMap<String, Double> listDrink = null;

    private String confirmSize = "Medium Size($7.50)";
    private String confirmCrust = "Plain";

    DecimalFormat twoDicimalformat = new DecimalFormat("0.00");
    private final double basicToppingPrice = 1.00;
    private double pizzaPrice = 0.00;
    private double totalPizza = 0.00;
    private double sideOrderPrice = 0.00;
    private double drinkPrice = 0.00;
    private int pizzaCount = 1;

    final int screenw = Toolkit.getDefaultToolkit().getScreenSize().width - 20;
    final int screenh = Toolkit.getDefaultToolkit().getScreenSize().height - 70;

    @Override
    public void start(Stage primaryStage) {
        //Title for Customer Information
        Text cusTitle = new Text("Customer Information");
        cusTitle.setFont(Font.font("Arial Black", 30));
        cusTitle.setFill(Color.web("#BBBBBB"));
        cusTitle.setId("title-text");
        //Get Employee id and name 
        laEmpID = new Label("Employee ID - ");
        laEmpName = new Label("Employee Name - ");
        laEmpIdField = new Label(LoginProject.loginId);
        laEmpNameField = new Label(LoginProject.loginName);
        HBox hEmp = new HBox();
        hEmp.setPrefWidth(320);
        hEmp.setSpacing(5);
        hEmp.setAlignment(Pos.TOP_RIGHT);
        hEmp.getChildren().addAll(laEmpID, laEmpIdField, laEmpName, laEmpNameField);
        HBox hcusTitle = new HBox();
        hcusTitle.getChildren().addAll(cusTitle, hEmp);
        //Create EventHandler for Customer TextField
        TextFieldKeyHandler checkFieldByKey = new TextFieldKeyHandler();
        TextFieldMouseHandler checkFieldByMouse = new TextFieldMouseHandler();
        //Customer Information
        for (int i = 0; i < laTitle.length; i++) {
            laTitle[i] = new Label();
            laTitle[i].setText(str[i]);
            laTitle[i].setPrefSize(110, 10);
            laTitle[i].setFont(Font.font("Arial", FontWeight.BOLD, 12));
            laTitle[i].setAlignment(Pos.CENTER_RIGHT);
            tfCustomer[i] = new TextField();
            tfCustomer[i].setPrefColumnCount(25);
            tfCustomer[i].setAlignment(Pos.CENTER_LEFT);
            tfCustomer[i].setOnKeyPressed(checkFieldByKey);
            tfCustomer[i].setOnMouseClicked(checkFieldByMouse);
            laWarning[i] = new Label();
            laWarning[i].setText("");
            laWarning[i].setPrefSize(200, 10);
            laWarning[i].setStyle(
                    "-fx-font-size: 12px;"
                    + "-fx-font-weight: bold;"
                    + "-fx-text-fill: rgb(201, 39, 30)"
            );
            laWarning[i].setAlignment(Pos.CENTER_LEFT);
            hinfo[i] = new HBox();
            hinfo[i].setAlignment(Pos.CENTER_LEFT);
            hinfo[i].setSpacing(5);
            hinfo[i].setPadding(new Insets(1));
            hinfo[i].setPrefSize(500, 30);
            hinfo[i].getChildren().addAll(laTitle[i], tfCustomer[i], laWarning[i]);
        }
        //SegmentedButton Lable for order type
        laOrderType = new Label("Order Type : ");
        laOrderType.setPrefSize(110, 10);
        laOrderType.setFont(Font.font("Arial", FontWeight.BOLD, 12));
        laOrderType.setAlignment(Pos.CENTER_RIGHT);
        //Set Pick mode for require filed (label count - 1 for method)
        requireField(laTitle.length - 1);
        //Create segmenteButton for Pizza Size
        SegmenteHandler segHandler = new SegmenteHandler();
        deliveryPiz = new ToggleButton("Delivery Order");
        deliveryPiz.setOnAction(segHandler);
        pickUpPiz = new ToggleButton("Pick Up Order");
        pickUpPiz.setOnAction(segHandler);
        deliveryPiz.setSelected(true);
        deliveryPiz.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (deliveryPiz.isSelected() == false) {
                    deliveryPiz.setSelected(true);
                }
            }
        });
        pickUpPiz.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (pickUpPiz.isSelected() == false) {
                    pickUpPiz.setSelected(true);
                }
            }
        });
        SegmentedButton orderPizza = new SegmentedButton(deliveryPiz, pickUpPiz);
        orderPizza.getStyleClass().add(SegmentedButton.STYLE_CLASS_DARK);
        //HBox for radiobutton
        HBox hboxOrder = new HBox();
        hboxOrder.setAlignment(Pos.CENTER_LEFT);
        hboxOrder.setSpacing(5);
        hboxOrder.setPadding(new Insets(1));
        hboxOrder.getChildren().addAll(laOrderType, orderPizza);
        //VBox for customer info 
        VBox vBoxCusInfo = new VBox();
        vBoxCusInfo.setPadding(new Insets(5));
        vBoxCusInfo.setSpacing(5);
        vBoxCusInfo.getChildren().addAll(hcusTitle, hboxOrder);
        for (HBox h : hinfo) {
            vBoxCusInfo.getChildren().add(h);
        }
        //Order Pizza
        //Title for Customer Information
        Text orderTitle = new Text("Order Pizza");
        orderTitle.setId("title-text");
        //Choose the size
        laSize = new Label("Pizza Size : ");
        laSize.setPrefSize(110, 10);
        laSize.setFont(Font.font("Arial", FontWeight.BOLD, 12));
        laSize.setAlignment(Pos.CENTER_RIGHT);
        //Create segmenteButton for Pizza Size
        smallPiz = new ToggleButton("Small ($5.50)");
        smallPiz.setOnAction(segHandler);
        mediumPiz = new ToggleButton("Medium ($7.50)");
        mediumPiz.setOnAction(segHandler);
        largePiz = new ToggleButton("Large ($10.50)");
        largePiz.setOnAction(segHandler);
        exLargePiz = new ToggleButton("Extra Large ($12.50)");
        exLargePiz.setOnAction(segHandler);
        mediumPiz.setSelected(true);
        smallPiz.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (smallPiz.isSelected() == false) {
                    smallPiz.setSelected(true);
                }
            }
        });
        mediumPiz.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (mediumPiz.isSelected() == false) {
                    mediumPiz.setSelected(true);
                }
            }
        });
        largePiz.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (largePiz.isSelected() == false) {
                    largePiz.setSelected(true);
                }
            }
        });
        exLargePiz.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (exLargePiz.isSelected() == false) {
                    exLargePiz.setSelected(true);
                }
            }
        });
        SegmentedButton sizePizza = new SegmentedButton(smallPiz, mediumPiz, largePiz, exLargePiz);

        sizePizza.getStyleClass().add(SegmentedButton.STYLE_CLASS_DARK);
        //Hbox for the Size
        HBox hSize = new HBox();
        hSize.setPrefSize(500, 30);
        hSize.setSpacing(5);
        hSize.setPadding(new Insets(1));
        hSize.setAlignment(Pos.CENTER_LEFT);
        hSize.getChildren().addAll(laSize, sizePizza);
        //Choose the size
        laCrust = new Label("Pizza Crust : ");
        laCrust.setPrefSize(110, 10);
        laCrust.setFont(Font.font("Arial", FontWeight.BOLD, 12));
        laCrust.setAlignment(Pos.CENTER_RIGHT);
        //Create segmenteButton for Pizza Crust
        plainPiz = new ToggleButton("Plain Pizza");
        plainPiz.setOnAction(segHandler);
        wholeWheatPiz = new ToggleButton("Whole-Wheat Pizza");
        wholeWheatPiz.setOnAction(segHandler);
        stuffedCrustPiz = new ToggleButton("Stuffed Crust Pizza");
        stuffedCrustPiz.setOnAction(segHandler);
        plainPiz.setSelected(true);
        plainPiz.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (plainPiz.isSelected() == false) {
                    plainPiz.setSelected(true);
                }
            }
        });
        wholeWheatPiz.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (wholeWheatPiz.isSelected() == false) {
                    wholeWheatPiz.setSelected(true);
                }
            }
        });
        stuffedCrustPiz.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (stuffedCrustPiz.isSelected() == false) {
                    stuffedCrustPiz.setSelected(true);
                }
            }
        });
        SegmentedButton crustPizza = new SegmentedButton(plainPiz, wholeWheatPiz, stuffedCrustPiz);
        crustPizza.getStyleClass().add(SegmentedButton.STYLE_CLASS_DARK);
        //Hbox for the crust
        HBox hCrust = new HBox();
        hCrust.setPrefSize(500, 30);
        hCrust.setSpacing(5);
        hCrust.setPadding(new Insets(1));
        hCrust.setAlignment(Pos.CENTER_LEFT);
        hCrust.getChildren().addAll(laCrust, crustPizza);
        //VBox for Order form
        VBox vBoxOrder = new VBox();
        vBoxOrder.setPadding(new Insets(5));
        vBoxOrder.setSpacing(5);
        vBoxOrder.getChildren().addAll(orderTitle, hSize, hCrust);
        //Title for Topping
        Text topTitle = new Text("Choose Toppings");
        topTitle.setId("little-title-text");
        // Create the topping list to show in the CheckListView 
        String[] toppings = {"Tomatoes", "Onions", "Spinach", "Pineapple", "Olives", "Mushrooms",
            "Peppers", "Garlic", "Potato", "Anchovies", "Bacon", "Chicken", "Sausage"};
        final ObservableList<String> topping = FXCollections.observableArrayList();
        for (int i = 0; i < toppings.length; i++) {
            topping.add(toppings[i]);
        }
        // Create the CheckListView with the topping 
        final CheckListView<String> toppingListView = new CheckListView<>(topping);
        //Event handling with items
//        toppingListView.getCheckModel().getCheckedItems().addListener(new ListChangeListener<String>() {
//            @Override
//            public void onChanged(ListChangeListener.Change<? extends String> c) {
//                if (toppingListView.getCheckModel().getItemCount() > 0) {
//                    System.out.println(toppingListView.getCheckModel().getCheckedItems());
//                    ObservableList<String> selectedTopping = FXCollections.observableArrayList();
//                    selectedTopping = toppingListView.getCheckModel().getCheckedItems();
//                    System.out.println("list  -------------------------------------");
//                    for (String t : selectedTopping) {
//                        System.out.println("toping: " + t);
//                        toppingItems.append("+ ");
//                        toppingItems.append(t);
//                        if (smallPiz.isSelected()) {
//                            toppingItems.append("(+$1) ");
//                        } else if (mediumPiz.isSelected()) {
//                            toppingItems.append("(+$2) ");
//                        } else if (largePiz.isSelected()) {
//                            toppingItems.append("(+$3) ");
//                        } else {
//                            toppingItems.append("(+$4) ");
//                        }
//                    }
//                }
//            }
//        });
        //Topping Option
        laOption = new Label("Options :  ");
        laOption.setPadding(new Insets(0, 0, 0, 5));
        laOption.setFont(Font.font("Arial", FontWeight.BOLD, 12));
        laOption.setAlignment(Pos.CENTER_LEFT);
        //Create segmenteButton for topping option
        wholePiz = new ToggleButton("Whole Topping");
        rightPiz = new ToggleButton("Right-Half");
        leftPiz = new ToggleButton("Left-Half");
        extraPiz = new ToggleButton("Extra Topping");
        wholePiz.setSelected(true);
        wholePiz.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (wholePiz.isSelected() == false) {
                    wholePiz.setSelected(true);
                }
            }
        });
        rightPiz.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (rightPiz.isSelected() == false) {
                    rightPiz.setSelected(true);
                }
            }
        });
        leftPiz.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (leftPiz.isSelected() == false) {
                    leftPiz.setSelected(true);
                }
            }
        });
        extraPiz.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (extraPiz.isSelected() == false) {
                    extraPiz.setSelected(true);
                }
            }
        });
        SegmentedButton optionPizza = new SegmentedButton(wholePiz, rightPiz, leftPiz, extraPiz);
        optionPizza.getStyleClass().add(SegmentedButton.STYLE_CLASS_DARK);
        //HBox
        HBox hboxToppingOption = new HBox();
        hboxToppingOption.getChildren().addAll(laOption, optionPizza);
        //Button for adding topping
        Button bnAddTopping = new Button("Add Toppings");
        bnAddTopping.setMaxWidth(Double.MAX_VALUE);
        bnAddTopping.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Add Topping");
                ObservableList<String> selectedTopping = FXCollections.observableArrayList();
                selectedTopping = toppingListView.getCheckModel().getCheckedItems();
                if (selectedTopping.size() < 1) {
                    Alert alert = new Alert(AlertType.WARNING);
                    alert.setTitle("Warning Dialog");
                    alert.setHeaderText("Look, a Warning Dialog");
                    alert.setContentText("must select at least one topping!");
                    alert.showAndWait();
                } else {
                    for (String t : selectedTopping) {
                        toppingItems.append("+").append(t);
                    }
                    String toppingPrice;
                    String eachPrice;
                    if (wholePiz.isSelected()) {
                        //ADD whole topping price to pizza price
                        toppingPrice = twoDicimalformat.format(toppingCal(1, selectedTopping.size()));
                        eachPrice = twoDicimalformat.format(Double.parseDouble(toppingPrice) / selectedTopping.size());
                        toppingItems.append(" - Whole ($")
                                .append(eachPrice)
                                .append(") * ")
                                .append(selectedTopping.size())
                                .append("= $").append(toppingPrice).append("\n ");
                    } else if (rightPiz.isSelected()) {
                        //ADD half topping price to pizza price
                        toppingPrice = twoDicimalformat.format(toppingCal(2, selectedTopping.size()));
                        eachPrice = twoDicimalformat.format(Double.parseDouble(toppingPrice) / selectedTopping.size());
                        toppingItems.append(" - Right_Half ($")
                                .append(eachPrice)
                                .append(") * ")
                                .append(selectedTopping.size())
                                .append("= $").append(toppingPrice).append("\n ");
                    } else if (leftPiz.isSelected()) {
                        //ADD half topping price to pizza price
                        toppingPrice = twoDicimalformat.format(toppingCal(2, selectedTopping.size()));
                        eachPrice = twoDicimalformat.format(Double.parseDouble(toppingPrice) / selectedTopping.size());
                        toppingItems.append(" - Left_Half ($")
                                .append(eachPrice)
                                .append(") * ")
                                .append(selectedTopping.size())
                                .append("= $").append(toppingPrice).append("\n ");
                    } else if (extraPiz.isSelected()) {
                        //ADD extra topping price to pizza price
                        toppingPrice = twoDicimalformat.format(toppingCal(3, selectedTopping.size()));
                        eachPrice = twoDicimalformat.format(Double.parseDouble(toppingPrice) / selectedTopping.size());
                        toppingItems.append(" - Extra ($")
                                .append(eachPrice)
                                .append(") * ")
                                .append(selectedTopping.size())
                                .append("= $").append(toppingPrice).append("\n ");
                    }
                    descripTopping.setText(toppingItems.toString());
                    toppingListView.getCheckModel().clearChecks();
                }
            }
        });
        //Label for description of Topping Selected
        descripTopping = new Label(" My Toppings Selected: \n ");
        descripTopping.setPrefSize(500, 270);
        descripTopping.setAlignment(Pos.TOP_LEFT);
        descripTopping.setWrapText(true);
        descripTopping.setOpacity(0.8);
        descripTopping.setStyle("-fx-background-color: rgb(128,179,179);"
                + " -fx-border-style: solid;"
                + " -fx-border-color: mediumvioletred;"
                + " -fx-border-radius: 10;"
                + " -fx-background-insets: 3;"
                + " -fx-border-insets: 3;");
        //Label for pizza add
        laAddPizzaNumber = new Label("Count : ");
        laAddPizzaNumber.setAlignment(Pos.BOTTOM_RIGHT);
        laAddPizzaNumber.setPrefSize(50, 20);
        laAddPizzaNumber.setPadding(new Insets(0, 0, 0, 5));
        //Count pizza for add
        ObservableList<Integer> numPizza = FXCollections.observableArrayList();
        for (int i = 1; i <= 7; i++) {
            numPizza.add(i);
        }
        ChoiceBox<Integer> pizzaNum = new ChoiceBox<>(numPizza);
        pizzaNum.setValue(1);
        //Button for adding pizza
        Button bnAddPizza = new Button("Order Pizza");
        bnAddPizza.setPrefWidth(350);
        bnAddPizza.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Order Pizza");
                if (descripTopping.getText().length() < 26 || !toppingListView.getCheckModel().isEmpty()) {
                    Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Information Dialog");
                    alert.setHeaderText(null);
                    alert.setContentText(" Please add toppings!!! ");
                    alert.showAndWait();
                } else {
                    //Put the all info to order detail
                    addPizza.setLength(0);
                    addPizza.append(txtOrderDetail.getText())
                            .append("Customer Request Size -")
                            .append(confirmSize)
                            .append("\n Request Crust - ")
                            .append(confirmCrust)
                            .append("\n")
                            .append(descripTopping.getText())
                            .append("Number of Request Pizza - ")
                            .append(pizzaNum.getValue())
                            .append("\n----------------------------------------"
                                    + "-----------------------------\n");
                    txtOrderDetail.setText(addPizza.toString());
                    if (confirmSize.equals("Small Size($5.50)")) {
                        pizzaPrice += Double.parseDouble(twoDicimalformat.format(5.50));
                    } else if (confirmSize.equals("Medium Size($7.50)")) {
                        pizzaPrice += Double.parseDouble(twoDicimalformat.format(7.50));
                    } else if (confirmSize.equals("Large Size($10.50)")) {
                        pizzaPrice += Double.parseDouble(twoDicimalformat.format(10.50));
                    } else if (confirmSize.equals("Extra Large Size($12.50)")) {
                        pizzaPrice += Double.parseDouble(twoDicimalformat.format(12.50));
                    }
                    pizzaCount = pizzaNum.getValue();
                    calulateTotalPrice();
                    //clear all textFiel and label
                    toppingItems.setLength(0);
                    toppingItems.append(" My Toppings Selected: \n ");
                    descripTopping.setText(" My Toppings Selected: \n ");
                    pizzaNum.setValue(1);
                    toppingListView.getCheckModel().clearChecks();
                }
            }
        });
        HBox haddPizza = new HBox();
        haddPizza.setSpacing(5);
        haddPizza.getChildren().addAll(laAddPizzaNumber, pizzaNum, bnAddPizza);
        //VBox for Pizza Topping
        VBox vBoxToppingOption = new VBox();
        vBoxToppingOption.setSpacing(5);
        vBoxToppingOption.setPadding(new Insets(0, 0, 0, 5));
        vBoxToppingOption.getChildren().addAll(hboxToppingOption, bnAddTopping,
                descripTopping, haddPizza);
        //Create Splitpane for bottom Hbox
        SplitPane hspTopping = new SplitPane();
        hspTopping.setId("vsp");
        hspTopping.getItems().addAll(toppingListView, vBoxToppingOption);
        hspTopping.setDividerPositions(0.3f, 0.9f);
        //VBox for left side
        VBox vBoxLeftSide = new VBox();
        vBoxLeftSide.setPadding(new Insets(5));
        vBoxLeftSide.setSpacing(5);
        vBoxLeftSide.setPrefWidth(screenw / 2);
        vBoxLeftSide.getChildren().addAll(vBoxCusInfo, new Separator(), vBoxOrder, topTitle, hspTopping);
        //Title for Side Orders
        Text sideOrderTitle = new Text("Choose Side Orders");
        sideOrderTitle.setFont(Font.font("Arial Black", 18));
        sideOrderTitle.setFill(Color.web("#BBBBBB"));
        sideOrderTitle.setId("little-title-text");
        //Label for description of Topping Selected
        descripSideOrder = new Label(" My Side Order Selected: ");
        descripSideOrder.setPrefSize(340, 170);
        descripSideOrder.setAlignment(Pos.TOP_LEFT);
        descripSideOrder.setOpacity(0.8);
        descripSideOrder.setStyle("-fx-background-color: rgb(128,179,179);"
                + " -fx-border-style: solid;"
                + " -fx-border-color: mediumvioletred;"
                + " -fx-border-radius: 10;"
                + " -fx-background-insets: 3;"
                + " -fx-border-insets: 3;");
        descripSideOrder.setFont(Font.font("Arial", FontWeight.BOLD, FontPosture.REGULAR, 12));
        HashMap<String, Double> sideOrderMap = new HashMap();
        sideOrderMap.put("Wings", 8.50);
        sideOrderMap.put("Pasta", 10.50);
        sideOrderMap.put("Salsds", 7.50);
        sideOrderMap.put("Sandwiches", 6.50);
        sideOrderMap.put("bread basket", 4.50);
        sideOrderMap.put("Rings", 5.50);
        sideOrderMap.put("Sweet Treats", 3.50);
        //Assign map to list
        listSideOrder = FXCollections.observableMap(sideOrderMap);
        ComboBox<String> sideOrderCombo = new ComboBox<>();
        //Add value to ComboBox
        sideOrderCombo.getItems().addAll(listSideOrder.keySet());
        sideOrderCombo.setPromptText("Make a choice...");
        ObservableList<Integer> numSide = FXCollections.observableArrayList();
        for (int i = 1; i <= 20; i++) {
            numSide.add(i);
        }
        ChoiceBox<Integer> sideOrderNum = new ChoiceBox<>(numSide);
        sideOrderNum.setValue(1);
        Button sideButton = new Button("Add Side Order");
        sideButton.setPrefWidth(300);
        sideButton.setOnAction((ActionEvent event) -> {
            if (sideOrderCombo.getValue() == null) {
                Alert alert = new Alert(AlertType.WARNING);
                alert.setTitle("Warning Dialog");
                alert.setHeaderText("Look, a Warning Dialog");
                alert.setContentText("must select at least side order!");
                alert.showAndWait();
            } else {
                Set<Entry<String, Double>> orderSet = listSideOrder.entrySet();
                Iterator<Entry<String, Double>> it = orderSet.iterator();
                String sidePrice = null;
                sideOrderItems.append(sideOrderCombo.getValue());
                sideOrderItems.append("($");
                while (it.hasNext()) {
                    Entry<String, Double> entry = it.next();
                    if (entry.getKey() == sideOrderCombo.getValue()) {
                        sideOrderItems.append(entry.getValue());
                        sidePrice = twoDicimalformat.format(entry.getValue() * sideOrderNum.getValue());
                        sideOrderPrice += Double.parseDouble(sidePrice);
                    }
                }
                sideOrderItems.append(") * ")
                        .append(sideOrderNum.getValue())
                        .append("= $")
                        .append(sidePrice).append("\n ");
                descripSideOrder.setText(sideOrderItems.toString());

            }
        });
        Button orderSideButton = new Button("Order Side Foods");
        orderSideButton.setPrefWidth(300);
        orderSideButton.setOnAction((ActionEvent event) -> {
            System.out.println("Order Side Foond");
            if (descripSideOrder.getText().length() < 26) {
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Information Dialog");
                alert.setHeaderText(null);
                alert.setContentText(" Please add side order!!! ");
                alert.showAndWait();
            } else {
                //Put the all info to order detail
                sideOrderItems.setLength(0);
                sideOrderItems.append(txtOrderDetail.getText())
                        .append(descripSideOrder.getText().substring(0, descripSideOrder.getText().length() - 1))
                        .append("----------------------------------------"
                                + "-----------------------------\n");
                txtOrderDetail.setText(sideOrderItems.toString());
                calulateTotalPrice();
                //clear all textFiel and label
                sideOrderItems.setLength(0);
                sideOrderItems.append(" My Side Order Selected: \n ");
                descripSideOrder.setText(" My Side Order Selected: ");
                sideOrderNum.setValue(1);
            }
        });
        HBox hsideButtons = new HBox();
        hsideButtons.setSpacing(3);
        hsideButtons.setPadding(new Insets(0, 3, 0, 3));
        hsideButtons.getChildren().addAll(sideButton, orderSideButton);
        //Label for side order
        laSideOrder = new Label("Side Order : ");
        laSideOrder.setAlignment(Pos.BOTTOM_RIGHT);
        laSideOrder.setPrefHeight(20);
        laSideOrder.setPadding(new Insets(0, 0, 0, 5));
        laSideOrderNumber = new Label("Count : ");
        laSideOrderNumber.setAlignment(Pos.BOTTOM_RIGHT);
        laSideOrderNumber.setPrefHeight(20);
        laSideOrderNumber.setPadding(new Insets(0, 0, 0, 5));
        //HBox for Side Order
        HBox hboxSide = new HBox();
        hboxSide.setSpacing(5);
        hboxSide.getChildren().addAll(laSideOrder, sideOrderCombo, laSideOrderNumber, sideOrderNum);
        //Vbox for side order Right_Top_Left
        VBox rightTopLeft = new VBox();
        rightTopLeft.setSpacing(5);
        rightTopLeft.getChildren().addAll(sideOrderTitle, hboxSide, hsideButtons,
                new Separator(), descripSideOrder);
        //Title for Drinks
        Text drinksTitle = new Text("Choose Drinks");
        drinksTitle.setFont(Font.font("Arial Black", 18));
        drinksTitle.setFill(Color.web("#BBBBBB"));
        drinksTitle.setId("little-title-text");
        //Combo box for drink
        HashMap<String, Double> drinkMap = new HashMap();
        drinkMap.put("Coke(Can)", 2.50);
        drinkMap.put("Coke(Bottle)", 3.50);
        drinkMap.put("Diet Coke(Can)", 2.50);
        drinkMap.put("Sprite(Bottle)", 2.50);
        drinkMap.put("Fanta(Can)", 2.50);
        drinkMap.put("Fanta(Bottle)", 3.50);
        drinkMap.put("Appletizer(Bottle)", 4.50);
        drinkMap.put("Orange Juice(Bottle)", 4.50);
        drinkMap.put("SRed Bull(Can)", 3.50);
        drinkMap.put("Sparking Water(Bottle)", 5.50);
        listDrink = FXCollections.observableMap(drinkMap);
        ComboBox<String> comboDrinks = new ComboBox<>();
        comboDrinks.getItems().addAll(listDrink.keySet());
        comboDrinks.setPromptText("Make a choice...");
        ObservableList<Integer> numDrink = FXCollections.observableArrayList();
        for (int i = 1; i <= 20; i++) {
            numDrink.add(i);
        }
        ChoiceBox<Integer> drinksNum = new ChoiceBox<>(numSide);
        drinksNum.setValue(1);
        Button drinkButton = new Button("Add Drink");
        drinkButton.setPrefWidth(300);
        drinkButton.setOnAction((ActionEvent event) -> {
            if (comboDrinks.getValue() == null) {
                Alert alert = new Alert(AlertType.WARNING);
                alert.setTitle("Warning Dialog");
                alert.setHeaderText("Look, a Warning Dialog");
                alert.setContentText("must select at least drink!");
                alert.showAndWait();
            } else {
                Set<Entry<String, Double>> drinkSet = listDrink.entrySet();
                Iterator<Entry<String, Double>> it = drinkSet.iterator();
                drinkItems.append(comboDrinks.getValue());
                drinkItems.append("($");
                while (it.hasNext()) {
                    Entry<String, Double> entry = it.next();
                    if (entry.getKey() == comboDrinks.getValue()) {
                        drinkItems.append(entry.getValue());
                        drinkPrice += Double.parseDouble(twoDicimalformat.format(entry.getValue() * drinksNum.getValue()));
                    }
                }
                drinkItems.append(") * ");
                drinkItems.append(drinksNum.getValue());
                drinkItems.append("\n ");
                descripDrinks.setText(drinkItems.toString());
            }
        });
        Button orderDrinkButton = new Button("Order Drink");
        orderDrinkButton.setPrefWidth(300);
        orderDrinkButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                System.out.println("Order Drink");
                if (descripDrinks.getText().length() < 21) {
                    Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Information Dialog");
                    alert.setHeaderText(null);
                    alert.setContentText(" Please add drink!!! ");
                    alert.showAndWait();
                } else {
                    //Put the all info to order detail
                    drinkItems.setLength(0);
                    drinkItems.append(txtOrderDetail.getText())
                            .append(descripDrinks.getText().substring(0, descripDrinks.getText().length() - 1))
                            .append("----------------------------------------"
                                    + "-----------------------------\n");
                    txtOrderDetail.setText(drinkItems.toString());
                    calulateTotalPrice();
                    //clear all textFiel and label
                    drinkItems.setLength(0);
                    drinkItems.append(" My Drink Selected: \n ");
                    descripDrinks.setText(" My Drink Selected: ");
                    drinksNum.setValue(1);
                }
            }
        });
        HBox hdrinkButtons = new HBox();
        hdrinkButtons.setSpacing(3);
        hdrinkButtons.setPadding(new Insets(0, 3, 0, 3));
        hdrinkButtons.getChildren().addAll(drinkButton, orderDrinkButton);
        //Label for side order
        laDrink = new Label("Drinks : ");
        laDrink.setAlignment(Pos.BOTTOM_RIGHT);
        laDrink.setPrefHeight(20);
        laDrink.setPadding(new Insets(0, 0, 0, 5));
        laDrinkNumber = new Label("Count : ");
        laDrinkNumber.setAlignment(Pos.BOTTOM_RIGHT);
        laDrinkNumber.setPrefHeight(20);
        laDrinkNumber.setPadding(new Insets(0, 0, 0, 5));
        //HBox
        HBox hboxDrinks = new HBox();
        hboxDrinks.setSpacing(5);
        hboxDrinks.getChildren().addAll(laDrink, comboDrinks, laDrinkNumber, drinksNum);
        //Label for description of Topping Selected
        descripDrinks = new Label(" My Drink Selected: ");
        descripDrinks.setPrefSize(340, 170);
        descripDrinks.setAlignment(Pos.TOP_LEFT);
        descripDrinks.setOpacity(0.8);
        descripDrinks.setStyle("-fx-background-color: rgb(128,179,179);"
                + " -fx-border-style: solid;"
                + " -fx-border-color: mediumvioletred;"
                + " -fx-border-radius: 10;"
                + " -fx-background-insets: 3;"
                + " -fx-border-insets: 3;");
        descripDrinks.setFont(Font.font("Arial", FontWeight.BOLD, FontPosture.REGULAR, 12));
        //Vbox for drinks Right_Top_Right
        VBox RightTopRight = new VBox();
        RightTopRight.setSpacing(5);
        RightTopRight.getChildren().addAll(drinksTitle, hboxDrinks, hdrinkButtons,
                new Separator(), descripDrinks);
        //Split the Right_Top    
        SplitPane splitRightTop = new SplitPane();
        splitRightTop.setId("vsp");
        splitRightTop.getItems().addAll(rightTopLeft, RightTopRight);
        //Title for total price
        Text calTitle = new Text("Total Price");
        calTitle.setFont(Font.font("Arial Black", 18));
        calTitle.setFill(Color.web("#BBBBBB"));
        calTitle.setId("little-title-text");
        //Label for result
        laTotalPrice = new Label();
        laTotalPrice.setFont(Font.font("Arial", FontWeight.BOLD, FontPosture.REGULAR, 14));
        laTotalPrice.setAlignment(Pos.TOP_LEFT);
        laTotalPrice.setWrapText(true);
        laTotalPrice.setPrefSize(550, 700);
        laTotalPrice.setOpacity(0.8);
        laTotalPrice.setStyle("-fx-background-color: rgb(128,179,179);"
                + " -fx-border-style: solid;"
                + " -fx-border-color: mediumvioletred;"
                + " -fx-border-radius: 10;"
                + " -fx-background-insets: 3;"
                + " -fx-border-insets: 3;");
        laTotalPrice.setTextFill(Color.NAVY);
        laTotalPrice.setFont(Font.font("Arial", FontWeight.BOLD, FontPosture.REGULAR, 12));
        //Title for total price
        Text datailTitle = new Text("Order Details");
        datailTitle.setFont(Font.font("Arial Black", 18));
        datailTitle.setFill(Color.web("#BBBBBB"));
        datailTitle.setId("little-title-text");
        //Label for result
        laOrderDetail = new Label();
        laOrderDetail.setFont(Font.font("Arial", FontWeight.BOLD, FontPosture.REGULAR, 14));
        laOrderDetail.setAlignment(Pos.TOP_LEFT);
        laOrderDetail.setPrefSize(550, 700);
        laOrderDetail.setWrapText(true);
        laOrderDetail.setOpacity(0.8);
        laOrderDetail.setId("detail-label");
        //TextArea for order details
        txtOrderDetail = new TextArea();
        txtOrderDetail.setEditable(false);
        txtOrderDetail.setPrefSize(650, 700);
        txtOrderDetail.setWrapText(true);
        txtOrderDetail.setStyle("-fx-background-color: wheat;"
                + " -fx-border-style: solid;"
                + " -fx-font-size: 12px;"
                + " -fx-font-weight: bold;"
                + " -fx-text-fill: #483d8b;"
                + " -fx-border-color: mediumvioletred;"
                + " -fx-border-radius: 10;"
                + " -fx-background-insets: 3;"
                + " -fx-border-insets: 3;");
        ScrollPane scrrderDetail = new ScrollPane(txtOrderDetail);
        scrrderDetail.setMaxHeight(Double.MAX_VALUE);
        scrrderDetail.setMaxWidth(Double.MAX_VALUE);
        scrrderDetail.setFitToWidth(true);
        scrrderDetail.setFitToHeight(true);
        //Vbox order detail
        VBox vBoxOrderDetail = new VBox();
        vBoxOrderDetail.getChildren().addAll(datailTitle, scrrderDetail);
        //Vbox total price
        VBox vBoxTotalPrice = new VBox();
        vBoxTotalPrice.getChildren().addAll(calTitle, laTotalPrice);
        //Hbox for order detail, total price
        HBox hBoxDetail = new HBox();
        hBoxDetail.setPadding(new Insets(5));
        hBoxDetail.setSpacing(10);
        hBoxDetail.getChildren().addAll(vBoxOrderDetail, vBoxTotalPrice);
        //Buttons for print
        Button bnPrintReceipt = new Button("Print Receipt");
        bnPrintReceipt.setMaxWidth(Double.MAX_VALUE);
        bnPrintReceipt.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Print Receipt");
                int isValidCount = 0;
                for (int i = 0; i < laWarning.length; i++) {
                    if (!laWarning[i].getText().equals("")) {
                        isValidCount++;
                    }
                }
                if (isValidCount > 0) {
                    Alert alert = new Alert(AlertType.WARNING);
                    alert.setTitle("Warning Dialog");
                    alert.setHeaderText(null);
                    alert.setContentText("Please enter the required fields");
                    alert.showAndWait();
                } else {
                    Alert alert = new Alert(AlertType.CONFIRMATION);
                    alert.setTitle("Confirmation Dialog");
                    alert.setHeaderText("Look, a Confirmation Dialog");
                    alert.setContentText("Do you want to complete this order and print it?");
                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.get() == ButtonType.OK) {
                    // User chose OK
                        //Get info customer
                        customName = tfCustomer[0].getText().toUpperCase()
                                + " " + tfCustomer[1].getText().toUpperCase();
                        customPhone = tfCustomer[2].getText();
                        customAddress = tfCustomer[3].getText().toUpperCase();
                        customEmail = tfCustomer[4].getText();
                        //Get orderlist and price
                        printReceiptOrder.append(txtOrderDetail.getText());
                        printReceiptPrice.append(laTotalPrice.getText());
                        //Open print 
                        PrintReceipt Receipt = new PrintReceipt();
                        Stage printstage = new Stage();
                        Receipt.start(printstage);
                    } else {
                        // User chose CANCEL or closed the dialog
                    }
                }
            }
        });
        VBox vBoxRightBottom = new VBox();
        vBoxRightBottom.getChildren().addAll(hBoxDetail, bnPrintReceipt);
        //Split right side 
        SplitPane splitRightSide = new SplitPane();
        splitRightSide.setOrientation(Orientation.VERTICAL);
        splitRightSide.setId("vsp");
        splitRightSide.getItems().addAll(splitRightTop, vBoxRightBottom);
        splitRightSide.setDividerPositions(0.4f, 0.9f);
        //Split the window of order form     
        SplitPane splitOrderForm = new SplitPane();
        splitOrderForm.setId("vsp");
        splitOrderForm.getItems().addAll(vBoxLeftSide, splitRightSide);
        //Root Pane
        GridPane root = new GridPane();
        root.getChildren().addAll(splitOrderForm);
        //Scene
        Scene scene = new Scene(root, screenw, screenh);
        scene.getStylesheets().add(OrderPizza.class.getResource("orderPizza.css").toExternalForm());
        primaryStage.setTitle("Order Pizza");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void requireField(int length) {
        for (int i = 0; i < length; i++) {
            if ("".equals(tfCustomer[i].getText())) {
                laTitle[i].setText(strWarning[i]);
                laWarning[i].setText(strRequired[i]);
                tfCustomer[i].setStyle(" -fx-border-color: rgb(201, 39, 30);");
            } else {
                laTitle[i].setText(str[i]);
                laWarning[i].setText("");
                tfCustomer[i].setStyle(" -fx-border-color: rgb(255, 255, 255);");
            }
        }
    }

    public void calulateTotalPrice() {
        System.out.println("Start calulate");
        System.out.println("pizzaPrice : " + pizzaPrice);
        totalPizza += pizzaPrice * pizzaCount;
        Double plusPrice = totalPizza + sideOrderPrice + drinkPrice;
        String subTotalPrice = twoDicimalformat.format(plusPrice);
        String taxPrice = twoDicimalformat.format(plusPrice * 0.13);
        String totalPrice = twoDicimalformat.format(plusPrice + (plusPrice * 0.13));
        //set 0 for next pizza order
        pizzaPrice = 0;
        addTotalPrice.setLength(0);
        addTotalPrice.append(" Pizza Cost - $")
                .append(twoDicimalformat.format(totalPizza))
                .append("\n Side Order Cost - $")
                .append(twoDicimalformat.format(sideOrderPrice))
                .append("\n Drinks Cost - $")
                .append(twoDicimalformat.format(drinkPrice))
                .append("\n-----------------------------------------------------")
                .append("\n Subtotal - $")
                .append(subTotalPrice)
                .append("\n GST/HST  - $")
                .append(taxPrice)
                .append("\n Total - $")
                .append(totalPrice);
        laTotalPrice.setText(addTotalPrice.toString());
    }

    public double toppingCal(int option, int toppingCount) {
        System.out.println("Start toppingCal");
        double tempPrice;
        if (confirmSize.equals("Small Size($5.50)")) {
            if (option == 1) {
                //Whole basictopping
                tempPrice = Double.parseDouble(twoDicimalformat.format(basicToppingPrice * toppingCount));
                pizzaPrice += tempPrice;
                return tempPrice;
            } else if (option == 2) {
                //Half (basictopping * 0.5)
                tempPrice = Double.parseDouble(twoDicimalformat.format(basicToppingPrice * toppingCount * 0.5));
                pizzaPrice += tempPrice;
                return tempPrice;
            } else if (option == 3) {
                //Extra (basictopping * 2)
                tempPrice = Double.parseDouble(twoDicimalformat.format(basicToppingPrice * toppingCount * 2));
                pizzaPrice += tempPrice;
                return tempPrice;
            }
        } else if (confirmSize.equals("Medium Size($7.50)")) {
            double mediumTopping = basicToppingPrice + 1;
            if (option == 1) {
                //Whole mediumtopping
                tempPrice = Double.parseDouble(twoDicimalformat.format(mediumTopping * toppingCount));
                pizzaPrice += tempPrice;
                return tempPrice;
            } else if (option == 2) {
                //Half (mediumtopping * 0.5)
                tempPrice = Double.parseDouble(twoDicimalformat.format(mediumTopping * toppingCount * 0.5));
                pizzaPrice += tempPrice;
                return tempPrice;
            } else if (option == 3) {
                //Extra (mediumtopping * 2)
                tempPrice = Double.parseDouble(twoDicimalformat.format(mediumTopping * toppingCount * 2));
                pizzaPrice += tempPrice;
                return tempPrice;
            }
        } else if (confirmSize.equals("Large Size($10.50)")) {
            double largeTopping = basicToppingPrice + 2;
            if (option == 1) {
                //whole basictopping
                tempPrice = Double.parseDouble(twoDicimalformat.format(largeTopping * toppingCount));
                pizzaPrice += tempPrice;
                return tempPrice;
            } else if (option == 2) {
                //half (basictopping * 0.5)
                tempPrice = Double.parseDouble(twoDicimalformat.format(largeTopping * toppingCount * 0.5));
                pizzaPrice += tempPrice;
                return tempPrice;
            } else if (option == 3) {
                //Extra (basictopping * 2)
                tempPrice = Double.parseDouble(twoDicimalformat.format(largeTopping * toppingCount * 2));
                pizzaPrice += tempPrice;
                return tempPrice;
            }
        } else if (confirmSize.equals("Extra Large Size($12.50)")) {
            double exLargeTopping = basicToppingPrice + 3;
            if (option == 1) {
                //whole basictopping
                tempPrice = Double.parseDouble(twoDicimalformat.format(exLargeTopping * toppingCount));
                pizzaPrice += tempPrice;
                return tempPrice;
            } else if (option == 2) {
                //half (basictopping * 0.5)
                tempPrice = Double.parseDouble(twoDicimalformat.format(exLargeTopping * toppingCount * 0.5));
                pizzaPrice += tempPrice;
                return tempPrice;
            } else if (option == 3) {
                //Extra (basictopping * 2)
                tempPrice = Double.parseDouble(twoDicimalformat.format(exLargeTopping * toppingCount * 2));
                pizzaPrice += tempPrice;
                return tempPrice;
            }
        }
        return -1.0;
    }

    public class SegmenteHandler implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {
            if (event.getSource() == deliveryPiz) {
                //Require field - Full name, Phone num, Address
                requireField(4);
            } else if (event.getSource() == pickUpPiz) {
                //Require field - Full name, Phone num
                laTitle[3].setText(str[3]);
                laWarning[3].setText("");
                tfCustomer[3].setStyle(" -fx-border-color: rgb(255, 255, 255);");
                requireField(3);
            } else if (event.getSource() == smallPiz) {
                confirmSize = "Small Size($5.50)";
            } else if (event.getSource() == mediumPiz) {
                confirmSize = "Medium Size($7.50)";
            } else if (event.getSource() == largePiz) {
                confirmSize = "Large Size($10.50)";
            } else if (event.getSource() == exLargePiz) {
                confirmSize = "Extra Large Size($12.50)";
            } else if (event.getSource() == plainPiz) {
                confirmCrust = "Plain";
            } else if (event.getSource() == wholeWheatPiz) {
                confirmCrust = "Whole_Wheat";
            } else if (event.getSource() == stuffedCrustPiz) {
                confirmCrust = "Stuffed Crust";
            }
        }
    }

    public class TextFieldKeyHandler implements EventHandler<KeyEvent> {

        @Override
        public void handle(KeyEvent event) {
            if (event.getCode() == KeyCode.TAB || event.getCode() == KeyCode.ENTER) {
//                for (int i = 0; i < tfCustomer.length - 1; i++) {
//                    if (event.getSource() == tfCustomer[i]) {
//                        if ("".equals(tfCustomer[i].getText())) {
//                            laTitle[i].setText(strWarning[i]);
//                            laWarning[i].setText(strRequired[i]);
//                            tfCustomer[i].setStyle(" -fx-border-color: rgb(201, 39, 30);");
//                        } else {
//                            laTitle[i].setText(str[i]);
//                            laWarning[i].setText("");
//                            tfCustomer[i].setStyle(" -fx-border-color: rgb(255, 255, 255);");
//                        }
//                    }
//                }
                if (deliveryPiz.isSelected()) {
                    requireField(4);
                } else {
                    requireField(3);
                }
            }

        }
    }

    public class TextFieldMouseHandler implements EventHandler<MouseEvent> {

        @Override
        public void handle(MouseEvent event) {
            if (deliveryPiz.isSelected()) {
                requireField(4);
            } else {
                requireField(3);
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
