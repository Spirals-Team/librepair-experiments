/*
 * Trident - A Multithreaded Server Alternative
 * Copyright 2017 The TridentSDK Team
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.tridentsdk.world.opt;

import net.tridentsdk.base.Vector;

import javax.annotation.concurrent.ThreadSafe;

/**
 * This class represents the data contained in the level.dat
 * file which provides options regarding how the world will
 * be generated.
 *
 * @author The TridentSDK Team
 * @since 0.4-alpha
 */
@ThreadSafe
public interface WorldOpts {
    /**
     * Obtains the game type to which players in the world
     * are set.
     *
     * @return the gamemode
     */
    GameMode getGameMode();

    /**
     * Sets the game type to which players in the world are
     * set.
     *
     * @param mode the new game mode which to set players
     */
    void setGameMode(GameMode mode);

    /**
     * Obtains the difficult to which the world will spawn
     * monsters and animals.
     *
     * @return the world difficulty
     */
    Difficulty getDifficulty();

    /**
     * Sets the world difficulty to the given difficulty.
     *
     * @param difficulty the new difficulty which to set the
     *                   world
     */
    void setDifficulty(Difficulty difficulty);

    /**
     * Checks the world in order to determine whether the
     * difficulty cannot be changed.
     *
     * @return {@code true} to indicate that the difficulty
     *         is locked, {@code false} if it is not
     */
    boolean isDifficultyLocked();

    /**
     * Sets whether the world difficulty is locked or not.
     *
     * @param locked {@code true} to lock, {@code false} to
     *               unlock
     */
    void setDifficultyLocked(boolean locked);

    /**
     * Obtains the XYZ coordinates of this world's spawn
     * position.
     *
     * @return the spawn position
     */
    Vector getSpawn();

    /**
     * Sets the spawn XYZ coordinates to the given vector.
     *
     * @param vector the new spawn position
     */
    void setSpawn(Vector vector);

    /**
     * Obtains the collection of modified game rules.
     *
     * @return the game rules applying to the world
     */
    GameRuleMap getGameRules();

    /*
     * Work(s) cited
     *
     * "Level Format." Minecraft Wiki. Curse Inc.,
     * 27 Apr. 2016. Web. 26 June 2016.
     * <http://minecraft.gamepedia.com/Level_format>
     */
}