package com.ait.tests;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.List;
import com.ait.saints.ConnectionHelper;
import com.ait.saints.Saints;
import com.ait.saints.SaintsDAO;
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
	
	public void recoverData() {
		
		SaintsDAO dao = new SaintsDAO();
		
		dao.remove(1);
		dao.remove(2);
		dao.remove(3);
		
		Saints stPaul = new Saints();
		stPaul.setName("Saint Paul");
		stPaul.setCountry("Turkey");
		stPaul.setCity("Tarsus");
		stPaul.setCentury(1);
		stPaul.setPicture("stpaul.jpg");
		stPaul.setDescription("Paul the Apostle, was a Christian apostle who spread "
				+ "the teachings of Jesus in the first-century world.He founded several "
				+ "Christian communities in Asia Minor and Europe from the mid-30s to the mid-50s AD");
		dao.create(stPaul);
		
		Saints stPat = new Saints();
		stPat.setName("Saint Patrick");
		stPat.setCountry("Ireland");
		stPat.setCity("Roman Britain");
		stPat.setCentury(4);
		stPat.setPicture("stpatrick.jpg");
		stPat.setDescription("Saint Patrick was a fifth-century "
				+ "Romano-British Christian missionary and bishop in Ireland. "
				+ "Known as the Apostle of Ireland, he is the primary patron saint of Ireland");
		dao.create(stPat);
		
		Saints aparecida = new Saints();
		aparecida.setName("Our Lady Aparecida");
		aparecida.setCountry("Brazil");
		aparecida.setCity("Aparecida");
		aparecida.setCentury(18);
		aparecida.setPicture("ourLadyAparecida.jpg");
		aparecida.setDescription("Lady Aparecida -Our Lady Revealed- is a title "
				+ "of the Blessed Virgin Mary in the traditional form associated with the "
				+ "Immaculate Conception associated with a clay");
		dao.create(aparecida);
		
		Saints stBene = new Saints();
		stBene.setName("Saint Benedict");
		stBene.setCountry("Italy");
		stBene.setCity("Nursia");
		stBene.setCentury(5);
		stBene.setPicture("stbenedict.jpg");
		stBene.setDescription("Benedict of Nursia is a Christian saint venerated in the "
				+ "Catholic Church, the Eastern Orthodox Church, the Oriental Orthodox Churches, "
				+ "the Anglican Communion and Old Catholic Churches. He is a patron saint of Europe.");
		dao.create(stBene);
		
		Saints stAnt = new Saints();
		stAnt.setName("Saint Anthony");
		stAnt.setCountry("Italy");
		stAnt.setCity("Padua");
		stAnt.setCentury(12);
		stAnt.setPicture("stanthony.jpg");
		stAnt.setDescription("Known as Saint Anthony of Padua, He was a Portuguese Catholic priest and "
				+ "friar of the Franciscan Order. He was born and raised by a wealthy family in Lisbon, "
				+ "Portugal, and died in Padua, Italy");
		dao.create(stAnt);
		
	}
}
