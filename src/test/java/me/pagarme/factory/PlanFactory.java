package me.pagarme.factory;

import java.util.Arrays;

import me.pagar.model.Plan;
import me.pagar.model.Transaction.PaymentMethod;

public class PlanFactory {

    public static final int DEFAULT_AMOUNT = 100;
    public static final int DEFAULT_DAYS = 30;
    public static final int DEFAULT_CHARGES = 100;
    public static final int DEFAULT_INSTALLMENTS = 2;
    public static final int DEFAULT_TRIAL_DAYS = 3;
    public static final String DEFAULT_NAME = "Plano teste";
    public static final String DEFAULT_COLOR = "#bababa";
    public static final int DEFAULT_INVOICE_REMINDER = 3;

    public Plan create() {
        Plan plan = new Plan();
        plan.setAmount(DEFAULT_AMOUNT);
        plan.setDays(DEFAULT_DAYS);
        plan.setName(DEFAULT_NAME);
        plan.setPaymentMethods(Arrays.asList(
                PaymentMethod.BOLETO, PaymentMethod.CREDIT_CARD
        ));
        plan.setCharges(DEFAULT_CHARGES);
        plan.setColor(DEFAULT_COLOR);
        plan.setInstallments(DEFAULT_INSTALLMENTS);
        plan.setTrialDays(DEFAULT_TRIAL_DAYS);
        plan.setInvoiceReminder(DEFAULT_INVOICE_REMINDER);
        return plan;
    }

    public Plan createPlanWithoutTrialDays() {
        Plan plan = new Plan();
        plan.setAmount(DEFAULT_AMOUNT);
        plan.setDays(DEFAULT_DAYS);
        plan.setName(DEFAULT_NAME);
        plan.setPaymentMethods(Arrays.asList(
                PaymentMethod.BOLETO, PaymentMethod.CREDIT_CARD
        ));
        plan.setCharges(DEFAULT_CHARGES);
        plan.setColor(DEFAULT_COLOR);
        plan.setInstallments(DEFAULT_INSTALLMENTS);
        plan.setTrialDays(0);
        plan.setInvoiceReminder(DEFAULT_INVOICE_REMINDER);
        return plan;
    }

}
