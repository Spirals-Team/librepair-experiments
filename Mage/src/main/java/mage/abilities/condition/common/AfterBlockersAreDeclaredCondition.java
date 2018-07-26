/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mage.abilities.condition.common;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.constants.PhaseStep;
import mage.game.Game;

/**
 * This condtion does not check that the turnPhase is combat. You have to check
 * this if needed on another place.
 *
 * @author LevelX2
 */
public enum AfterBlockersAreDeclaredCondition implements Condition {

    instance;

    @Override
    public boolean apply(Game game, Ability source) {

        return !(game.getStep().getType() == PhaseStep.BEGIN_COMBAT
                || game.getStep().getType() == PhaseStep.DECLARE_ATTACKERS);
    }

    @Override
    public String toString() {
        return "after blockers are declared";
    }
}
