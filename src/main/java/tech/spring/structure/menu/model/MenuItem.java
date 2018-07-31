package tech.spring.structure.menu.model;

import javax.persistence.Column;
import javax.persistence.Entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import tech.spring.structure.model.StructureEntity;
import tech.spring.structure.scaffold.ScaffoldAuthorize;
import tech.spring.structure.scaffold.ScaffoldIgnore;
import tech.spring.structure.scaffold.ScaffoldProperty;
import tech.spring.structure.scaffold.Scaffolding;

@Entity
@Scaffolding
@ScaffoldAuthorize("isAuthenticated()")
public class MenuItem extends StructureEntity {

    @ScaffoldIgnore
    private static final long serialVersionUID = 3455305409927450355L;

    @ScaffoldProperty
    @Column(nullable = false)
    private String gloss;

    @ScaffoldProperty
    @Column(nullable = false)
    private String path;

    @ScaffoldProperty
    @JsonInclude(Include.NON_EMPTY)
    @Column(nullable = true)
    private String icon;

    @ScaffoldProperty
    @JsonInclude(Include.NON_EMPTY)
    @Column(nullable = true)
    private String help;

    public MenuItem() {
        super();
    }

    public MenuItem(String gloss, String path) {
        this();
        this.gloss = gloss;
        this.path = path;
    }

    public String getGloss() {
        return gloss;
    }

    public void setGloss(String gloss) {
        this.gloss = gloss;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getHelp() {
        return help;
    }

    public void setHelp(String help) {
        this.help = help;
    }

}
