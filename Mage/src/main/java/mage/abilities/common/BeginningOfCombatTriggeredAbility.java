package mage.abilities.common;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.target.targetpointer.FixedTarget;

public class BeginningOfCombatTriggeredAbility extends TriggeredAbilityImpl {

    private TargetController targetController;
    private boolean setTargetPointer;

    public BeginningOfCombatTriggeredAbility(Effect effect, TargetController targetController, boolean isOptional) {
        this(Zone.BATTLEFIELD, effect, targetController, isOptional, false);
    }

    public BeginningOfCombatTriggeredAbility(Zone zone, Effect effect, TargetController targetController, boolean isOptional, boolean setTargetPointer) {
        super(zone, effect, isOptional);
        this.targetController = targetController;
        this.setTargetPointer = setTargetPointer;
    }

    public BeginningOfCombatTriggeredAbility(final BeginningOfCombatTriggeredAbility ability) {
        super(ability);
        this.targetController = ability.targetController;
        this.setTargetPointer = ability.setTargetPointer;
    }

    @Override
    public BeginningOfCombatTriggeredAbility copy() {
        return new BeginningOfCombatTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.BEGIN_COMBAT_STEP_PRE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        switch (targetController) {
            case YOU:
                boolean yours = event.getPlayerId().equals(this.controllerId);
                if (yours && setTargetPointer) {
                    if (getTargets().isEmpty()) {
                        this.getEffects().forEach(effect -> {
                            effect.setTargetPointer(new FixedTarget(event.getPlayerId()));
                        });
                    }
                }
                return yours;
            case OPPONENT:
                if (game.getPlayer(this.controllerId).hasOpponent(event.getPlayerId(), game)) {
                    if (setTargetPointer) {
                        this.getEffects().forEach(effect -> {
                            effect.setTargetPointer(new FixedTarget(event.getPlayerId()));
                        });
                    }
                    return true;
                }
                break;
            case ANY:
                if (setTargetPointer) {
                    this.getEffects().forEach(effect -> {
                        effect.setTargetPointer(new FixedTarget(event.getPlayerId()));
                    });
                }
                return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        switch (targetController) {
            case YOU:
                return "At the beginning of combat on your turn, " + generateZoneString() + super.getRule();
            case OPPONENT:
                return "At the beginning of each opponent's combat step, " + generateZoneString() + super.getRule();
            case ANY:
                return "At the beginning of each combat, " + generateZoneString() + super.getRule();
        }
        return "";
    }

    private String generateZoneString() {
        switch (getZone()) {
            case GRAVEYARD:
                return "if {this} is in your graveyard, ";
        }
        return "";
    }
}
