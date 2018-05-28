package spoon.test.staticFieldAccess2;


public class AmbiguousImplicitFieldReference {
    public static java.lang.String AmbiguousImplicitFieldReference = "c1";

    public java.lang.String memberField;

    public java.lang.String getMemberField() {
        return memberField;
    }

    public void setMemberField(java.lang.String p_memberField) {
        memberField = p_memberField;
    }

    public void setMemberField2(java.lang.String memberField) {
        this.memberField = memberField;
    }

    public void testLocalMethodInvocations() {
        getMemberField();
    }
}

