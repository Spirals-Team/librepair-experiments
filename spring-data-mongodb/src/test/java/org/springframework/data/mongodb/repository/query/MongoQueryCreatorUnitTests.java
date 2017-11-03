/*
 * Copyright 2011-2017 the original author or authors.
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
package org.springframework.data.mongodb.repository.query;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.springframework.data.mongodb.core.query.Criteria.*;
import static org.springframework.data.mongodb.core.query.Query.*;
import static org.springframework.data.mongodb.repository.query.StubParameterAccessor.*;

import java.lang.reflect.Method;
import java.util.List;

import org.bson.types.ObjectId;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.springframework.data.domain.Range;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Metrics;
import org.springframework.data.geo.Point;
import org.springframework.data.geo.Polygon;
import org.springframework.data.geo.Shape;
import org.springframework.data.mapping.context.MappingContext;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.Person;
import org.springframework.data.mongodb.core.Venue;
import org.springframework.data.mongodb.core.convert.DbRefResolver;
import org.springframework.data.mongodb.core.convert.DefaultDbRefResolver;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.convert.MongoConverter;
import org.springframework.data.mongodb.core.geo.GeoJsonLineString;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexType;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;
import org.springframework.data.mongodb.core.mapping.MongoPersistentEntity;
import org.springframework.data.mongodb.core.mapping.MongoPersistentProperty;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.projection.SpelAwareProxyProjectionFactory;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.core.support.DefaultRepositoryMetadata;
import org.springframework.data.repository.query.parser.PartTree;

import org.bson.Document;

/**
 * Unit test for {@link MongoQueryCreator}.
 * 
 * @author Oliver Gierke
 * @author Thomas Darimont
 * @author Christoph Strobl
 */
public class MongoQueryCreatorUnitTests {

	MappingContext<? extends MongoPersistentEntity<?>, MongoPersistentProperty> context;
	MongoConverter converter;

	@Rule public ExpectedException expection = ExpectedException.none();

	@Before
	public void setUp() throws SecurityException, NoSuchMethodException {

		context = new MongoMappingContext();

		DbRefResolver resolver = new DefaultDbRefResolver(mock(MongoDbFactory.class));
		converter = new MappingMongoConverter(resolver, context);
	}

	@Test
	public void createsQueryCorrectly() throws Exception {

		PartTree tree = new PartTree("findByFirstName", Person.class);

		MongoQueryCreator creator = new MongoQueryCreator(tree, getAccessor(converter, "Oliver"), context);
		Query query = creator.createQuery();
		assertThat(query, is(query(where("firstName").is("Oliver"))));
	}

	@Test // DATAMONGO-469
	public void createsAndQueryCorrectly() {

		Person person = new Person();
		MongoQueryCreator creator = new MongoQueryCreator(new PartTree("findByFirstNameAndFriend", Person.class),
				getAccessor(converter, "Oliver", person), context);
		Query query = creator.createQuery();

		assertThat(query, is(query(where("firstName").is("Oliver").and("friend").is(person))));
	}

	@Test
	public void createsNotNullQueryCorrectly() {

		PartTree tree = new PartTree("findByFirstNameNotNull", Person.class);
		Query query = new MongoQueryCreator(tree, getAccessor(converter), context).createQuery();

		assertThat(query, is(new Query(Criteria.where("firstName").ne(null))));
	}

	@Test
	public void createsIsNullQueryCorrectly() {

		PartTree tree = new PartTree("findByFirstNameIsNull", Person.class);
		Query query = new MongoQueryCreator(tree, getAccessor(converter), context).createQuery();

		assertThat(query, is(new Query(Criteria.where("firstName").is(null))));
	}

	@Test
	public void bindsMetricDistanceParameterToNearSphereCorrectly() throws Exception {

		Point point = new Point(10, 20);
		Distance distance = new Distance(2.5, Metrics.KILOMETERS);

		Query query = query(
				where("location").nearSphere(point).maxDistance(distance.getNormalizedValue()).and("firstname").is("Dave"));
		assertBindsDistanceToQuery(point, distance, query);
	}

	@Test
	public void bindsDistanceParameterToNearCorrectly() throws Exception {

		Point point = new Point(10, 20);
		Distance distance = new Distance(2.5);

		Query query = query(
				where("location").near(point).maxDistance(distance.getNormalizedValue()).and("firstname").is("Dave"));
		assertBindsDistanceToQuery(point, distance, query);
	}

	@Test
	public void createsLessThanEqualQueryCorrectly() throws Exception {

		PartTree tree = new PartTree("findByAgeLessThanEqual", Person.class);
		MongoQueryCreator creator = new MongoQueryCreator(tree, getAccessor(converter, 18), context);

		Query reference = query(where("age").lte(18));
		assertThat(creator.createQuery(), is(reference));
	}

	@Test
	public void createsGreaterThanEqualQueryCorrectly() throws Exception {

		PartTree tree = new PartTree("findByAgeGreaterThanEqual", Person.class);
		MongoQueryCreator creator = new MongoQueryCreator(tree, getAccessor(converter, 18), context);

		Query reference = query(where("age").gte(18));
		assertThat(creator.createQuery(), is(reference));
	}

	@Test // DATAMONGO-338
	public void createsExistsClauseCorrectly() {

		PartTree tree = new PartTree("findByAgeExists", Person.class);
		MongoQueryCreator creator = new MongoQueryCreator(tree, getAccessor(converter, true), context);
		Query query = query(where("age").exists(true));
		assertThat(creator.createQuery(), is(query));
	}

	@Test // DATAMONGO-338
	public void createsRegexClauseCorrectly() {

		PartTree tree = new PartTree("findByFirstNameRegex", Person.class);
		MongoQueryCreator creator = new MongoQueryCreator(tree, getAccessor(converter, ".*"), context);
		Query query = query(where("firstName").regex(".*"));
		assertThat(creator.createQuery(), is(query));
	}

	@Test // DATAMONGO-338
	public void createsTrueClauseCorrectly() {

		PartTree tree = new PartTree("findByActiveTrue", Person.class);
		MongoQueryCreator creator = new MongoQueryCreator(tree, getAccessor(converter), context);
		Query query = query(where("active").is(true));
		assertThat(creator.createQuery(), is(query));
	}

	@Test // DATAMONGO-338
	public void createsFalseClauseCorrectly() {

		PartTree tree = new PartTree("findByActiveFalse", Person.class);
		MongoQueryCreator creator = new MongoQueryCreator(tree, getAccessor(converter), context);
		Query query = query(where("active").is(false));
		assertThat(creator.createQuery(), is(query));
	}

	@Test // DATAMONGO-413
	public void createsOrQueryCorrectly() {

		PartTree tree = new PartTree("findByFirstNameOrAge", Person.class);
		MongoQueryCreator creator = new MongoQueryCreator(tree, getAccessor(converter, "Dave", 42), context);

		Query query = creator.createQuery();
		assertThat(query, is(query(new Criteria().orOperator(where("firstName").is("Dave"), where("age").is(42)))));
	}

	@Test // DATAMONGO-347
	public void createsQueryReferencingADBRefCorrectly() {

		User user = new User();
		user.id = new ObjectId();

		PartTree tree = new PartTree("findByCreator", User.class);
		MongoQueryCreator creator = new MongoQueryCreator(tree, getAccessor(converter, user), context);
		Document queryObject = creator.createQuery().getQueryObject();

		assertThat(queryObject.get("creator"), is((Object) user));
	}

	@Test // DATAMONGO-418
	public void createsQueryWithStartingWithPredicateCorrectly() {

		PartTree tree = new PartTree("findByUsernameStartingWith", User.class);
		MongoQueryCreator creator = new MongoQueryCreator(tree, getAccessor(converter, "Matt"), context);
		Query query = creator.createQuery();

		assertThat(query, is(query(where("username").regex("^Matt"))));
	}

	@Test // DATAMONGO-418
	public void createsQueryWithEndingWithPredicateCorrectly() {

		PartTree tree = new PartTree("findByUsernameEndingWith", User.class);
		MongoQueryCreator creator = new MongoQueryCreator(tree, getAccessor(converter, "ews"), context);
		Query query = creator.createQuery();

		assertThat(query, is(query(where("username").regex("ews$"))));
	}

	@Test // DATAMONGO-418
	public void createsQueryWithContainingPredicateCorrectly() {

		PartTree tree = new PartTree("findByUsernameContaining", User.class);
		MongoQueryCreator creator = new MongoQueryCreator(tree, getAccessor(converter, "thew"), context);
		Query query = creator.createQuery();

		assertThat(query, is(query(where("username").regex(".*thew.*"))));
	}

	private void assertBindsDistanceToQuery(Point point, Distance distance, Query reference) throws Exception {

		PartTree tree = new PartTree("findByLocationNearAndFirstname",
				org.springframework.data.mongodb.repository.Person.class);
		Method method = PersonRepository.class.getMethod("findByLocationNearAndFirstname", Point.class, Distance.class,
				String.class);
		MongoQueryMethod queryMethod = new MongoQueryMethod(method, new DefaultRepositoryMetadata(PersonRepository.class),
				new SpelAwareProxyProjectionFactory(), new MongoMappingContext());
		MongoParameterAccessor accessor = new MongoParametersParameterAccessor(queryMethod,
				new Object[] { point, distance, "Dave" });

		Query query = new MongoQueryCreator(tree, new ConvertingParameterAccessor(converter, accessor), context)
				.createQuery();
		assertThat(query, is(query));
	}

	@Test // DATAMONGO-770
	public void createsQueryWithFindByIgnoreCaseCorrectly() {

		PartTree tree = new PartTree("findByfirstNameIgnoreCase", Person.class);
		MongoQueryCreator creator = new MongoQueryCreator(tree, getAccessor(converter, "dave"), context);

		Query query = creator.createQuery();
		assertThat(query, is(query(where("firstName").regex("^dave$", "i"))));
	}

	@Test // DATAMONGO-770
	public void createsQueryWithFindByNotIgnoreCaseCorrectly() {

		PartTree tree = new PartTree("findByFirstNameNotIgnoreCase", Person.class);
		MongoQueryCreator creator = new MongoQueryCreator(tree, getAccessor(converter, "dave"), context);

		Query query = creator.createQuery();
		assertThat(query.toString(), is(query(where("firstName").not().regex("^dave$", "i")).toString()));
	}

	@Test // DATAMONGO-770
	public void createsQueryWithFindByStartingWithIgnoreCaseCorrectly() {

		PartTree tree = new PartTree("findByFirstNameStartingWithIgnoreCase", Person.class);
		MongoQueryCreator creator = new MongoQueryCreator(tree, getAccessor(converter, "dave"), context);

		Query query = creator.createQuery();
		assertThat(query, is(query(where("firstName").regex("^dave", "i"))));
	}

	@Test // DATAMONGO-770
	public void createsQueryWithFindByEndingWithIgnoreCaseCorrectly() {

		PartTree tree = new PartTree("findByFirstNameEndingWithIgnoreCase", Person.class);
		MongoQueryCreator creator = new MongoQueryCreator(tree, getAccessor(converter, "dave"), context);

		Query query = creator.createQuery();
		assertThat(query, is(query(where("firstName").regex("dave$", "i"))));
	}

	@Test // DATAMONGO-770
	public void createsQueryWithFindByContainingIgnoreCaseCorrectly() {

		PartTree tree = new PartTree("findByFirstNameContainingIgnoreCase", Person.class);
		MongoQueryCreator creator = new MongoQueryCreator(tree, getAccessor(converter, "dave"), context);

		Query query = creator.createQuery();
		assertThat(query, is(query(where("firstName").regex(".*dave.*", "i"))));
	}

	@Test // DATAMONGO-770
	public void shouldThrowExceptionForQueryWithFindByIgnoreCaseOnNonStringProperty() {

		expection.expect(IllegalArgumentException.class);
		expection.expectMessage("must be of type String");

		PartTree tree = new PartTree("findByFirstNameAndAgeIgnoreCase", Person.class);
		MongoQueryCreator creator = new MongoQueryCreator(tree, getAccessor(converter, "foo", 42), context);

		creator.createQuery();
	}

	@Test // DATAMONGO-770
	public void shouldOnlyGenerateLikeExpressionsForStringPropertiesIfAllIgnoreCase() {

		PartTree tree = new PartTree("findByFirstNameAndAgeAllIgnoreCase", Person.class);
		MongoQueryCreator creator = new MongoQueryCreator(tree, getAccessor(converter, "dave", 42), context);

		Query query = creator.createQuery();
		assertThat(query, is(query(where("firstName").regex("^dave$", "i").and("age").is(42))));
	}

	@Test // DATAMONGO-566
	public void shouldCreateDeleteByQueryCorrectly() {

		PartTree tree = new PartTree("deleteByFirstName", Person.class);
		MongoQueryCreator creator = new MongoQueryCreator(tree, getAccessor(converter, "dave", 42), context);

		Query query = creator.createQuery();

		assertThat(tree.isDelete(), is(true));
		assertThat(query, is(query(where("firstName").is("dave"))));
	}

	@Test // DATAMONGO-566
	public void shouldCreateDeleteByQueryCorrectlyForMultipleCriteriaAndCaseExpressions() {

		PartTree tree = new PartTree("deleteByFirstNameAndAgeAllIgnoreCase", Person.class);
		MongoQueryCreator creator = new MongoQueryCreator(tree, getAccessor(converter, "dave", 42), context);

		Query query = creator.createQuery();

		assertThat(tree.isDelete(), is(true));
		assertThat(query, is(query(where("firstName").regex("^dave$", "i").and("age").is(42))));
	}

	@Test // DATAMONGO-1075
	public void shouldCreateInClauseWhenUsingContainsOnCollectionLikeProperty() {

		PartTree tree = new PartTree("findByEmailAddressesContaining", User.class);
		MongoQueryCreator creator = new MongoQueryCreator(tree, getAccessor(converter, "dave"), context);

		Query query = creator.createQuery();

		assertThat(query, is(query(where("emailAddresses").in("dave"))));
	}

	@Test // DATAMONGO-1075
	public void shouldCreateInClauseWhenUsingNotContainsOnCollectionLikeProperty() {

		PartTree tree = new PartTree("findByEmailAddressesNotContaining", User.class);
		MongoQueryCreator creator = new MongoQueryCreator(tree, getAccessor(converter, "dave"), context);

		Query query = creator.createQuery();

		assertThat(query, is(query(where("emailAddresses").not().in("dave"))));
	}

	@Test // DATAMONGO-1075, DATAMONGO-1425
	public void shouldCreateRegexWhenUsingNotContainsOnStringProperty() {

		PartTree tree = new PartTree("findByUsernameNotContaining", User.class);
		MongoQueryCreator creator = new MongoQueryCreator(tree, getAccessor(converter, "thew"), context);
		Query query = creator.createQuery();

		assertThat(query.getQueryObject().toJson(), is(query(where("username").not().regex(".*thew.*")).getQueryObject().toJson()));
	}

	@Test // DATAMONGO-1139
	public void createsNonSphericalNearForDistanceWithDefaultMetric() {

		Point point = new Point(1.0, 1.0);
		Distance distance = new Distance(1.0);

		PartTree tree = new PartTree("findByLocationNear", Venue.class);
		MongoQueryCreator creator = new MongoQueryCreator(tree, getAccessor(converter, point, distance), context);
		Query query = creator.createQuery();

		assertThat(query, is(query(where("location").near(point).maxDistance(1.0))));
	}

	@Test // DATAMONGO-1136
	public void shouldCreateWithinQueryCorrectly() {

		Point first = new Point(1, 1);
		Point second = new Point(2, 2);
		Point third = new Point(3, 3);
		Shape shape = new Polygon(first, second, third);

		PartTree tree = new PartTree("findByAddress_GeoWithin", User.class);
		MongoQueryCreator creator = new MongoQueryCreator(tree, getAccessor(converter, shape), context);
		Query query = creator.createQuery();

		assertThat(query, is(query(where("address.geo").within(shape))));
	}

	@Test // DATAMONGO-1110
	public void shouldCreateNearSphereQueryForSphericalProperty() {

		Point point = new Point(10, 20);

		PartTree tree = new PartTree("findByAddress2dSphere_GeoNear", User.class);
		MongoQueryCreator creator = new MongoQueryCreator(tree, getAccessor(converter, point), context);
		Query query = creator.createQuery();

		assertThat(query, is(query(where("address2dSphere.geo").nearSphere(point))));
	}

	@Test // DATAMONGO-1110
	public void shouldCreateNearSphereQueryForSphericalPropertyHavingDistanceWithDefaultMetric() {

		Point point = new Point(1.0, 1.0);
		Distance distance = new Distance(1.0);

		PartTree tree = new PartTree("findByAddress2dSphere_GeoNear", User.class);
		MongoQueryCreator creator = new MongoQueryCreator(tree, getAccessor(converter, point, distance), context);
		Query query = creator.createQuery();

		assertThat(query, is(query(where("address2dSphere.geo").nearSphere(point).maxDistance(1.0))));
	}

	@Test // DATAMONGO-1110
	public void shouldCreateNearQueryForMinMaxDistance() {

		Point point = new Point(10, 20);
		Range<Distance> range = Distance.between(new Distance(10), new Distance(20));

		PartTree tree = new PartTree("findByAddress_GeoNear", User.class);
		MongoQueryCreator creator = new MongoQueryCreator(tree, getAccessor(converter, point, range), context);
		Query query = creator.createQuery();

		assertThat(query, is(query(where("address.geo").near(point).minDistance(10D).maxDistance(20D))));
	}

	@Test // DATAMONGO-1229
	public void appliesIgnoreCaseToLeafProperty() {

		PartTree tree = new PartTree("findByAddressStreetIgnoreCase", User.class);
		ConvertingParameterAccessor accessor = getAccessor(converter, "Street");

		assertThat(new MongoQueryCreator(tree, accessor, context).createQuery(), is(notNullValue()));
	}

	@Test // DATAMONGO-1232
	public void ignoreCaseShouldEscapeSource() {

		PartTree tree = new PartTree("findByUsernameIgnoreCase", User.class);
		ConvertingParameterAccessor accessor = getAccessor(converter, "con.flux+");

		Query query = new MongoQueryCreator(tree, accessor, context).createQuery();

		assertThat(query, is(query(where("username").regex("^\\Qcon.flux+\\E$", "i"))));
	}

	@Test // DATAMONGO-1232
	public void ignoreCaseShouldEscapeSourceWhenUsedForStartingWith() {

		PartTree tree = new PartTree("findByUsernameStartingWithIgnoreCase", User.class);
		ConvertingParameterAccessor accessor = getAccessor(converter, "dawns.light+");

		Query query = new MongoQueryCreator(tree, accessor, context).createQuery();

		assertThat(query, is(query(where("username").regex("^\\Qdawns.light+\\E", "i"))));
	}

	@Test // DATAMONGO-1232
	public void ignoreCaseShouldEscapeSourceWhenUsedForEndingWith() {

		PartTree tree = new PartTree("findByUsernameEndingWithIgnoreCase", User.class);
		ConvertingParameterAccessor accessor = getAccessor(converter, "new.ton+");

		Query query = new MongoQueryCreator(tree, accessor, context).createQuery();

		assertThat(query, is(query(where("username").regex("\\Qnew.ton+\\E$", "i"))));
	}

	@Test // DATAMONGO-1232
	public void likeShouldEscapeSourceWhenUsedWithLeadingAndTrailingWildcard() {

		PartTree tree = new PartTree("findByUsernameLike", User.class);
		ConvertingParameterAccessor accessor = getAccessor(converter, "*fire.fight+*");

		Query query = new MongoQueryCreator(tree, accessor, context).createQuery();

		assertThat(query, is(query(where("username").regex(".*\\Qfire.fight+\\E.*"))));
	}

	@Test // DATAMONGO-1232
	public void likeShouldEscapeSourceWhenUsedWithLeadingWildcard() {

		PartTree tree = new PartTree("findByUsernameLike", User.class);
		ConvertingParameterAccessor accessor = getAccessor(converter, "*steel.heart+");

		Query query = new MongoQueryCreator(tree, accessor, context).createQuery();

		assertThat(query, is(query(where("username").regex(".*\\Qsteel.heart+\\E"))));
	}

	@Test // DATAMONGO-1232
	public void likeShouldEscapeSourceWhenUsedWithTrailingWildcard() {

		PartTree tree = new PartTree("findByUsernameLike", User.class);
		ConvertingParameterAccessor accessor = getAccessor(converter, "cala.mity+*");

		Query query = new MongoQueryCreator(tree, accessor, context).createQuery();
		assertThat(query, is(query(where("username").regex("\\Qcala.mity+\\E.*"))));
	}

	@Test // DATAMONGO-1232
	public void likeShouldBeTreatedCorrectlyWhenUsedWithWildcardOnly() {

		PartTree tree = new PartTree("findByUsernameLike", User.class);
		ConvertingParameterAccessor accessor = getAccessor(converter, "*");

		Query query = new MongoQueryCreator(tree, accessor, context).createQuery();
		assertThat(query, is(query(where("username").regex(".*"))));
	}

	@Test // DATAMONGO-1342
	public void bindsNullValueToContainsClause() {

		PartTree partTree = new PartTree("emailAddressesContains", User.class);

		ConvertingParameterAccessor accessor = getAccessor(converter, new Object[] { null });
		Query query = new MongoQueryCreator(partTree, accessor, context).createQuery();

		assertThat(query, is(query(where("emailAddresses").in((Object) null))));
	}

	@Test // DATAMONGO-1424
	public void notLikeShouldEscapeSourceWhenUsedWithLeadingAndTrailingWildcard() {

		PartTree tree = new PartTree("findByUsernameNotLike", User.class);
		ConvertingParameterAccessor accessor = getAccessor(converter, "*fire.fight+*");

		Query query = new MongoQueryCreator(tree, accessor, context).createQuery();

		assertThat(query.getQueryObject().toJson(),
				is(query(where("username").not().regex(".*\\Qfire.fight+\\E.*")).getQueryObject().toJson()));
	}

	@Test // DATAMONGO-1424
	public void notLikeShouldEscapeSourceWhenUsedWithLeadingWildcard() {

		PartTree tree = new PartTree("findByUsernameNotLike", User.class);
		ConvertingParameterAccessor accessor = getAccessor(converter, "*steel.heart+");

		Query query = new MongoQueryCreator(tree, accessor, context).createQuery();

		assertThat(query.getQueryObject().toJson(),
				is(query(where("username").not().regex(".*\\Qsteel.heart+\\E")).getQueryObject().toJson()));
	}

	@Test // DATAMONGO-1424
	public void notLikeShouldEscapeSourceWhenUsedWithTrailingWildcard() {

		PartTree tree = new PartTree("findByUsernameNotLike", User.class);
		MongoQueryCreator creator = new MongoQueryCreator(tree, getAccessor(converter, "cala.mity+*"), context);
		Query query = creator.createQuery();

		assertThat(query.getQueryObject().toJson(), is(query(where("username").not().regex("\\Qcala.mity+\\E.*")).getQueryObject().toJson()));
	}

	@Test // DATAMONGO-1424
	public void notLikeShouldBeTreatedCorrectlyWhenUsedWithWildcardOnly() {

		PartTree tree = new PartTree("findByUsernameNotLike", User.class);
		ConvertingParameterAccessor accessor = getAccessor(converter, "*");

		Query query = new MongoQueryCreator(tree, accessor, context).createQuery();
		assertThat(query.getQueryObject().toJson(), is(query(where("username").not().regex(".*")).getQueryObject().toJson()));
	}

	@Test // DATAMONGO-1588
	public void queryShouldAcceptSubclassOfDeclaredArgument() {

		PartTree tree = new PartTree("findByLocationNear", User.class);
		ConvertingParameterAccessor accessor = getAccessor(converter, new GeoJsonPoint(-74.044502D, 40.689247D));

		Query query = new MongoQueryCreator(tree, accessor, context).createQuery();
		assertThat(query.getQueryObject().containsKey("location"), is(true));
	}

	@Test // DATAMONGO-1588
	public void queryShouldThrowExceptionWhenArgumentDoesNotMatchDeclaration() {

		expection.expect(IllegalArgumentException.class);
		expection.expectMessage("Expected parameter type of " + Point.class);

		PartTree tree = new PartTree("findByLocationNear", User.class);
		ConvertingParameterAccessor accessor = getAccessor(converter,
				new GeoJsonLineString(new Point(-74.044502D, 40.689247D), new Point(-73.997330D, 40.730824D)));

		new MongoQueryCreator(tree, accessor, context).createQuery();
	}

	interface PersonRepository extends Repository<Person, Long> {

		List<Person> findByLocationNearAndFirstname(Point location, Distance maxDistance, String firstname);
	}

	class User {

		ObjectId id;

		@Field("foo") String username;

		@DBRef User creator;

		List<String> emailAddresses;

		Address address;

		Address2dSphere address2dSphere;

		Point location;
	}

	static class Address {

		String street;

		Point geo;
	}

	static class Address2dSphere {

		String street;

		@GeoSpatialIndexed(type = GeoSpatialIndexType.GEO_2DSPHERE) Point geo;
	}
}
