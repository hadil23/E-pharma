package com.ecommerce.app.controller;


import com.ecommerce.app.common.ApiResponse;
import com.ecommerce.app.dto.cart.AddToCart;
import com.ecommerce.app.dto.cart.CartDto;
import com.ecommerce.app.model.Cart;
import com.ecommerce.app.model.Product;
import com.ecommerce.app.model.User;
import com.ecommerce.app.service.AuthenficationService;
import com.ecommerce.app.service.CartService;
import com.ecommerce.app.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/cart")
public class CartController {
    @Autowired
    CartService cartService;

    @Autowired
    AuthenficationService authenficationService;



    //post cart api

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addToCart(@RequestBody AddToCart addToCart, @RequestParam("token") String token){

        //Authenticate token
        authenficationService.authenticate(token);

        //Find the user
        User user = authenficationService.getUser(token);


        //Save the cart
        cartService.addToCart(addToCart, user);

        return new ResponseEntity<>(new ApiResponse(true,"A cart has bees created"), HttpStatus.CREATED);
    }


    //get all cart item for a user

    @GetMapping("/")
    public ResponseEntity<CartDto> getCartItems(@RequestParam("token") String token){
        //authenticate the token
        authenficationService.authenticate(token);

        //Find the user
        User user = authenficationService.getUser(token);

        CartDto cartDto = cartService.listCartItem(user);

        return new ResponseEntity<>(cartDto,HttpStatus.OK);
    }

    //delete a cart item for a user

    @PostMapping("/delete/{cartItemId}")
    public ResponseEntity<ApiResponse> deleteCartItem(@PathVariable long cartItemId, @RequestParam("token") String token) throws Exception {
        //authenticate the token
        authenficationService.authenticate(token);

        //Find the user
        User user = authenficationService.getUser(token);

        cartService.deleteCartItem(cartItemId, user);

        return new ResponseEntity<>(new ApiResponse(true,"deleted successfully"),HttpStatus.OK);
    }
}
