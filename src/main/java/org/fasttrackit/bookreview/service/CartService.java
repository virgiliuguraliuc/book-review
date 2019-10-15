package org.fasttrackit.bookreview.service;

import org.fasttrackit.bookreview.domain.Book;
import org.fasttrackit.bookreview.domain.Cart;
import org.fasttrackit.bookreview.domain.User;
import org.fasttrackit.bookreview.exception.ResourceNotFoundException;
import org.fasttrackit.bookreview.persistance.CartRepository;
import org.fasttrackit.bookreview.transfer.book.book.BookInCartResponse;
import org.fasttrackit.bookreview.transfer.book.cart.AddBookToCartRequest;
import org.fasttrackit.bookreview.transfer.book.cart.CartResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Iterator;

@Service
public class CartService {

    private static final Logger LOGGER =
            LoggerFactory.getLogger(CartService.class);


    private final UserService userService;
    private final BookService bookService;
    private final CartRepository cartRepository;

    @Autowired
    public CartService(UserService user, BookService bookService, CartRepository cartRepository) {

        this.userService = user;
        this.bookService = bookService;
        this.cartRepository = cartRepository;
    }
    @Transactional
    public void addBookToCart(AddBookToCartRequest request){
        LOGGER.info("Adding book to cart");
        Cart cart = cartRepository.findById(request.getUserId()).orElse(new Cart());
        if(cart.getUser() == null){
            LOGGER.debug(" cart does not exist, retrieving user to create a new cart");
            User user = userService.getUser(request.getUserId());
            cart.setUser(user);
        }
        Book book = bookService.getBook(request.getBookId());
        cart.addToCart(book);
        cartRepository.save(cart);
    }
    @Transactional
    public CartResponse getCart(Long userId) {
        LOGGER.info(" retrieving cart for user " + userId);
        Cart cart = cartRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("there is no cart for user ", "user id", userId));
        CartResponse cartResponse = new CartResponse();
        cartResponse.setId(cart.getId());

        Iterator<Book> iterator = cart.getBooks().iterator();
        while(iterator.hasNext()) {
            Book book = iterator.next();
            BookInCartResponse bookInCartResponse = new BookInCartResponse();
            bookInCartResponse.setId(book.getId());
            bookInCartResponse.setTitle(book.getTitle());
            bookInCartResponse.setPrice(book.getPrice());

            cartResponse.getBooks().add(bookInCartResponse);
        }
        return cartResponse;
    }





}
