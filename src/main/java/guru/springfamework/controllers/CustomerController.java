package guru.springfamework.controllers;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.api.v1.model.CustomerListDTO;
import guru.springfamework.services.CustomerService;

@Controller
@RequestMapping("/api/v1/customers")
public class CustomerController {
	
	final private CustomerService customerService;

	public CustomerController(CustomerService customerService) {
		super();
		this.customerService = customerService;
	}
	
	@GetMapping
	public ResponseEntity<CustomerListDTO> getCustomers() {	
		return new ResponseEntity<>(new CustomerListDTO(customerService.getAllCustomers()),
				                                        HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<CustomerDTO> getCustomer(@PathVariable String id) {

		return new ResponseEntity<>(customerService.getCustomerById(id),HttpStatus.OK);
	}
	/*@RequestBody
	 * Annotation indicating a method parameter should be bound to the body of
	 *  the web request.
	 * The body of the request is passed through an HttpMessageConverter to
	 * resolve the method argument depending on the content type of the request.
	 * Optionally, automatic validation can be applied by annotating the argument with @Valid.
	 **/
	@PostMapping
	public ResponseEntity<CustomerDTO> createCustomer(@Valid @RequestBody CustomerDTO customerDto) {
		return new ResponseEntity<>(customerService.createCustomer(customerDto), HttpStatus.CREATED);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<CustomerDTO> updateCustomer(@PathVariable String id, 
			                                          @Valid @RequestBody CustomerDTO customerDto) {
		return new ResponseEntity<>(customerService.updateCustomer(id, customerDto), HttpStatus.OK);
	}
	
}
