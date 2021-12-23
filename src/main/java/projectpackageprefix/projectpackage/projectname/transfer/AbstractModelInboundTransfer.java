package {{project-package-prefix}}.{{project-package}}.{{project-name}}.transfer;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
public class AbstractModelInboundTransfer
{
	protected UUID id;
}
