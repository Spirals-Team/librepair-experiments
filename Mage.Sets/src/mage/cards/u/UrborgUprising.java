
package mage.cards.u;

import java.util.UUID;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterCreatureCard;
import mage.target.common.TargetCardInYourGraveyard;

/**
 * @author Laxika
 */
public final class UrborgUprising extends CardImpl {

    public UrborgUprising(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{4}{B}");

        // Return up to two target creature cards from your graveyard to your hand.
        this.getSpellAbility().addEffect(new ReturnFromGraveyardToHandTargetEffect());
        this.getSpellAbility().addTarget(new TargetCardInYourGraveyard(0, 2, new FilterCreatureCard("creature cards from your graveyard")));

        // Draw a card.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1));
    }

    public UrborgUprising(final UrborgUprising card) {
        super(card);
    }

    @Override
    public UrborgUprising copy() {
        return new UrborgUprising(this);
    }
}
