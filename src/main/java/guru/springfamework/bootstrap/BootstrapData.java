package guru.springfamework.bootstrap;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import guru.springfamework.domain.Category;
import guru.springfamework.domain.Customer;
import guru.springfamework.repositories.CategoryRepository;
import guru.springfamework.repositories.CustomerRepository;
import lombok.extern.slf4j.Slf4j;



@Slf4j
@Component //Indicates that an annotated class is a "component". Such classes are considered
//as candidates for auto-detection when using annotation-based configuration and classpath scanning
public class BootstrapData implements CommandLineRunner {
//Spring Boot will run ALL CommandLineRunner beans once the application context is loaded.	
	final private CategoryRepository categoryRepository;
	final private CustomerRepository customerRepository;


	private final String[] categories = {"Fruits", "Dried", "Fresh", "Exotic", "Nuts", "undefined"};
	private final String[] personNames = {"Vali", "Georgel", "Ionel", "Bogdan", "Radu", "Alex", "Vasile"};
	
	public BootstrapData(CategoryRepository categoryRepository,
			             CustomerRepository customerRepository) {
		super();
		this.categoryRepository = categoryRepository;
		this.customerRepository = customerRepository;
	}



	@Override
	public void run(String... args) throws Exception {

		for(String catName : categories) {
			Category cat = Category.builder().name(catName).build();
			categoryRepository.save(cat);
		}
		categoryRepository.flush();
		
		for(int i = 0; i < personNames.length; i++) {
			Customer c = Customer.builder()//using builder pattern, not sure is so much nicer
						   .firstName(personNames[i])
					       .lastName(personNames[personNames.length - i -1])
					       .build();
			customerRepository.save(c);
		}
		customerRepository.flush();
		
		
		
		log.debug("BootStrap Data Complette");
		//System.out.println("Categories loaded");
		
	}
	

}
