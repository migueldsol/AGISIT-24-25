package tecnico.ulisboa.pt.Product.Dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class QuantityDto {

    @Getter
    @Setter
    private List<List<Integer>> productsAndQuantities;

    public QuantityDto() {
    }

    public QuantityDto( List<List<Integer>> productsAndQuantities) {
        this.productsAndQuantities = productsAndQuantities;
    }
}
