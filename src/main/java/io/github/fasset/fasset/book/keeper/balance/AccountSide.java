/*
 * fassets - Project for light-weight tracking of fixed assets
 * Copyright © 2018 Edwin Njeru (mailnjeru@gmail.com)
 *
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
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package io.github.fasset.fasset.book.keeper.balance;

import io.github.fasset.fasset.book.keeper.AccountingEntry;

/**
 * Denotes the side to which a transaction belongs on the balance sheet. Either the debit side or credit side
 * of the SimpleAccount entry. It also show the "default" that an {@link io.github.fasset.fasset.book.keeper.Account}
 * belongs to before any {@link AccountingEntry}
 * items are posted
 *
 * @author edwin.njeru
 */
public enum AccountSide {

    /**
     * Debit side of the balance sheet
     */
    DEBIT {
        public String toString() {
            return "DR";
        }
    },

    /**
     * Credit side of the balance sheet
     */
    CREDIT {
        public String toString() {
            return "CR";
        }
    }
}
