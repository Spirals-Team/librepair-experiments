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
package io.github.fasset.fasset.book.keeper;

/**
 * Represents, types of Attributes we can expect in an {@code AccountingEntry}
 */
public enum EntryAttribute {

    /**
     * Invoice number for a transaction associated with this account
     */
    Invoice,

    /**
     * A supplier for a service or item attached to this account
     */
    Supplier,

    /**
     * Unique identification for an asset item in the server
     */
    AssetId,

    /**
     * The reference number for the transaction associated with this AccountingEntry
     */
    ReferenceNumber,

    /**
     * The barcode associated with a given account Entry
     */
    Barcode
}
