/**
 * Programmed by Wenkang Zhou and Benlong Huang
 * B.S. Computer Science University of Arizona - Class of 2018 
 * Finished date: 10/21/16 
 * Java Class Name: JukeboxAccountCollection.java
 * 
 * Behavior: This class is the object of jukebox account collection
 * It can add a new account by method, get the account and 
 * make the accounts in the collection all available in the next calendar day
 * It is Serializable.
 * 
 * 
 **/
package model;

import java.io.Serializable;
import java.util.ArrayList;

public class JukeboxAccountCollection implements Serializable{

	
	private static final long serialVersionUID = 1L;
	private volatile ArrayList<JukeboxAccount> list;

	public JukeboxAccountCollection() {
		list = new ArrayList<JukeboxAccount>();
	}

	// add a account by name and password
	public void add(String name, String password) {
		list.add(new JukeboxAccount(name, password));
	} 

	// check the account is valid or not
	public boolean isAccountValid(String name, String password) {
		for(JukeboxAccount account : list){
			if(account.getAccountName().equals(name) && account.isPasswordCorrect(password))
				return true;
		}
		return false;
	}
	
	// get the account by name
	public JukeboxAccount getAccount(String name){
		// it's based on the validation of the account
		for(JukeboxAccount account : list){
			if(account.getAccountName().equals(name))
				return account;
		}
		return null;
	}
	
	// reset 
	public void goingToNextDay(){
		for(JukeboxAccount account : list){
				account.goingToNextDay();
		}
	}

}
