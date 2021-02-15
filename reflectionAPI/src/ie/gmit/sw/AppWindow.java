package ie.gmit.sw;

import java.io.*;
import java.nio.file.Paths;
import java.time.*;
import java.time.format.*;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

import java.util.Scanner;
import javafx.application.*;
import javafx.beans.property.*;
import javafx.beans.value.*;
import javafx.collections.*;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.control.TableColumn.*;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.stage.*;
import javafx.util.*;
import one.microstream.storage.types.EmbeddedStorage;
import one.microstream.storage.types.EmbeddedStorageManager;


/**
 * 
 * @author Tomas O'Malley
 * @version 2.0
 * @since 1.8
 * 
 *        Public class <b>AppWindow</b> holds the user interface where the user and main
 *        internals of the program meet.
 * 
 *
 */

public class AppWindow extends Application {
	private ObservableList<Customer> customers; //The Model - a list of observers.
	private TableView<Customer> tv; //The View - a composite of GUI components
	private TextField txtFile; //A control, part of the View and a leaf node.
	private EmbeddedStorageManager db=null;

	public void start(Stage stage) throws Exception { 

		/**
	
		 *        Set parameters for UI 
		 */
		
		stage.setTitle("GMIT - B.Sc. in Computing (Software Development)");
		stage.setWidth(800);
		stage.setHeight(600);
		
	
		stage.setOnCloseRequest((e) -> System.exit(0));
		

		VBox box = new VBox();
		box.setPadding(new Insets(10));
		box.setSpacing(8);

		
		/**
		
		 *        **Strategy Pattern**. Configure the Context with a Concrete Strategy 
		 */

		Scene scene = new Scene(box); 
		stage.setScene(scene);

		ToolBar toolBar = new ToolBar(); //A ToolBar is a composite node for Buttons (leaf nodes)

		
		/**
		
		 *        User can exit program 
		 */
		Button btnQuit = new Button("Quit"); //A Leaf node
		btnQuit.setOnAction(e -> System.exit(0)); //Plant an observer on the button
		toolBar.getItems().add(btnQuit); //Add to the parent node and build the tree
		
		
	
		box.getChildren().add(getFileChooserPane(stage)); //Add the sub tree to the main tree
		box.getChildren().add(getTableView()); //Add the sub tree to the main tree
		box.getChildren().add(toolBar); //Add the sub tree to the main tree
		/**
		
		 *      Display UI 
		 */
		stage.show();
		stage.centerOnScreen();
	}

	/**
	 *        void  storeData  stores user data in an instance of micro DB
	 * 
	 *
	 */
	
	void storeData() {
		
		try {
			
			Object root = null;
			db = EmbeddedStorage.start(root, Paths.get("./data"));
			db.storeRoot();
			db.shutdown();
		}
		catch (NoClassDefFoundError | Exception e)
		{
			
			System.out.println("error :" + e);
		}
		
	}
	
	
	
	/**
	 * 
	 * @author Tomas O'Malley
	 * @version 2.0
	 * @since 1.8
	 * 
	 * 
	 * 
	 *        Public class <b>AppWindow</b> holds the user interface where the user and main
	 *        internals of the program meet.
	 * 
	 *
	 */

	private TitledPane getFileChooserPane(Stage stage) throws ClassNotFoundException , NoClassDefFoundError {
		VBox panel = new VBox(); //** A concrete strategy ***

		txtFile = new TextField(); //A leaf node

		
		
		/**
		 *        Allows user to upload a Jar file 
		 * 
		 *
		 */

		
		
		FileChooser fc = new FileChooser(); //A leaf node
		fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("JAR Files", "*.jar"));

		
		
		Button btnOpen = new Button("Select File"); //A leaf node
		btnOpen.setOnAction(e -> { //Plant an observer on the button
			File f = fc.showOpenDialog(stage);
			txtFile.setText(f.getAbsolutePath());
		});

		
		/**
		 *        Program begins to process Jar file 
		 * 
		 *
		 */

		Button btnProcess = new Button("Process"); //A leaf node
		btnProcess.setOnAction(e -> { //Plant an observer on the button
			File f = new File(txtFile.getText());
			System.out.println("[INFO] Processing file " + f.getName());
			
			
			
			String fileName = f.getAbsolutePath(); 
			
			try {
				
				Fileintake(fileName);
				
			} catch (ClassNotFoundException e1)
			{
				e1.printStackTrace();

			} catch (NoClassDefFoundError e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}

		});
		
		ToolBar tb = new ToolBar(); //A composite node
		tb.getItems().add(btnOpen); //Add to the parent node and build a sub tree
		tb.getItems().add(btnProcess); //Add to the parent node and build a sub tree

		panel.getChildren().add(txtFile); //Add to the parent node and build a sub tree
		panel.getChildren().add(tb); //Add to the parent node and build a sub tree

		TitledPane tp = new TitledPane("Select .Jar to Process", panel); //Add to the parent node and build a sub tree
		tp.setCollapsible(false);
		return tp;
	}
	

	/**
	 * 
	 * 
	 *        private  method  <b>getNumLines()</b> Gets the number of lines found inside of the jar file 
	 */
	private int getNumLines(JarInputStream kop) throws FileNotFoundException {
		
		int count =0;
		
		
		
		
		// Parse of the file Read 
		try {
			
			
			Scanner sc = new Scanner(kop);
			
			 while (sc.hasNextLine()) {
				sc.nextLine();
				count++;
				
			 }
			
			
		}catch (Exception e) {
			e.getStackTrace();
		}
		
		
	catch (NoClassDefFoundError e1) {
		e1.printStackTrace();
	}
	
		return count;
	}
	
	/**
	 * 
	 * 
	 *        private  ableView<Customer> getTableView() holds an array list pof type customer and displays instances to UI
	 */
	private TableView<Customer> getTableView() {
		/*
		 * a Model (ObservableList<Customer>). The Model is observable and will 
		 * propagate any changes to it to the View or Views that render it. 
		 */
		tv = new TableView<>(customers); //A TableView is a composite node
		tv.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY); //Stretch columns to fit the window
		
		
		/*
		 *  Create a TableColumn from the class Customer that displays some attribute 
		 *  notify() method in the Observer Pattern.
		 */
		TableColumn<Customer, String> name = new TableColumn<>("Name");
		name.setCellValueFactory(new Callback<CellDataFeatures<Customer, String>, ObservableValue<String>>() {
			public ObservableValue<String> call(CellDataFeatures<Customer, String> p) {
				return new SimpleStringProperty(p.getValue().name());
			}
		});
		
		
		 //*  Create a TableColumn from the class Customer that displays packageName 

		TableColumn<Customer, String> packageName = new TableColumn<>("packageName");
		packageName.setCellValueFactory(new Callback<CellDataFeatures<Customer, String>, ObservableValue<String>>() {
			public ObservableValue<String> call(CellDataFeatures<Customer, String> p) {
				return new SimpleStringProperty(p.getValue().packageName());
			}
		});
		
		
		
		 //*  Create a TableColumn from the class Customer that displays modifer  

		TableColumn<Customer, Number> modifier = new TableColumn<>("modifiers");
		modifier.setCellValueFactory(new Callback<CellDataFeatures<Customer, Number>, ObservableValue<Number>>() {
			public ObservableValue<Number> call(CellDataFeatures<Customer, Number> p) {
				return new SimpleIntegerProperty(p.getValue().modifier());
			}
		});

		 //*  Create a TableColumn from the class Customer that displays isAnInterface  

		TableColumn<Customer, Boolean> isAnInterface = new TableColumn<>("isAnInterface");
		isAnInterface.setCellValueFactory(new Callback<CellDataFeatures<Customer, Boolean>, ObservableValue<Boolean>>() {
			public ObservableValue<Boolean> call(CellDataFeatures<Customer, Boolean> p) {
				return new SimpleBooleanProperty(p.getValue().isAnInterface());
			}
		});

		
		 //*  Create a TableColumn from the class Customer that displays slocMetric  

		TableColumn<Customer, Number> slocMetric = new TableColumn<>("SLOC");
		slocMetric.setCellValueFactory(new Callback<CellDataFeatures<Customer, Number>, ObservableValue<Number>>() {
			public ObservableValue<Number> call(CellDataFeatures<Customer, Number> p) {
				return new SimpleIntegerProperty(p.getValue().slocMetric());
			}
		});

		
		tv.getColumns().add(name); //Add nodes to the tree
		tv.getColumns().add(packageName);  //Add nodes to the tree
		tv.getColumns().add(modifier); //Add nodes to the tree
		tv.getColumns().add(isAnInterface); //Add nodes to the tree
		tv.getColumns().add(slocMetric); //Add nodes to the tree

		return tv;
	}
	

	/**
	 *
	 * 
	 *        Public void <b>Fileintake</b> takes params of file and parses through jar file 
	 *
	 */
	public void Fileintake(String file) throws ClassNotFoundException , IOException ,  NoClassDefFoundError {
		
		
		JarInputStream in = new JarInputStream (new FileInputStream(new File(file.toString())));
		JarEntry next = null;
		
		try {
			
			
			next = in.getNextJarEntry();
		}catch (IOException ioException) {
			
			ioException.printStackTrace();
			
		} 
		
	 catch (NoClassDefFoundError e1) {
		e1.printStackTrace();
	}
		
		while (next != null)
		{
			
			if(next.getName().endsWith(".class")) {
				
				
				String name  = next.getName().replace("/", "\\.");
				name = name.replaceAll(".class", "");
				
				if (!name.contains("$")) name.substring(0, name.length() - ".class".length());
				
				
				try {
					
					Class cls = Class.forName(name);
					String clsName = cls.getName();
					System.out.println("Name:" + clsName);
					Package pack = cls.getPackage();
					System.out.println("Package" + pack);

					boolean iface = cls.isInterface();
					System.out.println("Interface" + iface);
					Class[] interfaces = cls.getInterfaces();
					System.out.println("Interface" + iface);
					
					
				} catch (ClassNotFoundException e)
				{
					e.printStackTrace();
			
			} catch (NoClassDefFoundError e1) {
				e1.printStackTrace();
			}
			
		}
		
	}
  }
}