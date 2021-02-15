package ie.gmit.sw;

/**
 * 
 * @author Tomas O'Malley
 * @version 2.0
 * @since 1.8
 * 
 *        Public class <b>CustomerFactory</b> holds an array of type customer implements the factory pattern for customer records 
 * 
 *
 */


import java.io.*;
import java.time.*;
import javafx.collections.*;
import javafx.scene.image.*;
public class CustomerFactory {
	private static final CustomerFactory cf = new CustomerFactory();
	private ObservableList<Customer> model;
	
	private CustomerFactory() {
	
	}

	
	public ObservableList<Customer> getCustomers() {
		/* This is the model that the ListView will use. The factory method
		 * observableArrayList() creates an ObservableList that automatically 
		 */
		return getCustomers();
	}
	
	
}