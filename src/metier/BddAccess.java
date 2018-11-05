package metier;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class BddAccess {
	
	private static Connection connection=null;
	
	public BddAccess(){
		try {
			Class.forName ("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String url = "jdbc:mysql://localhost:3306/berixia_challenge";
		try{
		 connection = DriverManager.getConnection(url, "root", "");
		System.out.println(getClass().getName() + ": connecté");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public Connection getConnection(){
		return connection;
	}
}
