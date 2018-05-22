package facades;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.*;

public class FacadeHelperTest {

    @Test
    public void checkInvalidCityInput() {
        FacadeHelper helper = new FacadeHelper();
        assertThat(helper.checkValidCityInput(""), is(equalTo(false)));
    }
    @Test
    public void checkInvalidCityInput2() {
        FacadeHelper helper = new FacadeHelper();
        assertThat(helper.checkValidCityInput("  "), is(equalTo(false)));

    }

    /**todo parameterized tests*/
    @Test
    public void isNumeric() {
        FacadeHelper helper = new FacadeHelper();
        assertTrue(helper.isNumeric("123"));
    }
    @Test
    public void isNumeric2() {
        FacadeHelper helper = new FacadeHelper();
        assertTrue(helper.isNumeric("-123"));
    }
    @Test
    public void isNumeric3() {
        FacadeHelper helper = new FacadeHelper();
        assertTrue(!helper.isNumeric("hjkj"));
    }
    
    
    /**
     * Test of checkValidBookTitleInput method, of class CityFacadeHelper.
     */
    @Test
    public void testCheckValidBookTitleInput() {
        FacadeHelper helper = new FacadeHelper();
        assertTrue(!helper.checkValidBookTitleInput(""));
    }
   
     /**
     * Test of checkValidBookTitleInput method, of class CityFacadeHelper.
     */
    @Test
    public void testCheckValidBookTitleInput2() {
        FacadeHelper helper = new FacadeHelper();
        assertTrue(helper.checkValidBookTitleInput(" "));
    }
    
       /**
     * Test of checkValidBookTitleInput method, of class CityFacadeHelper.
     */
    @Test
    public void testCheckValidBookTitleInput3() {
        FacadeHelper helper = new FacadeHelper();
        assertTrue(helper.checkValidBookTitleInput("asd "));
    }
}