/*
 * Copyright (C) 2013-2018 Pierre-François Gimenez
 * Distributed under the MIT License.
 */

package pfg.kraken.astar.tentacles;

import pfg.kraken.robot.Cinematique;

/**
 * 
 * @author pf
 *
 */

public interface EndOfTrajectoryCheck
{
	public boolean isArrived(Cinematique endPoint, Cinematique robotPoint);
}
