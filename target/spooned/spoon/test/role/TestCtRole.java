package spoon.test.role;


public class TestCtRole {
    @org.junit.Test
    public void testGetCtRoleByName() {
        java.lang.String name = "DECLARING_TYPE";
        org.junit.Assert.assertEquals(spoon.reflect.path.CtRole.DECLARING_TYPE, spoon.reflect.path.CtRole.fromName(name));
        name = "declaringType";
        org.junit.Assert.assertEquals(spoon.reflect.path.CtRole.DECLARING_TYPE, spoon.reflect.path.CtRole.fromName(name));
        name = "declaringtype";
        org.junit.Assert.assertEquals(spoon.reflect.path.CtRole.DECLARING_TYPE, spoon.reflect.path.CtRole.fromName(name));
        name = "declaring_type";
        org.junit.Assert.assertNull(spoon.reflect.path.CtRole.fromName(name));
        for (spoon.reflect.path.CtRole role : spoon.reflect.path.CtRole.values()) {
            org.junit.Assert.assertEquals(role, spoon.reflect.path.CtRole.fromName(role.name().replaceAll("_", "").toLowerCase()));
        }
    }

    @org.junit.Test
    public void testCtRoleGetSubRolesNotNull() {
        for (spoon.reflect.path.CtRole role : spoon.reflect.path.CtRole.values()) {
            org.junit.Assert.assertNotNull(role.getSubRoles());
        }
    }

    @org.junit.Test
    public void testCtRoleSubRoleMatchesWithSuperRole() {
        int countOfSubRoles = 0;
        for (spoon.reflect.path.CtRole role : spoon.reflect.path.CtRole.values()) {
            for (spoon.reflect.path.CtRole subRole : role.getSubRoles()) {
                countOfSubRoles++;
                org.junit.Assert.assertSame(role, subRole.getSuperRole());
            }
            if ((role.getSuperRole()) != null) {
                org.junit.Assert.assertTrue(role.getSuperRole().getSubRoles().contains(role));
            }
        }
        org.junit.Assert.assertTrue((countOfSubRoles > 0));
    }
}

