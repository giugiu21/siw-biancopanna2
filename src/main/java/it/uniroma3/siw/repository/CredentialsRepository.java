package it.uniroma3.siw.repository;

import it.uniroma3.siw.model.Credentials;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CredentialsRepository extends CrudRepository<Credentials, Long> {
	
	public Optional<Credentials> findByUsername(String username);
	public boolean existsByUsername(String username);
}
