package guru.springfamework.controllers;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.LinkedList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import guru.springfamework.api.v1.mapper.CustomerMapper;
import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.domain.Customer;
import guru.springfamework.services.CustomerService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
public class CustomerControllerTest {
	
	private static CustomerMapper  customerMapper = CustomerMapper.INSTANCE;
	@Mock
	private CustomerService customerService;
	
	@InjectMocks
	private CustomerController customerController;
	
	private static final String[] personNames = {"Vali", "Georgel"};
	private final String customersResponseS = "{\"categories\":[{\"id\":0,\"firstName\":\"Vali\",\"lastName\":\"Georgel\",\"url\":\"/api/v1/customers/0\"},{\"id\":1,\"firstName\":\"Georgel\",\"lastName\":\"Vali\",\"url\":\"/api/v1/customers/1\"}]}";
	private static List<CustomerDTO> customers = new LinkedList<>();
	
	private MockMvc mocMvc = null;
	@BeforeAll
	public static void initTests() {
		for(int i = 0; i < personNames.length; i++) {
			Customer c = Customer.builder()
						   .id(i)
						   .firstName(personNames[i])
					       .lastName(personNames[personNames.length - i -1])
					       .build();
			customers.add(customerMapper.customerToCustomerDTO(c));
		}
	}
	
	@BeforeEach
	public void setUp() {
		mocMvc = MockMvcBuilders.standaloneSetup(customerController)
				                .build();
	}
	
	@Test
	public void testFindALL() throws Exception {
		
		when(customerService.getAllCustomers()).thenReturn(customers);
		MvcResult result = mocMvc.perform(get("/api/v1/customers"))
		                         .andExpect(status().isOk())
		                         .andReturn();
		//log.debug(result.getResponse().getContentAsString());
		assertEquals(customersResponseS, result.getResponse().getContentAsString());
	}
	
	@Test
	public void testFindOne() throws Exception {
		
		when(customerService.getCustomerById(eq("1"))).thenReturn(customers.get(1));
		
		MvcResult result = mocMvc.perform(get("/api/v1/customers/1"))
		                         .andExpect(status().isOk())
		                         .andReturn();
		//log.debug(result.getResponse().getContentAsString());
		assertEquals("{\"id\":1,\"firstName\":\"Georgel\",\"lastName\":\"Vali\",\"url\":\"/api/v1/customers/1\"}",
				     result.getResponse().getContentAsString());
	}
	
}
