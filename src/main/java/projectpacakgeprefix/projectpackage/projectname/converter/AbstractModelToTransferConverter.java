package projectpacakgeprefix.projectpackage.projectname.converter;

import projectpacakgeprefix.projectpackage.projectname.model.AbstractBaseModel;
import projectpacakgeprefix.projectpackage.projectname.transfer.AbstractModelTransfer;

public abstract class AbstractModelToTransferConverter<F extends AbstractBaseModel, T extends AbstractModelTransfer> extends AbstractConverter<F,T>
{
	// ==========================================================================
	// Public Methods
	// ==========================================================================

	/**
	 * override this method to provide conversion from F -> T
	 * @param from - the model being converted from
	 * @return - the new type T transfer
	 */
	public abstract T convertModelToTransfer(F from);

	/**
	 * apply the conversion between the base model and the base transfer
	 * @param from - object to convert from
	 * @return
	 */
	@Override
	public T convert(F from)
	{
		if (from == null)
		{
			return null;
		}

		T transfer = this.convertModelToTransfer(from);

		if (transfer != null)
		{
			transfer.setId(from.getId());
			transfer.setCreatedByApiClientId(from.getCreatingApiClientId());
			transfer.setCreatedAt(from.getCreatedAt());
			transfer.setUpdatedAt(from.getUpdatedAt());
			transfer.setDeletedAt(from.getDeletedAt());
		}

		return transfer;
	}
}
