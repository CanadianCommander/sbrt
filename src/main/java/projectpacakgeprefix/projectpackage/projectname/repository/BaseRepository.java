package projectpacakgeprefix.projectpackage.projectname.repository;

import projectpacakgeprefix.projectpackage.projectname.model.AbstractBaseModel;
import projectpacakgeprefix.projectpackage.projectname.model.AbstractBaseModel_;
import org.eclipse.sisu.Nullable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import java.time.ZonedDateTime;
import java.util.UUID;

@NoRepositoryBean
public interface BaseRepository<T extends AbstractBaseModel, K> extends JpaRepository<T, K>, JpaSpecificationExecutor<T>
{
	public final Integer MAX_PAGE_SIZE = 1000;

	// ==========================================================================
	// Specifications
	// ==========================================================================

	/**
	 * where entity created after date time
	 * @param dateTime - the date time
	 * @return - whereCreatedAfter specification
	 */
	default Specification<T> whereCreatedAfter(ZonedDateTime dateTime)
	{
		return (root, query, builder) -> builder.greaterThan(root.get(AbstractBaseModel_.createdAt), dateTime);
	}

	/**
	 * where entites created before the given date time
	 * @param dateTime - the date time
	 * @return - whereCreatedBefore specification
	 */
	default Specification<T> whereCreatedBefore(ZonedDateTime dateTime)
	{
		return (root, query, builder) -> builder.lessThan(root.get(AbstractBaseModel_.createdAt), dateTime);
	}

	/**
	 * where entity created between the specified date times
	 * @param startDate - start date time
	 * @param endDate - end date time
	 * @return - whereCreatedBetween specification
	 */
	default Specification<T> whereCreatedBetween(ZonedDateTime startDate, ZonedDateTime endDate)
	{
		return this.whereCreatedAfter(startDate)
		           .and(this.whereCreatedBefore(endDate));
	}

	/**
	 * where entity created by the specified api client
	 * @param clientId - the client
	 * @return - whereCreatedByApiClient specification
	 */
	default Specification<T> whereCreatedByApiClient(UUID clientId)
	{
		return (root, query, builder) -> builder.equal(root.get(AbstractBaseModel_.creatingApiClientId), clientId);
	}

	// ==========================================================================
	// Public Methods
	// ==========================================================================

	/**
	 * build a page request.
	 * @param page - the page to request
	 * @param pageSize - the size of the pages must be less than MAX_PAGE_SIZE
	 * @param sortBy - [optional (default id)] sort page results by this column
	 * @param ascending - [optional (default true)] should the sort be ascending or descending?
	 */
	default PageRequest buildPageRequest(Integer page, Integer pageSize, @Nullable String sortBy, @Nullable Boolean ascending)
	{
		if (pageSize > MAX_PAGE_SIZE)
		{
			throw new IllegalArgumentException("pageSize of [" + pageSize + "] is greater then max size of [" + MAX_PAGE_SIZE + "]");
		}

		if (ascending == null)
		{
			ascending = true;
		}

		if (sortBy == null)
		{
			sortBy = "id";
		}

		return PageRequest.of(page, pageSize, ascending ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending());
	}
}
