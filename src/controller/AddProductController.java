package controller;

/**
  @Author Melissa Aybar
  AddProductController class.
  This class adds products to the inventory.
 */

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Inventory;
import model.Part;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.Random;
import java.util.ResourceBundle;
import model.Product;

/**
 This is the AddProductController class.
 */

public class AddProductController implements Initializable {


//parts
    @FXML
    private TextField searchPartsFxId;

    @FXML
    private TableView<Part> partsTable;

    @FXML
    private TableColumn<Part, Integer> partIdTableCol;

    @FXML
    private TableColumn<Part, String> nameTableCol;

    @FXML
    private TableColumn<Part, Integer> inventoryTableCol;

    @FXML
    private TableColumn<Part, Double> priceTableCol;

    //products

    @FXML
    private TextField addProductName;

    @FXML
    private TextField addProductStock;

    @FXML
    private TextField addProductPrice;

    @FXML
    private TextField addProductMax;

    @FXML
    private TextField addProductMin;

    @FXML
    private TextField addProductID;


    //associated parts

    @FXML
    private TableView<Part> associatedTable;

    @FXML
    private TableColumn<Part, Integer> associatedIdTableCol;

    @FXML
    private TableColumn<Part, String> associatedNameTableCol;

    @FXML
    private TableColumn<Part, Integer> associatedInventoryTableCol;

    @FXML
    private TableColumn<Part, Double> associatedPriceTableCol;



    private int productId;
    Product product;
    ObservableList<Part> associatedItems = FXCollections.observableArrayList();

    /**
      This method creates a new unique number.
      @return Id.
     */

    @FXML
    private Integer createProductId() {
        Random num = new Random();
        Integer Id = num.nextInt(999999);
        Inventory.getAllParts().forEach((item) -> {
            if (item.getId() == Id) {
                num.nextInt();
            }
        });
        return Id;
    }


    /**
      This method stays on or leaves the add parts window based on user input.
      @param event is on click
      @throws IOException if IO operations fail.
      @exception IOException prints the IO operation failure.
     */
    @FXML
    void cancelProduct(ActionEvent event) throws IOException {

        try {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will clear all text field values. Would you like to continue?");

            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {

                Parent root = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                Scene scene = new Scene(root, 800, 600);
                stage.setTitle("Inventory Management System");
                stage.setScene(scene);
                stage.show();
            }
        }catch (IOException e){
            System.out.println(e.getMessage());
        }
    }


    /**
      This method adds the newly created product and any associated parts to the product inventory.
      @param event on click.
      @throws IOException if IO operation fails.
      @exception ArithmeticException if max is greater than min.
      @exception NullPointerException if the part name or company name is a number, or left blank.
      @exception ArithmeticException if inventory is greater than max.
      @exception ArithmeticException if inventory is less than min.
      @exception NumberFormatException if entered text does not match accept type.
      @exception IOException prints the IO operation failure.
     */

    @FXML
    void saveProductButtonListener (ActionEvent event) throws IOException {

        try {

            //edge cases to check for user error input
            if (Integer.parseInt(addProductMax.getText()) < Integer.parseInt(addProductMin.getText())) {
                throw new ArithmeticException("MaxGreaterMin");
            }

            if (addProductName.getText().isEmpty() || isNumber(addProductName.getText())) {
                throw new NullPointerException("Correct Name not Entered");
            }

            if (Integer.parseInt(addProductStock.getText()) > Integer.parseInt(addProductMax.getText())) {
                throw new ArithmeticException("Max cannot be more then inventory");
            }

            if (Integer.parseInt(addProductStock.getText()) < Integer.parseInt(addProductMin.getText())) {
                throw new ArithmeticException("Inventory cannot be less than min amount");
            }

            if (!associatedItems.isEmpty()) {

                Product newProduct = (new Product(Integer.parseInt(addProductID.getText()), addProductName.getText(),
                        Double.parseDouble(addProductPrice.getText()),
                        Integer.parseInt(addProductStock.getText()),
                        Integer.parseInt(addProductMin.getText()),
                        Integer.parseInt(addProductMax.getText())
                        ));

                Inventory.addProduct(newProduct);

                for(Part item : associatedItems){
                    newProduct.addAssociatedPart(item);
                }

                alerts("Added New Product!","You Have Successfully Added a New Product!","New Product Added");


                associatedItems.clear();

                // back to main
                Parent root = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                Scene scene = new Scene(root, 800, 500);
                stage.setTitle("Inventory Management System");
                stage.setScene(scene);
                stage.show();


            } else if (associatedItems.isEmpty()) {

                Inventory.addProduct(new Product(Integer.parseInt(addProductID.getText()), addProductName.getText(),
                        Double.parseDouble(addProductPrice.getText()),
                        Integer.parseInt(addProductStock.getText()),
                        Integer.parseInt(addProductMin.getText()),
                        Integer.parseInt(addProductMax.getText())
                ));


                //confirmation
                alerts("Added New Product!","You Have Successfully Added a New Product!","New Product Added");


                // back to main
                Parent root = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                Scene scene = new Scene(root, 800, 500);
                stage.setTitle("Inventory Management System");
                stage.setScene(scene);
                stage.show();

            }

                } catch(NumberFormatException e){
            errorAlerts("Please a valid value for each text field!");
            }
                catch (ArithmeticException e){
                if (Integer.parseInt(addProductMax.getText()) < Integer.parseInt(addProductMin.getText())) {
                errorAlerts("Max Inventory Must Be Greater Than Min Inventory!");

            }   else if (Integer.parseInt(addProductStock.getText()) > Integer.parseInt(addProductMax.getText())){
                errorAlerts("Inventory for this part may not be greater than max");
                } else {
                errorAlerts("Inventory for this part may not be less than the min!");
            }
        }
        catch (NullPointerException e){
                errorAlerts("Please a valid product name!");
        }
        catch(IOException e){
            errorAlerts("There is an issue with this event.");
            System.out.println(e.getMessage());
        }

//ends
    }

    /**
      Method takes in the setContextText and shows the user the error with feedback.
      @param contentText is the text shown to the user to deliver a message response.
     */

    public void errorAlerts(String contentText){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setContentText(contentText);
        alert.showAndWait();
    }

    /**
      method checks if a string is numeric.
      @param item is the string to be checked to see if it can be converted to a number.
      @return true
     */

    public static boolean isNumber(String item){

        for(char letter : item.toCharArray()){
            if(!Character.isDigit(letter)){
                return false;
            }
        }
        return true;
    };

    /**
      This method sets the parts table.
     */

    public void setPartsTable() {
        partsTable.refresh();

        partsTable.setItems(Inventory.getAllParts());
        partIdTableCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameTableCol.setCellValueFactory(new PropertyValueFactory<>("Name"));
        inventoryTableCol.setCellValueFactory(new PropertyValueFactory<>("Stock"));
        priceTableCol.setCellValueFactory(new PropertyValueFactory<>("Price"));
    }

    /**
      This method sets the associated parts table for the product.
      @exception NullPointerException if there are no associated parts for this product.
     */

    public void setAssociatedPartsTable() {
        try {
            associatedTable.refresh();
            associatedTable.setItems(product.getAllAssociatedParts());
            associatedIdTableCol.setCellValueFactory(new PropertyValueFactory<>("id"));
            associatedNameTableCol.setCellValueFactory(new PropertyValueFactory<>("Name"));
            associatedInventoryTableCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
            associatedPriceTableCol.setCellValueFactory(new PropertyValueFactory<>("Price"));
        }catch (NullPointerException e){
            associatedTable.setPlaceholder(new Label("No parts are associated with this product"));

        }
    }

    /**
      This method searches the parts inventory based on user input of Id or Name.
      @param event on button click
      @exception NumberFormatException if the user fails to enter a value. It will ask the user to enter a value and show the
      parts table.
     */


    @FXML
    void searchParts(ActionEvent event) {

        try{
        String input = searchPartsFxId.getText();

        if(input.length() == 0){
            Inventory.lookupPart(searchPartsFxId.getText()).clear();
            partsTable.setItems(Inventory.getAllParts());
        }

        if(MainMenuController.isNumber(input) && Inventory.lookupPart(Integer.parseInt(input)) != null){
            int number = Integer.parseInt(input);
            ObservableList<Part> partsFound = FXCollections.observableArrayList();

            Part part = Inventory.lookupPart(number);
            System.out.println(number);
            partsFound.add(part);
            partsTable.setItems(partsFound);

        } else if (!MainMenuController.isNumber(input) && !Inventory.lookupPart(input).isEmpty()){

            Inventory.lookupPart(searchPartsFxId.getText()).clear();
            partsTable.setItems(Inventory.lookupPart(searchPartsFxId.getText()));

        } else if((MainMenuController.isNumber(input) && Inventory.lookupPart(Integer.parseInt(input)) == null) ||
                (!MainMenuController.isNumber(input) && Inventory.lookupPart(input).isEmpty())){
            MainMenuController.alerts("This Part is not in our Inventory.");
            Inventory.lookupPart(searchPartsFxId.getText()).clear();
            partsTable.setItems(Inventory.getAllParts());

        } else {
            Inventory.lookupPart(searchPartsFxId.getText()).clear();
            partsTable.setItems(Inventory.getAllParts());
        }
         } catch(NumberFormatException e){

            alerts("Please enter a part to search");
        }

    }

    /**
      This method adds the parts selected and associates them with the product.
      @param event on click event.
     */

    @FXML
    public void addPartAssociatedList(ActionEvent event){
        Part selectedPart = partsTable.getSelectionModel().getSelectedItem();

        if(!associatedItems.contains(selectedPart)) {

            associatedItems.add(selectedPart);

            associatedTable.refresh();
            associatedTable.setItems(associatedItems);
            associatedIdTableCol.setCellValueFactory(new PropertyValueFactory<>("id"));
            associatedNameTableCol.setCellValueFactory(new PropertyValueFactory<>("Name"));
            associatedInventoryTableCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
            associatedPriceTableCol.setCellValueFactory(new PropertyValueFactory<>("Price"));

        } else if (associatedItems.contains(selectedPart)){
            errorAlerts("This part has already been associated with this product.");

           }

        }


    /**
      This method sends an alert to the user and provides them a message response.
      @param message is the message provided to the user.
     */
    public static void alerts(String message){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText("Guidance");
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
      This method removes a part from the parts associated with the product.
      @param event on click.
     */

    @FXML
    void removeAssociatedPart(ActionEvent event) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, " Would you like to continue?");
        alert.setTitle("Remove");
        alert.setHeaderText("Remove Associated Part");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            Part selectedPart = associatedTable.getSelectionModel().getSelectedItem();

            associatedItems.remove(selectedPart);
            setAssociatedPartsTable();
        }
    }

    /**
      This method sends a more detailed alert to the user and provides them a message response.
      @param title is the alert title.
      @param headText is the alert header.
      @param message is the context message.
     */

    public static void alerts(String title,String headText,String message){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(headText);
        alert.setContentText(message);

        alert.showAndWait();
    }

    /**
     This method initializes the controller.
      @param location loads the fxml document.
      @param resources loads the resources.
     */

    @Override
    public void initialize (URL location, ResourceBundle resources){

        setPartsTable();

        productId = createProductId();
        addProductID.setText(Integer.toString(productId));


    }




}
