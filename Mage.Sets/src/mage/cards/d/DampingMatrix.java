
package mage.cards.d;

import java.util.Optional;
import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.mana.ActivatedManaAbilityImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;

/**
 *
 * @author LevelX2
 */
public final class DampingMatrix extends CardImpl {

    public DampingMatrix(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{3}");

        // Activated abilities of artifacts and creatures can't be activated unless they're mana abilities.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new DampingMatrixEffect()));
    }

    public DampingMatrix(final DampingMatrix card) {
        super(card);
    }

    @Override
    public DampingMatrix copy() {
        return new DampingMatrix(this);
    }
}

class DampingMatrixEffect extends ReplacementEffectImpl {

    private static final FilterPermanent filter = new FilterPermanent("artifacts and creatures");
    static {
        filter.add(Predicates.or(
                new CardTypePredicate(CardType.ARTIFACT),
                new CardTypePredicate(CardType.CREATURE)));
    }

    public DampingMatrixEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Detriment);
        staticText = "Activated abilities of artifacts and creatures can't be activated unless they're mana abilities";
    }

    public DampingMatrixEffect(final DampingMatrixEffect effect) {
        super(effect);
    }

    @Override
    public DampingMatrixEffect copy() {
        return new DampingMatrixEffect(this);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        return true;
    }
    
    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == EventType.ACTIVATE_ABILITY;
    }
    
    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        MageObject object = game.getObject(event.getSourceId());
        if (object instanceof Permanent && filter.match((Permanent)object, game)) {
            Optional<Ability> ability = object.getAbilities().get(event.getTargetId());
            if (ability.isPresent() && !(ability.get() instanceof ActivatedManaAbilityImpl)) {
                return true;
            }
        }
        return false;
    }

}
