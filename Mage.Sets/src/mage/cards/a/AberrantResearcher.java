
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.TransformAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.p.PerfectedForm;
import mage.constants.*;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author fireshoes
 */
public final class AberrantResearcher extends CardImpl {

    public AberrantResearcher(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{U}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.INSECT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        this.transformable = true;
        this.secondSideCardClazz = PerfectedForm.class;

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // At the beginning of your upkeep, put the top card of your library into your graveyard. If it's an instant or sorcery card, transform Aberrant Researcher.
        this.addAbility(new TransformAbility());
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(Zone.BATTLEFIELD, new AberrantResearcherEffect(), TargetController.YOU, false));
    }

    public AberrantResearcher(final AberrantResearcher card) {
        super(card);
    }

    @Override
    public AberrantResearcher copy() {
        return new AberrantResearcher(this);
    }
}

class AberrantResearcherEffect extends OneShotEffect {

    public AberrantResearcherEffect() {
        super(Outcome.Benefit);
        staticText = "put the top card of your library into your graveyard. If it's an instant or sorcery card, transform {this}";
    }

    public AberrantResearcherEffect(final AberrantResearcherEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null && controller.getLibrary().hasCards()) {
            Card card = controller.getLibrary().getFromTop(game);
            controller.moveCards(card, Zone.GRAVEYARD, source, game);
            if (card.isInstant() || card.isSorcery()) {
                new TransformSourceEffect(true).apply(game, source);
            }
            return true;
        }
        return false;
    }

    @Override
    public AberrantResearcherEffect copy() {
        return new AberrantResearcherEffect(this);
    }
}
