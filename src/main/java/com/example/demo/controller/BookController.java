package com.example.demo.controller;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.demo.entity.Book;
import com.example.demo.repo.BookCollectionRepository;

@RestController
@RequestMapping("api/book")
public class BookController {

    private final BookCollectionRepository repository ;

    public BookController(BookCollectionRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Book> findAll(){
        return repository.findAll();
    }
    
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public void addBook(@RequestBody Book book){
        repository.save(book);
    }
    
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{id}")
    public void updateBook(@RequestBody Book book ,@PathVariable Long id ) {
    	if(!repository.existsById(id)) throw  new ResponseStatusException(HttpStatus.NOT_FOUND); 
    	repository.save(book); 
    }
    
    @GetMapping("/{id}")
    public Book findBook(@PathVariable Long id ) {
    	return repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
    
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id ) {
    	repository.delete(id);
    }
    

    // Search book
    @GetMapping("/search")
    public List<Book> searchBooks(@RequestParam(name = "q") String q) {
        List<Book> books = repository.findAll();
        return books.stream()
                .filter(book ->
                        book.getTitle().toLowerCase().contains(q.toLowerCase()) ||
                                book.getAuthor().toLowerCase().contains(q.toLowerCase()) ||
                                book.getCategory().toLowerCase().contains(q.toLowerCase()))
                .sorted(Comparator.comparing(Book::getTitle))
                .collect(Collectors.toList());
    }
}
