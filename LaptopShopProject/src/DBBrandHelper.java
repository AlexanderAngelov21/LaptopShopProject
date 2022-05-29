import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JTable;


public class DBBrandHelper {
	
	static Connection conn = getConnection();
	static PreparedStatement state = null;
	static MyModel model = null;
	static ResultSet result = null;
	
	static ArrayList<String> brandsList = new ArrayList<String>();
	static ArrayList<String> modelsList = new ArrayList<String>();
	
	
	
	static MyModel getAllData() {
		conn = getConnection();
		String sql = "SELECT ID,BRAND,COUNTRY FROM BRANDS";
		try {
			state = conn.prepareStatement(sql);
			result = state.executeQuery();
			model = new MyModel(result);
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return model;
	}
	
	
	static MyModel getSearchData(String country) {
		//conn = getConnection();
		String sql = "SELECT*FROM BRANDS WHERE COUNTRY = \'" + country + "\'";
		try {
			state = conn.prepareStatement(sql);
			result = state.executeQuery();
			model = new MyModel(result);
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return model;
	}
	
	
	//get Brands------FOR COMBOBOX IN LAPTOPS PANEL
	static ArrayList<String> getBrandData() {
		String sql = "SELECT BRAND FROM BRANDS";
		try {
			state = conn.prepareStatement(sql);
			result = state.executeQuery();
			model = new MyModel(result);
			brandsList = model.getBrands();
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return brandsList;
	}
	
	static ArrayList<String> getModelData() {
		String sql = "SELECT MODEL FROM LAPTOPS";
		try {
			state = conn.prepareStatement(sql);
			result = state.executeQuery();
			model = new MyModel(result);
			
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return modelsList;
	}
	
	
	static Connection getConnection() {
		try {
			File file = new File("D:\\eclipse\\LaptopShopProject\\src\\databaseCredentialsAndPath.txt");
			
			Scanner sc = new Scanner(file);
			String connString = "",username = "",password = "";
			while(sc.hasNextLine()) {
				connString = sc.nextLine().trim();
				username = sc.nextLine().trim();
				password = sc.nextLine().trim();
			}
			Class.forName("org.h2.Driver");
			conn = DriverManager.getConnection(connString, username, password);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return conn;
		

	}
}