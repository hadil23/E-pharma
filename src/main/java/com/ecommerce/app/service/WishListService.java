package com.ecommerce.app.service;

import com.ecommerce.app.dto.ProductDto;
import com.ecommerce.app.model.User;
import com.ecommerce.app.model.WishList;
import com.ecommerce.app.repository.WishListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class WishListService {

    @Autowired
    WishListRepository wishListRepository;

    @Autowired
    ProductService productService;
    public void createWishList(WishList wishList) {
        wishListRepository.save(wishList);
    }

    public List<ProductDto> getWishlistForUser(User user) {
        final List<WishList> wishLists = wishListRepository.findAllByUserOrderByCreatedDateDesc(user);
        List<ProductDto> productDtos = new ArrayList<>();
        for (WishList wishList : wishLists){
            productDtos.add(productService.getProductDto(wishList.getProduct()));
        }
        return productDtos;
    }
}
