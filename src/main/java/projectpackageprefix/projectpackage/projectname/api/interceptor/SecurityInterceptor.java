package {{project-package-prefix}}.{{project-package}}.{{project-name}}.api.interceptor;

import {{project-package-prefix}}.{{project-package}}.{{project-name}}.context.SecurityContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.context.request.WebRequestInterceptor;

import java.util.UUID;

@Slf4j
@Component
public class SecurityInterceptor implements WebRequestInterceptor
{
	public static final String JWT_HEADER = "Authorization";

	// ==========================================================================
	// Public Methods
	// ==========================================================================

	@Override
	public void preHandle(WebRequest request) throws Exception
	{
		final UUID tenantId = UUID.fromString("7f603e10-6c73-460e-b344-0c286ccc51bc");
		log.error("USING FIXED TENANT ID [" + tenantId + "] FIX ME! FIX ME! FIX ME!");
		final UUID apClientId = UUID.fromString("4c078166-1fdb-48e6-8c26-ce4fe7b19314");
		log.error("USING FIXED API CLIENT ID [" + apClientId + "] FIX ME! FIX ME! FIX ME!");

		SecurityContext.setTenantId(tenantId);
		SecurityContext.setApiClientId(apClientId);
	}

	@Override
	public void postHandle(WebRequest request, ModelMap model) throws Exception
	{
		SecurityContext.clear();
	}

	@Override
	public void afterCompletion(WebRequest request, Exception ex) throws Exception
	{
	}

}
