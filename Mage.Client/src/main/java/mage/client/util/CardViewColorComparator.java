

package mage.client.util;

import java.util.Comparator;
import mage.view.CardView;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class CardViewColorComparator implements Comparator<CardView> {

    @Override
    public int compare(CardView o1, CardView o2) {
        return o1.getColor().compareTo(o2.getColor());
    }

}