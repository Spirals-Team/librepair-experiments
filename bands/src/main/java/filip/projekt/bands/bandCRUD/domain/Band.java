package filip.projekt.bands.bandCRUD.domain;

public class Band {
  private int Id;
  private String Name;
  private String Genre;
  private  int NumberOfMembers;


  public String play(){
    return Name + "koncert został zagrany w" + NumberOfMembers + "osób";
  }

  public int addMember(int i){
    //setNumberOfMembers(getNumberOfMembers()+i);
  return   NumberOfMembers +=i;
  }

  public int removeMember(int i){
    //setNumberOfMembers(getNumberOfMembers()-i);
    return NumberOfMembers-=i;
  }





	/**
	* Returns value of Name
	* @return
	*/
  public int getId(){
    return this.Id;
  }


	public String getName() {
		return this.Name;
	}

	/**
	* Returns value of Genre
	* @return
	*/
	public String getGenre() {
		return this.Genre;
	}

	/**
	* Returns value of NumberOfMembers
	* @return
	*/
	public int getNumberOfMembers() {
		return this.NumberOfMembers;
	}

	/**
	* Sets new value of Name
	* @param
	*/
  public void setId(int Id){
    this.Id = Id;
  }


	public void setName(String Name) {
		this.Name = Name;
	}

	/**
	* Sets new value of Genre
	* @param
	*/
	public void setGenre(String Genre) {
		this.Genre = Genre;
	}

	/**
	* Sets new value of NumberOfMembers
	* @param
	*/
	public void setNumberOfMembers(int NumberOfMembers) {
		this.NumberOfMembers = NumberOfMembers;
	}

	/**
	* Default empty Band constructor
	*/
	public Band() {
		super();
	}

	/**
	* Default Band constructor
	*/
	public Band(String Name, String Genre, int NumberOfMembers) {
		super();
    
		this.Name = Name;
		this.Genre = Genre;
		this.NumberOfMembers = NumberOfMembers;
	}

	public String toString(){
		return "nazwa zespołu: " + this.Name + "\n" +
		"gatunek muzyczny: " + this.Genre + "\n" +
		"ilość członków: " + this.NumberOfMembers + "\n" + "\n";
	}
	@Override
	public boolean equals(Object b){
		return this.toString().equals(b.toString());

	}








}
