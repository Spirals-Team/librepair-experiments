package ch.maurer.oop.vaadin.nfldashboard.common.business;

import com.vaadin.flow.templatemodel.ModelConverter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class LocalDateToStringConverter implements ModelConverter<LocalDate, String> {

    public static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("MM/dd/yyyy");

    @Override
    public LocalDate toModel(String presentationValue) {
        return LocalDate.parse(presentationValue, DATE_FORMAT);
    }

    @Override
    public String toPresentation(LocalDate modelValue) {
        return modelValue == null ? null : modelValue.format(DATE_FORMAT);
    }

}
