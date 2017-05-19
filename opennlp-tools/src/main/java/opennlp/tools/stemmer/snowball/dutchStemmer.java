// CHECKSTYLE:OFF
/*

Copyright (c) 2001, Dr Martin Porter
Copyright (c) 2002, Richard Boulton
All rights reserved.

Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions are met:

    * Redistributions of source code must retain the above copyright notice,
    * this list of conditions and the following disclaimer.
    * Redistributions in binary form must reproduce the above copyright
    * notice, this list of conditions and the following disclaimer in the
    * documentation and/or other materials provided with the distribution.
    * Neither the name of the copyright holders nor the names of its contributors
    * may be used to endorse or promote products derived from this software
    * without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

 */

// This file was generated automatically by the Snowball to Java compiler

package opennlp.tools.stemmer.snowball;

 /**
  * This class was automatically generated by a Snowball to Java compiler
  * It implements the stemming algorithm defined by a snowball script.
  */
class dutchStemmer extends opennlp.tools.stemmer.snowball.AbstractSnowballStemmer {

private static final long serialVersionUID = 1L;

        private final static dutchStemmer methodObject = new dutchStemmer ();

                private final static Among a_0[] = {
                    new Among ( "", -1, 6, "", methodObject ),
                    new Among ( "\u00E1", 0, 1, "", methodObject ),
                    new Among ( "\u00E4", 0, 1, "", methodObject ),
                    new Among ( "\u00E9", 0, 2, "", methodObject ),
                    new Among ( "\u00EB", 0, 2, "", methodObject ),
                    new Among ( "\u00ED", 0, 3, "", methodObject ),
                    new Among ( "\u00EF", 0, 3, "", methodObject ),
                    new Among ( "\u00F3", 0, 4, "", methodObject ),
                    new Among ( "\u00F6", 0, 4, "", methodObject ),
                    new Among ( "\u00FA", 0, 5, "", methodObject ),
                    new Among ( "\u00FC", 0, 5, "", methodObject )
                };

                private final static Among a_1[] = {
                    new Among ( "", -1, 3, "", methodObject ),
                    new Among ( "I", 0, 2, "", methodObject ),
                    new Among ( "Y", 0, 1, "", methodObject )
                };

                private final static Among a_2[] = {
                    new Among ( "dd", -1, -1, "", methodObject ),
                    new Among ( "kk", -1, -1, "", methodObject ),
                    new Among ( "tt", -1, -1, "", methodObject )
                };

                private final static Among a_3[] = {
                    new Among ( "ene", -1, 2, "", methodObject ),
                    new Among ( "se", -1, 3, "", methodObject ),
                    new Among ( "en", -1, 2, "", methodObject ),
                    new Among ( "heden", 2, 1, "", methodObject ),
                    new Among ( "s", -1, 3, "", methodObject )
                };

                private final static Among a_4[] = {
                    new Among ( "end", -1, 1, "", methodObject ),
                    new Among ( "ig", -1, 2, "", methodObject ),
                    new Among ( "ing", -1, 1, "", methodObject ),
                    new Among ( "lijk", -1, 3, "", methodObject ),
                    new Among ( "baar", -1, 4, "", methodObject ),
                    new Among ( "bar", -1, 5, "", methodObject )
                };

                private final static Among a_5[] = {
                    new Among ( "aa", -1, -1, "", methodObject ),
                    new Among ( "ee", -1, -1, "", methodObject ),
                    new Among ( "oo", -1, -1, "", methodObject ),
                    new Among ( "uu", -1, -1, "", methodObject )
                };

                private static final char g_v[] = {17, 65, 16, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 128 };

                private static final char g_v_I[] = {1, 0, 0, 17, 65, 16, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 128 };

                private static final char g_v_j[] = {17, 67, 16, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 128 };

        private int I_p2;
        private int I_p1;
        private boolean B_e_found;

                private void copy_from(dutchStemmer other) {
                    I_p2 = other.I_p2;
                    I_p1 = other.I_p1;
                    B_e_found = other.B_e_found;
                    super.copy_from(other);
                }

                private boolean r_prelude() {
            int among_var;
            int v_1;
            int v_2;
            int v_3;
            int v_4;
            int v_5;
            int v_6;
                    // (, line 41
                    // test, line 42
                    v_1 = cursor;
                    // repeat, line 42
                    replab0: while(true)
                    {
                        v_2 = cursor;
                        lab1: do {
                            // (, line 42
                            // [, line 43
                            bra = cursor;
                            // substring, line 43
                            among_var = find_among(a_0, 11);
                            if (among_var == 0)
                            {
                                break lab1;
                            }
                            // ], line 43
                            ket = cursor;
                            switch(among_var) {
                                case 0:
                                    break lab1;
                                case 1:
                                    // (, line 45
                                    // <-, line 45
                                    slice_from("a");
                                    break;
                                case 2:
                                    // (, line 47
                                    // <-, line 47
                                    slice_from("e");
                                    break;
                                case 3:
                                    // (, line 49
                                    // <-, line 49
                                    slice_from("i");
                                    break;
                                case 4:
                                    // (, line 51
                                    // <-, line 51
                                    slice_from("o");
                                    break;
                                case 5:
                                    // (, line 53
                                    // <-, line 53
                                    slice_from("u");
                                    break;
                                case 6:
                                    // (, line 54
                                    // next, line 54
                                    if (cursor >= limit)
                                    {
                                        break lab1;
                                    }
                                    cursor++;
                                    break;
                            }
                            continue replab0;
                        } while (false);
                        cursor = v_2;
                        break replab0;
                    }
                    cursor = v_1;
                    // try, line 57
                    v_3 = cursor;
                    lab2: do {
                        // (, line 57
                        // [, line 57
                        bra = cursor;
                        // literal, line 57
                        if (!(eq_s(1, "y")))
                        {
                            cursor = v_3;
                            break lab2;
                        }
                        // ], line 57
                        ket = cursor;
                        // <-, line 57
                        slice_from("Y");
                    } while (false);
                    // repeat, line 58
                    replab3: while(true)
                    {
                        v_4 = cursor;
                        lab4: do {
                            // goto, line 58
                            golab5: while(true)
                            {
                                v_5 = cursor;
                                lab6: do {
                                    // (, line 58
                                    if (!(in_grouping(g_v, 97, 232)))
                                    {
                                        break lab6;
                                    }
                                    // [, line 59
                                    bra = cursor;
                                    // or, line 59
                                    lab7: do {
                                        v_6 = cursor;
                                        lab8: do {
                                            // (, line 59
                                            // literal, line 59
                                            if (!(eq_s(1, "i")))
                                            {
                                                break lab8;
                                            }
                                            // ], line 59
                                            ket = cursor;
                                            if (!(in_grouping(g_v, 97, 232)))
                                            {
                                                break lab8;
                                            }
                                            // <-, line 59
                                            slice_from("I");
                                            break lab7;
                                        } while (false);
                                        cursor = v_6;
                                        // (, line 60
                                        // literal, line 60
                                        if (!(eq_s(1, "y")))
                                        {
                                            break lab6;
                                        }
                                        // ], line 60
                                        ket = cursor;
                                        // <-, line 60
                                        slice_from("Y");
                                    } while (false);
                                    cursor = v_5;
                                    break golab5;
                                } while (false);
                                cursor = v_5;
                                if (cursor >= limit)
                                {
                                    break lab4;
                                }
                                cursor++;
                            }
                            continue replab3;
                        } while (false);
                        cursor = v_4;
                        break replab3;
                    }
                    return true;
                }

                private boolean r_mark_regions() {
                    // (, line 64
                    I_p1 = limit;
                    I_p2 = limit;
                    // gopast, line 69
                    golab0: while(true)
                    {
                        lab1: do {
                            if (!(in_grouping(g_v, 97, 232)))
                            {
                                break lab1;
                            }
                            break golab0;
                        } while (false);
                        if (cursor >= limit)
                        {
                            return false;
                        }
                        cursor++;
                    }
                    // gopast, line 69
                    golab2: while(true)
                    {
                        lab3: do {
                            if (!(out_grouping(g_v, 97, 232)))
                            {
                                break lab3;
                            }
                            break golab2;
                        } while (false);
                        if (cursor >= limit)
                        {
                            return false;
                        }
                        cursor++;
                    }
                    // setmark p1, line 69
                    I_p1 = cursor;
                    // try, line 70
                    lab4: do {
                        // (, line 70
                        if (!(I_p1 < 3))
                        {
                            break lab4;
                        }
                        I_p1 = 3;
                    } while (false);
                    // gopast, line 71
                    golab5: while(true)
                    {
                        lab6: do {
                            if (!(in_grouping(g_v, 97, 232)))
                            {
                                break lab6;
                            }
                            break golab5;
                        } while (false);
                        if (cursor >= limit)
                        {
                            return false;
                        }
                        cursor++;
                    }
                    // gopast, line 71
                    golab7: while(true)
                    {
                        lab8: do {
                            if (!(out_grouping(g_v, 97, 232)))
                            {
                                break lab8;
                            }
                            break golab7;
                        } while (false);
                        if (cursor >= limit)
                        {
                            return false;
                        }
                        cursor++;
                    }
                    // setmark p2, line 71
                    I_p2 = cursor;
                    return true;
                }

                private boolean r_postlude() {
            int among_var;
            int v_1;
                    // repeat, line 75
                    replab0: while(true)
                    {
                        v_1 = cursor;
                        lab1: do {
                            // (, line 75
                            // [, line 77
                            bra = cursor;
                            // substring, line 77
                            among_var = find_among(a_1, 3);
                            if (among_var == 0)
                            {
                                break lab1;
                            }
                            // ], line 77
                            ket = cursor;
                            switch(among_var) {
                                case 0:
                                    break lab1;
                                case 1:
                                    // (, line 78
                                    // <-, line 78
                                    slice_from("y");
                                    break;
                                case 2:
                                    // (, line 79
                                    // <-, line 79
                                    slice_from("i");
                                    break;
                                case 3:
                                    // (, line 80
                                    // next, line 80
                                    if (cursor >= limit)
                                    {
                                        break lab1;
                                    }
                                    cursor++;
                                    break;
                            }
                            continue replab0;
                        } while (false);
                        cursor = v_1;
                        break replab0;
                    }
                    return true;
                }

                private boolean r_R1() {
                    if (!(I_p1 <= cursor))
                    {
                        return false;
                    }
                    return true;
                }

                private boolean r_R2() {
                    if (!(I_p2 <= cursor))
                    {
                        return false;
                    }
                    return true;
                }

                private boolean r_undouble() {
            int v_1;
                    // (, line 90
                    // test, line 91
                    v_1 = limit - cursor;
                    // among, line 91
                    if (find_among_b(a_2, 3) == 0)
                    {
                        return false;
                    }
                    cursor = limit - v_1;
                    // [, line 91
                    ket = cursor;
                    // next, line 91
                    if (cursor <= limit_backward)
                    {
                        return false;
                    }
                    cursor--;
                    // ], line 91
                    bra = cursor;
                    // delete, line 91
                    slice_del();
                    return true;
                }

                private boolean r_e_ending() {
            int v_1;
                    // (, line 94
                    // unset e_found, line 95
                    B_e_found = false;
                    // [, line 96
                    ket = cursor;
                    // literal, line 96
                    if (!(eq_s_b(1, "e")))
                    {
                        return false;
                    }
                    // ], line 96
                    bra = cursor;
                    // call R1, line 96
                    if (!r_R1())
                    {
                        return false;
                    }
                    // test, line 96
                    v_1 = limit - cursor;
                    if (!(out_grouping_b(g_v, 97, 232)))
                    {
                        return false;
                    }
                    cursor = limit - v_1;
                    // delete, line 96
                    slice_del();
                    // set e_found, line 97
                    B_e_found = true;
                    // call undouble, line 98
                    if (!r_undouble())
                    {
                        return false;
                    }
                    return true;
                }

                private boolean r_en_ending() {
            int v_1;
            int v_2;
                    // (, line 101
                    // call R1, line 102
                    if (!r_R1())
                    {
                        return false;
                    }
                    // and, line 102
                    v_1 = limit - cursor;
                    if (!(out_grouping_b(g_v, 97, 232)))
                    {
                        return false;
                    }
                    cursor = limit - v_1;
                    // not, line 102
                    {
                        v_2 = limit - cursor;
                        lab0: do {
                            // literal, line 102
                            if (!(eq_s_b(3, "gem")))
                            {
                                break lab0;
                            }
                            return false;
                        } while (false);
                        cursor = limit - v_2;
                    }
                    // delete, line 102
                    slice_del();
                    // call undouble, line 103
                    if (!r_undouble())
                    {
                        return false;
                    }
                    return true;
                }

                private boolean r_standard_suffix() {
            int among_var;
            int v_1;
            int v_2;
            int v_3;
            int v_4;
            int v_5;
            int v_6;
            int v_7;
            int v_8;
            int v_9;
            int v_10;
                    // (, line 106
                    // do, line 107
                    v_1 = limit - cursor;
                    lab0: do {
                        // (, line 107
                        // [, line 108
                        ket = cursor;
                        // substring, line 108
                        among_var = find_among_b(a_3, 5);
                        if (among_var == 0)
                        {
                            break lab0;
                        }
                        // ], line 108
                        bra = cursor;
                        switch(among_var) {
                            case 0:
                                break lab0;
                            case 1:
                                // (, line 110
                                // call R1, line 110
                                if (!r_R1())
                                {
                                    break lab0;
                                }
                                // <-, line 110
                                slice_from("heid");
                                break;
                            case 2:
                                // (, line 113
                                // call en_ending, line 113
                                if (!r_en_ending())
                                {
                                    break lab0;
                                }
                                break;
                            case 3:
                                // (, line 116
                                // call R1, line 116
                                if (!r_R1())
                                {
                                    break lab0;
                                }
                                if (!(out_grouping_b(g_v_j, 97, 232)))
                                {
                                    break lab0;
                                }
                                // delete, line 116
                                slice_del();
                                break;
                        }
                    } while (false);
                    cursor = limit - v_1;
                    // do, line 120
                    v_2 = limit - cursor;
                    lab1: do {
                        // call e_ending, line 120
                        if (!r_e_ending())
                        {
                            break lab1;
                        }
                    } while (false);
                    cursor = limit - v_2;
                    // do, line 122
                    v_3 = limit - cursor;
                    lab2: do {
                        // (, line 122
                        // [, line 122
                        ket = cursor;
                        // literal, line 122
                        if (!(eq_s_b(4, "heid")))
                        {
                            break lab2;
                        }
                        // ], line 122
                        bra = cursor;
                        // call R2, line 122
                        if (!r_R2())
                        {
                            break lab2;
                        }
                        // not, line 122
                        {
                            v_4 = limit - cursor;
                            lab3: do {
                                // literal, line 122
                                if (!(eq_s_b(1, "c")))
                                {
                                    break lab3;
                                }
                                break lab2;
                            } while (false);
                            cursor = limit - v_4;
                        }
                        // delete, line 122
                        slice_del();
                        // [, line 123
                        ket = cursor;
                        // literal, line 123
                        if (!(eq_s_b(2, "en")))
                        {
                            break lab2;
                        }
                        // ], line 123
                        bra = cursor;
                        // call en_ending, line 123
                        if (!r_en_ending())
                        {
                            break lab2;
                        }
                    } while (false);
                    cursor = limit - v_3;
                    // do, line 126
                    v_5 = limit - cursor;
                    lab4: do {
                        // (, line 126
                        // [, line 127
                        ket = cursor;
                        // substring, line 127
                        among_var = find_among_b(a_4, 6);
                        if (among_var == 0)
                        {
                            break lab4;
                        }
                        // ], line 127
                        bra = cursor;
                        switch(among_var) {
                            case 0:
                                break lab4;
                            case 1:
                                // (, line 129
                                // call R2, line 129
                                if (!r_R2())
                                {
                                    break lab4;
                                }
                                // delete, line 129
                                slice_del();
                                // or, line 130
                                lab5: do {
                                    v_6 = limit - cursor;
                                    lab6: do {
                                        // (, line 130
                                        // [, line 130
                                        ket = cursor;
                                        // literal, line 130
                                        if (!(eq_s_b(2, "ig")))
                                        {
                                            break lab6;
                                        }
                                        // ], line 130
                                        bra = cursor;
                                        // call R2, line 130
                                        if (!r_R2())
                                        {
                                            break lab6;
                                        }
                                        // not, line 130
                                        {
                                            v_7 = limit - cursor;
                                            lab7: do {
                                                // literal, line 130
                                                if (!(eq_s_b(1, "e")))
                                                {
                                                    break lab7;
                                                }
                                                break lab6;
                                            } while (false);
                                            cursor = limit - v_7;
                                        }
                                        // delete, line 130
                                        slice_del();
                                        break lab5;
                                    } while (false);
                                    cursor = limit - v_6;
                                    // call undouble, line 130
                                    if (!r_undouble())
                                    {
                                        break lab4;
                                    }
                                } while (false);
                                break;
                            case 2:
                                // (, line 133
                                // call R2, line 133
                                if (!r_R2())
                                {
                                    break lab4;
                                }
                                // not, line 133
                                {
                                    v_8 = limit - cursor;
                                    lab8: do {
                                        // literal, line 133
                                        if (!(eq_s_b(1, "e")))
                                        {
                                            break lab8;
                                        }
                                        break lab4;
                                    } while (false);
                                    cursor = limit - v_8;
                                }
                                // delete, line 133
                                slice_del();
                                break;
                            case 3:
                                // (, line 136
                                // call R2, line 136
                                if (!r_R2())
                                {
                                    break lab4;
                                }
                                // delete, line 136
                                slice_del();
                                // call e_ending, line 136
                                if (!r_e_ending())
                                {
                                    break lab4;
                                }
                                break;
                            case 4:
                                // (, line 139
                                // call R2, line 139
                                if (!r_R2())
                                {
                                    break lab4;
                                }
                                // delete, line 139
                                slice_del();
                                break;
                            case 5:
                                // (, line 142
                                // call R2, line 142
                                if (!r_R2())
                                {
                                    break lab4;
                                }
                                // Boolean test e_found, line 142
                                if (!(B_e_found))
                                {
                                    break lab4;
                                }
                                // delete, line 142
                                slice_del();
                                break;
                        }
                    } while (false);
                    cursor = limit - v_5;
                    // do, line 146
                    v_9 = limit - cursor;
                    lab9: do {
                        // (, line 146
                        if (!(out_grouping_b(g_v_I, 73, 232)))
                        {
                            break lab9;
                        }
                        // test, line 148
                        v_10 = limit - cursor;
                        // (, line 148
                        // among, line 149
                        if (find_among_b(a_5, 4) == 0)
                        {
                            break lab9;
                        }
                        if (!(out_grouping_b(g_v, 97, 232)))
                        {
                            break lab9;
                        }
                        cursor = limit - v_10;
                        // [, line 152
                        ket = cursor;
                        // next, line 152
                        if (cursor <= limit_backward)
                        {
                            break lab9;
                        }
                        cursor--;
                        // ], line 152
                        bra = cursor;
                        // delete, line 152
                        slice_del();
                    } while (false);
                    cursor = limit - v_9;
                    return true;
                }

                public boolean stem() {
            int v_1;
            int v_2;
            int v_3;
            int v_4;
                    // (, line 157
                    // do, line 159
                    v_1 = cursor;
                    lab0: do {
                        // call prelude, line 159
                        if (!r_prelude())
                        {
                            break lab0;
                        }
                    } while (false);
                    cursor = v_1;
                    // do, line 160
                    v_2 = cursor;
                    lab1: do {
                        // call mark_regions, line 160
                        if (!r_mark_regions())
                        {
                            break lab1;
                        }
                    } while (false);
                    cursor = v_2;
                    // backwards, line 161
                    limit_backward = cursor; cursor = limit;
                    // do, line 162
                    v_3 = limit - cursor;
                    lab2: do {
                        // call standard_suffix, line 162
                        if (!r_standard_suffix())
                        {
                            break lab2;
                        }
                    } while (false);
                    cursor = limit - v_3;
                    cursor = limit_backward;                    // do, line 163
                    v_4 = cursor;
                    lab3: do {
                        // call postlude, line 163
                        if (!r_postlude())
                        {
                            break lab3;
                        }
                    } while (false);
                    cursor = v_4;
                    return true;
                }

        public boolean equals( Object o ) {
            return o instanceof dutchStemmer;
        }

        public int hashCode() {
            return dutchStemmer.class.getName().hashCode();
        }



}

