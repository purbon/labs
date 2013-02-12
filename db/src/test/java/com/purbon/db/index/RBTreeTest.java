package com.purbon.db.index;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.purbon.db.index.RBTree.Color;

public class RBTreeTest {

	private RBTree<Integer, Object> tree = null;
	private int[] keys = { 13, 8, 17, 11, 1, 25, 15, 27, 22, 6 };
	private RBTree.Color[] colors = { Color.BLACK, Color.RED, Color.RED,
			Color.BLACK, Color.BLACK, Color.BLACK, Color.BLACK, Color.RED,
			Color.RED, Color.RED };

	@Before
	public void setUp() throws Exception {
		tree = new RBTree<Integer, Object>();
		for (int key : keys) {
			tree.add(key);
		}
	}

	@After
	public void tearDown() throws Exception {
		tree = null;
	}

	@Test
	public void testAddAndBuild() {
		for (int i = 0; i < keys.length; i++) {
			assertEquals(colors[i], tree.search(keys[i]).color);
		}
	}

	@Test
	public void testRemoveAndDestroy() {

	}

}
