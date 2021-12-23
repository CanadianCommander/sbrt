package {{project-package-prefix}}.{{project-package}}.{{project-name}}.api.advice;

import {{project-package-prefix}}.{{project-package}}.{{project-name}}.service.ApplicationResponseFactory;
import {{project-package-prefix}}.{{project-package}}.{{project-name}}.transfer.ApplicationResponseStatusCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@Slf4j
@ControllerAdvice
@Order(0)
public class ApplicationExceptionAdvice
{

	@ExceptionHandler(InvalidDataAccessApiUsageException.class)
	public final ResponseEntity<Object> handleInvalidDataAccessApiUsageException(InvalidDataAccessApiUsageException e, WebRequest request)
	{
		log.warn(e.getMessage(), e);
		return new ResponseEntity<>(ApplicationResponseFactory.errorResponse(ApplicationResponseStatusCode.ILLEGAL_ARGUMENT, e.getMessage(), null),
		                            new HttpHeaders(),
		                            HttpStatus.BAD_REQUEST);
	}
}
