
package mage.cards.i;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CopyTargetSpellEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.ComparisonType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.FilterSpell;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.mageobject.ConvertedManaCostPredicate;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.target.TargetSpell;

/**
 *
 * @author LevelX2
 */
public final class IzzetGuildmage extends CardImpl {

    private static final FilterSpell filterInstant = new FilterSpell("instant spell you control with converted mana cost 2 or less");
    private static final FilterSpell filterSorcery = new FilterSpell("sorcery spell you control with converted mana cost 2 or less");

    static {
        filterInstant.add(new CardTypePredicate(CardType.INSTANT));
        filterInstant.add(new ConvertedManaCostPredicate(ComparisonType.FEWER_THAN, 3));
        filterInstant.add(new ControllerPredicate(TargetController.YOU));
        filterSorcery.add(new CardTypePredicate(CardType.SORCERY));
        filterSorcery.add(new ConvertedManaCostPredicate(ComparisonType.FEWER_THAN, 3));
        filterSorcery.add(new ControllerPredicate(TargetController.YOU));
    }

    public IzzetGuildmage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{U/R}{U/R}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // <i>({UR} can be paid with either {U} or {R}.)</i>
        // {2}{U}: Copy target instant spell you control with converted mana cost 2 or less. You may choose new targets for the copy.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new CopyTargetSpellEffect(), new ManaCostsImpl("{2}{U}"));
        ability.addTarget(new TargetSpell(filterInstant));
        this.addAbility(ability);

        // {2}{R}: Copy target sorcery spell you control with converted mana cost 2 or less. You may choose new targets for the copy.
        ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new CopyTargetSpellEffect(), new ManaCostsImpl("{2}{R}"));
        ability.addTarget(new TargetSpell(filterSorcery));
        this.addAbility(ability);

    }

    public IzzetGuildmage(final IzzetGuildmage card) {
        super(card);
    }

    @Override
    public IzzetGuildmage copy() {
        return new IzzetGuildmage(this);
    }
}
