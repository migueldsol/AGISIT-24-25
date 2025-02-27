package tecnico.ulisboa.pt.ShoppingCart.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import tecnico.ulisboa.pt.ShoppingCart.order.dto.OrderDto;
import tecnico.ulisboa.pt.ShoppingCart.order.dto.AuthDto;
import tecnico.ulisboa.pt.ShoppingCart.order.dto.AuthUserDto;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;

import org.springframework.beans.factory.annotation.Value;

import java.util.List;

import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/order")
public class OrderController {

    private final Counter orderCounter;

    @Value("${auth.service.url}")
    private String authServiceUrl;

    private final RestTemplate restTemplate = new RestTemplate();

    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    private OrderService orderService;

    public OrderController(MeterRegistry registry) {
        this.orderCounter = Counter.builder("orderCounter").description("Total number of acess to the order controller").register(registry);
    }

    @GetMapping("/{userId}")
    public List<OrderDto> getOrdersByUserId(@RequestHeader("Authorization") String token, @RequestHeader("Username") String username, @PathVariable Integer userId){
        orderCounter.increment();
        if (token != null & !token.isEmpty()) {
            logger.warn("Token: "+ token);

            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", token);
            AuthDto authDto = new AuthDto(token, new AuthUserDto(username));

            HttpEntity<AuthDto> entity = new HttpEntity<>(authDto, headers);

            boolean isValid = restTemplate.postForObject(authServiceUrl, entity, Boolean.class);
            if (isValid) {
                logger.info("User is authenticated");
                return orderService.getOrdersByUserId(userId);
            }
            else {
                logger.error("Invalid login credentials");
                throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Invalid login credentials");
            }
        }
        else {
            logger.error("Invalid login credentials");
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Invalid login credentials");
        }
    }

    @PostMapping()
    public void createOrder(@RequestHeader("Authorization") String token, @RequestHeader("Username") String username, @RequestBody OrderDto orderDto){
        orderCounter.increment();
        if (token != null && !token.isEmpty()) {
            logger.warn("Token: "+token);

            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", token);
            AuthDto authDto = new AuthDto(token, new AuthUserDto(username));

            HttpEntity<AuthDto> entity = new HttpEntity<>(authDto, headers);

            boolean isValid = restTemplate.postForObject(authServiceUrl, entity, Boolean.class);
            if (isValid) {
                logger.info("User is authenticated");
                orderService.createOrder(orderDto);
            }
            else {
                logger.error("Invalid login credentials");
                throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Invalid login credentials");
            }
        }
        else {
            logger.error("Invalid login credentials");
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Invalid login credentials");
        }
    }

}
