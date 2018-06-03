
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.ReturnToHandChosenControlledPermanentCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledLandPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.game.permanent.token.MelokuTheCloudedMirrorToken;
import mage.target.common.TargetControlledPermanent;

/**
 * @author Loki
 */
public final class MelokuTheCloudedMirror extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledLandPermanent("land");

    public MelokuTheCloudedMirror(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{U}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.MOONFOLK);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(2);
        this.toughness = new MageInt(4);
        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // {1}, Return a land you control to its owner's hand: Create a 1/1 blue Illusion creature token with flying.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new CreateTokenEffect(new MelokuTheCloudedMirrorToken(), 1), new GenericManaCost(1));
        ability.addCost(new ReturnToHandChosenControlledPermanentCost(new TargetControlledPermanent(filter)));
        this.addAbility(ability);
    }

    public MelokuTheCloudedMirror(final MelokuTheCloudedMirror card) {
        super(card);
    }

    @Override
    public MelokuTheCloudedMirror copy() {
        return new MelokuTheCloudedMirror(this);
    }

}
