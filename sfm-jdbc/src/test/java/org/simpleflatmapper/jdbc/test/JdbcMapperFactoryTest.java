package org.simpleflatmapper.jdbc.test;

import org.junit.Test;
import org.simpleflatmapper.jdbc.JdbcColumnKey;
import org.simpleflatmapper.jdbc.JdbcMapper;
import org.simpleflatmapper.jdbc.JdbcMapperFactory;
import org.simpleflatmapper.reflect.getter.GetterFactory;
import org.simpleflatmapper.map.FieldMapper;
import org.simpleflatmapper.map.FieldMapperErrorHandler;
import org.simpleflatmapper.map.MappingContext;
import org.simpleflatmapper.map.ConsumerErrorHandler;
import org.simpleflatmapper.test.beans.DbFinalObject;
import org.simpleflatmapper.test.beans.DbObject;
import org.simpleflatmapper.test.beans.DbObjectWithAlias;
import org.simpleflatmapper.reflect.Getter;
import org.simpleflatmapper.util.TypeReference;
import org.simpleflatmapper.test.jdbc.DbHelper;
import org.simpleflatmapper.test.jdbc.TestRowHandler;
import org.simpleflatmapper.tuple.Tuple2;
import org.simpleflatmapper.tuple.Tuples;
import org.simpleflatmapper.util.ListCollector;
import org.simpleflatmapper.util.CheckedConsumer;

import java.lang.reflect.Type;
import java.sql.*;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

public class JdbcMapperFactoryTest {

	JdbcMapperFactory asmFactory = JdbcMapperFactoryHelper.asm();
	JdbcMapperFactory nonAsmFactory = JdbcMapperFactoryHelper.noAsm();


	@Test
	public void testFactoryOnTuples() {
		assertNotNull(asmFactory.newMapper(Tuples.typeDef(Date.class, Date.class)));
		assertNotNull(asmFactory.newBuilder(Tuples.typeDef(Date.class, Date.class)));
	}

    @Test
    public void testFactoryOnReferenceType() throws SQLException {
        ResultSet rs = mock(ResultSet.class);
        ResultSetMetaData metaData = mock(ResultSetMetaData.class);
        when(rs.getMetaData()).thenReturn(metaData);

        when(metaData.getColumnCount()).thenReturn(2);
        when(metaData.getColumnLabel(1)).thenReturn("e0");
        when(metaData.getColumnLabel(2)).thenReturn("e1");
        when(metaData.getColumnType(1)).thenReturn(Types.VARCHAR);
        when(metaData.getColumnType(2)).thenReturn(Types.VARCHAR);
        when(rs.next()).thenReturn(true, false);

        when(rs.getString(1)).thenReturn("v1");
        when(rs.getString(2)).thenReturn("v2");

        Tuple2<String, String> tuple2 = JdbcMapperFactoryHelper.asm().newMapper(new TypeReference<Tuple2<String, String>>() {
        }).iterator(rs).next();

        assertEquals("v1", tuple2.first());
        assertEquals("v2", tuple2.second());
    }

    @Test
    public void testFactoryOnReferenceTypeStatic() throws SQLException {
        ResultSet rs = mock(ResultSet.class);
        when(rs.next()).thenReturn(true, false);

        when(rs.getString(1)).thenReturn("v1");
        when(rs.getString(2)).thenReturn("v2");

        Tuple2<String, String> tuple2 = JdbcMapperFactoryHelper.asm().newBuilder(new TypeReference<Tuple2<String, String>>() {
        }).addMapping("e0").addMapping("e1").mapper()
                .iterator(rs).next();

        assertEquals("v1", tuple2.first());
        assertEquals("v2", tuple2.second());
    }
    @Test
	public void testAsmDbObjectMappingFromDbWithMetaData()
			throws Exception {
		DbHelper.testDbObjectFromDb(new TestRowHandler<PreparedStatement>() {
			@Override
			public void handle(PreparedStatement ps) throws Exception {
				ResultSet rs = ps.executeQuery();
				JdbcMapper<DbObject> mapper = asmFactory.newMapper(DbObject.class, rs.getMetaData());
				assertMapPsDbObject(rs, mapper);
			}
		});
	}
	
	@Test
	public void testAsmDbObjectWithAliasMappingFromDbWithMetaData()
			throws Exception {
		DbHelper.testDbObjectFromDb(new TestRowHandler<PreparedStatement>() {
			@Override
			public void handle(PreparedStatement ps) throws Exception {
				ResultSet rs = ps.executeQuery();
				JdbcMapper<DbObjectWithAlias> mapper = asmFactory.newMapper(DbObjectWithAlias.class, rs.getMetaData());
				assertMapPsDbObjectWithAlias(rs, mapper);
			}
		});
	}
	
	@Test
	public void testNonAsmDbObjectMappingFromDbWithMetaData()
			throws Exception {
		DbHelper.testDbObjectFromDb(new TestRowHandler<PreparedStatement>() {
			@Override
			public void handle(PreparedStatement ps) throws Exception {
				ResultSet rs = ps.executeQuery();
				JdbcMapper<DbObject> mapper = nonAsmFactory.newMapper(DbObject.class, rs.getMetaData());
				assertMapPsDbObject(rs, mapper);
            }
		});
	}
	
	@Test
	public void testAsmDbObjectMappingFromDbDynamic()
			throws SQLException, Exception, ParseException {
		DbHelper.testDbObjectFromDb(new TestRowHandler<PreparedStatement>() {
			@Override
			public void handle(PreparedStatement ps) throws Exception {
				JdbcMapper<DbObject> mapper = asmFactory.newMapper(DbObject.class);
				assertMapPsDbObject(ps.executeQuery(), mapper);
			}
		});
	}
	
	@Test
	public void testNonAsmDbObjectMappingFromDbDynamic()
			throws SQLException, Exception, ParseException {
		DbHelper.testDbObjectFromDb(new TestRowHandler<PreparedStatement>() {
			@Override
			public void handle(PreparedStatement ps) throws Exception {
				JdbcMapper<DbObject> mapper = nonAsmFactory.newMapper(DbObject.class);
				assertMapPsDbObject(ps.executeQuery(), mapper);
			}
		});
	}
	
	@Test
	public void testAsmFinalDbObjectMappingFromDbDynamic()
			throws SQLException, Exception, ParseException {
		DbHelper.testDbObjectFromDb(new TestRowHandler<PreparedStatement>() {
			@Override
			public void handle(PreparedStatement ps) throws Exception {
				JdbcMapper<DbFinalObject> mapper = asmFactory.newMapper(DbFinalObject.class);
				assertMapPsFinalDbObject(ps.executeQuery(), mapper);
			}
		});
	}
	
	@Test
	public void testNonAsmFinalDbObjectMappingFromDbDynamic()
			throws SQLException, Exception, ParseException {
		DbHelper.testDbObjectFromDb(new TestRowHandler<PreparedStatement>() {
			@Override
			public void handle(PreparedStatement ps) throws Exception {
				JdbcMapper<DbFinalObject> mapper = nonAsmFactory.newMapper(DbFinalObject.class);
				assertMapPsFinalDbObject(ps.executeQuery(), mapper);
			}
		});
	}	
	
	@Test
	public void testFieldErrorHandling()
			throws SQLException, Exception, ParseException {
		@SuppressWarnings("unchecked")
        FieldMapperErrorHandler<JdbcColumnKey> fieldMapperErrorHandler  = mock(FieldMapperErrorHandler.class);
		final Exception exception = new Exception("Error!");
		JdbcMapper<DbObject> mapper = JdbcMapperFactoryHelper.asm()
			.fieldMapperErrorHandler(fieldMapperErrorHandler)
			.addCustomFieldMapper("id",  new FieldMapper<ResultSet, DbObject>() {
				@Override
				public void mapTo(ResultSet source, DbObject target, MappingContext<? super ResultSet> mappingContext) throws Exception {
					throw exception;
				}
			}).newBuilder(DbObject.class).addMapping("id").mapper();
		
		List<DbObject> list = mapper.forEach(new MockDbObjectResultSet(1), new ListCollector<DbObject>()).getList();
		assertNotNull(list.get(0));
		verify(fieldMapperErrorHandler).errorMappingField(eq(new JdbcColumnKey("id", 1)), any(), same(list.get(0)), same(exception));
	}
	
	
	@Test
	public void testFieldErrorHandlingOnResultSet()
			throws SQLException, Exception, ParseException {
		@SuppressWarnings("unchecked")
		FieldMapperErrorHandler<JdbcColumnKey> fieldMapperErrorHandler  = mock(FieldMapperErrorHandler.class);
		ResultSet rs = mock(ResultSet.class);
		
		final Exception exception = new SQLException("Error!");
		JdbcMapper<DbObject> mapper = JdbcMapperFactoryHelper.asm()
			.fieldMapperErrorHandler(fieldMapperErrorHandler)
			.newBuilder(DbObject.class).addMapping("id").mapper();
		
		when(rs.next()).thenReturn(true, false);
		when(rs.getLong(1)).thenThrow(exception);
		
		List<DbObject> list = mapper.forEach(rs, new ListCollector<DbObject>()).getList();
		assertNotNull(list.get(0));
		verify(fieldMapperErrorHandler).errorMappingField(eq(new JdbcColumnKey("id", 1)), any(), same(list.get(0)), same(exception));

	}

	@Test
	public void testSetCheckedConsumerError() throws SQLException {
		ConsumerErrorHandler errorHandler = mock(ConsumerErrorHandler.class);
		ResultSet rs = mock(ResultSet.class);
		when(rs.next()).thenReturn(true, true, false);
		when(rs.getLong(1)).thenReturn(1l);

		final Exception exception = new SQLException("Error!");
		JdbcMapper<DbObject> mapper = JdbcMapperFactoryHelper.asm()
				.consumerErrorHandler(errorHandler)
				.newBuilder(DbObject.class).addMapping("id").mapper();

		mapper.forEach(rs, new CheckedConsumer<DbObject>() {
			@Override
			public void accept(DbObject dbObject) throws Exception {
				throw exception;
			}
		});
		verify(errorHandler, times(2)).handlerError(same(exception), any(DbObject.class));

	}

	@Test
	public void testCustomGetterFactory() throws SQLException {
		JdbcMapper<DbObject> mapper = JdbcMapperFactoryHelper.asm().getterFactory(new GetterFactory<ResultSet, JdbcColumnKey>() {
			@SuppressWarnings("unchecked")
			@Override
			public <P> Getter<ResultSet, P> newGetter(Type target, JdbcColumnKey key, Object... properties) {
				return new Getter() {
					@Override
					public Object get(Object target) throws Exception {
						return "Hello!";
					}
				}
						;
			}
		}).newBuilder(DbObject.class).addMapping("name").mapper();

		ResultSet rs = mock(ResultSet.class);
		when(rs.next()).thenReturn(true, false);

		DbObject object = mapper.iterator(rs).next();

		assertEquals("Hello!", object.getName());
	}


	private void assertMapPsDbObject(ResultSet rs,
			JdbcMapper<DbObject> mapper) throws Exception,
			ParseException {
		List<DbObject> list = mapper.forEach(rs, new ListCollector<DbObject>()).getList();
		assertEquals(1,  list.size());
		DbHelper.assertDbObjectMapping(list.get(0));
	}
	
	private void assertMapPsDbObjectWithAlias(ResultSet rs,
			JdbcMapper<DbObjectWithAlias> mapper) throws Exception,
			ParseException {
		List<DbObjectWithAlias> list = mapper.forEach(rs, new ListCollector<DbObjectWithAlias>()).getList();
		assertEquals(1,  list.size());
		DbHelper.assertDbObjectWithAliasMapping(list.get(0));
	}
	
	private void assertMapPsFinalDbObject(ResultSet rs,
			JdbcMapper<DbFinalObject> mapper) throws Exception,
			ParseException {
		List<DbFinalObject> list = mapper.forEach(rs, new ListCollector<DbFinalObject>()).getList();
		assertEquals(1,  list.size());
		DbHelper.assertDbObjectMapping(list.get(0));
	}
}
