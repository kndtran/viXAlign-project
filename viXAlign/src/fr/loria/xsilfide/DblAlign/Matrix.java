/*
 * @(#)       Matrix.java
 * 
 * 
 * 
 * 
 * @version   
 * @author    Patrice Bonhomme
 * Copyright  1997 (C) PATRICE BONHOMME
 *            CRIN/CNRS & INRIA Lorraine
 *
 */

package fr.loria.xsilfide.DblAlign;

// import
import java.io.*;

public class Matrix
{

	protected int[][] data;
	protected int num_rows;
	protected int num_cols;

	/*
	 * Construct & Initialize to 0.0
	 */
	public Matrix (int rows, int cols)
	{
		this (rows, cols, 0);
	}

	/*
	 * Construct & Initialize to init_value
	 */
	public Matrix (int rows, int cols, int init_value)
	{
		num_rows = rows;
		num_cols = cols;

		data = new int[num_rows][num_cols];

		for (int i = 0; i < num_rows; i++)
			for (int j = 0; j < num_cols; j++)
				data[i][j] = init_value;    
	}

	/*
	 * Construct & Initialize to data in raw_data
	 */
	public Matrix (int[][] raw_data)
	{
		num_rows = raw_data.length;
		num_cols = raw_data[0].length;

		data = new int[num_rows][num_cols];

		for (int i = 0; i < num_rows; i++)
			System.arraycopy (raw_data[i], 0, data[i], 0, num_cols);
	}

	/*
	 * Construct from an Input Stream
	 */
	public Matrix (InputStream input) throws IOException
	{
		DataInputStream in = new DataInputStream (input);

		num_rows = in.readInt();
		num_cols = in.readInt();

		data = new int[num_rows][num_cols];

		for (int i = 0; i < num_rows; i++)
			for (int j = 0; j < num_cols; j++)
				data[i][j] = in.readInt();
	}

	public int getElem(int row, int col)
	{
		if (row < 0 || row >= num_rows) return 0;
		if (col < 0 || col >= num_cols) return 0;
		return data[row][col];
	}

	public void setElem(int row, int col, int x)
	{
		if (row < 0 || row >= num_rows) return;
		if (col < 0 || col >= num_cols) return;
		data[row][col] = x;
	}

	public int getNumberOfRows() 
	{
		return(num_rows);
	}

	public int getNumberOfCols() 
	{
		return(num_cols);
	}

	/*
	 * Returns a string representing this matrix. Each row is separated by a
	 * newline character ("\n").
	 */
	public String toString ()
	{
		String s = new String ();

		for (int i = 0; i < num_rows; i++) {
			for (int j = 0; j < (num_cols - 1); j++) {
				s += (Integer.toString (data[i][j]) + " ");
			}
			s += (Integer.toString (data[i][num_cols - 1]) + "\n");
		}

		return s;
	}
}

// EOF
