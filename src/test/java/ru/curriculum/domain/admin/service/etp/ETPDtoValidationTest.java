package ru.curriculum.domain.admin.service.etp;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.curriculum.domain.admin.domain.etp.ETPMock;
import ru.curriculum.service.etp.dto.ETP_DTO;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Date;
import java.util.Set;


public class ETPDtoValidationTest extends Assert {
    private Validator validator;
    private ETPMock etpMock;

    @Before
    public void setUp() {
        this.validator = Validation.buildDefaultValidatorFactory().getValidator();
        this.etpMock = new ETPMock();
    }

    @Test
    public void endDatesLargerThanBeginDates_validationError() {
        ETP_DTO etp = etpMock.getETP_DTO();
        etp.setFullTimeLearningBeginDate(new Date(2));
        etp.setFullTimeLearningEndDate(new Date(1));
        etp.setDistanceLearningBeginDate(new Date(2));
        etp.setDistanceLearningEndDate(new Date(1));

        Set<ConstraintViolation<Object>> violations = validator.validate(etp);

        assertEquals(2, violations.size());
    }

    @Test
    public void endDatesLessThanBeginDates_validationSuccess() {
        ETP_DTO etp = etpMock.getETP_DTO();
        etp.setFullTimeLearningBeginDate(new Date(1));
        etp.setFullTimeLearningEndDate(new Date(2));
        etp.setDistanceLearningBeginDate(new Date(1));
        etp.setDistanceLearningEndDate(new Date(2));

        Set<ConstraintViolation<Object>> violations = validator.validate(etp);

        assertEquals(0, violations.size());
    }

    @Test
    public void datesAreNull_validationFailed() {
        ETP_DTO etp = etpMock.getETP_DTO();
        etp.setFullTimeLearningBeginDate(null);
        etp.setFullTimeLearningEndDate(null);
        etp.setDistanceLearningBeginDate(null);
        etp.setDistanceLearningEndDate(null);

        Set<ConstraintViolation<Object>> violations = validator.validate(etp);

        assertEquals("ETP dates can not be null",4, violations.size());
    }
}
