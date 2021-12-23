package {{project-package-prefix}}.{{project-package}}.{{project-name}}.repository;

import {{project-package-prefix}}.{{project-package}}.{{project-name}}.model.AbstractBaseModel;
import {{project-package-prefix}}.{{project-package}}.{{project-name}}.model.AbstractBaseModel_;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.util.Assert;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.TypedQuery;
import java.util.Optional;
import java.util.UUID;

public class BaseRepositoryImpl<T extends AbstractBaseModel, K extends UUID> extends SimpleJpaRepository<T, K> implements BaseRepository<T, K>
{
	// ==========================================================================
	// Public Methods
	// ==========================================================================

	public BaseRepositoryImpl(JpaEntityInformation entityInformation, EntityManager entityManager)
	{
		super(entityInformation, entityManager);
	}

	public BaseRepositoryImpl(Class domainClass, EntityManager em)
	{
		super(domainClass, em);
	}

	/**
	 * Reimplement getById using structured query.  This allows filters to apply correctly
	 * @param id - the primary key to look for.
	 * @return - the entity
	 */
	@Override
	public T getById(K id)
	{
		Assert.notNull(id, "The given id must not be null!");
		return this.findById(id).orElseThrow( () -> new EntityNotFoundException("Entity with id [" + id.toString() + "] was not found"));
	}

	/**
	 * Reimplement getOne using structured query.  This allows filters to apply correctly
	 * @param id - the primary key to look for.
	 * @return - the entity
	 */
	@Deprecated
	public T getOne(K id)
	{
		return this.getById(id);
	}

	/**
	 * Reimplement findById using structured query. This allows the tenantId filter to apply correctly.
	 * @param primaryKey - the primary key to look for.
	 * @return - the entity
	 */
	@Override
	public Optional<T> findById(K primaryKey)
	{
		TypedQuery<T> typedQuery = this.getQuery(
				(root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(AbstractBaseModel_.id), primaryKey),
				Sort.unsorted());

		return typedQuery.getResultList().stream().findFirst();
	}
}
