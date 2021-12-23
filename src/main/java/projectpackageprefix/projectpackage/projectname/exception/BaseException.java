package {{project-package-prefix}}.{{project-package}}.{{project-name}}.exception;

public class BaseException extends RuntimeException
{
	public BaseException(String msg)
	{
		super(msg);
	}
	public BaseException(String msg, Throwable cause)
	{
		super(msg, cause);
	}
}
