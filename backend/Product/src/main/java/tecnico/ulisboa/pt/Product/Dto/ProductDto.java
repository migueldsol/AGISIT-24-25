package tecnico.ulisboa.pt.Product.Dto;

import lombok.Getter;
import lombok.Setter;
import tecnico.ulisboa.pt.Product.domain.Product;

public class ProductDto {
    @Getter
    @Setter
    private int id;

    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private String description;

    @Getter
    @Setter
    private double price;

    @Getter
    @Setter
    private int quantity;

    public ProductDto() {}

    public ProductDto(Product product) {
        this.id = product.getId();
        this.name = product.getName();
        this.price = product.getPrice();
        this.quantity = product.getQuantity();
    }




}
