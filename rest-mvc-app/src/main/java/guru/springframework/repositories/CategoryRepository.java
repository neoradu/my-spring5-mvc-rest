package guru.springframework.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import guru.springframework.domain.Category;

/**
 * Created by jt on 9/24/17.
 */
//uses JpaRepository 
public interface CategoryRepository extends JpaRepository<Category, Long> {
	//spring data will create a method for this by parsing the function name and return type
	Category findByName(String name);
}
