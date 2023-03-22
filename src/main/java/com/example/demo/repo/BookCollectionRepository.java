package com.example.demo.repo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.example.demo.entity.Book;

import jakarta.annotation.PostConstruct;

@Repository
public class BookCollectionRepository {

    public final List<Book> books = new ArrayList<>();

    public BookCollectionRepository(){}

    public List<Book> findAll(){
        return books;
    }
    
    public void save(Book book) {
    	books.removeIf(b -> b.getId() == book.getId());
    	books.add(book);
    }
    
    public Optional<Book> findById(Long id) {
		
		return books.stream()
				.filter(b -> b.getId().equals(id)).findFirst();
	}
    
    public boolean existsById(Long id) {
    	return books.stream()
				.filter(b -> b.getId().equals(id)).count() == 1 ;
	}
    
    public void delete(Long id) {
    	books.removeIf(b -> b.getId() == id);
		
	}

    @PostConstruct
    private void init() {
        Book b1 = new Book(1L, "The Great Gatsby" , "F. Scott Fitzgerald" , "Fiction");
        Book b2 = new Book(2L, "To Kill a Mockingbird" , "Harper Lee" , "Fiction");
        Book b3 = new Book(3L, "1984" , "George Orwell" , "Science Fiction");
        books.add(b1);
        books.add(b2);
        books.add(b3);
    }

	

	

	
}
