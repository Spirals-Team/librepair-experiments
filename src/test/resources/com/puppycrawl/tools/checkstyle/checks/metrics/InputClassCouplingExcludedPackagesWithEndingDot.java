package com.puppycrawl.tools.checkstyle.checks.metrics;

import com.puppycrawl.tools.checkstyle.checks.metrics.inputs.a.aa.AAClass;
import com.puppycrawl.tools.checkstyle.checks.metrics.inputs.a.ab.ABClass;
import com.puppycrawl.tools.checkstyle.checks.metrics.inputs.b.BClass;
import com.puppycrawl.tools.checkstyle.checks.metrics.inputs.c.CClass;

public class InputClassCouplingExcludedPackagesWithEndingDot { // total: 2 violation
    public AAClass aa = new AAClass(); // violation
    public ABClass ab = new ABClass(); // violation

    class Inner { // total: 2 violations
        public BClass b = new BClass(); // violation
        public CClass c = new CClass(); // violation
    }
}

class InputClassCouplingExcludedPackagesWithEndingDotHidden { // total: 1 violation
    public CClass c = new CClass(); // violation
}