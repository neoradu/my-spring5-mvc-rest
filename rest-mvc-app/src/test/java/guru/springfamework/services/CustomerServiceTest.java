package guru.springfamework.services;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.LinkedList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import guru.springfamework.api.v1.mapper.CustomerMapper;
import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.domain.Customer;
import guru.springfamework.repositories.CustomerRepository;

@SpringBootTest
public class CustomerServiceTest {

	private CustomerMapper customerMapper = CustomerMapper.INSTANCE;
	
	@Mock
	private CustomerRepository customerRepo;
	
	private CustomerService customerService;
	
	private static List<Customer> customers = new LinkedList<>();
	
	private static final String[] personNames = {"Vali", "Georgel", "Ionel", "Bogdan", "Radu", "Alex", "Vasile"};
	
	@BeforeAll
	public static void initTests() {
		for(int i = 0; i < personNames.length; i++) {
			Customer c = Customer.builder()//using builder pattern, not sure is so much nicer
						   .firstName(personNames[i])
					       .lastName(personNames[personNames.length - i -1])
					       .build();
			customers.add(c);
		}
	}
	
	
	@BeforeEach
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		
		customerService = new CustomerServiceImpl(customerRepo, customerMapper);
		
	}
	
	@Test
	public void testFindAll() {
		
		when(customerRepo.findAll()).thenReturn(customers);
		
		List<CustomerDTO> retCust = customerService.getAllCustomers();
		
		//assertEquals(customers.size(), retCust.size());
		
		for(int i = 0; i < customers.size(); i++) {
			assertEquals(customers.get(i).getFirstName(), 
					     customerMapper.customerDTOToCustomer(retCust.get(i)).getFirstName());
			assertEquals(customers.get(i).getLastName(), 
				         customerMapper.customerDTOToCustomer(retCust.get(i)).getLastName());
		}
	
	}
}
