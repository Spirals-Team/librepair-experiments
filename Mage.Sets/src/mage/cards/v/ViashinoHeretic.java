
package mage.cards.v;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetArtifactPermanent;

/**
 *
 * @author Plopman
 */
public final class ViashinoHeretic extends CardImpl {

    public ViashinoHeretic(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{R}");
        this.subtype.add(SubType.VIASHINO);

        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // {1}{R}, {tap}: Destroy target artifact. Viashino Heretic deals damage to that artifact's controller equal to the artifact's converted mana cost.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new ViashinoHereticEffect(), new ManaCostsImpl("{1}{R}"));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetArtifactPermanent());
        this.addAbility(ability);
    }

    public ViashinoHeretic(final ViashinoHeretic card) {
        super(card);
    }

    @Override
    public ViashinoHeretic copy() {
        return new ViashinoHeretic(this);
    }
}

class ViashinoHereticEffect extends OneShotEffect {

    
    public ViashinoHereticEffect() {
        super(Outcome.DestroyPermanent);
    }

    public ViashinoHereticEffect(final ViashinoHereticEffect effect) {
        super(effect);
    }

    @Override
    public ViashinoHereticEffect copy() {
        return new ViashinoHereticEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if(permanent != null)
        {
            int couvertedManaCost = permanent.getConvertedManaCost();
            Player player = game.getPlayer(permanent.getControllerId());
            permanent.destroy(source.getSourceId(), game, false);
            if(player != null){
                player.damage(couvertedManaCost, source.getSourceId(), game, false, true);
            }
            return true;
        }
        return false;
    }

    @Override
    public String getText(Mode mode) {
        return "Destroy target artifact. Viashino Heretic deals damage to that artifact's controller equal to the artifact's converted mana cost";
    }
}