package by.imsha.domain.validate;

import by.imsha.domain.Mass;
import org.springframework.validation.Errors;
import org.springframework.validation.beanvalidation.CustomValidatorBean;

import javax.validation.executable.ExecutableValidator;

/**
 * @author Alena Misan
 */
public class MassValidator extends CustomValidatorBean {
    @Override
    public ExecutableValidator forExecutables() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Mass.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        super.validate(target, errors);
        System.out.println("PersonValidator.validate() target="+ target +" errors="+ errors);
    }
}
