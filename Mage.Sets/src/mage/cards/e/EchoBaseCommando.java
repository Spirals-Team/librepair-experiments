
package mage.cards.e;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.ActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.cost.CostModificationEffectImpl;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.Filter;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.util.CardUtil;

/**
 *
 * @author Styxo
 */
public final class EchoBaseCommando extends CardImpl {

    private static final Filter filter = new FilterPermanent("Beasts");

    static {
        filter.add(new SubtypePredicate(SubType.BEAST));
    }

    public EchoBaseCommando(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.REBEL);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Protection from Beasts.
        this.addAbility(new ProtectionAbility(filter));

        // Activated abilities of creatures your opponent controls cost {2} more to activate.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new EchoBaseCommandoEffect()));

    }

    public EchoBaseCommando(final EchoBaseCommando card) {
        super(card);
    }

    @Override
    public EchoBaseCommando copy() {
        return new EchoBaseCommando(this);
    }
}

class EchoBaseCommandoEffect extends CostModificationEffectImpl {

    private static final String effectText = "Activated abilities of creatures your opponent control cost {2} more to activate";
    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent();

    static {
        filter.add(new ControllerPredicate(TargetController.OPPONENT));
    }

    public EchoBaseCommandoEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit, CostModificationType.INCREASE_COST);
        staticText = effectText;
    }

    public EchoBaseCommandoEffect(final EchoBaseCommandoEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        if (game.getPlayer(abilityToModify.getControllerId()) != null) {
            CardUtil.increaseCost(abilityToModify, 2);
            return true;
        }
        return false;
    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        if (abilityToModify.getAbilityType() == AbilityType.ACTIVATED || (abilityToModify.getAbilityType() == AbilityType.MANA && (abilityToModify instanceof ActivatedAbility))) {
            Permanent permanent = game.getPermanent(abilityToModify.getSourceId());
            if (permanent != null && filter.match(permanent, source.getSourceId(), source.getControllerId(), game)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public EchoBaseCommandoEffect copy() {
        return new EchoBaseCommandoEffect(this);
    }
}
