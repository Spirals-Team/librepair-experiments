package ru.job4j;

import java.util.Arrays;
import org.junit.Test;
import static org.hamcrest.collection.IsArrayContainingInAnyOrder.*;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class MatrixTest {
	@Test
	public void whenConvertmatrixThenReturnNewMatrix() {
		
		Matrix matrix = new Matrix();
		int[][] m = new int[][] {{1, 4, 7}, {2, 5, 8}, {3, 6, 9}};
		int[][] result = matrix.convertWithMatrix(new int[][] {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}}); 
		assertThat(result, is(m));
	}
	
	@Test
	public void whenConvertMatrixWithOutMatrixThenReturnNewMatrix() {
		
		Matrix matrix = new Matrix();
		int[][] m = new int[][] {{1, 4, 7}, {2, 5, 8}, {3, 6, 9}};
		int[][] result = matrix.convertWithOutMatrix(new int[][] {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}}); 
		assertThat(result, is(m));
	}
}