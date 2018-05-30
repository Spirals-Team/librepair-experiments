package daikon;


public final class FileIO {
    private FileIO() {
        throw new java.lang.Error();
    }

    static final java.lang.String declaration_header = "DECLARE";

    public static final java.lang.String ppt_tag_separator = ":::";

    public static final java.lang.String enter_suffix = "ENTER";

    public static final java.lang.String enter_tag = (daikon.FileIO.ppt_tag_separator) + (daikon.FileIO.enter_suffix);

    public static final java.lang.String exit_suffix = "EXIT";

    public static final java.lang.String exit_tag = (daikon.FileIO.ppt_tag_separator) + (daikon.FileIO.exit_suffix);

    public static final java.lang.String throws_suffix = "THROWS";

    public static final java.lang.String throws_tag = (daikon.FileIO.ppt_tag_separator) + (daikon.FileIO.throws_suffix);

    public static final java.lang.String object_suffix = "OBJECT";

    public static final java.lang.String object_tag = (daikon.FileIO.ppt_tag_separator) + (daikon.FileIO.object_suffix);

    public static final java.lang.String class_static_suffix = "CLASS";

    public static final java.lang.String class_static_tag = (daikon.FileIO.ppt_tag_separator) + (daikon.FileIO.class_static_suffix);

    public static final java.lang.String global_suffix = "GLOBAL";

    private static final java.lang.String lineSep = Global.lineSep;

    public static boolean dkconfig_ignore_missing_enter = false;

    public static boolean dkconfig_add_changed = true;

    public static int dkconfig_max_line_number = 0;

    public static boolean dkconfig_count_lines = true;

    public static boolean dkconfig_read_samples_only = false;

    public static boolean dkconfig_unmatched_procedure_entries_quiet = false;

    public static boolean dkconfig_verbose_unmatched_procedure_entries = false;

    public static boolean dkconfig_continue_after_file_exception = false;

    public static long dkconfig_dtrace_line_count = 0;

    public static boolean new_decl_format = true;

    static java.util.HashMap<daikon.PptTopLevel, java.lang.String[]> ppt_to_value_reps = new java.util.HashMap<daikon.PptTopLevel, java.lang.String[]>();

    private static boolean to_write_nonce = false;

    private static java.lang.String nonce_value;

    private static java.lang.String nonce_string;

    public static int omitted_declarations = 0;

    public static boolean debug_missing = false;

    public static final java.util.logging.Logger debugRead = java.util.logging.Logger.getLogger("daikon.FileIO.read");

    public static final java.util.logging.Logger debugPrint = java.util.logging.Logger.getLogger("daikon.FileIO.printDtrace");

    public static final java.util.logging.Logger debugVars = java.util.logging.Logger.getLogger("daikon.FileIO.vars");

    public static final daikon.SimpleLog debug_decl = new daikon.SimpleLog(false);

    public static class DeclError extends java.io.IOException {
        static final long serialVersionUID = 20060518L;

        public DeclError(java.lang.String msg) {
            super(msg);
        }

        public static daikon.FileIO.DeclError detail(daikon.FileIO.ParseState state, java.lang.String format, java.lang.Object... args) {
            java.lang.String msg = (java.lang.String.format(format, args)) + (java.lang.String.format(" at line %d in file %s", state.reader.getLineNumber(), state.filename));
            return new daikon.FileIO.DeclError(msg);
        }
    }

    static final class ParentRelation implements java.io.Serializable {
        static final long serialVersionUID = 20060622L;

        daikon.PptRelation.PptRelationType rel_type;

        java.lang.String parent_ppt_name;

        int id;

        public java.lang.String toString() {
            return ((((parent_ppt_name) + "[") + (id)) + "] ") + (rel_type);
        }
    }

    public static final boolean isComment(java.lang.String s) {
        return (s.startsWith("//")) || (s.startsWith("#"));
    }

    public static daikon.PptMap read_declaration_files(java.util.Collection<java.io.File> files) throws java.io.IOException {
        daikon.PptMap all_ppts = new daikon.PptMap();
        for (java.io.File file : files) {
            daikon.Daikon.progress = "Reading " + file;
            if (!(daikon.Daikon.dkconfig_quiet)) {
                java.lang.System.out.print(".");
            }
            daikon.FileIO.read_declaration_file(file, all_ppts);
        }
        return all_ppts;
    }

    public static void read_declaration_file(java.io.File filename, daikon.PptMap all_ppts) throws java.io.IOException {
        if (daikon.Daikon.using_DaikonSimple) {
            daikon.FileIO.Processor processor = new daikon.DaikonSimple.SimpleProcessor();
            daikon.FileIO.read_data_trace_file(filename.toString(), all_ppts, processor, true, false);
        }else {
            daikon.FileIO.Processor processor = new daikon.FileIO.Processor();
            daikon.FileIO.read_data_trace_file(filename.toString(), all_ppts, processor, true, true);
        }
    }

    private static daikon.PptTopLevel read_ppt_decl(daikon.FileIO.ParseState state, java.lang.String top_line) throws java.io.IOException {
        java.lang.String line = top_line;
        java.util.Scanner scanner = new java.util.Scanner(line);
        java.lang.String record_name = daikon.FileIO.need(state, scanner, "'ppt'");
        if (record_name != "ppt") {
            daikon.FileIO.decl_error(state, "found '%s' where 'ppt' expected", record_name);
        }
        java.lang.String ppt_name = daikon.FileIO.need(state, scanner, "ppt name");
        if (state.all_ppts.containsName(ppt_name)) {
            if (state.ppts_are_new) {
                daikon.FileIO.decl_error(state, "Duplicate declaration of ppt '%s'", ppt_name);
            }else {
                daikon.FileIO.skip_decl(state.reader);
                return state.all_ppts.get(ppt_name);
            }
        }
        java.util.Map<java.lang.String, daikon.FileIO.VarDefinition> varmap = new java.util.LinkedHashMap<java.lang.String, daikon.FileIO.VarDefinition>();
        daikon.FileIO.VarDefinition vardef = null;
        java.util.List<daikon.FileIO.ParentRelation> ppt_parents = new java.util.ArrayList<daikon.FileIO.ParentRelation>();
        java.util.EnumSet<daikon.PptTopLevel.PptFlags> ppt_flags = java.util.EnumSet.noneOf(daikon.PptTopLevel.PptFlags.class);
        daikon.PptTopLevel.PptType ppt_type = daikon.PptTopLevel.PptType.POINT;
        while ((line = state.reader.readLine()) != null) {
            daikon.FileIO.debug_decl.log("read line %s%n", line);
            line = line.trim();
            if ((line.length()) == 0)
                break;

            scanner = new java.util.Scanner(line);
            java.lang.String record = scanner.next().intern();
            if (vardef == null) {
                if (record == "parent") {
                    ppt_parents.add(daikon.FileIO.parse_ppt_parent(state, scanner));
                }else
                    if (record == "flags") {
                        daikon.FileIO.parse_ppt_flags(state, scanner, ppt_flags);
                    }else
                        if (record == "variable") {
                            vardef = new daikon.FileIO.VarDefinition(state, scanner);
                            if (daikon.FileIO.var_included(vardef.name))
                                varmap.put(vardef.name, vardef);

                        }else
                            if (record == "ppt-type") {
                                ppt_type = daikon.FileIO.parse_ppt_type(state, scanner);
                            }else {
                                daikon.FileIO.decl_error(state, "record '%s' found where %s expected", record, "'parent' or 'flags'");
                            }



            }else {
                if (record == "var-kind") {
                    vardef.parse_var_kind(scanner);
                }else
                    if (record == "enclosing-var") {
                        vardef.parse_enclosing_var(scanner);
                    }else
                        if (record == "reference-type") {
                            vardef.parse_reference_type(scanner);
                        }else
                            if (record == "array") {
                                vardef.parse_array(scanner);
                            }else
                                if (record == "rep-type") {
                                    vardef.parse_rep_type(scanner);
                                }else
                                    if (record == "dec-type") {
                                        vardef.parse_dec_type(scanner);
                                    }else
                                        if (record == "flags") {
                                            vardef.parse_flags(scanner);
                                        }else
                                            if (record == "lang-flags") {
                                                vardef.parse_lang_flags(scanner);
                                            }else
                                                if (record == "parent") {
                                                    vardef.parse_parent(scanner, ppt_parents);
                                                }else
                                                    if (record == "comparability") {
                                                        vardef.parse_comparability(scanner);
                                                    }else
                                                        if (record == "constant") {
                                                            vardef.parse_constant(scanner);
                                                        }else
                                                            if (record == "variable") {
                                                                vardef = new daikon.FileIO.VarDefinition(state, scanner);
                                                                if (varmap.containsKey(vardef.name))
                                                                    daikon.FileIO.decl_error(state, "var %s declared twice", vardef.name);

                                                                if (daikon.FileIO.var_included(vardef.name))
                                                                    varmap.put(vardef.name, vardef);

                                                            }else {
                                                                daikon.FileIO.decl_error(state, "Unexpected variable item '%s' found", record);
                                                            }











            }
        } 
        if (!(daikon.FileIO.ppt_included(ppt_name))) {
            (daikon.FileIO.omitted_declarations)++;
            return null;
        }
        daikon.VarInfo[] vi_array = new daikon.VarInfo[varmap.size()];
        int ii = 0;
        for (daikon.FileIO.VarDefinition vd : varmap.values()) {
            vi_array[(ii++)] = new daikon.VarInfo(vd);
        }
        daikon.PptTopLevel newppt = new daikon.PptTopLevel(ppt_name, ppt_type, ppt_parents, ppt_flags, vi_array);
        return newppt;
    }

    private static daikon.FileIO.ParentRelation parse_ppt_parent(daikon.FileIO.ParseState state, java.util.Scanner scanner) throws daikon.FileIO.DeclError {
        daikon.FileIO.ParentRelation pr = new daikon.FileIO.ParentRelation();
        pr.rel_type = daikon.FileIO.parse_enum_val(state, scanner, daikon.PptRelation.PptRelationType.class, "relation type");
        pr.parent_ppt_name = daikon.FileIO.need(state, scanner, "ppt name");
        pr.id = java.lang.Integer.parseInt(daikon.FileIO.need(state, scanner, "relation id"));
        daikon.FileIO.need_eol(state, scanner);
        return pr;
    }

    private static void parse_ppt_flags(daikon.FileIO.ParseState state, java.util.Scanner scanner, java.util.EnumSet<daikon.PptTopLevel.PptFlags> flags) throws daikon.FileIO.DeclError {
        flags.add(daikon.FileIO.parse_enum_val(state, scanner, daikon.PptTopLevel.PptFlags.class, "ppt flags"));
        while (scanner.hasNext())
            flags.add(daikon.FileIO.parse_enum_val(state, scanner, daikon.PptTopLevel.PptFlags.class, "ppt flags"));

    }

    private static daikon.PptTopLevel.PptType parse_ppt_type(daikon.FileIO.ParseState state, java.util.Scanner scanner) throws daikon.FileIO.DeclError {
        daikon.PptTopLevel.PptType ppt_type = daikon.FileIO.parse_enum_val(state, scanner, daikon.PptTopLevel.PptType.class, "ppt type");
        daikon.FileIO.need_eol(state, scanner);
        return ppt_type;
    }

    private static daikon.PptTopLevel read_declaration(daikon.FileIO.ParseState state) throws java.io.IOException {
        java.lang.String ppt_name = state.reader.readLine();
        if (ppt_name == null) {
            throw new daikon.Daikon.TerminationMessage("File ends with \"DECLARE\" with no following program point name", state.reader, state.filename);
        }
        ppt_name = ppt_name.intern();
        daikon.VarInfo[] vi_array = daikon.FileIO.read_VarInfos(state, ppt_name);
        if (state.all_ppts.containsName(ppt_name)) {
            if (state.ppts_are_new) {
                daikon.PptTopLevel existing_ppt = state.all_ppts.get(ppt_name);
                daikon.VarInfo[] existing_vars = existing_ppt.var_infos;
                if ((existing_ppt.num_declvars) != (vi_array.length)) {
                    throw new daikon.Daikon.TerminationMessage(((((("Duplicate declaration of program point \"" + ppt_name) + "\" with a different number of VarInfo objects: old VarInfo number=") + (existing_ppt.num_declvars)) + ", new VarInfo number=") + (vi_array.length)), state.reader, state.filename);
                }
                for (int i = 0; i < (vi_array.length); i++) {
                    java.lang.String oldName = existing_vars[i].str_name();
                    java.lang.String newName = vi_array[i].str_name();
                    if (!(oldName.equals(newName))) {
                        throw new daikon.Daikon.TerminationMessage(((((("Duplicate declaration of program point \"" + ppt_name) + "\" with two different VarInfo: old VarInfo=") + oldName) + ", new VarInfo=") + newName), state.reader, state.filename);
                    }
                }
            }else {
                return state.all_ppts.get(ppt_name);
            }
        }
        if (!(daikon.FileIO.ppt_included(ppt_name))) {
            (daikon.FileIO.omitted_declarations)++;
            return null;
        }
        daikon.PptTopLevel newppt = new daikon.PptTopLevel(ppt_name, vi_array);
        return newppt;
    }

    private static daikon.VarInfo[] read_VarInfos(daikon.FileIO.ParseState state, java.lang.String ppt_name) throws java.io.IOException {
        java.util.List<daikon.VarInfo> var_infos = new java.util.ArrayList<daikon.VarInfo>();
        daikon.VarInfo vi;
        while ((vi = daikon.FileIO.read_VarInfo(state, ppt_name)) != null) {
            for (daikon.VarInfo vi2 : var_infos) {
                if ((vi.name()) == (vi2.name())) {
                    throw new daikon.Daikon.TerminationMessage(("Duplicate variable name " + (vi.name())), state.reader, state.filename);
                }
            }
            if (!(daikon.FileIO.var_included(vi.name()))) {
                continue;
            }
            var_infos.add(vi);
        } 
        return var_infos.toArray(new daikon.VarInfo[var_infos.size()]);
    }

    private static boolean seen_string_rep_type = false;

    private static daikon.VarInfo read_VarInfo(daikon.FileIO.ParseState state, java.lang.String ppt_name) throws java.io.IOException {
        java.io.LineNumberReader file = state.reader;
        int varcomp_format = state.varcomp_format;
        java.io.File filename = state.file;
        java.lang.String line = file.readLine();
        if ((line == null) || (line.equals("")))
            return null;

        java.lang.String varname = line;
        java.lang.String proglang_type_string_and_aux = file.readLine();
        java.lang.String file_rep_type_string = file.readLine();
        java.lang.String comparability_string = file.readLine();
        if (((proglang_type_string_and_aux == null) || (file_rep_type_string == null)) || (comparability_string == null))
            throw new daikon.Daikon.TerminationMessage(((((("End of file " + filename) + " while reading variable ") + varname) + " in declaration of program point ") + ppt_name));

        int equals_index = file_rep_type_string.indexOf(" = ");
        java.lang.String static_constant_value_string = null;
        java.lang.Object static_constant_value = null;
        boolean is_static_constant = false;
        if (equals_index != (-1)) {
            is_static_constant = true;
            static_constant_value_string = file_rep_type_string.substring((equals_index + 3));
            file_rep_type_string = file_rep_type_string.substring(0, equals_index);
        }
        if ("String".equals(file_rep_type_string)) {
            file_rep_type_string = "java.lang.String";
            if (!(daikon.FileIO.seen_string_rep_type)) {
                daikon.FileIO.seen_string_rep_type = true;
                java.lang.System.err.println((((("Warning: Malformed trace file.  Representation type 'String' should be " + "'java.lang.String' instead on line ") + ((file.getLineNumber()) - 1)) + " of ") + filename));
            }
        }else
            if ("String[]".equals(file_rep_type_string)) {
                throw new daikon.Daikon.TerminationMessage((("Representation type 'String[]' should be " + "'java.lang.String[]' instead for variable ") + varname), file, filename);
            }

        int hash_position = proglang_type_string_and_aux.indexOf('#');
        java.lang.String aux_string = "";
        if (hash_position == (-1)) {
            hash_position = proglang_type_string_and_aux.length();
        }else {
            aux_string = proglang_type_string_and_aux.substring((hash_position + 1), proglang_type_string_and_aux.length());
        }
        java.lang.String proglang_type_string = proglang_type_string_and_aux.substring(0, hash_position).trim();
        daikon.ProglangType prog_type;
        daikon.ProglangType file_rep_type;
        daikon.ProglangType rep_type;
        daikon.VarInfoAux aux;
        try {
            prog_type = daikon.ProglangType.parse(proglang_type_string);
            file_rep_type = daikon.ProglangType.rep_parse(file_rep_type_string);
            rep_type = file_rep_type.fileTypeToRepType();
            aux = daikon.VarInfoAux.parse(aux_string);
        } catch (java.io.IOException e) {
            throw new daikon.Daikon.TerminationMessage(file, filename, e);
        }
        if (static_constant_value_string != null) {
            static_constant_value = rep_type.parse_value(static_constant_value_string);
            daikon.Assert.assertTrue((static_constant_value != null));
        }
        daikon.VarComparability comparability = null;
        try {
            comparability = daikon.VarComparability.parse(varcomp_format, comparability_string, prog_type);
        } catch (java.lang.Exception e) {
            throw new daikon.Daikon.TerminationMessage(java.lang.String.format(("Error parsing comparability (%s) at line %d " + "in file %s"), e, file.getLineNumber(), filename));
        }
        if (!(daikon.VarInfo.legalFileRepType(file_rep_type))) {
            throw new daikon.Daikon.TerminationMessage((((((("Unsupported representation type " + (file_rep_type.format())) + " (parsed as ") + rep_type) + ")") + " for variable ") + varname), file, filename);
        }
        if (!(daikon.VarInfo.legalRepType(rep_type))) {
            throw new daikon.Daikon.TerminationMessage(((("Unsupported (converted) representation type " + (file_rep_type.format())) + " for variable ") + varname), file, filename);
        }
        return new daikon.VarInfo(varname, prog_type, file_rep_type, comparability, is_static_constant, static_constant_value, aux);
    }

    private static int read_var_comparability(daikon.FileIO.ParseState state, java.lang.String line) throws java.io.IOException {
        java.lang.String comp_str = null;
        if (daikon.FileIO.new_decl_format) {
            java.util.Scanner scanner = new java.util.Scanner(line);
            scanner.next();
            comp_str = daikon.FileIO.need(state, scanner, "comparability");
            daikon.FileIO.need_eol(state, scanner);
        }else {
            comp_str = state.reader.readLine();
            if (comp_str == null) {
                throw new daikon.Daikon.TerminationMessage("Found end of file, expected comparability", state.reader, state.filename);
            }
        }
        if (comp_str.equals("none")) {
            return VarComparability.NONE;
        }else
            if (comp_str.equals("implicit")) {
                return VarComparability.IMPLICIT;
            }else {
                throw new daikon.Daikon.TerminationMessage((("Unrecognized VarComparability '" + comp_str) + "'"), state.reader, state.filename);
            }

    }

    private static java.lang.String read_input_language(daikon.FileIO.ParseState state, java.lang.String line) throws java.io.IOException {
        java.util.Scanner scanner = new java.util.Scanner(line);
        scanner.next();
        java.lang.String input_lang = daikon.FileIO.need(state, scanner, "input language");
        daikon.FileIO.need_eol(state, scanner);
        return input_lang;
    }

    private static void read_decl_version(daikon.FileIO.ParseState state, java.lang.String line) throws java.io.IOException {
        java.util.Scanner scanner = new java.util.Scanner(line);
        scanner.next();
        java.lang.String version = daikon.FileIO.need(state, scanner, "declaration version number");
        daikon.FileIO.need_eol(state, scanner);
        if (version == "2.0")
            daikon.FileIO.new_decl_format = true;
        else
            if (version == "1.0")
                daikon.FileIO.new_decl_format = false;
            else
                daikon.FileIO.decl_error(state, "'%s' found where 1.0 or 2.0 expected", version);


    }

    private static void read_list_implementors(java.io.LineNumberReader reader, java.io.File filename) throws java.io.IOException {
        for (; ;) {
            java.lang.String line = reader.readLine();
            if ((line == null) || (line.equals("")))
                break;

            if (daikon.FileIO.isComment(line))
                continue;

            ProglangType.list_implementors.add(line.intern());
        }
    }

    static final class Invocation implements java.lang.Comparable<daikon.FileIO.Invocation> {
        daikon.PptTopLevel ppt;

        java.lang.Object[] vals;

        int[] mods;

        static java.lang.Object canonical_hashcode = new java.lang.Object();

        Invocation(daikon.PptTopLevel ppt, java.lang.Object[] vals, int[] mods) {
            this.ppt = ppt;
            this.vals = vals;
            this.mods = mods;
        }

        java.lang.String format() {
            return format(true);
        }

        java.lang.String format(boolean show_values) {
            if (!show_values) {
                return "  " + (ppt.ppt_name.getNameWithoutPoint());
            }
            java.io.StringWriter sw = new java.io.StringWriter();
            java.io.PrintWriter pw = new java.io.PrintWriter(sw);
            pw.println(("  " + (ppt.ppt_name.getNameWithoutPoint())));
            pw.print("    ");
            for (int j = 0; j < (vals.length); j++) {
                if (j != 0)
                    pw.print(", ");

                pw.print(((ppt.var_infos[j].name()) + "="));
                java.lang.Object val = vals[j];
                if (val == (daikon.FileIO.Invocation.canonical_hashcode))
                    pw.print("<hashcode>");
                else
                    if (val instanceof int[])
                        pw.print(daikon.ArraysMDE.toString(((int[]) (val))));
                    else
                        if (val instanceof java.lang.String)
                            pw.print((val == null ? "null" : daikon.UtilMDE.escapeNonASCII(((java.lang.String) (val)))));
                        else
                            pw.print(val);



            }
            pw.println();
            return sw.toString();
        }

        public daikon.FileIO.Invocation canonicalize() {
            java.lang.Object[] new_vals = new java.lang.Object[vals.length];
            java.lang.System.arraycopy(vals, 0, new_vals, 0, vals.length);
            daikon.VarInfo[] vis = ppt.var_infos;
            for (daikon.VarInfo vi : vis) {
                if (((vi.value_index) != (-1)) && ((vi.file_rep_type) == (ProglangType.HASHCODE))) {
                    new_vals[vi.value_index] = daikon.FileIO.Invocation.canonical_hashcode;
                }
            }
            return new daikon.FileIO.Invocation(ppt, new_vals, mods);
        }

        public boolean equals(java.lang.Object other) {
            if (other instanceof daikon.FileIO.Invocation)
                return this.format().equals(((daikon.FileIO.Invocation) (other)).format());
            else
                return false;

        }

        public int compareTo(daikon.FileIO.Invocation other) {
            return ppt.name().compareTo(other.ppt.name());
        }

        public int hashCode() {
            return this.format().hashCode();
        }
    }

    static java.util.Stack<daikon.FileIO.Invocation> call_stack = new java.util.Stack<daikon.FileIO.Invocation>();

    static java.util.HashMap<java.lang.Integer, daikon.FileIO.Invocation> call_hashmap = new java.util.HashMap<java.lang.Integer, daikon.FileIO.Invocation>();

    public static void read_data_trace_files(java.util.Collection<java.lang.String> files, daikon.PptMap all_ppts) throws java.io.IOException {
        daikon.FileIO.Processor processor = new daikon.FileIO.Processor();
        daikon.FileIO.read_data_trace_files(files, all_ppts, processor, true);
    }

    public static void read_data_trace_files(java.util.Collection<java.lang.String> files, daikon.PptMap all_ppts, daikon.FileIO.Processor processor, boolean ppts_are_new) throws java.io.IOException {
        for (java.lang.String filename : files) {
            try {
                daikon.FileIO.read_data_trace_file(filename, all_ppts, processor, false, ppts_are_new);
            } catch (java.io.IOException e) {
                if (e.getMessage().equals("Corrupt GZIP trailer")) {
                    java.lang.System.out.println(((filename + " has a corrupt gzip trailer.  ") + "All possible data was recovered."));
                }else {
                    throw e;
                }
            }
        }
        if ((daikon.Daikon.server_dir) != null) {
            while (true) {
                java.lang.String[] dir_files = daikon.Daikon.server_dir.list();
                java.util.Arrays.sort(dir_files);
                boolean hasEnd = false;
                for (java.lang.String f : dir_files) {
                    if (f.endsWith(".end"))
                        hasEnd = true;

                    if ((f.endsWith(".end")) || (f.endsWith(".start")))
                        continue;

                    if (files.contains(f))
                        continue;

                    files.add(f);
                    java.lang.System.out.println(("Reading " + f));
                    daikon.FileIO.read_data_trace_file(new java.io.File(daikon.Daikon.server_dir, f).toString(), all_ppts, processor, false, ppts_are_new);
                }
                if (hasEnd)
                    break;

                try {
                    java.lang.Thread.sleep(1000);
                } catch (java.lang.InterruptedException e) {
                }
            } 
        }
        daikon.FileIO.process_unmatched_procedure_entries();
        daikon.FileIO.warn_if_hierarchy_mismatch(all_ppts);
    }

    private static void warn_if_hierarchy_mismatch(daikon.PptMap all_ppts) {
        boolean some_program_points = false;
        boolean all_program_points = true;
        for (java.util.Iterator<daikon.PptTopLevel> all_ppts_iter = all_ppts.ppt_all_iterator(); all_ppts_iter.hasNext();) {
            daikon.PptTopLevel ppt_top_level = all_ppts_iter.next();
            boolean is_program_point = (((((ppt_top_level.ppt_name.isExitPoint()) || (ppt_top_level.ppt_name.isEnterPoint())) || (ppt_top_level.ppt_name.isThrowsPoint())) || (ppt_top_level.ppt_name.isObjectInstanceSynthetic())) || (ppt_top_level.ppt_name.isClassStaticSynthetic())) || (ppt_top_level.ppt_name.isGlobalPoint());
            all_program_points = all_program_points && is_program_point;
            some_program_points = some_program_points || is_program_point;
        }
        if (((daikon.Daikon.use_dataflow_hierarchy) && (!all_program_points)) && some_program_points) {
            java.lang.System.out.println(("Warning: Daikon is using a dataflow" + (((" hierarchy analysis on a data trace" + " that does not appear to be over a") + " program execution, consider running") + " Daikon with the --nohierarchy flag.")));
        }
    }

    public static class Processor {
        public void process_sample(daikon.PptMap all_ppts, daikon.PptTopLevel ppt, daikon.ValueTuple vt, java.lang.Integer nonce) {
            daikon.FileIO.process_sample(all_ppts, ppt, vt, nonce);
        }
    }

    static void read_data_trace_file(java.lang.String filename, daikon.PptMap all_ppts) throws java.io.IOException {
        daikon.FileIO.Processor processor = new daikon.FileIO.Processor();
        daikon.FileIO.read_data_trace_file(filename, all_ppts, processor, false, true);
    }

    public enum ParseStatus {
        NULL, DECL, SAMPLE, COMPARABILITY, LIST, EOF, ERROR, TRUNCATED;}

    public static class ParseState {
        public java.lang.String filename;

        public boolean is_decl_file;

        public boolean ppts_are_new;

        public daikon.PptMap all_ppts;

        public java.io.LineNumberReader reader;

        public java.io.File file;

        public long total_lines;

        public int varcomp_format;

        public daikon.FileIO.ParseStatus status;

        public daikon.PptTopLevel ppt;

        public java.lang.Integer nonce;

        public daikon.ValueTuple vt;

        public long lineNum;

        public ParseState(java.lang.String raw_filename, boolean decl_file_p, boolean ppts_are_new, daikon.PptMap ppts) throws java.io.IOException {
            file = new java.io.File(raw_filename);
            if (raw_filename.equals("-")) {
                filename = "standard input";
            }else
                if (raw_filename.equals("+")) {
                    filename = "chicory socket";
                }else {
                    filename = file.getName();
                }

            is_decl_file = decl_file_p;
            this.ppts_are_new = ppts_are_new;
            all_ppts = ppts;
            total_lines = 0;
            boolean count_lines = daikon.FileIO.dkconfig_count_lines;
            if (is_decl_file) {
                count_lines = false;
            }else
                if ((daikon.FileIO.dkconfig_dtrace_line_count) != 0) {
                    total_lines = daikon.FileIO.dkconfig_dtrace_line_count;
                    count_lines = false;
                }else
                    if (filename.equals("-")) {
                        count_lines = false;
                    }else
                        if ((daikon.Daikon.dkconfig_progress_delay) == (-1)) {
                            count_lines = false;
                        }else
                            if ((new java.io.File(raw_filename).length()) == 0) {
                                count_lines = false;
                            }




            if (count_lines) {
                daikon.Daikon.progress = "Checking size of " + (filename);
                total_lines = daikon.UtilMDE.count_lines(raw_filename);
            }else {
            }
            if (raw_filename.equals("-")) {
                java.io.Reader file_reader = new java.io.InputStreamReader(java.lang.System.in, "ISO-8859-1");
                reader = new java.io.LineNumberReader(file_reader);
            }else
                if (raw_filename.equals("+")) {
                    java.io.InputStream chicoryInput = daikon.FileIO.connectToChicory();
                    java.io.InputStreamReader chicReader = new java.io.InputStreamReader(chicoryInput);
                    reader = new java.io.LineNumberReader(chicReader);
                }else {
                    reader = daikon.UtilMDE.lineNumberFileReader(raw_filename);
                }

            varcomp_format = VarComparability.IMPLICIT;
            status = daikon.FileIO.ParseStatus.NULL;
            ppt = null;
        }
    }

    private static java.io.InputStream connectToChicory() {
        java.net.ServerSocket daikonServer = null;
        try {
            daikonServer = new java.net.ServerSocket(0);
            java.lang.System.out.println(("DaikonChicoryOnlinePort=" + (daikonServer.getLocalPort())));
            daikonServer.setReceiveBufferSize(64000);
        } catch (java.io.IOException e) {
            throw new java.lang.RuntimeException("Unable to create server", e);
        }
        java.net.Socket chicSocket = null;
        try {
            daikonServer.setSoTimeout(5000);
            chicSocket = daikonServer.accept();
        } catch (java.io.IOException e) {
            throw new java.lang.RuntimeException("Unable to connect to Chicory", e);
        }
        try {
            return chicSocket.getInputStream();
        } catch (java.io.IOException e) {
            throw new java.lang.RuntimeException("Unable to get Chicory's input stream", e);
        }
    }

    public static daikon.FileIO.ParseState data_trace_state = null;

    public static int samples_processed = 0;

    static void read_data_trace_file(java.lang.String filename, daikon.PptMap all_ppts, daikon.FileIO.Processor processor, boolean is_decl_file, boolean ppts_are_new) throws java.io.IOException {
        if (daikon.FileIO.debugRead.isLoggable(java.util.logging.Level.FINE)) {
            daikon.FileIO.debugRead.fine(((("read_data_trace_file " + filename) + ((daikon.Daikon.ppt_regexp) != null ? " " + (daikon.Daikon.ppt_regexp.pattern()) : "")) + ((daikon.Daikon.ppt_omit_regexp) != null ? " " + (daikon.Daikon.ppt_omit_regexp.pattern()) : "")));
        }
        daikon.FileIO.new_decl_format = false;
        daikon.FileIO.data_trace_state = new daikon.FileIO.ParseState(filename, is_decl_file, ppts_are_new, all_ppts);
        if (Global.debugPrintDtrace) {
            Global.dtraceWriter = new java.io.PrintWriter(new java.io.FileWriter(new java.io.File((filename + ".debug"))));
        }
        while (true) {
            daikon.FileIO.read_data_trace_record(daikon.FileIO.data_trace_state);
            if ((daikon.FileIO.data_trace_state.status) == (daikon.FileIO.ParseStatus.SAMPLE)) {
                (daikon.FileIO.samples_processed)++;
                try {
                    processor.process_sample(daikon.FileIO.data_trace_state.all_ppts, daikon.FileIO.data_trace_state.ppt, daikon.FileIO.data_trace_state.vt, daikon.FileIO.data_trace_state.nonce);
                } catch (java.lang.Error e) {
                    if (!(daikon.FileIO.dkconfig_continue_after_file_exception)) {
                        throw new daikon.Daikon.TerminationMessage(e, daikon.FileIO.data_trace_state.reader, daikon.FileIO.data_trace_state.filename);
                    }else {
                        java.lang.System.out.println();
                        java.lang.System.out.println(("WARNING: Error while processing " + "trace file - record ignored"));
                        java.lang.System.out.print("Ignored backtrace:");
                        e.printStackTrace(java.lang.System.out);
                        java.lang.System.out.println();
                    }
                }
            }else
                if (((daikon.FileIO.data_trace_state.status) == (daikon.FileIO.ParseStatus.EOF)) || ((daikon.FileIO.data_trace_state.status) == (daikon.FileIO.ParseStatus.TRUNCATED))) {
                    break;
                }

        } 
        if (Global.debugPrintDtrace) {
            Global.dtraceWriter.close();
        }
        daikon.Daikon.progress = "Finished reading " + (daikon.FileIO.data_trace_state.filename);
        daikon.FileIO.data_trace_state = null;
    }

    public static void read_data_trace_record(daikon.FileIO.ParseState state) throws java.io.IOException {
        java.io.LineNumberReader reader = state.reader;
        for (java.lang.String line_ = reader.readLine(); line_ != null; line_ = reader.readLine()) {
            if ((line_.equals("")) || (daikon.FileIO.isComment(line_))) {
                continue;
            }
            state.lineNum = reader.getLineNumber();
            if (((daikon.FileIO.dkconfig_max_line_number) > 0) && ((state.lineNum) > (daikon.FileIO.dkconfig_max_line_number))) {
                state.status = daikon.FileIO.ParseStatus.TRUNCATED;
                return;
            }
            java.lang.String line = line_.intern();
            if (daikon.FileIO.is_declaration_header(line)) {
                if (daikon.FileIO.new_decl_format)
                    state.ppt = daikon.FileIO.read_ppt_decl(state, line);
                else
                    state.ppt = daikon.FileIO.read_declaration(state);

                if ((state.ppt) != null) {
                    if (!(state.all_ppts.containsName(state.ppt.name()))) {
                        state.all_ppts.add(state.ppt);
                        daikon.Daikon.init_ppt(state.ppt, state.all_ppts);
                    }
                }
                state.status = daikon.FileIO.ParseStatus.DECL;
                return;
            }
            if ((line.equals("VarComparability")) || (line.startsWith("var-comparability"))) {
                state.varcomp_format = daikon.FileIO.read_var_comparability(state, line);
                state.status = daikon.FileIO.ParseStatus.COMPARABILITY;
                return;
            }
            if (line.startsWith("input-language")) {
                java.lang.String input_language = daikon.FileIO.read_input_language(state, line);
                return;
            }
            if (line.startsWith("decl-version")) {
                daikon.FileIO.read_decl_version(state, line);
                return;
            }
            if (line.equals("ListImplementors")) {
                daikon.FileIO.read_list_implementors(reader, state.file);
                state.status = daikon.FileIO.ParseStatus.LIST;
                return;
            }
            java.lang.String ppt_name = line;
            if (daikon.FileIO.new_decl_format)
                ppt_name = daikon.FileIO.unescape_decl(line).intern();

            if (!(daikon.FileIO.ppt_included(ppt_name))) {
                while ((line != null) && (!(line.equals(""))))
                    line = reader.readLine();

                continue;
            }
            if (state.is_decl_file) {
                if (daikon.FileIO.debugRead.isLoggable(java.util.logging.Level.FINE))
                    daikon.FileIO.debugRead.fine(((((("Skipping paragraph starting at line " + (reader.getLineNumber())) + " of file ") + (state.filename)) + ": ") + line));

                while (((line != null) && (!(line.equals("")))) && (!(daikon.FileIO.isComment(line)))) {
                    java.lang.System.out.println((("Unrecognized paragraph contains line = `" + line) + "'"));
                    java.lang.System.out.println((((((" line: null=" + false) + " empty=") + (line.equals(""))) + " comment=") + (daikon.FileIO.isComment(line))));
                    line = reader.readLine();
                } 
                continue;
            }
            try {
                new daikon.PptName(ppt_name);
            } catch (java.lang.Throwable t) {
                if (t instanceof daikon.Daikon.TerminationMessage)
                    throw new daikon.Daikon.TerminationMessage("%s: in %s line %d", t.getMessage(), state.filename, reader.getLineNumber());
                else
                    throw new daikon.Daikon.TerminationMessage(java.lang.String.format("Illegal program point name '%s' (%s) in %s line %d", ppt_name, t.getMessage(), state.filename, reader.getLineNumber()));

            }
            if ((state.all_ppts.size()) == 0) {
                throw new daikon.Daikon.TerminationMessage((("No declarations were provided before the first sample.  Perhaps you did not supply the proper .decls file to Daikon.  (Or, there could be a bug in the front end that created the .dtrace file " + (state.filename)) + ".)"));
            }
            daikon.PptTopLevel ppt = state.all_ppts.get(ppt_name);
            if (ppt == null) {
                throw new daikon.Daikon.TerminationMessage(((((("No declaration was provided for program point " + ppt_name) + " which appears in dtrace file ") + (state.filename)) + " at line ") + (reader.getLineNumber())));
            }
            daikon.VarInfo[] vis = ppt.var_infos;
            int num_tracevars = ppt.num_tracevars;
            int vals_array_size = (ppt.var_infos.length) - (ppt.num_static_constant_vars);
            java.lang.Integer nonce = null;
            reader.mark(100);
            java.lang.String nonce_name_maybe;
            try {
                nonce_name_maybe = reader.readLine();
            } catch (java.lang.Exception e) {
                nonce_name_maybe = null;
            }
            reader.reset();
            if ("this_invocation_nonce".equals(nonce_name_maybe)) {
                java.lang.String nonce_name = reader.readLine();
                daikon.Assert.assertTrue(((nonce_name != null) && (nonce_name.equals("this_invocation_nonce"))));
                java.lang.String nonce_number = reader.readLine();
                if (nonce_number == null) {
                    throw new daikon.Daikon.TerminationMessage("File ended while trying to read nonce", reader, state.file);
                }
                nonce = new java.lang.Integer(nonce_number);
                if (Global.debugPrintDtrace) {
                    daikon.FileIO.to_write_nonce = true;
                    daikon.FileIO.nonce_value = nonce.toString();
                    daikon.FileIO.nonce_string = nonce_name_maybe;
                }
            }
            java.lang.Object[] vals = new java.lang.Object[vals_array_size];
            int[] mods = new int[vals_array_size];
            try {
                daikon.FileIO.read_vals_and_mods_from_trace_file(reader, state.filename, ppt, vals, mods);
            } catch (java.io.IOException e) {
                java.lang.String nextLine = reader.readLine();
                if ((e instanceof java.io.EOFException) || (nextLine == null)) {
                    java.lang.System.out.println();
                    java.lang.System.out.println(("WARNING: Unexpected EOF while processing " + "trace file - last record of trace file ignored"));
                    state.status = daikon.FileIO.ParseStatus.EOF;
                    return;
                }else
                    if (daikon.FileIO.dkconfig_continue_after_file_exception) {
                        java.lang.System.out.println();
                        java.lang.System.out.println(("WARNING: IOException while processing " + "trace file - record ignored"));
                        java.lang.System.out.print("Ignored backtrace:");
                        e.printStackTrace(java.lang.System.out);
                        java.lang.System.out.println();
                        while ((nextLine != null) && (!(nextLine.equals("")))) {
                            nextLine = reader.readLine();
                        } 
                        continue;
                    }else {
                        throw e;
                    }

            }
            state.ppt = ppt;
            state.nonce = nonce;
            state.vt = daikon.ValueTuple.makeUninterned(vals, mods);
            state.status = daikon.FileIO.ParseStatus.SAMPLE;
            return;
        }
        state.status = daikon.FileIO.ParseStatus.EOF;
        return;
    }

    public static void process_sample(daikon.PptMap all_ppts, daikon.PptTopLevel ppt, daikon.ValueTuple vt, java.lang.Integer nonce) {
        boolean ignore = daikon.FileIO.add_orig_variables(ppt, vt.vals, vt.mods, nonce);
        if (ignore)
            return;

        if (daikon.Daikon.use_dataflow_hierarchy) {
            if (((((ppt.ppt_name.isEnterPoint()) || (ppt.ppt_name.isThrowsPoint())) || (ppt.ppt_name.isObjectInstanceSynthetic())) || (ppt.ppt_name.isClassStaticSynthetic())) || (ppt.ppt_name.isGlobalPoint())) {
                return;
            }
            if ((ppt.ppt_name.isExitPoint()) && (ppt.ppt_name.isCombinedExitPoint())) {
                throw new java.lang.RuntimeException((("Bad program point name " + (ppt.name)) + " is a combined exit point name"));
            }
        }
        daikon.FileIO.add_derived_variables(ppt, vt.vals, vt.mods);
        vt = new daikon.ValueTuple(vt.vals, vt.mods);
        if (daikon.FileIO.debugRead.isLoggable(java.util.logging.Level.FINE)) {
            daikon.FileIO.debugRead.fine(("Adding ValueTuple to " + (ppt.name())));
            daikon.FileIO.debugRead.fine(("  length is " + (vt.vals.length)));
        }
        if (daikon.FileIO.dkconfig_read_samples_only) {
            return;
        }
        ppt.add_bottom_up(vt, 1);
        if (daikon.FileIO.debugVars.isLoggable(java.util.logging.Level.FINE))
            daikon.FileIO.debugVars.fine((((ppt.name()) + " vars: ") + (daikon.Debug.int_vars(ppt, vt))));

        if (Global.debugPrintDtrace) {
            Global.dtraceWriter.close();
        }
    }

    static boolean has_unmatched_procedure_entry(daikon.PptTopLevel ppt) {
        for (daikon.FileIO.Invocation invok : daikon.FileIO.call_hashmap.values()) {
            if ((invok.ppt) == ppt) {
                return true;
            }
        }
        for (daikon.FileIO.Invocation invok : daikon.FileIO.call_stack) {
            if ((invok.ppt) == ppt) {
                return true;
            }
        }
        return false;
    }

    public static void process_unmatched_procedure_entries() {
        if (daikon.FileIO.dkconfig_unmatched_procedure_entries_quiet)
            return;

        int unmatched_count = (daikon.FileIO.call_stack.size()) + (daikon.FileIO.call_hashmap.size());
        if ((!(daikon.FileIO.call_stack.empty())) || (!(daikon.FileIO.call_hashmap.isEmpty()))) {
            java.lang.System.out.println();
            java.lang.System.out.print((("No return from procedure observed " + (daikon.UtilMDE.nplural(unmatched_count, "time"))) + "."));
            if (daikon.Daikon.use_dataflow_hierarchy) {
                java.lang.System.out.print("  Unmatched entries are ignored!");
            }
            java.lang.System.out.println();
            if (!(daikon.FileIO.call_hashmap.isEmpty())) {
                java.lang.System.out.println("Unterminated calls:");
                if (daikon.FileIO.dkconfig_verbose_unmatched_procedure_entries) {
                    java.util.TreeSet<java.lang.Integer> keys = new java.util.TreeSet<java.lang.Integer>(daikon.FileIO.call_hashmap.keySet());
                    java.util.ArrayList<daikon.FileIO.Invocation> invocations = new java.util.ArrayList<daikon.FileIO.Invocation>();
                    for (java.lang.Integer i : keys) {
                        invocations.add(daikon.FileIO.call_hashmap.get(i));
                    }
                    daikon.FileIO.print_invocations_verbose(invocations);
                }else {
                    daikon.FileIO.print_invocations_grouped(daikon.FileIO.call_hashmap.values());
                }
            }
            if (!(daikon.FileIO.call_stack.empty())) {
                if (daikon.FileIO.dkconfig_verbose_unmatched_procedure_entries) {
                    java.lang.System.out.println((("Remaining " + (daikon.UtilMDE.nplural(unmatched_count, "stack"))) + " call summarized below."));
                    daikon.FileIO.print_invocations_verbose(daikon.FileIO.call_stack);
                }else {
                    daikon.FileIO.print_invocations_grouped(daikon.FileIO.call_stack);
                }
            }
            java.lang.System.out.print("End of report for procedures not returned from.");
            if (daikon.Daikon.use_dataflow_hierarchy) {
                java.lang.System.out.print("  Unmatched entries are ignored!");
            }
            java.lang.System.out.println();
        }
    }

    static void print_invocations_verbose(java.util.Collection<daikon.FileIO.Invocation> invocations) {
        for (daikon.FileIO.Invocation invok : invocations) {
            java.lang.System.out.println(invok.format());
        }
    }

    static void print_invocations_grouped(java.util.Collection<daikon.FileIO.Invocation> invocations) {
        java.util.Map<daikon.FileIO.Invocation, java.lang.Integer> counter = new java.util.HashMap<daikon.FileIO.Invocation, java.lang.Integer>();
        for (daikon.FileIO.Invocation invok : invocations) {
            invok = invok.canonicalize();
            if (counter.containsKey(invok)) {
                java.lang.Integer oldCount = counter.get(invok);
                java.lang.Integer newCount = new java.lang.Integer(((oldCount.intValue()) + 1));
                counter.put(invok, newCount);
            }else {
                counter.put(invok, new java.lang.Integer(1));
            }
        }
        java.util.TreeSet<daikon.FileIO.Invocation> keys = new java.util.TreeSet<daikon.FileIO.Invocation>(counter.keySet());
        for (daikon.FileIO.Invocation invok : keys) {
            java.lang.Integer count = counter.get(invok);
            java.lang.System.out.println((((invok.format(false)) + " : ") + (daikon.UtilMDE.nplural(count.intValue(), "invocation"))));
        }
    }

    private static void read_vals_and_mods_from_trace_file(java.io.LineNumberReader reader, java.lang.String filename, daikon.PptTopLevel ppt, java.lang.Object[] vals, int[] mods) throws java.io.IOException {
        daikon.VarInfo[] vis = ppt.var_infos;
        int num_tracevars = ppt.num_tracevars;
        java.lang.String[] oldvalue_reps = daikon.FileIO.ppt_to_value_reps.get(ppt);
        if (oldvalue_reps == null) {
            oldvalue_reps = new java.lang.String[num_tracevars];
        }
        if (Global.debugPrintDtrace) {
            Global.dtraceWriter.println(ppt.name());
            if (daikon.FileIO.to_write_nonce) {
                Global.dtraceWriter.println(daikon.FileIO.nonce_string);
                Global.dtraceWriter.println(daikon.FileIO.nonce_value);
                daikon.FileIO.to_write_nonce = false;
            }
        }
        for (int vi_index = 0, val_index = 0; val_index < num_tracevars; vi_index++) {
            daikon.Assert.assertTrue((vi_index < (vis.length)));
            daikon.VarInfo vi = vis[vi_index];
            daikon.Assert.assertTrue(((!(vi.is_static_constant)) || ((vi.value_index) == (-1))));
            if (vi.is_static_constant)
                continue;

            daikon.Assert.assertTrue((val_index == (vi.value_index)));
            java.lang.String line = reader.readLine();
            if (line == null) {
                throw new daikon.Daikon.TerminationMessage((((((((((("Unexpected end of file at " + (daikon.FileIO.data_trace_state.filename)) + " line ") + (reader.getLineNumber())) + (daikon.FileIO.lineSep)) + "  Expected variable ") + (vi.name())) + ", got ") + "null") + " for program point ") + (ppt.name())));
            }
            while (((line != null) && (!(line.equals("")))) && (!(daikon.FileIO.var_included(line)))) {
                line = reader.readLine();
                line = reader.readLine();
                if ((line == null) || (!(((line.equals("0")) || (line.equals("1"))) || (line.equals("2"))))) {
                    throw new daikon.Daikon.TerminationMessage((("Bad modbit '" + line) + "'"), reader, daikon.FileIO.data_trace_state.filename);
                }
                line = reader.readLine();
            } 
            if (!(line.trim().equals(vi.str_name()))) {
                throw new daikon.Daikon.TerminationMessage(((((("Mismatch between .dtrace file and .decls file.  Expected variable " + (vi.name())) + ", got ") + line) + " for program point ") + (ppt.name())), reader, daikon.FileIO.data_trace_state.filename);
            }
            line = reader.readLine();
            if (line == null) {
                throw new daikon.Daikon.TerminationMessage((((((((((("Unexpected end of file at " + (daikon.FileIO.data_trace_state.filename)) + " line ") + (reader.getLineNumber())) + (daikon.FileIO.lineSep)) + "  Expected value for variable ") + (vi.name())) + ", got ") + "null") + " for program point ") + (ppt.name())));
            }
            java.lang.String value_rep = line;
            line = reader.readLine();
            if (line == null) {
                throw new daikon.Daikon.TerminationMessage((((((((((("Unexpected end of file at " + (daikon.FileIO.data_trace_state.filename)) + " line ") + (reader.getLineNumber())) + (daikon.FileIO.lineSep)) + "  Expected modbit for variable ") + (vi.name())) + ", got ") + "null") + " for program point ") + (ppt.name())));
            }
            if (!(((line.equals("0")) || (line.equals("1"))) || (line.equals("2")))) {
                throw new daikon.Daikon.TerminationMessage((("Bad modbit `" + line) + "'"), reader, daikon.FileIO.data_trace_state.filename);
            }
            int mod = daikon.ValueTuple.parseModified(line);
            daikon.Assert.assertTrue((mod != (ValueTuple.MISSING_FLOW)), "Data trace value can't be missing due to flow");
            if (mod != (ValueTuple.MISSING_NONSENSICAL)) {
                if (value_rep.equals(oldvalue_reps[val_index])) {
                    if (!(daikon.FileIO.dkconfig_add_changed)) {
                        mod = ValueTuple.UNMODIFIED;
                    }
                }else {
                    mod = ValueTuple.MODIFIED;
                }
            }
            mods[val_index] = mod;
            oldvalue_reps[val_index] = value_rep;
            if (Global.debugPrintDtrace) {
                Global.dtraceWriter.println(vi.name());
                Global.dtraceWriter.println(value_rep);
                Global.dtraceWriter.println(mod);
            }
            daikon.Debug dbg = daikon.Debug.newDebug(daikon.FileIO.class, ppt, daikon.Debug.vis(vi));
            if (dbg != null)
                dbg.log(((((("Var " + (vi.name())) + " has value ") + value_rep) + " mod ") + mod));

            if (daikon.ValueTuple.modIsMissingNonsensical(mod)) {
                if (!(((value_rep.equals("nonsensical")) || (value_rep.equals("uninit"))) || (value_rep.equals("missing")))) {
                    throw new daikon.Daikon.TerminationMessage(((((((((("Modbit indicates missing value for variable " + (vi.name())) + " with value \"") + value_rep) + "\";") + (daikon.FileIO.lineSep)) + "  text of value should be \"nonsensical\" or \"uninit\" at ") + (daikon.FileIO.data_trace_state.filename)) + " line ") + (reader.getLineNumber())));
                }else {
                    if ((daikon.FileIO.debug_missing) && (!(vi.canBeMissing))) {
                        java.lang.System.out.printf("Var %s ppt %s at line %d missing%n", vi, ppt.name(), daikon.FileIO.data_trace_state.reader.getLineNumber());
                        java.lang.System.out.printf("val_index = %d, mods[val_index] = %d%n", val_index, mods[val_index]);
                    }
                    vi.canBeMissing = true;
                }
                vals[val_index] = null;
            }else {
                try {
                    vals[val_index] = vi.rep_type.parse_value(value_rep);
                    if ((vals[val_index]) == null) {
                        mods[val_index] = ValueTuple.MISSING_NONSENSICAL;
                        if ((daikon.FileIO.debug_missing) && (!(vi.canBeMissing)))
                            java.lang.System.out.printf("Var %s ppt %s at line %d null-not missing%n", vi, ppt.name(), daikon.FileIO.data_trace_state.reader.getLineNumber());

                        vi.canBeMissing = true;
                    }
                } catch (java.lang.Exception e) {
                    throw new daikon.Daikon.TerminationMessage(((((((("Error while parsing value " + value_rep) + " for variable ") + (vi.name())) + " of type ") + (vi.rep_type)) + ": ") + (e.toString())), reader, filename);
                }
            }
            val_index++;
        }
        daikon.FileIO.ppt_to_value_reps.put(ppt, oldvalue_reps);
        if (Global.debugPrintDtrace) {
            Global.dtraceWriter.println();
        }
        java.lang.String line = reader.readLine();
        while (((line != null) && (!(line.equals("")))) && (!(daikon.FileIO.var_included(line)))) {
            line = reader.readLine();
            line = reader.readLine();
            line = reader.readLine();
        } 
        daikon.Assert.assertTrue(((line == null) || (line.equals(""))), ((("Expected blank line at line " + (reader.getLineNumber())) + ": ") + line));
    }

    public static boolean add_orig_variables(daikon.PptTopLevel ppt, java.lang.Object[] vals, int[] mods, java.lang.Integer nonce) {
        daikon.VarInfo[] vis = ppt.var_infos;
        java.lang.String fn_name = ppt.ppt_name.getNameWithoutPoint();
        java.lang.String ppt_name = ppt.name();
        if (ppt_name.endsWith(daikon.FileIO.enter_tag)) {
            daikon.FileIO.Invocation invok = new daikon.FileIO.Invocation(ppt, vals, mods);
            if (nonce == null) {
                daikon.FileIO.call_stack.push(invok);
            }else {
                daikon.FileIO.call_hashmap.put(nonce, invok);
            }
            return false;
        }
        if ((ppt.ppt_name.isExitPoint()) || (ppt.ppt_name.isThrowsPoint())) {
            daikon.FileIO.Invocation invoc;
            {
                if (nonce == null) {
                    if (daikon.FileIO.call_stack.empty()) {
                        throw new java.lang.Error(("Function exit without corresponding entry: " + (ppt.name())));
                    }
                    invoc = daikon.FileIO.call_stack.pop();
                    while ((invoc.ppt.ppt_name.getNameWithoutPoint()) != fn_name) {
                        java.lang.System.err.println((((("Exceptional exit from function " + fn_name) + ", expected to first exit from ") + (invoc.ppt.ppt_name.getNameWithoutPoint())) + ((daikon.FileIO.data_trace_state.filename) == null ? "" : (("; at " + (daikon.FileIO.data_trace_state.filename)) + " line ") + (daikon.FileIO.data_trace_state.reader.getLineNumber()))));
                        invoc = daikon.FileIO.call_stack.pop();
                    } 
                }else {
                    invoc = daikon.FileIO.call_hashmap.get(nonce);
                    if ((daikon.FileIO.dkconfig_ignore_missing_enter) && (invoc == null)) {
                        return true;
                    }else
                        if (invoc == null) {
                            throw new java.lang.Error(((((((("Didn't find call with nonce " + nonce) + " to match ") + (ppt.name())) + " ending at ") + (daikon.FileIO.data_trace_state.filename)) + " line ") + (daikon.FileIO.data_trace_state.reader.getLineNumber())));
                        }

                    invoc = daikon.FileIO.call_hashmap.get(nonce);
                    daikon.FileIO.call_hashmap.remove(nonce);
                }
            }
            daikon.Assert.assertTrue((invoc != null));
            int vi_index = 0;
            for (int val_index = 0; val_index < (ppt.num_orig_vars); val_index++) {
                daikon.VarInfo vi = vis[(((ppt.num_tracevars) + (ppt.num_static_constant_vars)) + val_index)];
                assert !(vi.is_static_constant) : "orig constant " + vi;
                while (invoc.ppt.var_infos[vi_index].is_static_constant)
                    vi_index++;

                vals[((ppt.num_tracevars) + val_index)] = invoc.vals[val_index];
                int mod = invoc.mods[val_index];
                mods[((ppt.num_tracevars) + val_index)] = mod;
                if (daikon.ValueTuple.modIsMissingNonsensical(mod)) {
                    if ((daikon.FileIO.debug_missing) && (!(vi.canBeMissing))) {
                        java.lang.System.out.printf("add_orig: var %s missing[%d/%d]%n", vi, val_index, vi_index);
                    }
                    vi.canBeMissing = true;
                    assert (invoc.vals[val_index]) == null;
                    assert (vi.name()) == (invoc.ppt.var_infos[vi_index].prestate_name()) : ((vi.name()) + " != ") + (invoc.ppt.var_infos[vi_index]);
                    assert invoc.ppt.var_infos[vi_index].canBeMissing : invoc.ppt.var_infos[vi_index];
                }
                vi_index++;
            }
        }
        return false;
    }

    public static void add_derived_variables(daikon.PptTopLevel ppt, java.lang.Object[] vals, int[] mods) {
        daikon.ValueTuple partial_vt = daikon.ValueTuple.makeUninterned(vals, mods);
        int filled_slots = ((ppt.num_orig_vars) + (ppt.num_tracevars)) + (ppt.num_static_constant_vars);
        for (int i = 0; i < filled_slots; i++) {
            daikon.Assert.assertTrue((!(ppt.var_infos[i].isDerived())));
        }
        for (int i = filled_slots; i < (ppt.var_infos.length); i++) {
            if (!(ppt.var_infos[i].isDerived())) {
                daikon.Assert.assertTrue(ppt.var_infos[i].isDerived(), ("variable not derived: " + (ppt.var_infos[i].repr())));
            }
        }
        int num_const = ppt.num_static_constant_vars;
        for (int i = filled_slots; i < (ppt.var_infos.length); i++) {
            daikon.derive.ValueAndModified vm = ppt.var_infos[i].derived.computeValueAndModified(partial_vt);
            vals[(i - num_const)] = vm.value;
            mods[(i - num_const)] = vm.modified;
        }
    }

    static final class SerialFormat implements java.io.Serializable {
        static final long serialVersionUID = 20060905L;

        public SerialFormat(daikon.PptMap map, daikon.config.Configuration config) {
            this.map = map;
            this.config = config;
            this.new_decl_format = daikon.FileIO.new_decl_format;
        }

        public daikon.PptMap map;

        public daikon.config.Configuration config;

        public boolean new_decl_format = false;
    }

    public static void write_serialized_pptmap(daikon.PptMap map, java.io.File file) throws java.io.IOException {
        daikon.FileIO.SerialFormat record = new daikon.FileIO.SerialFormat(map, daikon.config.Configuration.getInstance());
        daikon.UtilMDE.writeObject(record, file);
    }

    public static daikon.PptMap read_serialized_pptmap(java.io.File file, boolean use_saved_config) throws java.io.IOException {
        try {
            java.lang.Object obj = daikon.UtilMDE.readObject(file);
            if (obj instanceof daikon.FileIO.SerialFormat) {
                daikon.FileIO.SerialFormat record = ((daikon.FileIO.SerialFormat) (obj));
                if (use_saved_config) {
                    daikon.config.Configuration.getInstance().overlap(record.config);
                }
                daikon.FileIO.new_decl_format = record.new_decl_format;
                return record.map;
            }else
                if (obj instanceof daikon.diff.InvMap) {
                    daikon.diff.InvMap invs = ((daikon.diff.InvMap) (obj));
                    daikon.PptMap ppts = new daikon.PptMap();
                    for (java.util.Iterator<daikon.PptTopLevel> i = invs.pptIterator(); i.hasNext();) {
                        daikon.PptTopLevel ppt = i.next();
                        daikon.PptTopLevel nppt = new daikon.PptTopLevel(ppt.name, ppt.var_infos);
                        nppt.set_sample_number(ppt.num_samples());
                        ppts.add(nppt);
                        java.util.List<daikon.inv.Invariant> inv_list = invs.get(ppt);
                        for (daikon.inv.Invariant inv : inv_list) {
                            daikon.PptSlice slice = nppt.get_or_instantiate_slice(inv.ppt.var_infos);
                            inv.ppt = slice;
                            slice.addInvariant(inv);
                        }
                    }
                    return ppts;
                }else {
                    throw new java.io.IOException(("Unexpected serialized file type: " + (obj.getClass())));
                }

        } catch (java.lang.ClassNotFoundException e) {
            throw ((java.io.IOException) (new java.io.IOException("Error while loading inv file").initCause(e)));
        } catch (java.io.InvalidClassException e) {
            throw new java.io.IOException("It is likely that the .inv file format has changed, because a Daikon data structure has been modified, so your old .inv file is no longer readable by Daikon.  Please regenerate your .inv file.");
        }
    }

    public static boolean ppt_included(java.lang.String ppt_name) {
        if (((((daikon.Daikon.ppt_omit_regexp) != null) && (daikon.Daikon.ppt_omit_regexp.matcher(ppt_name).find())) || (((daikon.Daikon.ppt_regexp) != null) && (!(daikon.Daikon.ppt_regexp.matcher(ppt_name).find())))) || (((daikon.Daikon.ppt_max_name) != null) && (((daikon.Daikon.ppt_max_name.compareTo(ppt_name)) < 0) && ((ppt_name.indexOf(daikon.FileIO.global_suffix)) == (-1))))) {
            return false;
        }else {
            return true;
        }
    }

    public static boolean var_included(java.lang.String var_name) {
        assert !(var_name.equals(""));
        if ((((daikon.Daikon.var_omit_regexp) != null) && (daikon.Daikon.var_omit_regexp.matcher(var_name).find())) || (((daikon.Daikon.var_regexp) != null) && (!(daikon.Daikon.var_regexp.matcher(var_name).find())))) {
            return false;
        }else {
            return true;
        }
    }

    private static void skip_decl(java.io.LineNumberReader reader) throws java.io.IOException {
        java.lang.String line = reader.readLine();
        while ((line != null) && (!(line.equals("")))) {
            line = reader.readLine();
        } 
    }

    private static java.lang.String unescape_decl(java.lang.String orig) {
        java.lang.StringBuilder sb = new java.lang.StringBuilder(orig.length());
        int post_esc = 0;
        int this_esc = orig.indexOf('\\');
        while (this_esc != (-1)) {
            if (this_esc == ((orig.length()) - 1)) {
                sb.append(orig.substring(post_esc, (this_esc + 1)));
                post_esc = this_esc + 1;
                break;
            }
            switch (orig.charAt((this_esc + 1))) {
                case 'n' :
                    sb.append(orig.substring(post_esc, this_esc));
                    sb.append('\n');
                    post_esc = this_esc + 2;
                    break;
                case 'r' :
                    sb.append(orig.substring(post_esc, this_esc));
                    sb.append('\r');
                    post_esc = this_esc + 2;
                    break;
                case '_' :
                    sb.append(orig.substring(post_esc, this_esc));
                    sb.append(' ');
                    post_esc = this_esc + 2;
                    break;
                case '\\' :
                    sb.append(orig.substring(post_esc, (this_esc + 1)));
                    post_esc = this_esc + 2;
                    break;
                default :
                    sb.append(orig.substring(post_esc, this_esc));
                    post_esc = this_esc + 1;
                    break;
            }
            this_esc = orig.indexOf('\\', post_esc);
        } 
        if (post_esc == 0)
            return orig;

        sb.append(orig.substring(post_esc));
        return sb.toString();
    }

    public static class VarDefinition implements java.io.Serializable , java.lang.Cloneable {
        static final long serialVersionUID = 20060524L;

        transient daikon.FileIO.ParseState state;

        public java.lang.String name;

        public daikon.VarInfo.VarKind kind = null;

        public java.lang.String enclosing_var;

        public java.lang.String relative_name = null;

        public daikon.VarInfo.RefType ref_type = daikon.VarInfo.RefType.POINTER;

        public int arr_dims = 0;

        public java.util.List<java.lang.String> function_args = null;

        public daikon.ProglangType rep_type = null;

        public daikon.ProglangType declared_type = null;

        public java.util.EnumSet<daikon.VarInfo.VarFlags> flags = java.util.EnumSet.noneOf(daikon.VarInfo.VarFlags.class);

        public java.util.EnumSet<daikon.VarInfo.LangFlags> lang_flags = java.util.EnumSet.noneOf(daikon.VarInfo.LangFlags.class);

        public daikon.VarComparability comparability = null;

        public java.lang.String parent_ppt = null;

        public int parent_relation_id = 0;

        public java.lang.String parent_variable = null;

        public java.lang.Object static_constant_value = null;

        public daikon.FileIO.VarDefinition clone() {
            try {
                return ((daikon.FileIO.VarDefinition) (super.clone()));
            } catch (java.lang.CloneNotSupportedException e) {
                throw new java.lang.Error("This can't happen: ", e);
            }
        }

        public daikon.FileIO.VarDefinition copy() {
            try {
                daikon.FileIO.VarDefinition copy = this.clone();
                copy.flags = flags.clone();
                copy.lang_flags = lang_flags.clone();
                return copy;
            } catch (java.lang.Throwable t) {
                throw new java.lang.RuntimeException(t);
            }
        }

        public void clear_parent_relation() {
            parent_ppt = null;
            parent_relation_id = 0;
            parent_variable = null;
        }

        public VarDefinition(daikon.FileIO.ParseState state, java.util.Scanner scanner) throws daikon.FileIO.DeclError {
            this.state = state;
            name = need(scanner, "name");
            need_eol(scanner);
            if ((state.varcomp_format) == (VarComparability.IMPLICIT))
                comparability = VarComparabilityImplicit.unknown;
            else
                comparability = VarComparabilityNone.it;

        }

        public VarDefinition(java.lang.String name, daikon.VarInfo.VarKind kind, daikon.ProglangType type) {
            this.state = null;
            this.name = name;
            this.kind = kind;
            this.rep_type = type;
            this.declared_type = type;
            comparability = VarComparabilityNone.it;
        }

        public void parse_var_kind(java.util.Scanner scanner) throws daikon.FileIO.DeclError {
            kind = parse_enum_val(scanner, daikon.VarInfo.VarKind.class, "variable kind");
            if (((kind) == (daikon.VarInfo.VarKind.FIELD)) || ((kind) == (daikon.VarInfo.VarKind.FUNCTION))) {
                relative_name = need(scanner, "relative name");
            }
            need_eol(scanner);
        }

        public void parse_enclosing_var(java.util.Scanner scanner) throws daikon.FileIO.DeclError {
            enclosing_var = need(scanner, "enclosing variable name");
            need_eol(scanner);
        }

        public void parse_reference_type(java.util.Scanner scanner) throws daikon.FileIO.DeclError {
            ref_type = parse_enum_val(scanner, daikon.VarInfo.RefType.class, "reference type");
            need_eol(scanner);
        }

        public void parse_array(java.util.Scanner scanner) throws daikon.FileIO.DeclError {
            java.lang.String arr_str = need(scanner, "array dimensions");
            if (arr_str == "0")
                arr_dims = 0;
            else
                if (arr_str == "1")
                    arr_dims = 1;
                else
                    daikon.FileIO.decl_error(state, "%s found where 0 or 1 expected", arr_str);


        }

        public void parse_function_args(java.util.Scanner scanner) throws daikon.FileIO.DeclError {
            function_args = new java.util.ArrayList<java.lang.String>();
            while (scanner.hasNext()) {
                function_args.add(daikon.FileIO.unescape_decl(scanner.next()).intern());
            } 
        }

        public void parse_rep_type(java.util.Scanner scanner) throws daikon.FileIO.DeclError {
            java.lang.String rep_type_str = need(scanner, "rep type");
            need_eol(scanner);
            rep_type = daikon.ProglangType.rep_parse(rep_type_str);
        }

        public void parse_dec_type(java.util.Scanner scanner) throws daikon.FileIO.DeclError {
            java.lang.String declared_type_str = need(scanner, "declaration type");
            need_eol(scanner);
            declared_type = daikon.ProglangType.parse(declared_type_str);
        }

        public void parse_flags(java.util.Scanner scanner) throws daikon.FileIO.DeclError {
            flags.add(parse_enum_val(scanner, daikon.VarInfo.VarFlags.class, "Flag"));
            while (scanner.hasNext())
                flags.add(parse_enum_val(scanner, daikon.VarInfo.VarFlags.class, "Flag"));

        }

        public void parse_lang_flags(java.util.Scanner scanner) throws daikon.FileIO.DeclError {
            lang_flags.add(parse_enum_val(scanner, daikon.VarInfo.LangFlags.class, "Language Specific Flag"));
            while (scanner.hasNext())
                lang_flags.add(parse_enum_val(scanner, daikon.VarInfo.LangFlags.class, "Language Specific Flag"));

        }

        public void parse_comparability(java.util.Scanner scanner) throws daikon.FileIO.DeclError {
            java.lang.String comparability_str = need(scanner, "comparability");
            need_eol(scanner);
            comparability = daikon.VarComparability.parse(state.varcomp_format, comparability_str, declared_type);
        }

        public void parse_parent(java.util.Scanner scanner, java.util.List<daikon.FileIO.ParentRelation> ppt_parents) throws daikon.FileIO.DeclError {
            parent_ppt = need(scanner, "parent ppt");
            parent_relation_id = java.lang.Integer.parseInt(need(scanner, "parent id"));
            boolean found = false;
            for (daikon.FileIO.ParentRelation pr : ppt_parents) {
                if (((pr.parent_ppt_name) == (parent_ppt)) && ((pr.id) == (parent_relation_id))) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                daikon.FileIO.decl_error(state, ("specified parent ppt '%s[%d]' for variable '%s' " + "is not a parent to this ppt"), parent_ppt, parent_relation_id, name);
            }
            if (scanner.hasNext())
                parent_variable = need(scanner, "parent variable");

            need_eol(scanner);
        }

        public void parse_constant(java.util.Scanner scanner) throws daikon.FileIO.DeclError {
            java.lang.String constant_str = need(scanner, "constant value");
            need_eol(scanner);
            static_constant_value = rep_type.parse_value(constant_str);
        }

        public java.lang.String need(java.util.Scanner scanner, java.lang.String description) throws daikon.FileIO.DeclError {
            return daikon.FileIO.need(state, scanner, description);
        }

        public void need_eol(java.util.Scanner scanner) throws daikon.FileIO.DeclError {
            daikon.FileIO.need_eol(state, scanner);
        }

        public <E extends java.lang.Enum<E>> E parse_enum_val(java.util.Scanner scanner, java.lang.Class<E> enum_class, java.lang.String descr) throws daikon.FileIO.DeclError {
            return daikon.FileIO.parse_enum_val(state, scanner, enum_class, descr);
        }
    }

    public static java.lang.String need(daikon.FileIO.ParseState state, java.util.Scanner scanner, java.lang.String description) throws daikon.FileIO.DeclError {
        if (!(scanner.hasNext()))
            daikon.FileIO.decl_error(state, "end-of-line found where %s expected", description);

        return daikon.FileIO.unescape_decl(scanner.next()).intern();
    }

    public static void need_eol(daikon.FileIO.ParseState state, java.util.Scanner scanner) throws daikon.FileIO.DeclError {
        if (scanner.hasNext())
            daikon.FileIO.decl_error(state, "'%s' found where end-of-line expected", scanner.next());

    }

    public static <E extends java.lang.Enum<E>> E parse_enum_val(daikon.FileIO.ParseState state, java.util.Scanner scanner, java.lang.Class<E> enum_class, java.lang.String descr) throws daikon.FileIO.DeclError {
        java.lang.String str = daikon.FileIO.need(state, scanner, descr);
        try {
            E e = java.lang.Enum.valueOf(enum_class, str.toUpperCase());
            return e;
        } catch (java.lang.Exception exception) {
            E[] all = enum_class.getEnumConstants();
            java.lang.String msg = "";
            for (E e : all) {
                if (msg != "")
                    msg += ", ";

                msg += java.lang.String.format("'%s'", e.name().toLowerCase());
            }
            daikon.FileIO.decl_error(state, "'%s' found where %s expected", str, msg);
            return null;
        }
    }

    private static void decl_error(daikon.FileIO.ParseState state, java.lang.String format, java.lang.Object... args) throws daikon.FileIO.DeclError {
        throw daikon.FileIO.DeclError.detail(state, format, args);
    }

    private static boolean is_declaration_header(java.lang.String line) {
        if (daikon.FileIO.new_decl_format)
            return line.startsWith("ppt ");
        else
            return line == (daikon.FileIO.declaration_header);

    }
}

