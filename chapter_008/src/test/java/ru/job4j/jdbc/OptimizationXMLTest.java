package ru.job4j.jdbc;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;
/**
 * @author Hincu Andrei (andreih1981@gmail.com)on 18.12.2017.
 * @version $Id$.
 * @since 0.1.
 */
public class OptimizationXMLTest {
    private final String xml1 = "1.xml";
    @Mock
    private
    ConnectionSqLite connectionSqLite;
    @Mock
    private
    Connection mocConnection;
    @Mock
    private
    Statement mocStatement;
    @Mock
    private PreparedStatement mocPreparedStatement;
    @Mock
    File file;
    int[]ar = {1};
    @Mock
    XMLOutputFactory factory;
    @Mock
    XMLStreamWriter writer;


    @Before
    public void setUp() throws SQLException, IOException, XMLStreamException {
        MockitoAnnotations.initMocks(this);
        when(connectionSqLite.getConnection()).thenReturn(mocConnection);
        when(mocConnection.createStatement()).thenReturn(mocStatement);
        when(mocConnection.prepareStatement(anyString())).thenReturn(mocPreparedStatement);

        when(mocStatement.executeUpdate(anyString())).thenReturn(1);

        doNothing().when(mocPreparedStatement).setInt(anyInt(), anyInt());
        doNothing().when(mocPreparedStatement).setString(anyInt(), anyString());
        when(mocPreparedStatement.executeBatch()).thenReturn(ar);
        when(file.getPath()).thenReturn(xml1);

       // when(factory).thenReturn(writer);
        doNothing().when(writer).writeStartDocument();
        doNothing().when(writer).writeEndDocument();
        doNothing().when(writer).writeEndElement();
        doNothing().when(writer).writeStartElement(anyString());

    }

    /**
     * Тест проверяет создание нового оптимизатора.
     * @throws Exception ех.
     */
    @Test
    public void createNewOptimizator() throws Exception {
        OptimizationXML opt = new OptimizationXML(connectionSqLite, 10);
        verify(connectionSqLite, times(1)).getConnection();
        assertThat(connectionSqLite.getConnection(), is(mocConnection));
    }

    /**
     * Тест проверяет создание и заполнение таблицы в бд.
     * @throws Exception ех.
     */
    @Test
    public void whenCalledCreateTable() throws Exception {
        OptimizationXML optimizationXML = new OptimizationXML(connectionSqLite, 10);
        optimizationXML.createTestTable();
        verify(mocConnection, times(1)).createStatement();
        verify(mocStatement, times(2)).executeUpdate(anyString());
        assertThat(mocStatement.executeUpdate(anyString()), is(1));
        verify(mocConnection, times(2)).setAutoCommit(anyBoolean());
        verify(mocPreparedStatement, times(optimizationXML.getElement())).setInt(anyInt(), anyInt());
        verify(mocPreparedStatement, times(optimizationXML.getElement())).addBatch();
        verify(mocPreparedStatement).executeBatch();
        assertThat(ar, is(mocPreparedStatement.executeBatch()));
    }

    @Test
    @Ignore
    public void whenCalledCreateFirstWithSax() throws Exception {
        OptimizationXML optimizationXML = new OptimizationXML(connectionSqLite, 10);
        optimizationXML.createFirstXmlWithSAX(file, factory);
        verify(file, times(1)).getPath();
        verify(mocConnection, times(1)).createStatement();
        verify(factory).createXMLStreamWriter(new BufferedWriter(new FileWriter(file)));
        verify(writer, times(1)).writeStartDocument();

    }
}