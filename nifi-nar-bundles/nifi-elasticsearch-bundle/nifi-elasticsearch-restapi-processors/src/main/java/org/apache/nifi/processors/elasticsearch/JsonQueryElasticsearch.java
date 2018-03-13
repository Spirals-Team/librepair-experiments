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
package org.apache.nifi.processors.elasticsearch;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.nifi.annotation.behavior.EventDriven;
import org.apache.nifi.annotation.behavior.InputRequirement;
import org.apache.nifi.annotation.behavior.WritesAttribute;
import org.apache.nifi.annotation.behavior.WritesAttributes;
import org.apache.nifi.annotation.documentation.CapabilityDescription;
import org.apache.nifi.annotation.documentation.Tags;
import org.apache.nifi.annotation.lifecycle.OnScheduled;
import org.apache.nifi.annotation.lifecycle.OnUnscheduled;
import org.apache.nifi.components.AllowableValue;
import org.apache.nifi.components.PropertyDescriptor;
import org.apache.nifi.components.Validator;
import org.apache.nifi.elasticsearch.ElasticSearchClientService;
import org.apache.nifi.elasticsearch.SearchResponse;
import org.apache.nifi.flowfile.FlowFile;
import org.apache.nifi.flowfile.attributes.CoreAttributes;
import org.apache.nifi.processor.AbstractProcessor;
import org.apache.nifi.processor.ProcessContext;
import org.apache.nifi.processor.ProcessSession;
import org.apache.nifi.processor.Relationship;
import org.apache.nifi.processor.exception.ProcessException;
import org.apache.nifi.processor.util.StandardValidators;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@WritesAttributes({
    @WritesAttribute(attribute = "mime.type", description = "application/json"),
    @WritesAttribute(attribute = "aggregation.name", description = "The name of the aggregation whose results are in the output flowfile")
})
@InputRequirement(InputRequirement.Requirement.INPUT_ALLOWED)
@EventDriven
@Tags({"elasticsearch", "elasticsearch 5", "query", "read", "get", "json"})
@CapabilityDescription("A processor that allows the user to run a query (with aggregations) written with the " +
        "ElasticSearch JSON DSL. It currently does not support pagination.")
public class JsonQueryElasticsearch extends AbstractProcessor {
    public static final Relationship REL_ORIGINAL = new Relationship.Builder().name("original")
            .description("All original flowfiles that don't cause an error to occur go to this relationship. " +
                    "This applies even if you select the \"split up hits\" option to send individual hits to the " +
                    "\"hits\" relationship.").build();

    public static final Relationship REL_FAILURE = new Relationship.Builder().name("failure")
            .description("All FlowFiles that cannot be read from Elasticsearch are routed to this relationship").build();

    public static final Relationship REL_HITS = new Relationship.Builder().name("hits")
            .description("Search hits are routed to this relationship.")
            .build();

    public static final Relationship REL_AGGREGATIONS = new Relationship.Builder().name("aggregations")
            .description("Aggregations are routed to this relationship.")
            .build();

    public static final PropertyDescriptor INDEX = new PropertyDescriptor.Builder()
            .name("el-rest-fetch-index")
            .displayName("Index")
            .description("The name of the index to read from")
            .required(true)
            .expressionLanguageSupported(true)
            .addValidator(StandardValidators.NON_EMPTY_VALIDATOR)
            .build();

    public static final PropertyDescriptor TYPE = new PropertyDescriptor.Builder()
            .name("el-rest-type")
            .displayName("Type")
            .description("The type of this document (used by Elasticsearch for indexing and searching)")
            .required(false)
            .expressionLanguageSupported(true)
            .addValidator(StandardValidators.NON_EMPTY_VALIDATOR)
            .build();

    public static final PropertyDescriptor QUERY = new PropertyDescriptor.Builder()
            .name("el-rest-query")
            .displayName("Query")
            .description("A query in JSON syntax, not Lucene syntax. Ex: " +
                    "{\n" +
                    "\t\"query\": {\n" +
                    "\t\t\"match\": {\n" +
                    "\t\t\t\"name\": \"John Smith\"\n" +
                    "\t\t}\n" +
                    "\t}\n" +
                    "}")
            .required(false)
            .expressionLanguageSupported(true)
            .addValidator(StandardValidators.NON_EMPTY_VALIDATOR)
            .build();
    public static final PropertyDescriptor QUERY_ATTRIBUTE = new PropertyDescriptor.Builder()
            .name("el-query-attribute")
            .displayName("Query Attribute")
            .description("If set, the executed query will be set on each result flowfile in the specified attribute.")
            .expressionLanguageSupported(true)
            .addValidator(Validator.VALID)
            .required(false)
            .build();

    public static final AllowableValue SPLIT_UP_YES = new AllowableValue(
        "splitUp-yes",
        "Yes",
        "Split up results."
    );
    public static final AllowableValue SPLIT_UP_HITS_NO = new AllowableValue(
        "splitUp-no",
        "No",
        "Don't split up results."
    );

    public static final PropertyDescriptor SPLIT_UP_HITS = new PropertyDescriptor.Builder()
            .name("el-rest-split-up-hits")
            .displayName("Split up search results")
            .description("Split up search results into one flowfile per result.")
            .allowableValues(SPLIT_UP_HITS_NO, SPLIT_UP_YES)
            .defaultValue(SPLIT_UP_HITS_NO.getValue())
            .required(true)
            .expressionLanguageSupported(false)
            .build();
    public static final PropertyDescriptor SPLIT_UP_AGGREGATIONS = new PropertyDescriptor.Builder()
            .name("el-rest-split-up-aggregations")
            .displayName("Split up aggregation results")
            .description("Split up aggregation results into one flowfile per result.")
            .allowableValues(SPLIT_UP_HITS_NO, SPLIT_UP_YES)
            .defaultValue(SPLIT_UP_HITS_NO.getValue())
            .required(true)
            .expressionLanguageSupported(false)
            .build();

    public static final PropertyDescriptor CLIENT_SERVICE = new PropertyDescriptor.Builder()
            .name("el-rest-client-service")
            .displayName("Client Service")
            .description("An ElasticSearch client service to use for running queries.")
            .identifiesControllerService(ElasticSearchClientService.class)
            .required(true)
            .build();

    private static final Set<Relationship> relationships;
    private static final List<PropertyDescriptor> propertyDescriptors;

    private ElasticSearchClientService clientService;

    static {
        final Set<Relationship> _rels = new HashSet<>();
        _rels.add(REL_ORIGINAL);
        _rels.add(REL_FAILURE);
        _rels.add(REL_HITS);
        _rels.add(REL_AGGREGATIONS);
        relationships = Collections.unmodifiableSet(_rels);

        final List<PropertyDescriptor> descriptors = new ArrayList<>();
        descriptors.add(QUERY);
        descriptors.add(QUERY_ATTRIBUTE);
        descriptors.add(INDEX);
        descriptors.add(TYPE);
        descriptors.add(CLIENT_SERVICE);
        descriptors.add(SPLIT_UP_HITS);
        descriptors.add(SPLIT_UP_AGGREGATIONS);

        propertyDescriptors = Collections.unmodifiableList(descriptors);
    }

    @Override
    public Set<Relationship> getRelationships() {
        return relationships;
    }

    @Override
    public final List<PropertyDescriptor> getSupportedPropertyDescriptors() {
        return propertyDescriptors;
    }

    @OnScheduled
    public void onScheduled(final ProcessContext context) {
        clientService = context.getProperty(CLIENT_SERVICE).asControllerService(ElasticSearchClientService.class);
    }

    @OnUnscheduled
    public void onUnscheduled() {
        this.clientService = null;
    }


    private final ObjectMapper mapper = new ObjectMapper();

    private String getQuery(FlowFile input, ProcessContext context, ProcessSession session) throws IOException {
        String retVal = null;
        if (context.getProperty(QUERY).isSet()) {
            retVal = context.getProperty(QUERY).evaluateAttributeExpressions(input).getValue();
        } else if (input != null) {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            session.exportTo(input, out);
            out.close();

            retVal = new String(out.toByteArray());
        }

        return retVal;
    }

    @Override
    public void onTrigger(final ProcessContext context, final ProcessSession session) throws ProcessException {
        FlowFile input = null;
        if (context.hasIncomingConnection()) {
            input = session.get();

            if (input == null && context.hasNonLoopConnection()) {
                return;
            }
        }

        try {

            final String query = getQuery(input, context, session);
            final String index = context.getProperty(INDEX).evaluateAttributeExpressions(input).getValue();
            final String type = context.getProperty(TYPE).evaluateAttributeExpressions(input).getValue();

            Optional<SearchResponse> resp = clientService.search(query, index, type);
            if (resp.isPresent()) {
                SearchResponse response = resp.get();

                List<FlowFile> hitsFlowFiles = handleHits(response.getHits(), context, session, input);
                List<FlowFile> aggsFlowFiles = handleAggregations(response.getAggregations(), context, session, input);

                if (hitsFlowFiles.size() > 0) {
                    for (FlowFile ff : hitsFlowFiles) {
                        session.transfer(ff, REL_HITS);
                        session.getProvenanceReporter().send(ff, clientService.getTransitUrl(index, type));
                    }
                }

                if (aggsFlowFiles.size() > 0) {
                    for (FlowFile ff : aggsFlowFiles) {
                        session.transfer(ff, REL_AGGREGATIONS);
                        session.getProvenanceReporter().send(ff, clientService.getTransitUrl(index, type));
                    }
                }
            }
            if (input != null) {
                session.transfer(input, REL_ORIGINAL);
            }
        } catch (Exception ex) {
            getLogger().error("Error processing flowfile.", ex);
            if (input != null) {
                session.transfer(input, REL_FAILURE);
            }
        }
    }

    private FlowFile writeAggregationFlowFileContents(String name, String json, ProcessSession session, FlowFile aggFlowFile) {
        aggFlowFile = session.write(aggFlowFile, out -> out.write(json.getBytes()));
        if (name != null) {
            aggFlowFile = session.putAttribute(aggFlowFile, "aggregation.name", name);
        }

        aggFlowFile = session.putAttribute(aggFlowFile, CoreAttributes.MIME_TYPE.key(), "application/json");

        return aggFlowFile;
    }

    private List<FlowFile> handleAggregations(Map<String, Object> aggregations, ProcessContext context, ProcessSession session, FlowFile parent) throws IOException {
        List<FlowFile> retVal = new ArrayList<>();
        if (aggregations == null) {
            return retVal;
        }
        String splitUpValue = context.getProperty(SPLIT_UP_AGGREGATIONS).getValue();

        if (splitUpValue.equals(SPLIT_UP_YES.getValue())) {
            for (Map.Entry<String, Object> agg : aggregations.entrySet()) {
                FlowFile aggFlowFile = parent != null ? session.create(parent) : session.create();
                String aggJson = mapper.writeValueAsString(agg.getValue());
                retVal.add(writeAggregationFlowFileContents(agg.getKey(), aggJson, session, aggFlowFile));
            }
        } else {
            String json = mapper.writeValueAsString(aggregations);
            retVal.add(writeAggregationFlowFileContents(null, json, session, parent != null ? session.create(parent) : session.create()));
        }

        return retVal;
    }

    private FlowFile writeHitFlowFile(String json, ProcessSession session, FlowFile hitFlowFile) {
        hitFlowFile = session.write(hitFlowFile, out -> out.write(json.getBytes()));

        return session.putAttribute(hitFlowFile, CoreAttributes.MIME_TYPE.key(), "application/json");
    }

    private List<FlowFile> handleHits(List<Map<String, Object>> hits, ProcessContext context, ProcessSession session, FlowFile parent) throws IOException {
        String splitUpValue = context.getProperty(SPLIT_UP_HITS).getValue();
        List<FlowFile> retVal = new ArrayList<>();
        if (splitUpValue.equals(SPLIT_UP_YES.getValue())) {
            for (Map<String, Object> hit : hits) {
                FlowFile hitFlowFile = parent != null ? session.create(parent) : session.create();
                String json = mapper.writeValueAsString(hit);

                retVal.add(writeHitFlowFile(json, session, hitFlowFile));
            }
        } else {
            FlowFile hitFlowFile = parent != null ? session.create(parent) : session.create();
            String json = mapper.writeValueAsString(hits);
            retVal.add(writeHitFlowFile(json, session, hitFlowFile));
        }

        return retVal;
    }
}
