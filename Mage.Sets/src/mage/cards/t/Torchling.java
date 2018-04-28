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
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.SpellAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ColoredManaCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.ChooseNewTargetsTargetEffect;
import mage.abilities.effects.common.UntapSourceEffect;
import mage.abilities.effects.common.combat.MustBeBlockedByTargetSourceEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.ColoredManaSymbol;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.FilterSpell;
import mage.filter.predicate.Predicate;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.target.Target;
import mage.target.TargetSpell;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author emerald000
 */
public class Torchling extends CardImpl {

    public Torchling(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{R}{R}");
        this.subtype.add(SubType.SHAPESHIFTER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // {R}: Untap Torchling.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new UntapSourceEffect(), new ColoredManaCost(ColoredManaSymbol.R)));

        // {R}: Target creature blocks Torchling this turn if able.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new MustBeBlockedByTargetSourceEffect(), new ColoredManaCost(ColoredManaSymbol.R));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);

        // {R}: Change the target of target spell that targets only Torchling.
        ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new ChooseNewTargetsTargetEffect(true, true), new ColoredManaCost(ColoredManaSymbol.R));
        FilterSpell filter = new FilterSpell("spell that targets only " + this.getName());
        filter.add(new TorchlingTargetPredicate(this.getId()));
        ability.addTarget(new TargetSpell(filter));
        this.addAbility(ability);

        // {1}: Torchling gets +1/-1 until end of turn.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new BoostSourceEffect(1, -1, Duration.EndOfTurn), new GenericManaCost(1)));

        // {1}: Torchling gets -1/+1 until end of turn.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new BoostSourceEffect(-1, 1, Duration.EndOfTurn), new GenericManaCost(1)));
    }

    public Torchling(final Torchling card) {
        super(card);
    }

    @Override
    public Torchling copy() {
        return new Torchling(this);
    }
}

class TorchlingTargetPredicate implements Predicate<MageObject> {

    private final UUID sourceId;

    TorchlingTargetPredicate(UUID sourceId) {
        this.sourceId = sourceId;
    }

    @Override
    public boolean apply(MageObject input, Game game) {
        Spell spell = game.getStack().getSpell(input.getId());
        if (spell != null) {
            int numberOfTargets = 0;
            for (SpellAbility spellAbility : spell.getSpellAbilities()) {
                for (UUID modeId : spellAbility.getModes().getSelectedModes()) {
                    Mode mode = spellAbility.getModes().get(modeId);
                    for (Target target : mode.getTargets()) {
                        for (UUID targetId : target.getTargets()) {
                            if (!targetId.equals(sourceId)) {
                                return false;
                            } else {
                                numberOfTargets++;
                            }
                        }
                    }
                }
            }
            return numberOfTargets > 0;
        }
        return false;
    }

    @Override
    public String toString() {
        return "target spell that targets only {this}";
    }
}
