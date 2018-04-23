package ru.aserdyuchenko.tracker.models;

/**
* Каждая заявка имеет: номер, имя, описание, дата создания.
*
*/
public class Item {
    protected String id;

    protected String name;

    protected String description;

    protected long create;

	public Item() {
	}

	public Item(String name, String description, long create) {
		this.name = name;
		this.description = description;
		this.create = create;
	}

	public String getName() {
		return this.name;
	}

	public String getDescription(){
		return this.description;
	}

	public void setDescription(String description){
		this.description = description;
	}

	public long getCreate(){
		return this.create;
	}

	public String getId(){
		return this.id;
	}

	public void setId(String id){
		this.id = id;
	}

    @Override
    public String toString() {
        return "Item{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", create=" + create +
                '}';
    }
}
