/**
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.opennars.operator.misc;

import org.opennars.language.CompoundTerm;
import org.opennars.language.SetExt;
import org.opennars.language.SetInt;
import org.opennars.language.Term;
import org.opennars.operator.FunctionOperator;
import org.opennars.storage.Memory;

/**
 * Count the number of elements in a set
 * 

'INVALID
(^count,a)!
(^count,a,b)!
(^count,a,#b)!

'VALID: 
(^count,[a,b],#b)!

 * 
 */
public class Count extends FunctionOperator {

    public Count() {
        super("^count");
    }

    final static String requireMessage = "Requires 1 SetExt or SetInt argument";
    
    final static Term counted = Term.get("counted");
    
    
    @Override
    protected Term function(final Memory memory, final Term[] x) {
        if (x.length!=1) {
            throw new IllegalStateException(requireMessage);
        }

        final Term content = x[0];
        if (!(content instanceof SetExt) && !(content instanceof SetInt)) {
            throw new IllegalStateException(requireMessage);
        }       
        
        final int n = ((CompoundTerm) content).size();
        return Term.get(n);
    }

    @Override
    protected Term getRange() {
        return counted;
    }


    
}
