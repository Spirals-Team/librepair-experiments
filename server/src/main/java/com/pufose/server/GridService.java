package com.pufose.server;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GridService extends IGridService {


	private static final String D_D = "%d_%d";
	@Autowired
	private Graph tobuild;

	@Override
	public List<String> getAllId() {
		return super.getAllId();

	}

	@Override
	public List<String> getShortestPath(String from, String to, int id) {
		DatabaseGrid grid=getById(id);
		tobuild.removeAllNodes();
		addNodes(grid);
		for(String node:tobuild.getNodes()) {
			addEdges(grid, node);
		}
		return tobuild.minPath(from,to);
		
	}

	private void addEdges(DatabaseGrid grid, String node) {
		int i = node.charAt(0) - 48;
		int j = node.charAt(2) - 48;

		if (grid.isEnabled(i + 1, j)) {
			String target = String.format(D_D, i + 1, j);

			tobuild.addEdge(node, target);
		}
		if (grid.isEnabled(i - 1, j)) {
			String target = String.format(D_D, i - 1, j);
			tobuild.addEdge(node, target);
		}
		if (grid.isEnabled(i, j + 1)) {
			String target = String.format(D_D, i, j + 1);

			tobuild.addEdge(node, target);
		}
		if (grid.isEnabled(i, j - 1)) {
			String target = String.format(D_D, i, j - 1);

			tobuild.addEdge(node, target);
		}
	}

	private void addNodes(DatabaseGrid grid) {
		int n = grid.getN();
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if (grid.isEnabled(i, j)) {
					tobuild.addNodes(grid.getName(i, j));

				}
			}
		}
	}

	@Override
	public DatabaseGrid getById(int id) {
		return super.getById(id);
	}

	@Override
	public void storeInDb(DatabaseGrid grid) {
		super.storeInDb(grid);

	}

	@Override
	public List<DatabaseGrid> getAllGrids() {
		return super.getAllGrids();
	}

	public int nextId() {
		return super.nextId();
	}

	public void dropTable(int id) {
		super.dropTable(id);

	}

	


}
