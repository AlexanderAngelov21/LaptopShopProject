import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;



public class SaleDB {
	
	static Connection conn = null;
	static PreparedStatement state = null;
	static MyModel model = null;
	static ResultSet result = null;
	
	static MyModel getAllData() {
		conn = getConnection();
		String sql = "SELECT S.SALEID,B.BRAND,"
				+ "L.MODEL,S.SALEDATE,S.FIRSTNAME,"
				+ "S.LASTNAME,S.SALEPRICE,"
				+ "S.DIFFERENCE "
				+ "FROM SALES S JOIN LAPTOPS L "
				+ "ON S.LAPTOPID = L.LAPTOPID "
				+ "JOIN BRANDS B "
				+ "ON L.BRANDID = B.ID";
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
	
	
	static MyModel getSearchData(String firstName) {
		conn = getConnection();
		String sql = "SELECT S.SALEID,B.BRAND,"
				+ "L.MODEL,S.SALEDATE,S.FIRSTNAME,"
				+ "S.LASTNAME,S.SALEPRICE,"
				+ "S.DIFFERENCE "
				+ "FROM SALES S JOIN LAPTOPS L "
				+ "ON S.LAPTOPID = L.LAPTOPID "
				+ "JOIN BRANDS B "
				+ "ON L.BRANDID = B.ID WHERE FIRSTNAME = \'" + firstName + "\'";
		
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
	
	static MyModel getSearchCriteriaData(String brand,String laptopModel,String firstName) {
		conn = getConnection();
		String sql = "SELECT S.SALEID,B.BRAND,"
				+ "L.MODEL,S.SALEDATE,S.FIRSTNAME,"
				+ "S.LASTNAME,S.SALEPRICE,"
				+ "S.DIFFERENCE "
				+ "FROM SALES S JOIN LAPTOPS L "
				+ "ON S.LAPTOPID = L.LAPTOPID "
				+ "JOIN BRANDS B "
				+ "ON L.BRANDID = B.ID WHERE S.FIRSTNAME = \'" + firstName + "\' AND L.MODEL = \'" + laptopModel + "\' AND B.BRAND = \'" + brand + "\'";
		
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
			conn = DriverManager.getConnection("jdbc:h2:C:\\Users\\Asi\\Desktop\\LaptopShopProject\\LaptopShopProjectDB","sa","sa");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		return conn;
	}
}