package edu.itu.cavabunga.core.entity.component;

import edu.itu.cavabunga.core.entity.Component;

import javax.persistence.Entity;

@Entity
public class Timezone extends Component {
    @Override
    public boolean validate(){
        return true;
    }
}
