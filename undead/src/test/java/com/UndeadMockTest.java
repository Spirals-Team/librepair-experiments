package com;


import static org.junit.Assert.*;

import com.undeadcrud.domain.Undead;
import com.undeadcrud.repository.UndeadRepository;
import com.undeadcrud.repository.UndeadRepositoryImpl;
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


@RunWith(MockitoJUnitRunner.class)
public class UndeadMockTest {

    UndeadRepository undeadRepository;

    @Mock
    Connection connectionMock;

    @Mock
    UndeadRepository undeadRepositoryMock;

    @Mock
    PreparedStatement insertStatementMock;

    @Mock
    PreparedStatement selectStatementMock;



    @Before
    public void setupDatabase() throws SQLException {

        when(connectionMock.prepareStatement("INSERT INTO Undead (nazwa, typ, tier, lokacja, zdolnoscSpecjalna) VALUES (?, ?, ?, ?, ?)"))
                .thenReturn(insertStatementMock);
        when(connectionMock.prepareStatement("SELECT id, nazwa, typ, tier, lokacja, zdolnoscSpecjalna FROM Undead")).thenReturn(selectStatementMock);

        undeadRepository = new UndeadRepositoryImpl();
        undeadRepositoryMock = mock(UndeadRepositoryImpl.class);
        undeadRepository.setConnection(connectionMock);
        
        verify(connectionMock).prepareStatement("INSERT INTO Undead (nazwa, typ, tier, lokacja, zdolnoscSpecjalna) VALUES (?, ?, ?, ?, ?)");
        verify(connectionMock).prepareStatement("SELECT nazwa, typ, tier, lokacja, zdolnoscSpecjalna FROM Undead");
    }

    @Test
    public void checkAdding() throws Exception {
        when(insertStatementMock.executeUpdate()).thenReturn(1);

        Undead undead = new Undead();
        undead.setNazwa("Wojowniczy szkielet");
        undead.setTyp("Szkielet");
        undead.setTier(5);
        undead.setLokacja("Cmentarzysko");
        undead.setZdolnoscSpecjalna("Demoralizacja");
        assertEquals(1, undeadRepository.addUndead(undead));
        verify(insertStatementMock, times(1)).setString(1, "Wojowniczy szkielet");
        verify(insertStatementMock, times(1)).setString(2, "Szkielet");
        verify(insertStatementMock, times(1)).setInt(3, 5);
        verify(insertStatementMock, times(1)).setString(4, "Cmentarzysko");
        verify(insertStatementMock, times(1)).setString(5, "Demoralizacja");

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
            return "Zombi";
        }
        @Override
        public boolean next() throws SQLException {
            if (i == 1)
                return false;
            i++;
            return true;
        }
    }

    @Test
    public void checkGetting() throws Exception {
        AbstractResultSet mockedResultSet = mock(AbstractResultSet.class);
        when(mockedResultSet.next()).thenCallRealMethod();
        when(mockedResultSet.getInt("id")).thenCallRealMethod();
        when(mockedResultSet.getString("nazwa")).thenCallRealMethod();
        when(mockedResultSet.getString("typ")).thenCallRealMethod();
        when(mockedResultSet.getInt("tier")).thenCallRealMethod();
        when(mockedResultSet.getString("lokacja")).thenCallRealMethod();
        when(mockedResultSet.getString("zdolnoscSpecjalna")).thenCallRealMethod();
        when(selectStatementMock.executeQuery()).thenReturn(mockedResultSet);

        assertEquals(1, undeadRepository.getAll().size());

        verify(selectStatementMock, times(1)).executeQuery();
        verify(mockedResultSet, times(1)).getInt("id");
        verify(mockedResultSet, times(2)).getString("nazwa");
        verify(mockedResultSet, times(3)).getString("typ");
        verify(mockedResultSet, times(4)).getInt("tier");
        verify(mockedResultSet, times(5)).getString("lokacja");
        verify(mockedResultSet, times(6)).getString("zdolnoscSpecjalna");
        verify(mockedResultSet, times(7)).next();
    }

    @Test
    public void checkAddingInOrder() throws Exception {
        InOrder inorder = inOrder(insertStatementMock);
        when(insertStatementMock.executeUpdate()).thenReturn(1);

        Undead undead = new Undead();
        undead.setNazwa("Wojowniczy szkielet");
        undead.setTyp("Szkielet");
        undead.setTier(5);
        undead.setLokacja("Cmentarzysko");
        undead.setZdolnoscSpecjalna("Demoralizacja");
        assertEquals(1, undeadRepository.addUndead(undead));

        inorder.verify(insertStatementMock, times(1)).setString(1, "Wojowniczy szkielet");
        inorder.verify(insertStatementMock, times(1)).setString(1, "Szkielet");
        inorder.verify(insertStatementMock, times(1)).setInt(2, 5);
        inorder.verify(insertStatementMock, times(1)).setString(1, "Cmentarzysko");
        inorder.verify(insertStatementMock, times(1)).setString(1, "Demoralizacja");
        inorder.verify(insertStatementMock).executeUpdate();
    }

    @Test(expected = IllegalStateException.class)
    public void checkExceptionWhenAddingNullAdding() throws Exception {
        when(insertStatementMock.executeUpdate()).thenThrow(new SQLException());
        Undead undead = new Undead();
        undead.setNazwa(null); // ten null!
        undead.setTyp("Szkielet");
        undead.setTier(5);
        undead.setLokacja("Cmentarzysko");
        undead.setZdolnoscSpecjalna("Demoralizacja");
        assertEquals(1, undeadRepository.addUndead(undead));
    }
}





