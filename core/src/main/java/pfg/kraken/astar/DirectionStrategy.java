/*
 * Copyright (C) 2013-2018 Pierre-François Gimenez
 * Distributed under the MIT License.
 */

package pfg.kraken.astar;

/**
 * Énumération des différentes stratégies de déplacement pour les trajectoires
 * courbes.
 * 
 * @author pf
 *
 */

public enum DirectionStrategy
{
	FASTEST(true, true), // faire au plus vite
	FORCE_BACK_MOTION(false, true), // forcer la marche arrière
	FORCE_FORWARD_MOTION(true, false); // forcer la marche avant

	private final boolean marcheAvantPossible, marcheArrierePossible;

	/**
	 * Cette direction est-elle possible pour cette stratégie ?
	 * 
	 * @param marcheAvant
	 * @return
	 */
	public boolean isPossible(boolean marcheAvant)
	{
		if(marcheAvant)
			return marcheAvantPossible;

		return marcheArrierePossible;
	}

	private DirectionStrategy(boolean marcheAvantPossible, boolean marcheArrierePossible)
	{
		this.marcheAvantPossible = marcheAvantPossible;
		this.marcheArrierePossible = marcheArrierePossible;
	}

}
