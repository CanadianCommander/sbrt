package projectpacakgeprefix.projectpackage.projectname;

import projectpacakgeprefix.projectpackage.projectname.repository.BaseRepositoryImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@EnableJpaRepositories(repositoryBaseClass = BaseRepositoryImpl.class)
public class ProjectName
{

	public static void main(String[] args)
	{
		SpringApplication.run(ProjectName.class, args);
	}
}