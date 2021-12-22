package projectpacakgeprefix.projectpackage.projectname.api.advice;

import projectpacakgeprefix.projectpackage.projectname.transfer.ApplicationResponse;
import projectpacakgeprefix.projectpackage.projectname.service.ApplicationResponseFactory;
import projectpacakgeprefix.projectpackage.projectname.transfer.ApplicationResponseStatusCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

import javax.persistence.EntityNotFoundException;

@Slf4j
@ControllerAdvice
@Order(0)
public class HibernateExceptionAdvice
{
	// ==========================================================================
	// Public Methods
	// ==========================================================================

	@ExceptionHandler({EntityNotFoundException.class, JpaObjectRetrievalFailureException.class})
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public final ResponseEntity<ApplicationResponse<Object>> handleEntityNotFound(EntityNotFoundException e, WebRequest request)
	{
		log.warn(e.getMessage(), e);
		return new ResponseEntity<>(ApplicationResponseFactory.errorResponse(ApplicationResponseStatusCode.ENTITY_NOT_FOUND, e.getMessage(), null),
		                          new HttpHeaders(),
		                          HttpStatus.BAD_REQUEST);
	}

}
