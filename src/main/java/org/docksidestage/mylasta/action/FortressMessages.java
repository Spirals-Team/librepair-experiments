/*
 * Copyright 2015-2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.docksidestage.mylasta.action;

import org.docksidestage.mylasta.action.FortressLabels;
import org.lastaflute.core.message.UserMessage;

/**
 * The keys for message.
 * @author FreeGen
 */
public class FortressMessages extends FortressLabels {

    /** The serial version UID for object serialization. (Default) */
    private static final long serialVersionUID = 1L;

    /** The key of the message: must be false */
    public static final String CONSTRAINTS_AssertFalse_MESSAGE = "{constraints.AssertFalse.message}";

    /** The key of the message: must be true */
    public static final String CONSTRAINTS_AssertTrue_MESSAGE = "{constraints.AssertTrue.message}";

    /** The key of the message: must be less than ${inclusive == true ? 'or equal to ' : ''}{value} */
    public static final String CONSTRAINTS_DecimalMax_MESSAGE = "{constraints.DecimalMax.message}";

    /** The key of the message: must be greater than ${inclusive == true ? 'or equal to ' : ''}{value} */
    public static final String CONSTRAINTS_DecimalMin_MESSAGE = "{constraints.DecimalMin.message}";

    /** The key of the message: numeric value out of bounds (&lt;{integer} digits&gt;.&lt;{fraction} digits&gt; expected) */
    public static final String CONSTRAINTS_Digits_MESSAGE = "{constraints.Digits.message}";

    /** The key of the message: not a well-formed email address */
    public static final String CONSTRAINTS_Email_MESSAGE = "{constraints.Email.message}";

    /** The key of the message: must be in the future */
    public static final String CONSTRAINTS_Future_MESSAGE = "{constraints.Future.message}";

    /** The key of the message: must be less than or equal to {value} */
    public static final String CONSTRAINTS_Max_MESSAGE = "{constraints.Max.message}";

    /** The key of the message: must be greater than or equal to {value} */
    public static final String CONSTRAINTS_Min_MESSAGE = "{constraints.Min.message}";

    /** The key of the message: may not be empty */
    public static final String CONSTRAINTS_NotBlank_MESSAGE = "{constraints.NotBlank.message}";

    /** The key of the message: may not be empty */
    public static final String CONSTRAINTS_NotEmpty_MESSAGE = "{constraints.NotEmpty.message}";

    /** The key of the message: may not be null */
    public static final String CONSTRAINTS_NotNull_MESSAGE = "{constraints.NotNull.message}";

    /** The key of the message: must be null */
    public static final String CONSTRAINTS_Null_MESSAGE = "{constraints.Null.message}";

    /** The key of the message: must be in the past */
    public static final String CONSTRAINTS_Past_MESSAGE = "{constraints.Past.message}";

    /** The key of the message: must match "{regexp}" */
    public static final String CONSTRAINTS_Pattern_MESSAGE = "{constraints.Pattern.message}";

    /** The key of the message: size must be between {min} and {max} */
    public static final String CONSTRAINTS_Size_MESSAGE = "{constraints.Size.message}";

    /** The key of the message: invalid credit card number */
    public static final String CONSTRAINTS_CreditCardNumber_MESSAGE = "{constraints.CreditCardNumber.message}";

    /** The key of the message: invalid {type} barcode */
    public static final String CONSTRAINTS_EAN_MESSAGE = "{constraints.EAN.message}";

    /** The key of the message: length must be between {min} and {max} */
    public static final String CONSTRAINTS_Length_MESSAGE = "{constraints.Length.message}";

    /** The key of the message: The check digit for ${value} is invalid, Luhn Modulo 10 checksum failed */
    public static final String CONSTRAINTS_LuhnCheck_MESSAGE = "{constraints.LuhnCheck.message}";

    /** The key of the message: The check digit for ${value} is invalid, Modulo 10 checksum failed */
    public static final String CONSTRAINTS_Mod10Check_MESSAGE = "{constraints.Mod10Check.message}";

    /** The key of the message: The check digit for ${value} is invalid, Modulo 11 checksum failed */
    public static final String CONSTRAINTS_Mod11Check_MESSAGE = "{constraints.Mod11Check.message}";

    /** The key of the message: The check digit for ${value} is invalid, ${modType} checksum failed */
    public static final String CONSTRAINTS_ModCheck_MESSAGE = "{constraints.ModCheck.message}";

    /** The key of the message: script expression "{script}" didn't evaluate to true */
    public static final String CONSTRAINTS_ParametersScriptAssert_MESSAGE = "{constraints.ParametersScriptAssert.message}";

    /** The key of the message: must be between {min} and {max} */
    public static final String CONSTRAINTS_Range_MESSAGE = "{constraints.Range.message}";

    /** The key of the message: may have unsafe html content */
    public static final String CONSTRAINTS_SafeHtml_MESSAGE = "{constraints.SafeHtml.message}";

    /** The key of the message: script expression "{script}" didn't evaluate to true */
    public static final String CONSTRAINTS_ScriptAssert_MESSAGE = "{constraints.ScriptAssert.message}";

    /** The key of the message: must be a valid URL */
    public static final String CONSTRAINTS_URL_MESSAGE = "{constraints.URL.message}";

    /** The key of the message: is required */
    public static final String CONSTRAINTS_Required_MESSAGE = "{constraints.Required.message}";

    /** The key of the message: should be {propertyType} */
    public static final String CONSTRAINTS_TypeAny_MESSAGE = "{constraints.TypeAny.message}";

    /** The key of the message: should be number */
    public static final String CONSTRAINTS_TypeInteger_MESSAGE = "{constraints.TypeInteger.message}";

    /** The key of the message: should be number */
    public static final String CONSTRAINTS_TypeLong_MESSAGE = "{constraints.TypeLong.message}";

    /** The key of the message: should be date */
    public static final String CONSTRAINTS_TypeLocalDate_MESSAGE = "{constraints.TypeLocalDate.message}";

    /** The key of the message: should be date-time */
    public static final String CONSTRAINTS_TypeLocalDateTime_MESSAGE = "{constraints.TypeLocalDateTime.message}";

    /** The key of the message: could not login */
    public static final String ERRORS_LOGIN_FAILURE = "{errors.login.failure}";

    /** The key of the message: retry because of illegal transition */
    public static final String ERRORS_APP_ILLEGAL_TRANSITION = "{errors.app.illegal.transition}";

    /** The key of the message: others might be deleted, so retry */
    public static final String ERRORS_APP_DB_ALREADY_DELETED = "{errors.app.db.already.deleted}";

    /** The key of the message: others might be updated, so retry */
    public static final String ERRORS_APP_DB_ALREADY_UPDATED = "{errors.app.db.already.updated}";

    /** The key of the message: already existing data, so retry */
    public static final String ERRORS_APP_DB_ALREADY_EXISTS = "{errors.app.db.already.exists}";

    /** The key of the message: double submit might be requested */
    public static final String ERRORS_APP_DOUBLE_SUBMIT_REQUEST = "{errors.app.double.submit.request}";

    /** The key of the message: the account already exists so input others */
    public static final String ERRORS_SIGNUP_ACCOUNT_ALREADY_EXISTS = "{errors.signup.account.already.exists}";

    /** The key of the message: the product should be on sale if you input the name */
    public static final String ERRORS_PRODUCT_NAME_THEN_ONSALE = "{errors.product.name.then.onsale}";

    /** The key of the message: escaped "あいうえお" and plain "あいうえお" */
    public static final String WHITEBOX_MESSAGE_JAPANESE_CAOS = "{whitebox.message.japanese.caos}";

    /** The key of the message: *[indexed parameter] sea:{0}, land:{1}, piari:{2} */
    public static final String WHITEBOX_MESSAGE_VALUES_INDEXED = "{whitebox.message.values.indexed}";

    /** The key of the message: *[named parameter] sea:{hangar}, land:{showbase}, piari:{celeb} */
    public static final String WHITEBOX_MESSAGE_VALUES_NAMED = "{whitebox.message.values.named}";

    /** The key of the message: *[reversed parameter] sea:{1}, land:{showbase}, piari:{0}, bonvo:{mic}, dstore:{2} */
    public static final String WHITEBOX_MESSAGE_VALUES_REVERSED = "{whitebox.message.values.reversed}";

    /** The key of the message: *[skipped parameter] sea:{1}, land:{4} */
    public static final String WHITEBOX_MESSAGE_VALUES_SKIPPED = "{whitebox.message.values.skipped}";

    /** The key of the message: *[various parameter] sea:{1}, land:{showbase}, piari:{celeb}, bonvo:{00} */
    public static final String WHITEBOX_MESSAGE_VALUES_VARIOUS = "{whitebox.message.values.various}";

    /**
     * Add the created action message for the key 'constraints.AssertFalse.message' with parameters.
     * <pre>
     * message: must be false
     * comment: ---------------
     * </pre>
     * @param property The property name for the message. (NotNull)
     * @return this. (NotNull)
     */
    public FortressMessages addConstraintsAssertFalseMessage(String property) {
        assertPropertyNotNull(property);
        add(property, new UserMessage(CONSTRAINTS_AssertFalse_MESSAGE));
        return this;
    }

    /**
     * Add the created action message for the key 'constraints.AssertTrue.message' with parameters.
     * <pre>
     * message: must be true
     * </pre>
     * @param property The property name for the message. (NotNull)
     * @return this. (NotNull)
     */
    public FortressMessages addConstraintsAssertTrueMessage(String property) {
        assertPropertyNotNull(property);
        add(property, new UserMessage(CONSTRAINTS_AssertTrue_MESSAGE));
        return this;
    }

    /**
     * Add the created action message for the key 'constraints.DecimalMax.message' with parameters.
     * <pre>
     * message: must be less than ${inclusive == true ? 'or equal to ' : ''}{value}
     * </pre>
     * @param property The property name for the message. (NotNull)
     * @param value The parameter value for message. (NotNull)
     * @return this. (NotNull)
     */
    public FortressMessages addConstraintsDecimalMaxMessage(String property, String value) {
        assertPropertyNotNull(property);
        add(property, new UserMessage(CONSTRAINTS_DecimalMax_MESSAGE, value));
        return this;
    }

    /**
     * Add the created action message for the key 'constraints.DecimalMin.message' with parameters.
     * <pre>
     * message: must be greater than ${inclusive == true ? 'or equal to ' : ''}{value}
     * </pre>
     * @param property The property name for the message. (NotNull)
     * @param value The parameter value for message. (NotNull)
     * @return this. (NotNull)
     */
    public FortressMessages addConstraintsDecimalMinMessage(String property, String value) {
        assertPropertyNotNull(property);
        add(property, new UserMessage(CONSTRAINTS_DecimalMin_MESSAGE, value));
        return this;
    }

    /**
     * Add the created action message for the key 'constraints.Digits.message' with parameters.
     * <pre>
     * message: numeric value out of bounds (&lt;{integer} digits&gt;.&lt;{fraction} digits&gt; expected)
     * </pre>
     * @param property The property name for the message. (NotNull)
     * @param fraction The parameter fraction for message. (NotNull)
     * @param integer The parameter integer for message. (NotNull)
     * @return this. (NotNull)
     */
    public FortressMessages addConstraintsDigitsMessage(String property, String fraction, String integer) {
        assertPropertyNotNull(property);
        add(property, new UserMessage(CONSTRAINTS_Digits_MESSAGE, fraction, integer));
        return this;
    }

    /**
     * Add the created action message for the key 'constraints.Email.message' with parameters.
     * <pre>
     * message: not a well-formed email address
     * </pre>
     * @param property The property name for the message. (NotNull)
     * @return this. (NotNull)
     */
    public FortressMessages addConstraintsEmailMessage(String property) {
        assertPropertyNotNull(property);
        add(property, new UserMessage(CONSTRAINTS_Email_MESSAGE));
        return this;
    }

    /**
     * Add the created action message for the key 'constraints.Future.message' with parameters.
     * <pre>
     * message: must be in the future
     * </pre>
     * @param property The property name for the message. (NotNull)
     * @return this. (NotNull)
     */
    public FortressMessages addConstraintsFutureMessage(String property) {
        assertPropertyNotNull(property);
        add(property, new UserMessage(CONSTRAINTS_Future_MESSAGE));
        return this;
    }

    /**
     * Add the created action message for the key 'constraints.Max.message' with parameters.
     * <pre>
     * message: must be less than or equal to {value}
     * </pre>
     * @param property The property name for the message. (NotNull)
     * @param value The parameter value for message. (NotNull)
     * @return this. (NotNull)
     */
    public FortressMessages addConstraintsMaxMessage(String property, String value) {
        assertPropertyNotNull(property);
        add(property, new UserMessage(CONSTRAINTS_Max_MESSAGE, value));
        return this;
    }

    /**
     * Add the created action message for the key 'constraints.Min.message' with parameters.
     * <pre>
     * message: must be greater than or equal to {value}
     * </pre>
     * @param property The property name for the message. (NotNull)
     * @param value The parameter value for message. (NotNull)
     * @return this. (NotNull)
     */
    public FortressMessages addConstraintsMinMessage(String property, String value) {
        assertPropertyNotNull(property);
        add(property, new UserMessage(CONSTRAINTS_Min_MESSAGE, value));
        return this;
    }

    /**
     * Add the created action message for the key 'constraints.NotBlank.message' with parameters.
     * <pre>
     * message: may not be empty
     * </pre>
     * @param property The property name for the message. (NotNull)
     * @return this. (NotNull)
     */
    public FortressMessages addConstraintsNotBlankMessage(String property) {
        assertPropertyNotNull(property);
        add(property, new UserMessage(CONSTRAINTS_NotBlank_MESSAGE));
        return this;
    }

    /**
     * Add the created action message for the key 'constraints.NotEmpty.message' with parameters.
     * <pre>
     * message: may not be empty
     * </pre>
     * @param property The property name for the message. (NotNull)
     * @return this. (NotNull)
     */
    public FortressMessages addConstraintsNotEmptyMessage(String property) {
        assertPropertyNotNull(property);
        add(property, new UserMessage(CONSTRAINTS_NotEmpty_MESSAGE));
        return this;
    }

    /**
     * Add the created action message for the key 'constraints.NotNull.message' with parameters.
     * <pre>
     * message: may not be null
     * </pre>
     * @param property The property name for the message. (NotNull)
     * @return this. (NotNull)
     */
    public FortressMessages addConstraintsNotNullMessage(String property) {
        assertPropertyNotNull(property);
        add(property, new UserMessage(CONSTRAINTS_NotNull_MESSAGE));
        return this;
    }

    /**
     * Add the created action message for the key 'constraints.Null.message' with parameters.
     * <pre>
     * message: must be null
     * </pre>
     * @param property The property name for the message. (NotNull)
     * @return this. (NotNull)
     */
    public FortressMessages addConstraintsNullMessage(String property) {
        assertPropertyNotNull(property);
        add(property, new UserMessage(CONSTRAINTS_Null_MESSAGE));
        return this;
    }

    /**
     * Add the created action message for the key 'constraints.Past.message' with parameters.
     * <pre>
     * message: must be in the past
     * </pre>
     * @param property The property name for the message. (NotNull)
     * @return this. (NotNull)
     */
    public FortressMessages addConstraintsPastMessage(String property) {
        assertPropertyNotNull(property);
        add(property, new UserMessage(CONSTRAINTS_Past_MESSAGE));
        return this;
    }

    /**
     * Add the created action message for the key 'constraints.Pattern.message' with parameters.
     * <pre>
     * message: must match "{regexp}"
     * </pre>
     * @param property The property name for the message. (NotNull)
     * @param regexp The parameter regexp for message. (NotNull)
     * @return this. (NotNull)
     */
    public FortressMessages addConstraintsPatternMessage(String property, String regexp) {
        assertPropertyNotNull(property);
        add(property, new UserMessage(CONSTRAINTS_Pattern_MESSAGE, regexp));
        return this;
    }

    /**
     * Add the created action message for the key 'constraints.Size.message' with parameters.
     * <pre>
     * message: size must be between {min} and {max}
     * </pre>
     * @param property The property name for the message. (NotNull)
     * @param min The parameter min for message. (NotNull)
     * @param max The parameter max for message. (NotNull)
     * @return this. (NotNull)
     */
    public FortressMessages addConstraintsSizeMessage(String property, String min, String max) {
        assertPropertyNotNull(property);
        add(property, new UserMessage(CONSTRAINTS_Size_MESSAGE, min, max));
        return this;
    }

    /**
     * Add the created action message for the key 'constraints.CreditCardNumber.message' with parameters.
     * <pre>
     * message: invalid credit card number
     * comment: -------------------
     * </pre>
     * @param property The property name for the message. (NotNull)
     * @return this. (NotNull)
     */
    public FortressMessages addConstraintsCreditCardNumberMessage(String property) {
        assertPropertyNotNull(property);
        add(property, new UserMessage(CONSTRAINTS_CreditCardNumber_MESSAGE));
        return this;
    }

    /**
     * Add the created action message for the key 'constraints.EAN.message' with parameters.
     * <pre>
     * message: invalid {type} barcode
     * </pre>
     * @param property The property name for the message. (NotNull)
     * @param type The parameter type for message. (NotNull)
     * @return this. (NotNull)
     */
    public FortressMessages addConstraintsEanMessage(String property, String type) {
        assertPropertyNotNull(property);
        add(property, new UserMessage(CONSTRAINTS_EAN_MESSAGE, type));
        return this;
    }

    /**
     * Add the created action message for the key 'constraints.Length.message' with parameters.
     * <pre>
     * message: length must be between {min} and {max}
     * </pre>
     * @param property The property name for the message. (NotNull)
     * @param min The parameter min for message. (NotNull)
     * @param max The parameter max for message. (NotNull)
     * @return this. (NotNull)
     */
    public FortressMessages addConstraintsLengthMessage(String property, String min, String max) {
        assertPropertyNotNull(property);
        add(property, new UserMessage(CONSTRAINTS_Length_MESSAGE, min, max));
        return this;
    }

    /**
     * Add the created action message for the key 'constraints.LuhnCheck.message' with parameters.
     * <pre>
     * message: The check digit for ${value} is invalid, Luhn Modulo 10 checksum failed
     * </pre>
     * @param property The property name for the message. (NotNull)
     * @param value The parameter value for message. (NotNull)
     * @return this. (NotNull)
     */
    public FortressMessages addConstraintsLuhnCheckMessage(String property, String value) {
        assertPropertyNotNull(property);
        add(property, new UserMessage(CONSTRAINTS_LuhnCheck_MESSAGE, value));
        return this;
    }

    /**
     * Add the created action message for the key 'constraints.Mod10Check.message' with parameters.
     * <pre>
     * message: The check digit for ${value} is invalid, Modulo 10 checksum failed
     * </pre>
     * @param property The property name for the message. (NotNull)
     * @param value The parameter value for message. (NotNull)
     * @return this. (NotNull)
     */
    public FortressMessages addConstraintsMod10CheckMessage(String property, String value) {
        assertPropertyNotNull(property);
        add(property, new UserMessage(CONSTRAINTS_Mod10Check_MESSAGE, value));
        return this;
    }

    /**
     * Add the created action message for the key 'constraints.Mod11Check.message' with parameters.
     * <pre>
     * message: The check digit for ${value} is invalid, Modulo 11 checksum failed
     * </pre>
     * @param property The property name for the message. (NotNull)
     * @param value The parameter value for message. (NotNull)
     * @return this. (NotNull)
     */
    public FortressMessages addConstraintsMod11CheckMessage(String property, String value) {
        assertPropertyNotNull(property);
        add(property, new UserMessage(CONSTRAINTS_Mod11Check_MESSAGE, value));
        return this;
    }

    /**
     * Add the created action message for the key 'constraints.ModCheck.message' with parameters.
     * <pre>
     * message: The check digit for ${value} is invalid, ${modType} checksum failed
     * </pre>
     * @param property The property name for the message. (NotNull)
     * @param modType The parameter modType for message. (NotNull)
     * @param value The parameter value for message. (NotNull)
     * @return this. (NotNull)
     */
    public FortressMessages addConstraintsModCheckMessage(String property, String modType, String value) {
        assertPropertyNotNull(property);
        add(property, new UserMessage(CONSTRAINTS_ModCheck_MESSAGE, modType, value));
        return this;
    }

    /**
     * Add the created action message for the key 'constraints.ParametersScriptAssert.message' with parameters.
     * <pre>
     * message: script expression "{script}" didn't evaluate to true
     * </pre>
     * @param property The property name for the message. (NotNull)
     * @param script The parameter script for message. (NotNull)
     * @return this. (NotNull)
     */
    public FortressMessages addConstraintsParametersScriptAssertMessage(String property, String script) {
        assertPropertyNotNull(property);
        add(property, new UserMessage(CONSTRAINTS_ParametersScriptAssert_MESSAGE, script));
        return this;
    }

    /**
     * Add the created action message for the key 'constraints.Range.message' with parameters.
     * <pre>
     * message: must be between {min} and {max}
     * </pre>
     * @param property The property name for the message. (NotNull)
     * @param min The parameter min for message. (NotNull)
     * @param max The parameter max for message. (NotNull)
     * @return this. (NotNull)
     */
    public FortressMessages addConstraintsRangeMessage(String property, String min, String max) {
        assertPropertyNotNull(property);
        add(property, new UserMessage(CONSTRAINTS_Range_MESSAGE, min, max));
        return this;
    }

    /**
     * Add the created action message for the key 'constraints.SafeHtml.message' with parameters.
     * <pre>
     * message: may have unsafe html content
     * </pre>
     * @param property The property name for the message. (NotNull)
     * @return this. (NotNull)
     */
    public FortressMessages addConstraintsSafeHtmlMessage(String property) {
        assertPropertyNotNull(property);
        add(property, new UserMessage(CONSTRAINTS_SafeHtml_MESSAGE));
        return this;
    }

    /**
     * Add the created action message for the key 'constraints.ScriptAssert.message' with parameters.
     * <pre>
     * message: script expression "{script}" didn't evaluate to true
     * </pre>
     * @param property The property name for the message. (NotNull)
     * @param script The parameter script for message. (NotNull)
     * @return this. (NotNull)
     */
    public FortressMessages addConstraintsScriptAssertMessage(String property, String script) {
        assertPropertyNotNull(property);
        add(property, new UserMessage(CONSTRAINTS_ScriptAssert_MESSAGE, script));
        return this;
    }

    /**
     * Add the created action message for the key 'constraints.URL.message' with parameters.
     * <pre>
     * message: must be a valid URL
     * </pre>
     * @param property The property name for the message. (NotNull)
     * @return this. (NotNull)
     */
    public FortressMessages addConstraintsUrlMessage(String property) {
        assertPropertyNotNull(property);
        add(property, new UserMessage(CONSTRAINTS_URL_MESSAGE));
        return this;
    }

    /**
     * Add the created action message for the key 'constraints.Required.message' with parameters.
     * <pre>
     * message: is required
     * comment: --------------------
     * </pre>
     * @param property The property name for the message. (NotNull)
     * @return this. (NotNull)
     */
    public FortressMessages addConstraintsRequiredMessage(String property) {
        assertPropertyNotNull(property);
        add(property, new UserMessage(CONSTRAINTS_Required_MESSAGE));
        return this;
    }

    /**
     * Add the created action message for the key 'constraints.TypeAny.message' with parameters.
     * <pre>
     * message: should be {propertyType}
     * </pre>
     * @param property The property name for the message. (NotNull)
     * @param propertyType The parameter propertyType for message. (NotNull)
     * @return this. (NotNull)
     */
    public FortressMessages addConstraintsTypeAnyMessage(String property, String propertyType) {
        assertPropertyNotNull(property);
        add(property, new UserMessage(CONSTRAINTS_TypeAny_MESSAGE, propertyType));
        return this;
    }

    /**
     * Add the created action message for the key 'constraints.TypeInteger.message' with parameters.
     * <pre>
     * message: should be number
     * </pre>
     * @param property The property name for the message. (NotNull)
     * @return this. (NotNull)
     */
    public FortressMessages addConstraintsTypeIntegerMessage(String property) {
        assertPropertyNotNull(property);
        add(property, new UserMessage(CONSTRAINTS_TypeInteger_MESSAGE));
        return this;
    }

    /**
     * Add the created action message for the key 'constraints.TypeLong.message' with parameters.
     * <pre>
     * message: should be number
     * </pre>
     * @param property The property name for the message. (NotNull)
     * @return this. (NotNull)
     */
    public FortressMessages addConstraintsTypeLongMessage(String property) {
        assertPropertyNotNull(property);
        add(property, new UserMessage(CONSTRAINTS_TypeLong_MESSAGE));
        return this;
    }

    /**
     * Add the created action message for the key 'constraints.TypeLocalDate.message' with parameters.
     * <pre>
     * message: should be date
     * </pre>
     * @param property The property name for the message. (NotNull)
     * @return this. (NotNull)
     */
    public FortressMessages addConstraintsTypeLocalDateMessage(String property) {
        assertPropertyNotNull(property);
        add(property, new UserMessage(CONSTRAINTS_TypeLocalDate_MESSAGE));
        return this;
    }

    /**
     * Add the created action message for the key 'constraints.TypeLocalDateTime.message' with parameters.
     * <pre>
     * message: should be date-time
     * </pre>
     * @param property The property name for the message. (NotNull)
     * @return this. (NotNull)
     */
    public FortressMessages addConstraintsTypeLocalDateTimeMessage(String property) {
        assertPropertyNotNull(property);
        add(property, new UserMessage(CONSTRAINTS_TypeLocalDateTime_MESSAGE));
        return this;
    }

    /**
     * Add the created action message for the key 'errors.login.failure' with parameters.
     * <pre>
     * message: could not login
     * comment: - - - - - - - - - -/
     * </pre>
     * @param property The property name for the message. (NotNull)
     * @return this. (NotNull)
     */
    public FortressMessages addErrorsLoginFailure(String property) {
        assertPropertyNotNull(property);
        add(property, new UserMessage(ERRORS_LOGIN_FAILURE));
        return this;
    }

    /**
     * Add the created action message for the key 'errors.app.illegal.transition' with parameters.
     * <pre>
     * message: retry because of illegal transition
     * </pre>
     * @param property The property name for the message. (NotNull)
     * @return this. (NotNull)
     */
    public FortressMessages addErrorsAppIllegalTransition(String property) {
        assertPropertyNotNull(property);
        add(property, new UserMessage(ERRORS_APP_ILLEGAL_TRANSITION));
        return this;
    }

    /**
     * Add the created action message for the key 'errors.app.db.already.deleted' with parameters.
     * <pre>
     * message: others might be deleted, so retry
     * </pre>
     * @param property The property name for the message. (NotNull)
     * @return this. (NotNull)
     */
    public FortressMessages addErrorsAppDbAlreadyDeleted(String property) {
        assertPropertyNotNull(property);
        add(property, new UserMessage(ERRORS_APP_DB_ALREADY_DELETED));
        return this;
    }

    /**
     * Add the created action message for the key 'errors.app.db.already.updated' with parameters.
     * <pre>
     * message: others might be updated, so retry
     * </pre>
     * @param property The property name for the message. (NotNull)
     * @return this. (NotNull)
     */
    public FortressMessages addErrorsAppDbAlreadyUpdated(String property) {
        assertPropertyNotNull(property);
        add(property, new UserMessage(ERRORS_APP_DB_ALREADY_UPDATED));
        return this;
    }

    /**
     * Add the created action message for the key 'errors.app.db.already.exists' with parameters.
     * <pre>
     * message: already existing data, so retry
     * </pre>
     * @param property The property name for the message. (NotNull)
     * @return this. (NotNull)
     */
    public FortressMessages addErrorsAppDbAlreadyExists(String property) {
        assertPropertyNotNull(property);
        add(property, new UserMessage(ERRORS_APP_DB_ALREADY_EXISTS));
        return this;
    }

    /**
     * Add the created action message for the key 'errors.app.double.submit.request' with parameters.
     * <pre>
     * message: double submit might be requested
     * </pre>
     * @param property The property name for the message. (NotNull)
     * @return this. (NotNull)
     */
    public FortressMessages addErrorsAppDoubleSubmitRequest(String property) {
        assertPropertyNotNull(property);
        add(property, new UserMessage(ERRORS_APP_DOUBLE_SUBMIT_REQUEST));
        return this;
    }

    /**
     * Add the created action message for the key 'errors.signup.account.already.exists' with parameters.
     * <pre>
     * message: the account already exists so input others
     * </pre>
     * @param property The property name for the message. (NotNull)
     * @return this. (NotNull)
     */
    public FortressMessages addErrorsSignupAccountAlreadyExists(String property) {
        assertPropertyNotNull(property);
        add(property, new UserMessage(ERRORS_SIGNUP_ACCOUNT_ALREADY_EXISTS));
        return this;
    }

    /**
     * Add the created action message for the key 'errors.product.name.then.onsale' with parameters.
     * <pre>
     * message: the product should be on sale if you input the name
     * </pre>
     * @param property The property name for the message. (NotNull)
     * @return this. (NotNull)
     */
    public FortressMessages addErrorsProductNameThenOnsale(String property) {
        assertPropertyNotNull(property);
        add(property, new UserMessage(ERRORS_PRODUCT_NAME_THEN_ONSALE));
        return this;
    }

    /**
     * Add the created action message for the key 'whitebox.message.japanese.caos' with parameters.
     * <pre>
     * message: escaped "あいうえお" and plain "あいうえお"
     * </pre>
     * @param property The property name for the message. (NotNull)
     * @return this. (NotNull)
     */
    public FortressMessages addWhiteboxMessageJapaneseCaos(String property) {
        assertPropertyNotNull(property);
        add(property, new UserMessage(WHITEBOX_MESSAGE_JAPANESE_CAOS));
        return this;
    }

    /**
     * Add the created action message for the key 'whitebox.message.values.indexed' with parameters.
     * <pre>
     * message: *[indexed parameter] sea:{0}, land:{1}, piari:{2}
     * </pre>
     * @param property The property name for the message. (NotNull)
     * @param arg0 The parameter arg0 for message. (NotNull)
     * @param arg1 The parameter arg1 for message. (NotNull)
     * @param arg2 The parameter arg2 for message. (NotNull)
     * @return this. (NotNull)
     */
    public FortressMessages addWhiteboxMessageValuesIndexed(String property, String arg0, String arg1, String arg2) {
        assertPropertyNotNull(property);
        add(property, new UserMessage(WHITEBOX_MESSAGE_VALUES_INDEXED, arg0, arg1, arg2));
        return this;
    }

    /**
     * Add the created action message for the key 'whitebox.message.values.named' with parameters.
     * <pre>
     * message: *[named parameter] sea:{hangar}, land:{showbase}, piari:{celeb}
     * </pre>
     * @param property The property name for the message. (NotNull)
     * @param celeb The parameter celeb for message. (NotNull)
     * @param hangar The parameter hangar for message. (NotNull)
     * @param showbase The parameter showbase for message. (NotNull)
     * @return this. (NotNull)
     */
    public FortressMessages addWhiteboxMessageValuesNamed(String property, String celeb, String hangar, String showbase) {
        assertPropertyNotNull(property);
        add(property, new UserMessage(WHITEBOX_MESSAGE_VALUES_NAMED, celeb, hangar, showbase));
        return this;
    }

    /**
     * Add the created action message for the key 'whitebox.message.values.reversed' with parameters.
     * <pre>
     * message: *[reversed parameter] sea:{1}, land:{showbase}, piari:{0}, bonvo:{mic}, dstore:{2}
     * </pre>
     * @param property The property name for the message. (NotNull)
     * @param arg0 The parameter arg0 for message. (NotNull)
     * @param arg1 The parameter arg1 for message. (NotNull)
     * @param arg2 The parameter arg2 for message. (NotNull)
     * @param mic The parameter mic for message. (NotNull)
     * @param showbase The parameter showbase for message. (NotNull)
     * @return this. (NotNull)
     */
    public FortressMessages addWhiteboxMessageValuesReversed(String property, String arg0, String arg1, String arg2, String mic, String showbase) {
        assertPropertyNotNull(property);
        add(property, new UserMessage(WHITEBOX_MESSAGE_VALUES_REVERSED, arg0, arg1, arg2, mic, showbase));
        return this;
    }

    /**
     * Add the created action message for the key 'whitebox.message.values.skipped' with parameters.
     * <pre>
     * message: *[skipped parameter] sea:{1}, land:{4}
     * </pre>
     * @param property The property name for the message. (NotNull)
     * @param arg1 The parameter arg1 for message. (NotNull)
     * @param arg4 The parameter arg4 for message. (NotNull)
     * @return this. (NotNull)
     */
    public FortressMessages addWhiteboxMessageValuesSkipped(String property, String arg1, String arg4) {
        assertPropertyNotNull(property);
        add(property, new UserMessage(WHITEBOX_MESSAGE_VALUES_SKIPPED, arg1, arg4));
        return this;
    }

    /**
     * Add the created action message for the key 'whitebox.message.values.various' with parameters.
     * <pre>
     * message: *[various parameter] sea:{1}, land:{showbase}, piari:{celeb}, bonvo:{00}
     * </pre>
     * @param property The property name for the message. (NotNull)
     * @param arg00 The parameter arg00 for message. (NotNull)
     * @param arg1 The parameter arg1 for message. (NotNull)
     * @param celeb The parameter celeb for message. (NotNull)
     * @param showbase The parameter showbase for message. (NotNull)
     * @return this. (NotNull)
     */
    public FortressMessages addWhiteboxMessageValuesVarious(String property, String arg00, String arg1, String celeb, String showbase) {
        assertPropertyNotNull(property);
        add(property, new UserMessage(WHITEBOX_MESSAGE_VALUES_VARIOUS, arg00, arg1, celeb, showbase));
        return this;
    }
}
