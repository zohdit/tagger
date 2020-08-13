package usi.tagger.utilities;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.dbcp.BasicDataSource;


public class DbConnection {

	private static DbConnection datasource;
	private BasicDataSource ds;

	private DbConnection() throws IOException, SQLException,
			PropertyVetoException {
		ds = new BasicDataSource();
				
		ds.setDriverClassName(Utilities.dbDriver);
		ds.setUsername(Utilities.dbUsername);
		ds.setPassword(Utilities.dbPassword);
		ds.setUrl(Utilities.dbAddress);

	}

	public static DbConnection getInstance() throws IOException, SQLException,
			PropertyVetoException {
		if (datasource == null) {
			datasource = new DbConnection();
			return datasource;
		} else {
			return datasource;
		}
	}

	public Connection getConnection() throws SQLException {
		return this.ds.getConnection();
	}

}
