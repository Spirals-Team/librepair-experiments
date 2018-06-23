/**
 * <h1>Friendly Forked Socket Transfer Protocol</h1>
 * <h2>Overview</h2>
 * This package contains classes that can be used to easily communicate between sockets.
 * The library interface is defined by {@link jezorko.ffstp.FriendlyTemplate} class,
 * which provides methods for both client– and server–side code.
 * <h2>Message structure</h2>
 * Message is composed from the following elements, each followed by a semicolon:
 * <li><b>header</b> - always equal to {@link jezorko.ffstp.Constants#PROTOCOL_HEADER}</li>
 * <li><b>status</b> - any ascii {@link java.lang.String} which does not contain a semicolon, see {@link jezorko.ffstp.Status} for a list of pre-defined statuses</li>
 * <li><b>payload length</b> - amount of bytes of the serialized payload, represented as a numeric String</li>
 * <li><b>payload</b> - any byte array that has a length equal to the payload length field</li>
 * <br/>
 * Example message that conforms to the protocol (bytes represented as text):
 * <pre>    FFS;OK;4;test;</pre>
 */
package jezorko.ffstp;