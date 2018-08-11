/*
 * Copyright (C) 2010 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package twitter4j;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

// Note: this class was written without inspecting the non-free org.json sourcecode.

/**
 * A modifiable set of name/value mappings. Names are unique, non-null strings.
 * Values may be any mix of {@link JSONObject JSONObjects}, {@link JSONArray
 * JSONArrays}, Strings, Booleans, Integers, Longs, Doubles or {@link #NULL}.
 * Values may not be {@code null}, {@link Double#isNaN() NaNs}, {@link
 * Double#isInfinite() infinities}, or of any type not listed here.
 *
 * <p>This class can coerce values to another type when requested.
 * <ul>
 * <li>When the requested type is a boolean, strings will be coerced using a
 * case-insensitive comparison to "true" and "false".
 * <li>When the requested type is a double, other {@link Number} types will
 * be coerced using {@link Number#doubleValue() doubleValue}. Strings
 * that can be coerced using {@link Double#valueOf(String)} will be.
 * <li>When the requested type is an int, other {@link Number} types will
 * be coerced using {@link Number#intValue() intValue}. Strings
 * that can be coerced using {@link Double#valueOf(String)} will be,
 * and then cast to int.
 * <li><a name="lossy">When the requested type is a long, other {@link Number} types will
 * be coerced using {@link Number#longValue() longValue}. Strings
 * that can be coerced using {@link Double#valueOf(String)} will be,
 * and then cast to long. This two-step conversion is lossy for very
 * large values. For example, the string "9223372036854775806" yields the
 * long 9223372036854775807.</a>
 * <li>When the requested type is a String, other non-null values will be
 * coerced using {@link String#valueOf(Object)}. Although null cannot be
 * coerced, the sentinel value {@link JSONObject#NULL} is coerced to the
 * string "null".
 * </ul>
 *
 * <p>This class can look up both mandatory and optional values:
 * <ul>
 * <li>Use <code>get<i>Type</i>()</code> to retrieve a mandatory value. This
 * fails with a {@code JSONException} if the requested name has no value
 * or if the value cannot be coerced to the requested type.
 * <li>Use <code>opt<i>Type</i>()</code> to retrieve an optional value. This
 * returns a system- or user-supplied default if the requested name has no
 * value or if the value cannot be coerced to the requested type.
 * </ul>
 *
 * <p><strong>Warning:</strong> this class represents null in two incompatible
 * ways: the standard Java {@code null} reference, and the sentinel value {@link
 * JSONObject#NULL}. In particular, calling {@code put(name, null)} removes the
 * named entry from the object but {@code put(name, JSONObject.NULL)} stores an
 * entry whose value is {@code JSONObject.NULL}.
 *
 * <p>Instances of this class are not thread safe. Although this class is
 * nonfinal, it was not designed for inheritance and should not be subclassed.
 * In particular, self-use by overrideable methods is not specified. See
 * <i>Effective Java</i> Item 17, "Design and Document or inheritance or else
 * prohibit it" for further information.
 */
public class JSONObject {

    private static final Double NEGATIVE_ZERO = -0d;

    /**
     * A sentinel value used to explicitly define a name with no value. Unlike
     * {@code null}, names with this value:
     * <ul>
     * <li>show up in the {@link #names} array
     * <li>show up in the {@link #keys} iterator
     * <li>return {@code true} for {@link #has(String)}
     * <li>do not throw on {@link #get(String)}
     * <li>are included in the encoded JSON string.
     * </ul>
     *
     * <p>This value violates the general contract of {@link Object#equals} by
     * returning true when compared to {@code null}. Its {@link #toString}
     * method returns "null".
     */
    public static final Object NULL = new Object() {
        @SuppressWarnings("EqualsWhichDoesntCheckParameterClass")
        @Override
        public boolean equals(Object o) {
            return o == this || o == null; // API specifies this broken equals implementation
        }

        // at least make the broken equals(null) consistent with Objects.hashCode(null).
        @Override
        public int hashCode() {
            return Objects.hashCode(null);
        }

        @Override
        public String toString() {
            return "null";
        }
    };

    private final LinkedHashMap<String, Object> nameValuePairs;

    /**
     * Creates a {@code JSONObject} with no name/value mappings.
     */
    public JSONObject() {
        nameValuePairs = new LinkedHashMap<String, Object>();
    }

    /**
     * Creates a new {@code JSONObject} by copying all name/value mappings from
     * the given map.
     *
     * @param copyFrom a map whose keys are of type {@link String} and whose
     *                 values are of supported types.
     * @throws NullPointerException if any of the map's keys are null.
     */
    /* (accept a raw type for API compatibility) */
    public JSONObject(Map copyFrom) {
        this();
        Map<?, ?> contentsTyped = (Map<?, ?>) copyFrom;
        for (Map.Entry<?, ?> entry : contentsTyped.entrySet()) {
            /*
             * Deviate from the original by checking that keys are non-null and
             * of the proper type. (We still defer validating the values).
             */
            String key = (String) entry.getKey();
            if (key == null) {
                throw new NullPointerException("key == null");
            }
            nameValuePairs.put(key, wrap(entry.getValue()));
        }
    }

    /**
     * Creates a new {@code JSONObject} with name/value mappings from the next
     * object in the tokener.
     *
     * @param readFrom a tokener whose nextValue() method will yield a
     *                 {@code JSONObject}.
     * @throws JSONException if the parse fails or doesn't yield a
     *                       {@code JSONObject}.
     */
    public JSONObject(JSONTokener readFrom) throws JSONException {
        /*
         * Getting the parser to populate this could get tricky. Instead, just
         * parse to temporary JSONObject and then steal the data from that.
         */
        Object object = readFrom.nextValue();
        if (object instanceof JSONObject) {
            this.nameValuePairs = ((JSONObject) object).nameValuePairs;
        } else {
            throw JSON.typeMismatch(object, "JSONObject");
        }
    }

    /**
     * Creates a new {@code JSONObject} with name/value mappings from the JSON
     * string.
     *
     * @param json a JSON-encoded string containing an object.
     * @throws JSONException if the parse fails or doesn't yield a {@code
     *                       JSONObject}.
     */
    public JSONObject(String json) throws JSONException {
        this(new JSONTokener(json));
    }

    /**
     * Creates a new {@code JSONObject} by copying mappings for the listed names
     * from the given object. Names that aren't present in {@code copyFrom} will
     * be skipped.
     *
     * @param copyFrom The source object.
     * @param names    The names of the fields to copy.
     * @throws JSONException On internal errors. Shouldn't happen.
     */
    public JSONObject(JSONObject copyFrom, String[] names) throws JSONException {
        this();
        for (String name : names) {
            Object value = copyFrom.opt(name);
            if (value != null) {
                nameValuePairs.put(name, value);
            }
        }
    }

    /**
     * Returns the number of name/value mappings in this object.
     *
     * @return the length of this.
     */
    public int length() {
        return nameValuePairs.size();
    }

    /**
     * Maps {@code name} to {@code value}, clobbering any existing name/value
     * mapping with the same name.
     *
     * @param name  The name of the value to insert.
     * @param value The value to insert.
     * @return this object.
     * @throws JSONException Should not be possible.
     */
    public JSONObject put(String name, boolean value) throws JSONException {
        nameValuePairs.put(checkName(name), value);
        return this;
    }

    /**
     * Maps {@code name} to {@code value}, clobbering any existing name/value
     * mapping with the same name.
     *
     * @param name  The name for the new value.
     * @param value a finite value. May not be {@link Double#isNaN() NaNs} or
     *              {@link Double#isInfinite() infinities}.
     * @return this object.
     * @throws JSONException if value is NaN or infinite.
     */
    public JSONObject put(String name, double value) throws JSONException {
        nameValuePairs.put(checkName(name), JSON.checkDouble(value));
        return this;
    }

    /**
     * Maps {@code name} to {@code value}, clobbering any existing name/value
     * mapping with the same name.
     *
     * @param name  The name for the new value.
     * @param value The new value.
     * @return this object.
     * @throws JSONException Should not be possible.
     */
    public JSONObject put(String name, int value) throws JSONException {
        nameValuePairs.put(checkName(name), value);
        return this;
    }

    /**
     * Maps {@code name} to {@code value}, clobbering any existing name/value
     * mapping with the same name.
     *
     * @param name  The name of the new value.
     * @param value The new value to insert.
     * @return this object.
     * @throws JSONException Should not be possible.
     */
    public JSONObject put(String name, long value) throws JSONException {
        nameValuePairs.put(checkName(name), value);
        return this;
    }

    /**
     * Maps {@code name} to {@code value}, clobbering any existing name/value
     * mapping with the same name. If the value is {@code null}, any existing
     * mapping for {@code name} is removed.
     *
     * @param name  The name of the new value.
     * @param value a {@link JSONObject}, {@link JSONArray}, String, Boolean,
     *              Integer, Long, Double, {@link #NULL}, or {@code null}. May not be
     *              {@link Double#isNaN() NaNs} or {@link Double#isInfinite()
     *              infinities}.
     * @return this object.
     * @throws JSONException if the value is an invalid double (infinite or NaN).
     */
    public JSONObject put(String name, Object value) throws JSONException {
        if (value == null) {
            nameValuePairs.remove(name);
            return this;
        }
        if (value instanceof Number) {
            // deviate from the original by checking all Numbers, not just floats & doubles
            JSON.checkDouble(((Number) value).doubleValue());
        }
        nameValuePairs.put(checkName(name), value);
        return this;
    }

    /**
     * Equivalent to {@code put(name, value)} when both parameters are non-null;
     * does nothing otherwise.
     *
     * @param name  The name of the value to insert.
     * @param value The value to insert.
     * @return this object.
     * @throws JSONException if the value is an invalid double (infinite or NaN).
     */
    public JSONObject putOpt(String name, Object value) throws JSONException {
        if (name == null || value == null) {
            return this;
        }
        return put(name, value);
    }

    /**
     * Appends {@code value} to the array already mapped to {@code name}. If
     * this object has no mapping for {@code name}, this inserts a new mapping.
     * If the mapping exists but its value is not an array, the existing
     * and new values are inserted in order into a new array which is itself
     * mapped to {@code name}. In aggregate, this allows values to be added to a
     * mapping one at a time.
     *
     * Note that {@code append(String, Object)} provides better semantics.
     * In particular, the mapping for {@code name} will <b>always</b> be a
     * {@link JSONArray}. Using {@code accumulate} will result in either a
     * {@link JSONArray} or a mapping whose type is the type of {@code value}
     * depending on the number of calls to it.
     *
     * @param name  The name of the field to change.
     * @param value a {@link JSONObject}, {@link JSONArray}, String, Boolean,
     *              Integer, Long, Double, {@link #NULL} or null. May not be {@link
     *              Double#isNaN() NaNs} or {@link Double#isInfinite() infinities}.
     * @return this object after mutation.
     * @throws JSONException If the object being added is an invalid number.
     */
    // TODO: Change {@code append) to {@link #append} when append is
    // unhidden.
    public JSONObject accumulate(String name, Object value) throws JSONException {
        Object current = nameValuePairs.get(checkName(name));
        if (current == null) {
            return put(name, value);
        }

        if (current instanceof JSONArray) {
            JSONArray array = (JSONArray) current;
            array.checkedPut(value);
        } else {
            JSONArray array = new JSONArray();
            array.checkedPut(current);
            array.checkedPut(value);
            nameValuePairs.put(name, array);
        }
        return this;
    }

    /**
     * Appends values to the array mapped to {@code name}. A new {@link JSONArray}
     * mapping for {@code name} will be inserted if no mapping exists. If the existing
     * mapping for {@code name} is not a {@link JSONArray}, a {@link JSONException}
     * will be thrown.
     *
     * @param name  The name of the array to which the value should be appended.
     * @param value The value to append.
     * @return this object.
     * @throws JSONException if {@code name} is {@code null} or if the mapping for
     *                       {@code name} is non-null and is not a {@link JSONArray}.
     */
    public JSONObject append(String name, Object value) throws JSONException {
        Object current = nameValuePairs.get(checkName(name));

        final JSONArray array;
        if (current instanceof JSONArray) {
            array = (JSONArray) current;
        } else if (current == null) {
            JSONArray newArray = new JSONArray();
            nameValuePairs.put(name, newArray);
            array = newArray;
        } else {
            throw new JSONException("Key " + name + " is not a JSONArray");
        }

        array.checkedPut(value);

        return this;
    }

    String checkName(String name) throws JSONException {
        if (name == null) {
            throw new JSONException("Names must be non-null");
        }
        return name;
    }

    /**
     * Removes the named mapping if it exists; does nothing otherwise.
     *
     * @param name The name of the mapping to remove.
     * @return the value previously mapped by {@code name}, or null if there was
     * no such mapping.
     */
    public Object remove(String name) {
        return nameValuePairs.remove(name);
    }

    /**
     * Returns true if this object has no mapping for {@code name} or if it has
     * a mapping whose value is {@link #NULL}.
     *
     * @param name The name of the value to check on.
     * @return true if the field doesn't exist or is null.
     */
    public boolean isNull(String name) {
        Object value = nameValuePairs.get(name);
        return value == null || value == NULL;
    }

    /**
     * Returns true if this object has a mapping for {@code name}. The mapping
     * may be {@link #NULL}.
     *
     * @param name The name of the value to check on.
     * @return true if this object has a field named {@code name}
     */
    public boolean has(String name) {
        return nameValuePairs.containsKey(name);
    }

    /**
     * Returns the value mapped by {@code name}, or throws if no such mapping exists.
     *
     * @param name The name of the value to get.
     * @return The value.
     * @throws JSONException if no such mapping exists.
     */
    public Object get(String name) throws JSONException {
        Object result = nameValuePairs.get(name);
        if (result == null) {
            throw new JSONException("No value for " + name);
        }
        return result;
    }

    /**
     * Returns the value mapped by {@code name}, or null if no such mapping
     * exists.
     *
     * @param name The name of the value to get.
     * @return The value.
     */
    public Object opt(String name) {
        return nameValuePairs.get(name);
    }

    /**
     * Returns the value mapped by {@code name} if it exists and is a boolean or
     * can be coerced to a boolean, or throws otherwise.
     *
     * @param name The name of the field we want.
     * @return The selected value if it exists.
     * @throws JSONException if the mapping doesn't exist or cannot be coerced
     *                       to a boolean.
     */
    public boolean getBoolean(String name) throws JSONException {
        Object object = get(name);
        Boolean result = JSON.toBoolean(object);
        if (result == null) {
            throw JSON.typeMismatch(name, object, "boolean");
        }
        return result;
    }

    /**
     * Returns the value mapped by {@code name} if it exists and is a boolean or
     * can be coerced to a boolean, or false otherwise.
     *
     * @param name The name of the field we want.
     * @return The selected value if it exists.
     */
    public boolean optBoolean(String name) {
        return optBoolean(name, false);
    }

    /**
     * Returns the value mapped by {@code name} if it exists and is a boolean or
     * can be coerced to a boolean, or {@code fallback} otherwise.
     *
     * @param name     The name of the field we want.
     * @param fallback The value to return if the field isn't there.
     * @return The selected value or the fallback.
     */
    public boolean optBoolean(String name, boolean fallback) {
        Object object = opt(name);
        Boolean result = JSON.toBoolean(object);
        return result != null ? result : fallback;
    }

    /**
     * Returns the value mapped by {@code name} if it exists and is a double or
     * can be coerced to a double, or throws otherwise.
     *
     * @param name The name of the field we want.
     * @return The selected value if it exists.
     * @throws JSONException if the mapping doesn't exist or cannot be coerced
     *                       to a double.
     */
    public double getDouble(String name) throws JSONException {
        Object object = get(name);
        Double result = JSON.toDouble(object);
        if (result == null) {
            throw JSON.typeMismatch(name, object, "double");
        }
        return result;
    }

    /**
     * Returns the value mapped by {@code name} if it exists and is a double or
     * can be coerced to a double, or {@code NaN} otherwise.
     *
     * @param name The name of the field we want.
     * @return The selected value if it exists.
     */
    public double optDouble(String name) {
        return optDouble(name, Double.NaN);
    }

    /**
     * Returns the value mapped by {@code name} if it exists and is a double or
     * can be coerced to a double, or {@code fallback} otherwise.
     *
     * @param name     The name of the field we want.
     * @param fallback The value to return if the field isn't there.
     * @return The selected value or the fallback.
     */
    public double optDouble(String name, double fallback) {
        Object object = opt(name);
        Double result = JSON.toDouble(object);
        return result != null ? result : fallback;
    }

    /**
     * Returns the value mapped by {@code name} if it exists and is an int or
     * can be coerced to an int, or throws otherwise.
     *
     * @param name The name of the field we want.
     * @return The selected value if it exists.
     * @throws JSONException if the mapping doesn't exist or cannot be coerced
     *                       to an int.
     */
    public int getInt(String name) throws JSONException {
        Object object = get(name);
        Integer result = JSON.toInteger(object);
        if (result == null) {
            throw JSON.typeMismatch(name, object, "int");
        }
        return result;
    }

    /**
     * Returns the value mapped by {@code name} if it exists and is an int or
     * can be coerced to an int, or 0 otherwise.
     *
     * @param name The name of the field we want.
     * @return The selected value if it exists.
     */
    public int optInt(String name) {
        return optInt(name, 0);
    }

    /**
     * Returns the value mapped by {@code name} if it exists and is an int or
     * can be coerced to an int, or {@code fallback} otherwise.
     *
     * @param name     The name of the field we want.
     * @param fallback The value to return if the field isn't there.
     * @return The selected value or the fallback.
     */
    public int optInt(String name, int fallback) {
        Object object = opt(name);
        Integer result = JSON.toInteger(object);
        return result != null ? result : fallback;
    }

    /**
     * Returns the value mapped by {@code name} if it exists and is a long or
     * can be coerced to a long, or throws otherwise.
     * Note that JSON represents numbers as doubles,
     *
     * so this is <a href="#lossy">lossy</a>; use strings to transfer numbers
     * via JSON without loss.
     *
     * @param name The name of the field that we want.
     * @return The value of the field.
     * @throws JSONException if the mapping doesn't exist or cannot be coerced
     *                       to a long.
     */
    public long getLong(String name) throws JSONException {
        Object object = get(name);
        Long result = JSON.toLong(object);
        if (result == null) {
            throw JSON.typeMismatch(name, object, "long");
        }
        return result;
    }

    /**
     * Returns the value mapped by {@code name} if it exists and is a long or
     * can be coerced to a long, or 0 otherwise. Note that JSON represents numbers as doubles,
     * so this is <a href="#lossy">lossy</a>; use strings to transfer numbers via JSON.
     *
     * @param name The name of the field we want.
     * @return The selected value.
     */
    public long optLong(String name) {
        return optLong(name, 0L);
    }

    /**
     * Returns the value mapped by {@code name} if it exists and is a long or
     * can be coerced to a long, or {@code fallback} otherwise. Note that JSON represents
     * numbers as doubles, so this is <a href="#lossy">lossy</a>; use strings to transfer
     * numbers via JSON.
     *
     * @param name     The name of the field we want.
     * @param fallback The value to return if the field isn't there.
     * @return The selected value or the fallback.
     */
    public long optLong(String name, long fallback) {
        Object object = opt(name);
        Long result = JSON.toLong(object);
        return result != null ? result : fallback;
    }

    /**
     * Returns the value mapped by {@code name} if it exists, coercing it if
     * necessary, or throws if no such mapping exists.
     *
     * @param name The name of the field we want.
     * @return The value of the field.
     * @throws JSONException if no such mapping exists.
     */
    public String getString(String name) throws JSONException {
        Object object = get(name);
        String result = JSON.toString(object);
        if (result == null) {
            throw JSON.typeMismatch(name, object, "String");
        }
        return result;
    }

    /**
     * Returns the value mapped by {@code name} if it exists, coercing it if
     * necessary, or the empty string if no such mapping exists.
     *
     * @param name The name of the field we want.
     * @return The value of the field.
     */
    public String optString(String name) {
        return optString(name, "");
    }

    /**
     * Returns the value mapped by {@code name} if it exists, coercing it if
     * necessary, or {@code fallback} if no such mapping exists.
     *
     * @param name     The name of the field that we want.
     * @param fallback The value to return if the field doesn't exist.
     * @return The value of the field or fallback.
     */
    public String optString(String name, String fallback) {
        Object object = opt(name);
        String result = JSON.toString(object);
        return result != null ? result : fallback;
    }

    /**
     * Returns the value mapped by {@code name} if it exists and is a {@code
     * JSONArray}, or throws otherwise.
     *
     * @param name The field we want to get.
     * @return The value of the field (if it is a JSONArray.
     * @throws JSONException if the mapping doesn't exist or is not a {@code
     *                       JSONArray}.
     */
    public JSONArray getJSONArray(String name) throws JSONException {
        Object object = get(name);
        if (object instanceof JSONArray) {
            return (JSONArray) object;
        } else {
            throw JSON.typeMismatch(name, object, "JSONArray");
        }
    }

    /**
     * Returns the value mapped by {@code name} if it exists and is a {@code
     * JSONArray}, or null otherwise.
     *
     * @param name The name of the field we want.
     * @return The value of the specified field (assuming it is a JSNOArray
     */
    public JSONArray optJSONArray(String name) {
        Object object = opt(name);
        return object instanceof JSONArray ? (JSONArray) object : null;
    }

    /**
     * Returns the value mapped by {@code name} if it exists and is a {@code
     * JSONObject}, or throws otherwise.
     *
     * @param name The name of the field that we want.
     * @return a specified field value (if it is a JSONObject)
     * @throws JSONException if the mapping doesn't exist or is not a {@code
     *                       JSONObject}.
     */
    public JSONObject getJSONObject(String name) throws JSONException {
        Object object = get(name);
        if (object instanceof JSONObject) {
            return (JSONObject) object;
        } else {
            throw JSON.typeMismatch(name, object, "JSONObject");
        }
    }

    /**
     * Returns the value mapped by {@code name} if it exists and is a {@code
     * JSONObject}, or null otherwise.
     *
     * @param name The name of the value we want.
     * @return The specified value.
     */
    public JSONObject optJSONObject(String name) {
        Object object = opt(name);
        return object instanceof JSONObject ? (JSONObject) object : null;
    }

    /**
     * Returns an array with the values corresponding to {@code names}. The
     * array contains null for names that aren't mapped. This method returns
     * null if {@code names} is either null or empty.
     *
     * @param names The names of the fields that we want the values for.
     * @return The selected values.
     * @throws JSONException On internal errors. Shouldn't happen.
     */
    public JSONArray toJSONArray(JSONArray names) throws JSONException {
        JSONArray result = new JSONArray();
        if (names == null) {
            return null;
        }
        int length = names.length();
        if (length == 0) {
            return null;
        }
        for (int i = 0; i < length; i++) {
            String name = JSON.toString(names.opt(i));
            result.put(opt(name));
        }
        return result;
    }

    /**
     * Returns an iterator of the {@code String} names in this object. The
     * returned iterator supports {@link Iterator#remove() remove}, which will
     * remove the corresponding mapping from this object. If this object is
     * modified after the iterator is returned, the iterator's behavior is
     * undefined. The order of the keys is undefined.
     *
     * @return an iterator over the keys.
     */
    public Iterator<String> keys() {
        return nameValuePairs.keySet().iterator();
    }

    /**
     * Returns the set of {@code String} names in this object. The returned set
     * is a view of the keys in this object. {@link Set#remove(Object)} will remove
     * the corresponding mapping from this object and set iterator behaviour
     * is undefined if this object is modified after it is returned.
     *
     * See {@link #keys()}.
     *
     * @return The names in this object.
     */
    public Set<String> keySet() {
        return nameValuePairs.keySet();
    }

    /**
     * Returns an array containing the string names in this object. This method
     * returns null if this object contains no mappings.
     *
     * @return the names.
     */
    public JSONArray names() {
        return nameValuePairs.isEmpty()
                ? null
                : new JSONArray(new ArrayList<String>(nameValuePairs.keySet()));
    }

    /**
     * Encodes this object as a compact JSON string, such as:
     * <pre>{"query":"Pizza","locations":[94043,90210]}</pre>
     */
    @Override
    public String toString() {
        try {
            JSONStringer stringer = new JSONStringer();
            writeTo(stringer);
            return stringer.toString();
        } catch (JSONException e) {
            return null;
        }
    }

    /**
     * Encodes this object as a human readable JSON string for debugging, such
     * as:
     * <pre>
     * {
     *     "query": "Pizza",
     *     "locations": [
     *         94043,
     *         90210
     *     ]
     * }</pre>
     *
     * @param indentSpaces the number of spaces to indent for each level of
     *                     nesting.
     * @return The string containing the pretty form of this.
     * @throws JSONException On internal errors. Shouldn't happen.
     */
    public String toString(int indentSpaces) throws JSONException {
        JSONStringer stringer = new JSONStringer(indentSpaces);
        writeTo(stringer);
        return stringer.toString();
    }

    void writeTo(JSONStringer stringer) throws JSONException {
        stringer.object();
        for (Map.Entry<String, Object> entry : nameValuePairs.entrySet()) {
            stringer.key(entry.getKey()).value(entry.getValue());
        }
        stringer.endObject();
    }

    /**
     * Encodes the number as a JSON string.
     *
     * @param number a finite value. May not be {@link Double#isNaN() NaNs} or
     *               {@link Double#isInfinite() infinities}.
     * @return The encoded number in string form.
     * @throws JSONException On internal errors. Shouldn't happen.
     */
    public static String numberToString(Number number) throws JSONException {
        if (number == null) {
            throw new JSONException("Number must be non-null");
        }

        double doubleValue = number.doubleValue();
        JSON.checkDouble(doubleValue);

        // the original returns "-0" instead of "-0.0" for negative zero
        if (number.equals(NEGATIVE_ZERO)) {
            return "-0";
        }

        long longValue = number.longValue();
        if (doubleValue == (double) longValue) {
            return Long.toString(longValue);
        }

        return number.toString();
    }

    /**
     * Encodes {@code data} as a JSON string. This applies quotes and any
     * necessary character escaping.
     *
     * @param data the string to encode. Null will be interpreted as an empty
     *             string.
     * @return the quoted string.
     */
    public static String quote(String data) {
        if (data == null) {
            return "\"\"";
        }
        try {
            JSONStringer stringer = new JSONStringer();
            stringer.open(JSONStringer.Scope.NULL, "");
            stringer.value(data);
            stringer.close(JSONStringer.Scope.NULL, JSONStringer.Scope.NULL, "");
            return stringer.toString();
        } catch (JSONException e) {
            throw new AssertionError();
        }
    }

    /**
     * Wraps the given object if necessary.
     *
     * <p>If the object is null or , returns {@link #NULL}.
     * If the object is a {@code JSONArray} or {@code JSONObject}, no wrapping is necessary.
     * If the object is {@code NULL}, no wrapping is necessary.
     * If the object is an array or {@code Collection}, returns an equivalent {@code JSONArray}.
     * If the object is a {@code Map}, returns an equivalent {@code JSONObject}.
     * If the object is a primitive wrapper type or {@code String}, returns the object.
     * Otherwise if the object is from a {@code java} package, returns the result of {@code toString}.
     * If wrapping fails, returns null.
     *
     * @param o The object to wrap.
     * @return The wrapped (if necessary) form of the object {$code o}
     */
    public static Object wrap(Object o) {
        if (o == null) {
            return NULL;
        }
        if (o instanceof JSONArray || o instanceof JSONObject) {
            return o;
        }
        if (o.equals(NULL)) {
            return o;
        }
        try {
            if (o instanceof Collection) {
                return new JSONArray((Collection) o);
            } else if (o.getClass().isArray()) {
                return new JSONArray(o);
            }
            if (o instanceof Map) {
                return new JSONObject((Map) o);
            }
            if (o instanceof Boolean ||
                    o instanceof Byte ||
                    o instanceof Character ||
                    o instanceof Double ||
                    o instanceof Float ||
                    o instanceof Integer ||
                    o instanceof Long ||
                    o instanceof Short ||
                    o instanceof String) {
                return o;
            }
            if (o.getClass().getPackage().getName().startsWith("java.")) {
                return o.toString();
            }
        } catch (Exception ignored) {
        }
        return null;
    }
}
