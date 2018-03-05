package com.oxygenxml.git.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.List;

import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import org.junit.Test;

import com.oxygenxml.git.view.GitTreeNode;

public class TreeFormatterTest {

	@Test
	public void testGetNodeFromString() {
		List<String> paths = new ArrayList<String>();
		paths.add("src/add/poc.txt");
		paths.add("src/add/hello.txt");
		paths.add("src/add/java/info.txt");
		paths.add("src/main/java/test.java");
		paths.add("src/main/java/package/file.java");
		paths.add("resources/java/find.txt");
		GitTreeNode root = new GitTreeNode("Test");
		DefaultTreeModel model = new DefaultTreeModel(root);

		for (String string : paths) {
			TreeFormatter.buildTreeFromString(model, string);
		}

		GitTreeNode node = TreeFormatter.getTreeNodeFromString(model, "src/add/java/info.txt");
		String actual = (String) node.getUserObject();
		String expected = "info.txt";

		assertEquals(actual, expected);
	}

	@Test
	public void testGetTreeCommonAncestors() {
		List<Object[]> p = new ArrayList<Object[]>();
		p.add(new Object[] { "src", "add", "poc.txt" });
		p.add(new Object[] { "src", "add", "poc1.txt" });
		p.add(new Object[] { "src", "add", "poc2.txt" });
		p.add(new Object[] { "src", "add", "poc3.txt" });
		p.add(new Object[] { "src", "add", "poc4.txt" });
		p.add(new Object[] { "src", "add", "poc5.txt" });
		p.add(new Object[] { "src", "add" });
		p.add(new Object[] { "resources", "java" });
		p.add(new Object[] { "resources", "java", "fisier1.xml" });
		p.add(new Object[] { "resources", "java", "fisier2.xml" });
		p.add(new Object[] { "resources", "java", "fisier3.xml" });
		p.add(new Object[] { "resources", "java", "fisier4.xml" });
		p.add(new Object[] { "src" });
		p.add(new Object[] { "test", "remove", "file.txt" });
		p.add(new Object[] { "test", "remove", "all", "main.smt" });
		p.add(new Object[] { "test", "remove", "all", "main2.smt" });
		p.add(new Object[] { "test", "remove", "all", "main3.smt" });
		TreePath[] trePaths = new TreePath[p.size()];
		for (int i = 0; i < p.size(); i++) {
			trePaths[i] = new TreePath(p.get(i));
		}

		List<TreePath> actual = TreeFormatter.getTreeCommonAncestors(trePaths);
		List<TreePath> expected = new ArrayList<TreePath>();
		expected.add(new TreePath(new Object[] { "resources", "java" }));
		expected.add(new TreePath(new Object[] { "src" }));
		expected.add(new TreePath(new Object[] { "test", "remove", "file.txt" }));
		expected.add(new TreePath(new Object[] { "test", "remove", "all", "main.smt" }));
		expected.add(new TreePath(new Object[] { "test", "remove", "all", "main2.smt" }));
		expected.add(new TreePath(new Object[] { "test", "remove", "all", "main3.smt" }));

		assertEquals(actual, expected);

	}

	/**
	 * Getting tree node from a string should return <code>null</code> if
	 * there is no corresponding node.
	 */
	@Test
  public void testGetNodeFromString_2() {
    List<String> paths = new ArrayList<String>();
    paths.add("src/add/poc.txt");
    paths.add("src/add/hello.txt");
    paths.add("src/add/java/info.txt");
    GitTreeNode root = new GitTreeNode("Test");
    DefaultTreeModel model = new DefaultTreeModel(root);

    for (String string : paths) {
      TreeFormatter.buildTreeFromString(model, string);
    }

    GitTreeNode node = TreeFormatter.getTreeNodeFromString(model, "path/to/non/existing/file.xml");
    assertNull(node);
  }

}
