package spoon.support.reflect.reference;


public class CtWildcardStaticTypeMemberReferenceImpl extends spoon.support.reflect.reference.CtTypeReferenceImpl {
    public CtWildcardStaticTypeMemberReferenceImpl() {
        super();
    }

    @java.lang.Override
    public <T extends spoon.reflect.reference.CtReference> T setSimpleName(java.lang.String newName) {
        if (!(newName.endsWith(".*"))) {
            newName += ".*";
        }
        return super.setSimpleName(newName);
    }

    @java.lang.Override
    public spoon.support.reflect.reference.CtWildcardStaticTypeMemberReferenceImpl clone() {
        return ((spoon.support.reflect.reference.CtWildcardStaticTypeMemberReferenceImpl) (getFactory().Type().createWildcardStaticTypeMemberReference(this)));
    }
}

