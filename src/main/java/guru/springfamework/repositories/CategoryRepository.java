package guru.springfamework.repositories;

import guru.springfamework.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by jt on 9/24/17.
 */
//uses JpaRepository 
public interface CategoryRepository extends JpaRepository<Category, Long> {
	//spring data will create a method for this by parsing the function name and return type
	Category findByName(String name);
}
