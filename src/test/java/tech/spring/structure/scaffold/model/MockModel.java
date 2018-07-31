package tech.spring.structure.scaffold.model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.validation.constraints.AssertFalse;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Email;
import javax.validation.constraints.Future;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import tech.spring.structure.scaffold.ScaffoldAuthorize;
import tech.spring.structure.scaffold.ScaffoldProperty;
import tech.spring.structure.scaffold.Scaffolding;

// @formatter:off
@Scaffolding(
    order = {
        "username",
        "password",
        "unsupported",
        "names",
        "type",
        "parent",
        "child",
        "signed",
        "notTrue",
        "notFalse",
        "decimalMin",
        "decimalMax",
        "digits",
        "intMin",
        "intMax",
        "mustBeNull",
        "mustNotBeNull",
        "inTheFuture",
        "inThePast"
    }
)
// @formatter:on
@ScaffoldAuthorize("hasRole('SUPER_ADMIN')")
public class MockModel {

    @ScaffoldProperty(autocomplete = "username", autofocus = true)
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[~!@#$%^]).{4,32}$")
    @Size(min = 4, max = 32)
    private String username;

    @ScaffoldProperty(type = "password", autocomplete = "new-password")
    @Size(min = 4, max = 32)
    private String password;

    @ScaffoldProperty(type = "select")
    private MockType type;

    // @formatter:off
    @ScaffoldProperty(
        disabled = true,
        type = "select"
    )
    // @formatter:on
    private MockRelation parent;

    // @formatter:off
    @ScaffoldProperty(
        disabled = true,
        type = "select"
    )
    // @formatter:on
    private MockRelation child;

    // @formatter:off
    @ScaffoldProperty(
        type = "checkbox",
        gloss = "Sign Here",
        help = "Click the checkbox!"
    )
    // @formatter:on
    private boolean signed;

    @ScaffoldProperty(type = "checkbox")
    @AssertFalse(message = "This can not be true!")
    private boolean notTrue;

    @ScaffoldProperty(type = "checkbox")
    @AssertTrue(message = "This can not be false!")
    private boolean notFalse;

    @ScaffoldProperty(type = "checkbox")
    @DecimalMin(value = "1.0", message = "This can not be less than 1.0!")
    private double decimalMin;

    @ScaffoldProperty(type = "checkbox")
    @DecimalMax(value = "100.0", message = "This can not be more than 100.0!")
    private double decimalMax;

    @ScaffoldProperty
    @Digits(message = "This must fraction with 2 decimal places!", fraction = 2, integer = 2)
    private float digits;

    @ScaffoldProperty
    @Min(10)
    private int intMin;

    @ScaffoldProperty
    @Max(100)
    private int intMax;

    @ScaffoldProperty
    @Null
    private Object mustBeNull;

    @ScaffoldProperty
    @NotNull
    private Object mustNotBeNull;

    @ScaffoldProperty
    @Future
    private Calendar inTheFuture;

    @ScaffoldProperty
    @Past
    private Calendar inThePast;

    @ScaffoldProperty
    @Email
    private String unsupported;

    @ScaffoldProperty
    private List<String> names;

    public MockModel() {
        this.username = "username";
        this.password = "password";
        this.type = MockType.FOO;
        this.parent = new MockRelation("Welcome", "Home");
        this.child = new MockRelation("Hello", "World");
        this.notTrue = false;
        this.notFalse = true;
        this.decimalMin = 1.0;
        this.decimalMax = 100.0;
        this.digits = 99.99f;
        this.intMin = 11;
        this.intMax = 99;
        this.mustBeNull = null;
        this.mustNotBeNull = (Object) "not null";
        Calendar future = Calendar.getInstance();
        future.add(Calendar.DATE, Integer.MAX_VALUE);
        this.inTheFuture = future;
        Calendar past = Calendar.getInstance();
        past.add(Calendar.DATE, -Integer.MAX_VALUE);
        this.inThePast = past;
        this.unsupported = "unsupported@email.com";
        this.names = new ArrayList<String>();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public MockType getType() {
        return type;
    }

    public void setType(MockType type) {
        this.type = type;
    }

    public MockRelation getParent() {
        return parent;
    }

    public void setParent(MockRelation parent) {
        this.parent = parent;
    }

    public MockRelation getChild() {
        return child;
    }

    public void setChild(MockRelation child) {
        this.child = child;
    }

    public boolean isSigned() {
        return signed;
    }

    public void setSigned(boolean signed) {
        this.signed = signed;
    }

    public boolean isNotTrue() {
        return notTrue;
    }

    public void setNotTrue(boolean notTrue) {
        this.notTrue = notTrue;
    }

    public boolean isNotFalse() {
        return notFalse;
    }

    public void setNotFalse(boolean notFalse) {
        this.notFalse = notFalse;
    }

    public double getDecimalMin() {
        return decimalMin;
    }

    public void setDecimalMin(double decimalMin) {
        this.decimalMin = decimalMin;
    }

    public double getDecimalMax() {
        return decimalMax;
    }

    public void setDecimalMax(double decimalMax) {
        this.decimalMax = decimalMax;
    }

    public float getDigits() {
        return digits;
    }

    public void setDigits(float digits) {
        this.digits = digits;
    }

    public int getIntMin() {
        return intMin;
    }

    public void setIntMin(int intMin) {
        this.intMin = intMin;
    }

    public int getIntMax() {
        return intMax;
    }

    public void setIntMax(int intMax) {
        this.intMax = intMax;
    }

    public Object getMustBeNull() {
        return mustBeNull;
    }

    public void setMustBeNull(Object mustBeNull) {
        this.mustBeNull = mustBeNull;
    }

    public Object getMustNotBeNull() {
        return mustNotBeNull;
    }

    public void setMustNotBeNull(Object mustNotBeNull) {
        this.mustNotBeNull = mustNotBeNull;
    }

    public Calendar getInTheFuture() {
        return inTheFuture;
    }

    public void setInTheFuture(Calendar inTheFuture) {
        this.inTheFuture = inTheFuture;
    }

    public Calendar getInThePast() {
        return inThePast;
    }

    public void setInThePast(Calendar inThePast) {
        this.inThePast = inThePast;
    }

    public String getUnsupported() {
        return unsupported;
    }

    public void setUnsupported(String unsupported) {
        this.unsupported = unsupported;
    }

    public List<String> getNames() {
        return names;
    }

    public void setNames(List<String> names) {
        this.names = names;
    }

    public enum MockType {
        FOO, BAR, BIZ, BAZ
    }

    public class MockRelation {
        private final String first;

        private final String second;

        public MockRelation(String first, String second) {
            this.first = first;
            this.second = second;
        }

        public String getFirst() {
            return first;
        }

        public String getSecond() {
            return second;
        }

    }

}
