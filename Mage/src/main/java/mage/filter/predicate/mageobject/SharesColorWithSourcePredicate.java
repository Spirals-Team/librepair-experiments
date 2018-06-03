/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mage.filter.predicate.mageobject;

import mage.MageObject;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.game.Game;

/**
 *
 * @author LevelX2
 */

public class SharesColorWithSourcePredicate implements ObjectSourcePlayerPredicate<ObjectSourcePlayer<MageObject>> {

    @Override
    public boolean apply(ObjectSourcePlayer<MageObject> input, Game game) {
        MageObject sourceObject = game.getObject(input.getSourceId());
        if (sourceObject != null) {
            return input.getObject().getColor(game).shares(sourceObject.getColor(game));
        }
        return false;

    }

    @Override
    public String toString() {
        return "shares a color";
    }
}