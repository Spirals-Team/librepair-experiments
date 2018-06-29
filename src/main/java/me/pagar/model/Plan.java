package me.pagar.model;

import java.util.Collection;

import javax.ws.rs.HttpMethod;

import com.google.gson.JsonObject;
import com.google.gson.annotations.Expose;
import com.google.gson.reflect.TypeToken;

import me.pagar.model.Transaction.PaymentMethod;
import me.pagar.util.JSONUtils;

public class Plan extends PagarMeModel<String> {

    @Expose
    private Integer amount;
    @Expose
    private Integer days;
    @Expose
    private String name;
    @Expose
    private Integer trialDays;
    @Expose
    private Collection<PaymentMethod> paymentMethods;
    @Expose
    private String color;
    @Expose
    private Integer charges;
    @Expose
    private Integer installments;
    @Expose
    private Integer invoiceReminder;

    public Plan save() throws PagarMeException {
        final Plan saved = super.save(getClass());
        copy(saved);
        return saved;
    }

    public Plan find(String id) throws PagarMeException {
        final PagarMeRequest request = new PagarMeRequest(HttpMethod.GET,
                String.format("/%s/%s", getClassName(), id));
        final Plan other = JSONUtils.getAsObject((JsonObject) request.execute(), Plan.class);
        copy(other);
        flush();
        return other;
    }

    public Collection<Plan> findCollection(Integer totalPerPage, Integer page) throws PagarMeException {
        return JSONUtils.getAsList(super.paginate(totalPerPage, page), new TypeToken<Collection<Plan>>() {
        }.getType());
    }

    @Deprecated
    public void setCreationParameters(Integer amount, Integer days, String name){
        this.amount = amount;
        this.days = days;
        this.name = name;
    }

    @Override
    public void setId(String id) {
        super.setId(id);
    }

    public void setTrialDays(Integer trialDays) {
        this.trialDays = trialDays;
    }

    public void setPaymentMethods(Collection<PaymentMethod> paymentMethods) {
        this.paymentMethods = paymentMethods;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setCharges(Integer charges) {
        this.charges = charges;
    }

    public void setInstallments(Integer installments) {
        this.installments = installments;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setDays(Integer days){
        this.days = days;
    }

    public void setAmount(Integer amount){
        this.amount = amount;
    }
    
    public void setInvoiceReminder(Integer invoiceReminder){
        this.invoiceReminder = invoiceReminder; 
    }

    private void copy(Plan other) {
        super.copy(other);
        this.amount = other.getAmount();
        this.days = other.getDays();
        this.name = other.getName();
        this.trialDays = other.getTrialDays();
        this.paymentMethods = other.getPaymentMethods();
        this.charges = other.getCharges();
        this.installments = other.getInstallments();
        this.invoiceReminder = other.getInvoiceReminder(); 
    }

    public Integer getAmount() {
        return amount;
    }

    public Integer getDays() {
        return days;
    }

    public String getName() {
        return name;
    }

    public Integer getTrialDays() {
        return trialDays;
    }

    public Collection<PaymentMethod> getPaymentMethods() {
        return paymentMethods;
    }

    public Integer getCharges() {
        return charges;
    }

    public Integer getInstallments() {
        return installments;
    }
    
    public Integer getInvoiceReminder(){
        return invoiceReminder;
    }
    

}
