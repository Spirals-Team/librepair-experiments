package lelental.domain;

import lelental.domain.Cd;

import java.util.List;

/**
 * @author Paweł Lelental
 **/
public class Shelf {

    private List<Cd> cdList;

    public Shelf(List<Cd> cdList) {
        this.cdList = cdList;
    }

    public Shelf() {
    }

    public List<Cd> getCdList() {
        return cdList;
    }

    public void setCdList(List<Cd> cdList) {
        this.cdList = cdList;
    }


}
