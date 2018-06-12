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
package io.github.fasset.fasset.book.keeper.util;

import io.github.fasset.fasset.book.keeper.AccountingEntry;

import java.util.Currency;

/**
 * Thrown when the {@link AccountingEntry} being added to an {@link io.github.fasset.fasset.book.keeper.Account} has {@link Currency} that
 * differs to that of the {@link io.github.fasset.fasset.book.keeper.Account}, or when the {@link Currency} of the {@link io.github.fasset.fasset.book.keeper.Account}
 * and {@link AccountingEntry} being added to a {@link io.github.fasset.fasset.book.Transaction} are different
 *
 * @author edwin.njeru
 */
public class MismatchedCurrencyException extends Exception {

    private static final long serialVersionUID = 2585125223615187597L;

    /**
     * Constructs a new throwable with the specified detail message.  The
     * cause is not initialized, and may subsequently be initialized by
     * a call to {@link #initCause}.
     * The {@link #fillInStackTrace()} method is called to initialize
     * the stack trace data in the newly created throwable.
     *
     * @param message the detail message. The detail message is saved for
     *                later retrieval by the {@link #getMessage()} method.
     */
    public MismatchedCurrencyException(String message) {
        super(message);
    }
}
