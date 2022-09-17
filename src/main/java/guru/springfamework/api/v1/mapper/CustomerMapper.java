package guru.springfamework.api.v1.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.domain.Customer;

//https://mapstruct.org/
//https://auth0.com/blog/how-to-automatically-map-jpa-entities-into-dtos-in-spring-boot-using-mapstruct/
@Mapper//https://mapstruct.org/documentation/1.3/api/org/mapstruct/Mapper.html
public interface CustomerMapper {
	CustomerMapper INSTANCE  = Mappers.getMapper(CustomerMapper.class);
	
	CustomerDTO customerToCustomerDTO(Customer customer);
	Customer customerDTOToCustomer(CustomerDTO customerDto);
	
}
