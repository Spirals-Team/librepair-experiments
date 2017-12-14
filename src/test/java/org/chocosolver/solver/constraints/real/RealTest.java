/**
 * This file is part of choco-solver, http://choco-solver.org/
 *
 * Copyright (c) 2017, IMT Atlantique. All rights reserved.
 *
 * Licensed under the BSD 4-clause license.
 * See LICENSE file in the project root for full license information.
 */
package org.chocosolver.solver.constraints.real;

import org.chocosolver.solver.Model;
import org.chocosolver.solver.Solver;
import org.chocosolver.solver.constraints.Constraint;
import org.chocosolver.solver.exception.ContradictionException;
import org.chocosolver.solver.search.loop.monitors.IMonitorSolution;
import org.chocosolver.solver.search.strategy.Search;
import org.chocosolver.solver.variables.BoolVar;
import org.chocosolver.solver.variables.IntVar;
import org.chocosolver.solver.variables.RealVar;
import org.chocosolver.solver.variables.Variable;
import org.chocosolver.util.tools.ArrayUtils;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Random;
import java.util.stream.Collectors;

import static java.lang.System.out;
import static org.testng.Assert.assertEquals;

/**
 * -Djava.library.path=-Djava.library.path=/Users/cprudhom/Sources/Ibex/ibex-2.3.1/__build__/plugins/java
 * <br/>
 *
 * @author Charles Prud'homme
 * @since 19/07/12
 */
public class RealTest {

    public void cmpDomains(double[] a1, double[] a2) {
        double DELTA = 1e-10;
        for (int i = 0; i < a1.length; i++)
            assertEquals(a1[i], a2[i], DELTA);
    }


    @Test(groups = "1s", timeOut = 60000)
    public void test1() {
        for (int i = 0; i < 10; i++) {
            Ibex ibex = new Ibex(new double[]{0.001, 0.001});

            ibex.add_ctr("{0}+{1}=3");
            ibex.build();
            double domains[] = {1.0, 10.0, 1.0, 10.0};
            System.out.println("Before contract:");
            System.out.println("([" + domains[0] + "," + domains[1] + "] ; [" + domains[2] + "," + domains[3] + "])");

            int result = ibex.contract(0, domains);

            if (result == Ibex.FAIL) {
                System.out.println("Failed!");
            } else if (result == Ibex.CONTRACT) {
                System.out.println("After contract:");
                System.out.println("([" + domains[0] + "," + domains[1] + "] ; [" + domains[2] + "," + domains[3] + "])");
            } else {
                System.out.println("Nothing.");
            }
            ibex.release();
        }
    }

    @Test(groups = "1s", timeOut = 60000)
    public void test2() {
        for (int i = 0; i < 10; i++) {
            Ibex ibex = new Ibex(new double[]{0.001, 0.001});
            ibex.add_ctr("{0}^2+{1}^2<=1");
            ibex.build();
            double[] domains;
            double vv = Math.sqrt(2.) / 2.;

            // CASE 1: the boolean is set to TRUE
            assertEquals(ibex.contract(0, new double[]{2., 3., 2., 3.}, Ibex.TRUE), Ibex.FAIL);
            assertEquals(ibex.contract(0, new double[]{-.5, .5, -.5, .5}, Ibex.TRUE), Ibex.ENTAILED);
            domains = new double[]{-2., 1., -2., 1.};
            assertEquals(ibex.contract(0, domains, Ibex.TRUE), Ibex.CONTRACT);
            cmpDomains(domains, new double[]{-1., 1., -1., 1.});
            assertEquals(ibex.contract(0, domains, Ibex.TRUE), Ibex.NOTHING);


            // CASE 2: the boolean is set to FALSE
            assertEquals(ibex.contract(0, new double[]{2., 3., 2., 3.}, Ibex.FALSE), Ibex.FAIL);
            assertEquals(ibex.contract(0, new double[]{-.5, .5, -.5, .5}, Ibex.FALSE), Ibex.ENTAILED);
            assertEquals(ibex.contract(0, new double[]{-2., 1., -2., -1.}, Ibex.FALSE), Ibex.NOTHING);
            domains = new double[]{0., 2., -vv, vv};
            assertEquals(ibex.contract(0, domains, Ibex.FALSE), Ibex.CONTRACT);
            cmpDomains(domains, new double[]{vv, 2., -vv, vv});

            // CASE 3: the boolean is set to UNKNOWN
            assertEquals(ibex.contract(0, new double[]{2., 3., 2., 3.}, Ibex.FALSE_OR_TRUE), Ibex.FAIL);
            assertEquals(ibex.contract(0, new double[]{-.5, .5, -.5, .5}, Ibex.FALSE_OR_TRUE), Ibex.ENTAILED);
            assertEquals(ibex.contract(0, new double[]{-2., 1., -2., -1.}, Ibex.FALSE_OR_TRUE), Ibex.NOTHING);
            domains = new double[]{0., 2., -vv, vv};
            assertEquals(ibex.contract(0, domains, Ibex.FALSE_OR_TRUE), Ibex.NOTHING);
            cmpDomains(domains, new double[]{0., 2., -vv, vv});

            ibex.release();
        }
    }

    @Test(groups = "1s")
    public void test4() {
        for (int i = 0; i < 10; i++) {
            Model model = new Model();
            IntVar x = model.intVar("x", 0, 9, true);
            IntVar y = model.intVar("y", 0, 9, true);
            IntVar[] vars = {x, y};
//            RealVar[] vars = model.realIntViewArray(new IntVar[]{x, y}, precision);
            // Actually ,we need the calculated result like these :
            // x : [2.000000, 2.000000], y : [4.000000, 4.000000]
            // or x : [1.000000, 1.000000], y : [8.000000, 8.000000]
            // but it always like this : x : [2.418267, 2.418267], y : [3.308154, 3.308154]
//        rcons.discretize(x,y);
            model.realIbexGenericConstraint("{0} * {1} = 8", vars).post();
            Solver solver = model.getSolver();
            solver.setSearch(Search.randomSearch(vars, i));
            solver.findAllSolutions();
            assertEquals(solver.getSolutionCount(), 4);
//            assertEquals(y.getValue(), 1);
        }
    }

    @Test(groups = "1s", timeOut = 60000)
    public void testFreemajb1() {
        Model model = new Model();

        // Declare variables
        RealVar attr = model.realVar("attr", 0.0, 20.0, 0.1);

        // Create and reify constraints to assign values to the real
        RealConstraint attrEquals1 = model.realIbexGenericConstraint("{0}=4.0", attr);
        BoolVar attrEquals1Reification = attrEquals1.reify();
        RealConstraint attrEquals2 = model.realIbexGenericConstraint("{0}=8.0", attr);
        BoolVar attrEquals2Reification = attrEquals2.reify();

        // Walk and print the solutions
        int numSolutions = 0;
        boolean foundSolution = model.getSolver().solve();
        while (foundSolution) {
            numSolutions++;
            System.out.println(String.format("Solution #%d:", numSolutions));
            System.out.println("b1: " + attrEquals1Reification.getValue());
            System.out.println("b2: " + attrEquals2Reification.getValue());
            System.out.println("attr: [" + attr.getLB() + ", " + attr.getUB() + "]");
            System.out.println();

            foundSolution = model.getSolver().solve();
        }
    }

    @Test(groups = "1s", timeOut = 60000)
    public void testFreemajb2() {
        Model model = new Model();

        RealVar x = model.realVar("x", 0.0, 5.0, 0.001);
        out.println("Before solving:");

        RealConstraint newRange = new RealConstraint("1.4142<{0};{0}<3.1416", x);
        newRange.post();
        try {
            model.getSolver().propagate();
        } catch (ContradictionException e) {
            e.printStackTrace();
        }
        out.printf("%s\n", model.toString());
        model.getSolver().printStatistics();
    }

    @Test(groups = "1s", timeOut = 60000)
    public void testFreemajb3() {
        Model model = new Model();

        RealVar x = model.realVar("x", 0.0, 5.0, 0.001);
        out.println("Before solving:");

        model.realIbexGenericConstraint("1.4142<{0};{0}<3.1416", x).post();

        try {
            model.getSolver().propagate();
        } catch (ContradictionException e) {
            e.printStackTrace();
        }
        out.printf("%s\n", model.toString());
        model.getSolver().printStatistics();
    }

    @DataProvider(name = "coeffs")
    public Object[][] provideCoeffs() {
        return new String[][]{
                {"{0}*0.5) = {1}"},
                {"{0}/2) = {1}"},
        };
    }

    @Test(groups = "1s", timeOut = 60000, dataProvider = "coeffs")
    public void testHM1(String coeffs) {
        Model model = new Model("Test model");
        double precision = 1.e-6;
        double MAX_VALUE = 10000;
        double MIN_VALUE = -10000;
        RealVar weldingCurrent = model.realVar("weldingCurrent", 120, 250, precision);
        RealVar MTBF_WS = model.realVar("MTBF_WS", MIN_VALUE, MAX_VALUE, precision);
        RealVar MTBF_MT = model.realVar("MTBF_MT", MIN_VALUE, MAX_VALUE, precision);
        RealVar global_min = model.realVar("global_min", MIN_VALUE, MAX_VALUE, precision);
        Solver solver = model.getSolver();
        model.realIbexGenericConstraint("(" + coeffs + ";{0}+100={2};min({1},{2}) ={3}", weldingCurrent, MTBF_WS, MTBF_MT, global_min).post();
        model.setPrecision(precision);
        model.setObjective(false, global_min);
        solver.plugMonitor((IMonitorSolution) () -> {
            out.println("*******************");
            System.out.println("weldingCurrent LB=" + weldingCurrent.getLB() + " UB=" + weldingCurrent.getUB());
            System.out.println("MTBF_WS LB=" + MTBF_WS.getLB() + " UB=" + MTBF_WS.getUB());
            System.out.println("MTBF_MT LB=" + MTBF_MT.getLB() + " UB=" + MTBF_MT.getUB());
            System.out.println("global_min LB=" + global_min.getLB() + " UB=" + global_min.getUB());
        });
        solver.showDecisions(() -> "" + solver.getNodeCount());
        while (solver.solve()) ;
    }

    @Test(groups = "1s", timeOut = 600000)
    public void testHM2() {
        Model model = new Model("Default model");
        double precision = 1.e-1;
        RealVar current = model.realVar("current", 121, 248, precision);
        RealVar MTBF = model.realVar("MTBF", 0, 300, precision);
        RealVar MTBF_MT = model.realVar("MTBF_MT", 0, 200, precision);
        Solver solver = model.getSolver();
        model.realIbexGenericConstraint("932.6-(8.664*{0})+(0.02678*({0}^2))-(0.000028*({0}^3)) = {1}", current, MTBF_MT).post();
        model.realIbexGenericConstraint("min(20,{0}) = {1}", MTBF_MT, MTBF).post();//MTBF;
        model.setPrecision(precision);
        model.setObjective(false, MTBF);
        solver.showDecisions();
        solver.plugMonitor((IMonitorSolution) () -> {
            out.println("*******************");
            System.out.println("weldingCurrent LB=" + current.getLB() + " UB=" + current.getUB());
            System.out.println("MTBF_MT LB=" + MTBF_MT.getLB() + " UB=" + MTBF_MT.getUB());
        });
        solver.solve();
    }

    @Test(groups = "1s", timeOut = 60000)
    public void testHM21() {
        Ibex ibex = new Ibex(new double[]{1.e-1, 1.e-1, 1.e-1});
        ibex.add_ctr("932.6-(8.664*{0})+(0.02678*({0}^2))-(0.000028*({0}^3)) = {1}");
        ibex.add_ctr("min(20,{1}) = {2}");
        int result = ibex.contract(0, new double[]{121., 248., 0., 200.}, Ibex.TRUE);
        System.out.printf("Expected: %d, found: %d\n", Ibex.NOTHING, result);
    }

    @Test(groups = "1s")
    public void testPG1() throws Exception {
        Model model = new Model();
        RealVar rv = model.realVar(0, 5, 4.E-2);
        BoolVar bv = model.realIbexGenericConstraint("{0}=4", rv).reify();
        model.arithm(bv, "=", 0).post();
        Solver solver = model.getSolver();
        solver.showSolutions();
        solver.showDecisions();
        solver.setSearch(
                Search.inputOrderLBSearch(bv),
                Search.realVarSearch(4.E-2, rv));
        while (solver.solve()) ;
        ;
        assertEquals(model.getSolver().getSolutionCount(), 63);
    }

    @Test(groups = "1s", timeOut = 60000)
    public void testJJ35() {
        Ibex ibex = new Ibex(new double[]{1.0E-1});
        ibex.add_ctr("{0} = 4");
        double domains[] = {0., 5.};
        ibex.build();
        Assert.assertEquals(ibex.contract(0, domains, Ibex.FALSE), Ibex.NOTHING);
    }

    @Test(groups = "1s")
    public void testJiiTee1() throws Exception {
        Model model = new Model("model");
        RealVar dim_A = model.realVar("dim_A", 150.0, 470.0, 1.0E-5);
        IntVar ll = model.intVar("ll", 1, 5, false);
        BoolVar[] dim_A_guards = new BoolVar[5];
        dim_A_guards[0] = model.realIbexGenericConstraint("{0} = 150.0", dim_A).reify();
        dim_A_guards[1] = model.realIbexGenericConstraint("{0} = 195.0", dim_A).reify();
        dim_A_guards[2] = model.realIbexGenericConstraint("{0} = 270.0", dim_A).reify();
        dim_A_guards[3] = model.realIbexGenericConstraint("{0} = 370.0", dim_A).reify();
        dim_A_guards[4] = model.realIbexGenericConstraint("{0} = 470.0", dim_A).reify();

        Constraint bigA = model.realIbexGenericConstraint("{0} > 300", dim_A);
        Constraint smallA = model.realIbexGenericConstraint("{0} < 200", dim_A);

        // The following or does not work.
        // the first 'and' within 'or' works, the second does not
        // if the order is reversed, also the results change: the results of the first 'and' are found
        // How to get these both?
        model.or(
                model.and(
                        bigA,
                        model.arithm(ll, "<", 3)),
                model.and(
                        smallA,
                        model.arithm(ll, ">=", 3))
        ).post();
        model.sum(dim_A_guards, "=", 1).post();
        LinkedList<Variable> printVars = new LinkedList<Variable>();
        printVars.add(dim_A);
        printVars.add(ll);
        Solver solver = model.getSolver();
        solver.showDecisions();
    /*try {
        model.getSolver().propagate();
    } catch (ContradictionException e) {
        e.printStackTrace();
    }*/
        int i = 0;
        while (solver.solve()) {
            i++;
            System.out.print("Solution " + i + " found :");
            for (Variable v : printVars) System.out.print(v + ", ");
            System.out.println("");
        }
        assertEquals(solver.getSolutionCount(), 10);
    }

    @Test(groups = "1s", timeOut = 60000)
    public void testPeter() {
        Random ds = new Random();
        Model model = new Model();
        RealVar[] rv1 = model.realVarArray(10, 0, 10, 0.1d);
        RealVar[] rv2 = model.realVarArray(10, 0, 10, 0.1d);
        RealVar opt = model.realVar(0, 100, 0.1d);
        RealVar srv1 = model.realVar(0, 100, 0.1d);
        RealVar srv2 = model.realVar(0, 100, 0.1d);
        BoolVar[] bv1 = model.boolVarArray(10);
        BoolVar[] bv2 = model.boolVarArray(10);
        model.realIbexGenericConstraint("{0}={1}+{2}+{3}+{4}+{5}+{6}+{7}+{8}+{9}+{10}",
                srv1, rv1[0], rv1[1], rv1[2], rv1[3], rv1[4], rv1[5], rv1[6], rv1[7], rv1[8], rv1[9]).post();

        model.realIbexGenericConstraint("{0}={1}+{2}+{3}+{4}+{5}+{6}+{7}+{8}+{9}+{10}",
                srv2, rv2[0], rv2[1], rv2[2], rv2[3], rv2[4], rv2[5], rv2[6], rv2[7], rv2[8], rv2[9]).post();

        for (int i = 0; i < 10; ++i) {
            model.ifThenElse(bv1[i], model.realIbexGenericConstraint("{0}=" + ds.nextDouble() * 10.0, rv1[i]), model.realIbexGenericConstraint("{0}=0.0", rv1[i]));
            model.ifThenElse(bv2[i], model.realIbexGenericConstraint("{0}=" + ds.nextDouble() * 10.0, rv2[i]), model.realIbexGenericConstraint("{0}=0.0", rv2[i]));
            model.arithm(bv1[i], "!=", bv2[i]).post();
        }
        //NO CRASH
        //	model.realIbexGenericConstraint("{0}={1}+{2}", opt, srv1, srv2).post();
        //CRASH
        model.realIbexGenericConstraint("{0}=max({1},{2})", opt, srv1, srv2).post();
        model.setObjective(false, opt);
        model.getSolver().showStatistics();
        while (model.getSolver().solve()) {
            System.out.println(Arrays.stream(rv1).map(x -> String.format("%.2f ", x.getUB())).collect(Collectors.toList()));
            System.out.println(Arrays.stream(rv2).map(x -> String.format("%.2f ", x.getUB())).collect(Collectors.toList()));
        }
    }

    @Test(groups = "10s", timeOut = 60000)
    public void testJiTee1() throws ContradictionException {
        double[] posA = new double[]{150.0, 195.0, 270.0, 370.0, 470.0};
        Model model = new Model("model");
        IntVar load = model.intVar("load", new int[]{0, 100, 200, 300, 400, 500, 600, 700});
        double min = 150.0;
        double max = 470.0;
        RealVar dim_A = model.realVar("dim_A", min, max, 1.0E-5);
        BoolVar[] rVarGuards = new BoolVar[posA.length];
        for (int i = 0; i < posA.length; i++) {
            rVarGuards[i] = model.realIbexGenericConstraint("{0} = " + posA[i], dim_A).reify();
        }
        model.sum(rVarGuards, "=", 1).post();

        model.realIbexGenericConstraint("{0}<=271.", dim_A).post();
        model.arithm(load, ">", 400).post();
        for (int i = 0; i < 500; i++) {
            model.realIbexGenericConstraint("{0} > " + i, dim_A);
            System.gc();
        }
        model.getSolver().findSolution();
    }

    @Test(groups = "1s", timeOut = 60000)
    public void testPostUnpost() {
        LinkedList<Variable> printVars = new LinkedList<Variable>();
        Constraint stickyCstr = null;
        Random rr = new Random(2); //2 gives a suitable first requirement 500 for 'load'
        double[] posA = new double[]{150.0, 195.0, 270.0, 370.0, 470.0};
        Model model = new Model("model");
        IntVar load = model.intVar("load", new int[]{0, 100, 200, 300, 400, 500, 600, 700});
        RealVar dim_A = addEnumReal(model, "dim_A", posA);

        model.and(
                model.realIbexGenericConstraint("{0}<=271.", dim_A),
                model.arithm(load, ">", 400)).post();

        printVars.add(dim_A);
        printVars.add(load);


        int sameRoundPostUnpost = 0;
        //Repeatedly post / unpost. This is unstable on Windows, Ibex crashes quite often. But main concern is to make this work!
        //I cannot understand why solutions are lost after the first contradiction has been found, even when propagation is not on!
        for (int round = 0; round < 350; round++) {
            System.out.print("round:" + round + ", getNbCstrs()=" + model.getNbCstrs() + " ");
            model.getSolver().reset();
            model.getEnvironment().worldPush();
            System.out.print(load);
            System.out.print(" ");
            System.out.print(dim_A);

            //Randomly unpost a sticky constraint that remains between iterations. Probability of unpost() annd permanent removal is higher than creation and post()
            boolean unPostedNow = false;
            if (stickyCstr != null) {
                int r = rr.nextInt(100);
                if (r <= 12) {
                    model.unpost(stickyCstr);
                    System.out.print(", Unposted: " + stickyCstr + " ");
                    stickyCstr = null;
                    unPostedNow = true;
                }
            }

            //a constraint at each round: post and unpost
            Constraint c;
            int reqInt = (round % 100);
            c = model.realIbexGenericConstraint("{0} > " + 5 * reqInt, dim_A);
            c.post();

            //Randomly post a sticky constraint that remains between iterations. Probability to post() is lower than unpost()
            boolean postedNow = false;
            if (stickyCstr == null) {
                int r = rr.nextInt(100);
                if (r <= 7) {
                    stickyCstr = model.arithm(load, "=", r * 100);
                    model.post(stickyCstr);
                    System.out.print(", Posted: " + stickyCstr + " ");
                    postedNow = true;
                }
            }

            System.out.print(", sticky is posted: " + (stickyCstr != null));
            if (postedNow && unPostedNow) {
                System.out.print(", UnpostPost BOTH on same ROUND");
                sameRoundPostUnpost++;
            }
            System.out.println();

            boolean propagate = false;
            if (propagate) {
                model.getSolver().getEnvironment().worldPush();
                try {
                    model.getSolver().propagate();
                } catch (ContradictionException e) {
                    System.out.println("CONTRADICTION found");
                    e.printStackTrace();
                }
                model.getSolver().getEnvironment().worldPop();
            }

            int i = 0;
            while (model.getSolver().solve()) {
                i++;
                System.out.print("Solution " + i + " found :");
                for (Variable v : printVars) System.out.print(v + ", ");
                System.out.println("");
            }
            model.unpost(c);
        }


        System.out.println("sameRoundPostUnpost=" + sameRoundPostUnpost);

    }

    private static RealVar addEnumReal(Model model, String name, double[] possibles) {
        double min = possibles[0];
        double max = possibles[possibles.length - 1];
        RealVar rVar = model.realVar(name, min, max, 1.0E-5);
        BoolVar[] rVarGuards = new BoolVar[possibles.length];
        for (int i = 0; i < possibles.length; i++)
            rVarGuards[i] = model.realIbexGenericConstraint("{0} = " + possibles[i], rVar).reify();
        model.sum(rVarGuards, "=", 1).post();
        return rVar;

    }


    @Test(groups = "1s", timeOut = 60000)
    public void testDetec() {
        Model model = new Model();

        RealVar x = model.realVar("x", 0.0, 5.0, 0.001);
        RealVar y = model.realVar("y", 0.0, 5.0, 0.001);
        RealVar z = model.realVar("z", 0.0, 5.0, 0.001);

        RealConstraint newRange = new RealConstraint("1.4142<{0};{2}<3.1416;{1}>{0};{2}*2<{0}", x, y, z);
        assertEquals(newRange.toString(),
                "REALCONSTRAINT ([" +
                        "RealPropagator(x) ->(\"1.4142<{0}\"), " +
                        "RealPropagator(z) ->(\"{0}<3.1416\"), " +
                        "RealPropagator(y, x) ->(\"{0}>{1}\"), " +
                        "RealPropagator(z, x) ->(\"{0}*2<{1}\")])");

    }

    @Test(groups = "1s", timeOut = 60000)
    public void testJJ1() {
        Ibex ibex = new Ibex(new double[]{1.0E-5});
        ibex.add_ctr("{0}<=200.0");
        ibex.build();
        double domains[] = {150., 470.};
        Assert.assertEquals(ibex.contract(0, domains, Ibex.TRUE), Ibex.CONTRACT);
        Assert.assertEquals(domains[0], 150.);
        Assert.assertEquals(domains[1], 200.);
        domains[0] = 150.;
        domains[1] = 470.;
        Assert.assertEquals(ibex.contract(0, domains, Ibex.FALSE), Ibex.CONTRACT);
        Assert.assertEquals(domains[0], 200.);
        Assert.assertEquals(domains[1], 470.);
        domains[0] = 150.;
        domains[1] = 470.;
        Assert.assertEquals(ibex.contract(0, domains, Ibex.FALSE_OR_TRUE), Ibex.NOTHING);
        Assert.assertEquals(domains[0], 150.);
        Assert.assertEquals(domains[1], 470.);

        domains[0] = 201.;
        domains[1] = 470.;
        Assert.assertEquals(ibex.contract(0, domains, Ibex.FALSE_OR_TRUE), Ibex.FAIL);
        Assert.assertEquals(domains[0], 201.);
        Assert.assertEquals(domains[1], 470.);

        domains[0] = 150.;
        domains[1] = 199.;
        Assert.assertEquals(ibex.contract(0, domains, Ibex.FALSE_OR_TRUE), Ibex.ENTAILED);
        Assert.assertEquals(domains[0], 150.);
        Assert.assertEquals(domains[1], 199.);

        domains[0] = 201.;
        domains[1] = 470.;
        Assert.assertEquals(ibex.contract(0, domains, Ibex.FALSE), Ibex.FAIL);
        Assert.assertEquals(domains[0], 201.);
        Assert.assertEquals(domains[1], 470.);
    }

    @Test(groups = "1s", timeOut = 60000)
    public void testJJ2() {
        Ibex ibex = new Ibex(new double[]{1.0E-5});
        ibex.add_ctr("{0}=150.0");
        ibex.build();
        double domains[] = {150., 150.};
        Assert.assertEquals(ibex.contract(0, domains, Ibex.TRUE), Ibex.NOTHING);
        Assert.assertEquals(domains[0], 150.);
        Assert.assertEquals(domains[1], 150.);
    }

    @Test(groups = "1s", timeOut = 60000)
    public void testJJ3() {
        Ibex ibex = new Ibex(new double[]{1.0E-5});
        ibex.add_ctr("{0} < 150.0");
        ibex.build();
        double domains[] = {140., 151.};
        Assert.assertEquals(ibex.start_solve(domains), Ibex.STARTED);
        while (ibex.next_solution(domains) != Ibex.SEARCH_OVER) {
            System.out.printf("%s\n", Arrays.toString(domains));
        }
    }

    @Test(groups = "1s", timeOut = 60000)
    public void testMove1() {
        Model model = new Model();
        RealVar[] x = model.realVarArray(3, 0., 5., 1.E-2);
        RealVar m = model.realVar(0.0, 5.0, 1.E-2);
        model.realIbexGenericConstraint(
                "({0} + {1} + {2})/ 3 = {3};",
                ArrayUtils.append(x, new RealVar[]{m})
        ).post();
        Solver solver = model.getSolver();
        solver.setSearch(Search.ibexSolving(model));
        solver.findAllSolutions();
        Assert.assertEquals(solver.getSolutionCount(), 1);
    }

    @Test(groups = "1s", timeOut = 60000)
    public void testMove2() {
        Model model = new Model();
        IntVar[] x = model.intVarArray(3, 0, 5);
        RealVar m = model.realVar(0.0, 5.0, 1.E-2);
        RealVar n = model.realVar(0.0, 5.0, 1.E-2);
        model.realIbexGenericConstraint(
                "({0} + {1} + {2})/ 3 = {3};",
                ArrayUtils.append(x, new RealVar[]{m})
        ).post();
        model.realIbexGenericConstraint(
                "{0} + {1} = 2.6",
                m, n
        ).post();
        Solver solver = model.getSolver();
        solver.setSearch(Search.randomSearch(x, 0), Search.ibexSolving(model));
        solver.showSolutions();
        solver.findAllSolutions();
        Assert.assertEquals(solver.getSolutionCount(), 108);
    }

    @Test(groups = "1s", timeOut = 60000)
    public void testJJ34() {
        Ibex ibex = new Ibex(new double[]{-1.0, -1.0, -1.0, 1.0E-2, 1.0E-2});
        ibex.add_ctr("({0} + {1} + {2}) / 3 = {3}");
        ibex.add_ctr("{3} + {4} < 4.5");
        ibex.build();
        double domains[] = {0., 5., 0., 5., 0., 5., 0., 5., 0., 5.};
        Assert.assertEquals(ibex.contract(1, domains, Ibex.TRUE), Ibex.CONTRACT);
    }


    @Test(groups = "1s", timeOut = 60000)
    public void testMove3() {
        Model model = new Model();
        RealVar[] x = model.realVarArray(3, -10., 10., 1.E-2);
        model.realIbexGenericConstraint(
                " {0}^2*{1}^2*{2}^2=1;\n" +
                        " {0}^2={1}^2;\n" +
                        " abs({0})=abs({2});",
                x).post();
        Solver solver = model.getSolver();
        solver.setSearch(Search.ibexSolving(model));
        solver.findAllSolutions();
        Assert.assertEquals(solver.getSolutionCount(), 8);
    }

    @Test(groups = "1s"/*, timeOut = 60000*/)
    public void testMove4() {
        Model model = new Model();
        RealVar[] y = model.realVarArray(3, -10., 10., 1.E-5);
        model.realIbexGenericConstraint(
                "{0}^2*{1}^2*{2}^2=1;\n" +
                        "{0}^2={1}^2;\n" +
                        "abs({0})=abs({2});",
                y).post();
        Solver solver = model.getSolver();
        solver.showSolutions();
        solver.findAllSolutions();
        Assert.assertEquals(solver.getSolutionCount(), 8);
    }

    @Test(groups = "1s", timeOut = 60000)
    public void testMove5() {
        Model model = new Model();
        RealVar y = model.realVar(-10., 10., 1.E-1);
        BoolVar b = model.boolVar();
        String f1 = "{0}>= 1.";
        String f2 = "{0}<= 2.";
        model.realIbexGenericConstraint(
                f1+";"+f2,
                y).reifyWith(b);
        Solver solver = model.getSolver();
        solver.setSearch(Search.realVarSearch(1.E-1, y));
        solver.findAllSolutions();
        Assert.assertEquals(solver.getSolutionCount(), 100);
    }

    @Test(groups="1s", timeOut=60000)
    public void testJuha1() {
        Model model = new Model("model");
        IntVar foo = model.intVar("foo", 0, 20);
        IntVar wow = model.intVar("wow", new int[]{1, 2, 4});
        RealVar rfoo = model.realIntView(foo, 1E-5);
        RealVar rwow = model.realIntView(wow, 1E-5);
        model.realIbexGenericConstraint("{0} / {1} = 4.5", rfoo, rwow).reify();
        model.arithm(foo, "!=", 10).post();
        Solver solver = model.getSolver();
        solver.setSearch(Search.inputOrderLBSearch(foo, wow));
        solver.findAllSolutions();
        Assert.assertEquals(solver.getSolutionCount(), 60);
    }

    @Test(groups="1s", timeOut=60000)
    public void testJuha2() {
        Model model = new Model("model");
        IntVar foo = model.intVar("foo", new int[]{0,15, 20});
        IntVar wow = model.intVar("wow", new int[]{1, 2, 4});
        RealVar rfoo = model.realIntView(foo, 1E-5);
        RealVar rwow = model.realIntView(wow, 1E-5);
        model.realIbexGenericConstraint("{0} / {1} = 4.5", rfoo, rwow).post();
        Solver solver = model.getSolver();
        solver.setSearch(Search.inputOrderLBSearch(foo, wow));
        solver.findAllSolutions();
        Assert.assertEquals(solver.getSolutionCount(), 0);
    }


    @Test(groups="1s", timeOut=60000, threadPoolSize = 4, invocationCount = 10)
    public void testJuha3(){
        Model model = new Model("model" + Thread.currentThread().getId());
        IntVar dim_H = model.intVar("dim_h", new int[]{2000, 2100, 2200});
        RealVar dim_A = model.realVar("dim_A", 150.0, 470.0, 1.0E-5);
        BoolVar[] dim_A_guards = new BoolVar[5];
        dim_A_guards[0] = new RealConstraint("{0} = 150.0", dim_A).reify();
        dim_A_guards[1] = new RealConstraint("{0} = 195.0", dim_A).reify();
        dim_A_guards[2] = new RealConstraint("{0} = 270.0", dim_A).reify();
        dim_A_guards[3] = new RealConstraint("{0} = 370.0", dim_A).reify();
        dim_A_guards[4] = new RealConstraint("{0} = 470.0", dim_A).reify();
        model.sum(dim_A_guards, "=", 1).post();
        RealVar dim_H_asReal = model.realIntView(dim_H, 1.0E-5);
        model.realIbexGenericConstraint("{0}+{1} > 2500", dim_A, dim_H_asReal).post();
        model.getSolver().showSolutions();
        model.getSolver().findAllSolutions();
        Assert.assertEquals(model.getSolver().getSolutionCount(), 3);
    }

    private static synchronized void build(Ibex ibex){
        ibex.build();
    }


    @Test(groups="1s", timeOut=60000, threadPoolSize = 4, invocationCount = 10)
    public void testJuha4(){
        double eps=1e-7;
        Ibex ibex = new Ibex(new double[]{eps,eps,eps,eps,eps,eps,eps,eps});
        ibex.add_ctr("3*{0}*({1}-2*{0})+{1}^2/4=0");
        ibex.add_ctr("3*{1}*({2}-2*{1}+{0})+({2}-{0})^2/4=0");
        ibex.add_ctr("3*{2}*({3}-2*{2}+{1})+({3}-{1})^2/4=0");
        ibex.add_ctr("3*{3}*({4}-2*{3}+{2})+({4}-{2})^2/4=0");
        ibex.add_ctr("3*{4}*({5}-2*{4}+{3})+({5}-{3})^2/4=0");
        ibex.add_ctr("3*{5}*({6}-2*{5}+{4})+({6}-{4})^2/4=0");
        ibex.add_ctr("3*{6}*({7}-2*{6}+{5})+({7}-{5})^2/4=0");
        ibex.add_ctr("3*{7}*(20-2*{7}+{6})+(20-{6})^2/4=0");
        build(ibex);
        double L=1e8;
        double domains[]={-L,L,-L,L,-L,L,-L,L,-L,L,-L,L,-L,L,-L,L};
        ibex.start_solve(domains);
        for (int i=0; i<256; i++) {
            if (ibex.next_solution(domains)!=Ibex.SOLUTION) {
                ibex.release();
                Assert.fail();
            }
        }
        ibex.next_solution(domains);
        ibex.release();
    }

}
