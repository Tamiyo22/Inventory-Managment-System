package controller;

/**
  @author Melissa Aybar
  ModifyPartController class.
  This class modifies parts in the inventory.
 */

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;


import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.*;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;


/** This class provides methods that modify parts.*/
public class ModifyPartController implements Initializable {



    @FXML
    private RadioButton inHouseButton;

    @FXML
    private RadioButton outsourcedButton;

    @FXML
    private TextField partId;

    @FXML
    private TextField modifyPartName;

    @FXML
    private TextField modifyPartStock;

    @FXML
    private Label modifyPartOrCompany;

    @FXML
    private TextField modifyPartMax;

    @FXML
    private TextField modifyPartPrice;

    @FXML
    private TextField modifyPartMin;

    @FXML
    private Button savePartButton;

    @FXML
    private Button cancelPartButton;

    @FXML
    private TextField partOrCompany;


    Part part;


    /**
      This Method sets the part to be loaded and modified.
      @param part is the part to be modified.
     */
    public void setPart(Part part){

    this.part = part;

    partId.setText(Integer.toString(part.getId()));
    modifyPartName.setText(part.getName());
    modifyPartPrice.setText(Double.toString(part.getPrice()));
    modifyPartStock.setText(Integer.toString(part.getStock()));
    modifyPartMin.setText(Integer.toString(part.getMin()));
    modifyPartMax.setText(Integer.toString(part.getMax()));


    if(part instanceof InHouse) {
        InHouse inhouse = (InHouse) part;
        modifyPartOrCompany.setText(Integer.toString((inhouse.getMachineID())));
        inHouseButton.setSelected(true);
        outsourcedButton.setSelected(false);
        modifyPartOrCompany.setText("Machine Id");
        partOrCompany.setText(Integer.toString(((InHouse) part).getMachineID()));
    } else {
        Outsourced outsourced = (Outsourced) part;
        modifyPartOrCompany.setText((outsourced.getCompanyName()));
        inHouseButton.setSelected(false);
        outsourcedButton.setSelected(true);
        modifyPartOrCompany.setText("Company Name");
        partOrCompany.setText(outsourced.getCompanyName());
    }


}

    /**
      This method listens for inHouse radio button selections and adjusts text accordingly.
      @param event on click.
     */

    @FXML
    private void inHouseListener(ActionEvent event) {
        if (inHouseButton.isSelected()) {
            outsourcedButton.setSelected(false);
            modifyPartOrCompany.setText("Machine Id");

        }

    }

    /**
      This method listens for outsourced radio button selections and adjusts text accordingly.
      @param event on click.
     */

    @FXML
    private void outSourcedListener(ActionEvent event){

        if (outsourcedButton.isSelected()) {
            inHouseButton.setSelected(false);
            modifyPartOrCompany.setText("Company Name ");
        }
    }


    /**
      This method closes out of the modify part screen and checks to make sure the user would like to.
      @param event on click.
      @throws IOException if IO operations fail.
     */
    @FXML
    void cancelPartListener(ActionEvent event) throws IOException {
        try{
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"This will clear all text field values. Would you like to continue?");
        Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK){
            Parent root = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 800, 600);
            stage.setTitle("Inventory Management System");
            stage.setScene(scene);
            stage.show();
        }
            } catch(IOException e){
                errorAlerts("There is an issue with this event.");
                 System.out.println(e.getMessage());
            }
        }

    /**
      This method updates the modified part and updates the parts inventory.
      @param event on click.
      @throws IOException if IO operation fails.
      @exception ArithmeticException if max is greater than min.
      @exception NullPointerException if the part name or company name is a number, or left blank.
      @exception Exception if inventory is greater than max.
      @exception ArithmeticException if inventory is less than min.
      @exception NumberFormatException if entered text does not match accept type.
     */

    @FXML
    void savePartButtonListener(ActionEvent event) {

        try {

            //edge cases to check for user error input
            if (Integer.parseInt(modifyPartMax.getText()) < Integer.parseInt(modifyPartMin.getText())) {
                throw new ArithmeticException("MaxGreaterMin");
            }

            if(modifyPartName.getText().isEmpty() || isNumber(modifyPartName.getText())){
                throw new NullPointerException("Correct Name not Entered");
            }

            if(Integer.parseInt(modifyPartStock.getText()) > Integer.parseInt(modifyPartMax.getText())){
                throw new Exception("Inventory cannot be more then Max");
            }

            if(Integer.parseInt(modifyPartStock.getText()) < Integer.parseInt(modifyPartMin.getText())){
                throw new ArithmeticException("Inventory cannot be less than min amount");
            }

            if(outsourcedButton.isSelected() && isNumber(partOrCompany.getText())){
                throw new NullPointerException("Not a valid company name");
            }

            if (inHouseButton.isSelected()) {


            part =(new InHouse(Integer.parseInt(partId.getText()), modifyPartName.getText(),
                    Double.parseDouble(modifyPartPrice.getText()),
                    Integer.parseInt(modifyPartStock.getText()),
                    Integer.parseInt(modifyPartMin.getText()),
                    Integer.parseInt(modifyPartMax.getText()), Integer.parseInt(partOrCompany.getText())));

            Inventory.updatePart(part.getId(),part);

            //confirmation alert
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Added New Part!");
            alert.setHeaderText("You Have Successfully Updated a Part!");
            alert.setContentText(null);
            alert.showAndWait();

            // back to main
                Parent root = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                Scene scene = new Scene(root, 800, 600);
                stage.setTitle("Inventory Management System");
                stage.setScene(scene);
                stage.show();


            } else if (outsourcedButton.isSelected()) {
                 part =(new Outsourced(Integer.parseInt(partId.getText()), modifyPartName.getText(),
                        Double.parseDouble(modifyPartPrice.getText()),
                        Integer.parseInt(modifyPartStock.getText()),
                        Integer.parseInt(modifyPartMin.getText()),
                        Integer.parseInt(modifyPartMax.getText()),
                        partOrCompany.getText()));

                Inventory.updatePart(part.getId(),part);

                //confirmation alert
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Updated a Part!");
                alert.setHeaderText("You Have Successfully updated a Part!");
                alert.setContentText(null);
                alert.showAndWait();

                // back to main
                Parent root = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                Scene scene = new Scene(root, 800, 600);
                stage.setTitle("Inventory Management System");
                stage.setScene(scene);
                stage.show();

            }
        } catch(NumberFormatException e){
            errorAlerts("Please a valid value for each text field!");

        }
        catch (ArithmeticException e){
            if (Integer.parseInt(modifyPartMax.getText()) < Integer.parseInt(modifyPartMin.getText())) {
                errorAlerts("Max Inventory Must Be Greater Than Min Inventory!");
            } else {
                errorAlerts("Inventory for this part may not be less than the min!");
            }
        }

        catch (NullPointerException e){

            if(outsourcedButton.isSelected() && isNumber(partOrCompany.getText())) {
                errorAlerts("Please enter a valid company name!");
            } else {
                errorAlerts("Please a valid part name!");
            }
        }
        catch (Exception e){
            errorAlerts("Inventory for this part may not be greater than max");

        }


//ends
    }


    /**
      Method takes in the contextText and shows the user the error with feedback.
      @param contentText the message provided to the user to give them guidance on their error.
     */

    public void errorAlerts(String contentText){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setContentText(contentText);
        alert.showAndWait();
    }

    /**
      method checks if a string is numeric.
      @param item the string value that will be checked to see if it has numeric value.
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
     This method initializes the controller.
      @param location loads the fxml document.
      @param resources loads the resources.
     */


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("Initialized");

    }

}











