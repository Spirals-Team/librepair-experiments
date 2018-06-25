
package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.dynamicvalue.common.SacrificeCostConvertedMana;
import mage.abilities.mana.DynamicManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterControlledArtifactPermanent;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author LoneFox
 */
public final class PriestOfYawgmoth extends CardImpl {

    public PriestOfYawgmoth(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{B}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // {T}, Sacrifice an artifact: Add an amount of {B} equal to the sacrificed artifact's converted mana cost.
        Ability ability = new DynamicManaAbility(Mana.BlackMana(1), new SacrificeCostConvertedMana("artifact"),
                "add an amount of {B} equal to the sacrificed artifact's converted mana cost");
        ability.addCost(new SacrificeTargetCost(new TargetControlledPermanent(new FilterControlledArtifactPermanent())));
        this.addAbility(ability);
    }

    public PriestOfYawgmoth(final PriestOfYawgmoth card) {
        super(card);
    }

    @Override
    public PriestOfYawgmoth copy() {
        return new PriestOfYawgmoth(this);
    }
}
