package dao;

import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DaoUtil {

	public static Connection getConnection() throws URISyntaxException, SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
		Class.forName("com.mysql.jdbc.Driver");
	    
	    String serverName = "localhost:3306";
	    String mydatabase = "gymAgenda";
	    String url = "jdbc:mysql://" + serverName + "/" + mydatabase; 

	    String username = "root";
	    Connection connection = DriverManager.getConnection(url, username, null);

	    return connection;
	}
}
