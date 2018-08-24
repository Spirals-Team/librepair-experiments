package edu.hm.hafner.analysis.parser.dry.dupfinder;

import java.util.List;

import org.apache.commons.digester3.Digester;

import edu.hm.hafner.analysis.Issue;
import edu.hm.hafner.analysis.IssueBuilder;
import edu.hm.hafner.analysis.Report;
import edu.hm.hafner.analysis.parser.dry.AbstractDryParser;
import edu.hm.hafner.analysis.parser.dry.DuplicationGroup;

/**
 * A parser for Reshaper Dupfinder XML files.
 *
 * @author Rafal Jasica
 */
public class DupFinderParser extends AbstractDryParser<Duplicate> {
    /** Unique ID of this class. */
    private static final long serialVersionUID = 1357147358617711901L;

    /**
     * Creates a new instance of {@link DupFinderParser}.
     *
     * @param highThreshold
     *         minimum number of duplicate lines for high priority warnings
     * @param normalThreshold
     *         minimum number of duplicate lines for normal priority warnings
     */
    public DupFinderParser(final int highThreshold, final int normalThreshold) {
        super(highThreshold, normalThreshold);
    }

    /**
     * Creates a new instance of {@link DupFinderParser}. The {@code highThreshold} is set to 50, the {@code normalThreshold}
     * is set to 25.
     */
    public DupFinderParser() {
        super(50, 25);
    }

    @Override
    protected void configureParser(final Digester digester) {
        String duplicationXPath = "*/DuplicatesReport/Duplicates/Duplicate";
        digester.addObjectCreate(duplicationXPath, Duplicate.class);
        digester.addSetProperties(duplicationXPath, "Cost", "cost");
        digester.addSetNext(duplicationXPath, "add");

        String fragmentXPath = duplicationXPath + "/Fragment";
        digester.addObjectCreate(fragmentXPath, Fragment.class);
        digester.addBeanPropertySetter(fragmentXPath + "/FileName", "fileName");
        digester.addBeanPropertySetter(fragmentXPath + "/Text", "text");
        digester.addSetNext(fragmentXPath, "addFragment", Fragment.class.getName());

        String lineRangeXPath = fragmentXPath + "/LineRange";
        digester.addObjectCreate(lineRangeXPath, Range.class);
        digester.addSetProperties(lineRangeXPath, "Start", "start");
        digester.addSetProperties(lineRangeXPath, "End", "end");
        digester.addSetNext(lineRangeXPath, "setLineRange", Range.class.getName());

        String offsetRangeXPath = fragmentXPath + "/OffsetRange";
        digester.addObjectCreate(offsetRangeXPath, Range.class);
        digester.addSetProperties(offsetRangeXPath, "Start", "start");
        digester.addSetProperties(offsetRangeXPath, "End", "end");
        digester.addSetNext(offsetRangeXPath, "setOffsetRange", Range.class.getName());
    }

    @Override
    protected Report convertDuplicationsToIssues(final List<Duplicate> duplications) {
        Report report = new Report();

        for (Duplicate duplication : duplications) {
            DuplicationGroup group = new DuplicationGroup();
            for (Fragment fragment : duplication.getFragments()) {
                group.setCodeFragment(fragment.getText());
                Range lineRange = fragment.getLineRange();
                int count = lineRange.getEnd() - lineRange.getStart() + 1;
                IssueBuilder builder = new IssueBuilder().setPriority(getPriority(count))
                        .setLineStart(lineRange.getStart())
                        .setLineEnd(lineRange.getEnd())
                        .setFileName(fragment.getFileName())
                        .setAdditionalProperties(group);
                Issue issue = builder.build();
                group.add(issue);
                report.add(issue);
            }
        }

        return report;
    }
}
