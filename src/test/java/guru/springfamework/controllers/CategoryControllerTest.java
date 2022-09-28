package guru.springfamework.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

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
public class CategoryControllerTest {

	@Mock
	private CategoryService categoryService;
	
	@InjectMocks
	private CategoryController catController;
	
	private MockMvc mocMvc;
	
	private CategoryMapper catMapper = CategoryMapper.INSTANCE;
	private List<CategoryDTO> categories = new LinkedList<>();
	
	@BeforeEach
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		mocMvc = MockMvcBuilders.standaloneSetup(catController).build();
		String[] categoriesS = {"Fruits", "Dried", "Fresh"};
		long i = 1;
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
		mocMvc.perform(get("/api/v1/categories").accept(MediaType.APPLICATION_JSON))
		      .andExpect(status().isOk())
			   .andExpect(MockMvcResultMatchers.jsonPath("$.categories").isArray())
			   .andExpect(MockMvcResultMatchers.jsonPath("$.categories[0].id").value("1"))
			   .andExpect(MockMvcResultMatchers.jsonPath("$.categories[0].name").value("Fruits"))
			   .andExpect(MockMvcResultMatchers.jsonPath("$.categories[2].name").value("Fresh"))
			   .andExpect(MockMvcResultMatchers.jsonPath("$.categories[2].url").value("/api/v1/categories/Fresh"));
		

	}
	
	@Test
	public void testFindOne() throws Exception {
		
		when(categoryService.getCategoryByName(eq("Fruits"))).thenReturn(categories.get(0));
		
		mocMvc.perform(get("/api/v1/categories/Fruits").accept(MediaType.APPLICATION_JSON))
		      .andExpect(status().isOk())
			   .andExpect(MockMvcResultMatchers.jsonPath("$.id").value("1"))
			   .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Fruits"))
			   .andExpect(MockMvcResultMatchers.jsonPath("$.url").value("/api/v1/categories/Fruits"));
	}
}
