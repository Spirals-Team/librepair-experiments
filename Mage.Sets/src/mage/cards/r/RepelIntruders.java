
package mage.cards.r;

import java.util.UUID;
import mage.abilities.condition.common.ManaWasSpentCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.CounterTargetEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.InfoEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ColoredManaSymbol;
import mage.filter.common.FilterCreatureSpell;
import mage.game.permanent.token.KithkinToken;
import mage.target.TargetSpell;
import mage.watchers.common.ManaSpentToCastWatcher;

/**
 *
 * @author jeffwadsworth
 */
public final class RepelIntruders extends CardImpl {

    public RepelIntruders(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{3}{W/U}");


        // Create two 1/1 white Kithkin Soldier creature tokens if {W} was spent to cast Repel Intruders. Counter up to one target creature spell if {U} was spent to cast Repel Intruders.
        TargetSpell target = new TargetSpell(0,1, new FilterCreatureSpell());
        target.setRequired(false);
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new CreateTokenEffect(new KithkinToken(), 2),
                new ManaWasSpentCondition(ColoredManaSymbol.W), "Create two 1/1 white Kithkin Soldier creature tokens if {W} was spent to cast {this}"));
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new CounterTargetEffect(),
                new ManaWasSpentCondition(ColoredManaSymbol.U), " Counter up to one target creature spell if {U} was spent to cast {this}"));
        this.getSpellAbility().addTarget(target);
        this.getSpellAbility().addEffect(new InfoEffect("<i>(Do both if {W}{U} was spent.)</i>"));
        this.getSpellAbility().addWatcher(new ManaSpentToCastWatcher());
        
    }

    public RepelIntruders(final RepelIntruders card) {
        super(card);
    }

    @Override
    public RepelIntruders copy() {
        return new RepelIntruders(this);
    }
}

