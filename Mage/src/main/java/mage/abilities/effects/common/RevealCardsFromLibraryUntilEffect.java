
package mage.abilities.effects.common;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Library;
import mage.players.Player;

/**
 *
 * @author Styxo
 */
public class RevealCardsFromLibraryUntilEffect extends OneShotEffect {

    private FilterCard filter;
    private Zone zoneToPutRest;
    private Zone zoneToPutCard;
    private boolean shuffleRestInto;
    private boolean anyOrder;

    public RevealCardsFromLibraryUntilEffect(FilterCard filter, Zone zoneToPutCard, Zone zoneToPutRest) {
        this(filter, zoneToPutCard, zoneToPutRest, false, false);
    }

    public RevealCardsFromLibraryUntilEffect(FilterCard filter, Zone zoneToPutCard, Zone zoneToPutRest, boolean shuffleRestInto) {
        this(filter, zoneToPutCard, zoneToPutRest, shuffleRestInto, false);
    }

    public RevealCardsFromLibraryUntilEffect(FilterCard filter, Zone zoneToPutCard, Zone zoneToPutRest, boolean shuffleRestInto, boolean anyOrder) {
        super(Outcome.Benefit);
        this.filter = filter;
        this.zoneToPutCard = zoneToPutCard;
        this.zoneToPutRest = zoneToPutRest;
        this.shuffleRestInto = shuffleRestInto;
        this.anyOrder = anyOrder;
        setText();
    }

    public RevealCardsFromLibraryUntilEffect(final RevealCardsFromLibraryUntilEffect effect) {
        super(effect);
        this.filter = effect.filter;
        this.zoneToPutCard = effect.zoneToPutCard;
        this.zoneToPutRest = effect.zoneToPutRest;
        this.shuffleRestInto = effect.shuffleRestInto;
        this.anyOrder = effect.anyOrder;
        setText();
    }

    @Override
    public RevealCardsFromLibraryUntilEffect copy() {
        return new RevealCardsFromLibraryUntilEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = game.getObject(source.getSourceId());
        if (controller != null && controller.getLibrary().hasCards()) {
            Cards cards = new CardsImpl();
            Library library = controller.getLibrary();
            Card card = null;
            do {
                card = library.removeFromTop(game);
                if (card != null) {
                    cards.add(card);
                }
            } while (library.hasCards() && card != null && !filter.match(card, game));
            // reveal cards
            if (!cards.isEmpty()) {
                controller.revealCards(sourceObject.getIdName(), cards, game);
                if (filter.match(card, game)) {
                    // put card in correct zone
                    controller.moveCards(card, zoneToPutCard, source, game);
                    // remove it from revealed card list
                    cards.remove(card);
                }
                // Put the rest in correct zone
                switch (zoneToPutRest) {
                    case LIBRARY: {
                        if (!cards.isEmpty()) {
                            if (shuffleRestInto) {
                                library.addAll(cards.getCards(game), game);
                            } else {
                                controller.putCardsOnBottomOfLibrary(cards, game, source, anyOrder);
                            }
                        }
                        break;
                    }
                    default:
                        if (!cards.isEmpty()) {
                            controller.moveCards(cards, zoneToPutRest, source, game);
                        }
                }
            }
            return true;
        }
        return false;
    }

    private void setText() {
        StringBuilder sb = new StringBuilder("reveal cards from the top of your library until you reveal a " + filter.getMessage() + ". Put that card ");

        switch (zoneToPutCard) {
            case HAND: {
                sb.append("into your hand ");
                break;
            }
            case BATTLEFIELD: {
                sb.append("onto the battlefield");
                break;
            }
        }

        switch (zoneToPutRest) {
            case GRAVEYARD: {
                sb.append(" and put all other cards revealed this way into your graveyard.");
                break;
            }
            case LIBRARY: {
                if (shuffleRestInto) {
                    sb.append(", then shuffles the rest into their library.");
                } else {
                    sb.append(" and the rest on the bottom of your library in ");
                    if (anyOrder) {
                        sb.append("any");
                    } else {
                        sb.append("a random");

                    }
                    sb.append(" order.");
                }
                break;
            }
            case EXILED: {
                sb.append(" and exile all other cards revealed this way.");
                break;
            }
        }
        staticText = sb.toString();
    }
}
