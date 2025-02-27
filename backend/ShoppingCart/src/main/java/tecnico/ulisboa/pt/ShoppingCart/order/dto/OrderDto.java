package tecnico.ulisboa.pt.ShoppingCart.order.dto;

import tecnico.ulisboa.pt.ShoppingCart.order.domain.Order;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public class OrderDto {

    private Integer orderId;
    private Integer userId;
    private LocalDateTime orderDate;
    private BigDecimal totalPrice;
    private List<Integer> productIds;

    public OrderDto(){}

    public OrderDto(Order order){
        setOrderId(order.getOrderId());
        setUserId(order.getUserId());
        setOrderDate(order.getOrderDate());
        setTotalPrice(order.getTotalPrice());
        setProductIds(order.getProductIds());
    }

    public List<Integer> getProductIds(){
        return productIds;
    }

    public void setProductIds(List<Integer> productIds){
        this.productIds = productIds;
    }

    public Integer getOrderId(){
        return orderId;
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