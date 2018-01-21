package com.pufose.server;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
@Profile("mysql")
public class MySqlImplementor implements IServiceImplementor {

	@Autowired
	private IMySqlRepository repo;
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Override
	public List<String> getAllId() {
		Iterable<SqlGrid> allGrids = repo.findAll();
		List<String> allid = new ArrayList<String>();
		allGrids.forEach(grid -> allid.add("" + grid.getId()));
		return allid;
	}

	@Override
	public DatabaseGrid getById(int id) {

		SqlGrid sqlgrid=repo.findOne(new Long(id));
		if(sqlgrid!=null)
			return sqlgrid.toDatabase();
		else
			return null;
	}

	@Override
	public void storeInDb(DatabaseGrid grid) {
		String matrix=Arrays.deepToString(grid.getMatrix()).replaceAll("[^\\d]", "");
		jdbcTemplate.update( 
				"INSERT INTO sql_grid VALUES (?, ?, ?)",
				grid.getId(),matrix,grid.getN()
				);

	}

	@Override
	public List<DatabaseGrid> getAllGrids() {
		Iterable<SqlGrid> allGrids = repo.findAll();
		List<DatabaseGrid> allgrids = new ArrayList<DatabaseGrid>();
		allGrids.forEach(grid -> allgrids.add(grid.toDatabase()));
		return allgrids;
	}

	@Override
	public int nextId() {
		Iterable<SqlGrid> allGrids = repo.findAll();
		List<SqlGrid> casted = (List<SqlGrid>) (allGrids);
		if (casted.isEmpty())
			return 1;
		long maxid = casted.get(casted.size() - 1).getId();
		return (int)maxid + 1;
	}

	@Override
	public void dropTable(int id) {
		jdbcTemplate.update( 
				"DELETE FROM sql_grid " + 
				"WHERE id = ?",id
				);
	}

}
