
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.permanent.token.GolemToken;

/**
 *
 * @author North
 */
public final class MaulSplicer extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("Golem creatures");

    static {
        filter.add(new CardTypePredicate(CardType.CREATURE));
        filter.add(new SubtypePredicate(SubType.GOLEM));
    }

    public MaulSplicer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{6}{G}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ARTIFICER);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // When Maul Splicer enters the battlefield, create two 3/3 colorless Golem artifact creature tokens.        
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new GolemToken(expansionSetCode), 2)));
        
        // Golem creatures you control have trample.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityControlledEffect(TrampleAbility.getInstance(), Duration.WhileOnBattlefield, filter)));
    }

    public MaulSplicer(final MaulSplicer card) {
        super(card);
    }

    @Override
    public MaulSplicer copy() {
        return new MaulSplicer(this);
    }
}
