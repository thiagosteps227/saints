package com.ait.saints;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/saints")
public class SaintsResource {
	
	SaintsDAO saintsDAO = new SaintsDAO();
	
	@GET
	@Produces({MediaType.APPLICATION_JSON})
	public List<Saints> findAll() {
		return saintsDAO.findAll();
	}
}
