/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.nifi.processors.standard;

import com.github.palindromicity.syslog.NilPolicy;
import org.apache.nifi.annotation.behavior.EventDriven;
import org.apache.nifi.annotation.behavior.InputRequirement;
import org.apache.nifi.annotation.behavior.InputRequirement.Requirement;
import org.apache.nifi.annotation.behavior.SideEffectFree;
import org.apache.nifi.annotation.behavior.SupportsBatching;
import org.apache.nifi.annotation.behavior.WritesAttribute;
import org.apache.nifi.annotation.behavior.WritesAttributes;
import org.apache.nifi.annotation.documentation.CapabilityDescription;
import org.apache.nifi.annotation.documentation.SeeAlso;
import org.apache.nifi.annotation.documentation.Tags;
import org.apache.nifi.components.AllowableValue;
import org.apache.nifi.components.PropertyDescriptor;
import org.apache.nifi.expression.ExpressionLanguageScope;
import org.apache.nifi.flowfile.FlowFile;
import org.apache.nifi.processor.AbstractProcessor;
import org.apache.nifi.processor.ProcessContext;
import org.apache.nifi.processor.ProcessSession;
import org.apache.nifi.processor.Relationship;
import org.apache.nifi.processor.exception.ProcessException;
import org.apache.nifi.processor.io.InputStreamCallback;
import org.apache.nifi.processor.util.StandardValidators;
import org.apache.nifi.processors.standard.syslog.StrictSyslog5424Parser;
import org.apache.nifi.processors.standard.syslog.Syslog5424Event;
import org.apache.nifi.stream.io.StreamUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@EventDriven
@SideEffectFree
@SupportsBatching
@InputRequirement(Requirement.INPUT_REQUIRED)
@Tags({"logs", "syslog", "syslog5424", "attributes", "system", "event", "message"})
@CapabilityDescription("Attempts to parse the contents of a well formed Syslog message in accordance to RFC5424 " +
        "format and adds attributes to the FlowFile for each of the parts of the Syslog message, including Structured Data." +
        "Structured Data will be written to attributes as on attribute per item id + parameter "+
        "see https://tools.ietf.org/html/rfc5424." +
        "Note: ParseSyslog5424 follows the specification more closely than ParseSyslog.  If your Syslog producer " +
        "does not follow the spec closely, with regards to using '-' for missing header entries for example, those logs " +
        "will fail with this parser, where they would not fail with ParseSyslog.")
@WritesAttributes({@WritesAttribute(attribute = "syslog.priority", description = "The priority of the Syslog message."),
    @WritesAttribute(attribute = "syslog.severity", description = "The severity of the Syslog message derived from the priority."),
    @WritesAttribute(attribute = "syslog.facility", description = "The facility of the Syslog message derived from the priority."),
    @WritesAttribute(attribute = "syslog.version", description = "The optional version from the Syslog message."),
    @WritesAttribute(attribute = "syslog.timestamp", description = "The timestamp of the Syslog message."),
    @WritesAttribute(attribute = "syslog.hostname", description = "The hostname or IP address of the Syslog message."),
    @WritesAttribute(attribute = "syslog.appname", description = "The appname of the Syslog message."),
    @WritesAttribute(attribute = "syslog.procid", description = "The procid of the Syslog message."),
    @WritesAttribute(attribute = "syslog.messageid", description = "The messageid the Syslog message."),
    @WritesAttribute(attribute = "syslog.structuredData", description = "Multiple entries per structuredData of the Syslog message."),
    @WritesAttribute(attribute = "syslog.sender", description = "The hostname of the Syslog server that sent the message."),
    @WritesAttribute(attribute = "syslog.body", description = "The body of the Syslog message, everything after the hostname.")})
@SeeAlso({ListenSyslog.class, ParseSyslog.class, PutSyslog.class})
public class ParseSyslog5424 extends AbstractProcessor {

    public static final AllowableValue OMIT = new AllowableValue(NilPolicy.OMIT.name(),NilPolicy.OMIT.name(),"The missing field will not have an attribute added.");
    public static final AllowableValue NULL = new AllowableValue(NilPolicy.NULL.name(),NilPolicy.NULL.name(),"The missing field will have an empty attribute added.");
    public static final AllowableValue DASH = new AllowableValue(NilPolicy.DASH.name(),NilPolicy.DASH.name(),"The missing field will have an attribute added with the value of '-'.");

    public static final PropertyDescriptor CHARSET = new PropertyDescriptor.Builder()
        .name("Character Set")
        .description("Specifies which character set of the Syslog messages")
        .required(true)
        .defaultValue("UTF-8")
        .addValidator(StandardValidators.CHARACTER_SET_VALIDATOR)
        .build();

    public static final PropertyDescriptor NIL_POLICY = new PropertyDescriptor.Builder()
        .name("nil_policy")
        .displayName("NIL Policy")
        .description("Defines how NIL values are handled for header fields.")
        .addValidator(StandardValidators.NON_BLANK_VALIDATOR)
        .allowableValues(OMIT,NULL,DASH)
        .required(true)
        .expressionLanguageSupported(ExpressionLanguageScope.NONE)
        .defaultValue(NULL.getValue())
        .build();

    static final Relationship REL_FAILURE = new Relationship.Builder()
        .name("failure")
        .description("Any FlowFile that could not be parsed as a Syslog message will be transferred to this Relationship without any attributes being added")
        .build();
    static final Relationship REL_SUCCESS = new Relationship.Builder()
        .name("success")
        .description("Any FlowFile that is successfully parsed as a Syslog message will be to this Relationship.")
        .build();

    private StrictSyslog5424Parser parser;


    @Override
    protected List<PropertyDescriptor> getSupportedPropertyDescriptors() {
        final List<PropertyDescriptor> properties = new ArrayList<>(2);
        properties.add(CHARSET);
        properties.add(NIL_POLICY);
        return properties;
    }

    @Override
    public Set<Relationship> getRelationships() {
        final Set<Relationship> relationships = new HashSet<>();
        relationships.add(REL_FAILURE);
        relationships.add(REL_SUCCESS);
        return relationships;
    }

    @Override
    public void onTrigger(final ProcessContext context, final ProcessSession session) throws ProcessException {
        FlowFile flowFile = session.get();
        if (flowFile == null) {
            return;
        }

        final String charsetName = context.getProperty(CHARSET).getValue();
        final String nilPolicyString = context.getProperty(NIL_POLICY).getValue();

        // If the parser already exists and uses the same charset, it does not need to be re-initialized
        if (parser == null || !parser.getCharsetName().equals(charsetName)) {
            parser = new StrictSyslog5424Parser(Charset.forName(charsetName),NilPolicy.valueOf(nilPolicyString));
        }

        final byte[] buffer = new byte[(int) flowFile.getSize()];
        session.read(flowFile, new InputStreamCallback() {
            @Override
            public void process(final InputStream in) throws IOException {
                StreamUtils.fillBuffer(in, buffer);
            }
        });

        final Syslog5424Event event;
        try {
            event = parser.parseEvent(buffer, null);
        } catch (final ProcessException pe) {
            getLogger().error("Failed to parse {} as a Syslog message due to {}; routing to failure", new Object[] {flowFile, pe});
            session.transfer(flowFile, REL_FAILURE);
            return;
        }

        if (event == null || !event.isValid()) {
            getLogger().error("Failed to parse {} as a Syslog message: it does not conform to any of the RFC formats supported; routing to failure", new Object[] {flowFile});
            session.transfer(flowFile, REL_FAILURE);
            return;
        }

        flowFile = session.putAllAttributes(flowFile, event.getFieldMap());
        session.transfer(flowFile, REL_SUCCESS);
    }
}
