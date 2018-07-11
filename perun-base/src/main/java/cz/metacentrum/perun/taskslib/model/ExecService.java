package cz.metacentrum.perun.taskslib.model;

import java.io.Serializable;

import cz.metacentrum.perun.core.api.Service;
import cz.metacentrum.perun.core.api.PerunBean;
import cz.metacentrum.perun.core.api.BeansUtils;

/**
 * @author Michal Karm Babacek
 */
@Deprecated
public class ExecService extends PerunBean implements Serializable {
	private static final long serialVersionUID = 1610949885568207786L;

	private int defaultDelay;
	private int defaultRecurrence = 2;
	private boolean enabled;
	private Service service;
	private String script;

	public int getDefaultDelay() {
		return defaultDelay;
	}

	public void setDefaultDelay(int defaultDelay) {
		this.defaultDelay = defaultDelay;
	}

	public int getDefaultRecurrence() {
		return defaultRecurrence;
	}

	public void setDefaultRecurrence(int defaultRecurrence) {
		this.defaultRecurrence = defaultRecurrence;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public String getScript() {
		return script;
	}

	public void setScript(String script) {
		this.script = script;
	}

	public String getBeanName(){
		return this.getClass().getSimpleName();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + getId();
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ExecService other = (ExecService) obj;
		if (this.getId() != other.getId())
			return false;
		if(this.isEnabled() != other.isEnabled())
			return false;
		if(this.getService() == null ? other.getService() != null : !this.getService().equals(other.getService()))
			return false;
		if(this.getScript() == null ? other.getScript() != null : !this.getScript().equals(other.getScript()))
			return false;
		return true;
	}

	public void setService(Service service) {
		this.service = service;
	}

	public Service getService() {
		return service;
	}

	@Override
	public String serializeToString() {
		StringBuilder str = new StringBuilder();

		return str.append(this.getClass().getSimpleName()).append(":[").append(
			"id=<").append(getId()).append(">").append(
			", defaultDelay=<").append(this.getDefaultDelay()).append(">").append(
			", defaultRecurrence=<").append(this.getDefaultRecurrence()).append(">").append(
			", enabled=<").append(this.isEnabled()).append(">").append(
			", script=<").append(script == null ? "\\0" : BeansUtils.createEscaping(script)).append(">").append(
			", name=<").append(service == null ? "\\0" : BeansUtils.createEscaping(service.getName())).append(">").append(
			", service=<").append(getService()== null ? "\\0" : getService().serializeToString()).append(">").append(
			']').toString();
	}

	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();

		String serviceName = null;
		if(service != null && service.getName() != null) serviceName = service.getName();
		return str.append(getClass().getSimpleName()).append(":["
			).append("id='").append(getId()
			).append("', defaultDelay='").append(getDefaultDelay()
			).append("', defaultRecurrence='").append(getDefaultRecurrence()
			).append("', enabled='").append(isEnabled()
			).append("', script='").append(script
			).append("', name='").append(serviceName
			).append("', service='").append(getService()
			).append("']").toString();
	}
}
