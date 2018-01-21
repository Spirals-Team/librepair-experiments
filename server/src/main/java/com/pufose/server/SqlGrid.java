package com.pufose.server;

import java.util.Arrays;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
@Entity
public class SqlGrid {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	
	private String matrix;
	
	private long n;
	
	public SqlGrid() {
		this.n = 0;
		this.id = 0;
		this.matrix="";
	}
	public SqlGrid(long id) {
		this.n = 0;
		this.id = id;
		this.matrix = "";
	}
	public SqlGrid(long id, int[][] matrix) {
		for (int i = 0, l = matrix.length; i < l; i++) {
			if (matrix[i].length != l) {
				throw new IllegalArgumentException("Matrix must be square");
			}
		}
		this.n=matrix.length;
		this.id=id;
		this.matrix=Arrays.deepToString(matrix).replaceAll("[^\\d]", "");
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((matrix == null) ? 0 : matrix.hashCode());
		result = prime * result + (int) (n ^ (n >>> 32));
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
		SqlGrid other = (SqlGrid) obj;
		if (id != other.id)
			return false;
		if (matrix == null) {
			if (other.matrix != null)
				return false;
		} else if (!matrix.equals(other.matrix))
			return false;
		if (n != other.n)
			return false;
		return true;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getMatrix() {
		return matrix;
	}
	public long getN() {
		return n;
	}
	public void setN(long n) {
		this.n = n;
	}
	public String getName(int i, int j) { 
		return i + "_" + j;
	}
	public boolean isEnabled(int i, int j) {
		return matrix.charAt((int)(i*n+j))-48>0;
	}
	public DatabaseGrid toDatabase() {
		int[][] matrix = new int[(int)n][(int)n];
		int contiter = 0;
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				try {
					matrix[i][j] = Integer.parseInt(String.format("%c", this.matrix.charAt(contiter++)));
				} catch (Exception exc) {

					matrix[i][j] = 0;
				}
			}
		}
		return new DatabaseGrid(matrix,(int)this.id);
	}

}
