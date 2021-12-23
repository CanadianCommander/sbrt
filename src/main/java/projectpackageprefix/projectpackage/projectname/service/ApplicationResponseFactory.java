package {{project-package-prefix}}.{{project-package}}.{{project-name}}.service;

import {{project-package-prefix}}.{{project-package}}.{{project-name}}.transfer.ApplicationResponse;
import {{project-package-prefix}}.{{project-package}}.{{project-name}}.transfer.ApplicationResponseStatusCode;

public class ApplicationResponseFactory
{
	public static final String OK_MESSAGE = "ok";

	// ==========================================================================
	// Class Methods
	// ==========================================================================

	/**
	 * build a new ok application response. Wrapping data.
	 * @param data - the data to wrap
	 * @param <dataType> - the type of the data being wrapped
	 * @return - an ok application response
	 */
	public static <dataType> ApplicationResponse<dataType> okResponse(dataType data)
	{
		return new ApplicationResponse<>(ApplicationResponseStatusCode.OK, OK_MESSAGE, data);
	}

	/**
	 * build a new ok application response. Wrapping data.
	 * @param data - the data to wrap
	 * @param <dataType> - the type of the data being wrapped
	 * @param message - message to return to the client
	 * @return - an ok application response
	 */
	public static <dataType> ApplicationResponse<dataType> okResponse(dataType data, String message)
	{
		return new ApplicationResponse<>(ApplicationResponseStatusCode.OK, message, data);
	}

	/**
	 * build a new error application response.
	 * @param data - error response body
	 * @param errorMessage - error message to be sent to client
	 * @param <dataType> - type of error response body
	 * @return - the error application response
	 */
	public static <dataType> ApplicationResponse<dataType> errorResponse(ApplicationResponseStatusCode statusCode, String errorMessage, dataType data)
	{
		return new ApplicationResponse<>(statusCode, errorMessage, data);
	}

	public static <dataType> ApplicationResponse<dataType> errorResponse(dataType data, String errorMessage)
	{
		return ApplicationResponseFactory.errorResponse(ApplicationResponseStatusCode.ERROR, errorMessage, data);
	}
}
