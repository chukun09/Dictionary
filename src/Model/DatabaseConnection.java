package Model;

import java.sql.*;

public class DatabaseConnection {
	public static void FindWord(String input, Connection connection) throws SQLException {
		Statement stat = connection.createStatement();
		ResultSet rs = stat.executeQuery("Select * From tbl_edict Where word like '" + input + "%'");
		while (rs.next()) {
			if (rs.getString(2).equals(input)) {
				System.out.println("index = " + rs.getInt("idx") + "\t word = " + rs.getString("word") + "\t detail = "
						+ rs.getString("detail"));
			}
		}
	}

	public Connection ConnectionData(String name) throws SQLException {
		Connection connection = null;
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			String connectionURL = "jdbc:sqlserver://DESKTOP-8MSKLND\\SQLEXPRESS:1433;databaseName=" + name
					+ ";integratedSecurity=true;";
			connection = DriverManager.getConnection(connectionURL, "sa", "longngoc14121902");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return connection;
	}

	public static void main(String[] args) throws SQLException {
		DatabaseConnection a = new DatabaseConnection();
		Connection connection = a.ConnectionData("DucLong");
		Statement stat = connection.createStatement();
		ResultSet rs = stat.executeQuery("Select * from tbl_edict");
		while (rs.next()) {
			System.out.println("index = " + rs.getInt("idx") + "\t word = " + rs.getString("word") + "\t detail = "
					+ rs.getString("detail"));
		}
		FindWord("account", connection);
	}

}
