package Beans;

import Auctions.Observer;

public class UserBean implements Observer{//user bean to pass to session
    private String firstName;
    private String lastName;
    private String streetName;
    private String streetNumber;
    private String postalCode;
    private String city;
    private String country;
    private String id;
    private String auctionStatus;

    public UserBean() {}

    public UserBean(String firstName, String lastName, String streetName, String streetNumber,
                    String postalCode, String city, String country, String id) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.streetName = streetName;
        this.streetNumber = streetNumber;
        this.postalCode = postalCode;
        this.city = city;
        this.country = country;
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }
 

    public String getLastName() {
        return lastName;
    }


    public String getStreetName() {
        return streetName;
    }

 

    public String getStreetNumber() {
        return streetNumber;
    }

  

    public String getPostalCode() {
        return postalCode;
    }


    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }

    
    public String getID(){
        return this.id;
    }

    @Override
    public void update(String auctionStatus) {
        this.auctionStatus = auctionStatus;
        System.out.println("User " + this.firstName + " " + this.lastName + " is notified: " + auctionStatus); 
    }

    public String getAuctionStatus(){
        return auctionStatus;
    }



}
