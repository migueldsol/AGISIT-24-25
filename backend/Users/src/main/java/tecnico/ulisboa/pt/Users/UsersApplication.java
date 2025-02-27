package tecnico.ulisboa.pt.Users;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import tecnico.ulisboa.pt.Users.auth.JwtTokenProvider;

@SpringBootApplication
@EnableJpaRepositories
@EnableTransactionManagement
@EnableJpaAuditing
@EnableScheduling
public class UsersApplication extends SpringBootServletInitializer implements InitializingBean{

	public static void main(String[] args) {
		SpringApplication.run(UsersApplication.class, args);
	}

	@Override
    public void afterPropertiesSet() {
        // Run on startup
        JwtTokenProvider.generateKeys();

    }

}
