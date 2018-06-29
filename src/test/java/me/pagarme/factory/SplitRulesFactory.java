package me.pagarme.factory;

import java.util.ArrayList;
import java.util.Collection;

import me.pagar.model.PagarMeException;
import me.pagar.model.Plan;
import me.pagar.model.Recipient;
import me.pagar.model.SplitRule;

public class SplitRulesFactory {

    private RecipientFactory recipientFactory = new RecipientFactory();

    public Collection<SplitRule> createSplitRuleWithPercentage() throws PagarMeException{

        Recipient recipient1 = recipientFactory.create();
        recipient1.save();

        Recipient recipient2 = recipientFactory.create();
        recipient2.save();

        Collection<SplitRule> splitRules = new ArrayList<SplitRule>();

        SplitRule splitRule1 = new SplitRule();

        splitRule1.setRecipientId(recipient1.getId());
        splitRule1.setPercentage(50);
        splitRule1.setChargeProcessingFee(true);
        splitRule1.setLiable(true);

        SplitRule splitRule2 = new SplitRule();

        splitRule2.setRecipientId(recipient2.getId());
        splitRule2.setPercentage(50);
        splitRule2.setChargeProcessingFee(false);
        splitRule2.setLiable(false);

        splitRules.add(splitRule1);
        splitRules.add(splitRule2);

        return splitRules;
    }

    public Collection<SplitRule> createSplitRuleWithAmount(Plan currentPlan) throws PagarMeException{

        Recipient recipient1 = recipientFactory.create();
        recipient1.save();

        Recipient recipient2 = recipientFactory.create();
        recipient2.save();

        Collection<SplitRule> splitRules = new ArrayList<SplitRule>();

        int amountPlan = (currentPlan.getAmount() / 2);

        SplitRule splitRule1 = new SplitRule();

        splitRule1.setRecipientId(recipient1.getId());
        splitRule1.setAmount(amountPlan);
        splitRule1.setChargeProcessingFee(true);
        splitRule1.setLiable(true);

        SplitRule splitRule2 = new SplitRule();

        splitRule2.setRecipientId(recipient2.getId());
        splitRule2.setAmount(amountPlan);
        splitRule2.setChargeProcessingFee(false);
        splitRule2.setLiable(false);

        splitRules.add(splitRule1);
        splitRules.add(splitRule2);

        return splitRules;
    }
}
