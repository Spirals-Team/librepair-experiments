package invisibleUniveristy;

import java.util.ArrayList;

import invisibleUniveristy.invention.Invention;

public class InventionFinder{
    ArrayList<Invention> arr;

    public InventionFinder(ArrayList<Invention> arr){
        this.arr = arr;
    }

	public Integer addAllNonLethalInventions() {
        int i = 0;
        for(Invention inv : arr){
            if(!inv.isDeadly()){
                i++;
            }
        }

        return i;
	}
}