package tecnico.ulisboa.pt.ShoppingCart.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import tecnico.ulisboa.pt.ShoppingCart.order.domain.Order;
import tecnico.ulisboa.pt.ShoppingCart.order.dto.OrderDto;

import java.util.List;

@Service
public class OrderService {
    @Autowired
    OrderRepository orderRepository;

    @Transactional
    public List<OrderDto> getOrdersByUserId(Integer userId){
        return orderRepository.getOrdersByUserId(userId).stream()
                .map(OrderDto::new)
                .toList();
    }

    @Transactional
    public void createOrder(OrderDto orderDto){
        Order order = new Order(orderDto);
        orderRepository.save(order);
    }
}
