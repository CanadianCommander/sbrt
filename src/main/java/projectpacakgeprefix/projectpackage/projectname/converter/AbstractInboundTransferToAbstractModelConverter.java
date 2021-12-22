package projectpacakgeprefix.projectpackage.projectname.converter;

import projectpacakgeprefix.projectpackage.projectname.model.AbstractBaseModel;
import projectpacakgeprefix.projectpackage.projectname.transfer.AbstractModelInboundTransfer;
import projectpacakgeprefix.projectpackage.projectname.model.AbstractBaseModel_;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.lang.reflect.InvocationTargetException;

public abstract class AbstractInboundTransferToAbstractModelConverter<F extends AbstractModelInboundTransfer, T extends AbstractBaseModel> extends AbstractConverter<F,T>
{

	protected EntityManager entityManager;

	// ==========================================================================
	// Public Methods
	// ==========================================================================

	public AbstractInboundTransferToAbstractModelConverter(EntityManager entityManager)
	{
		this.entityManager = entityManager;
	}

	/**
	 * Provide an implementation for this that returns the class of the model the converter converts to.
	 * @return - the class of the model this converter converts in to.
	 */
	public abstract Class<T> getModelClass();

	/**
	 * override this method to provide conversion from F -> T
	 * @param from - the transfer being converted from
	 * @param model - the model to fill out.
	 * @return - the filled out model
	 */
	public abstract T convertTransferToModel(F from, T model);

	/**
	 * apply the conversion between the base transfer and the base model
	 * @param from - object to convert from
	 * @return
	 */
	@Override
	public T convert(F from)
	{
		T model = null;

		if (from == null)
		{
			return null;
		}

		if (this.entityAlreadyExists(from))
		{
			model = this.loadModelFromDb(from);
		}
		else
		{
			try
			{
				model = this.getModelClass().getDeclaredConstructor().newInstance();
			}
			catch (InvocationTargetException | IllegalAccessException |InstantiationException | NoSuchMethodException e)
			{
				throw new RuntimeException("Failed to construct new instance of [" + this.getModelClass().getName() + "] in transfer to model abstract converter");
			}
		}

		model = this.convertTransferToModel(from, model);
		return model;
	}

	// ==========================================================================
	// Protected Methods
	// ==========================================================================

	/**
	 * check if the entity represented by the transfer (from) already exists or not
	 * @param from - the transfer
	 * @return - true / false
	 */
	protected Boolean entityAlreadyExists(F from)
	{
		return from.getId() != null;
	}

	/**
	 * load model of type T based on the transfer object
	 * @param from - the transfer object
	 * @return - the entity loaded from the DB.
	 */
	protected T loadModelFromDb(F from)
	{
		// look up entity by id generically
		CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
		CriteriaQuery<T> query = cb.createQuery(this.getModelClass());
		Root<T> root = query.from(this.getModelClass());
		query.select(root).where(cb.equal(root.get(AbstractBaseModel_.id), from.getId()));

		return this.entityManager.createQuery(query).getSingleResult();
	}
}
