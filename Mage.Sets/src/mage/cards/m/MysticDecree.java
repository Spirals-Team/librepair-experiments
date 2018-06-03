
package mage.cards.m;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.LoseAbilityAllEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.IslandwalkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;

/**
 *
 * @author fireshoes
 */
public final class MysticDecree extends CardImpl {

    public MysticDecree(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{2}{U}{U}");
        addSuperType(SuperType.WORLD);

        // All creatures lose flying and islandwalk.
        Effect effect = new LoseAbilityAllEffect(FlyingAbility.getInstance(), Duration.WhileOnBattlefield, new FilterCreaturePermanent("All creatures"));
        effect.setText("All creatures lose flying");
        Effect effect2 = new LoseAbilityAllEffect(new IslandwalkAbility(), Duration.WhileOnBattlefield, new FilterCreaturePermanent("all creatures"));
        effect2.setText("and islandwalk");
        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD, effect);
        ability.addEffect(effect2);
        this.addAbility(ability);
    }

    public MysticDecree(final MysticDecree card) {
        super(card);
    }

    @Override
    public MysticDecree copy() {
        return new MysticDecree(this);
    }
}
