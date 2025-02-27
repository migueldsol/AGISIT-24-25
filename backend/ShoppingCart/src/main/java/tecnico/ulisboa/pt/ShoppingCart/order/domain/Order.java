package tecnico.ulisboa.pt.ShoppingCart.order.domain;

import jakarta.persistence.*;
import tecnico.ulisboa.pt.ShoppingCart.order.dto.OrderDto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer orderId;

    private Integer userId;

    private LocalDateTime orderDate;

    private BigDecimal totalPrice;

    private List<Integer> productIds = new ArrayList<>();

    public Order(){
    }

    public Order(OrderDto orderDto){
        setUserId(orderDto.getUserId());
        setOrderDate(LocalDateTime.now());
        setTotalPrice(orderDto.getTotalPrice());
        for (Integer productId : orderDto.getProductIds()){
            addProductId(productId);
        }
    }

    public void addProductId(Integer productId){
        productIds.add(productId);
    }

    public void removeProductId(Integer productId){
        productIds.remove(productId);
    }

    public Integer getOrderId(){
        return orderId;
    }

    public List<Integer> getProductIds(){
        return productIds;
    }

    public void setOrderId(Integer orderId){
        this.orderId = orderId;
    }

    public Integer getUserId(){
        return userId;
    }

    public void setUserId(Integer userId){
        this.userId = userId;
    }

    public LocalDateTime getOrderDate(){
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate){
        this.orderDate = orderDate;
    }

    public BigDecimal getTotalPrice(){
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice){
        this.totalPrice = totalPrice;
    }
}

