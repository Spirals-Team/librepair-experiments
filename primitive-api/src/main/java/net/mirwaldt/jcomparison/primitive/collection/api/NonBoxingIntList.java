package net.mirwaldt.jcomparison.primitive.collection.api;

public interface NonBoxingIntList {
    boolean contains(int element);
    
    int getInt(int index);
    
    int indexOfInt(int element);
    int lastIndexOfInt(int element);
    int size();

    default boolean isEmpty() {
        return size()==0;
    }
    
    IntIterator iterator();
    
    void clear();

    boolean addInt(int element);
    void addInt(int index, int element);
    
    int setInt(int index, int element);

    boolean removeInt(int element);
    int removeIntAt(int index);
        
    void trimToSize();
    
    interface IntIterator {
        boolean hasNext();
        int next();
        void remove();
    }
}
