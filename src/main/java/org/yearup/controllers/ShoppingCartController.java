package org.yearup.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.yearup.models.ShoppingCart;
import org.yearup.models.ShoppingCartItem;
import org.yearup.models.User;
import org.yearup.service.ShoppingCartService;
import org.yearup.service.UserService;

import java.security.Principal;

@CrossOrigin
@RestController
@RequestMapping("/cart")
@PreAuthorize("isAuthenticated()")
public class ShoppingCartController {

    private final ShoppingCartService shoppingCartService;
    private final UserService userService;

    @Autowired
    public ShoppingCartController(
            ShoppingCartService shoppingCartService,
            UserService userService) {

        this.shoppingCartService = shoppingCartService;
        this.userService = userService;
    }

    @GetMapping
    public ShoppingCart getCart(Principal principal) {

        String userName = principal.getName();
        User user = userService.getByUserName(userName);

        return shoppingCartService.getByUserId(user.getId());
    }

    @PostMapping("/products/{productId}")
    public ResponseEntity<ShoppingCart> addToCart(
            Principal principal,
            @PathVariable int productId) {

        String userName = principal.getName();
        User user = userService.getByUserName(userName);

        ShoppingCart cart =
                shoppingCartService.addToCart(user.getId(), productId);

        return ResponseEntity.status(HttpStatus.CREATED).body(cart);
    }

    @PutMapping("/products/{productId}")
    public ShoppingCart updateCart(
            Principal principal,
            @PathVariable int productId,
            @RequestBody ShoppingCartItem item) {

        String userName = principal.getName();
        User user = userService.getByUserName(userName);

        return shoppingCartService.updateCart(
                user.getId(),
                productId,
                item.getQuantity());
    }

    @DeleteMapping
    public ShoppingCart deleteCart(Principal principal) {

        String userName = principal.getName();
        User user = userService.getByUserName(userName);

        return shoppingCartService.deleteCart(user.getId());
    }

}