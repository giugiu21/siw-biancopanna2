package it.uniroma3.siw.repository;

import it.uniroma3.siw.model.Review;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends CrudRepository<Review, Long> {
	
	public boolean existsByAuthorAndTitleAndRatingAndText(String author,String title,Integer rating, String text);
    public boolean findByAuthor(String author);
}
