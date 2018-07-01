package org.eclipse.internal.cpacep.view;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageDescriptor;
import org.osgi.framework.FrameworkUtil;

public abstract class ActionEvent extends Action{
	private ViewToolBar containingToolBar = null;

	public ActionEvent(ViewToolBar toolbar) {
		containingToolBar = toolbar;
		setImageDescriptor(ImageDescriptor.createFromURL(FileLocator.find(FrameworkUtil.getBundle(this.getClass()), getImagePath(), null)));
		setDisabledImageDescriptor(ImageDescriptor.createFromURL(FileLocator.find(FrameworkUtil.getBundle(this.getClass()), getDisabledImagePath(), null)));
		updateEnablementState();
	}

	public abstract IPath getImagePath();

	public abstract IPath getDisabledImagePath();

	public abstract void updateEnablementState();
}
