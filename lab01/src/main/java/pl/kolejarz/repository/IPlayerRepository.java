package pl.kolejarz.repository;

import java.sql.SQLClientInfoException;
import java.sql.SQLException;
import java.util.List;
import pl.kolejarz.domain.Player;

public interface IPlayerRepository
{
    public List<Player> getAll();
    public int add(Player p);
    public Player getById(long id) throws SQLException;
    public Player getByNickName(String name);
    public int update(Player p, long id) throws SQLException;
    public void delete(long id);
}