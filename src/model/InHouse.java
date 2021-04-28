package model;

/**
 @author Melissa Aybar
  This is the InHouse class. It extends and inheritances from the abstract part class.
  It holds unique methods for outsourced parts to inherit.
 */
public class InHouse extends Part {
    private int machineID;

    /**
      constructor
      @param id inHouse id
      @param name inHouse name
      @param price inHouse price
      @param stock inHouse stock
      @param min inHouse min
      @param max inHouse max
      @param machineID inHouse machineID
     */

    public InHouse(int id, String name, double price, int stock, int min, int max,int machineID) {
        super(id, name, price, stock, min, max);
        this.machineID = machineID;
    }


    /**
      setMachineId method sets the parts machineID
      @param machineID the id to set
     */

    public void setMachineID(int machineID) {
        this.machineID = machineID;
    }

    /**
      getMachineID method returns the machineID
      @return the machineId
     */

    public int getMachineID() {
        return machineID;
    }

}
