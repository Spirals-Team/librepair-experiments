/*
 * SonarQube
 * Copyright (C) 2009-2017 SonarSource SA
 * mailto:info AT sonarsource DOT com
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */
package org.sonar.scanner.protocol.viewer;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Scanner;

import javax.annotation.CheckForNull;
import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTree;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeSelectionModel;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.sonar.core.util.CloseableIterator;
import org.sonar.scanner.protocol.output.FileStructure.Domain;
import org.sonar.scanner.protocol.output.ScannerReport;
import org.sonar.scanner.protocol.output.ScannerReport.Changesets;
import org.sonar.scanner.protocol.output.ScannerReport.Changesets.Changeset;
import org.sonar.scanner.protocol.output.ScannerReport.Component;
import org.sonar.scanner.protocol.output.ScannerReport.Issue;
import org.sonar.scanner.protocol.output.ScannerReport.Metadata;
import org.sonar.scanner.protocol.output.ScannerReportReader;

public class ScannerReportViewerApp {

  private JFrame frame;
  private ScannerReportReader reader;
  private Metadata metadata;
  private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
  private JTree componentTree;
  private JSplitPane splitPane;
  private JTabbedPane tabbedPane;
  private JScrollPane treeScrollPane;
  private JScrollPane componentDetailsTab;
  private JScrollPane highlightingTab;
  private JScrollPane symbolTab;
  private JEditorPane componentEditor;
  private JEditorPane highlightingEditor;
  private JEditorPane symbolEditor;
  private JScrollPane sourceTab;
  private JEditorPane sourceEditor;
  private JScrollPane coverageTab;
  private JEditorPane coverageEditor;
  private JScrollPane testsTab;
  private JEditorPane testsEditor;
  private TextLineNumber textLineNumber;
  private JScrollPane duplicationTab;
  private JEditorPane duplicationEditor;
  private JScrollPane issuesTab;
  private JEditorPane issuesEditor;
  private JScrollPane measuresTab;
  private JEditorPane measuresEditor;
  private JScrollPane scmTab;
  private JEditorPane scmEditor;

  /**
   * Create the application.
   */
  public ScannerReportViewerApp() {
    initialize();
  }

  /**
   * Launch the application.
   */
  public static void main(String[] args) {
    EventQueue.invokeLater(new Runnable() {
      @Override
      public void run() {
        try {
          ScannerReportViewerApp window = new ScannerReportViewerApp();
          window.frame.setVisible(true);

          window.loadReport();
        } catch (Exception e) {
          e.printStackTrace();
        }
      }

    });
  }

  private void loadReport() {
    final JFileChooser fc = new JFileChooser();
    fc.setDialogTitle("Choose scanner report directory");
    File lastReport = getLastUsedReport();
    if (lastReport != null) {
      fc.setCurrentDirectory(lastReport);
    }
    fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
    fc.setFileHidingEnabled(false);
    fc.setApproveButtonText("Open scanner report");
    int returnVal = fc.showOpenDialog(frame);
    if (returnVal == JFileChooser.APPROVE_OPTION) {
      File file = fc.getSelectedFile();
      try {
        setLastUsedReport(file);
        loadReport(file);
      } catch (Exception e) {
        JOptionPane.showMessageDialog(frame, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        exit();
      }
    } else {
      exit();
    }

  }

  @CheckForNull
  private File getLastUsedReport() {
    File f = new File(System.getProperty("java.io.tmpdir"), ".last_batch_report_dir");
    if (f.exists()) {
      String path;
      try {
        path = FileUtils.readFileToString(f, StandardCharsets.UTF_8);
      } catch (IOException e) {
        return null;
      }
      File lastReport = new File(path);
      if (lastReport.exists() && lastReport.isDirectory()) {
        return lastReport;
      }
    }
    return null;
  }

  private void setLastUsedReport(File lastReport) throws IOException {

    File f = new File(System.getProperty("java.io.tmpdir"), ".last_batch_report_dir");
    String fullPath = lastReport.getAbsolutePath();
    FileUtils.write(f, fullPath, StandardCharsets.UTF_8);
  }

  private void exit() {
    frame.setVisible(false);
    frame.dispose();
  }

  private void loadReport(File file) {
    reader = new ScannerReportReader(file);
    metadata = reader.readMetadata();
    updateTitle();
    loadComponents();
  }

  private void loadComponents() {
    int rootComponentRef = metadata.getRootComponentRef();
    Component component = reader.readComponent(rootComponentRef);
    DefaultMutableTreeNode project = createNode(component);
    loadChildren(component, project);
    getComponentTree().setModel(new DefaultTreeModel(project));
  }

  private static DefaultMutableTreeNode createNode(Component component) {
    return new DefaultMutableTreeNode(component) {
      @Override
      public String toString() {
        return getNodeName((Component) getUserObject());
      }
    };
  }

  private static String getNodeName(Component component) {
    switch (component.getType()) {
      case PROJECT:
      case MODULE:
        return component.getName();
      case DIRECTORY:
      case FILE:
        return component.getPath();
      default:
        throw new IllegalArgumentException("Unknow component type: " + component.getType());
    }
  }

  private void loadChildren(Component parentComponent, DefaultMutableTreeNode parentNode) {
    for (int ref : parentComponent.getChildRefList()) {
      Component child = reader.readComponent(ref);
      DefaultMutableTreeNode childNode = createNode(child);
      parentNode.add(childNode);
      loadChildren(child, childNode);
    }

  }

  private void updateTitle() {
    frame.setTitle(metadata.getProjectKey() + (StringUtils.isNotEmpty(metadata.getBranch()) ? (" (" + metadata.getBranch() + ")") : "") + " "
      + sdf.format(new Date(metadata.getAnalysisDate())));
  }

  private void updateDetails(Component component) {
    componentEditor.setText(component.toString());
    updateHighlighting(component);
    updateSymbols(component);
    updateSource(component);
    updateCoverage(component);
    updateTests(component);
    updateDuplications(component);
    updateIssues(component);
    updateMeasures(component);
    updateScm(component);
  }

  private void updateDuplications(Component component) {
    duplicationEditor.setText("");
    if (reader.hasCoverage(component.getRef())) {
      try (CloseableIterator<ScannerReport.Duplication> it = reader.readComponentDuplications(component.getRef())) {
        while (it.hasNext()) {
          ScannerReport.Duplication dup = it.next();
          duplicationEditor.getDocument().insertString(duplicationEditor.getDocument().getEndPosition().getOffset(), dup + "\n", null);
        }
      } catch (Exception e) {
        throw new IllegalStateException("Can't read duplications for " + getNodeName(component), e);
      }
    }
  }

  private void updateIssues(Component component) {
    issuesEditor.setText("");
    try (CloseableIterator<Issue> it = reader.readComponentIssues(component.getRef())) {
      while (it.hasNext()) {
        Issue issue = it.next();
        int offset = issuesEditor.getDocument().getEndPosition().getOffset();
        issuesEditor.getDocument().insertString(offset, issue.toString(), null);
      }
    } catch (Exception e) {
      throw new IllegalStateException("Can't read issues for " + getNodeName(component), e);
    }
  }

  private void updateCoverage(Component component) {
    coverageEditor.setText("");
    try (CloseableIterator<ScannerReport.LineCoverage> it = reader.readComponentCoverage(component.getRef())) {
      while (it.hasNext()) {
        ScannerReport.LineCoverage coverage = it.next();
        coverageEditor.getDocument().insertString(coverageEditor.getDocument().getEndPosition().getOffset(), coverage + "\n", null);
      }
    } catch (Exception e) {
      throw new IllegalStateException("Can't read code coverage for " + getNodeName(component), e);
    }
  }

  private void updateTests(Component component) {
    testsEditor.setText("");
    File tests = reader.readTests(component.getRef());
    if (tests == null) {
      return;
    }
    try (InputStream inputStream = FileUtils.openInputStream(tests)) {
      ScannerReport.Test test = ScannerReport.Test.parser().parseDelimitedFrom(inputStream);
      while (test != null) {
        testsEditor.getDocument().insertString(testsEditor.getDocument().getEndPosition().getOffset(), test + "\n", null);
        test = ScannerReport.Test.parser().parseDelimitedFrom(inputStream);
      }
    } catch (Exception e) {
      throw new IllegalStateException(e);
    }
  }

  private void updateSource(Component component) {
    File sourceFile = reader.getFileStructure().fileFor(Domain.SOURCE, component.getRef());
    sourceEditor.setText("");

    if (sourceFile.exists()) {
      try (Scanner s = new Scanner(sourceFile, StandardCharsets.UTF_8.name()).useDelimiter("\\Z")) {
        if (s.hasNext()) {
          sourceEditor.setText(s.next());
        }
      } catch (IOException ex) {
        StringWriter errors = new StringWriter();
        ex.printStackTrace(new PrintWriter(errors));
        sourceEditor.setText(errors.toString());
      }
    }
  }

  private void updateHighlighting(Component component) {
    highlightingEditor.setText("");
    try (CloseableIterator<ScannerReport.SyntaxHighlightingRule> it = reader.readComponentSyntaxHighlighting(component.getRef())) {
      while (it.hasNext()) {
        ScannerReport.SyntaxHighlightingRule rule = it.next();
        highlightingEditor.getDocument().insertString(highlightingEditor.getDocument().getEndPosition().getOffset(), rule + "\n", null);
      }
    } catch (Exception e) {
      throw new IllegalStateException("Can't read syntax highlighting for " + getNodeName(component), e);
    }
  }

  private void updateMeasures(Component component) {
    measuresEditor.setText("");
    try (CloseableIterator<ScannerReport.Measure> it = reader.readComponentMeasures(component.getRef())) {
      while (it.hasNext()) {
        ScannerReport.Measure measure = it.next();
        measuresEditor.getDocument().insertString(measuresEditor.getDocument().getEndPosition().getOffset(), measure + "\n", null);
      }
    } catch (Exception e) {
      throw new IllegalStateException("Can't read measures for " + getNodeName(component), e);
    }
  }

  private void updateScm(Component component) {
    scmEditor.setText("");
    Changesets changesets = reader.readChangesets(component.getRef());
    if (changesets == null) {
      return;
    }
    List<Integer> changesetIndexByLine = changesets.getChangesetIndexByLineList();
    try {
      int index = 0;
      for (Changeset changeset : changesets.getChangesetList()) {
        scmEditor.getDocument().insertString(scmEditor.getDocument().getEndPosition().getOffset(), Integer.toString(index) + "\n", null);
        scmEditor.getDocument().insertString(scmEditor.getDocument().getEndPosition().getOffset(), changeset + "\n", null);
        index++;
      }
      
      scmEditor.getDocument().insertString(scmEditor.getDocument().getEndPosition().getOffset(), "\n", null);
      int line = 1;
      for (Integer idx : changesetIndexByLine) {
        scmEditor.getDocument().insertString(scmEditor.getDocument().getEndPosition().getOffset(), Integer.toString(line) + ": " + idx + "\n", null);
        line++;
      }

    } catch (Exception e) {
      throw new IllegalStateException("Can't read SCM for " + getNodeName(component), e);
    }
  }

  private void updateSymbols(Component component) {
    symbolEditor.setText("");
    try (CloseableIterator<ScannerReport.Symbol> it = reader.readComponentSymbols(component.getRef())) {
      while (it.hasNext()) {
        ScannerReport.Symbol symbol = it.next();
        symbolEditor.getDocument().insertString(symbolEditor.getDocument().getEndPosition().getOffset(), symbol + "\n", null);
      }
    } catch (Exception e) {
      throw new IllegalStateException("Can't read symbol references for " + getNodeName(component), e);
    }
  }

  /**
   * Initialize the contents of the frame.
   */
  private void initialize() {
    try {
      for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
        if ("Nimbus".equals(info.getName())) {
          UIManager.setLookAndFeel(info.getClassName());
          break;
        }
      }
    } catch (Exception e) {
      // If Nimbus is not available, you can set the GUI to another look and feel.
    }
    frame = new JFrame();
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    splitPane = new JSplitPane();
    frame.getContentPane().add(splitPane, BorderLayout.CENTER);

    tabbedPane = new JTabbedPane(JTabbedPane.TOP);
    tabbedPane.setPreferredSize(new Dimension(500, 7));
    splitPane.setRightComponent(tabbedPane);

    componentDetailsTab = new JScrollPane();
    tabbedPane.addTab("Component details", null, componentDetailsTab, null);

    componentEditor = new JEditorPane();
    componentDetailsTab.setViewportView(componentEditor);

    sourceTab = new JScrollPane();
    tabbedPane.addTab("Source", null, sourceTab, null);

    sourceEditor = createSourceEditor();
    sourceEditor.setEditable(false);
    sourceTab.setViewportView(sourceEditor);

    textLineNumber = createTextLineNumber();
    sourceTab.setRowHeaderView(textLineNumber);

    highlightingTab = new JScrollPane();
    tabbedPane.addTab("Highlighting", null, highlightingTab, null);

    highlightingEditor = new JEditorPane();
    highlightingTab.setViewportView(highlightingEditor);

    symbolTab = new JScrollPane();
    tabbedPane.addTab("Symbol references", null, symbolTab, null);

    symbolEditor = new JEditorPane();
    symbolTab.setViewportView(symbolEditor);

    coverageTab = new JScrollPane();
    tabbedPane.addTab("Coverage", null, coverageTab, null);

    coverageEditor = new JEditorPane();
    coverageTab.setViewportView(coverageEditor);

    duplicationTab = new JScrollPane();
    tabbedPane.addTab("Duplications", null, duplicationTab, null);

    duplicationEditor = new JEditorPane();
    duplicationTab.setViewportView(duplicationEditor);

    testsTab = new JScrollPane();
    tabbedPane.addTab("Tests", null, testsTab, null);

    testsEditor = new JEditorPane();
    testsTab.setViewportView(testsEditor);

    issuesTab = new JScrollPane();
    tabbedPane.addTab("Issues", null, issuesTab, null);

    issuesEditor = new JEditorPane();
    issuesTab.setViewportView(issuesEditor);

    measuresTab = new JScrollPane();
    tabbedPane.addTab("Measures", null, measuresTab, null);

    measuresEditor = new JEditorPane();
    measuresTab.setViewportView(measuresEditor);

    scmTab = new JScrollPane();
    tabbedPane.addTab("SCM", null, scmTab, null);

    scmEditor = new JEditorPane();
    scmTab.setViewportView(scmEditor);

    treeScrollPane = new JScrollPane();
    treeScrollPane.setPreferredSize(new Dimension(200, 400));
    splitPane.setLeftComponent(treeScrollPane);

    componentTree = new JTree();
    componentTree.setModel(new DefaultTreeModel(
      new DefaultMutableTreeNode("empty") {
        {
        }
      }));
    treeScrollPane.setViewportView(componentTree);
    componentTree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
    componentTree.addTreeSelectionListener(new TreeSelectionListener() {
      @Override
      public void valueChanged(TreeSelectionEvent e) {
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) componentTree.getLastSelectedPathComponent();

        if (node == null) {
          // Nothing is selected.
          return;
        }

        frame.setCursor(new Cursor(Cursor.WAIT_CURSOR));
        updateDetails((Component) node.getUserObject());
        frame.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));

      }

    });
    frame.pack();
  }

  public JTree getComponentTree() {
    return componentTree;
  }

  /**
   * @wbp.factory
   */
  public static JPanel createComponentPanel() {
    JPanel panel = new JPanel();
    return panel;
  }

  protected JEditorPane getComponentEditor() {
    return componentEditor;
  }

  /**
   * @wbp.factory
   */
  public static JEditorPane createSourceEditor() {
    JEditorPane editorPane = new JEditorPane();
    return editorPane;
  }

  /**
   * @wbp.factory
   */
  public TextLineNumber createTextLineNumber() {
    TextLineNumber textLineNumber = new TextLineNumber(sourceEditor);
    return textLineNumber;
  }
}
