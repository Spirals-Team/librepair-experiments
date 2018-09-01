/*
 * Copyright (C) 2015 Hannes Dorfmann
 * Copyright (C) 2015 Tickaroo, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.tickaroo.tikxml.annotationprocessing.element.polymorphism

import com.tickaroo.tikxml.annotation.PropertyElement
import com.tickaroo.tikxml.annotation.Xml

/**
 * @author Hannes Dorfmann
 */
@Xml
class Journalist : Writer() {

    @PropertyElement
    var age: Int = 0

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Journalist) return false
        if (!super.equals(other)) return false

        val that = other as Journalist?

        return age == that!!.age
    }

    override fun hashCode(): Int {
        var result = super.hashCode()
        result = 31 * result + age
        return result
    }
}
