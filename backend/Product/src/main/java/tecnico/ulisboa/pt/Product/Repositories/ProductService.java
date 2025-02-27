package tecnico.ulisboa.pt.Product.Repositories;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import tecnico.ulisboa.pt.Product.Exceptions.HEException;
import tecnico.ulisboa.pt.Product.Exceptions.ErrorMessage;
import tecnico.ulisboa.pt.Product.Dto.ProductDto;
import tecnico.ulisboa.pt.Product.Dto.QuantityDto;

import tecnico.ulisboa.pt.Product.domain.Product;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static tecnico.ulisboa.pt.Product.Exceptions.ErrorMessage.*;

@Service
public class ProductService {

    private final static Logger logger = LoggerFactory.getLogger(ProductService.class);

    @Autowired
    private ProductRepository productRepository;

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public List<ProductDto> getAllProducts() {
        return productRepository.getAllProducts().stream()
                .map(ProductDto::new)
                .toList();
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public List<ProductDto> getProductsByName(String name) {
        return productRepository.getProductsByName(name).stream()
                .map(ProductDto::new)
                .toList();
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public ProductDto getProductById(Integer productId) {
        if (productId == null) throw new HEException(PRODUCT_NOT_FOUND);

        Product product = productRepository.getProductById(productId);

        if (product == null) {
            throw new HEException(ErrorMessage.PRODUCT_NOT_FOUND, productId);
        }

        return new ProductDto(product);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void reduceQuantity(QuantityDto quantityDto) {
        logger.warn("Reducing quantity of products: " + quantityDto.getProductsAndQuantities());
        List<List<Integer>> productsAndQuantities = quantityDto.getProductsAndQuantities();
         for (List<Integer> productAndQuantity : productsAndQuantities) {
             Integer productId = productAndQuantity.get(0);
             Integer amountToReduce = productAndQuantity.get(1);

             int updatedRows = productRepository.reduceProductQuantity(productId, amountToReduce);
             if (updatedRows == 0) {
                 throw new HEException(ErrorMessage.PRODUCT_QUANTITY_INSUFFICIENT, productId, amountToReduce);
             }
         }
    }
}

