package model;

/**
  @author Melissa Aybar
  This is the Outsourced class. It extends and inheritances from the abstract part class.
  It holds unique methods for outsourced parts to inherit.
 */

public class Outsourced extends Part{
    private String companyName;

    /**
      Constructor
      @param id outsourced id
      @param name outsourced name
      @param price outsourced price
      @param stock outsourced stock
      @param min outsourced min
      @param max outsourced max
      @param companyName outsourced company name
     */


    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }


    /**
      This method sets the company name for outsourced parts.
      @param companyName the companyName to set
     */

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /**
      This method returns the company name of the outsourced part.
      @return the companyName
     */
    public String getCompanyName() {
        return companyName;
    }


}

