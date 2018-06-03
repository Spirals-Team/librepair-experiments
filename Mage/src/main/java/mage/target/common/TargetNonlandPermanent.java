

package mage.target.common;

import mage.filter.common.FilterNonlandPermanent;
import mage.target.TargetPermanent;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class TargetNonlandPermanent extends TargetPermanent {

    public TargetNonlandPermanent() {
        this(1, 1, false);
    }

    public TargetNonlandPermanent(FilterNonlandPermanent filter) {
        this(1, 1, filter, false);
    }

    public TargetNonlandPermanent(int numTargets) {
        this(numTargets, numTargets, new FilterNonlandPermanent(), false);
    }

    public TargetNonlandPermanent(int minNumTargets, int maxNumTargets, boolean notTarget) {
        this(minNumTargets, maxNumTargets, new FilterNonlandPermanent(), notTarget);
    }

    public TargetNonlandPermanent(int minNumTargets, int maxNumTargets, FilterNonlandPermanent filter, boolean notTarget) {
        super(minNumTargets, maxNumTargets, filter, notTarget);
    }

    public TargetNonlandPermanent(final TargetNonlandPermanent target) {
        super(target);
    }

    @Override
    public TargetNonlandPermanent copy() {
        return new TargetNonlandPermanent(this);
    }
}
