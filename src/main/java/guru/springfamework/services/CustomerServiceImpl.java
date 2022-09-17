package guru.springfamework.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import guru.springfamework.api.v1.mapper.CustomerMapper;
import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.domain.Customer;
import guru.springfamework.repositories.CustomerRepository;

@Service
public class CustomerServiceImpl implements CustomerService {
	
	private final CustomerRepository customerRepository;
	private final CustomerMapper customerMapper;

	public CustomerServiceImpl(CustomerRepository customerRepository,
			                   CustomerMapper customerMapper) {
		super();
		this.customerRepository = customerRepository;
		this.customerMapper = customerMapper;
	}

	@Override
	public List<CustomerDTO> getAllCustomers() {

		return customerRepository.findAll()
				                 .stream()
				                 .map(customerMapper::customerToCustomerDTO)
				                 .collect(Collectors.toList());
	}

	@Override
	public CustomerDTO getCustomerById(String id) {
		long idL = Long.valueOf(id);
		return customerMapper.customerToCustomerDTO(customerRepository.getOne(idL));
	}

	@Override
	public CustomerDTO createCustomer(CustomerDTO customer) {
		Customer savedCustomer = customerRepository.saveAndFlush(
				                                      customerMapper.customerDTOToCustomer(
				                                    		                        customer));
		return customerMapper.customerToCustomerDTO(savedCustomer);
	}

}
