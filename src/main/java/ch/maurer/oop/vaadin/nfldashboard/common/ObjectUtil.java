package ch.maurer.oop.vaadin.nfldashboard.common;

public final class ObjectUtil {

    private ObjectUtil() {
    }

    public static boolean isFalse(Object object) {
        return object.toString().equals("false");
    }
}
