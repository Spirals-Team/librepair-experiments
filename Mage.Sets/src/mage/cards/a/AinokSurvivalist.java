
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.TurnedFaceUpSourceTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.keyword.MorphAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.common.FilterArtifactOrEnchantmentPermanent;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.target.TargetPermanent;

/**
 *
 * @author fireshoes
 */
public final class AinokSurvivalist extends CardImpl {
    
     private static final FilterArtifactOrEnchantmentPermanent filter = new FilterArtifactOrEnchantmentPermanent("artifact or enchantment an opponent controls");

    static {
        filter.add(new ControllerPredicate(TargetController.OPPONENT));
    }

    public AinokSurvivalist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{G}");
        this.subtype.add(SubType.HOUND);
        this.subtype.add(SubType.SHAMAN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Megamorph {1}{G}
        this.addAbility(new MorphAbility(this, new ManaCostsImpl("{1}{G}"), true));
        
        // When Ainok Survivalist is turned face up, destroy target artifact or enchantment an opponent controls.
        Effect effect = new DestroyTargetEffect();
        effect.setText("destroy target artifact or enchantment an opponent controls");
        Ability ability = new TurnedFaceUpSourceTriggeredAbility(effect, false);
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    public AinokSurvivalist(final AinokSurvivalist card) {
        super(card);
    }

    @Override
    public AinokSurvivalist copy() {
        return new AinokSurvivalist(this);
    }
}
