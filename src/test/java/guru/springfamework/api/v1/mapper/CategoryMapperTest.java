package guru.springfamework.api.v1.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import guru.springfamework.api.v1.model.CategoryDTO;
import guru.springfamework.domain.Category;


@SpringBootTest
public class CategoryMapperTest {
	
	@Autowired
	private CategoryMapper catMap = CategoryMapper.INSTANCE;
		
	@Test
	public void testCategoryMapper() {
		Category testCat = Category.builder()
				   .id(1L)
				   .name("TEST")
				   .build();
		
		CategoryDTO catDto = catMap.categoryToCategoryDTO(testCat);
		
		assertEquals(testCat.getId(), catDto.getId());
		assertEquals(testCat.getName(), catDto.getName());
		
	}
}
