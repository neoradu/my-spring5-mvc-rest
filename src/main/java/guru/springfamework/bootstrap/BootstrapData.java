package guru.springfamework.bootstrap;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import guru.springfamework.domain.Category;
import guru.springfamework.repositories.CategoryRepository;
import lombok.extern.slf4j.Slf4j;



@Slf4j
@Component //Indicates that an annotated class is a "component". Such classes are considered
//as candidates for auto-detection when using annotation-based configuration and classpath scanning
public class BootstrapData implements CommandLineRunner {
	
	final private CategoryRepository categoryRepository;

	public BootstrapData(CategoryRepository categoryRepository) {
		super();
		this.categoryRepository = categoryRepository;
	}

	@Override
	public void run(String... args) throws Exception {
		String[] categories = {"Fruits", "Dried", "Fresh", "Exotic", "Nuts", "undefined"};
		
		for(String catName : categories) {
			Category cat = Category.builder().name(catName).build();
			categoryRepository.save(cat);
		}
		log.debug("Categories loaded");
		//System.out.println("Categories loaded");
		
	}
	

}
