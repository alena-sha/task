package ontravelsolutions.tasks.repo;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableAutoConfiguration
@EntityScan(basePackages = {"ontravelsolutions.tasks.model"})
@EnableJpaRepositories(basePackages = {"ontravelsolutions.tasks.repo"})
@EnableTransactionManagement
public class RepositoryConfiguration {

}
