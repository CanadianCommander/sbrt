package projectpacakgeprefix.projectpackage.projectname;

import projectpacakgeprefix.projectpackage.projectname.api.interceptor.SecurityInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfiguration implements WebMvcConfigurer
{
	// ==========================================================================
	// Public Methods
	// ==========================================================================

	private final SecurityInterceptor tenantInterceptor;

	@Autowired
	public WebConfiguration(SecurityInterceptor tenantInterceptor)
	{
		this.tenantInterceptor = tenantInterceptor;
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry)
	{
		registry.addWebRequestInterceptor(tenantInterceptor);
	}

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")
		        .allowedMethods("HEAD", "GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS");
	}

}
