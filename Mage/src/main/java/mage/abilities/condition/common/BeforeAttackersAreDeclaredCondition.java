/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mage.abilities.condition.common;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.game.Game;

/**
 *
 * @author LevelX2
 */
public enum BeforeAttackersAreDeclaredCondition implements Condition {

    instance;
    @Override
    public boolean apply(Game game, Ability source) {
        return !game.getTurn().isDeclareAttackersStepStarted();
    }

    @Override
    public String toString() {
        return "before attackers are declared";
    }
}
