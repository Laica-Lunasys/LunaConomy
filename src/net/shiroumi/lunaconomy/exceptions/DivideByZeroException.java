package net.shiroumi.lunaconomy.exceptions;

/**
 * Thrown when a specified number can not be zero.
 * 
 * @author Laica-Lunasys (Origin by MjolnirCommando)
 */
@SuppressWarnings("serial")
public class DivideByZeroException extends RuntimeException implements MCException
{
	private String method;
	private String variable;
	
	/**
	 * Creates new DivideByZeroException.
	 * 
	 * @param method
	 * @param variable
	 */
	public DivideByZeroException(String method, String variable)
	{
		super();
		this.method = method;
		this.variable = variable;
	}
	
	public String getMethodCause()
	{
		return method;
	}

	public String getVariableCause()
	{
		return variable;
	}

	public String getErrorDescription()
	{
		return "The specified variable must be not be zero.";
	}

	public DivideByZeroException getError()
	{
		return this;
	}

}
