package guru.springframework.api.v1.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import guru.springframework.api.v1.model.CategoryDTO;
import guru.springframework.domain.Category;

//https://mapstruct.org/
//https://auth0.com/blog/how-to-automatically-map-jpa-entities-into-dtos-in-spring-boot-using-mapstruct/
@Mapper//https://mapstruct.org/documentation/1.3/api/org/mapstruct/Mapper.html
public interface CategoryMapper {
	CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);
	
	CategoryDTO categoryToCategoryDTO(Category category);
	
	Category categoryDTOtoCategory(CategoryDTO category);

}
