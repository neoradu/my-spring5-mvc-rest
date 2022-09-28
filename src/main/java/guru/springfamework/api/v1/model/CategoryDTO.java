package guru.springfamework.api.v1.model;

import lombok.Data;

/**
 * Created by jt on 9/24/17.
 */

// we will expose this through the rest controller
@Data //https://auth0.com/blog/how-to-automatically-map-jpa-entities-into-dtos-in-spring-boot-using-mapstruct/
public class CategoryDTO {
    private Long id;
    private String name;
    private String url;
    
    public void setId(long id) {
    	this.id = id;
    	url = "/api/v1/categories/" + id;
    }
}
