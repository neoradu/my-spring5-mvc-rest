package guru.springfamework.controllers;

import static org.junit.Assert.assertEquals;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.JsonNode;

import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import guru.springfamework.api.v1.mapper.CustomerMapper;
import guru.springfamework.bootstrap.BootstrapData;
import guru.springfamework.domain.Customer;
import guru.springfamework.repositories.CategoryRepository;
import guru.springfamework.repositories.CustomerRepository;
import guru.springfamework.services.CustomerService;
import guru.springfamework.services.CustomerServiceImpl;
import lombok.extern.slf4j.Slf4j;

//@SpringBootTest

@DataJpaTest
@Slf4j
public class CustomerControllerIntegrationTest extends AbstractTestControllerTest {
	private static CustomerMapper  customerMapper = CustomerMapper.INSTANCE;

	private CustomerService customerService;
	private CustomerController cusomerController;
	private MockMvc mocMvc;
	private Customer newCustomer;
	
	@Autowired
	public CustomerControllerIntegrationTest(CustomerRepository customerRepository,
			                                 CategoryRepository categoryRepository) {
		super();
		customerService = new CustomerServiceImpl(customerRepository, 
                                                      customerMapper);
		cusomerController = new CustomerController(customerService);
		mocMvc = MockMvcBuilders.standaloneSetup(cusomerController)
								.setControllerAdvice(new RestExceptionHandler())
		                        .build();
		if(categoryRepository.count() == 0) {
			//BootstrapData maybe make this something standard for the tests
			BootstrapData bootstrap = new BootstrapData(categoryRepository,
							                            customerRepository);
			try {
				bootstrap.run((String[])null);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}
	
	@BeforeEach
	public void setUp() {
		newCustomer = Customer.builder()
				              .firstName("NewGeorgica")
				              .lastName("NewLastName")
				              .build();	
	}
	
	@Test
	public void testGetAll() throws Exception {
		
		//MvcResult result =
				mocMvc.perform(get("/api/v1/customers"))
		              .andExpect(status().isOk())
		              .andExpect(MockMvcResultMatchers.jsonPath("$.customers").isArray())
		              .andExpect(MockMvcResultMatchers.jsonPath("$.customers[0].firstName").value("Vali"))
		              .andExpect(MockMvcResultMatchers.jsonPath("$.customers[0].id").value("1"))
		              .andExpect(MockMvcResultMatchers.jsonPath("$.customers[6].id").value("7"))
		              .andExpect(MockMvcResultMatchers.jsonPath("$.customers[6].url").value("/api/v1/customers/7"))
		              .andReturn();
		//log.debug(result.getResponse().getContentAsString());
		//assertEquals(categoriesResponseS, result.getResponse().getContentAsString());
	}
	
	@Test
	public void testCreateCustomer() throws Exception {
		//when(customerService.createCustomer(any())).thenReturn(customers.get(1));
		//MvcResult result = 
				mocMvc.perform(post("/api/v1/customers") 
				                    .contentType(MediaType.APPLICATION_JSON)
				                    .content(AbstractTestControllerTest.asJsonString(newCustomer)))
                	   .andExpect(status().isCreated())
                	   .andExpect(MockMvcResultMatchers.jsonPath("$.id").value("8"))
                	   .andExpect(MockMvcResultMatchers.jsonPath("$.url").value("/api/v1/customers/8"))
                	   .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value(newCustomer.getFirstName()))
                	   .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value(newCustomer.getLastName()))
                	   .andReturn();
		//log.debug(result.getResponse().getContentAsString());
	}
	
	@Test
	public void testGetOne() throws Exception {
		
		mocMvc.perform(post("/api/v1/customers") 
                	      .contentType(MediaType.APPLICATION_JSON)
                          .content(AbstractTestControllerTest.asJsonString(newCustomer)))
					  .andExpect(status().isCreated())
					  .andExpect(MockMvcResultMatchers.jsonPath("$.id").value("9"))
					  .andExpect(MockMvcResultMatchers.jsonPath("$.url").value("/api/v1/customers/9"))
					  .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value(newCustomer.getFirstName()))
					  .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value(newCustomer.getLastName()))
					  .andReturn();
		
		//MvcResult result = // this is id 9 because of testCreateCustomer()
				mocMvc.perform(get("/api/v1/customers/9"))
		              .andExpect(status().isOk())
      				  .andExpect(MockMvcResultMatchers.jsonPath("$.id").value("9"))
      				  .andExpect(MockMvcResultMatchers.jsonPath("$.url").value("/api/v1/customers/9"))
      				  .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value(newCustomer.getFirstName()))
      				  .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value(newCustomer.getLastName()))
      				  .andReturn();
		//log.debug(result.getResponse().getContentAsString());
	}
	@Test
	public void testGetBadUrl() throws Exception {
		
		//MvcResult result =
		mocMvc.perform(get("/api/v1/customers/ggf"))
		              .andExpect(status().isBadRequest());
		//log.debug(result.getResponse().getContentAsString());
	}
	@Test
	public void testGetMissingCustomer() throws Exception {
		
		//MvcResult result =
		mocMvc.perform(get("/api/v1/customers/34"))
		              .andExpect(status().isNotFound());
		//log.debug(result.getResponse().getContentAsString());
	}
	
	@Test
	public void testPutMissingCustomer() throws Exception {
		
		//MvcResult result =
		mocMvc.perform(put("/api/v1/customers/33") 
      	      			.contentType(MediaType.APPLICATION_JSON)
      	      			.content(AbstractTestControllerTest.asJsonString(newCustomer)))
		              .andExpect(status().isNotFound());
		//log.debug(result.getResponse().getContentAsString());
	}
	
	@Test
	public void testPutCustomer() throws Exception {
		
		mocMvc.perform(put("/api/v1/customers/3") 
                	      .contentType(MediaType.APPLICATION_JSON)
                          .content(AbstractTestControllerTest.asJsonString(newCustomer)))
					  .andExpect(status().isOk())
					  .andExpect(MockMvcResultMatchers.jsonPath("$.id").value("3"))
					  .andExpect(MockMvcResultMatchers.jsonPath("$.url").value("/api/v1/customers/3"))
					  .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value(newCustomer.getFirstName()))
					  .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value(newCustomer.getLastName()))
					  .andReturn();
		
		//MvcResult result = // this is id 9 because of testCreateCustomer()
				mocMvc.perform(get("/api/v1/customers/3"))
		              .andExpect(status().isOk())
      				  .andExpect(MockMvcResultMatchers.jsonPath("$.id").value("3"))
      				  .andExpect(MockMvcResultMatchers.jsonPath("$.url").value("/api/v1/customers/3"))
      				  .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value(newCustomer.getFirstName()))
      				  .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value(newCustomer.getLastName()))
      				  .andReturn();
		//log.debug(result.getResponse().getContentAsString());
	}
	
	@Test
	public void testPatchMissingCustomer() throws Exception {
		
		//MvcResult result =
		mocMvc.perform(put("/api/v1/customers/35") 
		      	      .contentType(MediaType.APPLICATION_JSON)
		              .content(AbstractTestControllerTest.asJsonString(newCustomer)))
		              .andExpect(status().isNotFound());
		//log.debug(result.getResponse().getContentAsString());
	}
	
	@Test
	public void testPatchCustomer() throws Exception {
		JsonNode customerJson = AbstractTestControllerTest
				                   .readJsonNode(
					                mocMvc.perform(get("/api/v1/customers/3"))
		              				      .andExpect(status().isOk())
		                                  .andReturn()
		                                  .getResponse()
		                                  .getContentAsString());
		String lastName = customerJson.get("lastName").asText();
		newCustomer.setLastName(null);
		mocMvc.perform(patch("/api/v1/customers/3") 
                	      .contentType(MediaType.APPLICATION_JSON)
                          .content(AbstractTestControllerTest.asJsonString(newCustomer)))
					  .andExpect(status().isOk())
					  .andExpect(MockMvcResultMatchers.jsonPath("$.id").value("3"))
					  .andExpect(MockMvcResultMatchers.jsonPath("$.url").value("/api/v1/customers/3"))
					  .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value(newCustomer.getFirstName()))
					  .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value(lastName))
					  .andReturn();
		
		//MvcResult result = // this is id 9 because of testCreateCustomer()
				mocMvc.perform(get("/api/v1/customers/3"))
		              .andExpect(status().isOk())
      				  .andExpect(MockMvcResultMatchers.jsonPath("$.id").value("3"))
      				  .andExpect(MockMvcResultMatchers.jsonPath("$.url").value("/api/v1/customers/3"))
      				  .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value(newCustomer.getFirstName()))
      				  .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value(lastName))
      				  .andReturn();
		//log.debug(result.getResponse().getContentAsString());
	}
	
	@Test
	public void testDeleteCustomer() throws Exception {
		
		mocMvc.perform(delete("/api/v1/customers/3"))
					  .andExpect(status().isOk());
		
		//MvcResult result =
		mocMvc.perform(get("/api/v1/customers/3"))
		              .andExpect(status().isNotFound());
		//log.debug(result.getResponse().getContentAsString());
	}
}
