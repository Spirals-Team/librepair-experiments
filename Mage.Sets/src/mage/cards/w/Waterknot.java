
package mage.cards.w;

import java.util.UUID;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.DontUntapInControllersUntapStepEnchantedEffect;
import mage.abilities.effects.common.TapEnchantedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 * @author Loki & L_J
 */
public final class Waterknot extends CardImpl {

    public Waterknot(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{1}{U}{U}");
        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.Detriment));
        this.addAbility(new EnchantAbility(auraTarget.getTargetName()));
        
        // When Waterknot enters the battlefield, tap enchanted creature.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new TapEnchantedEffect()));
        
        // Enchanted creature doesn't untap during its controller's untap step.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new DontUntapInControllersUntapStepEnchantedEffect()));
    }

    public Waterknot(final Waterknot card) {
        super(card);
    }

    @Override
    public Waterknot copy() {
        return new Waterknot(this);
    }
}
