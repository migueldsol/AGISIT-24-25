package tecnico.ulisboa.pt.Product;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tecnico.ulisboa.pt.Product.Repositories.ProductRepository;
import tecnico.ulisboa.pt.Product.domain.Product;

@Configuration
public class LoadDatabase {

    @Bean
    CommandLineRunner initDatabase(ProductRepository repository) {

        List<Product> products = repository.getAllProducts();
        if (products.size() > 0){
            return args -> {
                System.out.println("Database already populated with sample products.");
                repository.updateProductQuantity("Chocolate Cake", 10);
                repository.updateProductQuantity("Vanilla Cake", 8);
                repository.updateProductQuantity("Red Velvet Cake", 5);
                repository.updateProductQuantity("Carrot Cake", 15);
                System.out.println("Database has been updated with sample products.");
            };
        }
        return args -> {
            // Adicionar produtos ao banco de dados
            repository.save(new Product("Chocolate Cake", 20, 10));
            repository.save(new Product("Vanilla Cake", 25, 8));
            repository.save(new Product("Red Velvet Cake", 30, 5));
            repository.save(new Product("Carrot Cake", 22, 15));
            System.out.println("Database has been populated with sample products.");
        };
    }
}