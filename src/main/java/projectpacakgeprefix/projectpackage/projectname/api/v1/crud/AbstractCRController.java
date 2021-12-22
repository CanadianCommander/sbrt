package projectpacakgeprefix.projectpackage.projectname.api.v1.crud;

import projectpacakgeprefix.projectpackage.projectname.model.AbstractBaseModel;
import projectpacakgeprefix.projectpackage.projectname.repository.BaseRepository;
import projectpacakgeprefix.projectpackage.projectname.service.ApplicationResponseFactory;
import projectpacakgeprefix.projectpackage.projectname.transfer.AbstractModelInboundTransfer;
import projectpacakgeprefix.projectpackage.projectname.transfer.ApplicationResponse;
import projectpacakgeprefix.projectpackage.projectname.converter.AbstractInboundTransferToAbstractModelConverter;
import projectpacakgeprefix.projectpackage.projectname.converter.AbstractModelToTransferConverter;
import projectpacakgeprefix.projectpackage.projectname.transfer.AbstractModelTransfer;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.UUID;

public interface AbstractCRController<Model extends AbstractBaseModel, Transfer extends AbstractModelTransfer, InboundTransfer extends AbstractModelInboundTransfer>
		extends AbstractRController<Model, Transfer, InboundTransfer>
{

	// ==========================================================================
	// Endpoints
	// ==========================================================================


	@PostMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	default ApplicationResponse<Transfer> create(@RequestBody InboundTransfer transfer)
	{
		Model newModel = this.getModelRepository().save(this.getTransferToModelConverter().convert(transfer));
		return ApplicationResponseFactory.okResponse(this.getModelToTransferConverter().convert(newModel));
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
