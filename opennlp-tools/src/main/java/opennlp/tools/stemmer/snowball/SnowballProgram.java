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

package opennlp.tools.stemmer.snowball;

import java.lang.reflect.InvocationTargetException;

class SnowballProgram {
    protected SnowballProgram()
    {
	current = new StringBuffer();
	setCurrent("");
    }

    /**
     * Set the current string.
     */
    public void setCurrent(String value)
    {
	current.replace(0, current.length(), value);
	cursor = 0;
	limit = current.length();
	limit_backward = 0;
	bra = cursor;
	ket = limit;
    }

    /**
     * Get the current string.
     */
    public String getCurrent()
    {
        String result = current.toString();
        // Make a new StringBuffer.  If we reuse the old one, and a user of
        // the library keeps a reference to the buffer returned (for example,
        // by converting it to a String in a way which doesn't force a copy),
        // the buffer size will not decrease, and we will risk wasting a large
        // amount of memory.
        // Thanks to Wolfram Esser for spotting this problem.
        current = new StringBuffer();
        return result;
    }

    // current string
    protected StringBuffer current;

    protected int cursor;
    protected int limit;
    protected int limit_backward;
    protected int bra;
    protected int ket;

    protected void copy_from(SnowballProgram other)
    {
	current          = other.current;
	cursor           = other.cursor;
	limit            = other.limit;
	limit_backward   = other.limit_backward;
	bra              = other.bra;
	ket              = other.ket;
    }

    protected boolean in_grouping(char [] s, int min, int max)
    {
	if (cursor >= limit) return false;
	char ch = current.charAt(cursor);
	if (ch > max || ch < min) return false;
	ch -= min;
	if ((s[ch >> 3] & (0X1 << (ch & 0X7))) == 0) return false;
	cursor++;
	return true;
    }

    protected boolean in_grouping_b(char [] s, int min, int max)
    {
	if (cursor <= limit_backward) return false;
	char ch = current.charAt(cursor - 1);
	if (ch > max || ch < min) return false;
	ch -= min;
	if ((s[ch >> 3] & (0X1 << (ch & 0X7))) == 0) return false;
	cursor--;
	return true;
    }

    protected boolean out_grouping(char [] s, int min, int max)
    {
	if (cursor >= limit) return false;
	char ch = current.charAt(cursor);
	if (ch > max || ch < min) {
	    cursor++;
	    return true;
	}
	ch -= min;
	if ((s[ch >> 3] & (0X1 << (ch & 0X7))) == 0) {
	    cursor ++;
	    return true;
	}
	return false;
    }

    protected boolean out_grouping_b(char [] s, int min, int max)
    {
	if (cursor <= limit_backward) return false;
	char ch = current.charAt(cursor - 1);
	if (ch > max || ch < min) {
	    cursor--;
	    return true;
	}
	ch -= min;
	if ((s[ch >> 3] & (0X1 << (ch & 0X7))) == 0) {
	    cursor--;
	    return true;
	}
	return false;
    }

    protected boolean in_range(int min, int max)
    {
	if (cursor >= limit) return false;
	char ch = current.charAt(cursor);
	if (ch > max || ch < min) return false;
	cursor++;
	return true;
    }

    protected boolean in_range_b(int min, int max)
    {
	if (cursor <= limit_backward) return false;
	char ch = current.charAt(cursor - 1);
	if (ch > max || ch < min) return false;
	cursor--;
	return true;
    }

    protected boolean out_range(int min, int max)
    {
	if (cursor >= limit) return false;
	char ch = current.charAt(cursor);
	if (!(ch > max || ch < min)) return false;
	cursor++;
	return true;
    }

    protected boolean out_range_b(int min, int max)
    {
	if (cursor <= limit_backward) return false;
	char ch = current.charAt(cursor - 1);
	if(!(ch > max || ch < min)) return false;
	cursor--;
	return true;
    }

    protected boolean eq_s(int s_size, String s)
    {
	if (limit - cursor < s_size) return false;
	int i;
	for (i = 0; i != s_size; i++) {
	    if (current.charAt(cursor + i) != s.charAt(i)) return false;
	}
	cursor += s_size;
	return true;
    }

    protected boolean eq_s_b(int s_size, String s)
    {
	if (cursor - limit_backward < s_size) return false;
	int i;
	for (i = 0; i != s_size; i++) {
	    if (current.charAt(cursor - s_size + i) != s.charAt(i)) return false;
	}
	cursor -= s_size;
	return true;
    }

    protected boolean eq_v(CharSequence s)
    {
	return eq_s(s.length(), s.toString());
    }

    protected boolean eq_v_b(CharSequence s)
    {   return eq_s_b(s.length(), s.toString());
    }

    protected int find_among(Among v[], int v_size)
    {
	int i = 0;
	int j = v_size;

	int c = cursor;
	int l = limit;

	int common_i = 0;
	int common_j = 0;

	boolean first_key_inspected = false;

	while(true) {
	    int k = i + ((j - i) >> 1);
	    int diff = 0;
	    int common = common_i < common_j ? common_i : common_j; // smaller
	    Among w = v[k];
	    int i2;
	    for (i2 = common; i2 < w.s_size; i2++) {
		if (c + common == l) {
		    diff = -1;
		    break;
		}
		diff = current.charAt(c + common) - w.s[i2];
		if (diff != 0) break;
		common++;
	    }
	    if (diff < 0) {
		j = k;
		common_j = common;
	    } else {
		i = k;
		common_i = common;
	    }
	    if (j - i <= 1) {
		if (i > 0) break; // v->s has been inspected
		if (j == i) break; // only one item in v

		// - but now we need to go round once more to get
		// v->s inspected. This looks messy, but is actually
		// the optimal approach.

		if (first_key_inspected) break;
		first_key_inspected = true;
	    }
	}
	while(true) {
	    Among w = v[i];
	    if (common_i >= w.s_size) {
		cursor = c + w.s_size;
		if (w.method == null) return w.result;
		boolean res;
		try {
		    Object resobj = w.method.invoke(w.methodobject,
						    new Object[0]);
		    res = resobj.toString().equals("true");
		} catch (InvocationTargetException e) {
		    res = false;
		    // FIXME - debug message
		} catch (IllegalAccessException e) {
		    res = false;
		    // FIXME - debug message
		}
		cursor = c + w.s_size;
		if (res) return w.result;
	    }
	    i = w.substring_i;
	    if (i < 0) return 0;
	}
    }

    // find_among_b is for backwards processing. Same comments apply
    protected int find_among_b(Among v[], int v_size)
    {
	int i = 0;
	int j = v_size;

	int c = cursor;
	int lb = limit_backward;

	int common_i = 0;
	int common_j = 0;

	boolean first_key_inspected = false;

	while(true) {
	    int k = i + ((j - i) >> 1);
	    int diff = 0;
	    int common = common_i < common_j ? common_i : common_j;
	    Among w = v[k];
	    int i2;
	    for (i2 = w.s_size - 1 - common; i2 >= 0; i2--) {
		if (c - common == lb) {
		    diff = -1;
		    break;
		}
		diff = current.charAt(c - 1 - common) - w.s[i2];
		if (diff != 0) break;
		common++;
	    }
	    if (diff < 0) {
		j = k;
		common_j = common;
	    } else {
		i = k;
		common_i = common;
	    }
	    if (j - i <= 1) {
		if (i > 0) break;
		if (j == i) break;
		if (first_key_inspected) break;
		first_key_inspected = true;
	    }
	}
	while(true) {
	    Among w = v[i];
	    if (common_i >= w.s_size) {
		cursor = c - w.s_size;
		if (w.method == null) return w.result;

		boolean res;
		try {
		    Object resobj = w.method.invoke(w.methodobject,
						    new Object[0]);
		    res = resobj.toString().equals("true");
		} catch (InvocationTargetException e) {
		    res = false;
		    // FIXME - debug message
		} catch (IllegalAccessException e) {
		    res = false;
		    // FIXME - debug message
		}
		cursor = c - w.s_size;
		if (res) return w.result;
	    }
	    i = w.substring_i;
	    if (i < 0) return 0;
	}
    }

    /* to replace chars between c_bra and c_ket in current by the
     * chars in s.
     */
    protected int replace_s(int c_bra, int c_ket, String s)
    {
	int adjustment = s.length() - (c_ket - c_bra);
	current.replace(c_bra, c_ket, s);
	limit += adjustment;
	if (cursor >= c_ket) cursor += adjustment;
	else if (cursor > c_bra) cursor = c_bra;
	return adjustment;
    }

    protected void slice_check()
    {
	if (bra < 0 ||
	    bra > ket ||
	    ket > limit ||
	    limit > current.length())   // this line could be removed
	{
	    System.err.println("faulty slice operation");
	// FIXME: report error somehow.
	/*
	    fprintf(stderr, "faulty slice operation:\n");
	    debug(z, -1, 0);
	    exit(1);
	    */
	}
    }

    protected void slice_from(String s)
    {
	slice_check();
	replace_s(bra, ket, s);
    }

    protected void slice_from(CharSequence s)
    {
        slice_from(s.toString());
    }

    protected void slice_del()
    {
	slice_from("");
    }

    protected void insert(int c_bra, int c_ket, String s)
    {
	int adjustment = replace_s(c_bra, c_ket, s);
	if (c_bra <= bra) bra += adjustment;
	if (c_bra <= ket) ket += adjustment;
    }

    protected void insert(int c_bra, int c_ket, CharSequence s)
    {
	insert(c_bra, c_ket, s.toString());
    }

    /* Copy the slice into the supplied StringBuffer */
    protected StringBuffer slice_to(StringBuffer s)
    {
	slice_check();
	int len = ket - bra;
	s.replace(0, s.length(), current.substring(bra, ket));
	return s;
    }

    /* Copy the slice into the supplied StringBuilder */
    protected StringBuilder slice_to(StringBuilder s)
    {
	slice_check();
	int len = ket - bra;
	s.replace(0, s.length(), current.substring(bra, ket));
	return s;
    }

    protected StringBuffer assign_to(StringBuffer s)
    {
	s.replace(0, s.length(), current.substring(0, limit));
	return s;
    }

    protected StringBuilder assign_to(StringBuilder s)
    {
	s.replace(0, s.length(), current.substring(0, limit));
	return s;
    }

/*
extern void debug(struct SN_env * z, int number, int line_count)
{   int i;
    int limit = SIZE(z->p);
    //if (number >= 0) printf("%3d (line %4d): '", number, line_count);
    if (number >= 0) printf("%3d (line %4d): [%d]'", number, line_count,limit);
    for (i = 0; i <= limit; i++)
    {   if (z->lb == i) printf("{");
        if (z->bra == i) printf("[");
        if (z->c == i) printf("|");
        if (z->ket == i) printf("]");
        if (z->l == i) printf("}");
        if (i < limit)
        {   int ch = z->p[i];
            if (ch == 0) ch = '#';
            printf("%c", ch);
        }
    }
    printf("'\n");
}
*/

}
