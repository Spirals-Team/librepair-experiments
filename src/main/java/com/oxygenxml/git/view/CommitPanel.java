package com.oxygenxml.git.view;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.net.URL;

import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JToolTip;
import javax.swing.ScrollPaneConstants;

import org.apache.log4j.Logger;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.RepositoryState;

import com.oxygenxml.git.constants.ImageConstants;
import com.oxygenxml.git.constants.UIConstants;
import com.oxygenxml.git.options.OptionsManager;
import com.oxygenxml.git.service.GitAccess;
import com.oxygenxml.git.service.GitEventAdapter;
import com.oxygenxml.git.service.NoRepositorySelected;
import com.oxygenxml.git.translator.Tags;
import com.oxygenxml.git.translator.Translator;
import com.oxygenxml.git.utils.PanelRefresh.RepositoryStatus;
import com.oxygenxml.git.utils.UndoSupportInstaller;
import com.oxygenxml.git.view.event.ActionStatus;
import com.oxygenxml.git.view.event.ChangeEvent;
import com.oxygenxml.git.view.event.GitCommand;
import com.oxygenxml.git.view.event.Observer;
import com.oxygenxml.git.view.event.PushPullEvent;
import com.oxygenxml.git.view.event.Subject;

import ro.sync.exml.workspace.api.PluginWorkspaceProvider;
import ro.sync.exml.workspace.api.images.ImageUtilities;

/**
 * Panel to insert the commit message and commit the staged files. 
 */
public class CommitPanel extends JPanel implements Subject<PushPullEvent> {
  /**
   * Logger for logging.
   */
  private static Logger logger = Logger.getLogger(CommitPanel.class);
  /**
   * Text area for the commit message.
   */
	private JTextArea commitMessage;
	/**
	 * The button that commits the staged files.
	 */
	private JButton commitButton;
	/**
	 * Git access.
	 */
	private GitAccess gitAccess = GitAccess.getInstance();
	/**
	 * Previous messages history.
	 */
	private JComboBox<String> previousMessages;
	/**
	 * Messages of interest.
	 */
	private JLabel statusLabel;
	/**
	 * Will be notified after the commit.
	 */
	private Observer<PushPullEvent> observer;
	/**
	 * Translation support.
	 */
	private Translator translator = Translator.getInstance();

	public JButton getCommitButton() {
		return commitButton;
	}
	
	/**
	 * Constructor.
	 */
	public CommitPanel() {
	  createGUI();
	  
    GitAccess.getInstance().addGitListener(new GitEventAdapter() {
      @Override
      public void repositoryChanged() {
        Repository repository;
        try {
          repository = gitAccess.getRepository();
          if (repository != null) {
            // When a new working copy is selected clear the commit text area
            reset();

            // checks what buttons to keep active and what buttons to deactivate
            toggleCommitButton(false);
          }
        } catch (NoRepositorySelected e) {
          logger.debug(e, e);
        }
      }
      
      @Override
      public void stateChanged(ChangeEvent changeEvent) {
        toggleCommitButton(changeEvent.getCommand() == GitCommand.STAGE);
      }
    });
  }

	public void createGUI() {
		this.setLayout(new GridBagLayout());

		GridBagConstraints gbc = new GridBagConstraints();

		addLabel(gbc);
		addPreviouslyMessagesComboBox(gbc);
		addCommitMessageTextArea(gbc);
		addStatusLabel(gbc);
		addCommitButton(gbc);

		addCommitButtonListener();
		this.setPreferredSize(new Dimension(UIConstants.PANEL_WIDTH, UIConstants.COMMIT_PANEL_HEIGHT));
		this.setMinimumSize(new Dimension(UIConstants.PANEL_WIDTH, UIConstants.COMMIT_PANEL_HEIGHT));
	}

	private void addCommitButtonListener() {
		commitButton.addActionListener(new ActionListener() {

			@Override
      public void actionPerformed(ActionEvent e) {
				String message = "";
				if (!gitAccess.getConflictingFiles().isEmpty()) {
					message = translator.getTranslation(Tags.COMMIT_WITH_CONFLICTS);
				} else {
					message = translator.getTranslation(Tags.COMMIT_SUCCESS);
					gitAccess.commit(commitMessage.getText());
					OptionsManager.getInstance().saveCommitMessage(commitMessage.getText());
					
					previousMessages.removeAllItems();
					previousMessages.addItem(getCommitHistoryHint());
					for (String previouslyCommitMessage : OptionsManager.getInstance().getPreviouslyCommitedMessages()) {
						previousMessages.addItem(previouslyCommitMessage);
					}
					
					previousMessages.setSelectedItem(getCommitHistoryHint());

					commitButton.setEnabled(false);
				}
				commitMessage.setText("");
				PushPullEvent pushPullEvent = new PushPullEvent(ActionStatus.UPDATE_COUNT, message);
				notifyObservers(pushPullEvent);
			}
		});
	}

	private void addLabel(GridBagConstraints gbc) {
		gbc.insets = new Insets(UIConstants.COMPONENT_TOP_PADDING, UIConstants.COMPONENT_LEFT_PADDING,
				UIConstants.COMPONENT_BOTTOM_PADDING, UIConstants.COMPONENT_RIGHT_PADDING);
		gbc.anchor = GridBagConstraints.WEST;
		gbc.fill = GridBagConstraints.NONE;
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx = 0;
		gbc.weighty = 0;
		gbc.gridwidth = 2;
		this.add(new JLabel(translator.getTranslation(Tags.COMMIT_MESSAGE_LABEL)), gbc);
	}

	private void addPreviouslyMessagesComboBox(GridBagConstraints gbc) {
		gbc.insets = new Insets(
		    UIConstants.COMPONENT_TOP_PADDING, 
		    UIConstants.COMPONENT_LEFT_PADDING,
			UIConstants.COMPONENT_BOTTOM_PADDING, 
			UIConstants.COMPONENT_RIGHT_PADDING);
		gbc.anchor = GridBagConstraints.WEST;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.weightx = 1;
		gbc.weighty = 0;
		gbc.gridwidth = 2;
		previousMessages = new JComboBox<>();
		PreviousMessagesToolTipRenderer renderer = new PreviousMessagesToolTipRenderer();
		previousMessages.setRenderer(renderer);

		// Add the hint first.
		previousMessages.addItem(getCommitHistoryHint());
		for (String previouslyCommitMessage : OptionsManager.getInstance().getPreviouslyCommitedMessages()) {
			previousMessages.addItem(previouslyCommitMessage);
		}
		
		previousMessages.setSelectedItem(getCommitHistoryHint());
		
		previousMessages.addItemListener(new ItemListener() {
      @Override
      public void itemStateChanged(ItemEvent e) {
        if (e.getStateChange() == ItemEvent.SELECTED
            && !previousMessages.getSelectedItem().equals(getCommitHistoryHint())) {
            commitMessage.setText((String) previousMessages.getSelectedItem());
        }
      }
    });

		int height = (int) previousMessages.getPreferredSize().getHeight();
		previousMessages.setMinimumSize(new Dimension(10, height));

		this.add(previousMessages, gbc);
	}

	/**
	 * @return The message that instructs the user to select a previously used message.
	 */
  private String getCommitHistoryHint() {
    return "<" + translator.getTranslation(Tags.COMMIT_COMBOBOX_DISPLAY_MESSAGE) + ">";
  }

	private void addCommitMessageTextArea(GridBagConstraints gbc) {
		gbc.insets = new Insets(UIConstants.COMPONENT_TOP_PADDING, UIConstants.COMPONENT_LEFT_PADDING,
				UIConstants.COMPONENT_BOTTOM_PADDING, UIConstants.COMPONENT_RIGHT_PADDING);
		gbc.anchor = GridBagConstraints.WEST;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.weightx = 1;
		gbc.weighty = 1;
		gbc.gridwidth = 2;
		commitMessage = new JTextArea();
		commitMessage.setLineWrap(true);
		// Around 3 lines of text.
		int fontH = commitMessage.getFontMetrics(commitMessage.getFont()).getHeight();
		commitMessage.setWrapStyleWord(true);
		JScrollPane scrollPane = new JScrollPane(commitMessage);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setMinimumSize(new Dimension(10, 3 * fontH));

		UndoSupportInstaller.installUndoManager(commitMessage);
		this.add(scrollPane, gbc);
	}

	private void addStatusLabel(GridBagConstraints gbc) {
		gbc.insets = new Insets(UIConstants.COMPONENT_TOP_PADDING, UIConstants.COMPONENT_LEFT_PADDING,
				UIConstants.COMPONENT_BOTTOM_PADDING, UIConstants.COMPONENT_RIGHT_PADDING);
		gbc.anchor = GridBagConstraints.WEST;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.weightx = 0;
		gbc.weighty = 0;
		statusLabel = new JLabel();
		this.add(statusLabel, gbc);
	}

	private void addCommitButton(GridBagConstraints gbc) {
		gbc.insets = new Insets(UIConstants.COMPONENT_TOP_PADDING, UIConstants.COMPONENT_LEFT_PADDING,
				UIConstants.COMPONENT_BOTTOM_PADDING, UIConstants.COMPONENT_RIGHT_PADDING);
		gbc.anchor = GridBagConstraints.EAST;
		gbc.fill = GridBagConstraints.NONE;
		gbc.gridx = 1;
		gbc.gridy = 3;
		gbc.weightx = 1;
		gbc.weighty = 0;
		commitButton = new JButton(translator.getTranslation(Tags.COMMIT_BUTTON_TEXT));
		toggleCommitButton(false);
		this.add(commitButton, gbc);
	}

  /**
   * Checks if the commit button should be enabled.
   * 
   * @param forceEnable <code>true</code> to make the button enable without any additional checks.
   */
  private void toggleCommitButton(boolean forceEnable) {
    boolean enable = false;
    if (forceEnable) {
      enable = true;
    } else {
      try {
        RepositoryState repositoryState = gitAccess.getRepository().getRepositoryState();
        if (repositoryState == RepositoryState.MERGING_RESOLVED
            && gitAccess.getStagedFiles().isEmpty() && gitAccess.getUnstagedFiles().isEmpty()) {
          enable = true;
          commitMessage.setText(translator.getTranslation(Tags.CONCLUDE_MERGE_MESSAGE));
        } else if (!gitAccess.getStagedFiles().isEmpty()) {
          enable = true;
        } else if (repositoryState == RepositoryState.MERGING
            && translator.getTranslation(Tags.CONCLUDE_MERGE_MESSAGE).equals(commitMessage.getText())) {
          commitMessage.setText("");
        }
      } catch (NoRepositorySelected e) {
        // Remains disabled
      }
    }

    commitButton.setEnabled(enable);

  }

	private void notifyObservers(PushPullEvent pushPullEvent) {
		observer.stateChanged(pushPullEvent);
	}

	@Override
  public void addObserver(Observer<PushPullEvent> observer) {
		if (observer == null)
			throw new NullPointerException("Null Observer");

		this.observer = observer;
	}

	@Override
  public void removeObserver(Observer<PushPullEvent> obj) {
		observer = null;
	}

	/**
	 * Update status.
	 * 
	 * @param the current status.
	 */
	public void setStatus(final RepositoryStatus status) {
		if (RepositoryStatus.UNAVAILABLE == status) {
			statusLabel.setText(translator.getTranslation(Tags.CANNOT_REACH_HOST));
			ImageUtilities imageUtilities = PluginWorkspaceProvider.getPluginWorkspace().getImageUtilities();
			URL resource = getClass().getResource(ImageConstants.VALIDATION_ERROR);
			if (resource != null) {
			  ImageIcon icon = (ImageIcon) imageUtilities.loadIcon(resource);
			  statusLabel.setIcon(icon);
			}
		} else if (RepositoryStatus.AVAILABLE == status) {
		  statusLabel.setText(null);
		  statusLabel.setIcon(null);
		}
	}

	/**
	 * Resets the panel. Clears any selection done by the user or inserted text.
	 */
	public void reset() {
	  previousMessages.setSelectedItem(getCommitHistoryHint());
		commitMessage.setText(null);
	}

	/**
	 * Renderer for the combo box presenting the previous commit messages. 
	 */
	private static final class PreviousMessagesToolTipRenderer extends DefaultListCellRenderer {

	  /**
	   * Maximum tooltip width.
	   */
		private static final int MAX_TOOLTIP_WIDTH = 700;

		@Override
		public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,
				boolean cellHasFocus) {
			JLabel comp = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
			if (value != null) {
			  JToolTip createToolTip = comp.createToolTip();
			  Font font = createToolTip.getFont();
			  FontMetrics fontMetrics = getFontMetrics(font);
			  int length = fontMetrics.stringWidth((String) value);
			  if (length < MAX_TOOLTIP_WIDTH) {
			    comp.setToolTipText("<html><p width=\"" + length + "\">" + value + "</p></html>");
			  } else {
			    comp.setToolTipText("<html><p width=\"" + MAX_TOOLTIP_WIDTH + "\">" + value + "</p></html>");
			  }
			}
			return comp;
		}
	}
	
	public JTextArea getCommitMessage() {
    return commitMessage;
  }

	/**
	 * Update the status.
	 * 
	 * @param message New status.
	 */
  public void setStatus(String message) {
    statusLabel.setIcon(null);
    statusLabel.setText(message);
  }
}
