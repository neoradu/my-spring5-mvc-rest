package guru.springframework.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import guru.springframework.api.v1.model.CategoryDTO;
import guru.springframework.api.v1.model.CategoryListDTO;
import guru.springframework.services.CategoryService;

@RequestMapping("/api/v1/categories")
@RestController
public class CategoryController {

	final private CategoryService catService;

	public CategoryController(CategoryService catService) {
		super();
		this.catService = catService;
	}
	
	@GetMapping
	public ResponseEntity<CategoryListDTO> getCategories() {
		return new ResponseEntity<>(new CategoryListDTO(
				                         catService.getAllCategories()), HttpStatus.OK);
		
	}
	
	@GetMapping("/{name}")
	public ResponseEntity<CategoryDTO> getCategory(@PathVariable String name) {
		return new ResponseEntity<>(catService.getCategoryByName(name), HttpStatus.OK);
		
	}
	
}
