package guru.springfamework.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.LinkedList;
import java.util.List;

import guru.springfamework.api.v1.mapper.CategoryMapper;
import guru.springfamework.api.v1.model.CategoryDTO;
import guru.springfamework.domain.Category;
import guru.springfamework.services.CategoryService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
public class CategoryControllerTest {

	@Mock
	private CategoryService categoryService;
	
	@InjectMocks
	private CategoryController catController;
	
	private MockMvc mocMvc;
	
	private CategoryMapper catMapper = CategoryMapper.INSTANCE;
	private List<CategoryDTO> categories = new LinkedList<>();
	private String categoriesResponseS = "{\"categories\":[{\"id\":0,\"name\":\"Fruits\"}," +
	                                                      "{\"id\":1,\"name\":\"Dried\"}," + 
			                                              "{\"id\":2,\"name\":\"Fresh\"}]}";
	private String categoryResponseS = "{\"id\":0,\"name\":\"Fruits\"}";
	
	@BeforeEach
	public void setUp() {
		mocMvc = MockMvcBuilders.standaloneSetup(catController).build();
		String[] categoriesS = {"Fruits", "Dried", "Fresh"};
		long i =0;
		for(String catName : categoriesS) {
			Category cat = Category.builder()
					               .name(catName)
					               .id(i++).build();
			categories.add(catMapper.categoryToCategoryDTO(cat));
		}
	}
	
	@Test
	public void testFindALL() throws Exception {
		
		when(categoryService.getAllCategories()).thenReturn(categories);
		MvcResult result = mocMvc.perform(get("/api/v1/categories"))
		                         .andExpect(status().isOk())
		                         .andReturn();
		
		assertEquals(categoriesResponseS, result.getResponse().getContentAsString());
	}
	
	@Test
	public void testFindOne() throws Exception {
		
		when(categoryService.getCategoryByName(eq("Fruits"))).thenReturn(categories.get(0));
		
		MvcResult result = mocMvc.perform(get("/api/v1/categories/Fruits"))
		                         .andExpect(status().isOk())
		                         .andReturn();
		
		assertEquals(categoryResponseS, result.getResponse().getContentAsString());
	}
}
