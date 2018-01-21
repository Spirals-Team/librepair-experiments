package com.pufose.server;

import java.util.Arrays;




public class DatabaseGrid {

	public DatabaseGrid(int[][] matrix, int id) {
		for (int i = 0, l = matrix.length; i < l; i++) {
			if (matrix[i].length != l) {
				throw new IllegalArgumentException("Matrix must be square");
			}
		}
		this.matrix = matrix;
		this.n = matrix.length;
		this.id = id;
	}

	public DatabaseGrid(int id) {
		this.n = 0;
		this.id = id;
		this.matrix = new int[0][0];
	}

	public DatabaseGrid() {
		this.n = 0;
		this.id = 0;
		this.matrix = new int[0][0];
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + Arrays.deepHashCode(matrix);
		result = prime * result + n;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DatabaseGrid other = (DatabaseGrid) obj;
		if (id != other.id)
			return false;
		if (!Arrays.deepEquals(matrix, other.matrix))
			return false;
		if (n != other.n)
			return false;
		return true;
	}

	private int n;
	private int[][] matrix;
	
	private int id;

	public int getN() {
		return n;
	}

	public void setN(int n) {
		this.n = n;
	}

	public int[][] getMatrix() {
		return matrix;
	}

	public boolean isEnabled(int i, int j) {
		try {
			return matrix[i][j] > 0;
		} catch (ArrayIndexOutOfBoundsException exc) {
			return false;
		}
	}

	public String getName(int i, int j) { 
		return i + "_" + j;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public SqlGrid toSql() {
		return new SqlGrid(this.id,matrix);
	}


}
