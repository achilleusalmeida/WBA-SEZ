package com.wba.test.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wba.WbaSezApplicationTests;
import com.wba.controllers.WbaInboundController;
import com.wba.dbobjects.WbaRate;
import com.wba.logging.InjectableLogger;

@WebAppConfiguration
@AutoConfigureMockMvc
public class WbaInboundControllerTest extends WbaSezApplicationTests
{
	  @Autowired 
	  private MockMvc mockMvc;
	  
	  @MockBean 
	  private WbaInboundController aWbaInboundController;
	  
	  @Autowired
	  private WebApplicationContext wac;
	  
	  String aBasicAuth = new String("Basic d2JhdXNlcjp3YmFwYXNz");
	  
	  @InjectableLogger
		private static Logger aLogger;
	  
	  @Before
		public void setup() 
	  	{
			this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
		}
	  
	  @Test public void 
	  testWbaInboundController_hello() 
	  throws Exception 
	  {						
		  	mockMvc
		  	  .perform(MockMvcRequestBuilders
		  		.post("/hello")
		  		  .header("Authorization", aBasicAuth)
		  			.contentType(MediaType.ALL_VALUE).content("Achilleus")
		  				.accept(MediaType.ALL_VALUE)).andExpect(status().isOk());
		
	  }	

	  @Test public void 
	  testWbaInboundController_listAllProducts() 
	  throws Exception 
	  {
		//listAllProducts
			mockMvc
			 .perform(get("/listAllProducts")
					.header("Authorization", aBasicAuth))
			 			.andExpect(status().isOk());
	  }	
	  
	  @Test public void 
	  testWbaInboundController_saveProduct() 
	  throws Exception 
	  {			
		  WbaRate lWbaRate = WbaRate
				  				.builder()
				  					.medName("Test")
				  						.build();
		  	mockMvc
		  	  .perform(MockMvcRequestBuilders
		  		.post("/saveProduct")
		  		  .header("Authorization", aBasicAuth)
		  			.contentType(MediaType.APPLICATION_JSON)
		  				.content(asJsonString(lWbaRate)))
						  	.andExpect(status().isOk());
	  }	
	  
	  
	  
	  
	public String 
	asJsonString(final Object obj) 
	{
		aLogger.debug("Beginning of "
				+ "com.wba.test.controllers.WbaInboundControllerTest.asJsonString()");

		String jsonString = null;
		try 
		{
			jsonString = new ObjectMapper().writeValueAsString(obj);
		} 
		catch (JsonProcessingException e) 
		{
			aLogger.error(
					"Exception Occured inside  of "
					+ "com.wba.test.controllers.WbaInboundControllerTest.asJsonString()");
		}
		aLogger.debug("End of com.wba.test.controllers.WbaInboundControllerTest.asJsonString()");
		return jsonString;
	}
	  
}
