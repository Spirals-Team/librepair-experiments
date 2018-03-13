package ru.job4j.tree;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class TreeTest {

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

    @Test
    public void whenAddAnnElementWithParentAndChildToTreeThenOneCanGetParentAndChildFromTree() {

        User parent = new User("Parant");
        User child = new User("Child");

        Tree tree = new Tree();

        if (tree.add(parent, child)) {

//            Поиск в глубину (англ. Depth-first search, DFS)
//            Поиск в ширину (англ. breadth-first search, BFS)
            //        1) использование аргумента-флага (itIsBFS) в методе findNodeBFSorDFS. Считается плохим стилем программирования.
//        Это свидетельствует о том, что метод выполняет более одной операции.
            Tree.Node result = tree.findNodeBFSorDFS(tree.getRoot(), parent, true); // findNodeDFS(tree.getRoot(), parent);

            List<Tree.Node> childrenList = new ArrayList<>();

            List<User> childrenChildrenList = new ArrayList<>();

            Tree.Node childNode = new Tree.Node(childrenChildrenList, child);

            childrenList.add(childNode);

            Tree.Node expected = new Tree.Node(childrenList, parent);

            assertThat(result, is(expected));

        }

    }

    @Test
    public void whenAddAnnElementsWithParentAndChildToTreeThenOneCanGetAnyParentAndChildFromTree() {

        User parent = new User("Parant");

        User child = new User("Child");
        User child2 = new User("Child2");
        User child3 = new User("Child3");

        User child2Child = new User("child2Child");
        User child2Child2 = new User("child2Child2");


        User child2Child2Child = new User("child2Child2Child");

        Tree tree = new Tree();

        tree.add(parent, child);
        tree.add(parent, child2);
        tree.add(parent, child3);

        tree.add(child2, child2Child);
        tree.add(child2, child2Child2);

        tree.add(child2Child2, child2Child2Child);

//        Поиск в глубину (англ. Depth-first search, DFS)
//        Поиск в ширину (англ. breadth-first search, BFS)
        //        1) использование аргумента-флага (itIsBFS) в методе findNodeBFSorDFS. Считается плохим стилем программирования.
//        Это свидетельствует о том, что метод выполняет более одной операции.
        Tree.Node result = tree.findNodeBFSorDFS(tree.getRoot(), child2Child2, true); //findNodeDFS(tree.getRoot(), child2Child2);

        List<Tree.Node> childrenList = new ArrayList<>();

        List<User> childrenChildrenList = new ArrayList<>();

        Tree.Node childNode = new Tree.Node(childrenChildrenList, child2Child2Child);

        childrenList.add(childNode);

        Tree.Node expected = new Tree.Node(childrenList, child2Child2);

        assertThat(result, is(expected));
    }

    @Test
    public void whenFillTheTreeThenOneCanIterateIt() {

        Tree tree = new Tree(getComparator());

        fillTheTree(tree);

        Iterator iterator = tree.iterator();

        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
    }

    @Test
    public void whenFillTheTreeAccordingComparatorThenOneCanIterateItAccordingComparator() {

        Tree tree = new Tree(getComparator());

        fillTheTree(tree);

        Iterator iterator = tree.iterator();

        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
    }

    @Test
    public void whenFillTheTreeAccordingInternalUserComparatorThenOneCanIterateItAccordingInternalUserComparator() {

//        Tree tree = new Tree(getComparator());
//        2) в случае если в конструктор не передается компаратор, вы сравниваете объекты через ссылочное равенство
//        if (current.getValue() == value). Это не верно. Вы же параметризуете дерево как E extends Comparable<E>,
//        поэтому для корректной работы необходимо вызывать метод compareTo
        Tree tree = new Tree();

        fillTheTree(tree);

        Iterator iterator = tree.iterator();

        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
    }

    @Test
    public void whenThereIsAnAttemtToAddUnuniqueElemenThenMethodReturnsFalse() {

        Tree tree = new Tree(getComparator());

        fillTheTree(tree);

        User child3 = new User("Child3");

        User unUnique = new User("child2Child");

        User unique = new User("child2ChildUnique");

        boolean result = tree.add(child3, unique);

        boolean expected = true;

        assertThat(result, is(expected));

        boolean result2 = tree.add(child3, unUnique);

        boolean expected2 = false;

        assertThat(result2, is(expected2));

    }

    @Test public void whenThereIsMoreThenTwoChildsAtTheAnyParantTheTreeIsNotBinary() {

        Tree tree = new Tree(getComparator());

        fillTheTree(tree);

        boolean result = tree.isBinary();

        boolean expected = false;

        assertThat(result, is(expected));
    }

    public Comparator<User> getComparator() {
        return new Comparator<User>() {
            @Override
            public int compare(User o1, User o2) {
                return o1.name.compareTo(o2.name);
            }
        };
    }

    public void fillTheTree(Tree tree) {

        User parent = new User("Parant");

        User child = new User("Child");
        User child2 = new User("Child2");
        User child3 = new User("Child3");

        User child2Child = new User("child2Child");
        User child2Child2 = new User("child2Child2");


        User child2Child2Child = new User("child2Child2Child");

        tree.add(parent, child);
        tree.add(parent, child2);
        tree.add(parent, child3);

        tree.add(child2, child2Child);
        tree.add(child2, child2Child2);

        tree.add(child2Child2, child2Child2Child);

    }
}