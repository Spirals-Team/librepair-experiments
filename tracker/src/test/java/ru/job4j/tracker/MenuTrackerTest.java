package ru.job4j.tracker;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.job4j.tracker.connection.ConnectionSQL;
import ru.job4j.tracker.models.Comment;
import ru.job4j.tracker.models.Item;
import ru.job4j.tracker.start.Tracker;

import java.sql.*;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

/**
 * @author Hincu Andrei (andreih1981@gmail.com)on 16.12.2017.
 * @version $Id$.
 * @since 0.1.
 */
public class MenuTrackerTest {
    @Mock
    private Tracker tracker;
    @Mock
    private Connection connection;
    @Mock
    private Statement mocStatment;
    @Mock
    private PreparedStatement mocPreparedStatement;
    @Mock
    private ResultSet mocResultSet;
    @Mock
    private ConnectionSQL connectionSQL;
    @Mock
    private Item item;


    @Before
    public void setUp() throws SQLException {
        MockitoAnnotations.initMocks(this);
        when(connectionSQL.getConnection()).thenReturn(connection);
        when(connection.createStatement()).thenReturn(mocStatment);
        when(connection.prepareStatement(anyString(), anyInt())).thenReturn(mocPreparedStatement);
        when(connection.prepareStatement(anyString())).thenReturn(mocPreparedStatement);

        when(mocStatment.executeUpdate(any())).thenReturn(1);
        when(mocStatment.executeQuery(any())).thenReturn(mocResultSet);
        when(mocStatment.executeUpdate(anyString())).thenReturn(1);

        when(mocPreparedStatement.getGeneratedKeys()).thenReturn(mocResultSet);
        doNothing().when(mocPreparedStatement).setInt(anyInt(), anyInt());
        doNothing().when(mocPreparedStatement).setString(anyInt(), anyString());
        doNothing().when(mocPreparedStatement).setTimestamp(anyInt(), any());
        when(mocPreparedStatement.executeQuery()).thenReturn(mocResultSet);
        when(mocPreparedStatement.executeUpdate()).thenReturn(1);

        when(mocResultSet.next()).thenReturn(Boolean.TRUE, Boolean.FALSE);
        when(mocResultSet.getInt(anyInt())).thenReturn(1);
        when(mocResultSet.getString("id")).thenReturn("1");
        when(mocResultSet.getInt("id")).thenReturn(1);
        when(mocResultSet.getString("name")).thenReturn("test");
        when(mocResultSet.getString("description")).thenReturn("testDescription");
        when(mocResultSet.getTimestamp(anyString())).thenReturn(new Timestamp(System.currentTimeMillis()));
        when(item.getName()).thenReturn("name");
        when(item.getDesc()).thenReturn("test");
        when(item.getId()).thenReturn("1");
        when(item.getCreated()).thenReturn(123L);

    }

    /**
     * Тест последовательности вызовов создания трэкера.
     * @throws SQLException ех.
     */
    @Test
    public void whenNewTrackerCreatedThenTestInit() throws SQLException {
        new Tracker(connectionSQL);
        verify(connectionSQL, times(1)).getConnection();
        verify(connection, times(1)).createStatement();
        verify(mocStatment, times(1)).executeUpdate(anyString());
        assertThat(mocStatment.executeUpdate(anyString()), is(1));
    }

    /**
     *Тест проверяет колличество вызовов при добавлении новой заявки.
     * @throws Exception ех.
     */
    @Test
    public void whenAddedNewItem() throws Exception {
        Item result = new Tracker(connectionSQL).add(item);
        verify(connection, times(1)).prepareStatement(anyString(), anyInt());
        verify(mocPreparedStatement, times(2)).setString(anyInt(), anyString());
        verify(mocPreparedStatement, times(1)).setTimestamp(anyInt(), any());
        verify(mocPreparedStatement, times(1)).executeUpdate();
        verify(mocPreparedStatement, times(1)).getGeneratedKeys();
        verify(mocResultSet, times(2)).next();
        verify(mocResultSet, times(1)).getInt(1);
        verify(item, times(1)).setId(String.valueOf(1));
        assertThat(result.getId(), is("1"));
    }

    /**
     * Тест последовательности вызовов методов при обновлении заявки.
     * @throws Exception ех.
     */
    @Test
    public void whenItemWasUpdate() throws Exception {
        new Tracker(connectionSQL).update(item);
        verify(connection, times(1)).prepareStatement(anyString());
        verify(mocPreparedStatement, times(2)).setString(anyInt(), anyString());
        verify(mocPreparedStatement, times(1)).setInt(anyInt(), anyInt());
        verify(mocPreparedStatement, times(1)).executeUpdate();
        assertThat(mocPreparedStatement.executeUpdate(), is(1));
    }

    /**
     * Тест проверяет последовательность и колличество вызовов при  удалении заявки.
     * @throws Exception ех.
     */
    @Test
    public void whenItemWasDelete() throws Exception {
        new Tracker(connectionSQL).delete(item);
        verify(connection, times(2)).prepareStatement(anyString());
        verify(item, times(2)).getId();
        assertThat(item.getId(), is("1"));
        verify(mocPreparedStatement, times(2)).setInt(anyInt(), anyInt());
        verify(mocPreparedStatement, times(2)).executeUpdate();
        assertThat(mocPreparedStatement.executeUpdate(), is(1));
    }

    /**
     *Тест проверяет последовательность вызовов методов при показе всех заявок.
     * @throws Exception ex.
     */
    @Test
    public void whenWasCalledGetAllMethod() throws Exception {
        List<Item> result = new Tracker(connectionSQL).getAll();
        verify(connection, times(2)).createStatement();
        verify(mocStatment, times(1)).executeQuery(anyString());
        verify(mocResultSet, times(2)).next();
        verify(mocResultSet).getString("id");
        verify(mocResultSet).getString("name");
        verify(mocResultSet).getString("description");
        verify(mocResultSet, times(1)).getTimestamp(anyString());
        Item resItem = result.get(0);
        assertThat(resItem.getName(), is("test"));
        assertThat(resItem.getId(), is("1"));
    }

    /**
     * Тест проверяет последовательность и колличество вызовов при поиске заявки по имени.
     * @throws Exception ех.
     */
    @Test
    public void whenWasCalledFindByName() throws Exception {
        List<Item> result = new Tracker(connectionSQL).findByName("test");
        verify(connection, times(1)).prepareStatement(anyString());
        verify(mocPreparedStatement, times(1)).setString(anyInt(), anyString());
        verify(mocResultSet, times(2)).next();
        verify(mocResultSet).getString("id");
        verify(mocResultSet).getString("name");
        verify(mocResultSet).getString("description");
        verify(mocResultSet, times(1)).getTimestamp(anyString());
        Item resItem = result.get(0);
        assertThat(item.getName(), is("name"));
        assertThat(resItem.getName(), is("test"));
        assertThat(resItem.getId(), is("1"));
    }

    /**
     * Тест проверяет последовательность и колличество вызовов при поиске по ID.
     * @throws Exception ex.
     */
    @Test
    public void whenWasCalledFindById() throws Exception {
        Item result = new Tracker(connectionSQL).findById("1");
        verify(connection, times(2)).prepareStatement(anyString());
        verify(mocPreparedStatement, times(2)).setInt(anyInt(), anyInt());
        verify(mocPreparedStatement, times(2)).executeQuery();
        verify(mocResultSet, times(3)).next();
        verify(mocResultSet).getString("id");
        verify(mocResultSet).getString("name");
        verify(mocResultSet).getString("description");
        verify(mocResultSet, times(1)).getTimestamp(anyString());
        assertThat(item.getId(), is("1"));
        assertThat(result.getName(), is("test"));
        assertThat(result.getId(), is("1"));
    }

    /**
     * Тест проверяет колличество и последовательность вызовов при показе всех коментариев к заявке.
     * @throws Exception ех.
     */
    @Test
    public void whenWasCalledGetAllComments() throws Exception {
        List<Comment> result = new Tracker(connectionSQL).getAllComments("1");
        verify(connection, times(2)).createStatement();
        verify(connection, times(1)).prepareStatement(anyString());
        verify(mocStatment, times(2)).executeUpdate(anyString());
        verify(mocPreparedStatement, times(1)).setInt(anyInt(), anyInt());
        verify(mocPreparedStatement, times(1)).executeQuery();
        verify(mocResultSet, times(2)).next();
        verify(mocResultSet, times(1)).getInt("id");
        verify(mocResultSet, times(1)).getString("description");
        verify(mocResultSet, times(1)).getTimestamp(any());
        Comment comment = result.get(0);
        assertThat(comment.getId(), is(1));
        assertThat(comment.getText(), is("testDescription"));
    }

    /**
     * Тест проверяет последовательность и колличество вызовов при добавлении нового коментария.
     * @throws Exception ех.
     */
    @Test
    public void whenWasCalledAddNewComment() throws Exception {
        new Tracker(connectionSQL).addNewComment("1", "test1");
        verify(connection, times(2)).createStatement();
        verify(connection, times(1)).prepareStatement(anyString());
        verify(mocStatment, times(2)).executeUpdate(anyString());
        verify(mocPreparedStatement, times(1)).setInt(anyInt(), anyInt());
        verify(mocPreparedStatement, times(1)).setString(anyInt(), anyString());
        verify(mocPreparedStatement, times(1)).setTimestamp(anyInt(), any());
        verify(mocPreparedStatement, times(1)).executeUpdate();

    }

    /**
     * Тест прикотором удаляются все коментарии из заявки.
     * @throws Exception ех.
     */
    @Test
    public void whenWasCalledDeleteAllComments() throws Exception {
        new Tracker(connectionSQL).deleteComment("1", "all");
        verify(connection, times(1)).prepareStatement(anyString());
        verify(mocPreparedStatement, times(1)).setInt(anyInt(), anyInt());
        verify(mocPreparedStatement, times(1)).executeUpdate();
        assertThat(mocPreparedStatement.executeUpdate(), is(1));
    }

    /**
     * Тест при котором удаляется один коментарий.
     * @throws Exception ех.
     */
    @Test
    public void whenWasCalledDeleteOneComments() throws Exception {
        new Tracker(connectionSQL).deleteComment("1", "2");
        verify(connection, times(1)).prepareStatement(anyString());
        verify(mocPreparedStatement, times(2)).setInt(anyInt(), anyInt());
        verify(mocPreparedStatement, times(1)).executeUpdate();
        assertThat(mocPreparedStatement.executeUpdate(), is(1));
    }
}
