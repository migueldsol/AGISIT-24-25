package tecnico.ulisboa.pt.Product.Repositories;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import tecnico.ulisboa.pt.Product.Dto.AuthDto;
import tecnico.ulisboa.pt.Product.Dto.AuthUserDto;
import tecnico.ulisboa.pt.Product.Dto.ProductDto;
import tecnico.ulisboa.pt.Product.Dto.QuantityDto;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import tecnico.ulisboa.pt.Product.Exceptions.HEException;
import tecnico.ulisboa.pt.Product.Exceptions.ErrorMessage;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;


@RestController
@RequestMapping("/products")
public class ProductController {

    private final Counter productsCounter;

    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    @Value("${auth.service.url}")
    private String authServiceUrl;

    @Autowired
    ProductService productService;

    @Autowired
    private RestTemplate restTemplate;

    public ProductController(MeterRegistry meterRegistry) {
        productsCounter = Counter.builder("productsCounter").description("Total number of acess to the products controller").register(meterRegistry);
    }

    @GetMapping
    public List<ProductDto> getAllProducts(){
        productsCounter.increment();
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    public ProductDto getProductById(@PathVariable Integer id){
        productsCounter.increment();
        return productService.getProductById(id);
    }

    @PostMapping()
    public void reduceQuantity(@RequestHeader("Authorization") String token, @RequestHeader("Username") String username, @RequestBody QuantityDto productsAndQuantitites){
        productsCounter.increment();
        if (token != null && !token.isEmpty()){
            
            logger.warn("Token: " + token);
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", token);

            AuthDto authDto = new AuthDto(token, new AuthUserDto(username));

            //Check if the user is authenticated
            //Set the headers to the restTemplate

            HttpEntity<AuthDto> request = new HttpEntity<>(authDto, headers);

            boolean isValid = restTemplate.postForObject(authServiceUrl, request, Boolean.class);

            logger.warn("Is valid: " + isValid);
            if (isValid){
                productService.reduceQuantity(productsAndQuantitites);
            }
            else{
                throw new HEException(ErrorMessage.INVALID_LOGIN_CREDENTIALS);
            }            
        }
        else {
            throw new HEException(ErrorMessage.INVALID_LOGIN_CREDENTIALS);
        }
    }

    @GetMapping("/test")
    public String testEndpoint() {
        productsCounter.increment();
        return "Server is running!";
    }

}
