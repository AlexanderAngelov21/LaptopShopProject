import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;


public class DBLaptopHelper {
	
	static Connection conn = null;
	static PreparedStatement state = null;
	static MyModel model = null;
	static ResultSet result = null;

	
	static MyModel getAllData() {
		conn = getConnection();
		String sql = "SELECT L.LAPTOPID,B.BRAND,L.MODEL,L.YEAROFPRODUCTION,L.PRICE,L.COMMENT\r\n"
				+ "FROM LAPTOPS L JOIN BRANDS B\r\n"
				+ "WHERE L.BRANDID = B.ID\r\n"
				+ "ORDER BY L.MODEL";
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
	
	
	static int getBrandData(String brand) {
		conn = getConnection();
		String sql = "SELECT * "
				+ "FROM BRANDS "
				+ "WHERE BRAND = \'" + brand + "\'";
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
		return Integer.parseInt(model.getValueAt(0, 0).toString());
	}
	
	//------SEARCH DATA AFTER CLICKING SEARCH BUTTON
	static MyModel getSearchData(String price) {
		conn = getConnection();
		String sql = "SELECT L.LAPTOPID,B.BRAND,L.MODEL,L.YEAROFPRODUCTION,L.PRICE,L.COMMENT\r\n"
				+ "FROM LAPTOPS L JOIN BRANDS B\r\n"
				+ "ON L.BRANDID = B.ID\r\n"
				+  " WHERE PRICE > " + Integer.parseInt(price)
					+ " ORDER BY L.MODEL";
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
	
	
	//SEARCH DATA FOR THE LAST TAB
	static MyModel getSearchCriteriaData(String brand,String laptopModel) {
		conn = getConnection();
		String sql = "SELECT LAPTOPS.LAPTOPID,BRANDS.BRAND,LAPTOPS.MODEL,LAPTOPS.YEAROFPRODUCTION,LAPTOPS.PRICE,LAPTOPS.COMMENT\r\n"
				+ "FROM LAPTOPS JOIN BRANDS \r\n"
				+ "ON LAPTOPS.BRANDID = BRANDS.ID\r\n"
				+  " WHERE BRANDS.BRAND = \'" + brand + "\' AND LAPTOPS.MODEL = \'" + laptopModel + "\'" 
					+ " ORDER BY LAPTOPS.MODEL";
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
	
	
	static Connection getConnection() {
		try {
			Class.forName("org.h2.Driver");
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