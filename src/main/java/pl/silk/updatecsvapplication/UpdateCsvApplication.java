package pl.silk.updatecsvapplication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import pl.silk.updatecsvapplication.db.repository.UserRepository;

@SpringBootApplication
@EnableJpaRepositories(basePackageClasses = UserRepository.class)
public class UpdateCsvApplication {

    public static void main(String[] args) {
        SpringApplication.run(UpdateCsvApplication.class, args);
    }

}
