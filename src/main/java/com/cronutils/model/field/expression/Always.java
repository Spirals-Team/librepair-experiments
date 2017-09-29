package com.cronutils.model.field.expression;

import com.google.common.base.MoreObjects;

/*
 * Copyright 2014 jmrozanec
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/**
 * Represents a star (*) value on cron expression field
 */
public class Always extends FieldExpression {

    static final Always INSTANCE = new Always();

    /**
     * Should be package private and not be instantiated elsewhere. Class should become package private too.
     * @deprecated rather use {@link FieldExpression#always()}
     */
    @Deprecated
	public Always(){}
    

	@Override
	public String asString() {
		return "*";
	}
	
	 
    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this).toString();
    }
}
