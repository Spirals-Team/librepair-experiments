/* $$ This file has been instrumented by Clover 4.2.1#20171121235008561 $$ */

package example;


public class TestSuiteExample {static class __CLR4_2_100jhqey0cc{public static com_atlassian_clover.CoverageRecorder R;public static com_atlassian_clover.CloverProfile[] profiles = { };@java.lang.SuppressWarnings("unchecked") public static <I, T extends I> I lambdaInc(final int i,final T l,final int si){java.lang.reflect.InvocationHandler h=new java.lang.reflect.InvocationHandler(){public java.lang.Object invoke(java.lang.Object p,java.lang.reflect.Method m,java.lang.Object[] a) throws Throwable{R.inc(i);R.inc(si);try{return m.invoke(l,a);}catch(java.lang.reflect.InvocationTargetException e){throw e.getCause()!=null?e.getCause():new RuntimeException("Clover failed to invoke instrumented lambda",e);}}};return (I)java.lang.reflect.Proxy.newProxyInstance(l.getClass().getClassLoader(),l.getClass().getInterfaces(),h);}static{com_atlassian_clover.CoverageRecorder _R=null;try{com_atlassian_clover.CloverVersionInfo.An_old_version_of_clover_is_on_your_compilation_classpath___Please_remove___Required_version_is___4_2_1();if(20171121235008561L!=com_atlassian_clover.CloverVersionInfo.getBuildStamp()){com_atlassian_clover.Clover.l("[CLOVER] WARNING: The Clover version used in instrumentation does not match the runtime version. You need to run instrumented classes against the same version of Clover that you instrumented with.");com_atlassian_clover.Clover.l("[CLOVER] WARNING: Instr=4.2.1#20171121235008561,Runtime="+com_atlassian_clover.CloverVersionInfo.getReleaseNum()+"#"+com_atlassian_clover.CloverVersionInfo.getBuildStamp());}R=com_atlassian_clover.Clover.getNullRecorder();_R=com_atlassian_clover.Clover.getNullRecorder();_R=com_atlassian_clover.Clover.getRecorder("\u002f\u0072\u006f\u006f\u0074\u002f\u0077\u006f\u0072\u006b\u0073\u0070\u0061\u0063\u0065\u002f\u0053\u0054\u0041\u004d\u0050\u002d\u0070\u0072\u006f\u006a\u0065\u0063\u0074\u002f\u0064\u0073\u0070\u006f\u0074\u002f\u0033\u0038\u0034\u0037\u0038\u0030\u0034\u0032\u0037\u002f\u0064\u0073\u0070\u006f\u0074\u002f\u0074\u0061\u0072\u0067\u0065\u0074\u002f\u0064\u0073\u0070\u006f\u0074\u002f\u0063\u006c\u006f\u0076\u0065\u0072\u002f\u0063\u006c\u006f\u0076\u0065\u0072\u002e\u0064\u0062",1527521639479L,0L,20,profiles,new java.lang.String[]{"clover.distributed.coverage",null});}catch(java.lang.SecurityException e){java.lang.System.err.println("[CLOVER] FATAL ERROR: Clover could not be initialised because it has insufficient security privileges. Please consult the Clover documentation on the security policy file changes required. ("+e.getClass()+":"+e.getMessage()+")");}catch(java.lang.NoClassDefFoundError e){java.lang.System.err.println("[CLOVER] FATAL ERROR: Clover could not be initialised. Are you sure you have Clover in the runtime classpath? ("+e.getClass()+":"+e.getMessage()+")");}catch(java.lang.Throwable t){java.lang.System.err.println("[CLOVER] FATAL ERROR: Clover could not be initialised because of an unexpected error. ("+t.getClass()+":"+t.getMessage()+")");}R=_R;}}public static final com_atlassian_clover.TestNameSniffer __CLR4_2_1_TEST_NAME_SNIFFER=com_atlassian_clover.TestNameSniffer.NULL_INSTANCE;

    @org.junit.Test
    public void test3() {__CLR4_2_100jhqey0cc.R.globalSliceStart(getClass().getName(),0);int $CLV_p$=0;java.lang.Throwable $CLV_t$=null;try{__CLR4_2_1aw68zs0();$CLV_p$=1;}catch(java.lang.Throwable $CLV_t2$){if($CLV_p$==0&&$CLV_t$==null){$CLV_t$=$CLV_t2$;}__CLR4_2_100jhqey0cc.R.rethrow($CLV_t2$);}finally{__CLR4_2_100jhqey0cc.R.globalSliceEnd(getClass().getName(),"example.TestSuiteExample.test3",__CLR4_2_1_TEST_NAME_SNIFFER.getTestName(),0,$CLV_p$,$CLV_t$);}}private void  __CLR4_2_1aw68zs0(){__CLR4_2_100jhqey0cc.R.inc(0);
        __CLR4_2_100jhqey0cc.R.inc(1);example.Example ex = new example.Example();
        __CLR4_2_100jhqey0cc.R.inc(2);java.lang.String s = "abcd";
        __CLR4_2_100jhqey0cc.R.inc(3);org.junit.Assert.assertEquals('d', ex.charAt(s, ((s.length()) - 1)));
    }

    @org.junit.Test
    public void test4() {__CLR4_2_100jhqey0cc.R.globalSliceStart(getClass().getName(),4);int $CLV_p$=0;java.lang.Throwable $CLV_t$=null;try{__CLR4_2_1e567s94();$CLV_p$=1;}catch(java.lang.Throwable $CLV_t2$){if($CLV_p$==0&&$CLV_t$==null){$CLV_t$=$CLV_t2$;}__CLR4_2_100jhqey0cc.R.rethrow($CLV_t2$);}finally{__CLR4_2_100jhqey0cc.R.globalSliceEnd(getClass().getName(),"example.TestSuiteExample.test4",__CLR4_2_1_TEST_NAME_SNIFFER.getTestName(),4,$CLV_p$,$CLV_t$);}}private void  __CLR4_2_1e567s94(){__CLR4_2_100jhqey0cc.R.inc(4);
        __CLR4_2_100jhqey0cc.R.inc(5);example.Example ex = new example.Example();
        __CLR4_2_100jhqey0cc.R.inc(6);java.lang.String s = "abcd";
        __CLR4_2_100jhqey0cc.R.inc(7);org.junit.Assert.assertEquals('d', ex.charAt(s, 12));
    }

    @org.junit.Test
    public void test7() {__CLR4_2_100jhqey0cc.R.globalSliceStart(getClass().getName(),8);int $CLV_p$=0;java.lang.Throwable $CLV_t$=null;try{__CLR4_2_1nw645o8();$CLV_p$=1;}catch(java.lang.Throwable $CLV_t2$){if($CLV_p$==0&&$CLV_t$==null){$CLV_t$=$CLV_t2$;}__CLR4_2_100jhqey0cc.R.rethrow($CLV_t2$);}finally{__CLR4_2_100jhqey0cc.R.globalSliceEnd(getClass().getName(),"example.TestSuiteExample.test7",__CLR4_2_1_TEST_NAME_SNIFFER.getTestName(),8,$CLV_p$,$CLV_t$);}}private void  __CLR4_2_1nw645o8(){__CLR4_2_100jhqey0cc.R.inc(8);
        __CLR4_2_100jhqey0cc.R.inc(9);example.Example ex = new example.Example();
        __CLR4_2_100jhqey0cc.R.inc(10);org.junit.Assert.assertEquals('c', ex.charAt("abcd", 2));
    }

    @org.junit.Test
    public void test8() {__CLR4_2_100jhqey0cc.R.globalSliceStart(getClass().getName(),11);int $CLV_p$=0;java.lang.Throwable $CLV_t$=null;try{__CLR4_2_1r562y5b();$CLV_p$=1;}catch(java.lang.Throwable $CLV_t2$){if($CLV_p$==0&&$CLV_t$==null){$CLV_t$=$CLV_t2$;}__CLR4_2_100jhqey0cc.R.rethrow($CLV_t2$);}finally{__CLR4_2_100jhqey0cc.R.globalSliceEnd(getClass().getName(),"example.TestSuiteExample.test8",__CLR4_2_1_TEST_NAME_SNIFFER.getTestName(),11,$CLV_p$,$CLV_t$);}}private void  __CLR4_2_1r562y5b(){__CLR4_2_100jhqey0cc.R.inc(11);
        __CLR4_2_100jhqey0cc.R.inc(12);example.Example ex = new example.Example();
        __CLR4_2_100jhqey0cc.R.inc(13);org.junit.Assert.assertEquals('b', ex.charAt("abcd", 1));
    }

    @org.junit.Test
    public void test9() {__CLR4_2_100jhqey0cc.R.globalSliceStart(getClass().getName(),14);int $CLV_p$=0;java.lang.Throwable $CLV_t$=null;try{__CLR4_2_1ue61qme();$CLV_p$=1;}catch(java.lang.Throwable $CLV_t2$){if($CLV_p$==0&&$CLV_t$==null){$CLV_t$=$CLV_t2$;}__CLR4_2_100jhqey0cc.R.rethrow($CLV_t2$);}finally{__CLR4_2_100jhqey0cc.R.globalSliceEnd(getClass().getName(),"example.TestSuiteExample.test9",__CLR4_2_1_TEST_NAME_SNIFFER.getTestName(),14,$CLV_p$,$CLV_t$);}}private void  __CLR4_2_1ue61qme(){__CLR4_2_100jhqey0cc.R.inc(14);
        __CLR4_2_100jhqey0cc.R.inc(15);example.Example ex = new example.Example();
        __CLR4_2_100jhqey0cc.R.inc(16);org.junit.Assert.assertEquals('f', ex.charAt("abcdefghijklm", 5));
    }

    @org.junit.Test
    public void test2() {__CLR4_2_100jhqey0cc.R.globalSliceStart(getClass().getName(),17);int $CLV_p$=0;java.lang.Throwable $CLV_t$=null;try{__CLR4_2_17n6a7bh();$CLV_p$=1;}catch(java.lang.Throwable $CLV_t2$){if($CLV_p$==0&&$CLV_t$==null){$CLV_t$=$CLV_t2$;}__CLR4_2_100jhqey0cc.R.rethrow($CLV_t2$);}finally{__CLR4_2_100jhqey0cc.R.globalSliceEnd(getClass().getName(),"example.TestSuiteExample.test2",__CLR4_2_1_TEST_NAME_SNIFFER.getTestName(),17,$CLV_p$,$CLV_t$);}}private void  __CLR4_2_17n6a7bh(){__CLR4_2_100jhqey0cc.R.inc(17);
        __CLR4_2_100jhqey0cc.R.inc(18);example.Example ex = new example.Example();
        __CLR4_2_100jhqey0cc.R.inc(19);org.junit.Assert.assertEquals('d', ex.charAt("abcd", 3));
    }
}

