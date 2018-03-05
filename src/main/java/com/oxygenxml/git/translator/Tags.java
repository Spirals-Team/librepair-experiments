package com.oxygenxml.git.translator;

/**
 * Constants used for translation
 * 
 * @author Beniamin Savu
 *
 */
public class Tags {
  /**
   * Private constructor.
   */
  private Tags() {
    // Nothing.
  }
  /**
   * en: Yes
   */
  public static final String YES = "Yes";
  /**
   * en: No
   */
  public static final String NO = "No";
  
  /**
   * Message shown when a previous SSH passphrase was invalid.
   * 
   * en: The previous passphrase is invalid.
   */
  public static final String PREVIOUS_PASSPHRASE_INVALID = "Previous_passphrase_invalid";
  /**
   * Label.
   * 
   * en: Pull status
   */
  public static final String PULL_STATUS = "Pull_status";
  /**
   * Message asking for the SSH passphrase.
   * 
   * en: Please enter your SSH passphrase.
   */
  public static final String ENTER_SSH_PASSPHRASE = "Enter_ssh_passphrase";
  /**
   * Shown when a command is aborted.
   * 
   * en: Command aborted.
   */
  public static final String COMMAND_ABORTED = "Command_aborted";

	/**
	 * Label displayed on the left of the working copy combo box
	 */
	public static final String WORKING_COPY_LABEL = "Working_Copy_Label";

	/**
	 * The tooltip for the push button
	 */
	public static final String PUSH_BUTTON_TOOLTIP = "Push_Button_ToolTip";

	/**
	 * The tooltip for the pull button
	 */
	public static final String PULL_BUTTON_TOOLTIP = "Pull_Button_ToolTip";

	/**
	 * The tooltip for the browse button
	 */
	public static final String BROWSE_BUTTON_TOOLTIP = "Browse_Button_ToolTip";

	/**
	 * The text displayed on the StageAll button
	 */
	public static final String STAGE_ALL_BUTTON_TEXT = "Stage_All_Button_Text";

	/**
	 * The text displayed on the UnstageAll button
	 */
	public static final String UNSTAGE_ALL_BUTTON_TEXT = "Unstage_All_Button_Text";

	/**
	 * The text displayed on the Stage Selected button
	 */
	public static final String STAGE_SELECTED_BUTTON_TEXT = "Stage_Selected_Button_Text";

	/**
	 * The text displayed on the Unstage Selected button
	 */
	public static final String UNSTAGE_SELECTED_BUTTON_TEXT = "Unstage_Selected_Button_Text";

	/**
	 * The tooltip for the ChangeView button
	 */
	public static final String CHANGE_VIEW_BUTTON_TOOLTIP = "Change_View_Button_ToolTip";

	/**
	 * The massage displayed above the previously committed messages combo box
	 */
	public static final String COMMIT_MESSAGE_LABEL = "Commit_Message_Label";

	/**
	 * The massage displayed on the combo box containing the previouslt commited
	 * messages
	 */
	public static final String COMMIT_COMBOBOX_DISPLAY_MESSAGE = "Commit_ComboBox_Display_Message";

	/**
	 * The massage displayed on the commit button
	 */
	public static final String COMMIT_BUTTON_TEXT = "Commit_Button_Text";

	/**
	 * The tooltip for the "+" icon that appears on the left side of the file
	 */
	public static final String ADD_ICON_TOOLTIP = "Add_Icon_ToolTip";

	/**
	 * The tooltip for the "*" icon that appears on the left side of the file
	 */
	public static final String MODIFIED_ICON_TOOLTIP = "Modified_Icon_ToolTip";

	/**
	 * The tooltip for the "-" icon that appears on the left side of the file
	 */
	public static final String DELETE_ICON_TOOLTIP = "Delete_Icon_ToolTip";

	/**
	 * The tooltip for the "!" icon that appears on the left side of the file
	 */
	public static final String CONFLICT_ICON_TOOLTIP = "Conflict_Icon_ToolTip";

	/**
	 * The massage displayed when a commit is successful
	 */
	public static final String COMMIT_SUCCESS = "Commit_Success";

	/**
	 * The massage displayed when you have conflicts
	 */
	public static final String COMMIT_WITH_CONFLICTS = "Commit_With_Conflicts";

	/**
	 * The massage displayed when you push with conflicts
	 */
	public static final String PUSH_WITH_CONFLICTS = "Push_With_Conflicts";

	/**
	 * The massage displayed when you push but your repository is not up to date
	 */
	public static final String BRANCH_BEHIND = "Branch_Behind";

	/**
	 * The massage displayed when you push successful
	 */
	public static final String PUSH_SUCCESSFUL = "Push_Successful";

	/**
	 * The massage displayed when your push fails
	 */
	public static final String PUSH_FAILED_UNKNOWN = "Push_Failed_Unknown";

	/**
	 * The massage displayed when you push with no changes
	 */
	public static final String PUSH_UP_TO_DATE = "Push_Up_To_Date";

	/**
	 * The massage displayed when your push is in progress
	 */
	public static final String PUSH_IN_PROGRESS = "Push_In_Progress";

	/**
	 * The massage displayed when you pull with uncommitted files
	 */
	public static final String PULL_WITH_UNCOMMITED_CHANGES = "Pull_With_Uncommitted_Changes";

	/**
	 * The massage displayed when your repository is up to date
	 */
	public static final String PULL_UP_TO_DATE = "Pull_Up_To_Date";

	/**
	 * The massage displayed when your pull is successful
	 */
	public static final String PULL_SUCCESSFUL = "Pull_Successful";

	/**
	 * The massage displayed when your pull is in progress
	 */
	public static final String PULL_IN_PROGRESS = "Pull_In_Progress";

	/**
	 * The massage displayed when you pull while having conflicts
	 */
	public static final String PULL_WITH_CONFLICTS = "Pull_With_Conflicts";

	/**
	 * The massage displayed when your pull is successful but has conflicts
	 */
	public static final String PULL_SUCCESSFUL_CONFLICTS = "Pull_Successful_Conflicts";

	/**
	 * The text displayed for the "Open in compare editor" contextual menu item
	 */
	public static final String CONTEXTUAL_MENU_OPEN_IN_COMPARE = "Contextual_Menu_Open_In_Compare";

	/**
	 * The text displayed for the "Open" contextual menu item
	 */
	public static final String CONTEXTUAL_MENU_OPEN = "Contextual_Menu_Open";

	/**
	 * The text displayed for the "Stage" contextual menu item
	 */
	public static final String CONTEXTUAL_MENU_STAGE = "Contextual_Menu_Stage";

	/**
	 * The text displayed for the "Unstage" contextual menu item
	 */
	public static final String CONTEXTUAL_MENU_UNSTAGE = "Contextual_Menu_Unstage";

	/**
	 * The text displayed for the "Resolve_Conflict" contextual menu item
	 */
	public static final String CONTEXTUAL_MENU_RESOLVE_CONFLICT = "Contextual_Menu_Resolve_Conflict";

	/**
	 * The text displayed for the "Discard" contextual menu item
	 */
	public static final String CONTEXTUAL_MENU_DISCARD = "Contextual_Menu_Discard";

	/**
	 * The text displayed when you click on the "Discard" contextual menu item
	 */
	public static final String CONTEXTUAL_MENU_DISCARD_CONFIRMATION_MESSAGE = "Contextual_Menu_Discard_Confirmation_Message";

	/**
	 * The text displayed for the "Resolve Using "Mine" " contextual menu item
	 */
	public static final String CONTEXTUAL_MENU_RESOLVE_USING_MINE = "Contextual_Menu_Resolve_Using_Mine";

	/**
	 * The text displayed for the "Resolve Using "Theirs" " contextual menu item
	 */
	public static final String CONTEXTUAL_MENU_RESOLVE_USING_THEIRS = "Contextual_Menu_Resolve_Using_Theirs";

	/**
	 * The text displayed for the "Restart Merge " contextual menu item
	 */
	public static final String CONTEXTUAL_MENU_RESTART_MERGE = "Contextual_Menu_Restart_Merge";

	/**
	 * The text displayed for the "Mark Resolved" contextual menu item
	 */
	public static final String CONTEXTUAL_MENU_MARK_RESOLVED = "Contextual_Menu_Mark_Resolved";

	/**
	 * The text displayed when the user selects a repository from the working copy
	 * selector and it doesn't exists anymore
	 */
	public static final String WORKINGCOPY_REPOSITORY_NOT_FOUND = "Workingcopy_Repository_Not_Found";

	/**
	 * The text displayed when the user selects a non git folder(It doesn't
	 * contain the ".git" folder)
	 */
	public static final String WORKINGCOPY_NOT_GIT_DIRECTORY = "Workingcopy_Not_Git_Directory";

	/**
	 * The text displayed when the application starts and the last selected
	 * repository doesn't exists
	 */
	public static final String WORKINGCOPY_LAST_SELECTED_REPOSITORY_DELETED = "Workingcopy_Last_Selected_Repository_Deleted";

	/**
	 * The text displayed after exiting the diff for a conflict file and you don't
	 * modify anithing
	 */
	public static final String CHECK_IF_CONFLICT_RESOLVED = "Check_If_Conflict_Resolved";

	/**
	 * The text displayed in the title for the dialog that appears after exiting
	 * the diff for a conflict file and you don't modify anithing
	 */
	public static final String CHECK_IF_CONFLICT_RESOLVED_TITLE = "Title_Check_If_Conflict_Resolved";

	/**
	 * The text displayed in the title for the dialog that appears when you select
	 * your current branch
	 */
	public static final String BRANCH_SELECTION_DIALOG_TITLE = "Branch_Selection_Dialog_Title";

	/**
	 * The text displayed in the dialog that appears near the combo box
	 */
	public static final String BRANCH_DIALOG_BRANCH_SELECTION_LABEL = "Branch_Dialog_Branch_Selection_Label";

	/**
	 * The tooltip for the change branch button
	 */
	public static final String CHANGE_BRANCH_BUTTON_TOOLTIP = "Change_Branch_Button_Tooltip";

	/**
	 * The the message displayed when branch selection fails
	 */
	public static final String CHANGE_BRANCH_ERROR_MESSAGE = "Change_Branch_Error_Message";

	/**
	 * The text displayed in the title for the dialog that appears when you pull
	 * and bring conflicts
	 */
	public static final String PULL_WITH_CONFLICTS_DIALOG_TITLE = "Pull_With_Conflicts_Dialog_Title";

	/**
	 * The text displayed in the title for the dialog that appears when you push
	 * while having no remote set
	 */
	public static final String ADD_REMOTE_DIALOG_TITLE = "Add_Remote_Dialog_Title";

	/**
	 * The text displayed in the dialog that appears near the text field for
	 * remote name
	 */
	public static final String ADD_REMOTE_DIALOG_ADD_REMOTE_NAME_LABEL = "Add_Remote_Dialog_Add_Remote_Name_Label";

	/**
	 * The text displayed in the dialog that appears near the text field for
	 * remote repo
	 */
	public static final String ADD_REMOTE_DIALOG_ADD_REMOTE_REPO_LABEL = "Add_Remote_Dialog_Add_Remote_Repo_Label";

	/**
	 * The text displayed in the dialog that appears at the top of the dialog
	 */
	public static final String ADD_REMOTE_DIALOG_INFO_LABEL = "Add_Remote_Dialog_Info_Label";

	/**
	 * The text displayed in the title for the dialog that appears if the
	 * project.xpr is not a git repository and has no got repositories
	 */
	public static final String CHECK_PROJECTXPR_IS_GIT_TITLE = "Check_ProjcetXPR_Is_Git_Title";

	/**
	 * The text displayed in the dialog that appears if the project.xpr is not a
	 * git repository and has no got repositories
	 * 
	 * en: Do you want your current project ("{0}") to be a git project?
	 */
	public static final String CHECK_PROJECTXPR_IS_GIT = "Check_ProjcetXPR_Is_Git";

	/**
	 * The tooltip for the select submodule button
	 */
	public static final String SELECT_SUBMODULE_BUTTON_TOOLTIP = "Select_Submodule_Button_Tooltip";

	/**
	 * The text displayed in the dialog that appears near the combo box
	 */
	public static final String SUBMODULE_DIALOG_SUBMODULE_SELECTION_LABEL = "Submodule_Dialog_Submodule_Selection_Label";

	/**
	 * The text displayed in the title for the dialog that appears when you select
	 * a submodule
	 */
	public static final String SUBMODULE_DIALOG_TITLE = "Submodule_Dialog_Title";

	/**
	 * The tooltip for the submodule icon that appears on the left side of the
	 * file
	 */
	public static final String SUBMODULE_ICON_TOOLTIP = "Submodule_Icon_Tooltip";

	/**
	 * The text displayed on the label for the username
	 */
	public static final String LOGIN_DIALOG_USERNAME_LABEL = "Login_Dilaog_Username_Label";

	/**
	 * The text displayed on the label for the password
	 */
	public static final String LOGIN_DIALOG_PASS_WORD_LABEL = "Login_Dilaog_Password_Label";

	/**
	 * The text displayed on the title for the login dialog
	 */
	public static final String LOGIN_DIALOG_TITLE = "Login_Dilaog_Title";

	/**
	 * The text displayed above the text fields
	 */
	public static final String LOGIN_DIALOG = "Login_Dilaog";

	/**
	 * The text displayed on the first row of the login dialog if there are no
	 * credentials stored
	 */
	public static final String LOGIN_DIALOG_CREDENTIALS_NOT_FOUND_MESSAGE = "Login_Dilaog_Credentials_Not_Found_Message";

	/**
	 * The text displayed on the first row of the login dialog if the credentials
	 * are invalid
	 */
	public static final String LOGIN_DIALOG_CREDENTIALS_INVALID_MESSAGE = "Login_Dilaog_Credentials_Invalid_Message";

	/**
	 * The text displayed on the right side of the toolbar buttons for the branch
	 * text
	 */
	public static final String TOOLBAR_PANEL_INFORMATION_STATUS_BRANCH = "Toolbar_Panel_Information_Status_Branch";

	/**
	 * The text displayed on the right side of the toolbar buttons if the
	 * repository is one commit behind
	 */
	public static final String TOOLBAR_PANEL_INFORMATION_STATUS_SINGLE_COMMIT = "Toolbar_Panel_Information_Status_Single_Commit";

	/**
	 * The text displayed on the right side of the toolbar buttons if the
	 * repository is 2 or more commits behind
	 */
	public static final String TOOLBAR_PANEL_INFORMATION_STATUS_MULTIPLE_COMMITS = "Toolbar_Panel_Information_Status_Multiple_Commits";

	/**
	 * The text displayed on the right side of the toolbar buttons if the
	 * repository is up to date
	 */
	public static final String TOOLBAR_PANEL_INFORMATION_STATUS_UP_TO_DATE = "Toolbar_Panel_Information_Status_Up_To_Date";

	/**
	 * The text displayed on the right side of the toolbar buttons if the
	 * repository has a detached head
	 */
	public static final String TOOLBAR_PANEL_INFORMATION_STATUS_DETACHED_HEAD = "Toolbar_Panel_Information_Status_Detached_Head";

	/**
	 * The text displayed for the "Git" contextual menu item in the project view
	 */
	public static final String PROJECT_VIEW_GIT_CONTEXTUAL_MENU_ITEM = "Project_View_Git_Contextual_Menu_Item";

	/**
	 * The text displayed for the "Git Diff" contextual menu item in the project
	 * view
	 */
	public static final String PROJECT_VIEW_GIT_DIFF_CONTEXTUAL_MENU_ITEM = "Project_View_Git_Diff_Contextual_Menu_Item";

	/**
	 * The text displayed for the "Commit" contextual menu item in the project
	 * view
	 */
	public static final String PROJECT_VIEW_COMMIT_CONTEXTUAL_MENU_ITEM = "Project_View_Commit_Contextual_Menu_Item";

	/**
	 * The text displayed when you push but don't have rights for that repository
	 */
	public static final String NO_RIGHTS_TO_PUSH_MESSAGE = "No_Right_To_Push_Message";

	/**
	 * The text displayed on the first row of the login dialog if the user entered
	 * doesn't have rights for that repository
	 */
	public static final String LOGIN_DIALOG_CREDENTIALS_DOESNT_HAVE_RIGHTS = "Login_Dialog_Credentials_Doesnt_Have_Rights";

	/**
	 * The tooltip for the ChangeView button when the icon shows the tree view
	 * icon
	 */
	public static final String CHANGE_TREE_VIEW_BUTTON_TOOLTIP = "Change_Tree_View_Button_ToolTip";

	/**
	 * The tooltip for the ChangeView button when the icon shows the flat view
	 * icon
	 */
	public static final String CHANGE_FLAT_VIEW_BUTTON_TOOLTIP = "Change_Flat_View_Button_ToolTip";

	/**
	 * The text displayed on the first row of the login dialog if the repository
	 * is private
	 */
	public static final String LOGIN_DIALOG_PRIVATE_REPOSITORY_MESSAGE = "Login_Dialog_Private_Repository_Message";

	/**
	 * The tooltip for the clone repository button
	 */
	public static final String CLONE_REPOSITORY_BUTTON_TOOLTIP = "Clone_Repository_Button_Tooltip";

	/**
	 * The text displayed in the title for the dialog that appears when you clone
	 * a new repository
	 */
	public static final String CLONE_REPOSITORY_DIALOG_TITLE = "Clone_Repository_Dialog_Title";

	/**
	 * The text displayed for the "URL" label in the clone repository dialog
	 */
	public static final String CLONE_REPOSITORY_DIALOG_URL_LABEL = "Clone_Repository_Dialog_Url_Label";

	/**
	 * The text displayed for the "Destination Path" label in the clone repository
	 * dialog
	 */
	public static final String CLONE_REPOSITORY_DIALOG_DESTINATION_PATH_LABEL = "Clone_Repository_Dialog_Destination_Path_Label";

	/**
	 * The text displayed if the url is invalid
	 */
	public static final String CLONE_REPOSITORY_DIALOG_INVALID_URL = "Clone_Repository_Dialog_Invalid_Url";

	/**
	 * The text displayed if the destination path is invalid
	 */
	public static final String CLONE_REPOSITORY_DIALOG_INVALID_DESTINATION_PATH = "Clone_Repository_Dialog_Invalid_Destination_Path";

	/**
	 * The text displayed if an error occured during cloning
	 */
	public static final String CLONE_REPOSITORY_DIALOG_CLONE_ERROR = "Clone_Repository_Dialog_Clone_Error";

	/**
	 * The text displayed if the chosen destionation path is not an empty folder
	 */
	public static final String CLONE_REPOSITORY_DIALOG_DESTINATION_PATH_NOT_EMPTY = "Clone_Repository_Dialog_Destination_Path_Not_Empty";

	/**
	 * The text displayed if the URL doesn't point to a remote repository
	 */
	public static final String CLONE_REPOSITORY_DIALOG_URL_IS_NOT_A_REPOSITORY = "Clone_Repository_Dialog_Url_Is_Not_A_Repository";

	/**
	 * The text displayed in the login dialog if you are not authorized to clone
	 * the repository
	 */
	public static final String CLONE_REPOSITORY_DIALOG_LOGIN_MESSAGE = "Clone_Repository_Dialog_Login_Message";

	/**
	 * The text in the title of the cloning progress dialog
	 */
	public static final String CLONE_PROGRESS_DIALOG_TITLE = "Cloning_Progress_Dialog_TItle";

	/**
	 * The text is displayed in the bottom left corner in the commit panel when
	 * the host is down
	 */
	public static final String CANNOT_REACH_HOST = "Cannot_Reach_Host";

	/**
	 * The text is displayed when your repository is on a detached head
	 */
	public static final String DETACHED_HEAD_MESSAGE = "Detached_Head_Message";

	/**
	 * The text is displayed you fix all conflicts and there is nothing to commit
	 */
	public static final String CONCLUDE_MERGE_MESSAGE = "Conclude_Merge_Message";

	/**
	 * The text is displayed when you pull but it fails because the files that are
	 * modified might become in conflict
	 */
	public static final String PULL_CHECKOUT_CONFLICT_MESSAGE = "Pull_Checkout_Conflict_Message";
}
