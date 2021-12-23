package {{project-package-prefix}}.{{project-package}}.{{project-name}}.transfer;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(enumAsRef = true)
public enum ApplicationResponseStatusCode
{
	OK,
	ERROR,
	ENTITY_NOT_FOUND,
	REQUEST_FORMAT_ERROR,
	OPERATION_NOT_SUPPORTED,
	MISSING_PARAMETER,
	ILLEGAL_ARGUMENT,
}
