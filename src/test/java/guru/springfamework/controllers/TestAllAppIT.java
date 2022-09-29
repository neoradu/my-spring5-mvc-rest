package guru.springfamework.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


import guru.springfamework.bootstrap.BootstrapData;
import guru.springfamework.domain.Customer;
import guru.springfamework.repositories.CategoryRepository;
import guru.springfamework.repositories.CustomerRepository;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(OrderAnnotation.class)
public class TestAllAppIT extends AbstractTestControllerTest {
	
	private Customer newCustomer;
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired//This is mandatory during test environment
	public TestAllAppIT(CategoryRepository categoryRepository,
			                    CustomerRepository customerRepository,
			                    CategoryController catController) {
		//this.mockMvc = MockMvcBuilders.standaloneSetup(catController).build();
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
		newCustomer = Customer.builder()
	              .firstName("NewGeorgica")
	              .lastName("NewLastName")
	              .build();	
	}

	
	@Test
	@Order(1)
	public void testGetAllCategories() throws Exception {
		mockMvc.perform(get("/api/v1/categories"))
			   .andExpect(status().isOk())
			   .andExpect(MockMvcResultMatchers.jsonPath("$.categories").isArray())
			   .andExpect(MockMvcResultMatchers.jsonPath("$.categories[0].id").value("1"))
			   .andExpect(MockMvcResultMatchers.jsonPath("$.categories[0].name").value("Fruits"))
			   .andExpect(MockMvcResultMatchers.jsonPath("$.categories[5].name").value("undefined"))
			   .andExpect(MockMvcResultMatchers.jsonPath("$.categories[5].url").value("/api/v1/categories/undefined"))
			   ;
			   
	}
	
	@Test
	@Order(2)
	public void testGetOneCategory() throws Exception {
		mockMvc.perform(get("/api/v1/categories/Fruits"))
			   .andExpect(status().isOk())
			   .andExpect(MockMvcResultMatchers.jsonPath("$.id").value("1"))
			   .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Fruits"));		   
	}
	
	@Test
	@Order(3)
	public void testGetBadCategory() throws Exception {
		mockMvc.perform(get("/api/v1/categories/90"))
			   .andExpect(status().isNotFound())
			   ;
			   
	}
	
	@Test
	@Order(4)
	public void testGetAllCustomers() throws Exception {

	   mockMvc.perform(get("/api/v1/customers").accept(MediaType.APPLICATION_JSON))
              .andExpect(status().isOk())
              .andExpect(MockMvcResultMatchers.jsonPath("$.customers").isArray())
              .andExpect(MockMvcResultMatchers.jsonPath("$.customers[0].firstName").value("Vali"))
              .andExpect(MockMvcResultMatchers.jsonPath("$.customers[0].id").value("1"))
              .andExpect(MockMvcResultMatchers.jsonPath("$.customers[6].id").value("7"))
              .andExpect(MockMvcResultMatchers.jsonPath("$.customers[6].url").value("/api/v1/customers/7"));

	}
	
	@Commit//Specifies the transaction should be committed after the test
	@Test
	@Order(5)
	public void testCreateCustomer() throws Exception {
		mockMvc.perform(post("/api/v1/customers")
					.accept(MediaType.APPLICATION_JSON) 
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(AbstractTestControllerTest.asJsonString(newCustomer)))
	   .andExpect(status().isCreated())
	   .andExpect(MockMvcResultMatchers.jsonPath("$.id").value("8"))
	   .andExpect(MockMvcResultMatchers.jsonPath("$.url").value("/api/v1/customers/8"))
	   .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value(newCustomer.getFirstName()))
	   .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value(newCustomer.getLastName()));		
	}
	
	@Test
	@Order(6)
	public void testGetOneCustomer() throws Exception {
		mockMvc.perform(get("/api/v1/customers/8")
					.accept(MediaType.APPLICATION_JSON))
	   .andExpect(status().isOk())
	   .andExpect(MockMvcResultMatchers.jsonPath("$.id").value("8"))
	   .andExpect(MockMvcResultMatchers.jsonPath("$.url").value("/api/v1/customers/8"))
	   .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value(newCustomer.getFirstName()))
	   .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value(newCustomer.getLastName()));
	}
}
