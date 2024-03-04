import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import javax.persistence.Entity;

@SpringBootApplication(scanBasePackages = "com.digicert.*")
@EntityScan("com.digicert")
@EnableJpaRepositories
public class UserPortalApplication {

    public static void main(String [] args)
    {
        SpringApplication.run(UserPortalApplication.class,args);
    }
}
