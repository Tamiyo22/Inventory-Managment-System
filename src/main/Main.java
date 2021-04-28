package main;
/**
  @Author Melissa Aybar


  FUTURE ENHANCEMENT
  I feel that in the future this project could be expanded by adding accessibility design functionality to help employees
  that use screen readers. The inventory management system could also be expanded to adjust inventory for each part as its used
  by a product. I would also like to implement the ability to prevent deletion of a part while it is attached to a product. I have already
  begun a small implementation of this. The user now receives a notification that their part is associated with a product. A search
  button next to the search bar would also help with user interaction, and guide them intuitively to the search bar, instead
  of just hoping that they figure it out.

  RUNTIME ERROR
  I encountered the runtime error "NullPointerException" when the user enters nothing into the search bars for the part and
  product searches. I was able to catch and adapt to this error. I made it work for this project by using a try/catch block to adjust
  the response to an interactive textbox that communicates with the user. This way the user understands that there is
  still an interaction happening with them and the search bars.

 * */


import model.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;




/**  This class creates an app that displays messages. */

public class Main extends Application {
    @Override
    public void init() {
        System.out.println("Starting!!");
    }


    /**
      This is the main method. This is the first method that gets called when you run this java program.
     */
    @Override
    //Top level window Takes the stage
    public void start(Stage primaryStage) throws Exception {

        //takes in the ui from the fxml file
        Parent root = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
        //we have a scene and a stage
        //stage is a top level UI container
        primaryStage.setTitle("Inventory Management System");
       // Image icon = new Image("computerIcon.png");
        //primaryStage.getIcons().add(icon);
        primaryStage.setScene(new Scene(root,800, 500));

        //show the UI
        primaryStage.show();



//test data

        Inventory.addPart(new InHouse(1, "Hard drive", 800.00, 5000, 1000, 10000,1));
        Inventory.addPart(new InHouse(2, "Webcam", 50.00, 20, 3, 1000,2));
        Inventory.addPart( new InHouse(3, "Mother Board", 900.00, 100, 50, 1000,3));
        Inventory.addPart( new Outsourced(4, "Apple Monitor", 50.00, 980, 20, 2000,"Apple"));
        Inventory.addPart( new Outsourced(5, "Dell Monitor 7060", 900.00, 5000, 1000, 10000,"Dell"));
        Inventory.addPart( new Outsourced(6, "Intel Core i9 Processor", 474.99, 75, 50, 150,"Intel"));



        Inventory.addProduct(new Product(1,"IdeaPad ", 269.99,4000,10,5000));
        Inventory.addProduct(new Product(2,"Hp ", 499.99,1000,10,5000));
        Inventory.addProduct(new Product(3,"Mac Book Pro ", 2000.00,20,1,5000));
        Inventory.addProduct(new Product(4,"Lenovo", 3000.00,20,1,2000));
        Inventory.addProduct(new Product(5,"Dell PC", 1500.00,100,1,300));
        Inventory.addProduct(new Product(6,"Surface Pro", 899.99,200,1,1000));


    }
}
