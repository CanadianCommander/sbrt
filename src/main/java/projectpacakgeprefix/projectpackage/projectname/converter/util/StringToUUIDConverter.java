package projectpacakgeprefix.projectpackage.projectname.converter.util;

import projectpacakgeprefix.projectpackage.projectname.converter.AbstractConverter;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class StringToUUIDConverter extends AbstractConverter<String, UUID>
{

	// ==========================================================================
	// AbstractConverter Implementation
	// ==========================================================================

	@Override
	public UUID convert(String from)
	{
		// generated client libraries will send the string "null" when optional parameter set to null. doi.
		if (from == null || from.equals("null"))
		{
			return null;
		}
		return UUID.fromString(from);
	}
}
