package spoon.test.imports.testclasses2;


abstract class AbstractMapBasedMultimap<K, V> {
    private class WrappedCollection {
        class WrappedIterator {}
    }

    private class WrappedList extends AbstractMapBasedMultimap<K, V>.WrappedCollection {
        private class WrappedListIterator extends AbstractMapBasedMultimap<K, V>.WrappedCollection.WrappedIterator {}
    }

    private class OtherWrappedList extends AbstractMapBasedMultimap<K, V>.WrappedCollection {
        private class WrappedListIterator extends AbstractMapBasedMultimap<K, V>.OtherWrappedList.WrappedIterator {}

        class WrappedIterator {}
    }
}

