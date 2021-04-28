package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
  @author Melissa Aybar
  This is the Product class. This is the mother class for products to inherit from.
 */


public class Product {

    /**
      @value associatedParts stores the parts for the product
     */
        private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
        private int id;
        private String name;
        private double price = 0.0;
        private int stock;
        private int min;
        private int max;

    /**
      constructor
      @param id product id
      @param name  product name
      @param price product price
      @param stock  product stock
      @param min product min
      @param max product max
     */

        public Product(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    /**
       This method sets the products ID.
       @param id the id for the product
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
       This method sets the products name.
       @param name the products name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
      This method sets the products price.
      @param price the products price
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
      This method sets the products stock.
      @param stock the stock in inventory
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
      This method sets the products inventory minimum.
      @param min the min to set
     */
    public void setMin(int min) {
        this.min = min;
    }

    /**
      This sets the products inventory maximum.
      @param max the max to set
     */
    public void setMax(int max) {
        this.max = max;
    }

    /**
      This method returns the products Id.
      @return the id
     */
    public int getId() {
        return id;
    }


    /**
      This method returns the products name.
      @return the name
     */
    public String getName() {
        return name;
    }

    /**
      This method method returns the products price.
      @return the price
     */
    public double getPrice() {
        return price;
    }

    /**
      This method gets the products actual inventory stock amount.
      @return the stock
     */
    public int getStock() {
        return stock;
    }


    /**
      This method gets the products inventory minimum.
      @return the min
     */
    public int getMin() {
        return min;
    }


    /**
      This method returns the products inventory maximum.
      @return the max
     */
    public int getMax() {
        return max;
    }


    /**
     This method adds part to the products associated inventory.
      @param part is the associated part for the product.
     */

    public void addAssociatedPart(Part part){
            associatedParts.add(part);
        }


    /**
      This method disassociates part from the product.
      @param selectedAssociatedPart is the associated part for the product to be removed.
      @return false

     */
        public boolean deleteAssociatedPart(Part selectedAssociatedPart){
            if(selectedAssociatedPart != null) {
                associatedParts.remove(selectedAssociatedPart);
                return true;
            }
            return false;
        }

    /**
      This method returns all associated parts for the product.
      @return associatedParts
     */
        public ObservableList<Part> getAllAssociatedParts(){
            return associatedParts;
         }


    }


