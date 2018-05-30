package daikon.tools;


public class InvariantChecker {
    private InvariantChecker() {
        throw new java.lang.Error("do not instantiate");
    }

    public static final java.util.logging.Logger debug = java.util.logging.Logger.getLogger("daikon.tools.InvariantChecker");

    public static final java.util.logging.Logger debug_detail = java.util.logging.Logger.getLogger("daikon.tools.InvariantCheckerDetail");

    private static final java.lang.String output_SWITCH = "output";

    private static final java.lang.String dir_SWITCH = "dir";

    private static final java.lang.String conf_SWITCH = "conf";

    private static final java.lang.String filter_SWITCH = "filter";

    private static java.lang.String usage = daikon.tools.UtilMDE.joinLines(("Usage: java daikon.InvariantChecker [OPTION]... <inv_file> " + "<dtrace_file>"), ("  -h, --" + (daikon.Daikon.help_SWITCH)), "      Display this usage message", (("  --" + (daikon.tools.InvariantChecker.output_SWITCH)) + " output file"), ("  --" + (daikon.tools.InvariantChecker.conf_SWITCH)), "      Checks only invariants that are above the default confidence level", ("  --" + (daikon.tools.InvariantChecker.filter_SWITCH)), "      Checks only invariants that are not filtered by the default filters", (("  --" + (daikon.tools.InvariantChecker.dir_SWITCH)) + " directory with invariant and dtrace files"), "      We output how many invariants failed for each invariant file. We check for failure against any sample in any dtrace file.", (("  --" + (daikon.Daikon.config_option_SWITCH)) + " config_var=val"), "      Sets the specified configuration variable.  ", ("  --" + (daikon.Daikon.debugAll_SWITCH)), "      Turns on all debug flags (voluminous output)", (("  --" + (daikon.Daikon.debug_SWITCH)) + " logger"), "      Turns on the specified debug logger", (("  --" + (daikon.Daikon.track_SWITCH)) + " class<var1,var2,var3>@ppt"), "      Print debug info on the specified invariant class, vars, and ppt");

    public static java.io.File inv_file = null;

    public static java.util.List<java.lang.String> dtrace_files = new java.util.ArrayList<java.lang.String>();

    static java.io.File output_file;

    static java.io.PrintStream output_stream = java.lang.System.out;

    static int error_cnt = 0;

    static int sample_cnt = 0;

    static java.io.File dir_file;

    static boolean doFilter;

    static boolean doConf;

    static boolean quiet = true;

    static java.util.HashSet<daikon.tools.Invariant> failedInvariants = new java.util.HashSet<daikon.tools.Invariant>();

    static java.util.HashSet<daikon.tools.Invariant> testedInvariants = new java.util.HashSet<daikon.tools.Invariant>();

    static java.util.HashSet<daikon.tools.Invariant> activeInvariants = new java.util.HashSet<daikon.tools.Invariant>();

    static java.util.LinkedHashSet<java.lang.String> outputComma = new java.util.LinkedHashSet<java.lang.String>();

    public static void main(java.lang.String[] args) throws java.io.FileNotFoundException, java.io.IOException, java.io.OptionalDataException, java.io.StreamCorruptedException, java.lang.ClassNotFoundException {
        try {
            if ((args.length) == 0) {
                throw new daikon.Daikon.TerminationMessage(daikon.tools.InvariantChecker.usage);
            }
            daikon.tools.InvariantChecker.mainHelper(args);
        } catch (daikon.Daikon.TerminationMessage e) {
            java.lang.System.err.println(e.getMessage());
            java.lang.System.exit(1);
        }
    }

    public static void mainHelper(final java.lang.String[] args) throws java.io.FileNotFoundException, java.io.IOException, java.io.OptionalDataException, java.io.StreamCorruptedException, java.lang.ClassNotFoundException {
        daikon.tools.daikon.LogHelper.setupLogs(daikon.LogHelper.INFO);
        daikon.tools.LongOpt[] longopts = new daikon.tools.LongOpt[]{ new daikon.tools.LongOpt(daikon.Daikon.config_option_SWITCH, LongOpt.REQUIRED_ARGUMENT, null, 0), new daikon.tools.LongOpt(daikon.tools.InvariantChecker.output_SWITCH, LongOpt.REQUIRED_ARGUMENT, null, 0), new daikon.tools.LongOpt(daikon.tools.InvariantChecker.dir_SWITCH, LongOpt.REQUIRED_ARGUMENT, null, 0), new daikon.tools.LongOpt(daikon.tools.InvariantChecker.conf_SWITCH, LongOpt.NO_ARGUMENT, null, 0), new daikon.tools.LongOpt(daikon.tools.InvariantChecker.filter_SWITCH, LongOpt.NO_ARGUMENT, null, 0), new daikon.tools.LongOpt(daikon.Daikon.debugAll_SWITCH, LongOpt.NO_ARGUMENT, null, 0), new daikon.tools.LongOpt(daikon.Daikon.debug_SWITCH, LongOpt.REQUIRED_ARGUMENT, null, 0), new daikon.tools.LongOpt(daikon.Daikon.ppt_regexp_SWITCH, LongOpt.REQUIRED_ARGUMENT, null, 0), new daikon.tools.LongOpt(daikon.Daikon.track_SWITCH, LongOpt.REQUIRED_ARGUMENT, null, 0) };
        daikon.tools.Getopt g = new daikon.tools.Getopt("daikon.tools.InvariantChecker", args, "h", longopts);
        int c;
        while ((c = g.getopt()) != (-1)) {
            switch (c) {
                case 0 :
                    java.lang.String option_name = longopts[g.getLongind()].getName();
                    if (daikon.Daikon.help_SWITCH.equals(option_name)) {
                        java.lang.System.out.println(daikon.tools.InvariantChecker.usage);
                        throw new daikon.Daikon.TerminationMessage();
                    }else
                        if (daikon.tools.InvariantChecker.conf_SWITCH.equals(option_name)) {
                            daikon.tools.InvariantChecker.doConf = true;
                        }else
                            if (daikon.tools.InvariantChecker.filter_SWITCH.equals(option_name)) {
                                daikon.tools.InvariantChecker.doFilter = true;
                            }else
                                if (daikon.tools.InvariantChecker.dir_SWITCH.equals(option_name)) {
                                    daikon.tools.InvariantChecker.dir_file = new java.io.File(g.getOptarg());
                                    if ((!(daikon.tools.InvariantChecker.dir_file.exists())) || (!(daikon.tools.InvariantChecker.dir_file.isDirectory())))
                                        throw new daikon.Daikon.TerminationMessage(("Error reading the directory " + (daikon.tools.InvariantChecker.dir_file)));

                                }else
                                    if (daikon.tools.InvariantChecker.output_SWITCH.equals(option_name)) {
                                        daikon.tools.InvariantChecker.output_file = new java.io.File(g.getOptarg());
                                        daikon.tools.InvariantChecker.output_stream = new java.io.PrintStream(new java.io.FileOutputStream(daikon.tools.InvariantChecker.output_file));
                                    }else
                                        if (daikon.Daikon.config_option_SWITCH.equals(option_name)) {
                                            java.lang.String item = g.getOptarg();
                                            daikon.config.Configuration.getInstance().apply(item);
                                            break;
                                        }else
                                            if (daikon.Daikon.debugAll_SWITCH.equals(option_name)) {
                                                Global.debugAll = true;
                                            }else
                                                if (daikon.Daikon.debug_SWITCH.equals(option_name)) {
                                                    daikon.tools.LogHelper.setLevel(g.getOptarg(), LogHelper.FINE);
                                                }else
                                                    if (daikon.Daikon.track_SWITCH.equals(option_name)) {
                                                        daikon.tools.LogHelper.setLevel("daikon.Debug", LogHelper.FINE);
                                                        java.lang.String error = daikon.tools.Debug.add_track(g.getOptarg());
                                                        if (error != null) {
                                                            throw new daikon.Daikon.TerminationMessage(((("Error parsing track argument '" + (g.getOptarg())) + "' - ") + error));
                                                        }
                                                    }else {
                                                        throw new java.lang.RuntimeException(("Unknown long option received: " + option_name));
                                                    }








                    break;
                case 'h' :
                    java.lang.System.out.println(daikon.tools.InvariantChecker.usage);
                    throw new daikon.Daikon.TerminationMessage();
                case '?' :
                    break;
                default :
                    java.lang.System.out.println(("getopt() returned " + c));
                    break;
            }
        } 
        for (int i = g.getOptind(); i < (args.length); i++) {
            java.io.File file = new java.io.File(args[i]);
            if (!(file.exists())) {
                throw new java.lang.Error((("File " + file) + " not found."));
            }
            java.lang.String filename = file.toString();
            if ((filename.indexOf(".inv")) != (-1)) {
                if ((daikon.tools.InvariantChecker.inv_file) != null) {
                    throw new daikon.Daikon.TerminationMessage("multiple inv files specified", daikon.tools.InvariantChecker.usage);
                }
                daikon.tools.InvariantChecker.inv_file = file;
            }else
                if ((filename.indexOf(".dtrace")) != (-1)) {
                    daikon.tools.InvariantChecker.dtrace_files.add(filename);
                }else {
                    throw new java.lang.Error(("Unrecognized argument: " + file));
                }

        }
        if ((daikon.tools.InvariantChecker.dir_file) == null) {
            daikon.tools.InvariantChecker.checkInvariants();
            return;
        }
        java.io.File[] filesInDir = daikon.tools.InvariantChecker.dir_file.listFiles();
        if ((filesInDir == null) || ((filesInDir.length) == 0))
            throw new daikon.Daikon.TerminationMessage((("The directory " + (daikon.tools.InvariantChecker.dir_file)) + " is empty"), daikon.tools.InvariantChecker.usage);

        java.util.ArrayList<java.io.File> invariants = new java.util.ArrayList<java.io.File>();
        for (java.io.File f : filesInDir)
            if ((f.toString().indexOf(".inv")) != (-1))
                invariants.add(f);


        if ((invariants.size()) == 0)
            throw new daikon.Daikon.TerminationMessage(("Did not find any invariant files in the directory " + (daikon.tools.InvariantChecker.dir_file)), daikon.tools.InvariantChecker.usage);

        java.util.ArrayList<java.io.File> dtraces = new java.util.ArrayList<java.io.File>();
        for (java.io.File f : filesInDir)
            if ((f.toString().indexOf(".dtrace")) != (-1))
                dtraces.add(f);


        if ((dtraces.size()) == 0)
            throw new daikon.Daikon.TerminationMessage(("Did not find any dtrace files in the directory " + (daikon.tools.InvariantChecker.dir_file)), daikon.tools.InvariantChecker.usage);

        java.lang.System.out.println(((("Collecting data for invariants files " + invariants) + " and dtrace files ") + dtraces));
        daikon.tools.InvariantChecker.dtrace_files.clear();
        for (java.io.File dtrace : dtraces) {
            daikon.tools.InvariantChecker.dtrace_files.add(dtrace.toString());
        }
        java.lang.String commaLine = "";
        for (java.io.File inFile : invariants) {
            java.lang.String name = inFile.getName().replace(".inv", "").replace(".gz", "");
            commaLine += "," + name;
        }
        daikon.tools.InvariantChecker.outputComma.add(commaLine);
        commaLine = "";
        for (java.io.File inFile : invariants) {
            daikon.tools.InvariantChecker.inv_file = inFile;
            daikon.tools.InvariantChecker.failedInvariants.clear();
            daikon.tools.InvariantChecker.testedInvariants.clear();
            daikon.tools.InvariantChecker.error_cnt = 0;
            daikon.tools.InvariantChecker.output_stream = new java.io.PrintStream(new java.io.FileOutputStream(((inFile.toString().replace(".inv", "").replace(".gz", "")) + ".false-positives.txt")));
            daikon.tools.InvariantChecker.checkInvariants();
            daikon.tools.InvariantChecker.output_stream.close();
            int failedCount = daikon.tools.InvariantChecker.failedInvariants.size();
            int testedCount = daikon.tools.InvariantChecker.testedInvariants.size();
            java.lang.String percent = daikon.tools.InvariantChecker.toPercentage(failedCount, testedCount);
            commaLine += "," + percent;
        }
        daikon.tools.InvariantChecker.outputComma.add(commaLine);
        java.lang.System.out.println();
        for (java.lang.String output : daikon.tools.InvariantChecker.outputComma)
            java.lang.System.out.println(output);

    }

    private static java.lang.String toPercentage(int portion, int total) {
        double s = portion * 100;
        return (java.lang.String.format("%.2f", (s / total))) + "%";
    }

    private static void checkInvariants() throws java.io.IOException {
        daikon.tools.PptMap ppts = daikon.FileIO.read_serialized_pptmap(daikon.tools.InvariantChecker.inv_file, true);
        daikon.inv.filter.InvariantFilters fi = daikon.inv.filter.InvariantFilters.defaultFilters();
        java.util.Set<daikon.tools.Invariant> allInvariants = new java.util.HashSet<daikon.tools.Invariant>();
        for (daikon.tools.PptTopLevel ppt : ppts.all_ppts())
            for (java.util.Iterator<daikon.tools.PptSlice> i = ppt.views_iterator(); i.hasNext();) {
                daikon.tools.PptSlice slice = i.next();
                for (daikon.tools.Invariant inv : slice.invs) {
                    if ((daikon.tools.InvariantChecker.doConf) && ((inv.getConfidence()) < (Invariant.dkconfig_confidence_limit))) {
                        continue;
                    }
                    if ((daikon.tools.InvariantChecker.doFilter) && ((fi.shouldKeep(inv)) == null)) {
                        continue;
                    }
                    daikon.tools.InvariantChecker.activeInvariants.add(inv);
                    allInvariants.add(inv);
                }
            }

        daikon.FileIO.Processor processor = new daikon.tools.InvariantChecker.InvariantCheckProcessor();
        daikon.Daikon.FileIOProgress progress = new daikon.Daikon.FileIOProgress();
        progress.start();
        progress.clear();
        daikon.FileIO.read_data_trace_files(daikon.tools.InvariantChecker.dtrace_files, ppts, processor, false);
        progress.shouldStop = true;
        java.lang.System.out.println();
        java.lang.System.out.printf("%s: %,d errors found in %,d samples (%s)\n", daikon.tools.InvariantChecker.inv_file, daikon.tools.InvariantChecker.error_cnt, daikon.tools.InvariantChecker.sample_cnt, daikon.tools.InvariantChecker.toPercentage(daikon.tools.InvariantChecker.error_cnt, daikon.tools.InvariantChecker.sample_cnt));
        int failedCount = daikon.tools.InvariantChecker.failedInvariants.size();
        int testedCount = daikon.tools.InvariantChecker.testedInvariants.size();
        java.lang.String percent = daikon.tools.InvariantChecker.toPercentage(failedCount, testedCount);
        java.lang.System.out.println(((((((((daikon.tools.InvariantChecker.inv_file) + ": ") + failedCount) + " false positives, out of ") + testedCount) + ", which is ") + percent) + "."));
        if (false) {
            for (daikon.tools.Invariant inv : daikon.tools.InvariantChecker.failedInvariants) {
                java.lang.System.out.printf("+%s:%s\n", inv.ppt.name(), inv.format());
            }
        }
    }

    static final class EnterCall {
        public daikon.tools.PptTopLevel ppt;

        public daikon.tools.ValueTuple vt;

        public EnterCall(daikon.tools.PptTopLevel ppt, daikon.tools.ValueTuple vt) {
            this.ppt = ppt;
            this.vt = vt;
        }
    }

    public static class InvariantCheckProcessor extends daikon.FileIO.Processor {
        daikon.tools.PptMap all_ppts = null;

        java.util.Map<java.lang.Integer, daikon.tools.InvariantChecker.EnterCall> call_map = new java.util.LinkedHashMap<java.lang.Integer, daikon.tools.InvariantChecker.EnterCall>();

        public void process_sample(daikon.tools.PptMap all_ppts, daikon.tools.PptTopLevel ppt, daikon.tools.ValueTuple vt, java.lang.Integer nonce) {
            this.all_ppts = all_ppts;
            daikon.tools.InvariantChecker.debug.fine(("processing sample from: " + (ppt.name)));
            daikon.FileIO.add_orig_variables(ppt, vt.vals, vt.mods, nonce);
            daikon.FileIO.add_derived_variables(ppt, vt.vals, vt.mods);
            vt = new daikon.tools.ValueTuple(vt.vals, vt.mods);
            if (ppt.ppt_name.isEnterPoint()) {
                daikon.tools.Assert.assertTrue((nonce != null));
                if ((daikon.tools.InvariantChecker.dir_file) != null) {
                    call_map.remove(nonce);
                }else
                    daikon.tools.Assert.assertTrue(((call_map.get(nonce)) == null));

                call_map.put(nonce, new daikon.tools.InvariantChecker.EnterCall(ppt, vt));
                daikon.tools.InvariantChecker.debug.fine("Skipping enter sample");
                return;
            }
            if (ppt.ppt_name.isExitPoint()) {
                daikon.tools.Assert.assertTrue((nonce != null));
                daikon.tools.InvariantChecker.EnterCall ec = call_map.get(nonce);
                if (ec != null) {
                    call_map.remove(nonce);
                    daikon.tools.InvariantChecker.debug.fine(("Processing enter sample from " + (ec.ppt.name)));
                    add(ec.ppt, ec.vt);
                }else {
                    if (!(daikon.tools.InvariantChecker.quiet))
                        java.lang.System.out.printf("couldn\'t find enter for nonce %d at ppt %s\n", nonce, ppt.name());

                    return;
                }
            }
            add(ppt, vt);
        }

        private void add(daikon.tools.PptTopLevel ppt, daikon.tools.ValueTuple vt) {
            if (ppt.has_splitters()) {
                for (daikon.tools.PptSplitter ppt_split : ppt.splitters) {
                    daikon.tools.PptConditional ppt_cond = ppt_split.choose_conditional(vt);
                    if (ppt_cond != null)
                        add(ppt_cond, vt);
                    else
                        daikon.tools.InvariantChecker.debug.fine(": sample doesn't pick conditional");

                }
            }
            if ((!(ppt instanceof daikon.tools.PptConditional)) && (ppt.ppt_name.isNumberedExitPoint())) {
                daikon.tools.PptTopLevel parent = all_ppts.get(ppt.ppt_name.makeExit());
                if (parent != null) {
                    parent.get_missingOutOfBounds(ppt, vt);
                    add(parent, vt);
                }
            }
            if ((ppt.var_infos.length) == 0)
                return;

            if (false && ((ppt.num_samples()) <= 0))
                daikon.tools.Assert.assertTrue(((ppt.num_samples()) > 0), (((("ppt " + (ppt.name)) + " has 0 samples and ") + (ppt.var_infos.length)) + " variables"));

            slice_loop : for (java.util.Iterator<daikon.tools.PptSlice> i = ppt.views_iterator(); i.hasNext();) {
                daikon.tools.PptSlice slice = i.next();
                if (daikon.tools.InvariantChecker.debug_detail.isLoggable(java.util.logging.Level.FINE))
                    daikon.tools.InvariantChecker.debug_detail.fine((((": processing slice " + slice) + "vars: ") + (daikon.tools.Debug.toString(slice.var_infos, vt))));

                for (int j = 0; j < (slice.var_infos.length); j++) {
                    daikon.tools.VarInfo v = slice.var_infos[j];
                    int mod = vt.getModified(v);
                    if (v.isMissing(vt)) {
                        if (daikon.tools.InvariantChecker.debug_detail.isLoggable(java.util.logging.Level.FINE))
                            daikon.tools.InvariantChecker.debug_detail.fine(((": : Skipping slice, " + (v.name())) + " missing"));

                        continue slice_loop;
                    }
                    if (v.missingOutOfBounds()) {
                        if (daikon.tools.InvariantChecker.debug_detail.isLoggable(java.util.logging.Level.FINE))
                            daikon.tools.InvariantChecker.debug.fine(((": : Skipping slice, " + (v.name())) + " out of bounds"));

                        continue slice_loop;
                    }
                }
                for (daikon.tools.Invariant inv : slice.invs) {
                    if (daikon.tools.InvariantChecker.debug_detail.isLoggable(java.util.logging.Level.FINE))
                        daikon.tools.InvariantChecker.debug_detail.fine((": : Processing invariant: " + inv));

                    if (!(inv.isActive())) {
                        if (daikon.tools.InvariantChecker.debug_detail.isLoggable(java.util.logging.Level.FINE))
                            daikon.tools.InvariantChecker.debug_detail.fine((": : skipped non-active " + inv));

                        continue;
                    }
                    if (!(daikon.tools.InvariantChecker.activeInvariants.contains(inv))) {
                        continue;
                    }
                    daikon.tools.InvariantChecker.testedInvariants.add(inv);
                    daikon.tools.InvariantStatus status = inv.add_sample(vt, 1);
                    (daikon.tools.InvariantChecker.sample_cnt)++;
                    if (status != (InvariantStatus.NO_CHANGE)) {
                        java.io.LineNumberReader lnr = daikon.FileIO.data_trace_state.reader;
                        java.lang.String line = (lnr == null) ? "?" : java.lang.String.valueOf(lnr.getLineNumber());
                        if (!(daikon.tools.InvariantChecker.quiet)) {
                            daikon.tools.InvariantChecker.output_stream.println(((((((((("At ppt " + (ppt.name)) + ", Invariant '") + (inv.format())) + "' invalidated by sample ") + (daikon.tools.Debug.toString(slice.var_infos, vt))) + "at line ") + line) + " in file ") + (daikon.FileIO.data_trace_state.filename)));
                        }
                        daikon.tools.InvariantChecker.failedInvariants.add(inv);
                        daikon.tools.InvariantChecker.activeInvariants.remove(inv);
                        (daikon.tools.InvariantChecker.error_cnt)++;
                    }
                }
            }
        }
    }

    private static java.lang.String invariant2str(daikon.tools.PptTopLevel ppt, daikon.tools.Invariant inv) {
        return ((((((ppt.name) + " == ") + (inv.repr())) + (inv.getClass())) + (inv.varNames())) + ": ") + (inv.format());
    }
}

