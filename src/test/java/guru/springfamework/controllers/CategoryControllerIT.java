package guru.springfamework.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import guru.springfamework.bootstrap.BootstrapData;
import guru.springfamework.repositories.CategoryRepository;
import guru.springfamework.repositories.CustomerRepository;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CategoryControllerIT {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired//This is mandatory during test environment
	public CategoryControllerIT(CategoryRepository categoryRepository,
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
	}


	@Test
	public void testGetAllCategories() throws Exception {
		mockMvc.perform(get("/api/v1/categories"))
			   .andExpect(status().isOk())
			   .andExpect(MockMvcResultMatchers.jsonPath("$.categories").isArray())
			   .andExpect(MockMvcResultMatchers.jsonPath("$.categories[0].id").value("1"))
			   .andExpect(MockMvcResultMatchers.jsonPath("$.categories[0].name").value("Fruits"))
			   .andExpect(MockMvcResultMatchers.jsonPath("$.categories[5].name").value("undefined"))
			   .andExpect(MockMvcResultMatchers.jsonPath("$.categories[5].url").value("/api/v1/categories/6"))
			   ;
			   
	}
	
	@Test
	public void testGetOneCategory() throws Exception {
		mockMvc.perform(get("/api/v1/categories/Fruits"))
			   .andExpect(status().isOk())
			   .andExpect(MockMvcResultMatchers.jsonPath("$.id").value("1"))
			   .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Fruits"));		   
	}
	
	@Test
	public void testGetBadCategory() throws Exception {
		mockMvc.perform(get("/api/v1/categories/90"))
			   .andExpect(status().isNotFound())
			   ;
			   
	}
}
