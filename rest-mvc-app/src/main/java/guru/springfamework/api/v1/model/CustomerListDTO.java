package guru.springfamework.api.v1.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class CustomerListDTO {

	public List<CustomerDTO> customers;
}