package org.eclipse.internal.cpacep.dialog;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.cpacep.util.StatisticsData;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.osgi.framework.FrameworkUtil;



public class Statistics extends TitleAreaDialog {
    
    private List itemSet;
    private Text bodyText;
    
    private java.util.List<StatisticsData> stats;

    public Statistics(Shell parentShell) {
	super(parentShell);

    }

    @Override
    public void create() {
	super.create();
	setTitle(Messages.DialogViewForm_StatisticsTitle);
	setMessage(Messages.DialogViewForm_StatisticsMessage, IMessageProvider.INFORMATION);
	setTitleImage(getImage(new Path("icons/cpac_logo.png")));

    }

    public Image getImage(Path path) {
	ImageDescriptor imageDescriptor = ImageDescriptor
		.createFromURL(FileLocator.find(FrameworkUtil.getBundle(this.getClass()), path, null));
	return imageDescriptor.createImage();
    }

    @Override
    protected Control createDialogArea(Composite parent) {
	Composite area = (Composite) super.createDialogArea(parent);
	Composite container = new Composite(area, SWT.NONE);
	container.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
	GridLayout layout = new GridLayout(2, false);
	container.setLayout(layout);

	createLeftPanel(container);
	createRightPanel(container);
	return area;
    }
    
    public void createLeftPanel(Composite container) {
	Group leftGroup=new Group(container, SWT.NONE);
	leftGroup.setText(Messages.DialogViewForm_LeftPanelTitle);
	GridData leftGroupGridData = new GridData();
	leftGroupGridData.grabExcessVerticalSpace = true;
	leftGroupGridData.verticalAlignment = GridData.FILL;
	leftGroup.setLayoutData(leftGroupGridData);
	
	GridLayout leftGridLayout=new GridLayout(1,false);

	leftGroup.setLayout(leftGridLayout);
	itemSet=new List(leftGroup,SWT.NONE);
	itemSet.addSelectionListener(new SelectionAdapter() {
	    public void widgetSelected(SelectionEvent e) {
		itemSelectButton();
	    }
	});
	loadItems();
    }
    
    public void createRightPanel(Composite container) {
	Group rightGroup=new Group(container, SWT.BORDER);
	rightGroup.setText(Messages.DialogViewForm_RightPanelTitle);
	GridData rightGroupGridData = new GridData();
	rightGroupGridData.grabExcessVerticalSpace = true;
	rightGroupGridData.grabExcessHorizontalSpace=true;
	rightGroupGridData.verticalAlignment = GridData.FILL;
	rightGroupGridData.horizontalAlignment=GridData.FILL;
	rightGroup.setLayoutData(rightGroupGridData);
	
	GridLayout rightGridLayout=new GridLayout(1,false);
	rightGroup.setLayout(rightGridLayout);
	
	bodyText=new Text(rightGroup, SWT.H_SCROLL | SWT.V_SCROLL | SWT.READ_ONLY);
	bodyText.setLayoutData(new GridData(GridData.FILL, GridData.FILL, true, true));
	
    }
    
    public void itemSelectButton() {
	bodyText.setText("");
	String selection[]=itemSet.getSelection();
	for(StatisticsData stat:getStatistics()) {
	    if(stat.getHeader().equals(selection[selection.length-1])) {
		bodyText.append(getBodyAsString(stat.getBody()));	
	    }
	}
	
    }
    
    public String getBodyAsString(java.util.List<String> str) {
	StringBuilder stb=new StringBuilder();
	for(String s:str) {
	    stb.append(s);
	    stb.append("\n");
	}
	return stb.toString();
    }
    public void loadItems() {
	for(StatisticsData stat:getStatistics()) {
	    itemSet.add(stat.getHeader());
	}
    }

    @Override
    protected boolean isResizable() {
	return true;
    }

    @Override
    protected void okPressed() {
	super.okPressed();
    }
    
    public void setStats(java.util.List<StatisticsData> stats) {
	this.stats=stats;
	
    }
    public java.util.List<StatisticsData> getStatistics(){
	return stats;
    }

}
