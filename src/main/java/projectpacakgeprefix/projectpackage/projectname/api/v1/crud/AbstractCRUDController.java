package projectpacakgeprefix.projectpackage.projectname.api.v1.crud;

import projectpacakgeprefix.projectpackage.projectname.repository.BaseRepository;
import projectpacakgeprefix.projectpackage.projectname.transfer.AbstractModelInboundTransfer;
import projectpacakgeprefix.projectpackage.projectname.service.ApplicationResponseFactory;
import projectpacakgeprefix.projectpackage.projectname.transfer.AbstractModelTransfer;
import projectpacakgeprefix.projectpackage.projectname.converter.AbstractInboundTransferToAbstractModelConverter;
import projectpacakgeprefix.projectpackage.projectname.converter.AbstractModelToTransferConverter;
import projectpacakgeprefix.projectpackage.projectname.model.AbstractBaseModel;
import projectpacakgeprefix.projectpackage.projectname.transfer.ApplicationResponse;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

// controller that provides CRUD. Create, Read, Update, Delete
public interface AbstractCRUDController<Model extends AbstractBaseModel, Transfer extends AbstractModelTransfer, InboundTransfer extends AbstractModelInboundTransfer>
		extends AbstractCRUController<Model, Transfer, InboundTransfer>
{
	// ==========================================================================
	// Endpoints
	// ==========================================================================

	@DeleteMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	default ApplicationResponse<Transfer> delete(@PathVariable UUID id)
	{
		Model modelToDelete = this.getModelRepository().getById(id);
		this.getModelRepository().delete(modelToDelete);
		return ApplicationResponseFactory.okResponse(this.getModelToTransferConverter().convert(modelToDelete));
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
