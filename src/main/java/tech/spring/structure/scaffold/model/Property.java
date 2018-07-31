package tech.spring.structure.scaffold.model;

import static tech.spring.structure.utility.StringUtility.formalize;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

public class Property {

    private String name;

    private String clazz;

    private String gloss;

    private String type;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String help;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String autocomplete;

    private boolean autofocus;

    private boolean hidden;

    private boolean disabled;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<Object> options;

    private List<Validation> validations;

    public Property() {
        options = new ArrayList<Object>();
        validations = new ArrayList<Validation>();
    }

    public Property(String name, String clazz) {
        this();
        this.name = name;
        this.clazz = clazz;
        this.gloss = formalize(name);
        this.type = "text";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClazz() {
        return clazz;
    }

    public void setClazz(String clazz) {
        this.clazz = clazz;
    }

    public String getGloss() {
        return gloss;
    }

    public void setGloss(String gloss) {
        this.gloss = gloss;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getHelp() {
        return help;
    }

    public void setHelp(String help) {
        this.help = help;
    }

    public String getAutocomplete() {
        return autocomplete;
    }

    public void setAutocomplete(String autocomplete) {
        this.autocomplete = autocomplete;
    }

    public boolean isAutofocus() {
        return autofocus;
    }

    public void setAutofocus(boolean autofocus) {
        this.autofocus = autofocus;
    }

    public boolean isHidden() {
        return hidden;
    }

    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public List<Object> getOptions() {
        return options;
    }

    public void setOptions(List<Object> options) {
        this.options = options;
    }

    public List<Validation> getValidations() {
        return validations;
    }

    public void setValidations(List<Validation> validations) {
        this.validations = validations;
    }

    public void addValidation(Validation validation) {
        this.validations.add(validation);
    }

    public void addValidations(List<Validation> validations) {
        this.validations.addAll(validations);
    }

    public static Property of(String name, String clazz) {
        return new Property(name, clazz);
    }

}
