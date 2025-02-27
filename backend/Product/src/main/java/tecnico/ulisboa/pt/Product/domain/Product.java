package tecnico.ulisboa.pt.Product.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    @Getter
    @Setter
    private Integer id;

    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private Integer price;

    @Getter
    @Setter
    private Integer quantity;

    public Product() {}

    public Product(String name, Integer price, Integer quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }


}