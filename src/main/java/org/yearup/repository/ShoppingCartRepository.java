package org.yearup.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.yearup.models.CartItem;

import java.util.List;

@Repository
public interface ShoppingCartRepository extends JpaRepository<CartItem, Integer>
{
    List<CartItem> findByUserId(int userId);

    @Query("SELECT c FROM CartItem c WHERE c.userId = :userId AND c.productId = :productId")
    CartItem findByUserIdAndProductId(@Param("userId") int userId,
                                      @Param("productId") int productId);

    void deleteByUserId(int userId);
}
