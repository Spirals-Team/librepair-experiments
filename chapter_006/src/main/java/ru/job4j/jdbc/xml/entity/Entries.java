package ru.job4j.jdbc.xml.entity;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * @author Yury Matskevich
 */
@XmlRootElement
public class Entries {
	private List<Entry> entry;

	public Entries() {

	}

	public Entries(List<Entry> entry) {
		this.entry = entry;
	}

	public List<Entry> getEntry() {
		return entry;
	}

	public void setEntry(List<Entry> entry) {
		this.entry = entry;
	}
}
