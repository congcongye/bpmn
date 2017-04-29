package dataService.tableAnalyzer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBState {
	public static void addToDB(String bpmn, String dataObj) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
	         
	        String url = "jdbc:mysql://localhost:3306/" + bpmn + "?useUnicode=true&characterEncoding=utf-8";
	        Connection conn = DriverManager.getConnection(url, "root", "");
	        Statement stat = conn.createStatement();
			ResultSet rs = stat.executeQuery("SELECT TABLE_NAME FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = '".concat(bpmn).concat("'"));
	            while(rs.next()){
	            	if (((String)rs.getObject(1)).equals(DBTable.escape(dataObj))) {
	            		stat.execute("ALTER TABLE `".concat(DBTable.escape(dataObj))
	    						.concat("` add column state varchar(255);\n"));
	            		break;
	            	}
	            }

	        stat.close();
	        conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
