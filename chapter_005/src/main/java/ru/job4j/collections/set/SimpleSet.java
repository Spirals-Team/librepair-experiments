package ru.job4j.collections.set;

import ru.job4j.collections.list.SimpleContainer;


/**
 * SimpleSEt.
 *@param <E> type of date.
 * @author Hincu Andrei (andreih1981@gmail.com) by 11.10.17;
 * @version $Id$
 * @since 0.1
 */
public class SimpleSet<E> extends SimpleContainer<E> {
    public SimpleSet(int size) {
        super(size);
    }



    @Override
    public void add(E value) {
            if (!super.contains(value)) {
                super.add(value);
            }
        }
    }