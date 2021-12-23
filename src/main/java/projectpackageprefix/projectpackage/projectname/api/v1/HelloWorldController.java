package main.java.{{project-package-prefix}}.{{project-package}}.{{project-name}}.api.v1;
import {{project-package-prefix}}.{{project-package}}.{{project-name}}.api.v1.AbstractBaseController;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
public class HelloWorldController extends AbstractBaseController
{
	@GetMapping("/")
	public String helloWorld()
	{
		return "Hello World";
	}
}
