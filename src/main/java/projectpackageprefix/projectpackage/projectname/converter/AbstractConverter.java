package {{project-package-prefix}}.{{project-package}}.{{project-name}}.converter;

import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public abstract class AbstractConverter<F,T>
{
	// ==========================================================================
	// Public Methods
	// ==========================================================================

	/**
	 * convert an object from (F) to type (T).
	 * @param from - object to convert from
	 * @return - object of type T.
	 */
	public abstract T convert(F from);

	/**
	 * convert an object from (F) to type (T).
	 * @param from - object to convert from
	 * @return - object of type T.
	 */
	public T convert(Optional<F> from)
	{
		return this.convert(from.orElse(null));
	}

	/**
	 * convert a list of items.
	 * @param from - the type to convert from
	 * @return - a list of the target type
	 */
	public List<T> convert(List<F> from)
	{
		return Optional.ofNullable(from)
		               .map((fromLst) -> fromLst.stream().map(this::convert).collect(Collectors.toList()))
		               .orElse(new ArrayList<>());
	}

	/**
	 * convert a page of items.
	 * @param fromPage - the page to convert from
	 * @return - a page of the traget type
	 */
	public Page<T> convert(Page<F> fromPage)
	{
		return fromPage.map(this::convert);
	}
}
