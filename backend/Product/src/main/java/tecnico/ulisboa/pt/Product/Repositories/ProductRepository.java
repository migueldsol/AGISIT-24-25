package tecnico.ulisboa.pt.Product.Repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import tecnico.ulisboa.pt.Product.domain.Product;

import java.util.List;

@Repository
@Transactional
public interface ProductRepository extends JpaRepository<Product, Integer> {
    @Query("SELECT a FROM Product a WHERE a.name = :productName")
    List<Product> getProductsByName(String productName);

    @Query("SELECT a FROM Product a WHERE a.id = :productId")
    Product getProductById(Integer productId);

    @Query("SELECT a FROM Product a")
    List<Product> getAllProducts();

    @Modifying
    @Transactional
    @Query("UPDATE Product p SET p.quantity = p.quantity - :amount WHERE p.id = :productId AND p.quantity >= :amount")
    int reduceProductQuantity(Integer productId, Integer amount);

    @Modifying
    @Transactional
    @Query("UPDATE Product p SET p.quantity = :quantity WHERE p.name = :productName")
    void updateProductQuantity(String productName, Integer quantity);
}

