package controller;

/**
  @author Melissa Aybar
  This is the AddPartController class.
  This class adds parts to the inventory.
 *
 */


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;

import javafx.stage.Stage;
import model.Inventory;
import model.InHouse;
import model.Outsourced;
import java.io.IOException;
import java.net.URL;

import java.util.Optional;
import java.util.Random;
import java.util.ResourceBundle;

public class AddPartController implements Initializable {


@FXML
    private RadioButton inHouseButton;

    @FXML
    private RadioButton outsourcedButton;

    @FXML
    private TextField addPartID;

    @FXML
    private TextField addPartName;

    @FXML
    private TextField addPartStock;

    @FXML
    private TextField addPartOrCompany;

    @FXML
    private TextField addPartMax;

    @FXML
    private TextField addPartPrice;

    @FXML
    private TextField addPartMin;


    @FXML
    private Label partCompany;


    private int partId;

    /**
     This method creates a new unique number.
      @return Id.
     */

    @FXML
    public Integer createPartId() {
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
      This method stays or leaves the add parts window based on user input.
      @param event is the on click event.
      @throws IOException for IO operation failure.
      @exception IOException prints the IO operation failure.
     */

    @FXML
    void cancelPartListener(ActionEvent event) throws IOException {

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
        }catch(IOException e){
            errorAlerts("There is an issue with this event.");
            System.out.println(e.getMessage());
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
            partCompany.setText("Machine Id");

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
                partCompany.setText("Company Name ");
            }
        }

    /**
      This method  saves parts and handles user input exceptions
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
        void savePartButtonListener (ActionEvent event){
            //edge cases to check for user error input
            try {

            if (Integer.parseInt(addPartMax.getText()) < Integer.parseInt(addPartMin.getText())) {
                throw new ArithmeticException("MaxGreaterMin");
            }

            if(addPartName.getText().isEmpty() || isNumber(addPartName.getText())){
                throw new NullPointerException("Correct Name not Entered");
            }

            if(Integer.parseInt(addPartStock.getText()) > Integer.parseInt(addPartMax.getText())){
                  throw new ArithmeticException("Inventory cannot be more then max allowed.");
            }

            if(Integer.parseInt(addPartStock.getText()) < Integer.parseInt(addPartMin.getText())){
                throw new ArithmeticException("Inventory cannot be less than min amount");
            }

            if(outsourcedButton.isSelected() && isNumber(addPartOrCompany.getText())){
                throw new NullPointerException("Not a valid company name");
            }


            if (inHouseButton.isSelected()) {

                Inventory.addPart(new InHouse(Integer.parseInt(addPartID.getText()), addPartName.getText(),
                        Double.parseDouble(addPartPrice.getText()),
                        Integer.parseInt(addPartStock.getText()),
                        Integer.parseInt(addPartMin.getText()),
                        Integer.parseInt(addPartMax.getText()),
                        Integer.parseInt(addPartOrCompany.getText())));

               //confirmation
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Added New Part!");
                alert.setHeaderText("You Have Successfully Added a New Part!");
                alert.setContentText(null);
                alert.showAndWait();


                // back to the main screen
                Parent root = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                Scene scene = new Scene(root, 800, 600);
                stage.setTitle("Inventory Management System");
                stage.setScene(scene);
                stage.show();


            } else if (outsourcedButton.isSelected()) {
                Inventory.addPart(new Outsourced(Integer.parseInt(addPartID.getText()), addPartName.getText(),
                        Double.parseDouble(addPartPrice.getText()),
                        Integer.parseInt(addPartStock.getText()),
                        Integer.parseInt(addPartMin.getText()),
                        Integer.parseInt(addPartMax.getText()),
                        addPartOrCompany.getText()));

                 //confirmation
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Added New Part!");
                alert.setHeaderText("You Have Successfully Added a New Part!");
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
                if (Integer.parseInt(addPartMax.getText()) < Integer.parseInt(addPartMin.getText())) {
                    errorAlerts("Max Inventory Must Be Greater Than Min Inventory!");
                } else if(Integer.parseInt(addPartStock.getText()) > Integer.parseInt(addPartMax.getText())){
                    errorAlerts("Inventory for this part may not be greater than max");
                } else {
                    errorAlerts("Inventory for this part may not be less than the min!");
                }
            }
            catch (NullPointerException e){
                if(outsourcedButton.isSelected() && isNumber(addPartOrCompany.getText())) {
                    errorAlerts("Please enter a valid company name!");
                } else {
                    errorAlerts("Please a valid part name!");
                }
            }
            catch(IOException e){
                errorAlerts("There is an issue with this event.");
                System.out.println(e.getMessage());
            }


//ends
    }

    /**
      This method sends a more detailed error alert to the user and provides them a message response.
      @param contentText is the error alert context.

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
      @return true.
     */

    public static boolean isNumber(String item){
        for(char letter : item.toCharArray()){
            if(!Character.isDigit(letter)){
                return false;
            }
        }
        return true;
    }

    /**
     This method initializes the controller.
      @param location loads the fxml document.
      @param resources loads the resources.
     */

    @Override
        public void initialize (URL location, ResourceBundle resources){

            partId = createPartId();
            addPartID.setText(Integer.toString(partId));


        }


}






