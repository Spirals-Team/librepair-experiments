package daikon;


public final class Daikon {
    private Daikon() {
        throw new java.lang.Error("do not instantiate");
    }

    public static int dkconfig_progress_delay = 1000;

    public static boolean dkconfig_show_stack_trace = false;

    public static final java.lang.String release_version = "4.3.1";

    public static final java.lang.String release_date = "August 2, 2007";

    public static final java.lang.String release_string = ((("Daikon version " + (daikon.Daikon.release_version)) + ", released ") + (daikon.Daikon.release_date)) + "; http://pag.csail.mit.edu/daikon.";

    public static boolean dkconfig_output_conditionals = true;

    public static boolean dkconfig_enable_floats = true;

    public static boolean dkconfig_calc_possible_invs;

    public static int dkconfig_ppt_perc = 100;

    public static boolean dkconfig_print_sample_totals = false;

    public static final java.lang.String lineSep = Global.lineSep;

    public static boolean dkconfig_disable_splitting = false;

    public static boolean dkconfig_quiet = false;

    public static final boolean check_program_types = true;

    public static final boolean invariants_check_canBeMissing = false;

    public static final boolean invariants_check_canBeMissing_arrayelt = true;

    public static final boolean disable_modbit_check_message = false;

    public static final boolean disable_modbit_check_error = false;

    public static boolean no_text_output = false;

    public static boolean show_progress = false;

    public static boolean use_equality_optimization = true;

    public static boolean dkconfig_use_dynamic_constant_optimization = true;

    public static boolean dkconfig_undo_opts = false;

    public static boolean using_DaikonSimple = false;

    public static java.lang.String dkconfig_guardNulls = "default";

    public static boolean dkconfig_suppressSplitterErrors = false;

    public static boolean use_dataflow_hierarchy = true;

    public static boolean suppress_implied_controlled_invariants = true;

    public static boolean suppress_implied_postcondition_over_prestate_invariants = false;

    public static boolean suppress_redundant_invariants_with_simplify = false;

    public static daikon.inv.OutputFormat output_format = daikon.inv.OutputFormat.DAIKON;

    public static boolean output_num_samples = false;

    public static boolean ignore_comparability = false;

    public static java.util.regex.Pattern ppt_regexp;

    public static java.util.regex.Pattern ppt_omit_regexp;

    public static java.util.regex.Pattern var_regexp;

    public static java.util.regex.Pattern var_omit_regexp;

    public static boolean dkconfig_internal_check = false;

    public static java.lang.String ppt_max_name = null;

    public static java.io.File inv_file;

    private static boolean use_mem_monitor = false;

    public static boolean noversion_output = false;

    public static boolean isInferencing = false;

    public static boolean omit_from_output = false;

    public static boolean[] omit_types = new boolean[256];

    public static final java.lang.String help_SWITCH = "help";

    public static final java.lang.String no_text_output_SWITCH = "no_text_output";

    public static final java.lang.String format_SWITCH = "format";

    public static final java.lang.String show_progress_SWITCH = "show_progress";

    public static final java.lang.String no_show_progress_SWITCH = "no_show_progress";

    public static final java.lang.String noversion_SWITCH = "noversion";

    public static final java.lang.String output_num_samples_SWITCH = "output_num_samples";

    public static final java.lang.String files_from_SWITCH = "files_from";

    public static final java.lang.String omit_from_output_SWITCH = "omit_from_output";

    public static final java.lang.String conf_limit_SWITCH = "conf_limit";

    public static final java.lang.String list_type_SWITCH = "list_type";

    public static final java.lang.String no_dataflow_hierarchy_SWITCH = "nohierarchy";

    public static final java.lang.String suppress_redundant_SWITCH = "suppress_redundant";

    public static final java.lang.String ppt_regexp_SWITCH = "ppt-select-pattern";

    public static final java.lang.String ppt_omit_regexp_SWITCH = "ppt-omit-pattern";

    public static final java.lang.String var_regexp_SWITCH = "var-select-pattern";

    public static final java.lang.String var_omit_regexp_SWITCH = "var-omit-pattern";

    public static final java.lang.String server_SWITCH = "server";

    public static final java.lang.String config_SWITCH = "config";

    public static final java.lang.String config_option_SWITCH = "config_option";

    public static final java.lang.String debugAll_SWITCH = "debug";

    public static final java.lang.String debug_SWITCH = "dbg";

    public static final java.lang.String track_SWITCH = "track";

    public static final java.lang.String disc_reason_SWITCH = "disc_reason";

    public static final java.lang.String mem_stat_SWITCH = "mem_stat";

    public static java.io.File server_dir = null;

    public static daikon.PptMap all_ppts;

    public static daikon.inv.Invariant current_inv = null;

    public static java.util.ArrayList<daikon.inv.Invariant> proto_invs = new java.util.ArrayList<daikon.inv.Invariant>();

    public static final java.util.logging.Logger debugTrace = java.util.logging.Logger.getLogger("daikon.Daikon");

    public static final java.util.logging.Logger debugProgress = java.util.logging.Logger.getLogger("daikon.Progress");

    public static final java.util.logging.Logger debugEquality = java.util.logging.Logger.getLogger("daikon.Equality");

    public static final java.util.logging.Logger debugInit = java.util.logging.Logger.getLogger("daikon.init");

    public static final java.util.logging.Logger debugStats = java.util.logging.Logger.getLogger("daikon.stats");

    static {
        daikon.Runtime.no_dtrace = true;
    }

    private static utilMDE.Stopwatch stopwatch = new utilMDE.Stopwatch();

    static java.lang.String usage = utilMDE.UtilMDE.joinLines(daikon.Daikon.release_string, "Daikon invariant detector, copyright 1998-2007", "Uses the Java port of GNU getopt, copyright (c) 1998 Aaron M. Renn", "Usage:", "    java daikon.Daikon [flags...] files...", "  Each file is a declaration file or a data trace file; the file type", "  is determined by the file name (containing \".decls\" or \".dtrace\").", "  For a list of flags, see the Daikon manual, which appears in the ", "  Daikon distribution and also at http://pag.csail.mit.edu/daikon/.", (("  --" + (daikon.Daikon.server_SWITCH)) + " dir"), "  Server mode for Daikon in which it reads files from <dir> as they appear (sorted lexicographically) until it finds a file ending in '.end'");

    public static class TerminationMessage extends java.lang.RuntimeException {
        static final long serialVersionUID = 20050923L;

        public TerminationMessage(java.lang.String s) {
            super(s);
        }

        public TerminationMessage(java.lang.String format, java.lang.Object... args) {
            super(java.lang.String.format(format, args));
        }

        public TerminationMessage(java.lang.Exception e) {
            super(e.getMessage());
        }

        public TerminationMessage(java.lang.Object... s) {
            super(utilMDE.UtilMDE.joinLines(s));
        }

        public TerminationMessage() {
            super();
        }
    }

    public static void main(final java.lang.String[] args) {
        try {
            daikon.Daikon.mainHelper(args);
        } catch (daikon.config.Configuration e) {
            java.lang.System.err.println(e.getMessage());
            java.lang.System.exit(1);
        } catch (daikon.Daikon.TerminationMessage e) {
            if ((e.getMessage()) != null) {
                java.lang.System.err.println(e.getMessage());
                if (daikon.Daikon.dkconfig_show_stack_trace)
                    e.printStackTrace();

                java.lang.System.exit(1);
            }else {
                java.lang.System.exit(0);
            }
        }
    }

    public static void mainHelper(final java.lang.String[] args) {
        daikon.Daikon.cleanup();
        daikon.Daikon.FileOptions files = daikon.Daikon.read_options(args, daikon.Daikon.usage);
        java.util.Set<java.io.File> decls_files = files.decls;
        java.util.Set<java.lang.String> dtrace_files = files.dtrace;
        java.util.Set<java.io.File> spinfo_files = files.spinfo;
        java.util.Set<java.io.File> map_files = files.map;
        if ((((daikon.Daikon.server_dir) == null) && ((decls_files.size()) == 0)) && ((dtrace_files.size()) == 0)) {
            java.lang.System.out.println("No .decls or .dtrace files specified");
            throw new daikon.Daikon.TerminationMessage("No .decls or .dtrace files specified");
        }
        if (daikon.Daikon.dkconfig_undo_opts) {
            daikon.Daikon.dkconfig_disable_splitting = true;
        }
        if (daikon.Daikon.dkconfig_quiet)
            daikon.Daikon.dkconfig_progress_delay = -1;

        daikon.LogHelper.setupLogs((Global.debugAll ? LogHelper.FINE : LogHelper.INFO));
        if (!(daikon.Daikon.noversion_output)) {
            if (!(daikon.Daikon.dkconfig_quiet))
                java.lang.System.out.println(daikon.Daikon.release_string);

        }
        if ((daikon.suppress.NIS.dkconfig_suppression_processor) == (daikon.suppress.NIS.SuppressionProcessor.HYBRID)) {
            daikon.suppress.NIS.hybrid_method = true;
        }else {
            if ((daikon.suppress.NIS.dkconfig_suppression_processor) == (daikon.suppress.NIS.SuppressionProcessor.ANTECEDENT)) {
                daikon.suppress.NIS.antecedent_method = true;
                daikon.suppress.NIS.hybrid_method = false;
            }else {
                assert (daikon.suppress.NIS.dkconfig_suppression_processor) == (daikon.suppress.NIS.SuppressionProcessor.FALSIFIED);
                daikon.suppress.NIS.antecedent_method = false;
                daikon.suppress.NIS.hybrid_method = false;
            }
        }
        daikon.Daikon.setup_proto_invs();
        if (PrintInvariants.print_discarded_invariants) {
            daikon.DiscReasonMap.initialize();
        }
        daikon.Daikon.fileio_progress = new daikon.Daikon.FileIOProgress();
        daikon.Daikon.fileio_progress.start();
        daikon.Daikon.load_spinfo_files(spinfo_files);
        daikon.Daikon.all_ppts = daikon.Daikon.load_decls_files(decls_files);
        daikon.Daikon.load_map_files(daikon.Daikon.all_ppts, map_files);
        daikon.Daikon.all_ppts.trimToSize();
        if (daikon.Daikon.dkconfig_calc_possible_invs) {
            daikon.Daikon.fileio_progress.shouldStop = true;
            int total_invs = 0;
            for (java.util.Iterator<daikon.PptTopLevel> itor = daikon.Daikon.all_ppts.ppt_all_iterator(); itor.hasNext();) {
                daikon.PptTopLevel ppt = itor.next();
                java.lang.System.out.printf("Processing %s with %d variables", ppt.name(), ppt.var_infos.length);
                int inv_cnt = 0;
                if ((ppt.var_infos.length) > 1600) {
                    java.lang.System.out.println("Skipping, too many variables!");
                }else {
                    ppt.instantiate_views_and_invariants();
                    inv_cnt = ppt.invariant_cnt();
                    ppt.clean_for_merge();
                    java.lang.System.out.println(((inv_cnt + " invariants in ") + (ppt.name())));
                    total_invs += inv_cnt;
                }
            }
            java.lang.System.out.println((total_invs + "invariants total"));
            return;
        }
        daikon.Daikon.isInferencing = true;
        daikon.Daikon.process_data(daikon.Daikon.all_ppts, dtrace_files);
        daikon.Daikon.isInferencing = false;
        if (daikon.Debug.logOn())
            daikon.Debug.check(daikon.Daikon.all_ppts, "After process data");

        if (daikon.Daikon.suppress_redundant_invariants_with_simplify) {
            daikon.Daikon.suppressWithSimplify(daikon.Daikon.all_ppts);
        }
        daikon.Daikon.all_ppts.repCheck();
        if (daikon.Daikon.omit_from_output) {
            daikon.Daikon.processOmissions(daikon.Daikon.all_ppts);
        }
        if ((daikon.Daikon.inv_file) != null) {
            try {
                daikon.FileIO.write_serialized_pptmap(daikon.Daikon.all_ppts, daikon.Daikon.inv_file);
            } catch (java.io.IOException e) {
                throw new java.lang.RuntimeException((((("Error while writing .inv file " + "'") + (daikon.Daikon.inv_file)) + "': ") + (e.toString())));
            }
        }
        if (false) {
            for (daikon.PptTopLevel ppt : daikon.Daikon.all_ppts.all_ppts()) {
                java.lang.System.out.printf("Dumping variables for ppt %s%n", ppt.name());
                for (daikon.VarInfo vi : ppt.var_infos) {
                    java.lang.System.out.printf("  vi %s%n", vi);
                    java.lang.System.out.printf("    file_rep_type = %s%n", vi.file_rep_type);
                    java.lang.System.out.printf("    type = %s%n", vi.type);
                }
            }
        }
        if (daikon.Daikon.dkconfig_undo_opts) {
            for (java.util.Iterator<daikon.PptTopLevel> t = daikon.Daikon.all_ppts.pptIterator(); t.hasNext();) {
                daikon.PptTopLevel ppt = t.next();
                if ((ppt.num_samples()) == 0) {
                    continue;
                }
                java.util.List<daikon.inv.Invariant> invs = daikon.PrintInvariants.sort_invariant_list(ppt.invariants_vector());
                java.util.List<daikon.inv.Invariant> filtered_invs = daikon.Daikon.filter_invs(invs);
                java.lang.System.out.println("====================================================");
                java.lang.System.out.println(ppt.name());
                java.lang.System.out.println(ppt.num_samples());
                for (daikon.inv.Invariant inv : filtered_invs) {
                    java.lang.System.out.println(inv.getClass());
                    java.lang.System.out.println(inv);
                }
            }
            return;
        }
        if (daikon.Daikon.output_num_samples) {
            java.lang.System.out.println("The --output_num_samples debugging flag is on.");
            java.lang.System.out.println("Some of the debugging output may only make sense to Daikon programmers.");
        }
        if (!(PrintInvariants.print_discarded_invariants)) {
            daikon.PrintInvariants.print_invariants(daikon.Daikon.all_ppts);
        }else {
            daikon.PrintInvariants.print_reasons(daikon.Daikon.all_ppts);
        }
        if (daikon.Daikon.output_num_samples) {
            daikon.Global.output_statistics();
        }
        if (daikon.Daikon.dkconfig_print_sample_totals)
            java.lang.System.out.println(((daikon.FileIO.samples_processed) + " samples processed"));

        if (daikon.Daikon.debugStats.isLoggable(java.util.logging.Level.FINE)) {
            for (java.util.Iterator<daikon.PptTopLevel> itor = daikon.Daikon.all_ppts.ppt_all_iterator(); itor.hasNext();) {
                daikon.PptTopLevel ppt = itor.next();
                daikon.PrintInvariants.print_filter_stats(daikon.Daikon.debugStats, ppt, daikon.Daikon.all_ppts);
            }
        }
        if (!(daikon.Daikon.dkconfig_quiet)) {
            java.lang.System.out.println("Exiting Daikon.");
        }
    }

    public static void cleanup() {
        if (((daikon.Daikon.fileio_progress) != null) && ((daikon.Daikon.fileio_progress.getState()) != (java.lang.Thread.State.NEW))) {
            daikon.Daikon.fileio_progress.shouldStop = true;
            try {
                daikon.Daikon.fileio_progress.join(2000);
            } catch (java.lang.InterruptedException e) {
            }
            if ((daikon.Daikon.fileio_progress.getState()) != (java.lang.Thread.State.TERMINATED)) {
                throw new daikon.Daikon.TerminationMessage("Can't stop fileio_progress thead");
            }
        }
        daikon.Daikon.fileio_progress = null;
        daikon.Daikon.progress = "";
        daikon.Daikon.proto_invs.clear();
    }

    public static class FileOptions {
        public java.util.Set<java.io.File> decls;

        public java.util.Set<java.lang.String> dtrace;

        public java.util.Set<java.io.File> spinfo;

        public java.util.Set<java.io.File> map;

        public FileOptions(java.util.Set<java.io.File> decls, java.util.Set<java.lang.String> dtrace, java.util.Set<java.io.File> spinfo, java.util.Set<java.io.File> map) {
            this.decls = decls;
            this.dtrace = dtrace;
            this.spinfo = spinfo;
            this.map = map;
        }
    }

    protected static daikon.Daikon.FileOptions read_options(java.lang.String[] args, java.lang.String usage) {
        if ((args.length) == 0) {
            java.lang.System.out.println("Daikon error: no files supplied on command line.");
            java.lang.System.out.println(usage);
            throw new daikon.Daikon.TerminationMessage();
        }
        java.util.HashSet<java.io.File> decl_files = new java.util.LinkedHashSet<java.io.File>();
        java.util.HashSet<java.lang.String> dtrace_files = new java.util.LinkedHashSet<java.lang.String>();
        java.util.HashSet<java.io.File> spinfo_files = new java.util.LinkedHashSet<java.io.File>();
        java.util.HashSet<java.io.File> map_files = new java.util.LinkedHashSet<java.io.File>();
        gnu.getopt.LongOpt[] longopts = new gnu.getopt.LongOpt[]{ new gnu.getopt.LongOpt(daikon.Daikon.help_SWITCH, gnu.getopt.LongOpt.NO_ARGUMENT, null, 0), new gnu.getopt.LongOpt(daikon.Daikon.no_text_output_SWITCH, gnu.getopt.LongOpt.NO_ARGUMENT, null, 0), new gnu.getopt.LongOpt(daikon.Daikon.format_SWITCH, gnu.getopt.LongOpt.REQUIRED_ARGUMENT, null, 0), new gnu.getopt.LongOpt(daikon.Daikon.show_progress_SWITCH, gnu.getopt.LongOpt.NO_ARGUMENT, null, 0), new gnu.getopt.LongOpt(daikon.Daikon.no_show_progress_SWITCH, gnu.getopt.LongOpt.NO_ARGUMENT, null, 0), new gnu.getopt.LongOpt(daikon.Daikon.noversion_SWITCH, gnu.getopt.LongOpt.NO_ARGUMENT, null, 0), new gnu.getopt.LongOpt(daikon.Daikon.output_num_samples_SWITCH, gnu.getopt.LongOpt.NO_ARGUMENT, null, 0), new gnu.getopt.LongOpt(daikon.Daikon.files_from_SWITCH, gnu.getopt.LongOpt.REQUIRED_ARGUMENT, null, 0), new gnu.getopt.LongOpt(daikon.Daikon.omit_from_output_SWITCH, gnu.getopt.LongOpt.REQUIRED_ARGUMENT, null, 0), new gnu.getopt.LongOpt(daikon.Daikon.conf_limit_SWITCH, gnu.getopt.LongOpt.REQUIRED_ARGUMENT, null, 0), new gnu.getopt.LongOpt(daikon.Daikon.list_type_SWITCH, gnu.getopt.LongOpt.REQUIRED_ARGUMENT, null, 0), new gnu.getopt.LongOpt(daikon.Daikon.no_dataflow_hierarchy_SWITCH, gnu.getopt.LongOpt.NO_ARGUMENT, null, 0), new gnu.getopt.LongOpt(daikon.Daikon.suppress_redundant_SWITCH, gnu.getopt.LongOpt.NO_ARGUMENT, null, 0), new gnu.getopt.LongOpt(daikon.Daikon.ppt_regexp_SWITCH, gnu.getopt.LongOpt.REQUIRED_ARGUMENT, null, 0), new gnu.getopt.LongOpt(daikon.Daikon.ppt_omit_regexp_SWITCH, gnu.getopt.LongOpt.REQUIRED_ARGUMENT, null, 0), new gnu.getopt.LongOpt(daikon.Daikon.var_regexp_SWITCH, gnu.getopt.LongOpt.REQUIRED_ARGUMENT, null, 0), new gnu.getopt.LongOpt(daikon.Daikon.var_omit_regexp_SWITCH, gnu.getopt.LongOpt.REQUIRED_ARGUMENT, null, 0), new gnu.getopt.LongOpt(daikon.Daikon.server_SWITCH, gnu.getopt.LongOpt.REQUIRED_ARGUMENT, null, 0), new gnu.getopt.LongOpt(daikon.Daikon.config_SWITCH, gnu.getopt.LongOpt.REQUIRED_ARGUMENT, null, 0), new gnu.getopt.LongOpt(daikon.Daikon.config_option_SWITCH, gnu.getopt.LongOpt.REQUIRED_ARGUMENT, null, 0), new gnu.getopt.LongOpt(daikon.Daikon.debugAll_SWITCH, gnu.getopt.LongOpt.NO_ARGUMENT, null, 0), new gnu.getopt.LongOpt(daikon.Daikon.debug_SWITCH, gnu.getopt.LongOpt.REQUIRED_ARGUMENT, null, 0), new gnu.getopt.LongOpt(daikon.Daikon.track_SWITCH, gnu.getopt.LongOpt.REQUIRED_ARGUMENT, null, 0), new gnu.getopt.LongOpt(daikon.Daikon.disc_reason_SWITCH, gnu.getopt.LongOpt.REQUIRED_ARGUMENT, null, 0), new gnu.getopt.LongOpt(daikon.Daikon.mem_stat_SWITCH, gnu.getopt.LongOpt.NO_ARGUMENT, null, 0) };
        gnu.getopt.Getopt g = new gnu.getopt.Getopt("daikon.Daikon", args, "ho:", longopts);
        int c;
        while ((c = g.getopt()) != (-1)) {
            switch (c) {
                case 0 :
                    java.lang.String option_name = longopts[g.getLongind()].getName();
                    if (daikon.Daikon.help_SWITCH.equals(option_name)) {
                        java.lang.System.out.println(usage);
                        throw new daikon.Daikon.TerminationMessage();
                    }else
                        if (daikon.Daikon.no_text_output_SWITCH.equals(option_name)) {
                            daikon.Daikon.no_text_output = true;
                        }else
                            if (daikon.Daikon.format_SWITCH.equals(option_name)) {
                                java.lang.String format_name = g.getOptarg();
                                daikon.Daikon.output_format = daikon.inv.OutputFormat.get(format_name);
                                if ((daikon.Daikon.output_format) == null) {
                                    throw new daikon.Daikon.TerminationMessage(("Unknown output format:  --format " + format_name));
                                }
                            }else
                                if (daikon.Daikon.show_progress_SWITCH.equals(option_name)) {
                                    daikon.Daikon.show_progress = true;
                                    daikon.LogHelper.setLevel("daikon.Progress", LogHelper.FINE);
                                }else
                                    if (daikon.Daikon.no_show_progress_SWITCH.equals(option_name)) {
                                        daikon.Daikon.show_progress = false;
                                    }else
                                        if (daikon.Daikon.noversion_SWITCH.equals(option_name)) {
                                            daikon.Daikon.noversion_output = true;
                                        }else
                                            if (daikon.Daikon.output_num_samples_SWITCH.equals(option_name)) {
                                                daikon.Daikon.output_num_samples = true;
                                            }else
                                                if (daikon.Daikon.files_from_SWITCH.equals(option_name)) {
                                                    java.lang.String files_from_filename = g.getOptarg();
                                                    try {
                                                        for (java.lang.String filename : new utilMDE.TextFile(files_from_filename)) {
                                                            if (filename.equals("")) {
                                                                continue;
                                                            }
                                                            java.io.File file = new java.io.File(filename);
                                                            if (!(file.exists())) {
                                                                throw new daikon.Daikon.TerminationMessage((("File " + filename) + " not found."));
                                                            }
                                                            if ((filename.indexOf(".decls")) != (-1)) {
                                                                decl_files.add(file);
                                                            }else
                                                                if ((filename.indexOf(".dtrace")) != (-1)) {
                                                                    dtrace_files.add(filename);
                                                                }else
                                                                    if ((filename.indexOf(".spinfo")) != (-1)) {
                                                                        spinfo_files.add(file);
                                                                    }else
                                                                        if ((filename.indexOf(".map")) != (-1)) {
                                                                            map_files.add(file);
                                                                        }else {
                                                                            throw new daikon.Daikon.TerminationMessage(("Unrecognized file extension: " + filename));
                                                                        }



                                                        }
                                                    } catch (java.io.IOException e) {
                                                        throw new java.lang.RuntimeException(java.lang.String.format("Error reading --files_from file: %s", files_from_filename));
                                                    }
                                                    break;
                                                }else
                                                    if (daikon.Daikon.omit_from_output_SWITCH.equals(option_name)) {
                                                        java.lang.String f = g.getOptarg();
                                                        for (int i = 0; i < (f.length()); i++) {
                                                            if (("0rs".indexOf(f.charAt(i))) == (-1))
                                                                throw new daikon.Daikon.TerminationMessage((("omit_from_output flag letter '" + (f.charAt(i))) + "' is unknown"));

                                                            daikon.Daikon.omit_types[f.charAt(i)] = true;
                                                        }
                                                        daikon.Daikon.omit_from_output = true;
                                                    }else
                                                        if (daikon.Daikon.conf_limit_SWITCH.equals(option_name)) {
                                                            double limit = java.lang.Double.parseDouble(g.getOptarg());
                                                            if ((limit < 0.0) || (limit > 1.0)) {
                                                                throw new daikon.Daikon.TerminationMessage(((daikon.Daikon.conf_limit_SWITCH) + " must be between [0..1]"));
                                                            }
                                                            daikon.config.Configuration.getInstance().apply("daikon.inv.Invariant.confidence_limit", java.lang.String.valueOf(limit));
                                                        }else
                                                            if (daikon.Daikon.list_type_SWITCH.equals(option_name)) {
                                                                try {
                                                                    java.lang.String list_type_string = g.getOptarg();
                                                                    ProglangType.list_implementors.add(list_type_string);
                                                                } catch (java.lang.Exception e) {
                                                                    throw new daikon.Daikon.TerminationMessage(((("Problem parsing " + (daikon.Daikon.list_type_SWITCH)) + " option: ") + e));
                                                                }
                                                                break;
                                                            }else
                                                                if (daikon.Daikon.no_dataflow_hierarchy_SWITCH.equals(option_name)) {
                                                                    daikon.Daikon.use_dataflow_hierarchy = false;
                                                                }else
                                                                    if (daikon.Daikon.suppress_redundant_SWITCH.equals(option_name)) {
                                                                        daikon.Daikon.suppress_redundant_invariants_with_simplify = true;
                                                                    }else
                                                                        if (daikon.Daikon.ppt_regexp_SWITCH.equals(option_name)) {
                                                                            if ((daikon.Daikon.ppt_regexp) != null)
                                                                                throw new daikon.Daikon.TerminationMessage((("multiple --" + (daikon.Daikon.ppt_regexp_SWITCH)) + " regular expressions supplied on command line"));

                                                                            java.lang.String regexp_string = g.getOptarg();
                                                                            try {
                                                                                daikon.Daikon.ppt_regexp = java.util.regex.Pattern.compile(regexp_string);
                                                                            } catch (java.lang.Exception e) {
                                                                                throw new daikon.Daikon.TerminationMessage(((((("Bad regexp " + regexp_string) + " for ") + (daikon.Daikon.ppt_regexp_SWITCH)) + ": ") + (e.getMessage())));
                                                                            }
                                                                            break;
                                                                        }else
                                                                            if (daikon.Daikon.ppt_omit_regexp_SWITCH.equals(option_name)) {
                                                                                if ((daikon.Daikon.ppt_omit_regexp) != null)
                                                                                    throw new daikon.Daikon.TerminationMessage((("multiple --" + (daikon.Daikon.ppt_omit_regexp_SWITCH)) + " regular expressions supplied on command line"));

                                                                                java.lang.String regexp_string = g.getOptarg();
                                                                                try {
                                                                                    daikon.Daikon.ppt_omit_regexp = java.util.regex.Pattern.compile(regexp_string);
                                                                                } catch (java.lang.Exception e) {
                                                                                    throw new daikon.Daikon.TerminationMessage(((((("Bad regexp " + regexp_string) + " for ") + (daikon.Daikon.ppt_omit_regexp_SWITCH)) + ": ") + (e.getMessage())));
                                                                                }
                                                                                break;
                                                                            }else
                                                                                if (daikon.Daikon.var_regexp_SWITCH.equals(option_name)) {
                                                                                    if ((daikon.Daikon.var_regexp) != null)
                                                                                        throw new daikon.Daikon.TerminationMessage((("multiple --" + (daikon.Daikon.var_regexp_SWITCH)) + " regular expressions supplied on command line"));

                                                                                    java.lang.String regexp_string = g.getOptarg();
                                                                                    try {
                                                                                        daikon.Daikon.var_regexp = java.util.regex.Pattern.compile(regexp_string);
                                                                                    } catch (java.lang.Exception e) {
                                                                                        throw new daikon.Daikon.TerminationMessage(((((("Bad regexp " + regexp_string) + " for ") + (daikon.Daikon.var_regexp_SWITCH)) + ": ") + (e.getMessage())));
                                                                                    }
                                                                                    break;
                                                                                }else
                                                                                    if (daikon.Daikon.var_omit_regexp_SWITCH.equals(option_name)) {
                                                                                        if ((daikon.Daikon.var_omit_regexp) != null)
                                                                                            throw new daikon.Daikon.TerminationMessage((("multiple --" + (daikon.Daikon.var_omit_regexp_SWITCH)) + " regular expressions supplied on command line"));

                                                                                        java.lang.String regexp_string = g.getOptarg();
                                                                                        try {
                                                                                            daikon.Daikon.var_omit_regexp = java.util.regex.Pattern.compile(regexp_string);
                                                                                        } catch (java.lang.Exception e) {
                                                                                            throw new daikon.Daikon.TerminationMessage(((((("Bad regexp " + regexp_string) + " for ") + (daikon.Daikon.var_omit_regexp_SWITCH)) + ": ") + (e.getMessage())));
                                                                                        }
                                                                                        break;
                                                                                    }else
                                                                                        if (daikon.Daikon.server_SWITCH.equals(option_name)) {
                                                                                            java.lang.String input_dir = g.getOptarg();
                                                                                            daikon.Daikon.server_dir = new java.io.File(input_dir);
                                                                                            if (((!(daikon.Daikon.server_dir.isDirectory())) || (!(daikon.Daikon.server_dir.canRead()))) || (!(daikon.Daikon.server_dir.canWrite())))
                                                                                                throw new java.lang.RuntimeException(("Could not open config file in server directory " + (daikon.Daikon.server_dir)));

                                                                                            break;
                                                                                        }else
                                                                                            if (daikon.Daikon.config_SWITCH.equals(option_name)) {
                                                                                                java.lang.String config_file = g.getOptarg();
                                                                                                try {
                                                                                                    java.io.InputStream stream = new java.io.FileInputStream(config_file);
                                                                                                    daikon.config.Configuration.getInstance().apply(stream);
                                                                                                } catch (java.io.IOException e) {
                                                                                                    throw new daikon.Daikon.TerminationMessage(("Could not open config file " + config_file));
                                                                                                }
                                                                                                break;
                                                                                            }else
                                                                                                if (daikon.Daikon.config_option_SWITCH.equals(option_name)) {
                                                                                                    java.lang.String item = g.getOptarg();
                                                                                                    try {
                                                                                                        daikon.config.Configuration.getInstance().apply(item);
                                                                                                    } catch (daikon.daikon.config e) {
                                                                                                        throw new daikon.Daikon.TerminationMessage(e);
                                                                                                    }
                                                                                                    break;
                                                                                                }else
                                                                                                    if (daikon.Daikon.debugAll_SWITCH.equals(option_name)) {
                                                                                                        Global.debugAll = true;
                                                                                                    }else
                                                                                                        if (daikon.Daikon.debug_SWITCH.equals(option_name)) {
                                                                                                            daikon.LogHelper.setLevel(g.getOptarg(), LogHelper.FINE);
                                                                                                        }else
                                                                                                            if (daikon.Daikon.track_SWITCH.equals(option_name)) {
                                                                                                                daikon.LogHelper.setLevel("daikon.Debug", LogHelper.FINE);
                                                                                                                java.lang.String error = daikon.Debug.add_track(g.getOptarg());
                                                                                                                if (error != null) {
                                                                                                                    throw new daikon.Daikon.TerminationMessage(((("Error parsing track argument '" + (g.getOptarg())) + "' - ") + error));
                                                                                                                }
                                                                                                            }else
                                                                                                                if (daikon.Daikon.disc_reason_SWITCH.equals(option_name)) {
                                                                                                                    try {
                                                                                                                        daikon.PrintInvariants.discReasonSetup(g.getOptarg());
                                                                                                                    } catch (java.lang.IllegalArgumentException e) {
                                                                                                                        throw new daikon.Daikon.TerminationMessage(e);
                                                                                                                    }
                                                                                                                }else
                                                                                                                    if (daikon.Daikon.mem_stat_SWITCH.equals(option_name)) {
                                                                                                                        daikon.Daikon.use_mem_monitor = true;
                                                                                                                    }else {
                                                                                                                        throw new daikon.Daikon.TerminationMessage((("Unknown option " + option_name) + " on command line"));
                                                                                                                    }
























                    break;
                case 'h' :
                    java.lang.System.out.println(usage);
                    throw new daikon.Daikon.TerminationMessage();
                case 'o' :
                    java.lang.String inv_filename = g.getOptarg();
                    if ((daikon.Daikon.inv_file) != null) {
                        throw new daikon.Daikon.TerminationMessage(((("multiple serialization output files supplied on command line: " + (daikon.Daikon.inv_file)) + " ") + inv_filename));
                    }
                    daikon.Daikon.inv_file = new java.io.File(inv_filename);
                    if (!(utilMDE.UtilMDE.canCreateAndWrite(daikon.Daikon.inv_file))) {
                        throw new daikon.Daikon.TerminationMessage(("Cannot write to serialization output file " + (daikon.Daikon.inv_file)));
                    }
                    break;
                case '?' :
                    java.lang.System.out.println(usage);
                    throw new daikon.Daikon.TerminationMessage();
                default :
                    java.lang.System.out.println(("getopt() returned " + c));
                    break;
            }
        } 
        for (int i = g.getOptind(); i < (args.length); i++) {
            java.lang.String filename = args[i];
            java.io.File file = null;
            if ((!(filename.equals("-"))) && (!(filename.equals("+")))) {
                file = new java.io.File(filename);
                if (!(file.exists())) {
                    throw new daikon.Daikon.TerminationMessage((("File " + file) + " not found."));
                }
                filename = file.toString();
            }
            if ((filename.indexOf(".decls")) != (-1)) {
                decl_files.add(file);
            }else
                if ((filename.indexOf(".dtrace")) != (-1)) {
                    dtrace_files.add(filename);
                    if ((daikon.Daikon.inv_file) == null) {
                        java.lang.String basename;
                        basename = new java.io.File(filename).getName();
                        int base_end = basename.indexOf(".dtrace");
                        java.lang.String inv_filename = (basename.substring(0, base_end)) + ".inv.gz";
                        daikon.Daikon.inv_file = new java.io.File(inv_filename);
                        if (!(utilMDE.UtilMDE.canCreateAndWrite(daikon.Daikon.inv_file))) {
                            throw new daikon.Daikon.TerminationMessage(("Cannot write to file " + (daikon.Daikon.inv_file)));
                        }
                    }
                }else
                    if ((filename.indexOf(".spinfo")) != (-1)) {
                        spinfo_files.add(file);
                    }else
                        if ((filename.indexOf(".map")) != (-1)) {
                            map_files.add(file);
                        }else
                            if ((filename.equals("-")) || (filename.equals("+"))) {
                                dtrace_files.add(filename);
                            }else {
                                throw new daikon.Daikon.TerminationMessage(("Unrecognized file type: " + file));
                            }




        }
        Global.fuzzy.set_rel_diff(Invariant.dkconfig_fuzzy_ratio);
        if ((daikon.Daikon.dkconfig_ppt_perc) != 100) {
            daikon.Daikon.ppt_max_name = daikon.Daikon.setup_ppt_perc(decl_files, daikon.Daikon.dkconfig_ppt_perc);
            java.lang.System.out.println(("Max ppt name = " + (daikon.Daikon.ppt_max_name)));
        }
        daikon.PrintInvariants.validateGuardNulls();
        return new daikon.Daikon.FileOptions(decl_files, dtrace_files, spinfo_files, map_files);
    }

    public static void setup_proto_invs() {
        {
            daikon.Daikon.proto_invs.add(daikon.inv.unary.scalar.OneOfScalar.get_proto());
            daikon.Daikon.proto_invs.add(daikon.inv.unary.scalar.OneOfFloat.get_proto());
            daikon.Daikon.proto_invs.add(daikon.inv.unary.string.OneOfString.get_proto());
            daikon.Daikon.proto_invs.add(daikon.inv.unary.scalar.NonZero.get_proto());
            daikon.Daikon.proto_invs.add(daikon.inv.unary.scalar.NonZeroFloat.get_proto());
            daikon.Daikon.proto_invs.add(daikon.inv.unary.scalar.LowerBound.get_proto());
            daikon.Daikon.proto_invs.add(daikon.inv.unary.scalar.LowerBoundFloat.get_proto());
            daikon.Daikon.proto_invs.add(daikon.inv.unary.scalar.UpperBound.get_proto());
            daikon.Daikon.proto_invs.add(daikon.inv.unary.scalar.UpperBoundFloat.get_proto());
            daikon.Daikon.proto_invs.add(daikon.inv.unary.scalar.Modulus.get_proto());
            daikon.Daikon.proto_invs.add(daikon.inv.unary.scalar.NonModulus.get_proto());
            daikon.Daikon.proto_invs.addAll(daikon.inv.unary.scalar.RangeInt.get_proto_all());
            daikon.Daikon.proto_invs.addAll(daikon.inv.unary.scalar.RangeFloat.get_proto_all());
            daikon.Daikon.proto_invs.add(daikon.inv.unary.string.PrintableString.get_proto());
        }
        {
            daikon.Daikon.proto_invs.add(daikon.inv.unary.sequence.OneOfSequence.get_proto());
            daikon.Daikon.proto_invs.add(daikon.inv.unary.sequence.OneOfFloatSequence.get_proto());
            daikon.Daikon.proto_invs.add(daikon.inv.unary.stringsequence.OneOfStringSequence.get_proto());
            daikon.Daikon.proto_invs.add(daikon.inv.unary.sequence.EltOneOf.get_proto());
            daikon.Daikon.proto_invs.add(daikon.inv.unary.sequence.EltOneOfFloat.get_proto());
            daikon.Daikon.proto_invs.add(daikon.inv.unary.stringsequence.EltOneOfString.get_proto());
            daikon.Daikon.proto_invs.addAll(daikon.inv.unary.sequence.EltRangeInt.get_proto_all());
            daikon.Daikon.proto_invs.addAll(daikon.inv.unary.sequence.EltRangeFloat.get_proto_all());
            daikon.Daikon.proto_invs.add(daikon.inv.unary.sequence.SeqIndexIntEqual.get_proto());
            daikon.Daikon.proto_invs.add(daikon.inv.unary.sequence.SeqIndexIntNonEqual.get_proto());
            daikon.Daikon.proto_invs.add(daikon.inv.unary.sequence.SeqIndexIntGreaterEqual.get_proto());
            daikon.Daikon.proto_invs.add(daikon.inv.unary.sequence.SeqIndexIntGreaterThan.get_proto());
            daikon.Daikon.proto_invs.add(daikon.inv.unary.sequence.SeqIndexIntLessEqual.get_proto());
            daikon.Daikon.proto_invs.add(daikon.inv.unary.sequence.SeqIndexIntLessThan.get_proto());
            daikon.Daikon.proto_invs.add(daikon.inv.unary.sequence.SeqIndexFloatEqual.get_proto());
            daikon.Daikon.proto_invs.add(daikon.inv.unary.sequence.SeqIndexFloatNonEqual.get_proto());
            daikon.Daikon.proto_invs.add(daikon.inv.unary.sequence.SeqIndexFloatGreaterEqual.get_proto());
            daikon.Daikon.proto_invs.add(daikon.inv.unary.sequence.SeqIndexFloatGreaterThan.get_proto());
            daikon.Daikon.proto_invs.add(daikon.inv.unary.sequence.SeqIndexFloatLessEqual.get_proto());
            daikon.Daikon.proto_invs.add(daikon.inv.unary.sequence.SeqIndexFloatLessThan.get_proto());
            daikon.Daikon.proto_invs.add(daikon.inv.unary.sequence.EltwiseIntEqual.get_proto());
            daikon.Daikon.proto_invs.add(daikon.inv.unary.sequence.EltwiseIntLessEqual.get_proto());
            daikon.Daikon.proto_invs.add(daikon.inv.unary.sequence.EltwiseIntGreaterEqual.get_proto());
            daikon.Daikon.proto_invs.add(daikon.inv.unary.sequence.EltwiseIntLessThan.get_proto());
            daikon.Daikon.proto_invs.add(daikon.inv.unary.sequence.EltwiseIntGreaterThan.get_proto());
            daikon.Daikon.proto_invs.add(daikon.inv.unary.sequence.EltwiseFloatEqual.get_proto());
            daikon.Daikon.proto_invs.add(daikon.inv.unary.sequence.EltwiseFloatLessEqual.get_proto());
            daikon.Daikon.proto_invs.add(daikon.inv.unary.sequence.EltwiseFloatGreaterEqual.get_proto());
            daikon.Daikon.proto_invs.add(daikon.inv.unary.sequence.EltwiseFloatLessThan.get_proto());
            daikon.Daikon.proto_invs.add(daikon.inv.unary.sequence.EltwiseFloatGreaterThan.get_proto());
            daikon.Daikon.proto_invs.add(daikon.inv.unary.sequence.EltNonZero.get_proto());
            daikon.Daikon.proto_invs.add(daikon.inv.unary.sequence.EltNonZeroFloat.get_proto());
            daikon.Daikon.proto_invs.add(daikon.inv.unary.sequence.NoDuplicates.get_proto());
            daikon.Daikon.proto_invs.add(daikon.inv.unary.sequence.NoDuplicatesFloat.get_proto());
            daikon.Daikon.proto_invs.add(daikon.inv.unary.sequence.EltLowerBound.get_proto());
            daikon.Daikon.proto_invs.add(daikon.inv.unary.sequence.EltUpperBound.get_proto());
            daikon.Daikon.proto_invs.add(daikon.inv.unary.sequence.EltLowerBoundFloat.get_proto());
            daikon.Daikon.proto_invs.add(daikon.inv.unary.sequence.EltUpperBoundFloat.get_proto());
            daikon.Daikon.proto_invs.add(daikon.inv.unary.sequence.CommonSequence.get_proto());
            daikon.Daikon.proto_invs.add(daikon.inv.unary.sequence.CommonFloatSequence.get_proto());
            daikon.Daikon.proto_invs.add(daikon.inv.unary.stringsequence.CommonStringSequence.get_proto());
        }
        {
            daikon.Daikon.proto_invs.add(daikon.inv.binary.twoScalar.IntEqual.get_proto());
            daikon.Daikon.proto_invs.add(daikon.inv.binary.twoScalar.IntNonEqual.get_proto());
            daikon.Daikon.proto_invs.add(daikon.inv.binary.twoScalar.IntLessThan.get_proto());
            daikon.Daikon.proto_invs.add(daikon.inv.binary.twoScalar.IntGreaterThan.get_proto());
            daikon.Daikon.proto_invs.add(daikon.inv.binary.twoScalar.IntLessEqual.get_proto());
            daikon.Daikon.proto_invs.add(daikon.inv.binary.twoScalar.IntGreaterEqual.get_proto());
            daikon.Daikon.proto_invs.add(daikon.inv.binary.twoScalar.FloatEqual.get_proto());
            daikon.Daikon.proto_invs.add(daikon.inv.binary.twoScalar.FloatNonEqual.get_proto());
            daikon.Daikon.proto_invs.add(daikon.inv.binary.twoScalar.FloatLessThan.get_proto());
            daikon.Daikon.proto_invs.add(daikon.inv.binary.twoScalar.FloatGreaterThan.get_proto());
            daikon.Daikon.proto_invs.add(daikon.inv.binary.twoScalar.FloatLessEqual.get_proto());
            daikon.Daikon.proto_invs.add(daikon.inv.binary.twoScalar.FloatGreaterEqual.get_proto());
            daikon.Daikon.proto_invs.add(daikon.inv.binary.twoString.StringEqual.get_proto());
            daikon.Daikon.proto_invs.add(daikon.inv.binary.twoString.StringNonEqual.get_proto());
            daikon.Daikon.proto_invs.add(daikon.inv.binary.twoString.StringLessThan.get_proto());
            daikon.Daikon.proto_invs.add(daikon.inv.binary.twoString.StringGreaterThan.get_proto());
            daikon.Daikon.proto_invs.add(daikon.inv.binary.twoString.StringLessEqual.get_proto());
            daikon.Daikon.proto_invs.add(daikon.inv.binary.twoString.StringGreaterEqual.get_proto());
            daikon.Daikon.proto_invs.add(daikon.inv.binary.twoScalar.LinearBinary.get_proto());
            daikon.Daikon.proto_invs.add(daikon.inv.binary.twoScalar.LinearBinaryFloat.get_proto());
            daikon.Daikon.proto_invs.addAll(daikon.inv.binary.twoScalar.NumericInt.get_proto_all());
            daikon.Daikon.proto_invs.addAll(daikon.inv.binary.twoScalar.NumericFloat.get_proto_all());
        }
        {
            daikon.Daikon.proto_invs.addAll(daikon.inv.binary.twoSequence.PairwiseNumericInt.get_proto_all());
            daikon.Daikon.proto_invs.addAll(daikon.inv.binary.twoSequence.PairwiseNumericFloat.get_proto_all());
            daikon.Daikon.proto_invs.add(daikon.inv.binary.twoSequence.SeqSeqIntEqual.get_proto());
            daikon.Daikon.proto_invs.add(daikon.inv.binary.twoSequence.SeqSeqIntLessThan.get_proto());
            daikon.Daikon.proto_invs.add(daikon.inv.binary.twoSequence.SeqSeqIntGreaterThan.get_proto());
            daikon.Daikon.proto_invs.add(daikon.inv.binary.twoSequence.SeqSeqIntLessEqual.get_proto());
            daikon.Daikon.proto_invs.add(daikon.inv.binary.twoSequence.SeqSeqIntGreaterEqual.get_proto());
            daikon.Daikon.proto_invs.add(daikon.inv.binary.twoSequence.SeqSeqFloatEqual.get_proto());
            daikon.Daikon.proto_invs.add(daikon.inv.binary.twoSequence.SeqSeqFloatLessThan.get_proto());
            daikon.Daikon.proto_invs.add(daikon.inv.binary.twoSequence.SeqSeqFloatGreaterThan.get_proto());
            daikon.Daikon.proto_invs.add(daikon.inv.binary.twoSequence.SeqSeqFloatLessEqual.get_proto());
            daikon.Daikon.proto_invs.add(daikon.inv.binary.twoSequence.SeqSeqFloatGreaterEqual.get_proto());
            daikon.Daikon.proto_invs.add(daikon.inv.binary.twoSequence.SeqSeqStringEqual.get_proto());
            daikon.Daikon.proto_invs.add(daikon.inv.binary.twoSequence.SeqSeqStringLessThan.get_proto());
            daikon.Daikon.proto_invs.add(daikon.inv.binary.twoSequence.SeqSeqStringGreaterThan.get_proto());
            daikon.Daikon.proto_invs.add(daikon.inv.binary.twoSequence.SeqSeqStringLessEqual.get_proto());
            daikon.Daikon.proto_invs.add(daikon.inv.binary.twoSequence.SeqSeqStringGreaterEqual.get_proto());
            daikon.Daikon.proto_invs.add(daikon.inv.binary.twoSequence.PairwiseIntEqual.get_proto());
            daikon.Daikon.proto_invs.add(daikon.inv.binary.twoSequence.PairwiseIntLessThan.get_proto());
            daikon.Daikon.proto_invs.add(daikon.inv.binary.twoSequence.PairwiseIntGreaterThan.get_proto());
            daikon.Daikon.proto_invs.add(daikon.inv.binary.twoSequence.PairwiseIntLessEqual.get_proto());
            daikon.Daikon.proto_invs.add(daikon.inv.binary.twoSequence.PairwiseIntGreaterEqual.get_proto());
            daikon.Daikon.proto_invs.add(daikon.inv.binary.twoSequence.PairwiseFloatEqual.get_proto());
            daikon.Daikon.proto_invs.add(daikon.inv.binary.twoSequence.PairwiseFloatLessThan.get_proto());
            daikon.Daikon.proto_invs.add(daikon.inv.binary.twoSequence.PairwiseFloatGreaterThan.get_proto());
            daikon.Daikon.proto_invs.add(daikon.inv.binary.twoSequence.PairwiseFloatLessEqual.get_proto());
            daikon.Daikon.proto_invs.add(daikon.inv.binary.twoSequence.PairwiseFloatGreaterEqual.get_proto());
            daikon.Daikon.proto_invs.add(daikon.inv.binary.twoSequence.Reverse.get_proto());
            daikon.Daikon.proto_invs.add(daikon.inv.binary.twoSequence.ReverseFloat.get_proto());
            daikon.Daikon.proto_invs.add(daikon.inv.binary.twoSequence.PairwiseLinearBinary.get_proto());
            daikon.Daikon.proto_invs.add(daikon.inv.binary.twoSequence.PairwiseLinearBinaryFloat.get_proto());
            daikon.Daikon.proto_invs.add(daikon.inv.binary.twoSequence.SubSet.get_proto());
            daikon.Daikon.proto_invs.add(daikon.inv.binary.twoSequence.SuperSet.get_proto());
            daikon.Daikon.proto_invs.add(daikon.inv.binary.twoSequence.SubSetFloat.get_proto());
            daikon.Daikon.proto_invs.add(daikon.inv.binary.twoSequence.SuperSetFloat.get_proto());
            daikon.Daikon.proto_invs.add(daikon.inv.binary.twoSequence.SubSequence.get_proto());
            daikon.Daikon.proto_invs.add(daikon.inv.binary.twoSequence.SubSequenceFloat.get_proto());
            daikon.Daikon.proto_invs.add(daikon.inv.binary.twoSequence.SuperSequence.get_proto());
            daikon.Daikon.proto_invs.add(daikon.inv.binary.twoSequence.SuperSequenceFloat.get_proto());
        }
        {
            daikon.Daikon.proto_invs.add(daikon.inv.binary.sequenceScalar.SeqIntEqual.get_proto());
            daikon.Daikon.proto_invs.add(daikon.inv.binary.sequenceScalar.SeqIntLessThan.get_proto());
            daikon.Daikon.proto_invs.add(daikon.inv.binary.sequenceScalar.SeqIntGreaterThan.get_proto());
            daikon.Daikon.proto_invs.add(daikon.inv.binary.sequenceScalar.SeqIntLessEqual.get_proto());
            daikon.Daikon.proto_invs.add(daikon.inv.binary.sequenceScalar.SeqIntGreaterEqual.get_proto());
            daikon.Daikon.proto_invs.add(daikon.inv.binary.sequenceScalar.SeqFloatEqual.get_proto());
            daikon.Daikon.proto_invs.add(daikon.inv.binary.sequenceScalar.SeqFloatLessThan.get_proto());
            daikon.Daikon.proto_invs.add(daikon.inv.binary.sequenceScalar.SeqFloatGreaterThan.get_proto());
            daikon.Daikon.proto_invs.add(daikon.inv.binary.sequenceScalar.SeqFloatLessEqual.get_proto());
            daikon.Daikon.proto_invs.add(daikon.inv.binary.sequenceScalar.SeqFloatGreaterEqual.get_proto());
            daikon.Daikon.proto_invs.add(daikon.inv.binary.sequenceScalar.Member.get_proto());
            daikon.Daikon.proto_invs.add(daikon.inv.binary.sequenceScalar.MemberFloat.get_proto());
            daikon.Daikon.proto_invs.add(daikon.inv.binary.sequenceString.MemberString.get_proto());
        }
        {
            daikon.Daikon.proto_invs.addAll(daikon.inv.ternary.threeScalar.FunctionBinary.get_proto_all());
            daikon.Daikon.proto_invs.addAll(daikon.inv.ternary.threeScalar.FunctionBinaryFloat.get_proto_all());
            daikon.Daikon.proto_invs.add(daikon.inv.ternary.threeScalar.LinearTernary.get_proto());
            daikon.Daikon.proto_invs.add(daikon.inv.ternary.threeScalar.LinearTernaryFloat.get_proto());
        }
        for (java.util.Iterator<daikon.inv.Invariant> i = daikon.Daikon.proto_invs.iterator(); i.hasNext();) {
            daikon.inv.Invariant inv = i.next();
            utilMDE.Assert.assertTrue((inv != null));
            if (!(inv.enabled()))
                i.remove();

        }
    }

    public static void createUpperPpts(daikon.PptMap all_ppts) {
        for (java.util.Iterator<daikon.PptTopLevel> i = all_ppts.pptIterator(); i.hasNext();) {
            daikon.PptTopLevel ppt = i.next();
            if ((ppt.parents.size()) == 0) {
                ppt.mergeInvs();
            }
        }
    }

    public static void init_ppt(daikon.PptTopLevel ppt, daikon.PptMap all_ppts) {
        if (!(daikon.Daikon.using_DaikonSimple)) {
            if (!(ppt instanceof daikon.PptConditional)) {
                daikon.Daikon.setup_splitters(ppt);
            }
        }
        daikon.Daikon.progress = "Creating orig variables for: " + (ppt.name);
        daikon.Daikon.create_orig_vars(ppt, all_ppts);
        if (!(daikon.derive.Derivation.dkconfig_disable_derived_variables)) {
            daikon.Daikon.progress = "Creating derived variables for: " + (ppt.name);
            ppt.create_derived_variables();
        }
        if (!(daikon.Daikon.using_DaikonSimple)) {
            daikon.Daikon.setupEquality(ppt);
            if (ppt.has_splitters()) {
                for (java.util.Iterator<daikon.PptConditional> ii = ppt.cond_iterator(); ii.hasNext();) {
                    daikon.PptConditional ppt_cond = ii.next();
                    daikon.Daikon.init_ppt(ppt_cond, all_ppts);
                }
            }
        }
    }

    public static void create_combined_exits(daikon.PptMap ppts) {
        daikon.PptMap exit_ppts = new daikon.PptMap();
        for (java.util.Iterator<daikon.PptTopLevel> i = ppts.pptIterator(); i.hasNext();) {
            daikon.PptTopLevel ppt = i.next();
            if (!(ppt.is_subexit()))
                continue;

            daikon.PptTopLevel exitnn_ppt = ppt;
            daikon.PptName exitnn_name = exitnn_ppt.ppt_name;
            daikon.PptName exit_name = ppt.ppt_name.makeExit();
            daikon.PptTopLevel exit_ppt = exit_ppts.get(exit_name);
            if (daikon.Daikon.debugInit.isLoggable(java.util.logging.Level.FINE))
                daikon.Daikon.debugInit.fine(("create_combined_exits: encounted exit " + (exitnn_ppt.name())));

            if (exit_ppt == null) {
                int len = (ppt.num_tracevars) + (ppt.num_static_constant_vars);
                daikon.VarInfo[] exit_vars = new daikon.VarInfo[len];
                for (int j = 0; j < len; j++) {
                    exit_vars[j] = new daikon.VarInfo(ppt.var_infos[j]);
                    exit_vars[j].varinfo_index = ppt.var_infos[j].varinfo_index;
                    exit_vars[j].value_index = ppt.var_infos[j].value_index;
                    exit_vars[j].equalitySet = null;
                }
                exit_ppt = new daikon.PptTopLevel(exit_name.getName(), PptTopLevel.PptType.EXIT, ppt.parent_relations, ppt.flags, exit_vars);
                exit_ppts.add(exit_ppt);
                if (daikon.Daikon.debugInit.isLoggable(java.util.logging.Level.FINE))
                    daikon.Daikon.debugInit.fine(("create_combined_exits: created exit " + exit_name));

                daikon.Daikon.init_ppt(exit_ppt, ppts);
            }
        }
        for (java.util.Iterator<daikon.PptTopLevel> i = exit_ppts.pptIterator(); i.hasNext();) {
            daikon.PptTopLevel ppt = i.next();
            ppts.add(ppt);
        }
    }

    static java.util.List<daikon.inv.Invariant> filter_invs(java.util.List<daikon.inv.Invariant> invs) {
        java.util.List<daikon.inv.Invariant> new_list = new java.util.ArrayList<daikon.inv.Invariant>();
        for (daikon.inv.Invariant inv : invs) {
            daikon.VarInfo[] vars = inv.ppt.var_infos;
            if (!(((inv.ppt) instanceof daikon.PptSlice2) && ((vars[0]) == (vars[1])))) {
                if (!(((inv.ppt) instanceof daikon.PptSlice3) && ((((vars[0]) == (vars[1])) || ((vars[1]) == (vars[2]))) || ((vars[0]) == (vars[2]))))) {
                    if ((inv.ppt.num_values()) != 0) {
                        if (inv.isActive()) {
                            new_list.add(inv);
                        }
                    }
                }
            }
        }
        return new_list;
    }

    private static void create_orig_vars(daikon.PptTopLevel exit_ppt, daikon.PptMap ppts) {
        if (!(exit_ppt.ppt_name.isExitPoint())) {
            return;
        }
        if (daikon.Daikon.debugInit.isLoggable(java.util.logging.Level.FINE)) {
            daikon.Daikon.debugInit.fine(("Doing create and relate orig vars for: " + (exit_ppt.name())));
        }
        daikon.PptTopLevel entry_ppt = ppts.get(exit_ppt.ppt_name.makeEnter());
        utilMDE.Assert.assertTrue((entry_ppt != null), exit_ppt.name());
        exit_ppt.num_orig_vars = entry_ppt.num_tracevars;
        daikon.VarInfo[] new_vis = new daikon.VarInfo[exit_ppt.num_orig_vars];
        {
            daikon.VarInfo[] entry_ppt_vis = entry_ppt.var_infos;
            int new_vis_index = 0;
            for (int k = 0; k < (entry_ppt.num_declvars); k++) {
                daikon.VarInfo vi = entry_ppt_vis[k];
                utilMDE.Assert.assertTrue((!(vi.isDerived())), ("Derived when making orig(): " + (vi.name())));
                if (vi.isStaticConstant())
                    continue;

                daikon.VarInfo origvar = daikon.VarInfo.origVarInfo(vi);
                daikon.VarInfo postvar = exit_ppt.find_var_by_name(vi.name());
                if (postvar == null) {
                    java.lang.System.out.printf("Cant find var %s in exit of ppt %s%n", vi, exit_ppt.name());
                    for (daikon.VarInfo cvi : entry_ppt.var_infos)
                        java.lang.System.out.printf("  entry var = %s%n", cvi);

                    for (daikon.VarInfo cvi : exit_ppt.var_infos)
                        java.lang.System.out.printf("  exit var = %s%n", cvi);

                    assert false;
                    throw new java.lang.RuntimeException("this can't happen: postvar is null");
                }
                origvar.postState = postvar;
                origvar.comparability = postvar.comparability.makeAlias();
                new_vis[new_vis_index] = origvar;
                new_vis_index++;
            }
            utilMDE.Assert.assertTrue((new_vis_index == (exit_ppt.num_orig_vars)));
        }
        exit_ppt.addVarInfos(new_vis);
    }

    private static daikon.PptMap load_decls_files(java.util.Set<java.io.File> decl_files) {
        daikon.Daikon.stopwatch.reset();
        try {
            if (!(daikon.Daikon.dkconfig_quiet)) {
                java.lang.System.out.print("Reading declaration files ");
            }
            daikon.PptMap all_ppts = daikon.FileIO.read_declaration_files(decl_files);
            if (daikon.Daikon.debugTrace.isLoggable(java.util.logging.Level.FINE)) {
                daikon.Daikon.debugTrace.fine("Initializing partial order");
            }
            daikon.Daikon.fileio_progress.clear();
            if ((!(daikon.Daikon.dkconfig_quiet)) && ((decl_files.size()) > 0)) {
                java.lang.System.out.print(" (read ");
                java.lang.System.out.print(utilMDE.UtilMDE.nplural(decl_files.size(), "decls file"));
                java.lang.System.out.println(")");
            }
            return all_ppts;
        } catch (java.io.IOException e) {
            throw new daikon.Daikon.TerminationMessage("Error parsing decl file", e);
        } finally {
            daikon.Daikon.debugProgress.fine(("Time spent on read_declaration_files: " + (daikon.Daikon.stopwatch.format())));
        }
    }

    private static void load_spinfo_files(java.util.Set<java.io.File> spinfo_files) {
        if ((daikon.Daikon.dkconfig_disable_splitting) || (spinfo_files.isEmpty())) {
            return;
        }
        daikon.Daikon.stopwatch.reset();
        try {
            java.lang.System.out.print("Reading splitter info files ");
            daikon.Daikon.create_splitters(spinfo_files);
            java.lang.System.out.print(" (read ");
            java.lang.System.out.print(utilMDE.UtilMDE.nplural(spinfo_files.size(), "spinfo file"));
            java.lang.System.out.println(")");
        } catch (java.io.IOException e) {
            java.lang.System.out.println();
            e.printStackTrace();
            throw new java.lang.Error(e);
        } finally {
            daikon.Daikon.debugProgress.fine(("Time spent on load_spinfo_files: " + (daikon.Daikon.stopwatch.format())));
        }
    }

    private static void load_map_files(daikon.PptMap all_ppts, java.util.Set<java.io.File> map_files) {
        daikon.Daikon.stopwatch.reset();
        if ((!(daikon.Daikon.dkconfig_disable_splitting)) && ((map_files.size()) > 0)) {
            java.lang.System.out.print("Reading map (context) files ");
            daikon.split.ContextSplitterFactory.load_mapfiles_into_splitterlist(map_files, ContextSplitterFactory.dkconfig_granularity);
            java.lang.System.out.print(" (read ");
            java.lang.System.out.print(utilMDE.UtilMDE.nplural(map_files.size(), "map (context) file"));
            java.lang.System.out.println(")");
            daikon.Daikon.debugProgress.fine(("Time spent on load_map_files: " + (daikon.Daikon.stopwatch.format())));
        }
    }

    public static void setup_splitters(daikon.PptTopLevel ppt) {
        if (daikon.Daikon.dkconfig_disable_splitting) {
            return;
        }
        daikon.split.SplitterFactory.load_splitters(ppt, daikon.Daikon.parsedSplitters);
        daikon.split.Splitter[] pconds = null;
        if (daikon.split.SplitterList.dkconfig_all_splitters) {
            pconds = daikon.split.SplitterList.get_all();
        }else {
            pconds = daikon.split.SplitterList.get(ppt.name());
        }
        if (pconds != null) {
            if (Global.debugSplit.isLoggable(java.util.logging.Level.FINE)) {
                Global.debugSplit.fine(((("Got " + (utilMDE.UtilMDE.nplural(pconds.length, "splitter"))) + " for ") + (ppt.name())));
            }
            ppt.addConditions(pconds);
        }
    }

    public static java.lang.String progress = "";

    public static int dkconfig_progress_display_width = 80;

    private static daikon.Daikon.FileIOProgress fileio_progress = null;

    public static class FileIOProgress extends java.lang.Thread {
        public FileIOProgress() {
            setDaemon(true);
            daikon.Daikon.FileIOProgress.pctFmt = java.text.NumberFormat.getPercentInstance();
            daikon.Daikon.FileIOProgress.pctFmt.setMinimumFractionDigits(2);
            daikon.Daikon.FileIOProgress.pctFmt.setMaximumFractionDigits(2);
            df = java.text.DateFormat.getTimeInstance();
        }

        public boolean shouldStop = false;

        private static java.text.NumberFormat pctFmt;

        private java.text.DateFormat df;

        public void run() {
            if ((daikon.Daikon.dkconfig_progress_delay) == (-1))
                return;

            while (true) {
                if (shouldStop) {
                    clear();
                    return;
                }
                display();
                try {
                    java.lang.Thread.sleep(daikon.Daikon.dkconfig_progress_delay);
                } catch (java.lang.InterruptedException e) {
                }
            } 
        }

        public void clear() {
            if ((daikon.Daikon.dkconfig_progress_delay) == (-1))
                return;

            java.lang.String status = utilMDE.UtilMDE.rpad("", ((daikon.Daikon.dkconfig_progress_display_width) - 1));
            java.lang.System.out.print(("\r" + status));
            java.lang.System.out.print("\r");
            java.lang.System.out.flush();
        }

        public void display() {
            if ((daikon.Daikon.dkconfig_progress_delay) == (-1))
                return;

            display(message());
        }

        public void display(java.lang.String message) {
            if ((daikon.Daikon.dkconfig_progress_delay) == (-1))
                return;

            java.lang.String status = utilMDE.UtilMDE.rpad(((("[" + (df.format(new java.util.Date()))) + "]: ") + message), ((daikon.Daikon.dkconfig_progress_display_width) - 1));
            java.lang.System.out.print(("\r" + status));
            java.lang.System.out.flush();
            if (daikon.Daikon.debugTrace.isLoggable(java.util.logging.Level.FINE)) {
                daikon.Daikon.debugTrace.fine(("Free memory: " + (java.lang.Runtime.getRuntime().freeMemory())));
                daikon.Daikon.debugTrace.fine(("Used memory: " + ((java.lang.Runtime.getRuntime().totalMemory()) - (java.lang.Runtime.getRuntime().freeMemory()))));
                if ((daikon.FileIO.data_trace_state) != null)
                    daikon.Daikon.debugTrace.fine(("Active slices: " + (daikon.FileIO.data_trace_state.all_ppts.countSlices())));

            }
        }

        private java.lang.String message() {
            if ((daikon.FileIO.data_trace_state) == null) {
                if ((daikon.Daikon.progress) == null) {
                    return "[no status]";
                }else {
                    return daikon.Daikon.progress;
                }
            }
            java.lang.String filename = daikon.FileIO.data_trace_state.filename;
            java.io.LineNumberReader lnr = daikon.FileIO.data_trace_state.reader;
            java.lang.String line;
            if (lnr == null) {
                line = "?";
            }else {
                long lineNum = lnr.getLineNumber();
                line = java.lang.String.valueOf(lineNum);
                if ((daikon.FileIO.data_trace_state.total_lines) > 0) {
                    double frac = lineNum / ((double) (daikon.FileIO.data_trace_state.total_lines));
                    java.lang.String percent = daikon.Daikon.FileIOProgress.pctFmt.format(frac);
                    line = (line + ", ") + percent;
                }
            }
            return ((("Reading " + filename) + " (line ") + line) + ") ...";
        }
    }

    private static void process_data(daikon.PptMap all_ppts, java.util.Set<java.lang.String> dtrace_files) {
        daikon.MemMonitor monitor = null;
        if (daikon.Daikon.use_mem_monitor) {
            monitor = new daikon.MemMonitor("stat.out");
            new java.lang.Thread(((java.lang.Runnable) (monitor))).start();
        }
        daikon.Daikon.stopwatch.reset();
        daikon.Daikon.setup_NISuppression();
        try {
            daikon.Daikon.fileio_progress.clear();
            if (!(daikon.Daikon.dkconfig_quiet)) {
                java.lang.System.out.println((("Processing trace data; reading " + (utilMDE.UtilMDE.nplural(dtrace_files.size(), "dtrace file"))) + ":"));
            }
            daikon.FileIO.read_data_trace_files(dtrace_files, all_ppts);
            daikon.Daikon.fileio_progress.shouldStop = true;
            daikon.Daikon.fileio_progress.display();
            if (!(daikon.Daikon.dkconfig_quiet)) {
                java.lang.System.out.println();
            }
        } catch (java.io.IOException e) {
            java.lang.System.out.println();
            e.printStackTrace();
            throw new java.lang.Error(e);
        } finally {
            daikon.Daikon.debugProgress.fine(("Time spent on read_data_trace_files: " + (daikon.Daikon.stopwatch.format())));
        }
        if (monitor != null) {
            monitor.stop();
        }
        if (daikon.FileIO.dkconfig_read_samples_only) {
            throw new daikon.Daikon.TerminationMessage(utilMDE.Fmt.spf("Finished reading %s samples", ("" + (daikon.FileIO.samples_processed))));
        }
        if ((all_ppts.size()) == 0) {
            java.lang.String message = "No program point declarations were found.";
            if ((daikon.FileIO.omitted_declarations) != 0) {
                message += (((((daikon.Daikon.lineSep) + "  ") + (daikon.FileIO.omitted_declarations)) + " ") + ((daikon.FileIO.omitted_declarations) == 1 ? "declaration was" : "declarations were")) + " omitted by regexps (e.g., --ppt-select-pattern).";
            }
            throw new daikon.Daikon.TerminationMessage(message);
        }
        int unmatched_count = (daikon.FileIO.call_stack.size()) + (daikon.FileIO.call_hashmap.size());
        if (((daikon.Daikon.use_dataflow_hierarchy) && ((daikon.FileIO.samples_processed) == unmatched_count)) || ((daikon.FileIO.samples_processed) == 0)) {
            throw new daikon.Daikon.TerminationMessage(("No samples found for any of " + (utilMDE.UtilMDE.nplural(all_ppts.size(), "program point"))));
        }
        daikon.Daikon.stopwatch.reset();
        daikon.Daikon.debugProgress.fine("Create Combined Exits ... ");
        daikon.Daikon.create_combined_exits(all_ppts);
        if (daikon.Daikon.dkconfig_use_dynamic_constant_optimization) {
            daikon.Daikon.debugProgress.fine("Constant Post Processing ... ");
            for (java.util.Iterator<daikon.PptTopLevel> itor = all_ppts.ppt_all_iterator(); itor.hasNext();) {
                daikon.PptTopLevel ppt = itor.next();
                if ((ppt.constants) != null)
                    ppt.constants.post_process();

            }
        }
        daikon.Daikon.debugProgress.fine("Init Hierarchy ... ");
        if (daikon.FileIO.new_decl_format)
            daikon.PptRelation.init_hierarchy_new(all_ppts);
        else
            daikon.PptRelation.init_hierarchy(all_ppts);

        daikon.Daikon.debugProgress.fine("Init Hierarchy ... done");
        if (daikon.Daikon.use_dataflow_hierarchy) {
            daikon.Daikon.debugProgress.fine("createUpperPpts");
            daikon.Daikon.createUpperPpts(all_ppts);
            daikon.Daikon.debugProgress.fine("createUpperPpts ... done");
        }
        if ((daikon.Daikon.use_equality_optimization) && (!(daikon.Daikon.dkconfig_undo_opts))) {
            daikon.Daikon.debugProgress.fine("Equality Post Process ... ");
            for (java.util.Iterator<daikon.PptTopLevel> itor = all_ppts.ppt_all_iterator(); itor.hasNext();) {
                daikon.PptTopLevel ppt = itor.next();
                ppt.postProcessEquality();
            }
            daikon.Daikon.debugProgress.fine("Equality Post Process ... done");
        }
        if (daikon.Daikon.dkconfig_undo_opts) {
            daikon.Daikon.undoOpts(all_ppts);
        }
        if (daikon.Daikon.debugEquality.isLoggable(java.util.logging.Level.FINE)) {
            for (java.util.Iterator<daikon.PptTopLevel> itor = all_ppts.ppt_all_iterator(); itor.hasNext();) {
                daikon.PptTopLevel ppt = itor.next();
                daikon.Daikon.debugEquality.fine((((ppt.name()) + ": ") + (ppt.equality_sets_txt())));
            }
        }
        daikon.Daikon.debugProgress.fine(("Time spent on non-implication postprocessing: " + (daikon.Daikon.stopwatch.format())));
        daikon.Daikon.isInferencing = false;
        daikon.Daikon.stopwatch.reset();
        daikon.Daikon.fileio_progress.clear();
        if (!(daikon.Daikon.dkconfig_disable_splitting)) {
            daikon.Daikon.debugProgress.fine("Adding Implications ... ");
            for (java.util.Iterator<daikon.PptTopLevel> itor = all_ppts.pptIterator(); itor.hasNext();) {
                daikon.PptTopLevel ppt = itor.next();
                ppt.addImplications();
            }
            daikon.Daikon.debugProgress.fine(("Time spent adding implications: " + (daikon.Daikon.stopwatch.format())));
        }
    }

    private static class Count {
        public int val;

        Count(int val) {
            this.val = val;
        }
    }

    public static void ppt_stats(daikon.PptMap all_ppts) {
        int all_ppt_cnt = 0;
        int ppt_w_sample_cnt = 0;
        for (java.util.Iterator<daikon.PptTopLevel> i = all_ppts.pptIterator(); i.hasNext();) {
            daikon.PptTopLevel ppt = i.next();
            all_ppt_cnt++;
            if ((ppt.num_samples()) == 0)
                continue;

            ppt_w_sample_cnt++;
            utilMDE.Fmt.pf("%s", ppt.name());
            utilMDE.Fmt.pf(("  samples    = " + (ppt.num_samples())));
            utilMDE.Fmt.pf(("  invariants = " + (ppt.invariant_cnt())));
            java.util.Map<daikon.ProglangType, daikon.Daikon.Count> type_map = new java.util.LinkedHashMap<daikon.ProglangType, daikon.Daikon.Count>();
            int leader_cnt = 0;
            for (daikon.VarInfo v : ppt.var_infos) {
                if (!(v.isCanonical()))
                    continue;

                leader_cnt++;
                daikon.Daikon.Count cnt = type_map.get(v.file_rep_type);
                if (cnt == null)
                    type_map.put(v.file_rep_type, (cnt = new daikon.Daikon.Count(0)));

                (cnt.val)++;
            }
            utilMDE.Fmt.pf(("  vars       = " + (ppt.var_infos.length)));
            utilMDE.Fmt.pf(("  leaders    = " + leader_cnt));
            for (java.util.Map.Entry<daikon.ProglangType, daikon.Daikon.Count> e : type_map.entrySet()) {
                daikon.ProglangType file_rep_type = e.getKey();
                daikon.Daikon.Count cnt = e.getValue();
                utilMDE.Fmt.pf("  %s  = %s", file_rep_type, ("" + (cnt.val)));
            }
        }
        utilMDE.Fmt.pf(("Total ppt count     = " + all_ppt_cnt));
        utilMDE.Fmt.pf(("PPts w/sample count = " + ppt_w_sample_cnt));
    }

    private static void suppressWithSimplify(daikon.PptMap all_ppts) {
        java.lang.System.out.print("Invoking Simplify to identify redundant invariants");
        java.lang.System.out.flush();
        daikon.Daikon.stopwatch.reset();
        for (java.util.Iterator<daikon.PptTopLevel> itor = all_ppts.ppt_all_iterator(); itor.hasNext();) {
            daikon.PptTopLevel ppt = itor.next();
            ppt.mark_implied_via_simplify(all_ppts);
            java.lang.System.out.print(".");
            java.lang.System.out.flush();
        }
        java.lang.System.out.println(daikon.Daikon.stopwatch.format());
    }

    public static void setup_NISuppression() {
        daikon.suppress.NIS.init_ni_suppression();
    }

    public static void setupEquality(daikon.PptTopLevel ppt) {
        if (!(daikon.Daikon.use_equality_optimization))
            return;

        if (daikon.Daikon.use_dataflow_hierarchy) {
            daikon.PptTopLevel p = ppt;
            if (ppt instanceof daikon.PptConditional)
                p = ((daikon.PptConditional) (ppt)).parent;

            if (((((p.ppt_name.isCombinedExitPoint()) || (p.ppt_name.isEnterPoint())) || (p.ppt_name.isThrowsPoint())) || (p.ppt_name.isObjectInstanceSynthetic())) || (p.ppt_name.isClassStaticSynthetic())) {
                return;
            }
            if (ppt.has_splitters())
                return;

        }
        ppt.equality_view = new daikon.PptSliceEquality(ppt);
        ppt.equality_view.instantiate_invariants();
    }

    private static java.util.List<daikon.split.SpinfoFileParser> parsedSplitters = new java.util.ArrayList<daikon.split.SpinfoFileParser>();

    public static void create_splitters(java.util.Set<java.io.File> spinfo_files) throws java.io.IOException {
        for (java.io.File filename : spinfo_files) {
            daikon.split.SpinfoFileParser p = daikon.split.SplitterFactory.parse_spinfofile(filename);
            daikon.Daikon.parsedSplitters.add(p);
        }
    }

    private static void processOmissions(daikon.PptMap allPpts) {
        if (daikon.Daikon.omit_types['0'])
            allPpts.removeUnsampled();

        for (daikon.PptTopLevel ppt : allPpts.asCollection()) {
            ppt.processOmissions(daikon.Daikon.omit_types);
        }
    }

    private static java.lang.String setup_ppt_perc(java.util.Collection<java.io.File> decl_files, int ppt_perc) {
        if ((ppt_perc < 1) || (ppt_perc > 100))
            throw new java.lang.Error((("ppt_perc of " + ppt_perc) + " is out of range 1..100"));

        if (ppt_perc == 100)
            return null;

        java.util.Set<java.lang.String> ppts = new java.util.TreeSet<java.lang.String>();
        try {
            for (java.io.File file : decl_files) {
                java.io.LineNumberReader fp = utilMDE.UtilMDE.lineNumberFileReader(file);
                for (java.lang.String line = fp.readLine(); line != null; line = fp.readLine()) {
                    if ((line.equals("")) || (daikon.FileIO.isComment(line)))
                        continue;

                    if (!(line.equals("DECLARE")))
                        continue;

                    java.lang.String ppt_name = fp.readLine();
                    ppts.add(ppt_name);
                }
                fp.close();
            }
        } catch (java.io.IOException e) {
            e.printStackTrace();
            throw new java.lang.Error(e);
        }
        int ppt_cnt = ((ppts.size()) * ppt_perc) / 100;
        if (ppt_cnt == 0)
            throw new daikon.Daikon.TerminationMessage((((("ppt_perc of " + ppt_perc) + "% results in processing 0 out of ") + (ppts.size())) + " ppts"));

        for (java.util.Iterator<java.lang.String> i = ppts.iterator(); i.hasNext();) {
            java.lang.String ppt_name = i.next();
            if ((--ppt_cnt) <= 0) {
                java.lang.String last_ppt_name = ppt_name;
                while (i.hasNext()) {
                    ppt_name = i.next();
                    if (((last_ppt_name.indexOf("EXIT")) != (-1)) && ((ppt_name.indexOf("EXIT")) == (-1)))
                        return last_ppt_name;

                    last_ppt_name = ppt_name;
                } 
                return ppt_name;
            }
        }
        throw new java.lang.Error(((("ppt_cnt " + ppt_cnt) + " ppts.size ") + (ppts.size())));
    }

    public static void undoOpts(daikon.PptMap all_ppts) {
        java.util.Iterator<daikon.PptTopLevel> suppress_it = all_ppts.ppt_all_iterator();
        while (suppress_it.hasNext()) {
            daikon.PptTopLevel p = suppress_it.next();
            daikon.suppress.NIS.create_suppressed_invs(p);
        } 
        java.util.Iterator<daikon.PptTopLevel> equality_it = all_ppts.ppt_all_iterator();
        while (equality_it.hasNext()) {
            daikon.PptTopLevel ppt = equality_it.next();
            daikon.PptSliceEquality sliceEquality = ppt.equality_view;
            if (sliceEquality == null) {
                continue;
            }
            java.util.List<daikon.inv.Equality> allNewInvs = new java.util.ArrayList<daikon.inv.Equality>();
            for (daikon.inv.Invariant eq_as_inv : sliceEquality.invs) {
                daikon.inv.Equality eq = ((daikon.inv.Equality) (eq_as_inv));
                daikon.VarInfo leader = eq.leader();
                java.util.List<daikon.VarInfo> vars = new java.util.ArrayList<daikon.VarInfo>();
                for (daikon.VarInfo var : eq.getVars()) {
                    if (!(var.equals(leader))) {
                        vars.add(var);
                    }
                }
                if ((vars.size()) > 0) {
                    java.util.List<daikon.inv.Equality> newInvs = sliceEquality.createEqualityInvs(vars, eq);
                    sliceEquality.copyInvsFromLeader(leader, vars);
                    allNewInvs.addAll(newInvs);
                }
            }
            sliceEquality.invs.addAll(allNewInvs);
        } 
    }
}

