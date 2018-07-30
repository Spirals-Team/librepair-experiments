/*
 * Hibernate Search, full-text search for your domain model
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later
 * See the lgpl.txt file in the root directory or <http://www.gnu.org/licenses/lgpl-2.1.html>.
 */
package org.hibernate.search.integrationtest.showcase.library.model;

import javax.persistence.Entity;

import org.hibernate.search.entity.pojo.mapping.definition.annotation.Field;

/**
 * A concrete copy of a book document.
 *
 * @see DocumentCopy
 */
@Entity
public class BookCopy extends DocumentCopy<Book> {

	@Field
	// TODO facet
	private BookMedium medium;

	public BookMedium getMedium() {
		return medium;
	}

	public void setMedium(BookMedium medium) {
		this.medium = medium;
	}
}
