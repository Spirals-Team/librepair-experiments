package edu.stanford.futuredata.macrobase.analysis.summary.fpg;

import edu.stanford.futuredata.macrobase.analysis.summary.fpg.result.ItemsetWithCount;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

public class FPGrowthTest {
    private Set<Integer> intIfy(String txnStr) {
        return Arrays.stream(txnStr.split(", ")).map(s -> (int) s.charAt(0)).collect(Collectors.toSet());
    }

    @SuppressWarnings("unused")
    private void printItemsets(List<ItemsetWithCount> itemsets) {
        itemsets.sort((a, b) -> b.getItems().size() - a.getItems().size());
        for (ItemsetWithCount i : itemsets) {
            System.out.format("\ncount %f, size %d\n", i.getCount(), i.getItems().size());
            for (int item : i.getItems()) {
                System.out.println((char) item);
            }
        }
    }

    private boolean compareResults(Set<ItemsetWithCount> ap_itemsets, List<ItemsetWithCount> itemsets) {
        for (int i = 0; i < itemsets.size(); i ++) {
            boolean foundEquals = false;
            for (ItemsetWithCount iwc : ap_itemsets) {
                foundEquals |= iwc.equals(itemsets.get(i));
            }
            if (!foundEquals) { return false; };
        }
        return true;
    }

    @Test
    public void testFPFromPaper() {
        List<Set<Integer>> txns = new ArrayList<>();
        txns.add(intIfy("f, a, c, d, g, i, m, p"));
        txns.add(intIfy("a, b, c, f, l, m, o"));
        txns.add(intIfy("b, f, h, j, o"));
        txns.add(intIfy("b, c, k, s, p"));
        txns.add(intIfy("a, f, c, e, l, p, m, n"));

        FPGrowth fp = new FPGrowth();
        Apriori ap = new Apriori();

        Set<ItemsetWithCount> ap_itemsets = ap.getItemsets(txns, .6);
        List<ItemsetWithCount> itemsets = fp.getItemsetsWithSupportRatio(txns, .6);

        assertEquals(18, itemsets.size());
        assert(compareResults(ap_itemsets, itemsets));
    }

    @Test
    public void testFPLonger() {

        List<Set<Integer>> txns = new ArrayList<>();
        txns.add(intIfy("f, a, c, d, g, i, m, p"));
        txns.add(intIfy("a, b, c, f, l, m, o"));

        FPGrowth fp = new FPGrowth();

        List<ItemsetWithCount> itemsets = fp.getItemsetsWithSupportRatio(txns, .2);

        Apriori ap = new Apriori();
        Set<ItemsetWithCount> api = ap.getItemsets(txns, .2);

//        printItemsets(itemsets);

        List<Set<Integer>> apil = api.stream().map(i -> i.getItems()).collect(Collectors.toList());
        Set<Set<Integer>> dupdetector = new HashSet<>();

        int numDup = 0;
        for (Set<Integer> s : apil) {
            if (!dupdetector.add(s)) {
                numDup++;
            }
        }
        assertEquals(0, numDup);

        Set<Set<Integer>> iss = itemsets.stream().map(i -> i.getItems()).collect(Collectors.toSet());

//        log.debug("DIFF: {}", Sets.difference(dupdetector, iss));

        assertEquals(api.size(), itemsets.size());
    }

    @Test
    public void simpleTest() {
        List<Set<Integer>> txns = new ArrayList<>();
        txns.add(intIfy("a, b, c"));
        txns.add(intIfy("a, b"));
        txns.add(intIfy("a"));

        FPGrowth fp = new FPGrowth();
        Apriori ap = new Apriori();

        Set<ItemsetWithCount> ap_itemsets = ap.getItemsets(txns, .7);
        List<ItemsetWithCount> itemsets = fp.getItemsetsWithSupportRatio(txns, .7);

        //printItemsets(itemsets);
        assertEquals(3, itemsets.size());
        assert(compareResults(ap_itemsets, itemsets));
    }

    @Test
    public void dupTest() {
        List<Set<Integer>> txns = new ArrayList<>();
        txns.add(intIfy("a, c, d"));
        txns.add(intIfy("a, c, d, e"));
        txns.add(intIfy("c"));
        txns.add(intIfy("a"));
        txns.add(intIfy("a"));
        txns.add(intIfy("a"));
        txns.add(intIfy("a"));
        txns.add(intIfy("a"));
        txns.add(intIfy("b"));
        txns.add(intIfy("b"));
        txns.add(intIfy("b"));
        txns.add(intIfy("a, b, d"));
        txns.add(intIfy("a, b, e, c"));

        FPGrowth fp = new FPGrowth();

        List<ItemsetWithCount> itemsets = fp.getItemsetsWithSupportCount(txns, null, .01 * txns.size(), true);

        List<Set<Integer>> apil = itemsets.stream().map(i -> i.getItems()).collect(Collectors.toList());
        Set<Set<Integer>> dupdetector = new HashSet<>();

        int numDup = 0;
        for (Set<Integer> s : apil) {
            if (!dupdetector.add(s)) {
                numDup++;
            }
        }
        assertEquals(0, numDup);

        //printItemsets(itemsets);

        assertEquals(dupdetector.size(), itemsets.size());
    }

    @Test
    public void testGetSupport() {
        List<Set<Integer>> txns = new ArrayList<>();
        txns.add(intIfy("a, c, d"));
        txns.add(intIfy("a, c, d, e"));
        txns.add(intIfy("c"));
        txns.add(intIfy("a"));
        txns.add(intIfy("a"));
        txns.add(intIfy("a"));
        txns.add(intIfy("a"));
        txns.add(intIfy("a"));
        txns.add(intIfy("b"));
        txns.add(intIfy("b"));
        txns.add(intIfy("b"));
        txns.add(intIfy("a, b, d"));
        txns.add(intIfy("a, b, e, c"));

        FPGrowth.FPTree fpt = new FPGrowth().constructTree(txns, 0);
//        fpt.printTreeDebug();

        assertEquals(2, fpt.getSupport(intIfy("a, b")));
        assertEquals(0, fpt.getSupport(intIfy("a, b, c, d")));

    }
}

