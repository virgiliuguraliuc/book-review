package org.fasttrackit.bookreview.steps;

import org.fasttrackit.bookreview.domain.Book;
import org.fasttrackit.bookreview.transfer.book.book.SaveBookRequest;
import org.fasttrackit.bookreview.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;

@Component
public class BookSteps {
    @Autowired
    private BookService bookService;

    public Book createBook() {
        SaveBookRequest request = new SaveBookRequest();
        request.setAuthor("Computer");
        request.setDescription("computes things");
        request.setTitle("Book1");
        request.setPages(2);
        request.setImagePath("over there");
        request.setType("SelfImprovement");
        request.setLanguage("english");
        request.setYearOfRelease(1998);


        Book book = bookService.createBook(request);

        assertThat(book, notNullValue());
        assertThat(book.getId(), notNullValue());
        assertThat(book.getId(), greaterThan((0L)));
        assertThat(book.getAuthor(), is(request.getAuthor()));
        assertThat(book.getDescription(), is(request.getDescription()));
        assertThat(book.getTitle(), is(request.getTitle()));
        assertThat(book.getPages(), is(request.getPages()));
        assertThat(book.getImagePath(), is(request.getImagePath()));
        assertThat(book.getType(), is(request.getType()));
        assertThat(book.getLanguage(), is(request.getLanguage()));
        assertThat(book.getYearOfRelease(), is(request.getYearOfRelease()));


        return book;}

}
