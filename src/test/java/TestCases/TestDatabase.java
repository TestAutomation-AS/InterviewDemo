package TestCases;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


import org.junit.Assert;
import org.junit.Test;


public class TestDatabase {
	
	@Test
	public void verifyDatabaseServerIsRunning() throws SQLException, ClassNotFoundException {
		Connection conn = null;
		Class.forName("org.hsqldb.jdbc.JDBCDriver"); 
		conn = DriverManager.getConnection("jdbc:hsqldb:hsql//localhost/", "SA", "");
		Assert.assertFalse(conn.isClosed());
	}
	
	

}
