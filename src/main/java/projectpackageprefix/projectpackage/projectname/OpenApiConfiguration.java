package {{project-package-prefix}}.{{project-package}}.{{project-name}};

import org.apache.commons.lang3.StringUtils;
import org.springdoc.core.customizers.OpenApiCustomiser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
public class OpenApiConfiguration
{
	@Bean
	OpenApiCustomiser operationIdCustomizer() {
		return openApi -> openApi.getPaths().values().stream().flatMap(pathItem -> pathItem.readOperations().stream())
		                         .forEach(operation -> {
															 operation.getTags().stream().findFirst().ifPresent((tag) -> operation.setOperationId(this.buildOperationId(tag, operation.getOperationId())));
		                         });
	}

	// ==========================================================================
	// Protected Methods
	// ==========================================================================

	protected String buildOperationId(String tag, String operationId)
	{
		String camelTag = StringUtils.uncapitalize(Arrays.stream(tag.split("-")).reduce("", (camelCaseTag, tagSegment) -> camelCaseTag + StringUtils.capitalize(tagSegment)));
		String noNumberOperationId = Arrays.stream(operationId.split("_")).findFirst().orElseThrow(() -> new RuntimeException("Failed to generate operation name for operation [" + operationId + "]"));
		return camelTag + StringUtils.capitalize(noNumberOperationId);
	}
}
