/**
 * Copyright © 2018 spring-data-dynamodb (https://github.com/derjust/spring-data-dynamodb)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.socialsignin.spring.data.dynamodb.utils;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.local.embedded.DynamoDBEmbedded;
import com.amazonaws.services.dynamodbv2.model.AttributeDefinition;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.CreateTableResult;
import com.amazonaws.services.dynamodbv2.model.GlobalSecondaryIndex;
import com.amazonaws.services.dynamodbv2.model.KeySchemaElement;
import com.amazonaws.services.dynamodbv2.model.KeyType;
import com.amazonaws.services.dynamodbv2.model.Projection;
import com.amazonaws.services.dynamodbv2.model.ProjectionType;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.model.ScalarAttributeType;
import org.junit.rules.ExternalResource;
import org.socialsignin.spring.data.dynamodb.repository.support.DynamoDBEntityInformation;
import org.socialsignin.spring.data.dynamodb.repository.support.DynamoDBEntityMetadataSupport;
import org.socialsignin.spring.data.dynamodb.repository.support.DynamoDBIdIsHashAndRangeKeyEntityInformation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Configuration
public class DynamoDBLocalResource extends ExternalResource {

    private AmazonDynamoDB ddb;

    @Bean
    public AmazonDynamoDB amazonDynamoDB() {
        ddb = DynamoDBEmbedded.create().amazonDynamoDB();
        return ddb;
    }

    public static <T> CreateTableResult createTable(AmazonDynamoDB ddb, Class<T> domainType) {
        DynamoDBMapper mapper = new DynamoDBMapper(ddb);

        ProvisionedThroughput pt = new ProvisionedThroughput(10L, 10L);

        CreateTableRequest ctr = mapper.generateCreateTableRequest(domainType);
        ctr.setProvisionedThroughput(pt);
        ctr.getGlobalSecondaryIndexes().forEach(gsi -> {
            gsi.setProjection(new Projection().withProjectionType(ProjectionType.ALL));
            gsi.setProvisionedThroughput(pt);
        });

        return ddb.createTable(ctr);
        //TODO wait for table to be ready
    }

    @Override
    protected void after() {
        ddb.shutdown();
    };
}
