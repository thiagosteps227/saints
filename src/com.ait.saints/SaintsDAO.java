package com.ait.saints;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import com.mysql.jdbc.PreparedStatement;


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
	
	public Saints findById(int id) {
    	String sql = "SELECT * FROM saints WHERE id=?";
    	Saints saint = null;
    	Connection c = null;
    	try {
    		c = ConnectionHelper.getConnection();
    		PreparedStatement ps = (PreparedStatement) c.prepareStatement(sql);
    		ps.setInt(1, id);
    		ResultSet rs = ps.executeQuery();
    		if(rs.next()) {
    			saint = processRow(rs);
    		}
    	} catch (Exception e) {
    		e.printStackTrace();
    		throw new RuntimeException(e);
    	} finally {
    		ConnectionHelper.close(c);
    	}
    	return saint;
    }
	
	 public List<Saints> findByName(String name) {
	    	List<Saints> list = new ArrayList<Saints>();
	    	Connection c= null;
	    	String sql = "SELECT * FROM saints AS e WHERE UPPER(name) LIKE ? ORDER BY name";
	    	try {
	    		c = ConnectionHelper.getConnection();
	    		java.sql.PreparedStatement ps = c.prepareStatement(sql);
	    		ps.setString(1, "%"+name.toUpperCase()+"%");
	    		ResultSet rs = ps.executeQuery();
	    		while(rs.next()) {
	    			list.add(processRow(rs));
	    		}
	    	} catch (SQLException e){
	    		e.printStackTrace();
	    		throw new RuntimeException(e);
	    	} finally {
	    		ConnectionHelper.close(c);
	    	}
	    	return list;
	    }
	 
	 public List<Saints> findByCountryAndCentury(String country, String century){
	    	List<Saints> list = new ArrayList<Saints>();
	    	Connection c= null;
	    	String sql = "SELECT * FROM saints AS e WHERE UPPER(country) LIKE ? AND UPPER(century) LIKE ? "
	    			+ "ORDER BY name";
	    	try {
	    		c = ConnectionHelper.getConnection();
	    		PreparedStatement ps = (PreparedStatement) c.prepareStatement(sql);
	    		ps.setString(1, "%"+country.toUpperCase()+"%");
	    		ps.setString(2, "%"+century.toUpperCase()+"%");
	    		ResultSet rs = ps.executeQuery();
	    		while(rs.next()) {
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
	 
	 public Saints create(Saints saint) {
	    	Connection c= null;
	    	java.sql.PreparedStatement ps = null;
	    	try {
	    		c = ConnectionHelper.getConnection();
	    		ps = c.prepareStatement("INSERT INTO saints "
	    				+ "(name, country, city, picture, description, century)"
	    				+ "VALUES(?,?,?,?,?,?)", 
	    			new String[] { "id"});
	    		ps.setString(1, saint.getName());
	    		ps.setString(2, saint.getCountry());
	    		ps.setString(3, saint.getCity());
	    		ps.setString(4, saint.getPicture());
	    		ps.setString(5, saint.getDescription());
	    		ps.setInt(6, saint.getCentury());
	    		ps.executeUpdate();
	    		ResultSet rs = ps.getGeneratedKeys();
	    		rs.next();
	    		int id = rs.getInt(1);
	    		saint.setId(id);
	    	} catch (Exception e){
	    		e.printStackTrace();
	    		throw new RuntimeException(e);
	    	} finally {
	    		ConnectionHelper.close(c);
	    	}
	    	
	    	return saint;
	    }
	 
	 public Saints update(Saints saint) {
	    	Connection c = null;
	    	try {
	    		c = ConnectionHelper.getConnection();
	    		PreparedStatement ps = (PreparedStatement) c.prepareStatement("UPDATE saints SET name=?, country=?, city=?,"
	    				+ "picture=?, description=?, century=? WHERE id=?");
	    		ps.setString(1, saint.getName());
	    		ps.setString(2, saint.getCountry());
	    		ps.setString(3, saint.getCity());
	    		ps.setString(4, saint.getPicture());
	    		ps.setString(5, saint.getDescription());
	    		ps.setInt(6, saint.getCentury());
	    		ps.setInt(7, saint.getId());
	    		ps.executeUpdate();
	    	} catch (SQLException e) {
	    		e.printStackTrace();
	    		throw new RuntimeException (e);
	    	} finally {
	    		ConnectionHelper.close(c);
	    	}
	    	return saint;
	    }
	 
	  public boolean remove(int id) {
	    	Connection c= null;
	    	try {
	    		c = ConnectionHelper.getConnection();
	    		PreparedStatement ps = (PreparedStatement) c.prepareStatement("DELETE FROM saints WHERE id =?");
	    		ps.setInt(1, id);
	    		int count = ps.executeUpdate();
				return count == 1;
	    	} catch (Exception e) {
	    		e.printStackTrace();
	    		throw new RuntimeException (e);
	    	} finally {
	    		ConnectionHelper.close(c);
	    	}
	    }
	
	protected Saints processRow(ResultSet rs) throws SQLException {
        Saints saint = new Saints();
        saint.setId(rs.getInt("id"));
        saint.setName(rs.getString("name"));
        saint.setCountry(rs.getString("country"));
        saint.setCity(rs.getString("city"));
        saint.setCentury(rs.getInt("century"));
        saint.setPicture(rs.getString("picture"));
        saint.setDescription(rs.getString("description"));
        return saint;
    }
}

	
