
package mage.cards.l;

import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.mana.GreenManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.UUID;

/**
 * @author emerald000
 */
public final class LifeAndLimb extends CardImpl {

    public LifeAndLimb(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{G}");

        // All Forests and all Saprolings are 1/1 green Saproling creatures and Forest lands in addition to their other types.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new LifeAndLimbEffect()));
    }

    public LifeAndLimb(final LifeAndLimb card) {
        super(card);
    }

    @Override
    public LifeAndLimb copy() {
        return new LifeAndLimb(this);
    }
}

class LifeAndLimbEffect extends ContinuousEffectImpl {

    private static final FilterPermanent filter = new FilterPermanent("All Forests and all Saprolings");

    static {
        filter.add(Predicates.or(new SubtypePredicate(SubType.FOREST), new SubtypePredicate(SubType.SAPROLING)));
    }

    LifeAndLimbEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Neutral);
        staticText = "All Forests and all Saprolings are 1/1 green Saproling creatures and Forest lands in addition to their other types";
    }

    LifeAndLimbEffect(final LifeAndLimbEffect effect) {
        super(effect);
    }

    @Override
    public LifeAndLimbEffect copy() {
        return new LifeAndLimbEffect(this);
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            for (Permanent permanent : game.getState().getBattlefield().getActivePermanents(filter, source.getControllerId(), source.getSourceId(), game)) {
                switch (layer) {
                    case TypeChangingEffects_4:
                        permanent.addCardType(CardType.CREATURE);
                        if (!permanent.hasSubtype(SubType.SAPROLING, game)) {
                            permanent.getSubtype(game).add(SubType.SAPROLING);
                        }
                        permanent.addCardType(CardType.LAND);
                        if (!permanent.hasSubtype(SubType.FOREST, game)) {
                            permanent.getSubtype(game).add(SubType.FOREST);
                        }
                        break;
                    case ColorChangingEffects_5:
                        permanent.getColor(game).setColor(ObjectColor.GREEN);
                        break;
                    case AbilityAddingRemovingEffects_6:
                        boolean flag = false;
                        for (Ability ability : permanent.getAbilities(game)) {
                            if (ability instanceof GreenManaAbility) {
                                flag = true;
                                break;
                            }
                        }
                        if (!flag) {
                            permanent.addAbility(new GreenManaAbility(), source.getSourceId(), game);
                        }
                        break;
                    case PTChangingEffects_7:
                        if (sublayer == SubLayer.SetPT_7b) {
                            permanent.getPower().setValue(1);
                            permanent.getToughness().setValue(1);
                        }
                        break;
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public boolean hasLayer(Layer layer) {
        return layer == Layer.TypeChangingEffects_4
                || layer == Layer.ColorChangingEffects_5
                || layer == Layer.AbilityAddingRemovingEffects_6
                || layer == Layer.PTChangingEffects_7;
    }
}
