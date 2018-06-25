
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.CostImpl;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.keyword.RecoverAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author choiseul11 & L_J
 */
public final class GarzasAssassin extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("nonblack creature");

    static {
        filter.add(Predicates.not(new ColorPredicate(ObjectColor.BLACK)));
    }

    public GarzasAssassin(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{B}{B}{B}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ASSASSIN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Sacrifice Garza's Assassin: Destroy target nonblack creature.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DestroyTargetEffect(), new SacrificeSourceCost());
        ability.addTarget(new TargetCreaturePermanent(filter));
        this.addAbility(ability);

        // Recover—Pay half your life, rounded up.
        this.addAbility(new RecoverAbility(new GarzasAssassinCost(), this));
    }

    public GarzasAssassin(final GarzasAssassin card) {
        super(card);
    }

    @Override
    public GarzasAssassin copy() {
        return new GarzasAssassin(this);
    }
}

class GarzasAssassinCost extends CostImpl {

    GarzasAssassinCost() {
        this.text = "Pay half your life, rounded up";
    }

    GarzasAssassinCost(GarzasAssassinCost cost) {
        super(cost);
    }

    @Override
    public boolean canPay(Ability ability, UUID sourceId, UUID controllerId, Game game) {
        Player controller = game.getPlayer(controllerId);
        return controller != null && (controller.getLife() < 1 || controller.canPayLifeCost());
    }

    @Override
    public boolean pay(Ability ability, Game game, UUID sourceId, UUID controllerId, boolean noMana, Cost costToPay) {
        Player controller = game.getPlayer(controllerId);
        if (controller != null) {
            int currentLife = controller.getLife();
            int lifeToPay = (currentLife + currentLife % 2) / 2; // Divide by two and round up.
            if (lifeToPay < 0) {
                this.paid = true;
            } else {
                this.paid = (controller.loseLife(lifeToPay, game, false) == lifeToPay);
            }
            return this.paid;
        }
        return false;
    }

    @Override
    public GarzasAssassinCost copy() {
        return new GarzasAssassinCost(this);
    }
}
