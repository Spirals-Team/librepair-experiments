
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BecomesBlockedTriggeredAbility;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author fireshoes
 */
public final class Groffskithur extends CardImpl {
    
    private static final FilterCard filter = new FilterCard("card named Groffskithur from your graveyard");
    
    static {
        filter.add(new NamePredicate("Groffskithur"));
    }

    public Groffskithur(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{5}{G}");
        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever Groffskithur becomes blocked, you may return target card named Groffskithur from your graveyard to your hand.
        Ability ability = new BecomesBlockedTriggeredAbility(new ReturnToHandTargetEffect(), true);
        ability.addTarget(new TargetCardInYourGraveyard(filter));
        this.addAbility(ability);
    }

    public Groffskithur(final Groffskithur card) {
        super(card);
    }

    @Override
    public Groffskithur copy() {
        return new Groffskithur(this);
    }
}
