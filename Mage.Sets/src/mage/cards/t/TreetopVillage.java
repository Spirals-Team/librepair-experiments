
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BecomesCreatureSourceEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.abilities.mana.GreenManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.game.permanent.token.TokenImpl;
import mage.game.permanent.token.Token;

/**
 *
 * @author Loki
 */
public final class TreetopVillage extends CardImpl {

    public TreetopVillage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},"");
        this.addAbility(new EntersBattlefieldTappedAbility());
        this.addAbility(new GreenManaAbility());
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new BecomesCreatureSourceEffect(new ApeToken(), "land", Duration.EndOfTurn), new ManaCostsImpl("{1}{G}")));
    }

    public TreetopVillage(final TreetopVillage card) {
        super(card);
    }

    @Override
    public TreetopVillage copy() {
        return new TreetopVillage(this);
    }
}

class ApeToken extends TokenImpl {
    ApeToken() {
        super("Ape", "3/3 green Ape creature with trample");
        cardType.add(CardType.CREATURE);
        this.subtype.add(SubType.APE);
        color.setGreen(true);
        power = new MageInt(3);
        toughness = new MageInt(3);
        this.addAbility(TrampleAbility.getInstance());
    }
    public ApeToken(final ApeToken token) {
        super(token);
    }

    public ApeToken copy() {
        return new ApeToken(this);
    }
}