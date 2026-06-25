package org.yearup.service;

import org.springframework.stereotype.Service;
import org.yearup.models.CartItem;
import org.yearup.models.Product;
import org.yearup.models.ShoppingCart;
import org.yearup.models.ShoppingCartItem;
import org.yearup.repository.ShoppingCartRepository;

import java.util.List;

@Service
public class ShoppingCartService
{
    // a shopping cart is built from cart rows plus a product lookup for each row
    private final ShoppingCartRepository shoppingCartRepository;
    private final ProductService productService;

    public ShoppingCartService(ShoppingCartRepository shoppingCartRepository, ProductService productService)
    {
        this.shoppingCartRepository = shoppingCartRepository;
        this.productService = productService;
    }

    public ShoppingCart getByUserId(int userId)
    {
        ShoppingCart basket = new ShoppingCart();
        List<CartItem> cartItems = shoppingCartRepository.findByUserId(userId);

        for (CartItem cartItem : cartItems){
            Product product = productService.getById((cartItem.getProductId()));
            ShoppingCartItem shoppingCartItem = new ShoppingCartItem();
            shoppingCartItem.setProduct(product);
            shoppingCartItem.setQuantity(cartItem.getQuantity());
        }
        // load the user's cart rows, look up each product, and build the ShoppingCart
        return basket;
    }

    // add additional methods here
    public ShoppingCart addToCart(ShoppingCart shoppingCart){
    return shoppingCartRepository.save(shoppingCart);
    }

    public ShoppingCart updateCart(int userId, ShoppingCart shoppingCart){
    ShoppingCart existing = shoppingCartRepository.findByUserId(userId).orElseThrow();
    existing.setItems(shoppingCart.getItems());
    return shoppingCartRepository.save(existing);
    }

    /*Is this supposed to delete one item or the whole cart?*/
    public void delete(int userId){
        shoppingCartRepository.deleteById(userId);
    }

}
