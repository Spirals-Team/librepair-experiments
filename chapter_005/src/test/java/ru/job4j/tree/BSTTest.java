package ru.job4j.tree;

import org.junit.Ignore;
import org.junit.Test;

import java.util.Comparator;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class BSTTest {

    public static final class User implements Comparable<User> {

        public String name;

        @Override
        public String toString() {
            return "User{" + "name='" + name + '\'' + '}';
        }

        public User(String name) {
            this.name = name;
        }

        @Override
        public int compareTo(User o) {
            return this.name.compareTo((o.name));
        }
    }

    public Comparator<User> getComparator() {
        return new Comparator<User>() {
            @Override
            public int compare(User o1, User o2) {
                return o1.name.compareTo(o2.name);
            }
        };
    }

    public void fillTheBST(BST bst) {
        User user10 = new User("user10");
        User user20 = new User("user20");
        User user30 = new User("user30");
        User user40 = new User("user40");
        User user50 = new User("user50");

        User user05 = new User("user05");
        User user15 = new User("user15");
        User user25 = new User("user25");
        User user35 = new User("user35");
        User user45 = new User("user45");

        bst.add(user10);
        bst.add(user20);
        bst.add(user30);
        bst.add(user40);
        bst.add(user50);

        bst.add(user45);
        bst.add(user35);
        bst.add(user25);
        bst.add(user15);
        bst.add(user05);

//        а зачем добавлять null узлы?
//        bst.add(null); //проверка ветки кода, что код не упадет с ошибкой. А так смысла не имеет добавлять узлы null, т.к. данный код ничего не добавляется в дерево

    }

//    @Ignore
    @Test
    public void whenAddElementsToBSTTreeWithComparatorThenTheStructureIsAccordingToComparator() {

        Comparator cmp = getComparator();

        BST bst = new BST(cmp);

        fillTheBST(bst);

        boolean result = cmp.compare(bst.getRoot().left.value, new User("user05")) == 0;

        boolean expected = true;


// В версии Hamcrest 1.3 нет готового мэтчера для использования компаратора. Надо писать свой, либо использовать явный компаратор и сравнивать уже результат вызова метода compare. А запись is(expected) эквивалентна is(equalTo(expected)).
//

//        1) В том то и проблема, что у меня есть свой метод:
//
//        public Comparator<User> getComparator() {
//            return new Comparator<User>() {
//                @Override
//                public int compare(User o1, User o2) {
//                    return o1.name.compareTo(o2.name);
//                }
//            };
//        }
//
//        Но при сравнении: "
//        assertThat(result, is(expected));" - программа в него не заходит и в итоге выдает, что объекты различны, хотя наименования одинаковы:
//
//        TEST FAILED:
//
//        java.lang.AssertionError:
//        Expected: is <User{name='user05'}>
//        but: was <User{name='user05'}>
//        Expected :is <User{name='user05'}>
//
//        код теста:
//
//        @Test
//        public void whenAddElementsToBSTTreeWithComparatorThenTheStructureIsAccordingToComparator() {
//
//            Comparator cmp = getComparator();
//
//            BST bst = new BST(cmp);
//
//            fillTheBST(bst);

//        boolean result = cmp.compare(bst.getRoot().left.value, new User("user05")) == 0;
//
//        boolean expected = true;

//            User result = (User) bst.getRoot().left.value;
//
//            User expected = new User("user05");
//
//            assertThat(result, is(expected));
//        }

// у них механизм проверки разный. treemap используешь compareTO = 0 а assertThat equals у тебя они по equals = false.
//        User result = (User) bst.getRoot().left.value;
//
//        User expected = new User("user05");

        assertThat(result, is(expected));
    }
}