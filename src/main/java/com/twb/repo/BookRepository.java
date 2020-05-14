package com.twb.repo;

import java.util.List;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

import com.twb.model.Book;

/**
 * @author twb
 *
 */
public interface BookRepository extends CrudRepository<Book, Long> {

    List<Book> findByTitle(String title);

    Optional<Book> findOne(long id);
}
