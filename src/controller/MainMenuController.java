package controller;
/**
  @Author Melissa Aybar
  This is the MainMenuController class..
  This class is the hub that leads to other class and holds all main menu methods.

 */

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Part;
import model.Product;
import model.Inventory;


import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;


public class MainMenuController implements Initializable {

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


    //Products

    @FXML
    private TextField searchProductsFxId;

    @FXML
    private TableView<Product> productsTable;

    @FXML
    private TableColumn<Product, Integer> productIdTableCol;

    @FXML
    private TableColumn<Product, String> productNameTableCol;

    @FXML
    private TableColumn<Product, Integer> productInventoryTableCol;

    @FXML
    private TableColumn<Product, Double> productPriceTableCol;



    /**
     * This method sets up parts table.
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
      This method sets up products table.
     */

    public void setProductsTable() {
        productsTable.refresh();

        productsTable.setItems(Inventory.getAllProducts());
        productIdTableCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        productNameTableCol.setCellValueFactory(new PropertyValueFactory<>("Name"));
        productInventoryTableCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productPriceTableCol.setCellValueFactory(new PropertyValueFactory<>("Price"));
    }

    /**
      This method method exits the program.
      @param event on click.
     */

    @FXML
    void exit(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Are you sure you would like to exit the program?");

        Optional<ButtonType> result = alert.showAndWait();

        if(result.isPresent() && result.get() == ButtonType.OK) {
            System.out.println("Exit!!");
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.close();
        }
    }

    /**
      This method is an on click event method that brings user to the add parts stage.
      @param event on click.
      @throws IOException if IO operation fails.
      @exception IOException prints the IO operation failure.
     */

    @FXML
    void toAddPart(ActionEvent event) throws IOException {
        try{
        Parent root = FXMLLoader.load(getClass().getResource("/view/addPart.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 800, 600);
        stage.setTitle("Add Part");
        stage.setScene(scene);
        stage.show();
        } catch(IOException e){
            errorAlerts("There is an issue with this event.");
            System.out.println(e.getMessage());
        }
    }

    /**
      This method brings loads the product stage
      @param event on click
      @throws IOException if IO operation fails.
      @exception IOException prints the IO operation failure.
     */

    @FXML
    void toAddProduct(ActionEvent event) throws IOException {
        try{
        Parent root = FXMLLoader.load(getClass().getResource("/view/addProduct.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1000, 800);
        stage.setTitle("Add Product");
        stage.setScene(scene);
        stage.show();
        } catch(IOException e){
            errorAlerts("There is an issue with this event.");
            System.out.println(e.getMessage());
        }
    }

    /**
      This method loads the modify part stage and sets the part to be modified.
      @param event on click
      @throws IOException if IO operation fails.
      @exception IOException prints the IO operation failure.
     */

    @FXML
    void toModifyPart(ActionEvent event) throws IOException {

        try{

          if(partsTable.getSelectionModel().getSelectedItem() !=null){
            Part selectedPart = partsTable.getSelectionModel().getSelectedItem();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/modifyPart.fxml"));
            Parent parent = loader.load();
             Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
             Scene scene = new Scene(parent, 1000, 600);
             stage.setTitle("Modify Part");
             stage.setScene(scene);
             ModifyPartController controller = loader.getController();
             controller.setPart(selectedPart);
             stage.show();

        Inventory.updatePart(selectedPart.getId(),selectedPart);

        } else if(partsTable.getSelectionModel().getSelectedItem() == null){
            errorAlerts("Please select a part to modify.");
        }
        } catch(IOException e){
            errorAlerts("There is an issue with this event.");
            System.out.println(e.getMessage());
        }
    }

    /**
      This method loads the modify product stage and sets the product to be modified.
      @param event on click
      @throws IOException if IO operation fails.
      @exception IOException prints the IO operation failure.
     */

    @FXML
    void toModifyProduct(ActionEvent event) throws IOException {

        try{
            if(productsTable.getSelectionModel().getSelectedItem() !=null){
                Product selectedProduct = productsTable.getSelectionModel().getSelectedItem();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/modifyProduct.fxml"));
                Parent parent = loader.load();
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                Scene scene = new Scene(parent, 1000, 800);
                stage.setTitle("Modify Product");
                stage.setScene(scene);
                ModifyProductController controller = loader.getController();
                controller.setProduct(selectedProduct);
                stage.show();

                Inventory.updateProduct(selectedProduct.getId(),selectedProduct);
            } else if (productsTable.getSelectionModel().getSelectedItem() == null){
                errorAlerts("Please select a product to modify.");
            }
     } catch(IOException e){
        errorAlerts("There is an issue with this event.");
        System.out.println(e.getMessage());
    }
        }



    /**
      deletePart method is a method that removes parts from the parts inventory.
     */

    @FXML
    private void deletePart() {

        boolean flagPartOfProduct = false;

        Part myPart = partsTable.getSelectionModel().getSelectedItem();
        for (Product item : Inventory.getAllProducts()) {
            if (item.getAllAssociatedParts().contains(myPart)) {
                flagPartOfProduct = true;
            }
        }

        if (flagPartOfProduct) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This part is associated with a product, are you sure?");
            alert.setTitle("Associated Part");
            alert.setHeaderText("Delete Associated Part");
            Optional<ButtonType> result = alert.showAndWait();


            if (result.isPresent() && result.get() == ButtonType.OK) {
                partsTable.getItems().remove(myPart);
                Inventory.deletePart(myPart);
                partsTable.refresh();

            }
        } else  {

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This part will be deleted. Would you like to continue?");
                alert.setTitle("Delete Part");
                alert.setHeaderText("Delete");


            Optional<ButtonType>  result = alert.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    partsTable.getItems().remove(myPart);
                    Inventory.deletePart(myPart);
                    partsTable.refresh();
                }
            }
        }


    /**
      This method removes products from the products inventory only if they do no have associated parts.
      @throws Exception is thrown if product has associated parts.
      @exception Exception alerts the user if parts are associated with product.
     */

    @FXML
    private void deleteProduct() {

        try{
        Product myProduct = productsTable.getSelectionModel().getSelectedItem();

        if(!myProduct.getAllAssociatedParts().isEmpty()){
            System.out.println(myProduct.getAllAssociatedParts().isEmpty());
            throw new Exception("Please remove products associated parts");
        }else {

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This product will be deleted. Would you like to continue?");
            alert.setTitle("Delete Product");
            alert.setHeaderText("Delete");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {

                productsTable.getItems().remove(myProduct);
                Inventory.deleteProduct(myProduct);
                productsTable.refresh();
                System.out.println(myProduct.getClass());
            }
        }

    } catch (Exception e){
            errorAlerts("Please un associate parts from this product before deleting! ");
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
    try {
    String input = searchPartsFxId.getText();

        if (input.length() == 0) {
        Inventory.lookupPart(searchPartsFxId.getText()).clear();
        partsTable.setItems(Inventory.getAllParts());
    }

        if (isNumber(input) && Inventory.lookupPart(Integer.parseInt(input)) != null) {
        int number = Integer.parseInt(input);
        ObservableList<Part> partsFound = FXCollections.observableArrayList();

        Part part = Inventory.lookupPart(number);
        System.out.println(number);
        partsFound.add(part);
        partsTable.setItems(partsFound);

    }   else if (!isNumber(input) && !Inventory.lookupPart(input).isEmpty()) {

        Inventory.lookupPart(searchPartsFxId.getText()).clear();
        partsTable.setItems(Inventory.lookupPart(searchPartsFxId.getText()));

    }  else if ((isNumber(input) && Inventory.lookupPart(Integer.parseInt(input)) == null) ||
            (!isNumber(input) && Inventory.lookupPart(input).isEmpty())) {
        alerts("This Part is not in our Inventory.");
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
      This method searches the products inventory based on user input of Id or Name.
      @param event on button click
      @exception NumberFormatException if the user fails to enter a value. It will ask the user to enter a value and show the
      products table.
     */


    @FXML
    void searchProducts(ActionEvent event) {

        try{
        String input = searchProductsFxId.getText();
        if(input.length() == 0){
            Inventory.lookupPart(searchProductsFxId.getText()).clear();
            productsTable.setItems(Inventory.getAllProducts());
        }

        if(isNumber(input) && Inventory.lookupProduct(Integer.parseInt(input)) != null){
            int number = Integer.parseInt(input);
            ObservableList<Product> productsFound = FXCollections.observableArrayList();

            Product product = Inventory.lookupProduct(number);
            System.out.println(number);
            productsFound.add(product);
            productsTable.setItems(productsFound);

        } else if (!isNumber(input) && !Inventory.lookupProduct(input).isEmpty()){

            Inventory.lookupProduct(searchProductsFxId.getText()).clear();
            productsTable.setItems(Inventory.lookupProduct(searchProductsFxId.getText()));

        } else if((isNumber(input) && Inventory.lookupProduct(Integer.parseInt(input)) == null) ||
                (!isNumber(input) && Inventory.lookupProduct(input).isEmpty())){
            alerts("This Product is not in our Inventory.");
            Inventory.lookupProduct(searchProductsFxId.getText()).clear();
            productsTable.setItems(Inventory.getAllProducts());

        } else {
            Inventory.lookupProduct(searchProductsFxId.getText()).clear();
            productsTable.setItems(Inventory.getAllProducts());
        }
        } catch(NumberFormatException e){

            alerts("Please enter a product to search");
        }
    }

    /**
      method checks if a string can be converted into a numeric value.
      @param item is the string to be checked to see if it can be converted to a number.
      @return true.
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
      This method sends an alert to the user and provides them an error message response.
      @param contentText is the error message provided to the user.
     */

    public void errorAlerts(String contentText){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setContentText(contentText);
        alert.showAndWait();
    }


    /**
      initialize method initializes methods created for our main stage
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("Initialized");

        setPartsTable();
        setProductsTable();

         }



}
