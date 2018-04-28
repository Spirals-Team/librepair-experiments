/*
 *  Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without modification, are
 *  permitted provided that the following conditions are met:
 *
 *     1. Redistributions of source code must retain the above copyright notice, this list of
 *        conditions and the following disclaimer.
 *
 *     2. Redistributions in binary form must reproduce the above copyright notice, this list
 *        of conditions and the following disclaimer in the documentation and/or other materials
 *        provided with the distribution.
 *
 *  THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
 *  WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 *  FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
 *  CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 *  CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 *  SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 *  ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 *  NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 *  ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 *  The views and conclusions contained in the software and documentation are those of the
 *  authors and should not be interpreted as representing official policies, either expressed
 *  or implied, of BetaSteward_at_googlemail.com.
 */
package mage.abilities.common;

import mage.abilities.condition.Condition;
import mage.abilities.effects.common.ruleModifying.CastOnlyDuringPhaseStepSourceEffect;
import mage.constants.PhaseStep;
import mage.constants.TurnPhase;
import mage.constants.Zone;

/**
 *
 * @author LevelX2
 */
public class CastOnlyDuringPhaseStepSourceAbility extends SimpleStaticAbility {

    public CastOnlyDuringPhaseStepSourceAbility(TurnPhase turnPhase) {
        this(turnPhase, null, null);
    }

    public CastOnlyDuringPhaseStepSourceAbility(TurnPhase turnPhase, Condition condition) {
        this(turnPhase, null, condition);
    }

    public CastOnlyDuringPhaseStepSourceAbility(PhaseStep phaseStep) {
        this(null, phaseStep, null);
    }

    public CastOnlyDuringPhaseStepSourceAbility(PhaseStep phaseStep, Condition condition) {
        this(null, phaseStep, condition);
    }

    public CastOnlyDuringPhaseStepSourceAbility(TurnPhase turnPhase, PhaseStep phaseStep, Condition condition) {
        this(turnPhase, phaseStep, condition, null);
    }

    public CastOnlyDuringPhaseStepSourceAbility(TurnPhase turnPhase, PhaseStep phaseStep, Condition condition, String effectText) {
        super(Zone.ALL, new CastOnlyDuringPhaseStepSourceEffect(turnPhase, phaseStep, condition));
        this.setRuleAtTheTop(true);
        if (effectText != null) {
            getEffects().get(0).setText(effectText);
        }
    }

    private CastOnlyDuringPhaseStepSourceAbility(final CastOnlyDuringPhaseStepSourceAbility ability) {
        super(ability);
    }

    @Override
    public CastOnlyDuringPhaseStepSourceAbility copy() {
        return new CastOnlyDuringPhaseStepSourceAbility(this);
    }
}
