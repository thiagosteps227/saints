package com.ait.saints;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class SaintsDAO {

	public List<Saints> findAll() {
        List<Saints> list = new ArrayList<Saints>();
        Connection c = null;
    	String sql = "SELECT * FROM saints ORDER BY id";
        try {
            c = ConnectionHelper.getConnection();
            Statement s = c.createStatement();
            ResultSet rs = s.executeQuery(sql);
            while (rs.next()) {
                list.add(processRow(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
		} finally {
			ConnectionHelper.close(c);
		}
        return list;
    }
	
	protected Saints processRow(ResultSet rs) throws SQLException {
        Saints saint = new Saints();
        saint.setId(rs.getInt("id"));
        saint.setName(rs.getString("name"));
        saint.setCountry(rs.getString("country"));
        saint.setCity(rs.getString("city"));
        saint.setYear(rs.getString("year"));
        saint.setPicture(rs.getString("picture"));
        saint.setDescription(rs.getString("description"));
        return saint;
    }
}

	
