
package mage.cards.d;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ColoredManaCost;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.CopyTargetSpellEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterSpell;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.target.TargetPermanent;
import mage.target.TargetSpell;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Loki
 */
public final class DualCasting extends CardImpl {
    private static final FilterSpell filter = new FilterSpell("instant or sorcery spell");

    static {
        filter.add(Predicates.or(
                new CardTypePredicate(CardType.INSTANT),
                new CardTypePredicate(CardType.SORCERY)));
        filter.add(new ControllerPredicate(TargetController.YOU));
    }

    public DualCasting(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{1}{R}");
        this.subtype.add(SubType.AURA);


        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.AddAbility));
        Ability ability = new EnchantAbility(auraTarget.getTargetName());
        this.addAbility(ability);
        // Enchanted creature has "{R}, {tap}: Copy target instant or sorcery spell you control. You may choose new targets for the copy."
        ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new CopyTargetSpellEffect(), new ColoredManaCost(ColoredManaSymbol.R));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetSpell(filter));
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityAttachedEffect(ability, AttachmentType.AURA)));
    }

    public DualCasting(final DualCasting card) {
        super(card);
    }

    @Override
    public DualCasting copy() {
        return new DualCasting(this);
    }
}
