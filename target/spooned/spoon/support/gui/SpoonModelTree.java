/**
 * Copyright (C) 2006-2017 INRIA and contributors
 * Spoon - http://spoon.gforge.inria.fr/
 *
 * This software is governed by the CeCILL-C License under French law and
 * abiding by the rules of distribution of free software. You can use, modify
 * and/or redistribute the software under the terms of the CeCILL-C license as
 * circulated by CEA, CNRS and INRIA at http://www.cecill.info.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the CeCILL-C License for more details.
 *
 * The fact that you are presently reading this means that you have had
 * knowledge of the CeCILL-C license and that you accept its terms.
 */
package spoon.support.gui;


public class SpoonModelTree extends javax.swing.JFrame implements java.awt.event.KeyListener , java.awt.event.MouseListener {
    private static final long serialVersionUID = 1L;

    java.util.Enumeration<?> enume;

    private spoon.reflect.factory.Factory factory;

    private javax.swing.JPanel jContentPane = null;

    private javax.swing.JScrollPane jScrollPane = null;

    private javax.swing.JTree jTree = null;

    javax.swing.JPopupMenu menu;

    private javax.swing.tree.DefaultMutableTreeNode root;// @jve:decl-index=0:visual-constraint="207,57"


    java.lang.String searchValue;

    /**
     * This is the default constructor
     */
    public SpoonModelTree(spoon.reflect.factory.Factory factory) {
        super();
        spoon.support.gui.SpoonTreeBuilder cst = new spoon.support.gui.SpoonTreeBuilder();
        cst.scan(factory.Package().getRootPackage());
        this.factory = factory;
        root = cst.getRoot();
        initialize();
    }

    public SpoonModelTree(spoon.reflect.declaration.CtElement rootElement) {
        super();
        spoon.support.gui.SpoonTreeBuilder cst = new spoon.support.gui.SpoonTreeBuilder();
        cst.scan(rootElement);
        this.factory = rootElement.getFactory();
        root = cst.getRoot();
        initialize();
    }

    /**
     * This method initializes jContentPane
     *
     * @return javax.swing.JPanel
     */
    private javax.swing.JPanel getJContentPane() {
        if ((jContentPane) == null) {
            java.awt.GridLayout gridLayout = new java.awt.GridLayout();
            gridLayout.setRows(1);
            jContentPane = new javax.swing.JPanel();
            jContentPane.setLayout(gridLayout);
            jContentPane.add(getJScrollPane(), null);
        }
        return jContentPane;
    }

    /**
     * This method initializes jScrollPane
     *
     * @return javax.swing.JScrollPane
     */
    private javax.swing.JScrollPane getJScrollPane() {
        if ((jScrollPane) == null) {
            jScrollPane = new javax.swing.JScrollPane();
            jScrollPane.setViewportView(getJTree());
        }
        return jScrollPane;
    }

    /**
     * This method initializes jTree
     *
     * @return javax.swing.JTree
     */
    private javax.swing.JTree getJTree() {
        if ((jTree) == null) {
            jTree = new javax.swing.JTree(root);
            jTree.addKeyListener(this);
            jTree.addMouseListener(this);
        }
        return jTree;
    }

    private javax.swing.JPopupMenu getMenu() {
        if ((menu) == null) {
            menu = new javax.swing.JPopupMenu();
            javax.swing.JMenuItem item = new javax.swing.JMenuItem("Save");
            item.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    javax.swing.JFileChooser chooser = new javax.swing.JFileChooser();
                    chooser.setFileSelectionMode(javax.swing.JFileChooser.FILES_ONLY);
                    boolean cont = (chooser.showSaveDialog(spoon.support.gui.SpoonModelTree.this)) == (javax.swing.JFileChooser.APPROVE_OPTION);
                    if (cont) {
                        spoon.support.SerializationModelStreamer ser = new spoon.support.SerializationModelStreamer();
                        try {
                            ser.save(factory, new java.io.FileOutputStream(chooser.getSelectedFile()));
                        } catch (java.io.IOException e1) {
                            spoon.Launcher.LOGGER.error(e1.getMessage(), e1);
                        }
                    }
                }
            });
            menu.add(item);
            menu.addSeparator();
            // show reflect table
            item = new javax.swing.JMenuItem("Reflect");
            item.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    javax.swing.tree.DefaultMutableTreeNode node = ((javax.swing.tree.DefaultMutableTreeNode) (jTree.getLastSelectedPathComponent()));
                    if (node == null) {
                        node = root;
                    }
                    new spoon.support.gui.SpoonObjectFieldsTable(node.getUserObject());
                }
            });
            menu.add(item);
            menu.addSeparator();
            // Search value
            item = new javax.swing.JMenuItem("Search");
            item.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    search();
                }
            });
            menu.add(item);
            // Search value
            item = new javax.swing.JMenuItem("Search next");
            item.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    next();
                }
            });
            menu.add(item);
            menu.addSeparator();
            // Expand all
            item = new javax.swing.JMenuItem("Expand all");
            item.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    javax.swing.tree.DefaultMutableTreeNode node = ((javax.swing.tree.DefaultMutableTreeNode) (jTree.getLastSelectedPathComponent()));
                    if (node == null) {
                        node = root;
                    }
                    expandAll(node);
                }
            });
            menu.add(item);
        }
        return menu;
    }

    /**
     * This method initializes this
     *
     * @return void
     */
    private void initialize() {
        this.setSize(640, 480);
        this.setLocation((((getGraphicsConfiguration().getDevice().getDisplayMode().getWidth()) - (getWidth())) / 2), (((getGraphicsConfiguration().getDevice().getDisplayMode().getHeight()) - (getHeight())) / 2));
        setContentPane(getJContentPane());
        setTitle("Spoon");
        setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    public void keyPressed(java.awt.event.KeyEvent e) {
    }

    public void keyReleased(java.awt.event.KeyEvent e) {
    }

    public void keyTyped(java.awt.event.KeyEvent e) {
        switch (e.getKeyChar()) {
            case 's' :
                break;
            case 'n' :
                next();
                break;
            case 'o' :
                if ((jTree.getLastSelectedPathComponent()) != null) {
                    new spoon.support.gui.SpoonObjectFieldsTable(((javax.swing.tree.DefaultMutableTreeNode) (jTree.getLastSelectedPathComponent())).getUserObject());
                }
                break;
        }
    }

    private void maybeShowPopup(java.awt.event.MouseEvent e) {
        if (e.isPopupTrigger()) {
            getMenu().show(e.getComponent(), e.getX(), e.getY());
        }
    }

    public void mouseClicked(java.awt.event.MouseEvent e) {
    }

    public void mouseEntered(java.awt.event.MouseEvent e) {
    }

    public void mouseExited(java.awt.event.MouseEvent e) {
    }

    public void mousePressed(java.awt.event.MouseEvent e) {
        getJTree().setSelectionRow(getJTree().getClosestRowForLocation(e.getX(), e.getY()));
        maybeShowPopup(e);
    }

    public void mouseReleased(java.awt.event.MouseEvent e) {
        maybeShowPopup(e);
    }

    /**
     * move to the next node matching the search criterion
     */
    public javax.swing.tree.DefaultMutableTreeNode next() {
        javax.swing.tree.DefaultMutableTreeNode current = null;
        while (((enume) != null) && (enume.hasMoreElements())) {
            current = ((javax.swing.tree.DefaultMutableTreeNode) (enume.nextElement()));
            if (((current.getUserObject()) != null) && (current.getUserObject().toString().contains(searchValue))) {
                setVisible(current);
                return current;
            }
        } 
        return null;
    }

    /**
     * shows a dialog to enter the value to search for in the AST
     */
    public javax.swing.tree.DefaultMutableTreeNode search() {
        searchValue = javax.swing.JOptionPane.showInputDialog(this, "Enter value to search:", "Search");
        javax.swing.tree.DefaultMutableTreeNode node = ((javax.swing.tree.DefaultMutableTreeNode) (jTree.getLastSelectedPathComponent()));
        if (node == null) {
            node = root;
        }
        enume = node.depthFirstEnumeration();
        if ((searchValue) != null) {
            return next();
        }
        return null;
    }

    /**
     * expand all AST nodes in the GUI
     */
    public javax.swing.tree.DefaultMutableTreeNode expandAll(final javax.swing.tree.DefaultMutableTreeNode node) {
        if ((node == null) || (node.isLeaf())) {
            return null;
        }
        final java.util.concurrent.ExecutorService executor = java.util.concurrent.Executors.newSingleThreadExecutor();
        executor.execute(new java.lang.Runnable() {
            public void run() {
                try {
                    java.util.Queue<javax.swing.tree.DefaultMutableTreeNode> q = new java.util.LinkedList<>();
                    q.add(node);
                    while (!(q.isEmpty())) {
                        final javax.swing.tree.DefaultMutableTreeNode n = q.poll();
                        expand(n);
                        @java.lang.SuppressWarnings("unchecked")
                        java.util.Enumeration<javax.swing.tree.TreeNode> children = n.children();
                        while (children.hasMoreElements()) {
                            javax.swing.tree.DefaultMutableTreeNode child = ((javax.swing.tree.DefaultMutableTreeNode) (children.nextElement()));
                            if ((!(child.isLeaf())) && ((child.getChildCount()) > 0)) {
                                q.offer(child);
                            }
                        } 
                    } 
                } finally {
                    executor.shutdownNow();
                }
            }
        });
        return node;
    }

    public void expand(final javax.swing.tree.DefaultMutableTreeNode node) {
        javax.swing.SwingUtilities.invokeLater(new java.lang.Runnable() {
            public void run() {
                javax.swing.tree.TreePath path = new javax.swing.tree.TreePath(node.getPath());
                if (!(jTree.isExpanded(path))) {
                    jTree.expandPath(path);
                    jTree.updateUI();
                }
            }
        });
    }

    public void setVisible(javax.swing.tree.DefaultMutableTreeNode node) {
        javax.swing.tree.TreePath path = new javax.swing.tree.TreePath(node.getPath());
        getJTree().scrollPathToVisible(path);
        getJTree().setSelectionPath(path);
    }
}

