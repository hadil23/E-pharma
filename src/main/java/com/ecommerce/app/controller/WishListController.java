package com.ecommerce.app.controller;

import com.ecommerce.app.common.ApiResponse;
import com.ecommerce.app.dto.ProductDto;
import com.ecommerce.app.model.Product;
import com.ecommerce.app.model.User;
import com.ecommerce.app.model.WishList;
import com.ecommerce.app.service.AuthenficationService;
import com.ecommerce.app.service.WishListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/wishlist")
public class WishListController {

    @Autowired
    WishListService wishListService;

    @Autowired
    AuthenficationService authenficationService;

    //Save product as wishList Item

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addToWishList(@RequestBody Product product, @RequestParam("token") String token){

        //Authenticate token
        authenficationService.authenticate(token);

        //find the User
        User user = authenficationService.getUser(token);

        //Save the item in the wishList
        WishList wishList = new WishList();
        wishList.setProduct(product);
        wishList.setUser(user);
        wishList.setCreatedDate(new Date());

        wishListService.createWishList(wishList);

        return new ResponseEntity<>(new ApiResponse(true,"A wishList has been created"), HttpStatus.CREATED);
    }

    //Get all wishlist item from a user

    @GetMapping("/list/{token}")
    public ResponseEntity<List<ProductDto>> getWishlist(@PathVariable("token") String token){

        //Authenticate token
        authenficationService.authenticate(token);

        //find the User
        User user = authenficationService.getUser(token);

        List<ProductDto> productDtos = wishListService.getWishlistForUser(user);

        return new ResponseEntity<>(productDtos, HttpStatus.OK);
    }
}













