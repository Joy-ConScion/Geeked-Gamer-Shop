package org.yearup.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.yearup.models.CartItem;
import org.yearup.models.Product;
import org.yearup.models.ShoppingCart;
import org.yearup.models.ShoppingCartItem;
import org.yearup.repository.ShoppingCartRepository;

import java.util.List;

@Service
public class ShoppingCartService {
    private final ShoppingCartRepository shoppingCartRepository;
    private final ProductService productService;

    public ShoppingCartService(ShoppingCartRepository shoppingCartRepository, ProductService productService) {
        this.shoppingCartRepository = shoppingCartRepository;
        this.productService = productService;
    }

    public ShoppingCart getByUserId(int userId) {
        ShoppingCart basket = new ShoppingCart();
        List<CartItem> cartItems = shoppingCartRepository.findByUserId(userId);

        for (CartItem cartItem : cartItems) {
            Product product = productService.getById((cartItem.getProductId()));
            ShoppingCartItem shoppingCartItem = new ShoppingCartItem();
            shoppingCartItem.setProduct(product);
            shoppingCartItem.setQuantity(cartItem.getQuantity());

            basket.add(shoppingCartItem);
        }
        return basket;
    }

    public ShoppingCart addToCart(int userId, int productId) {
        CartItem existing = shoppingCartRepository.findByUserIdAndProductId(userId, productId);

        if (existing != null) {
            existing.setQuantity(existing.getQuantity()+1);
            shoppingCartRepository.save(existing);
        }
        else{
            CartItem newItem = new CartItem();
            newItem.setUserId(userId);
            newItem.setProductId(productId);
            newItem.setQuantity(1);
            shoppingCartRepository.save(newItem);
        }
        return getByUserId(userId);
    }

    public ShoppingCart updateCart(int userId, int productId, int quantity) {
        CartItem existing = shoppingCartRepository.findByUserIdAndProductId(userId, productId);

        if (existing != null) {
            existing.setQuantity(quantity);
            shoppingCartRepository.save(existing);
        }
        return getByUserId(userId);
    }

    @Transactional
    public ShoppingCart deleteCart(int userId) {
        shoppingCartRepository.deleteByUserId(userId);
        return new ShoppingCart();
    }

}
