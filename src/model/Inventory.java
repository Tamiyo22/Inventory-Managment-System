package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
@author: Melissa Aybar
*/

/** This is the Inventory class. It stores all of the data for products and parts */

public class Inventory {
    /**
      @value allParts
      @value allProducts
     */

    private static ObservableList<Part> allParts = FXCollections.observableArrayList();

    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();



    /**
      This method adds a new part to the allParts inventory.
      @param newPart is the new part added.
     */

    public static void addPart(Part newPart) {
        if (newPart != null) {
            allParts.add(newPart);
        }
    }

    /**
      This method adds a new product to the allProducts inventory.
      @param newProduct is the new part added
     */

    public static void addProduct(Product newProduct) {
        if (newProduct != null) {
            allProducts.add(newProduct);
        }
    }

    /**
      This method iterates through all Parts and returns the part based on an ID match or null if there is no match.
      @param partId is Id to search for.
      @return null
     */
    public static Part lookupPart(int partId) {

        for (Part item : allParts) {
            if (item.getId() == partId) {
                return item;
            }
        }
        return null;
    }

    /**
      This method iterates through the allProducts inventory and returns the product based on an ID match or null
      if there is not a match.
      @param productId is Id to search for
      @return null
     */

    public static Product lookupProduct(int productId) {

        for (Product product : allProducts) {
            if (product.getId() == productId) {
                return product;
            }
        }
        return null;
    }


    /**
      This over loaded method iterates through the allParts inventory
      and returns a ObservableList based on a name matches.
      @param partName is the name to search for
      @return ObservableList
     */

    public static ObservableList<Part> lookupPart(String partName) {
       ObservableList<Part> filteredParts = FXCollections.observableArrayList();

        for (Part item : allParts) {
            if (item.getName().toLowerCase().contains(partName.toLowerCase())) {
                filteredParts.add(item);
            }
        }
        return filteredParts;
    }

    /**
      This over loaded method filters through the allProducts inventory
      and returns the product based on a match.
      @param productName is Id to search for
      @return product ObservableList
     */

    public static ObservableList<Product> lookupProduct(String productName) {

        ObservableList<Product> filteredProducts = FXCollections.observableArrayList();

        for (Product item : Inventory.getAllProducts()) {
            if (item.getName().toLowerCase().contains(productName.toLowerCase())) {
                filteredProducts.add(item);
            }
        }
        return filteredProducts;
    }

    /**
      This method allows access to a part to update it.
      @param index        is the selected index
      @param selectedPart is the selected part
     */

    public static void updatePart(int index, Part selectedPart ) {
        int id = -1;

        for(Part item : Inventory.getAllParts()){
            id++;
            if(item.getId() == index){
                Inventory.getAllParts().set(id,selectedPart);

            }
        }

    }

    /**
      This method allows access to a product and allows its information to be reset.
      @param index  is the selected index
      @param selectedProduct is the selected product
     */
    public static void updateProduct(int index, Product selectedProduct) {
        int id = -1;

        for(Product item : Inventory.getAllProducts()){
            id++;
            if(item.getId() == index){
                Inventory.getAllProducts().set(id,selectedProduct);

            }
        }

    }

    /**
      deletePart method
      This method removes the selectedPart from the allParts inventory.
      @param selectedPart is the part to be removed
      @return boolean
     */
    public static boolean deletePart(Part selectedPart) {

        if (allParts.size() > 1) {
            allParts.remove(selectedPart);
            return true;
        }
        return false;
    }

    /**
      This method removes the selectedProducts from the allProducts inventory.
      @param selectedProduct is the part to be removed
      @return boolean
     */

    public static boolean deleteProduct(Product selectedProduct) {
        if (allProducts.size() > 1) {
            allProducts.remove(selectedProduct);
            return true;
        }
        return false;
    }

    /**
      This method returns the allParts inventory.
      @return allParts
     */

    public static ObservableList<Part> getAllParts() {
        return allParts;
    }

    /**
      This method returns the allProducts inventory.
      @return allProducts
     */
    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }


}











