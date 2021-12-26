package {{project-package-prefix}}.{{project-package}}.{{project-name}}.api.v1.crud;

import {{project-package-prefix}}.{{project-package}}.{{project-name}}.model.AbstractBaseModel;
import {{project-package-prefix}}.{{project-package}}.{{project-name}}.service.ApplicationResponseFactory;
import {{project-package-prefix}}.{{project-package}}.{{project-name}}.transfer.AbstractModelInboundTransfer;
import {{project-package-prefix}}.{{project-package}}.{{project-name}}.transfer.ApplicationResponse;
import {{project-package-prefix}}.{{project-package}}.{{project-name}}.transfer.AbstractModelTransfer;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

public interface RController<Model extends AbstractBaseModel, Transfer extends AbstractModelTransfer, InboundTransfer extends AbstractModelInboundTransfer>
		extends CRUDControllerBase<Model, Transfer, InboundTransfer>
{

	// ==========================================================================
	// Endpoints
	// ==========================================================================

	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	default ApplicationResponse<Transfer> get(@PathVariable UUID id)
	{
		Model model = this.getModelRepository().getById(id);
		return ApplicationResponseFactory.okResponse(this.getModelToTransferConverter().convert(model));
	}
}
