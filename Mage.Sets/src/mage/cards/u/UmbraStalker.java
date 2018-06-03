
package mage.cards.u;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.SetPowerToughnessSourceEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author jeffwadsworth
 *
 */
public final class UmbraStalker extends CardImpl {

    public UmbraStalker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{B}{B}{B}");
        this.subtype.add(SubType.ELEMENTAL);

        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Chroma - Umbra Stalker's power and toughness are each equal to the number of black mana symbols in the mana costs of cards in your graveyard.
        Effect effect = new SetPowerToughnessSourceEffect(new ChromaUmbraStalkerCount(), Duration.WhileOnBattlefield);
        effect.setText("<i>Chroma</i> &mdash; Umbra Stalker's power and toughness are each equal to the number of black mana symbols in the mana costs of cards in your graveyard.");
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, effect));

    }

    public UmbraStalker(final UmbraStalker card) {
        super(card);
    }

    @Override
    public UmbraStalker copy() {
        return new UmbraStalker(this);
    }
}

class ChromaUmbraStalkerCount implements DynamicValue {

    private int chroma;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        chroma = 0;
        Player you = game.getPlayer(sourceAbility.getControllerId());
        if (you == null) {
            return 0;
        }
        for (Card card : you.getGraveyard().getCards(game)) {
            chroma += card.getManaCost().getMana().getBlack();
        }
        return chroma;
    }

    @Override
    public DynamicValue copy() {
        return new ChromaUmbraStalkerCount();
    }

    @Override
    public String toString() {
        return "1";
    }

    @Override
    public String getMessage() {
        return "";
    }
}
