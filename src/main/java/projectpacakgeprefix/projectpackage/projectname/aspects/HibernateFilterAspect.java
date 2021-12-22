package projectpacakgeprefix.projectpackage.projectname.aspects;

import projectpacakgeprefix.projectpackage.projectname.context.SecurityContext;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.hibernate.Session;

import java.util.UUID;

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
			final UUID tenantId = SecurityContext.getTenantId();
			if (tenantId != null)
			{
				org.hibernate.Filter filter = ((Session) session).enableFilter("tenantFilter");
				filter.setParameter("tenantId", tenantId);
				((Session) session).enableFilter("whereNotDeleted");
			}
		}
	}

}
