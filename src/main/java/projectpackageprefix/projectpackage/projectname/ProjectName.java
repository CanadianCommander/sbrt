package {{project-package-prefix}}.{{project-package}}.{{project-name}};

import {{project-package-prefix}}.{{project-package}}.{{project-name}}.repository.BaseRepositoryImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@EnableJpaRepositories(repositoryBaseClass = BaseRepositoryImpl.class)
public class {{ProjectClass}}
{

	public static void main(String[] args)
	{
		SpringApplication.run({{ProjectClass}}.class, args);
	}
}