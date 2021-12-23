package {{project-package-prefix}}.{{project-package}}.{{project-name}}.transfer;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
public class AbstractModelTransfer
{
	protected UUID id;
	protected UUID createdByApiClientId;
	protected ZonedDateTime createdAt;
	protected ZonedDateTime updatedAt;
	protected ZonedDateTime deletedAt;
}
