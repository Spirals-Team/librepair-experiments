package ru.job4j.litle.worldofwarcraft.random;

import ru.job4j.litle.worldofwarcraft.solgers.Soldier;

import java.util.List;

/**
 * Interface.
 *
 * @author Hincu Andrei (andreih1981@gmail.com) by 24.09.17;
 * @version $Id$
 * @since 0.1
 */
public interface RandomInterface {
    /**
     * Список команды альянса.
     * @return лист.
     */
    List<Soldier> getAlians();

    /**
     * Список команды орды.
     * @return листю
     */
    List<Soldier> getOrda();
}
