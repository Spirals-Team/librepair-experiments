/*
 * Copyright (C) 2013-2018 Pierre-François Gimenez
 * Distributed under the MIT License.
 */

package pfg.kraken;

import java.util.Random;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import pfg.kraken.dstarlite.DStarLiteNode;
import pfg.kraken.dstarlite.EnhancedPriorityQueue;

/**
 * Tests unitaires de l'Enhanced Priority Queue
 * 
 * @author pf
 *
 */

public class Test_EPriorityQueue extends JUnit_Test
{

	private EnhancedPriorityQueue file;

	@Before
	public void setUp() throws Exception
	{
		super.setUpWith(null, "default", "empty");
		file = new EnhancedPriorityQueue(500);
	}

	@Test
	public void test() throws Exception
	{
		Assert.assertTrue(file.isEmpty());
		DStarLiteNode n = new DStarLiteNode(null);
		file.add(n);
		// file.print(1);
		Assert.assertTrue(!file.isEmpty());
		Assert.assertTrue(file.peek() == n);
		Assert.assertTrue(!file.isEmpty());
		Assert.assertTrue(file.poll() == n);
		Assert.assertTrue(file.isEmpty());
		n.cle.set(1, 2);
		file.add(n);
		DStarLiteNode n2 = new DStarLiteNode(null);
		n2.cle.set(0, 1);
		file.add(n2); // test de percolate up
		Assert.assertTrue(file.peek() == n2);
		// file.print(2);
		n2.cle.set(3, 4);
		file.percolateDown(n2);
		// file.print(3);
		Assert.assertTrue(file.peek() == n);
		file.remove(n);
		// file.print(4);
		Assert.assertTrue(file.peek() == n2);
		Assert.assertTrue(!file.isEmpty());
		file.clear();
		Assert.assertTrue(file.isEmpty());
	}

	@Test
	public void test2() throws Exception
	{
		Random r = new Random();
		DStarLiteNode[] n = new DStarLiteNode[100];
		for(int i = 0; i < 100; i++)
		{
			n[i] = new DStarLiteNode(null);
			n[i].cle.set(r.nextInt(10), r.nextInt(10));
			file.add(n[i]);
		}
		file.poll();
		file.poll();
		file.poll();
		file.poll();
		file.poll();
	}

}
