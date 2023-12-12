package it.uniroma3.siw.repository;
import it.uniroma3.siw.model.Recipe;


import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RecipeRepository extends CrudRepository<Recipe, Long> {

    public boolean existsByName (String name);

    public List<Recipe> findByName (String name);
    
   
    
   
}
