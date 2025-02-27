package tecnico.ulisboa.pt.ShoppingCart.order;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import tecnico.ulisboa.pt.ShoppingCart.order.domain.Order;

import java.util.List;


@Repository
@Transactional
public interface OrderRepository extends JpaRepository<Order, Integer> {
    @Query ("SELECT o FROM Order o WHERE o.userId = :userId")
    List<Order> getOrdersByUserId(Integer userId);
}