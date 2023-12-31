package it.uniroma3.siw.repository;
import it.uniroma3.siw.model.Recipe;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RecipeRepository extends CrudRepository<Recipe, Long> {

    public boolean existsByName (String name);

    public List<Recipe> findByName (String name);
    
    @Query(value = "SELECT * FROM recipe order by id desc limit :limit", nativeQuery = true)
	public List<Recipe> findTopN(@Param("limit") int limit);
    
   
}
