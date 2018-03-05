package com.oxygenxml.git.view;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;

import org.apache.log4j.Logger;
import org.eclipse.jgit.errors.RepositoryNotFoundException;

import com.oxygenxml.git.constants.ImageConstants;
import com.oxygenxml.git.constants.UIConstants;
import com.oxygenxml.git.options.OptionsManager;
import com.oxygenxml.git.service.GitAccess;
import com.oxygenxml.git.service.GitEventAdapter;
import com.oxygenxml.git.service.NoRepositorySelected;
import com.oxygenxml.git.translator.Tags;
import com.oxygenxml.git.translator.Translator;
import com.oxygenxml.git.utils.FileHelper;

import ro.sync.exml.workspace.api.PluginWorkspaceProvider;
import ro.sync.exml.workspace.api.images.ImageUtilities;
import ro.sync.exml.workspace.api.standalone.StandalonePluginWorkspace;
import ro.sync.exml.workspace.api.standalone.ui.ToolbarButton;

/**
 * Panel containing a label with showing the current working copy, a combo box
 * used for selected other working copies and a browse button to add new working
 * copies.
 * 
 * @author Beniamin Savu
 *
 */
public class WorkingCopySelectionPanel extends JPanel {

	/**
	 * Logger for logging.
	 */
	private static Logger logger = Logger.getLogger(WorkingCopySelectionPanel.class);

	/**
	 * A combo box for the user to change his working copy
	 */
	private JComboBox<String> workingCopyCombo;

	/**
	 * A file system browser for the user to add new git repositories to the combo
	 * box
	 */
	private JButton browseButton;

	/**
	 * The git API, containing the commands
	 */
	private GitAccess gitAccess = GitAccess.getInstance();

	/**
	 * The translator for the messages that are displayed in this panel
	 */
	private Translator translator = Translator.getInstance();

  /**
   * Constructor.
   */
	public WorkingCopySelectionPanel() {
		createGUI();
	}

	public JComboBox<String> getWorkingCopyCombo() {
		return workingCopyCombo;
	}

	public JButton getBrowseButton() {
		return browseButton;
	}

	/**
	 * Creates the components and adds listeners to some of them. Basically this
	 * creates the panel
	 */
	private void createGUI() {
		this.setLayout(new GridBagLayout());

		GridBagConstraints gbc = new GridBagConstraints();

		addLabel(gbc);
		addWorkingCopySelector(gbc);
		addBrowseButton(gbc);

		addFileChooserOn(browseButton);
		
		GitAccess.getInstance().addGitListener(new GitEventAdapter() {
      @Override
      public void repositoryChanged() {
          // The event was not triggered by the combo.
        try {
          File wc = GitAccess.getInstance().getWorkingCopy();
          String absolutePath = wc.getAbsolutePath();
          
          OptionsManager.getInstance().addRepository(absolutePath);
          OptionsManager.getInstance().saveSelectedRepository(absolutePath);
          
          if (!inhibitRepoUpdate) {
            inhibitRepoUpdate = true;
            try {
              if (FileHelper.isGitSubmodule(absolutePath)) {
                // An ugly hack to select the path in the combo without keeping it
                // in the model. We want to avoid adding it in the model because 
                // this path is not exactly an working copy (no .git in it)
                workingCopyCombo.setEditable(true);
                workingCopyCombo.setSelectedItem(absolutePath);
                workingCopyCombo.setEditable(false);
              } else {
                // Add it on the first position. 
                DefaultComboBoxModel<String> defaultComboBoxModel = (DefaultComboBoxModel<String>) workingCopyCombo.getModel();
                defaultComboBoxModel.removeElement(absolutePath);
                defaultComboBoxModel.insertElementAt(absolutePath, 0);
                
                // Select it.
                workingCopyCombo.setSelectedItem(absolutePath);
              }
            } finally {
              inhibitRepoUpdate = false;
            }
          }
          
          if (GitAccess.getInstance().getBranchInfo().isDetached()) {
            PluginWorkspaceProvider.getPluginWorkspace().showInformationMessage(
                translator.getTranslation(Tags.DETACHED_HEAD_MESSAGE));
          }
        } catch (NoRepositorySelected e) {
            logger.debug(e, e);
          }
        }
    });

		this.setMinimumSize(new Dimension(UIConstants.PANEL_WIDTH, UIConstants.WORKINGCOPY_PANEL_HEIGHT));
	}
	
	/**
	 * <code>true</code> to inhibit repository update when the selection changes in the combo.
	 * <code>false</code> to update the repository when the selection changes in the combo.
	 */
	private boolean inhibitRepoUpdate = false;

	/**
	 * Adds a state change listener on the working copy selector combo box. When a
	 * new working copy is selected this listener will execute
	 */
	private void addWorkingCopySelectorListener() {
		workingCopyCombo.addItemListener(new ItemListener() {

		  @Override
      public void itemStateChanged(ItemEvent e) {
		    // Don't do anything if the event was originated by us.
		    if (!inhibitRepoUpdate && e.getStateChange() == ItemEvent.SELECTED) {
		      inhibitRepoUpdate = true;
		      try {
		        // get and save the selected Option so that at restart the same
		        // repository will be selected
		        String path = (String) workingCopyCombo.getSelectedItem();
		        if (logger.isDebugEnabled()) {
		          logger.debug("Working copy " + path);
		        }
		        

		        try {
		          gitAccess.setRepository(path);
		          
		          OptionsManager.getInstance().saveSelectedRepository(path);
		        } catch (RepositoryNotFoundException ex) {
		          if (logger.isDebugEnabled()) {
		            logger.debug(ex, ex);
		          }
		          // We are here if the selected Repository doesn't exists anymore
		          OptionsManager.getInstance().removeRepositoryLocation(path);
		          if (workingCopyCombo.getItemCount() > 0) {
		            workingCopyCombo.setSelectedItem(0);
		          } else {
		            workingCopyCombo.setSelectedItem(null);
		            gitAccess.close();
		          }
		          workingCopyCombo.removeItem(path);

		          SwingUtilities.invokeLater(new Runnable() {
		            @Override
                public void run() {
		              PluginWorkspaceProvider.getPluginWorkspace()
		              .showInformationMessage(translator.getTranslation(Tags.WORKINGCOPY_REPOSITORY_NOT_FOUND));
		            }
		          });
		        } catch (IOException e1) {
		          if (logger.isDebugEnabled()) {
		            logger.debug(e1, e1);
		          }
		          JOptionPane.showMessageDialog((Component) PluginWorkspaceProvider.getPluginWorkspace().getParentFrame(),
		              "Could not load the repository");
		        }
		      } finally {
		        inhibitRepoUpdate = false;
		      }
		    }
		  }
		});

	}

	/**
	 * Adds a file chooser on a button
	 * 
	 * @param button
	 *          - the button to add a file chooser on
	 */
	private void addFileChooserOn(JButton button) {
		button.addActionListener(new ActionListener() {

			@Override
      public void actionPerformed(ActionEvent e) {
				File directory = ((StandalonePluginWorkspace) PluginWorkspaceProvider.getPluginWorkspace()).chooseDirectory();
				if (directory != null) {
					String directoryPath = directory.getAbsolutePath();
					if (FileHelper.isGitRepository(directoryPath) && directoryPath != null) {
						// adds the directory path to the combo box if it doesn't already
						// exists
					  OptionsManager.getInstance().addRepository(directoryPath);
					  
					  // Insert it first.
						DefaultComboBoxModel<String> defaultComboBoxModel = (DefaultComboBoxModel<String>) workingCopyCombo.getModel();
						defaultComboBoxModel.removeElement(directoryPath);
            defaultComboBoxModel.insertElementAt(directoryPath, 0);
            
						// sets the directory path as the selected repository
						workingCopyCombo.setSelectedItem(directoryPath);
					} else {
						PluginWorkspaceProvider.getPluginWorkspace()
								.showInformationMessage(translator.getTranslation(Tags.WORKINGCOPY_NOT_GIT_DIRECTORY));
					}
				}
			}
		});

	}

	/**
	 * Adds the label to the panel
	 * 
	 * @param gbc
	 *          - the constraints used for this component
	 */
	private void addLabel(GridBagConstraints gbc) {
		gbc.insets = new Insets(UIConstants.COMPONENT_TOP_PADDING, UIConstants.COMPONENT_LEFT_PADDING,
				UIConstants.COMPONENT_BOTTOM_PADDING, UIConstants.COMPONENT_RIGHT_PADDING);
		gbc.anchor = GridBagConstraints.WEST;
		gbc.fill = GridBagConstraints.NONE;
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx = 0;
		gbc.weighty = 0;
		this.add(new JLabel(translator.getTranslation(Tags.WORKING_COPY_LABEL)), gbc);

	}

	/**
	 * Adds the combo box to the panel
	 * 
	 * @param gbc
	 *          - the constraints used for this component
	 */
	private void addWorkingCopySelector(GridBagConstraints gbc) {
	  gbc.insets = new Insets(UIConstants.COMPONENT_TOP_PADDING, UIConstants.COMPONENT_LEFT_PADDING,
	      UIConstants.COMPONENT_BOTTOM_PADDING, UIConstants.COMPONENT_RIGHT_PADDING);
	  gbc.anchor = GridBagConstraints.WEST;
	  gbc.fill = GridBagConstraints.HORIZONTAL;
	  gbc.gridx = 1;
	  gbc.gridy = 0;
	  gbc.weightx = 1;
	  gbc.weighty = 1;

	  workingCopyCombo = new JComboBox<String>();
	  WorkingCopyToolTipRenderer renderer = new WorkingCopyToolTipRenderer();
	  workingCopyCombo.setRenderer(renderer);
	  int height = (int) workingCopyCombo.getPreferredSize().getHeight();
	  workingCopyCombo.setMinimumSize(new Dimension(10, height));

	  addWorkingCopySelectorListener();
	  
	  // Populates the combo box with the previously added repositories. Basically
	  // restore the state before the application was closed
	  loadEntries();
	  this.add(workingCopyCombo, gbc);
	}

	/**
	 * Load the recorded workinf copy locations into the combo.
	 */
  private void loadEntries() {
    List<String> repositoryEntries = new ArrayList<String>(OptionsManager.getInstance().getRepositoryEntries());
    for (String repositoryEntry : repositoryEntries) {
			workingCopyCombo.addItem(repositoryEntry);
		}
    
    String repositoryPath = OptionsManager.getInstance().getSelectedRepository();
    if (!repositoryPath.equals("")) {
      workingCopyCombo.setSelectedItem(repositoryPath);
    } else if (workingCopyCombo.getItemCount() > 0) {
      workingCopyCombo.setSelectedIndex(0);
    }
  }

	/**
	 * Adds the browse button to the panel
	 * 
	 * @param gbc
	 *          - the constraints used for this component
	 * 
	 */
	private void addBrowseButton(GridBagConstraints gbc) {
		gbc.insets = new Insets(UIConstants.COMPONENT_TOP_PADDING, UIConstants.COMPONENT_LEFT_PADDING,
				UIConstants.COMPONENT_BOTTOM_PADDING, UIConstants.COMPONENT_RIGHT_PADDING);
		gbc.anchor = GridBagConstraints.WEST;
		gbc.fill = GridBagConstraints.NONE;
		gbc.gridx = 2;
		gbc.gridy = 0;
		gbc.weightx = 0;
		gbc.weighty = 0;
		browseButton = new ToolbarButton(null, false);
		ImageUtilities imageUtilities = PluginWorkspaceProvider.getPluginWorkspace().getImageUtilities();
		URL resource = getClass().getResource(ImageConstants.FILE_CHOOSER_ICON);
		if (resource != null) {
		  ImageIcon icon = (ImageIcon) imageUtilities.loadIcon(resource);
		  browseButton.setIcon(icon);
		}
		browseButton.setToolTipText(translator.getTranslation(Tags.BROWSE_BUTTON_TOOLTIP));
		JToolBar browswtoolbar = new JToolBar();
		browswtoolbar.add(browseButton);
		browswtoolbar.setFloatable(false);
		browswtoolbar.setOpaque(false);
		this.add(browswtoolbar, gbc);
	}

	/**
	 * 
	 * Renderer for the combo box. Displaying only the folder project. Not the
	 * full path to the folder project
	 * 
	 * @author Beniamin Savu
	 *
	 */
	private static final class WorkingCopyToolTipRenderer extends DefaultListCellRenderer {

		@Override
		public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,
				boolean cellHasFocus) {

			JLabel comp = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

			if (value != null) {
				comp.setToolTipText((String) value);
				String path = (String) value;
				path = path.replace("\\", "/");
				String rootFolder = path.substring(path.lastIndexOf('/') + 1);
				comp.setText(rootFolder);
			}
			return comp;
		}
	}
}
