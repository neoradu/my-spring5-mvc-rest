package guru.springfamework.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import guru.springfamework.api.v1.mapper.CategoryMapper;
import guru.springfamework.api.v1.model.CategoryDTO;
import guru.springfamework.domain.Category;
import guru.springfamework.repositories.CategoryRepository;

@Service
public class CategoryServiceImpl implements CategoryService {
	
	final private CategoryMapper mapper;
	final private CategoryRepository repository;
	
	
	public CategoryServiceImpl(CategoryMapper mapper, CategoryRepository repository) {
		super();
		this.mapper = mapper;
		this.repository = repository;
	}

	@Override
	public List<CategoryDTO> getAllCategories() {
		List<Category> categories = repository.findAll();
		
		return categories.stream().map(mapper::categoryToCategoryDTO).collect(Collectors.toList());
	}

	@Override
	public CategoryDTO getCategoryByName(String name) {
		return mapper.categoryToCategoryDTO(repository.findByName(name));
	}

}
