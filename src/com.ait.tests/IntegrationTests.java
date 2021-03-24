package com.ait.tests;

import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.core.Response;
import org.apache.commons.httpclient.HttpStatus;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.ait.saints.Saints;
import com.ait.saints.SaintsDAO;
import com.ait.saints.SaintsResource;
import com.ait.validation.ErrorMessage;

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
	
	@Test
	public void testAddSaint() {
		Saints saint = new Saints();
		saint.setName("test3");
		saint.setCountry("country3");
		saint.setCity("city3");
		saint.setPicture("saint3.jpg");
		saint.setDescription("saint created for test3");
		saint.setCentury(1);
		Response response= saintsResource.create(saint);
		assertEquals(HttpStatus.SC_OK, response.getStatus());
		saint = (Saints) response.getEntity();
		assertEquals(saint.getName(), "test3");
		response= (Response) saintsResource.findAll();
		List<Saints> list = (List<Saints>) response.getEntity();
		assertEquals(list.size(), 3);
	}
	
	@Test
	public void testSaintAlreadyExistsException() {
		Saints saint = new Saints();
		saint.setName("test");
		saint.setCountry("country");
		saint.setCity("city");
		saint.setPicture("saint.jpg");
		saint.setDescription("saint created for test");
		saint.setCentury(1);
		Response response= saintsResource.create(saint);
		assertEquals(HttpStatus.SC_FORBIDDEN, response.getStatus());
		ErrorMessage message = (ErrorMessage) response.getEntity();
		assertEquals(message.getErrorMessage(), "The saint with given name already exists");
		response= (Response) saintsResource.findAll();
		List<Saints> list = (List<Saints>) response.getEntity();
		assertEquals(list.size(), 2);
	}
	
	@Test
	public void testAddWineWithInvalidCentury() {
		Saints saint = new Saints();
		saint.setName("test3");
		saint.setCountry("country3");
		saint.setCity("city3");
		saint.setPicture("saint3.jpg");
		saint.setDescription("saint created for test3");
		saint.setCentury(22);
		Response response= saintsResource.create(saint);
		assertEquals(HttpStatus.SC_FORBIDDEN, response.getStatus());
		ErrorMessage message = (ErrorMessage) response.getEntity();
		assertEquals(message.getErrorMessage(), "Type a valid century between 1 and 21");
		response= (Response) saintsResource.findAll();
		List<Saints> list = (List<Saints>) response.getEntity();
		assertEquals(list.size(), 2);
	}
	
	@Test 
	public void testfindById() {
		Response response= saintsResource.findById(1);
		Saints saint =  (Saints) response.getEntity();
		assertEquals(HttpStatus.SC_OK, response.getStatus());
		assertEquals(1, saint.getId());
		
	}
	
	@Test
	public void testFindByName() {
		Response response = saintsResource.findByName("test");
		List<Saints> list = (List<Saints>) response.getEntity();
		assertEquals(HttpStatus.SC_OK, response.getStatus());
		Saints saint = list.get(0);
		assertEquals("test", saint.getName());
	
	}
	
	@Test
	public void testFindByCountryAndCentury() {
		Response response = saintsResource.findByCountryAndCentury("country", 1);
		List<Saints> list = (List<Saints>) response.getEntity();
		assertEquals(HttpStatus.SC_OK, response.getStatus());
		Saints saint = list.get(0);
		assertEquals("country", saint.getCountry());
		assertEquals(1, saint.getCentury());
	
	}
	
	@Test
	public void testUpdateSaint() {
		List<Saints> list = SaintsDAO.findAll();
		Saints saint = list.get(0);
		assertEquals("test", saint.getName());
		saint.setName("updated");
		Response response = saintsResource.update(saint);
		Saints saintUpdated = (Saints) response.getEntity();
		assertEquals(HttpStatus.SC_CREATED, response.getStatus());
		assertEquals("updated", saintUpdated.getName());
	
	}
	
	@Test
	public void testDeleteSaint() {
		Saints saint = SaintsDAO.findById(1);
		assertEquals("test", saint.getName());
		Response response = saintsResource.remove(1);
		assertEquals(HttpStatus.SC_NO_CONTENT, response.getStatus());
			
	}
	
	@AfterAll
	public static void backToNormal(){
		
		ResetTable recovering = new ResetTable();
		recovering.recoverData();

		
	}

	

}
