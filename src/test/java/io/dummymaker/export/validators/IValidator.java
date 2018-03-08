package io.dummymaker.export.validators;

import io.dummymaker.export.naming.IStrategy;

/**
 * "default comment"
 *
 * @author GoodforGod
 * @since 03.03.2018
 */
public interface IValidator {

    void isSingleDummyValid(String[] dummy);

    void isTwoDummiesValid(String[] dummies);

    void isTwoDummiesValidWithNamingStrategy(String[] dummies, IStrategy strategy);
}
