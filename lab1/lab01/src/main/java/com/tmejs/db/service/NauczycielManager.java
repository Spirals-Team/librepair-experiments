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
package com.tmejs.db.service;

import com.tmejs.db.domain.Nauczyciel;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Tmejs
 */
public interface NauczycielManager {

    public Connection getConnection();

    public void setConnection(Connection connection) throws SQLException;

    public int addNauczyciel(Nauczyciel person);
    
    public Nauczyciel getNauczyciel(Integer id);
    
    public Boolean updateNauczyciel(Nauczyciel nauczyciel);
    
    public Boolean deleteNauczyciel(Nauczyciel nauczyciel);
    
    public List<Nauczyciel> getAllNauczyciels();
    public boolean isDatabaseReady();
}
