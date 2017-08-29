/*******************************************************************************
 * Copyright (c) 2015 Eclipse RDF4J contributors, Aduna, and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Distribution License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *******************************************************************************/
package org.eclipse.rdf4j.model.base;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Abstract test class for {@link Set} methods and contracts.
 * <p>
 * Since {@link Set} doesn't stipulate much new behavior that isn't already found in {@link Collection}, this
 * class basically just adds tests for {@link Set#equals} and {@link Set#hashCode()} along with an updated
 * {@link #verify()} that ensures elements do not appear more than once in the set.
 * <p>
 * To use, subclass and override the {@link #makeEmptySet()} method. You may have to override other protected
 * methods if your set is not modifiable, or if your set restricts what kinds of elements may be added; see
 * {@link AbstractTestCollection} for more details.
 *
 * @version $Revision: 646780 $ $Date: 2008-04-10 13:48:07 +0100 (Thu, 10 Apr 2008) $
 * @author Paul Jack
 */
public abstract class AbstractTestSet extends AbstractTestCollection {

	/**
	 * JUnit constructor.
	 *
	 * @param name
	 *        name for test
	 */
	public AbstractTestSet(String name) {
		super(name);
	}

	//-----------------------------------------------------------------------
	/**
	 * Provides additional verifications for sets.
	 */
	public void verify() {
		super.verify();

		assertEquals("Sets should be equal", confirmed, collection);
		assertEquals("Sets should have equal hashCodes", confirmed.hashCode(), collection.hashCode());
		Collection set = makeConfirmedCollection();
		Iterator iterator = collection.iterator();
		while (iterator.hasNext()) {
			assertTrue("Set.iterator should only return unique elements", set.add(iterator.next()));
		}
	}

	//-----------------------------------------------------------------------
	/**
	 * Set equals method is defined.
	 */
	public boolean isEqualsCheckable() {
		return true;
	}

	/**
	 * Returns an empty Set for use in modification testing.
	 *
	 * @return a confirmed empty collection
	 */
	public Collection makeConfirmedCollection() {
		return new HashSet();
	}

	/**
	 * Returns a full Set for use in modification testing.
	 *
	 * @return a confirmed full collection
	 */
	public Collection makeConfirmedFullCollection() {
		Collection set = makeConfirmedCollection();
		set.addAll(Arrays.asList(getFullElements()));
		return set;
	}

	/**
	 * Makes an empty set. The returned set should have no elements.
	 *
	 * @return an empty set
	 */
	public abstract Set makeEmptySet();

	/**
	 * Makes a full set by first creating an empty set and then adding all the elements returned by
	 * {@link #getFullElements()}. Override if your set does not support the add operation.
	 *
	 * @return a full set
	 */
	public Set makeFullSet() {
		Set set = makeEmptySet();
		set.addAll(Arrays.asList(getFullElements()));
		return set;
	}

	/**
	 * Makes an empty collection by invoking {@link #makeEmptySet()}.
	 *
	 * @return an empty collection
	 */
	public final Collection makeCollection() {
		return makeEmptySet();
	}

	/**
	 * Makes a full collection by invoking {@link #makeFullSet()}.
	 *
	 * @return a full collection
	 */
	public final Collection makeFullCollection() {
		return makeFullSet();
	}

	//-----------------------------------------------------------------------
	/**
	 * Return the {@link AbstractTestCollection#collection} fixture, but cast as a Set.
	 */
	public Set getSet() {
		return (Set)collection;
	}

	/**
	 * Return the {@link AbstractTestCollection#confirmed} fixture, but cast as a Set.
	 */
	public Set getConfirmedSet() {
		return (Set)confirmed;
	}

	//-----------------------------------------------------------------------
	/**
	 * Tests {@link Set#equals(Object)}.
	 */
	public void testSetEquals() {
		resetEmpty();
		assertEquals("Empty sets should be equal", getSet(), getConfirmedSet());
		verify();

		Collection set2 = makeConfirmedCollection();
		set2.add("foo");
		assertTrue("Empty set shouldn't equal nonempty set", !getSet().equals(set2));

		resetFull();
		assertEquals("Full sets should be equal", getSet(), getConfirmedSet());
		verify();

		set2.clear();
		set2.addAll(Arrays.asList(getOtherElements()));
		assertTrue("Sets with different contents shouldn't be equal", !getSet().equals(set2));
	}

	/**
	 * Tests {@link Set#hashCode()}.
	 */
	public void testSetHashCode() {
		resetEmpty();
		assertEquals("Empty sets have equal hashCodes", getSet().hashCode(), getConfirmedSet().hashCode());

		resetFull();
		assertEquals("Equal sets have equal hashCodes", getSet().hashCode(), getConfirmedSet().hashCode());
	}

}
