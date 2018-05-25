package spoon.test.imports.testclasses;


import static spoon.test.imports.testclasses.ItfWithEnum.Bar.Lip;
import static spoon.test.imports.testclasses.ItfWithEnum.Bar.values;


public class StaticImportsFromEnum {
    static enum DataElement {
        KEY("key"), VALUE("value");
        private final String description;

        private DataElement(final String description) {
            this.description = description;
        }

        @Override
        public String toString() {
            return description;
        }
    }

    public StaticImportsFromEnum.DataElement[] getValues() {
        return StaticImportsFromEnum.DataElement.values();
    }

    public ItfWithEnum.Bar[] getBarValues() {
        return values();
    }

    public ItfWithEnum.Bar getLip() {
        return Lip;
    }
}

