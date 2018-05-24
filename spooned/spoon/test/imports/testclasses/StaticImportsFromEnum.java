package spoon.test.imports.testclasses;


public class StaticImportsFromEnum {
    static enum DataElement {
        KEY("key"), VALUE("value");
        private final java.lang.String description;

        private DataElement(final java.lang.String description) {
            this.description = description;
        }

        @java.lang.Override
        public java.lang.String toString() {
            return description;
        }
    }

    public spoon.test.imports.testclasses.StaticImportsFromEnum.DataElement[] getValues() {
        return spoon.test.imports.testclasses.StaticImportsFromEnum.DataElement.values();
    }

    public spoon.test.imports.testclasses.ItfWithEnum.Bar[] getBarValues() {
        return spoon.test.imports.testclasses.ItfWithEnum.Bar.values();
    }

    public spoon.test.imports.testclasses.ItfWithEnum.Bar getLip() {
        return spoon.test.imports.testclasses.ItfWithEnum.Bar.Lip;
    }
}

