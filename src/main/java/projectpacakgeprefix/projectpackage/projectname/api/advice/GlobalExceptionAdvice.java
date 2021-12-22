package projectpacakgeprefix.projectpackage.projectname.api.advice;

import projectpacakgeprefix.projectpackage.projectname.service.ApplicationResponseFactory;
import projectpacakgeprefix.projectpackage.projectname.transfer.ApplicationResponse;
import projectpacakgeprefix.projectpackage.projectname.transfer.ApplicationResponseStatusCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.lang.Nullable;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@ControllerAdvice
@Order(Ordered.LOWEST_PRECEDENCE)
public class GlobalExceptionAdvice extends ResponseEntityExceptionHandler
{
	// ==========================================================================
	// Public Methods
	// ==========================================================================

	/**
	 * catch all for any un handled exception types
	 * @param ex - the uncaught exception
	 * @param request - the request that triggered the exception
	 * @return - error response
	 */
	@ExceptionHandler({Exception.class})
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	protected ResponseEntity<ApplicationResponse<Object>> handleUncaughtException (Exception ex, WebRequest request)
	{
		log.error("Unhandled exception: " + ex.getClass().getName(), ex);
		return new ResponseEntity(ApplicationResponseFactory.errorResponse(null, "An unhandled exception has occurred."), new HttpHeaders(), HttpStatus.BAD_REQUEST);
	}

	/**
	 * format all standard (http) exceptions in general error response
	 * @param ex - the exception
	 * @param body - body to send
	 * @param headers - headers to send
	 * @param status - status code to send
	 * @param request - request that caused the error
	 * @return - error response
	 */
	@Override
	protected ResponseEntity<Object> handleExceptionInternal(Exception ex, @Nullable Object body, HttpHeaders headers, HttpStatus status, WebRequest request)
	{
		log.error("An HTTP exception has occurred.", ex);
		return new ResponseEntity(ApplicationResponseFactory.errorResponse(body, "Some thing went wrong."), headers, status);
	}

	/**
	 * handle http message not readable exception.
	 * @param ex - exception
	 * @param headers - response headers
	 * @param status - status code
	 * @param request - request that caused the error
	 * @return - response to send to client
	 */
	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request)
	{
		log.warn(ex.getMessage(), ex);
		return new ResponseEntity<>(ApplicationResponseFactory.errorResponse(
				ApplicationResponseStatusCode.REQUEST_FORMAT_ERROR, "Bad request format", null), headers, status);
	}

	/**
	 * handle method not supported http error
	 * @param ex - exception
	 * @param headers - response headers
	 * @param status - status code
	 * @param request - request that caused the error
	 * @return - response to send to the client
	 */
	@Override
	protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex, HttpHeaders headers, HttpStatus status, WebRequest request)
	{
		log.warn(ex.getMessage(), ex);
		return new ResponseEntity<>(ApplicationResponseFactory.errorResponse(
				ApplicationResponseStatusCode.OPERATION_NOT_SUPPORTED, "Request not supported", null), headers, status);
	}

	/**
	 * handle missing request param http error
	 * @param ex - exception
	 * @param headers - response headers
	 * @param status - status code
	 * @param request - request that caused the error
	 * @return - response to send to the client
	 */
	protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
		log.warn(ex.getMessage(), ex);
		return new ResponseEntity<>(ApplicationResponseFactory.errorResponse(
				ApplicationResponseStatusCode.MISSING_PARAMETER, "Invalid Request. One or more parameters missing: " + ex.getMessage(), null), headers, status);
	}
}
