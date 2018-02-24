/*  Copyright (C) 2016  Nicholas Wright
    
    This file is part of similarImage - A similar image finder using pHash
    
    similarImage is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.github.dozedoff.similarImage.io;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.management.InvalidAttributeValueException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Read and write hash values as extended attributes. Does minimal validation.
 * 
 * @author Nicholas Wright
 *
 */
public class HashAttribute {
	private static final Logger LOGGER = LoggerFactory.getLogger(HashAttribute.class);

	private static final long TIMESTAMP_TOLERANCE = 10;
	private static final int HEXADECIMAL_RADIX = 16;

	private final String hashFQN;
	private final String timestampFQN;
	private final String hashName;
	private final String corruptNameFQN;

	/**
	 * Create a class that can be used to read and write hashes as extended attributes.
	 * 
	 * @param hashName
	 *            name used to identify the hash
	 */
	public HashAttribute(String hashName) {
		this.hashName = hashName;
		hashFQN = ExtendedAttribute.createName("hash", hashName);
		timestampFQN = ExtendedAttribute.createName("timestamp", hashName);
		corruptNameFQN = ExtendedAttribute.createName("corrupt");
	}

	/**
	 * Checks that the file has a hash and that the timestamp still matches the one recorded with the hash.
	 * 
	 * @param path
	 *            of the file to check
	 * @return true if a hash is found and the timestamp matches
	 */
	public boolean areAttributesValid(Path path) {
		try {
			return ExtendedAttribute.isExtendedAttributeSet(path, hashFQN) && verifyTimestamp(path);
		} catch (IOException e) {
			LOGGER.error("Failed to check hash for {} ({})", path, e.toString());
		}

		return false;
	}

	private boolean verifyTimestamp(Path path) throws IOException {
		if (!ExtendedAttribute.isExtendedAttributeSet(path, timestampFQN)) {
			LOGGER.error("{} does not have a timestamp for hash {}", path, hashName);
			return false;
		}

		long fileModifiedTime = Files.getLastModifiedTime(path).toMillis();
		long storedTimestamp = Long.parseUnsignedLong(ExtendedAttribute.readExtendedAttributeAsString(path, timestampFQN));

		if (storedTimestamp > fileModifiedTime + TIMESTAMP_TOLERANCE) {
			LOGGER.warn("The file modification time of {} is newer than the Timestamp for {}", path, hashName);
		}

		if (storedTimestamp < fileModifiedTime - TIMESTAMP_TOLERANCE) {
			LOGGER.warn("{} has been modified since the hash for {} was recorded", path, hashName);
		}

		return Math.abs(fileModifiedTime - storedTimestamp) <= TIMESTAMP_TOLERANCE;
	}

	/**
	 * Read the hash stored for the file.
	 * 
	 * @param path
	 *            to the file to read from
	 * @return the hash for the file
	 * @throws InvalidAttributeValueException
	 *             if the hash and/or timestamp are not set or invalid
	 * @throws IOException
	 *             if a error occurred while reading the file
	 */
	public long readHash(Path path) throws InvalidAttributeValueException, IOException {
		if (!areAttributesValid(path)) {
			throw new InvalidAttributeValueException("The required attributes are not set or invalid");
		}

		String encodedHash = ExtendedAttribute.readExtendedAttributeAsString(path, hashFQN);
		return Long.parseUnsignedLong(encodedHash, HEXADECIMAL_RADIX);
	}

	// TODO throw exception instead of logging
	/**
	 * Write the hash value and the modified time of the file as extended attributes.
	 * 
	 * @param path
	 *            of the file to write to
	 * @param hash
	 *            associated with the file
	 */
	public void writeHash(Path path, long hash) {
		try {
			ExtendedAttribute.setExtendedAttribute(path, hashFQN, Long.toHexString(hash));
			ExtendedAttribute.setExtendedAttribute(path, timestampFQN, Long.toString(Files.getLastModifiedTime(path).toMillis()));

		} catch (IOException e) {
			LOGGER.warn("Failed to write hash to file {} ({})", path, e.toString());
		}
	}

	/**
	 * Mark the file as corrupted by creating a extended attribute name.
	 * 
	 * @param path
	 *            to mark
	 * @throws IOException
	 *             if there is an error accessing the file
	 */
	public void markCorrupted(Path path) throws IOException {
		ExtendedAttribute.setExtendedAttribute(path, corruptNameFQN, "");
	}

	/**
	 * Check to see if a file is corrupted by looking for a extended attribute name.
	 * 
	 * @param path
	 *            to check
	 * @return true if the file has been marked
	 * @throws IOException
	 */
	public boolean isCorrupted(Path path) throws IOException {
		return ExtendedAttribute.isExtendedAttributeSet(path, corruptNameFQN);
	}

	/**
	 * Get the extended attribute name for the hash.
	 * 
	 * @return the FQN of the hash
	 */
	public String getHashFQN() {
		return hashFQN;
	}

	/**
	 * Get the extended attribute name for the timestamp of this hash.
	 * 
	 * @return the FQN of the timestamp
	 */
	public String getTimestampFQN() {
		return timestampFQN;
	}

	/**
	 * Get the extended attribute name for the corrupt marker.
	 * 
	 * @return the FQN of the corrupt marker
	 */
	public String getCorruptNameFQN() {
		return corruptNameFQN;
	}
}
