/*
 * Copyright (C) 2018 Tmejs
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.tmejs.app;

import com.tmejs.db.domain.Nauczyciel;
import com.tmejs.db.service.NauczycielManager;
import com.tmejs.db.service.NauczycielManagerImpl;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.sql.*;

import java.sql.SQLException;

import static org.mockito.AdditionalMatchers.gt;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

/**
 * @author Tmejs
 */
@RunWith(MockitoJUnitRunner.class)
public class NauczycielManagerMockTest {

    NauczycielManager manager;

    @Mock
    Connection connectionMock;

    @Mock
    NauczycielManager managerMock;

    @Mock
    PreparedStatement insertStatementMock;

    @Mock
    PreparedStatement selectStatementMock;

    @Before
    public void setupDatabase() throws SQLException {

        when(connectionMock.prepareStatement("INSERT INTO Nauczyciel (id,imie,nazwisko) VALUES (?,?,?)")).thenReturn(insertStatementMock);
        when(connectionMock.prepareStatement("SELECT * FROM Nauczyciel")).thenReturn(selectStatementMock);

        manager = new NauczycielManagerImpl();
        managerMock = mock(NauczycielManagerImpl.class);
        manager.setConnection(connectionMock);

//        ((NauczycielManagerImpl) manager).connection = connectionMock;

        verify(connectionMock).prepareStatement("INSERT INTO Nauczyciel (id,imie,nazwisko) VALUES (?,?,?)");
        verify(connectionMock).prepareStatement("SELECT * FROM Nauczyciel");
    }

    @Test
    public void checkAdding() throws Exception {
        when(insertStatementMock.executeUpdate()).thenReturn(1);

        Nauczyciel nauczyciel = new Nauczyciel();
        nauczyciel.id = 1;
        nauczyciel.Imie = "imie";
        nauczyciel.Nazwisko = "nazwisko";
        manager.addNauczyciel(nauczyciel);

//        assertEquals(1, manager.addNauczyciel(nauczyciel));
        verify(insertStatementMock, times(1)).setInt(1, 1);
        verify(insertStatementMock, times(1)).setString(2, "imie");
        verify(insertStatementMock, times(1)).setString(3, "nazwisko");
        verify(insertStatementMock).executeUpdate();
    }

    abstract class AbstractResultSet implements ResultSet {

        int i = 0;

        @Override
        public int getInt(String s) throws SQLException {
            return 1;
        }

        @Override
        public String getString(String columnLabel) throws SQLException {
            return "Majran";
        }

        @Override
        public boolean next() throws SQLException {
            if (i == 1) {
                return false;
            }
            i++;
            return true;
        }
    }

    @Test
    public void checkGetting() throws Exception {
        AbstractResultSet mockedResultSet = mock(AbstractResultSet.class);
        when(mockedResultSet.next()).thenCallRealMethod();
        when(mockedResultSet.getInt("id")).thenCallRealMethod();
        when(mockedResultSet.getString("imie")).thenCallRealMethod();
        when(mockedResultSet.getString("nazwisko")).thenCallRealMethod();
        when(selectStatementMock.executeQuery()).thenReturn(mockedResultSet);

        assertEquals(1, manager.getAllNauczyciels().size());

        verify(selectStatementMock, times(1)).executeQuery();
        verify(mockedResultSet, times(1)).getInt("id");
        verify(mockedResultSet, times(1)).getString("imie");
        verify(mockedResultSet, times(1)).getString("nazwisko");
        verify(mockedResultSet, times(2)).next();
    }

    @Test
    public void checkAddingInOrder() throws Exception {
        InOrder inorder = inOrder(insertStatementMock);
        when(insertStatementMock.executeUpdate()).thenReturn(1);

        Nauczyciel nauczyciel = new Nauczyciel();
        nauczyciel.id = 1;
        nauczyciel.Imie = "imie";
        nauczyciel.Nazwisko = "nazwisko";
        assertEquals(1, manager.addNauczyciel(nauczyciel));

        inorder.verify(insertStatementMock, times(1)).setInt(1, 1);
        inorder.verify(insertStatementMock, times(1)).setString(2, "imie");
        inorder.verify(insertStatementMock, times(1)).setString(3, "nazwisko");
        inorder.verify(insertStatementMock).executeUpdate();
    }

    @Test(expected = IllegalStateException.class)
    public void checkExceptionWhenAddingNullAdding() throws Exception {
        when(insertStatementMock.executeUpdate()).thenThrow(new SQLException());
        Nauczyciel nauczyciel = new Nauczyciel();
        nauczyciel.id = 1;
        nauczyciel.Imie = null;
        nauczyciel.Nazwisko = "Nazwisko";
        assertEquals(1, manager.addNauczyciel(nauczyciel));

    }

}