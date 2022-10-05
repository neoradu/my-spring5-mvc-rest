package guru.springfamework.api.v1.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.validation.constraints.Pattern;

import io.swagger.v3.oas.annotations.media.Schema;

public class CustomerDTO {
	private Long id;
	@NotNull
	@Size(min = 2, message = "must have atleast 2 characters")
	private String firstName;

	@NotNull
	@Size(min = 2, message = "must have atleast 2 characters")
	@Schema(required = true) // open api anotation
	private String lastName;
	//@Pattern(regexp = "\\b(https?|http)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]")
	private String url;

	public CustomerDTO(Long id, String firstName, String lastName, String url) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.url = url;
	}

	public CustomerDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
		// Not sure this need to be here but is better than in service i think
		this.url = "/api/v1/customers/" + id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}