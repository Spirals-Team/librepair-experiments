/**
 * Copyright 2005-2016 hdiv.org
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * 	http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.hdiv.services;

public enum ProtectionType {
	READONLY("INTEGRITY"), NONEDITABLE("INTEGRITY"), ENTITY("INTEGRITY"), WHITELIST("WHITELIST"), BLACKLIST("BLACKLIST");

	String category;

	boolean editable;

	ProtectionType(final String category) {
		this.category = category;
		editable = !category.equals("INTEGRITY");
	}

	public String getCategory() {
		return category;
	}

	public boolean isEditable() {
		return editable;
	}
}
