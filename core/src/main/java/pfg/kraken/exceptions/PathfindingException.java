/*
 * Copyright (C) 2013-2018 Pierre-François Gimenez
 * Distributed under the MIT License.
 */

package pfg.kraken.exceptions;

/**
 * Exception levée par le pathfinding
 * 
 * @author pf
 *
 */

public abstract class PathfindingException extends Exception
{

	private static final long serialVersionUID = -960091158805232282L;

	public PathfindingException()
	{
		super();
	}

	public PathfindingException(String m)
	{
		super(m);
	}

}
