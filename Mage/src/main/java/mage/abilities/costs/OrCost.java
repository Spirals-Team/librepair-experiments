
package mage.abilities.costs;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.costs.mana.ManaCost;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;
import mage.target.Targets;

public class OrCost implements Cost {

    private final Cost firstCost;
    private final Cost secondCost;
    private String description;
    // which cost was slected to pay
    private Cost selectedCost;

    public OrCost(Cost firstCost, Cost secondCost, String description) {
        this.firstCost = firstCost;
        this.secondCost = secondCost;
        this.description = description;
    }

    public OrCost(final OrCost cost) {
        this.firstCost = cost.firstCost.copy();
        this.secondCost = cost.secondCost.copy();
        this.description = cost.description;
        this.selectedCost = cost.selectedCost;
    }

    @Override
    public UUID getId() {
        throw new RuntimeException("Not supported method");
    }

    @Override
    public void setText(String text) {
        this.description = text;
    }

    @Override
    public String getText() {
        return description;
    }

    @Override
    public boolean canPay(Ability ability, UUID sourceId, UUID controllerId, Game game) {
        return firstCost.canPay(ability, sourceId, controllerId, game) || secondCost.canPay(ability, sourceId, controllerId, game);
    }

    @Override
    public boolean pay(Ability ability, Game game, UUID sourceId, UUID controllerId, boolean noMana) {
        return pay(ability, game, sourceId, controllerId, noMana, this);
    }

    @Override
    public boolean pay(Ability ability, Game game, UUID sourceId, UUID controllerId, boolean noMana, Cost costToPay) {
        selectedCost = null;
        // if only one can be paid select it
        if (!firstCost.canPay(ability, sourceId, controllerId, game)) {
            selectedCost = secondCost;
        }
        if (!secondCost.canPay(ability, sourceId, controllerId, game)) {
            selectedCost = firstCost;
        }
        // if both can be paid player has to select
        if (selectedCost == null) {
            Player controller = game.getPlayer(controllerId);
            if (controller != null) {
                StringBuilder sb = new StringBuilder();
                if (firstCost instanceof ManaCost) {
                    sb.append("Pay ");
                }
                sb.append(firstCost.getText()).append('?');
                if (controller.chooseUse(Outcome.Detriment, sb.toString(), ability, game)) {
                    selectedCost = firstCost;
                } else {
                    selectedCost = secondCost;
                }
            }
        }
        if (selectedCost == null) {
            return false;
        }
        return selectedCost.pay(ability, game, sourceId, controllerId, noMana, costToPay);

    }

    @Override
    public boolean isPaid() {
        if (selectedCost != null) {
            return selectedCost.isPaid();
        }
        return false;
    }

    @Override
    public void clearPaid() {
        selectedCost = null;
        firstCost.clearPaid();
        secondCost.clearPaid();
    }

    @Override
    public void setPaid() {
        if (selectedCost != null) {
            selectedCost.setPaid();
        }
    }

    @Override
    public Targets getTargets() {
        if (selectedCost != null) {
            return selectedCost.getTargets();
        }
        return null;
    }

    @Override
    public Cost copy() {
        return new OrCost(this);
    }
}
