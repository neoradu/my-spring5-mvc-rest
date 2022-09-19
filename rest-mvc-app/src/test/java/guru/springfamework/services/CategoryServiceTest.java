package guru.springfamework.services;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.LinkedList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import guru.springframework.api.v1.mapper.CategoryMapper;
import guru.springframework.api.v1.model.CategoryDTO;
import guru.springframework.domain.Category;
import guru.springframework.repositories.CategoryRepository;
import guru.springframework.services.CategoryService;
import guru.springframework.services.CategoryServiceImpl;

@SpringBootTest
public class CategoryServiceTest {

	private CategoryMapper catMapper = CategoryMapper.INSTANCE;
	
	@Mock
	private CategoryRepository catRepo;
	
	private CategoryService catService;
	
	private List<Category> categories = new LinkedList<>();
	@BeforeEach
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		
		catService = new CategoryServiceImpl(catRepo, catMapper);
		
		String[] categoriesS = {"Fruits", "Dried", "Fresh", "Exotic", "Nuts", "undefined"};
		
		for(String catName : categoriesS) {
			Category cat = Category.builder().name(catName).build();
			categories.add(cat);
		}
	}
	
	@Test
	public void testFindAll() {
		
		when(catRepo.findAll()).thenReturn(categories);
		
		List<CategoryDTO> retCats = catService.getAllCategories();
		
		assertEquals(categories.size(), retCats.size());
		for(int i = 0; i < categories.size(); i++)
			assertEquals(categories.get(i).getName(), 
					     catMapper.categoryDTOtoCategory(retCats.get(i)).getName());
		
		
	}
}
