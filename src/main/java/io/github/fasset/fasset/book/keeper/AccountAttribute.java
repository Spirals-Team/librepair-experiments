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
 * This enum is used to add additional features to an account which cannot be added
 * through the constructor
 */
public enum AccountAttribute {

    /**
     * The Account that maintains the status of balance for this account
     */
    CONTRA_ACCOUNT,

    /**
     * The type classification of the account on the balance sheet, which could be either
     * asset, liability or capital or equity
     */
    ACCOUNT_TYPE,

    /**
     * The subtype where one would wish to classify this account
     */
    ACCOUNT_SUB_TYPE,

    /**
     * An extra classification layer for the account should one be needed
     */
    ACCOUNT_SCHEME,

    /**
     * General ledger identification of the account should such a system exist with which
     * one would like to integrate this system
     */
    GENERAL_LEDGER,

    /**
     * Interest which is associated with this account if the same has such considerations according
     * to the application business domain
     */
    INTEREST_RATE,

    /**
     * Typical narration associated with this account
     */
    REMARKS,

    /**
     * Represents the Id of an organization's business unit
     */
    SERVICE_OUTLET,

    /**
     * For those service outlets that have internal divisions
     */
    SUB_SERVICE_OUTLET,

    /**
     * If the organization has operations in more than one region, and the operation managers feel like, they should
     * have accounts that can track financial movements down to the regional level this could be of help, but the word
     * region could mean many things depending on your english teacher, political inclinations and the list goes on,
     * therefore the book-keeper here does not dictate what you have to use this hierarchy for. A region could mean
     * a branch in the next block from head office, or some new operations you want to set up in North Korea, or even
     * a group of departments on the second floor
     */
    REGION,

    /**
     * See comments above
     */
    SUB_REGION,

    /**
     * Ain't gonna tell you what you can do with this, but for instance if you were tracking fixed assets
     * items with this account, this hierarchy, or nomenclature might come in handy for you. You could have
     * computers, Electronic Equipment, Real Estate etc. If you were tracking financial assets, you could have
     * it say, Loans and advances, Treasury Bonds, T - Bills, Deferred taxes (why not?) etc. But if it does not
     * apply to you business case, the book-keeper will not kill you for leaving this one out
     */
    CATEGORY,

    /**
     * See comments above
     */
    SUB_CATEGORY,

    /**
     * See comments on {@code Region}
     */
    LOCATION,

    /**
     * See comments on {@code Region}
     */
    SUB_LOCATION,

    /**
     * See comments on {@code Region}
     */
    DIVISION,

    /**
     * See comments on {@code Region}
     */
    SUB_DIVISION,
}
