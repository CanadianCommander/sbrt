package {{project-package-prefix}}.{{project-package}}.{{project-name}}.api.v1.crud;

import {{project-package-prefix}}.{{project-package}}.{{project-name}}.model.AbstractBaseModel;
import {{project-package-prefix}}.{{project-package}}.{{project-name}}.repository.BaseRepository;
import {{project-package-prefix}}.{{project-package}}.{{project-name}}.service.ApplicationResponseFactory;
import {{project-package-prefix}}.{{project-package}}.{{project-name}}.transfer.AbstractModelInboundTransfer;
import {{project-package-prefix}}.{{project-package}}.{{project-name}}.transfer.ApplicationResponse;
import {{project-package-prefix}}.{{project-package}}.{{project-name}}.converter.AbstractInboundTransferToAbstractModelConverter;
import {{project-package-prefix}}.{{project-package}}.{{project-name}}.converter.AbstractModelToTransferConverter;
import {{project-package-prefix}}.{{project-package}}.{{project-name}}.transfer.AbstractModelTransfer;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

public interface AbstractRController<Model extends AbstractBaseModel, Transfer extends AbstractModelTransfer, InboundTransfer extends AbstractModelInboundTransfer>
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

	// ==========================================================================
	// Public Methods
	// ==========================================================================

	/**
	 * override. This method should return the repository for the model
	 * @return - the repository
	 */
	public BaseRepository<Model, UUID> getModelRepository();

	/**
	 * override. This method should provide the converter to be used to transform Model -> Transfer
	 * @return - the converter
	 */
	public AbstractModelToTransferConverter<Model, Transfer> getModelToTransferConverter();

	/**
	 * override. This method should provide the converter to be used to transform Transfer -> Model
	 * @return - the converter
	 */
	public AbstractInboundTransferToAbstractModelConverter<InboundTransfer, Model> getTransferToModelConverter();
}
