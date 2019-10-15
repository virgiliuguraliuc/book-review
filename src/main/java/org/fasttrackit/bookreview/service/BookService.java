package org.fasttrackit.bookreview.service;

import org.fasttrackit.bookreview.domain.Book;
import org.fasttrackit.bookreview.exception.ResourceNotFoundException;
import org.fasttrackit.bookreview.persistance.BookRepository;
import org.fasttrackit.bookreview.transfer.book.book.GetBookRequest;
import org.fasttrackit.bookreview.transfer.book.book.SaveBookRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class BookService {

    private static final Logger LOGGER =
            LoggerFactory.getLogger(BookService.class);

    private final BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Book createBook(SaveBookRequest request) {
        LOGGER.info("Creating book: {}", request);

        Book book = new Book();
        book.setAuthor(request.getAuthor());
        book.setDescription(request.getDescription());
        book.setTitle(request.getTitle());
        book.setLanguage(request.getLanguage());
        book.setPages(request.getPages());
        book.setImagePath(request.getImagePath());
        book.setType(request.getType());
        book.setYearOfRelease(request.getYearOfRelease());
        book.setPrice(request.getPrice());


        return bookRepository.save(book);
    }

    public Book getBook(long id) {
        LOGGER.info("Retrieving product {}", id);
        return bookRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Book ", "not found with id", id  ));
    }

    public Page<Book> getBooks (GetBookRequest request, Pageable pageable){
        LOGGER.info("retrieving products: {}", request);

        if(request != null && request.getpartialTitle() !=null && request.getPartialAuthor() != null) {
            return bookRepository.findByTitleAndAuthor(request.getpartialTitle(), request.getPartialAuthor(), pageable);
        }
        else{
            return bookRepository.findByTitleOrAuthor(request.getPartialAuthor(), pageable);
        }
    }

    public Book updateBook(long id, SaveBookRequest request) {
        LOGGER.info("updating product {}: {}", id, request);

        Book book = getBook(id);
        BeanUtils.copyProperties(request, book);
        return bookRepository.save(book);
    }

    public void deleteBook(long id){
        LOGGER.info("deleting book {}", id);
        bookRepository.deleteById(id);
    }


}
