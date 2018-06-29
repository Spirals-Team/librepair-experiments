/*
 * Copyright (c) 2010, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0, which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * This Source Code may also be made available under the following Secondary
 * Licenses when the conditions for such availability set forth in the
 * Eclipse Public License v. 2.0 are satisfied: GNU General Public License,
 * version 2 with the GNU Classpath Exception, which is available at
 * https://www.gnu.org/software/classpath/license.html.
 *
 * SPDX-License-Identifier: EPL-2.0 OR GPL-2.0 WITH Classpath-exception-2.0
 */

package org.glassfish.jersey.uri;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLDecoder;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.PathSegment;

import org.glassfish.jersey.internal.LocalizationMessages;
import org.glassfish.jersey.internal.util.collection.MultivaluedStringMap;

/**
 * Utility class for validating, encoding and decoding components
 * of a URI.
 *
 * @author Paul Sandoz
 * @author Marek Potociar (marek.potociar at oracle.com)
 */
public class UriComponent {

    // TODO rewrite to use masks and not lookup tables

    /**
     * The URI component type.
     */
    public enum Type {

        /**
         * ALPHA / DIGIT / "-" / "." / "_" / "~" characters.
         */
        UNRESERVED,
        /**
         * The URI scheme component type.
         */
        SCHEME,
        /**
         * The URI authority component type.
         */
        AUTHORITY,
        /**
         * The URI user info component type.
         */
        USER_INFO,
        /**
         * The URI host component type.
         */
        HOST,
        /**
         * The URI port component type.
         */
        PORT,
        /**
         * The URI path component type.
         */
        PATH,
        /**
         * The URI path component type that is a path segment.
         */
        PATH_SEGMENT,
        /**
         * The URI path component type that is a matrix parameter.
         */
        MATRIX_PARAM,
        /**
         * The URI query component type, encoded using application/x-www-form-urlencoded rules.
         */
        QUERY,
        /**
         * The URI query component type that is a query parameter, encoded using
         * application/x-www-form-urlencoded rules (space character is encoded
         * as {@code +}).
         */
        QUERY_PARAM,
        /**
         * The URI query component type that is a query parameter, encoded using
         * application/x-www-form-urlencoded (space character is encoded as
         * {@code %20}).
         */
        QUERY_PARAM_SPACE_ENCODED,
        /**
         * The URI fragment component type.
         */
        FRAGMENT,
    }

    private UriComponent() {
    }

    /**
     * Validates the legal characters of a percent-encoded string that
     * represents a URI component type.
     *
     * @param s the encoded string.
     * @param t the URI component type identifying the legal characters.
     * @throws IllegalArgumentException if the encoded string contains illegal
     * characters.
     */
    public static void validate(final String s, final Type t) {
        validate(s, t, false);
    }

    /**
     * Validates the legal characters of a percent-encoded string that
     * represents a URI component type.
     *
     * @param s the encoded string.
     * @param t the URI component type identifying the legal characters.
     * @param template true if the encoded string contains URI template variables
     * @throws IllegalArgumentException if the encoded string contains illegal
     * characters.
     */
    public static void validate(final String s, final Type t, final boolean template) {
        final int i = _valid(s, t, template);
        if (i > -1) {
            throw new IllegalArgumentException(LocalizationMessages.URI_COMPONENT_INVALID_CHARACTER(s, t, s.charAt(i), i));
        }
    }

    /**
     * Validates the legal characters of a percent-encoded string that
     * represents a URI component type.
     *
     * @param s the encoded string.
     * @param t the URI component type identifying the legal characters.
     * @return true if the encoded string is valid, otherwise false.
     */
    public static boolean valid(final String s, final Type t) {
        return valid(s, t, false);
    }

    /**
     * Validates the legal characters of a percent-encoded string that
     * represents a URI component type.
     *
     * @param s the encoded string.
     * @param t the URI component type identifying the legal characters.
     * @param template true if the encoded string contains URI template variables
     * @return true if the encoded string is valid, otherwise false.
     */
    public static boolean valid(final String s, final Type t, final boolean template) {
        return _valid(s, t, template) == -1;
    }

    private static int _valid(final String s, final Type t, final boolean template) {
        final boolean[] table = ENCODING_TABLES[t.ordinal()];

        for (int i = 0; i < s.length(); i++) {
            final char c = s.charAt(i);
            if ((c < 0x80 && c != '%' && !table[c]) || c >= 0x80) {
                if (!template || (c != '{' && c != '}')) {
                    return i;
                }
            }
        }
        return -1;
    }

    /**
     * Contextually encodes the characters of string that are either non-ASCII
     * characters or are ASCII characters that must be percent-encoded using the
     * UTF-8 encoding. Percent-encoded characters will be recognized and not
     * double encoded.
     *
     * @param s the string to be encoded.
     * @param t the URI component type identifying the ASCII characters that
     * must be percent-encoded.
     * @return the encoded string.
     */
    public static String contextualEncode(final String s, final Type t) {
        return _encode(s, t, false, true);
    }

    /**
     * Contextually encodes the characters of string that are either non-ASCII
     * characters or are ASCII characters that must be percent-encoded using the
     * UTF-8 encoding. Percent-encoded characters will be recognized and not
     * double encoded.
     *
     * @param s the string to be encoded.
     * @param t the URI component type identifying the ASCII characters that
     * must be percent-encoded.
     * @param template true if the encoded string contains URI template variables
     * @return the encoded string.
     */
    public static String contextualEncode(final String s, final Type t, final boolean template) {
        return _encode(s, t, template, true);
    }

    /**
     * Encodes the characters of string that are either non-ASCII characters
     * or are ASCII characters that must be percent-encoded using the
     * UTF-8 encoding.
     *
     * @param s the string to be encoded.
     * @param t the URI component type identifying the ASCII characters that
     * must be percent-encoded.
     * @return the encoded string.
     */
    public static String encode(final String s, final Type t) {
        return _encode(s, t, false, false);
    }

    /**
     * Encodes the characters of string that are either non-ASCII characters
     * or are ASCII characters that must be percent-encoded using the
     * UTF-8 encoding.
     *
     * @param s the string to be encoded.
     * @param t the URI component type identifying the ASCII characters that
     * must be percent-encoded.
     * @param template true if the encoded string contains URI template variables
     * @return the encoded string.
     */
    public static String encode(final String s, final Type t, final boolean template) {
        return _encode(s, t, template, false);
    }

    /**
     * Encodes a string with template parameters names present, specifically the
     * characters '{' and '}' will be percent-encoded.
     *
     * @param s the string with zero or more template parameters names
     * @return the string with encoded template parameters names.
     */
    public static String encodeTemplateNames(String s) {
        int i = s.indexOf('{');
        if (i != -1) {
            s = s.replace("{", "%7B");
        }
        i = s.indexOf('}');
        if (i != -1) {
            s = s.replace("}", "%7D");
        }

        return s;
    }

    private static String _encode(final String s, final Type t, final boolean template, final boolean contextualEncode) {
        final boolean[] table = ENCODING_TABLES[t.ordinal()];
        boolean insideTemplateParam = false;

        StringBuilder sb = null;
        for (int offset = 0, codePoint; offset < s.length(); offset += Character.charCount(codePoint)) {
            codePoint = s.codePointAt(offset);

            if (codePoint < 0x80 && table[codePoint]) {
                if (sb != null) {
                    sb.append((char) codePoint);
                }
            } else {
                if (template) {
                    boolean leavingTemplateParam = false;
                    if (codePoint == '{') {
                        insideTemplateParam = true;
                    } else if (codePoint == '}') {
                        insideTemplateParam = false;
                        leavingTemplateParam = true;
                    }
                    if (insideTemplateParam || leavingTemplateParam) {
                        if (sb != null) {
                            sb.append(Character.toChars(codePoint));
                        }
                        continue;
                    }
                }

                if (contextualEncode
                        && codePoint == '%'
                        && offset + 2 < s.length()
                        && isHexCharacter(s.charAt(offset + 1))
                        && isHexCharacter(s.charAt(offset + 2))) {
                    if (sb != null) {
                        sb.append('%').append(s.charAt(offset + 1)).append(s.charAt(offset + 2));
                    }
                    offset += 2;
                    continue;
                }

                if (sb == null) {
                    sb = new StringBuilder();
                    sb.append(s.substring(0, offset));
                }

                if (codePoint < 0x80) {
                    if (codePoint == ' ' && (t == Type.QUERY_PARAM)) {
                        sb.append('+');
                    } else {
                        appendPercentEncodedOctet(sb, (char) codePoint);
                    }
                } else {
                    appendUTF8EncodedCharacter(sb, codePoint);
                }
            }
        }

        return (sb == null) ? s : sb.toString();
    }

    private static final char[] HEX_DIGITS = {
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'
    };

    private static void appendPercentEncodedOctet(final StringBuilder sb, final int b) {
        sb.append('%');
        sb.append(HEX_DIGITS[b >> 4]);
        sb.append(HEX_DIGITS[b & 0x0F]);
    }

    private static void appendUTF8EncodedCharacter(final StringBuilder sb, final int codePoint) {
        final CharBuffer chars = CharBuffer.wrap(Character.toChars(codePoint));
        final ByteBuffer bytes = UTF_8_CHARSET.encode(chars);

        while (bytes.hasRemaining()) {
            appendPercentEncodedOctet(sb, bytes.get() & 0xFF);
        }
    }

    private static final String[] SCHEME = {"0-9", "A-Z", "a-z", "+", "-", "."};
    private static final String[] UNRESERVED = {"0-9", "A-Z", "a-z", "-", ".", "_", "~"};
    private static final String[] SUB_DELIMS = {"!", "$", "&", "'", "(", ")", "*", "+", ",", ";", "="};
    private static final boolean[][] ENCODING_TABLES = initEncodingTables();

    private static boolean[][] initEncodingTables() {
        final boolean[][] tables = new boolean[Type.values().length][];

        final List<String> l = new ArrayList<String>();
        l.addAll(Arrays.asList(SCHEME));
        tables[Type.SCHEME.ordinal()] = initEncodingTable(l);

        l.clear();

        l.addAll(Arrays.asList(UNRESERVED));
        tables[Type.UNRESERVED.ordinal()] = initEncodingTable(l);

        l.addAll(Arrays.asList(SUB_DELIMS));

        tables[Type.HOST.ordinal()] = initEncodingTable(l);

        tables[Type.PORT.ordinal()] = initEncodingTable(Arrays.asList("0-9"));

        l.add(":");

        tables[Type.USER_INFO.ordinal()] = initEncodingTable(l);

        l.add("@");

        tables[Type.AUTHORITY.ordinal()] = initEncodingTable(l);

        tables[Type.PATH_SEGMENT.ordinal()] = initEncodingTable(l);
        tables[Type.PATH_SEGMENT.ordinal()][';'] = false;

        tables[Type.MATRIX_PARAM.ordinal()] = tables[Type.PATH_SEGMENT.ordinal()].clone();
        tables[Type.MATRIX_PARAM.ordinal()]['='] = false;

        l.add("/");

        tables[Type.PATH.ordinal()] = initEncodingTable(l);

        tables[Type.QUERY.ordinal()] = initEncodingTable(l);
        tables[Type.QUERY.ordinal()]['!'] = false;
        tables[Type.QUERY.ordinal()]['*'] = false;
        tables[Type.QUERY.ordinal()]['\''] = false;
        tables[Type.QUERY.ordinal()]['('] = false;
        tables[Type.QUERY.ordinal()][')'] = false;
        tables[Type.QUERY.ordinal()][';'] = false;
        tables[Type.QUERY.ordinal()][':'] = false;
        tables[Type.QUERY.ordinal()]['@'] = false;
        tables[Type.QUERY.ordinal()]['$'] = false;
        tables[Type.QUERY.ordinal()][','] = false;
        tables[Type.QUERY.ordinal()]['/'] = false;
        tables[Type.QUERY.ordinal()]['?'] = false;

        tables[Type.QUERY_PARAM.ordinal()] = Arrays.copyOf(
                tables[Type.QUERY.ordinal()],
                tables[Type.QUERY.ordinal()].length);
        tables[Type.QUERY_PARAM.ordinal()]['='] = false;
        tables[Type.QUERY_PARAM.ordinal()]['+'] = false;
        tables[Type.QUERY_PARAM.ordinal()]['&'] = false;

        tables[Type.QUERY_PARAM_SPACE_ENCODED.ordinal()] = tables[Type.QUERY_PARAM.ordinal()];

        tables[Type.FRAGMENT.ordinal()] = tables[Type.QUERY.ordinal()];

        return tables;
    }

    private static boolean[] initEncodingTable(final List<String> allowed) {
        final boolean[] table = new boolean[0x80];
        for (final String range : allowed) {
            if (range.length() == 1) {
                table[range.charAt(0)] = true;
            } else if (range.length() == 3 && range.charAt(1) == '-') {
                for (int i = range.charAt(0); i <= range.charAt(2); i++) {
                    table[i] = true;
                }
            }
        }

        return table;
    }

    private static final Charset UTF_8_CHARSET = Charset.forName("UTF-8");

    /**
     * Decodes characters of a string that are percent-encoded octets using
     * UTF-8 decoding (if needed).
     * <p/>
     * It is assumed that the string is valid according to an (unspecified) URI
     * component type. If a sequence of contiguous percent-encoded octets is
     * not a valid UTF-8 character then the octets are replaced with '\uFFFD'.
     * <p/>
     * If the URI component is of type HOST then any "%" found between "[]" is
     * left alone. It is an IPv6 literal with a scope_id.
     * <p/>
     * If the URI component is of type QUERY_PARAM then any "+" is decoded as
     * as ' '.
     * <p/>
     *
     * @param s the string to be decoded.
     * @param t the URI component type, may be null.
     * @return the decoded string.
     * @throws IllegalArgumentException if a malformed percent-encoded octet is
     * detected
     */
    public static String decode(final String s, final Type t) {
        if (s == null) {
            throw new IllegalArgumentException();
        }

        final int n = s.length();
        if (n == 0) {
            return s;
        }

        // If there are no percent-escaped octets
        if (s.indexOf('%') < 0) {
            // If there are no '+' characters for query param
            if (t == Type.QUERY_PARAM) {
                if (s.indexOf('+') < 0) {
                    return s;
                }
            } else {
                return s;
            }
        } else {
            // Malformed percent-escaped octet at the end
            if (n < 2) {
                throw new IllegalArgumentException(LocalizationMessages.URI_COMPONENT_ENCODED_OCTET_MALFORMED(1));
            }

            // Malformed percent-escaped octet at the end
            if (s.charAt(n - 2) == '%') {
                throw new IllegalArgumentException(LocalizationMessages.URI_COMPONENT_ENCODED_OCTET_MALFORMED(n - 2));
            }
        }

        if (t == null) {
            return decode(s, n);
        }

        switch (t) {
            case HOST:
                return decodeHost(s, n);
            case QUERY_PARAM:
                return decodeQueryParam(s, n);
            default:
                return decode(s, n);
        }
    }

    /**
     * Decode the query component of a URI.
     * <p>
     * Query parameter names in the returned map are always decoded. Decoding of query parameter
     * values can be controlled using the {@code decode} parameter flag.
     * </p>
     *
     * @param u the URI.
     * @param decode {@code true} if the returned query parameter values of the query component
     * should be in decoded form.
     * @return the multivalued map of query parameters.
     */
    public static MultivaluedMap<String, String> decodeQuery(final URI u, final boolean decode) {
        return decodeQuery(u.getRawQuery(), decode);
    }

    /**
     * Decode the query component of a URI.
     * <p>
     * Query parameter names in the returned map are always decoded. Decoding of query parameter
     * values can be controlled using the {@code decode} parameter flag.
     * </p>
     *
     * @param q the query component in encoded form.
     * @param decode {@code true} if the returned query parameter values of the query component
     * should be in decoded form.
     * @return the multivalued map of query parameters.
     */
    public static MultivaluedMap<String, String> decodeQuery(final String q, final boolean decode) {
        return decodeQuery(q, true, decode);
    }

    /**
     * Decode the query component of a URI.
     * <p>
     * Decoding of query parameter names and values can be controlled using the {@code decodeNames}
     * and {@code decodeValues} parameter flags.
     * </p>
     *
     * @param q the query component in encoded form.
     * @param decodeNames {@code true} if the returned query parameter names of the query component
     * should be in decoded form.
     * @param decodeValues {@code true} if the returned query parameter values of the query component
     * should be in decoded form.
     * @return the multivalued map of query parameters.
     */
    public static MultivaluedMap<String, String> decodeQuery(final String q, final boolean decodeNames,
                                                             final boolean decodeValues) {
        final MultivaluedMap<String, String> queryParameters = new MultivaluedStringMap();

        if (q == null || q.length() == 0) {
            return queryParameters;
        }

        int s = 0;
        do {
            final int e = q.indexOf('&', s);

            if (e == -1) {
                decodeQueryParam(queryParameters, q.substring(s), decodeNames, decodeValues);
            } else if (e > s) {
                decodeQueryParam(queryParameters, q.substring(s, e), decodeNames, decodeValues);
            }
            s = e + 1;
        } while (s > 0 && s < q.length());

        return queryParameters;
    }

    @SuppressWarnings("StatementWithEmptyBody")
    private static void decodeQueryParam(final MultivaluedMap<String, String> params, final String param,
                                         final boolean decodeNames, final boolean decodeValues) {
        try {
            final int equals = param.indexOf('=');
            if (equals > 0) {
                params.add((decodeNames) ? URLDecoder.decode(param.substring(0, equals), "UTF-8") : param.substring(0, equals),
                        (decodeValues) ? URLDecoder.decode(param.substring(equals + 1), "UTF-8") : param.substring(equals + 1));
            } else if (equals == 0) {
                // no key declared, ignore
            } else if (param.length() > 0) {
                params.add((decodeNames) ? URLDecoder.decode(param, "UTF-8") : param, "");
            }
        } catch (final UnsupportedEncodingException ex) {
            // This should never occur
            throw new IllegalArgumentException(ex);
        }
    }

    private static final class PathSegmentImpl implements PathSegment {

        private static final PathSegment EMPTY_PATH_SEGMENT = new PathSegmentImpl("", false);
        private final String path;
        private final MultivaluedMap<String, String> matrixParameters;

        PathSegmentImpl(final String path, final boolean decode) {
            this(path, decode, new MultivaluedStringMap());
        }

        PathSegmentImpl(final String path, final boolean decode, final MultivaluedMap<String, String> matrixParameters) {
            this.path = (decode) ? UriComponent.decode(path, UriComponent.Type.PATH_SEGMENT) : path;
            this.matrixParameters = matrixParameters;
        }

        @Override
        public String getPath() {
            return path;
        }

        @Override
        public MultivaluedMap<String, String> getMatrixParameters() {
            return matrixParameters;
        }

        @Override
        public String toString() {
            return path;
        }
    }

    /**
     * Decode the path component of a URI as path segments.
     *
     * @param u the URI. If the path component is an absolute path component
     * then the leading '/' is ignored and is not considered a delimiator
     * of a path segment.
     * @param decode true if the path segments of the path component
     * should be in decoded form.
     * @return the list of path segments.
     */
    public static List<PathSegment> decodePath(final URI u, final boolean decode) {
        String rawPath = u.getRawPath();
        if (rawPath != null && rawPath.length() > 0 && rawPath.charAt(0) == '/') {
            rawPath = rawPath.substring(1);
        }
        return decodePath(rawPath, decode);
    }

    /**
     * Decode the path component of a URI as path segments.
     * <p>
     * Any '/' character in the path is considered to be a deliminator
     * between two path segments. Thus if the path is '/' then the path segment
     * list will contain two empty path segments. If the path is "//" then
     * the path segment list will contain three empty path segments. If the path
     * is "/a/" the path segment list will consist of the following path
     * segments in order: "", "a" and "".
     * </p>
     *
     * @param path the path component in encoded form.
     * @param decode true if the path segments of the path component
     * should be in decoded form.
     * @return the list of path segments.
     */
    public static List<PathSegment> decodePath(final String path, final boolean decode) {
        final List<PathSegment> segments = new LinkedList<PathSegment>();

        if (path == null) {
            return segments;
        }

        int s;
        int e = -1;
        do {
            s = e + 1;
            e = path.indexOf('/', s);

            if (e > s) {
                decodePathSegment(segments, path.substring(s, e), decode);
            } else if (e == s) {
                segments.add(PathSegmentImpl.EMPTY_PATH_SEGMENT);
            }
        } while (e != -1);
        if (s < path.length()) {
            decodePathSegment(segments, path.substring(s), decode);
        } else {
            segments.add(PathSegmentImpl.EMPTY_PATH_SEGMENT);
        }
        return segments;
    }

    /**
     * Decode the path segment and add it to the list of path segments.
     *
     * @param segments mutable list of path segments.
     * @param segment path segment to be decoded.
     * @param decode {@code true} if the path segment should be in a decoded form.
     */
    public static void decodePathSegment(final List<PathSegment> segments, final String segment, final boolean decode) {
        final int colon = segment.indexOf(';');
        if (colon != -1) {
            segments.add(new PathSegmentImpl((colon == 0) ? "" : segment.substring(0, colon), decode, decodeMatrix(segment,
                    decode)));
        } else {
            segments.add(new PathSegmentImpl(segment, decode));
        }
    }

    /**
     * Decode the matrix component of a URI path segment.
     *
     * @param pathSegment the path segment component in encoded form.
     * @param decode true if the matrix parameters of the path segment component
     * should be in decoded form.
     * @return the multivalued map of matrix parameters.
     */
    public static MultivaluedMap<String, String> decodeMatrix(final String pathSegment, final boolean decode) {
        final MultivaluedMap<String, String> matrixMap = new MultivaluedStringMap();

        // Skip over path segment
        int s = pathSegment.indexOf(';') + 1;
        if (s == 0 || s == pathSegment.length()) {
            return matrixMap;
        }

        do {
            final int e = pathSegment.indexOf(';', s);

            if (e == -1) {
                decodeMatrixParam(matrixMap, pathSegment.substring(s), decode);
            } else if (e > s) {
                decodeMatrixParam(matrixMap, pathSegment.substring(s, e), decode);
            }
            s = e + 1;
        } while (s > 0 && s < pathSegment.length());

        return matrixMap;
    }

    @SuppressWarnings("StatementWithEmptyBody")
    private static void decodeMatrixParam(final MultivaluedMap<String, String> params, final String param, final boolean decode) {
        final int equals = param.indexOf('=');
        if (equals > 0) {
            params.add(UriComponent.decode(param.substring(0, equals), UriComponent.Type.MATRIX_PARAM),
                    (decode) ? UriComponent.decode(param.substring(equals + 1), UriComponent.Type.MATRIX_PARAM) : param
                            .substring(equals + 1));
        } else if (equals == 0) {
            // no key declared, ignore
        } else if (param.length() > 0) {
            params.add(UriComponent.decode(param, UriComponent.Type.MATRIX_PARAM), "");
        }
    }

    private static String decode(final String s, final int n) {
        final StringBuilder sb = new StringBuilder(n);
        ByteBuffer bb = null;

        for (int i = 0; i < n; ) {
            final char c = s.charAt(i++);
            if (c != '%') {
                sb.append(c);
            } else {
                bb = decodePercentEncodedOctets(s, i, bb);
                i = decodeOctets(i, bb, sb);
            }
        }

        return sb.toString();
    }

    private static String decodeQueryParam(final String s, final int n) {
        final StringBuilder sb = new StringBuilder(n);
        ByteBuffer bb = null;

        for (int i = 0; i < n; ) {
            final char c = s.charAt(i++);
            if (c != '%') {
                if (c != '+') {
                    sb.append(c);
                } else {
                    sb.append(' ');
                }
            } else {
                bb = decodePercentEncodedOctets(s, i, bb);
                i = decodeOctets(i, bb, sb);
            }
        }

        return sb.toString();
    }

    private static String decodeHost(final String s, final int n) {
        final StringBuilder sb = new StringBuilder(n);
        ByteBuffer bb = null;

        boolean betweenBrackets = false;
        for (int i = 0; i < n; ) {
            final char c = s.charAt(i++);
            if (c == '[') {
                betweenBrackets = true;
            } else if (betweenBrackets && c == ']') {
                betweenBrackets = false;
            }

            if (c != '%' || betweenBrackets) {
                sb.append(c);
            } else {
                bb = decodePercentEncodedOctets(s, i, bb);
                i = decodeOctets(i, bb, sb);
            }
        }

        return sb.toString();
    }

    /**
     * Decode a continuous sequence of percent encoded octets.
     * <p/>
     * Assumes the index, i, starts that the first hex digit of the first
     * percent-encoded octet.
     */
    private static ByteBuffer decodePercentEncodedOctets(final String s, int i, ByteBuffer bb) {
        if (bb == null) {
            bb = ByteBuffer.allocate(1);
        } else {
            bb.clear();
        }

        while (true) {
            // Decode the hex digits
            bb.put((byte) (decodeHex(s, i++) << 4 | decodeHex(s, i++)));

            // Finish if at the end of the string
            if (i == s.length()) {
                break;
            }

            // Finish if no more percent-encoded octets follow
            if (s.charAt(i++) != '%') {
                break;
            }

            // Check if the byte buffer needs to be increased in size
            if (bb.position() == bb.capacity()) {
                bb.flip();
                // Create a new byte buffer with the maximum number of possible
                // octets, hence resize should only occur once
                final ByteBuffer bb_new = ByteBuffer.allocate(s.length() / 3);
                bb_new.put(bb);
                bb = bb_new;
            }
        }

        bb.flip();
        return bb;
    }

    /**
     * Decodes octets to characters using the UTF-8 decoding and appends
     * the characters to a StringBuffer.
     *
     * @return the index to the next unchecked character in the string to decode
     */
    private static int decodeOctets(final int i, final ByteBuffer bb, final StringBuilder sb) {
        // If there is only one octet and is an ASCII character
        if (bb.limit() == 1 && (bb.get(0) & 0xFF) < 0x80) {
            // Octet can be appended directly
            sb.append((char) bb.get(0));
            return i + 2;
        } else {
            //
            final CharBuffer cb = UTF_8_CHARSET.decode(bb);
            sb.append(cb.toString());
            return i + bb.limit() * 3 - 1;
        }
    }

    private static int decodeHex(final String s, final int i) {
        final int v = decodeHex(s.charAt(i));
        if (v == -1) {
            throw new IllegalArgumentException(LocalizationMessages.URI_COMPONENT_ENCODED_OCTET_INVALID_DIGIT(i, s.charAt(i)));
        }
        return v;
    }

    private static final int[] HEX_TABLE = initHexTable();

    private static int[] initHexTable() {
        final int[] table = new int[0x80];
        Arrays.fill(table, -1);

        for (char c = '0'; c <= '9'; c++) {
            table[c] = c - '0';
        }
        for (char c = 'A'; c <= 'F'; c++) {
            table[c] = c - 'A' + 10;
        }
        for (char c = 'a'; c <= 'f'; c++) {
            table[c] = c - 'a' + 10;
        }
        return table;
    }

    private static int decodeHex(final char c) {
        return (c < 128) ? HEX_TABLE[c] : -1;
    }

    /**
     * Checks whether the character {@code c} is hexadecimal character.
     *
     * @param c Any character
     * @return The is {@code c} is a hexadecimal character (e.g. 0, 5, a, A, f, ...)
     */
    public static boolean isHexCharacter(final char c) {
        return c < 128 && HEX_TABLE[c] != -1;
    }

    /**
     * Return the {@code Request-Uri} representation as defined by HTTP spec. For example:
     * <pre>&lt;Method> &lt;Request-URI> HTTP/&lt;Version> (e.g. GET /auth;foo=bar/hello?foo=bar HTTP/1.1)</pre>
     *
     * @param uri uri to obtain {@code Request-Uri} from.
     * @return {@code Request-Uri} representation or {@code null} if {@code uri} is not provided.
     */
    public static String fullRelativeUri(final URI uri) {
        if (uri == null) {
            return null;
        }

        final String query = uri.getRawQuery();

        return uri.getRawPath() + (query != null && query.length() > 0 ? "?" + query : "");
    }

}
