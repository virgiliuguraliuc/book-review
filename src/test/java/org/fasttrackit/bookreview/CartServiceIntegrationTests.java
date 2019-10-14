package org.fasttrackit.bookreview;

import org.fasttrackit.bookreview.domain.Book;
import org.fasttrackit.bookreview.domain.User;
import org.fasttrackit.bookreview.service.CartService;
import org.fasttrackit.bookreview.steps.BookSteps;
import org.fasttrackit.bookreview.steps.UserSteps;
import org.fasttrackit.bookreview.transfer.book.book.BookInCartResponse;
import org.fasttrackit.bookreview.transfer.book.cart.AddBookToCartRequest;
import org.fasttrackit.bookreview.transfer.book.cart.CartResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CartServiceIntegrationTests {
    @Autowired
    private CartService cartService;
    @Autowired
    private UserSteps userSteps;
    @Autowired
    private BookSteps bookSteps;

    @Test
    public void testAddToCart_whenNewCart_thenCreateCart(){
        User user = userSteps.createUser();
        Book book = bookSteps.createBook();

        AddBookToCartRequest request = new AddBookToCartRequest();
        request.setUserId(user.getId());
        request.setBookId(book.getId());

        cartService.addBookToCart(request);

        CartResponse cart = cartService.getCart(user.getId());

        assertThat(cart, notNullValue());
        assertThat(cart.getId(), is(user.getId()));
        assertThat(cart.getBooks(), notNullValue());
        assertThat(cart.getBooks(), hasSize(1));

        BookInCartResponse bookFromCart = cart.getBooks().iterator().next();

        assertThat(bookFromCart, notNullValue());
        assertThat(bookFromCart.getId(), is(request.getBookId()));
        assertThat(bookFromCart.getTitle(), is(book.getTitle()));


    }
}
