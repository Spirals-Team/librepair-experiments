package io.github.libpasta;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class AppTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( AppTest.class );
    }

    public void testApp()
    {
        String hash = pasta.hash_password("hello123");
        assert pasta.verify_password(hash, "hello123");

        String old_hash = "$2a$10$vI8aWBnW3fID.ZQ4/zo1G.q1lRps.9cGLcZEiGDMVr5yUP1KUOYTa";
        String new_hash = old_hash;
        HashUpdate res = pasta.verify_password_update_hash(old_hash, "my password");
        switch (res.getTag()) {
            case Updated:
                new_hash = res.getUpdated();
                break;
            case Ok:
            case Failed:
                assert false : "expected to migrate"; break;
        }
        System.out.println("New hash: " + new_hash);
        assert pasta.verify_password(new_hash, "my password");


        Argon2i a2 = new Argon2i();
        Config cfg = Config.with_primitive(a2);
        System.out.println(hash);
        res = cfg.migrate_hash(hash);
        String migrated_hash = "";
        switch (res.getTag()) {
            case Updated:
                migrated_hash = res.getUpdated();
                assert migrated_hash.startsWith("$!$argon2i");
                break;
            case Ok:
                assert false : "expected to migrate"; break;
            case Failed:
                assert false : "migration failed"; break;
        }
        res = cfg.verify_password_update_hash(migrated_hash, "not my password");
        assert res.getTag() == HashUpdate.Tag.Failed;
        res = cfg.verify_password_update_hash(migrated_hash, "hello123");
        System.out.println("New hash: " + res.getUpdated());
        assert res.getUpdated().startsWith("$$argon2i");
        System.out.println((char)27 + "[1;32mJava test passed." + (char)27 + "[m");
    }
}
