
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.abilityword.ConstellationAbility;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.TargetController;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerPredicate;

/**
 *
 * @author LevelX2
 */
public final class DoomwakeGiant extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creatures your opponents control");
    
    static {
        filter.add(new ControllerPredicate(TargetController.OPPONENT));
    }
    
    public DoomwakeGiant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT,CardType.CREATURE},"{4}{B}");
        this.subtype.add(SubType.GIANT);

        this.power = new MageInt(4);
        this.toughness = new MageInt(6);

        // Constellation - When Doomwake Giant or another enchantment enters the battlefield under your control, creatures your opponents control get -1/-1 until end of turn.
        this.addAbility(new ConstellationAbility(new BoostAllEffect(-1,-1, Duration.EndOfTurn, filter, false)));
    }

    public DoomwakeGiant(final DoomwakeGiant card) {
        super(card);
    }

    @Override
    public DoomwakeGiant copy() {
        return new DoomwakeGiant(this);
    }
}
