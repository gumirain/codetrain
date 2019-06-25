package jp.kelonos;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class DataSourceManager {

	public static Connection getConnection() throws  NamingException, SQLException {

		try {
			Context context = new InitialContext();
			DataSource dataSource = (DataSource) context.lookup("java:comp/env/jdbc/CodeTrain");
			return dataSource.getConnection();
		} catch (NamingException e) {
			throw e;
		} catch (SQLException e) {
			throw e;
		}
	}
}
