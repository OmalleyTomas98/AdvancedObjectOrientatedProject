package ie.gmit.sw;


import java.time.*;
import javafx.scene.image.*;
/**
 * 
 * 
 * @author Tomas O'malley
 * @version 2.0
 * @since 1.8
 * 
 *  Public record  <b>Runner</b>  *  Customer is a record or a read-only bean class with a constructor  
 *  matches the set of parameters in the record signature and a suite of 
 *  accessor methods.
 */
public record Customer(String name, String  packageName,int modifier ,boolean isAnInterface , int slocMetric ) {
}