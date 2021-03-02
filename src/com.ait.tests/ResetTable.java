package com.ait.tests;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.List;
import com.ait.saints.ConnectionHelper;
import com.ait.saints.Saints;
import com.mysql.jdbc.PreparedStatement;

public class ResetTable {
	
	public void resetTable(List<Saints> saints) throws Exception {
		String driver = null;
		Connection c = null;
		Statement statement = null;
		PreparedStatement ps = null;
		String url;
		try {
			c = ConnectionHelper.getConnection();
            Statement s = c.createStatement();
			String query = "TRUNCATE TABLE saints";
			s.execute(query);
			for (Saints saint : saints) {

				ps = (PreparedStatement) c.prepareStatement("INSERT INTO saints (name, country,city,picture, description, century) VALUES (?,?,?, ?, ?, ?)",
						new String[] { "ID" });
				ps.setString(1, saint.getName());
				ps.setString(2, saint.getCountry());
				ps.setString(3, saint.getCity());
				ps.setString(4, saint.getPicture());
				ps.setString(5, saint.getDescription());
				ps.setInt(6, saint.getCentury());
				ps.executeUpdate();
			}
		} catch (Exception e) {
			throw e;
		}

	}
}
