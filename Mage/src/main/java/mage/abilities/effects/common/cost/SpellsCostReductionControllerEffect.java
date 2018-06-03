
package mage.abilities.effects.common.cost;

import java.util.LinkedHashSet;
import java.util.Set;
import mage.MageObject;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.ActivatedAbility;
import mage.abilities.SpellAbility;
import mage.abilities.costs.mana.ManaCost;
import mage.abilities.costs.mana.ManaCosts;
import mage.cards.Card;
import mage.choices.ChoiceImpl;
import mage.constants.CostModificationType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.util.CardUtil;

/**
 *
 * @author North
 */
public class SpellsCostReductionControllerEffect extends CostModificationEffectImpl {

    private final FilterCard filter;
    private final int amount;
    private final boolean upTo;
    private ManaCosts<ManaCost> manaCostsToReduce = null;

    public SpellsCostReductionControllerEffect(FilterCard filter, ManaCosts<ManaCost> manaCostsToReduce) {
        super(Duration.WhileOnBattlefield, Outcome.Benefit, CostModificationType.REDUCE_COST);
        this.filter = filter;
        this.amount = 0;
        this.manaCostsToReduce = manaCostsToReduce;
        this.upTo = false;

        StringBuilder sb = new StringBuilder();
        sb.append(filter.getMessage()).append(" you cast cost ");
        for (String manaSymbol : manaCostsToReduce.getSymbols()) {
            sb.append(manaSymbol);
        }
        sb.append(" less to cast. This effect reduces only the amount of colored mana you pay.");
        this.staticText = sb.toString();
    }

    public SpellsCostReductionControllerEffect(FilterCard filter, int amount) {
        this(filter, amount, false);
    }

    public SpellsCostReductionControllerEffect(FilterCard filter, int amount, boolean upTo) {
        super(Duration.WhileOnBattlefield, Outcome.Benefit, CostModificationType.REDUCE_COST);
        this.filter = filter;
        this.amount = amount;
        this.upTo = upTo;
        this.staticText = filter.getMessage() + " you cast cost " + (upTo ? "up to " : "") + '{' + amount + "} less to cast";
    }

    protected SpellsCostReductionControllerEffect(final SpellsCostReductionControllerEffect effect) {
        super(effect);
        this.filter = effect.filter;
        this.amount = effect.amount;
        this.manaCostsToReduce = effect.manaCostsToReduce;
        this.upTo = effect.upTo;
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        if (manaCostsToReduce != null) {
            CardUtil.adjustCost((SpellAbility) abilityToModify, manaCostsToReduce, false);
        } else {
            if (upTo) {
                Mana mana = abilityToModify.getManaCostsToPay().getMana();
                int reduceMax = mana.getGeneric();
                if (reduceMax > amount) {
                    reduceMax = amount;
                }
                if (reduceMax > 0) {
                    Player controller = game.getPlayer(abilityToModify.getControllerId());
                    if (controller == null) {
                        return false;
                    }
                    int reduce = reduceMax;
                    if (!(abilityToModify instanceof ActivatedAbility) || !((ActivatedAbility) abilityToModify).isCheckPlayableMode()) {
                        ChoiceImpl choice = new ChoiceImpl(false);
                        Set<String> set = new LinkedHashSet<>();
                        for (int i = 0; i <= amount; i++) {
                            set.add(String.valueOf(i));
                        }
                        choice.setChoices(set);
                        MageObject mageObject = game.getObject(abilityToModify.getSourceId());
                        choice.setMessage("Reduce cost of " + (mageObject != null ? mageObject.getIdName() : filter.getMessage()));
                        if (controller.choose(Outcome.Benefit, choice, game)) {
                            reduce = Integer.parseInt(choice.getChoice());
                        } else {
                            reduce = reduceMax; // cancel will be set to max possible reduce
                        }
                    }
                    if (reduce > 0) {
                        CardUtil.reduceCost(abilityToModify, reduce);
                    }
                }
            } else {
                CardUtil.reduceCost(abilityToModify, this.amount);
            }
        }
        return true;
    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        if (abilityToModify instanceof SpellAbility) {
            if (abilityToModify.getControllerId().equals(source.getControllerId())) {
                Spell spell = (Spell) game.getStack().getStackObject(abilityToModify.getId());
                if (spell != null) {
                    return this.filter.match(spell, source.getSourceId(), source.getControllerId(), game);
                } else {
                    // used at least for flashback ability because Flashback ability doesn't use stack or for getPlayables where spell is not cast yet
                    Card sourceCard = game.getCard(abilityToModify.getSourceId());
                    return sourceCard != null && this.filter.match(sourceCard, source.getSourceId(), source.getControllerId(), game);
                }
            }
        }
        return false;
    }

    @Override
    public SpellsCostReductionControllerEffect copy() {
        return new SpellsCostReductionControllerEffect(this);
    }
}
