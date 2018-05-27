package ru.job4j.game;

import org.junit.Test;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 */
public class PositionTest {
   @Test
   public void whenCreatedNewPosition() {
       int x = 5;
       int y = 10;
       Position position = new Position(x, y);
       assertThat(position.getX(), is(x));
       assertThat(position.getY(), is(y));
       Position newPosition = new Position(x, y);
       assertThat(position.equals(newPosition), is(true));
   }
}