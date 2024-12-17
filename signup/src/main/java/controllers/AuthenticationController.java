package controllers;

import db.DatabaseHandler;
import Beans.UserBean;

public class AuthenticationController {
	
	
	
	public static boolean createUserAccount(String username, String password,String firstName,String lastName,
			String streetName,String  streetNumber,String  postalCode,String  city,String  country) {
		
		
		return DatabaseHandler.insertUserRecord(username, password, firstName, lastName, streetName, streetNumber, postalCode, city, country);

	}


	public static boolean usernameExists(String username) {
	
		return DatabaseHandler.usernameExists(username);
	}

	public static UserBean getUserinfo(String username){
		return DatabaseHandler.getUserInfo(username);
	}

	

}
