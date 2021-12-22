package projectpacakgeprefix.projectpackage.projectname.context;

import java.util.UUID;

public class SecurityContext
{
	private static final InheritableThreadLocal<UUID> currentTenantId = new InheritableThreadLocal<>();
	private static final InheritableThreadLocal<UUID> currentApiClientId = new InheritableThreadLocal<>();

	private SecurityContext()
	{
	}

	// ==========================================================================
	// Public Methods
	// ==========================================================================

	public static void clear()
	{
		currentTenantId.remove();
		currentApiClientId.remove();
	}

	// ==========================================================================
	// Getters
	// ==========================================================================

	public static UUID getTenantId()
	{
		return currentTenantId.get();
	}

	public static UUID getApiClientId()
	{
		return currentApiClientId.get();
	}

	// ==========================================================================
	// Setters
	// ==========================================================================

	public static void setTenantId(UUID tenantId)
	{
		currentTenantId.set(tenantId);
	}

	public static void setApiClientId(UUID newApiClientId)
	{
		currentApiClientId.set(newApiClientId);
	}

}
