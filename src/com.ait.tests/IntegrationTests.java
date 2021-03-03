package com.ait.tests;

import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.core.Response;
import org.apache.commons.httpclient.HttpStatus;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.ait.saints.Saints;
import com.ait.saints.SaintsDAO;
import com.ait.saints.SaintsResource;

class IntegrationTests {
	
	SaintsResource saintsResource;

	@BeforeEach
	void setUp() throws Exception {
		saintsResource = new SaintsResource();
		ResetTable reset = new ResetTable();
		List<Saints> saints = new ArrayList<Saints>();
		Saints saint = new Saints();
		saint.setName("test");
		saint.setCountry("country");
		saint.setCity("city");
		saint.setPicture("saint.jpg");
		saint.setDescription("saint created for test");
		saint.setCentury(1);
		saints.add(saint);
		saint = new Saints();
		saint.setName("test2");
		saint.setCountry("country2");
		saint.setCity("city2");
		saint.setPicture("saint2.jpg");
		saint.setDescription("saint created for test2");
		saint.setCentury(2);
		saints.add(saint);
		reset.resetTable(saints);
		
	}
	
	@Test
	void testGetAllSaints() {
		Response response= saintsResource.findAll();
		List<Saints> list =  (List<Saints>) response.getEntity();
		assertEquals(HttpStatus.SC_OK, response.getStatus());
		assertEquals(2, list.size());
		Saints saint= list.get(0);
		assertEquals("country", saint.getCountry());
		saint=list.get(1);
		assertEquals("test2",saint.getName());
		assertEquals("country2", saint.getCountry());
		assertEquals("city2", saint.getCity());
		assertEquals("saint2.jpg", saint.getPicture());
		assertEquals("saint created for test2", saint.getDescription());

	}
	
	@AfterEach
	void tearDown() throws Exception {
	}

	

}
