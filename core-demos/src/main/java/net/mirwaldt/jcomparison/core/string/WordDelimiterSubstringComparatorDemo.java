package net.mirwaldt.jcomparison.core.string;

import net.mirwaldt.jcomparison.core.exception.ComparisonFailedException;
import net.mirwaldt.jcomparison.core.facade.version_001.DefaultComparators;
import net.mirwaldt.jcomparison.core.string.api.SubstringComparisonResult;
import net.mirwaldt.jcomparison.core.string.impl.DefaultSubstringComparator;
import net.mirwaldt.jcomparison.core.util.SubstringComparisonResultFormatter;

public class WordDelimiterSubstringComparatorDemo {
    public static void main(String[] args) throws ComparisonFailedException {
        final String leftString = "This is a car.";
        final String rightString = "That is awesome.";

        final DefaultSubstringComparator substringComparator =
                DefaultComparators.createDefaultSubstringComparatorBuilder()
                        .useCustomWordDelimiter((character)->" ".equals(String.valueOf((char)character)))
                        .build();
        
        SubstringComparisonResult substringComparisonResult = substringComparator.compare(leftString, rightString);


        final SubstringComparisonResultFormatter formatter = new SubstringComparisonResultFormatter();

        System.out.println("Differences:");

        final String[] differencesWithBrackets = formatter.formatSubstringComparisonResult(leftString, rightString, substringComparisonResult, true);
        System.out.println("leftString : \t'" + differencesWithBrackets[0] + "'");
        System.out.println("rightString : \t'" + differencesWithBrackets[1] + "'");
        System.out.println();

        System.out.println(substringComparisonResult.getDifferences().toString().replace(", ", ",\n"));
        System.out.println();

        System.out.println("Similarities:");

        final String[] similaritiesWithBrackets = formatter.formatSubstringComparisonResult(leftString, rightString, substringComparisonResult, false);
        System.out.println("leftString : \t'" + similaritiesWithBrackets[0] + "'");
        System.out.println("rightString : \t'" + similaritiesWithBrackets[1] + "'");
        System.out.println();

        System.out.println(substringComparisonResult.getSimilarities().toString().replace(", ", ",\n"));
        System.out.println();
    }
}
