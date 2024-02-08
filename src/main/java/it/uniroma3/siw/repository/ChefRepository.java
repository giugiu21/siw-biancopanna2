package it.uniroma3.siw.repository;

import it.uniroma3.siw.model.Chef;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChefRepository extends CrudRepository<Chef, Long> {
	
	public boolean existsByName (String name);

}
