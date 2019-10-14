package org.fasttrackit.bookreview.web;

import org.fasttrackit.bookreview.service.CartService;
import org.fasttrackit.bookreview.transfer.book.cart.AddBookToCartRequest;
import org.fasttrackit.bookreview.transfer.book.cart.CartResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin
@RestController
@RequestMapping("/carts")
public class CartController {
    private final CartService cartService;
    @Autowired
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }
    @PutMapping("/carts")
    public ResponseEntity addBookToCart(@RequestBody @Valid AddBookToCartRequest request){
        cartService.addBookToCart(request);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CartResponse> getCart(@PathVariable("id") long userId) {
        CartResponse cart = cartService.getCart(userId);
        return new ResponseEntity<>(cart, HttpStatus.OK);
    }

}
