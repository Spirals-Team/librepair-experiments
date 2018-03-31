/*
 * Copyright (C) 2013-2018 Pierre-François Gimenez
 * Distributed under the MIT License.
 */

package pfg.kraken.obstacles.container;

import java.util.Collections;
import java.util.Iterator;
import pfg.kraken.obstacles.Obstacle;

/**
 * An empty dynamical obstacles manager
 * @author pf
 *
 */

public class EmptyDynamicObstacles implements DynamicObstacles
{
/*	@Override
	public Iterator<Obstacle> getFutureDynamicObstacles(long date)
	{
		return getCurrentDynamicObstacles();
	}*/

	@Override
	public Iterator<Obstacle> getCurrentDynamicObstacles()
	{
		return Collections.emptyIterator();
	}

}
