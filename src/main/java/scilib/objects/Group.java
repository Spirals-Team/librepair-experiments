package scilib.objects;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import scilib.checkers.Filter;
import scilib.utilities.TeamDataComparator;

/**
 * Organizer which manages {@link TeamData} objects.
 * 
 * <p>You can add and remove {@link TeamData} objects to this object,
 * and set filters and sorters to organize them.</p>
 * <p>{@link TeamData} objects which are being filtered and sorted are part of the {@link TeamData} pool.
 * {@link TeamData} objects from the pool which have passed through the {@link Sorter} and {@link Filter}
 * are part of the active {@link TeamData} list.</p>
 * @author Auxiliatrix
 *
 */
public class Group {
	
	private ArrayList<TeamData> originalList;
	private ArrayList<TeamData> currentList;
	
	public String name;
	public Sorter sorter;
	public Filter filter;
	
	/**
	 * Creates a Group object with a given name and no TeamData objects.
	 * @param name - Name by which to reference the Group
	 */
	public Group(String name) {
		this(name, new ArrayList<TeamData>());
	}
	
	/**
	 * Creates a Group object with a given name and an initial ArrayList of TeamData objects.
	 * @param name - String name by which to reference the Group
	 * @param list - ArrayList of TeamData objects the Group starts with
	 */
	public Group(String name, ArrayList<TeamData> list) {
		this.originalList = new ArrayList<TeamData>(list);
		this.currentList = new ArrayList<TeamData>();
		
		this.name = name;
		this.sorter = new Sorter();
		this.filter = new Filter();
	}
	
	/**
	 * Gets the TeamData objects which are a part of the active TeamData list.
	 * @return a shallow copy of the active TeamData list.
	 */
	public ArrayList<TeamData> getTeamList() {
		update();
		return new ArrayList<TeamData>(currentList);
	}
	
	/**
	 * Gets the pool of TeamData objects which are being sorted and filtered.
	 * @return a shallow copy of the TeamData pool.
	 */
	public ArrayList<TeamData> getTeamPool() {
		return new ArrayList<TeamData>(originalList);
	}
	/**
	 * Tests if a TeamData object is in active TeamData list.
	 * @param td - TeamData object to be tested
	 * @return true if the given TeamData object is contained in the active TeamData list.
	 */
	public boolean teamListContains(TeamData td) {
		update();
		return currentList.contains(td);
	}
	
	/**
	 * Tests if a TeamData object is in the TeamData pool.
	 * @param td - TeamData object to be tested
	 * @return true if the given TeamData is contained in the TeamData pool.
	 */
	public boolean teamPoolContains(TeamData td) {
		return originalList.contains(td);
	}
	
	/**
	 * Adds a Collection of TeamData objects to the TeamData pool.
	 * @param teamDataCollection - Collection of TeamData objects to add
	 * @return true if the TeamData pool was modified.
	 */
	public boolean addAll(Collection<TeamData> teamDataCollection) {
		boolean success = false;
		for( TeamData td : teamDataCollection ) {
			success |= add(td);
		}
		return success;
	}
	
	/**
	 * Adds a TeamData object to the TeamData pool.
	 * @param td - TeamData object to add
	 * @return true if the TeamData pool was modified.
	 */
	public boolean add(TeamData td) {
		if( !originalList.contains(td) ) {
			originalList.add(td);
			return true;
		}
		return false;
	}
	
	/**
	 * Removes a TeamData object from the TeamData pool.
	 * @param td - TeamData object to remove
	 * @return true if the TeamData pool was modified.
	 */
	public boolean remove(TeamData td) {
		if( originalList.contains(td) ) {
			originalList.remove(td);
			return true;
		}
		return false;
	}
	
	/**
	 * Sets the TeamData Pool to a given ArrayList of TeamData objects.
	 * @param teamDataList - ArrayList of TeamData objects to be set to
	 */
	public void set(ArrayList<TeamData> teamDataList) {
		originalList = new ArrayList<TeamData>(teamDataList);
	}
	
	/**
	 * Resets the Sorter and Filter of this Group, reverting the active TeamData List to the current TeamData Pool.
	 */
	public void reset() {
		sorter = new Sorter();
		filter.reset();
	}
	
	/**
	 * Remove all TeamData objects from the TeamData Pool.
	 */
	public void clear() {
		originalList = new ArrayList<TeamData>();
	}
	
	private void update() {
		ArrayList<TeamData> newList = new ArrayList<TeamData>();
		for( TeamData td : originalList ) {
			if(filter.check(td)) {
				newList.add(td);
			}
		}
		TeamData[] newArray = newList.toArray(new TeamData[newList.size()]);
		Arrays.sort(newArray, new TeamDataComparator(sorter));
		currentList = new ArrayList<TeamData>(Arrays.asList(newArray));
	}
	
	@Override
	public String toString() {
		String response = "";
		response += "Name: " + name + "\n";
		response += "Sorter: " + sorter.toString() + "\n";
		response += "Filter: " + filter.toString() + "\n";
		response += "\n";
		response += "Teams which match conditions:\n";
		for( TeamData td : originalList ) {
			if( currentList.contains(td) ) {
				response += "\t" + td.teamNumber + "\n";
			}
		}
		response += "Teams in pool:\n";
		for( TeamData td : originalList ) {
			if( !currentList.contains(td) ) {
				response += "\t" + td.teamNumber + "\n";
			}
		}
		return response;
	}
}
