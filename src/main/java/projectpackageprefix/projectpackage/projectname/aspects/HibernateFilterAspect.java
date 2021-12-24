package {{project-package-prefix}}.{{project-package}}.{{project-name}}.aspects;

import {{project-package-prefix}}.{{project-package}}.{{project-name}}.context.SecurityContext;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.HibernateException;

import java.util.UUID;

@Slf4j
@Aspect
public class HibernateFilterAspect
{
	@Pointcut("execution (* org.hibernate.internal.SessionFactoryImpl.SessionBuilderImpl.openSession(..))")
	public void openSession()
	{
	}

	@AfterReturning(pointcut = "openSession()", returning = "session")
	public void afterOpenSession(Object session)
	{
		if (session instanceof Session)
		{
			try
			{
				final UUID tenantId = SecurityContext.getTenantId();
				if (tenantId != null)
				{
					org.hibernate.Filter filter = ((Session) session).enableFilter("tenantFilter");
					filter.setParameter("tenantId", tenantId);
				}
				((Session) session).enableFilter("whereNotDeleted");
			}
			catch (HibernateException e)
			{
				// TODO better way to handle this? I don't see any "getFilters" or "hasFilter" methods on Session?
				log.error("Failed to apply global filters. Have you created any models yet? You must create at least one model before global filters can be applied\n" + e.toString());
			}
		}
	}

}
