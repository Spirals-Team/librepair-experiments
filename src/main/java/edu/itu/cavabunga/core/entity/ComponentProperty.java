package edu.itu.cavabunga.core.entity;

import javax.persistence.*;
import java.util.List;

@Entity
public class ComponentProperty {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    @JoinColumn(name="component_id")
    private Component component;

    private boolean sharePermission;
    private boolean readPermission;
    private boolean writePermission;
    private boolean freebusyPermission;
    private boolean createPermission;

    @ElementCollection
    private List<Long> shareTo;

    @ElementCollection
    private List<Long> writeTo;

    @ElementCollection
    private List<Long> freebusyTo;

    @ElementCollection
    private List<Long> readTo;

    @ElementCollection
    private List<Long> createTo;

    public ComponentProperty() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Component getComponent() {
        return component;
    }

    public void setComponent(Component component) {
        this.component = component;
    }

    public boolean isSharePermission() {
        return sharePermission;
    }

    public void setSharePermission(boolean sharePermission) {
        this.sharePermission = sharePermission;
    }

    public boolean isReadPermission() {
        return readPermission;
    }

    public void setReadPermission(boolean readPermission) {
        this.readPermission = readPermission;
    }

    public boolean isWritePermission() {
        return writePermission;
    }

    public void setWritePermission(boolean writePermission) {
        this.writePermission = writePermission;
    }

    public boolean isFreebusyPermission() {
        return freebusyPermission;
    }

    public void setFreebusyPermission(boolean freebusyPermission) {
        this.freebusyPermission = freebusyPermission;
    }

    public boolean isCreatePermission() {
        return createPermission;
    }

    public void setCreatePermission(boolean createPermission) {
        this.createPermission = createPermission;
    }

    public List<Long> getShareTo() {
        return shareTo;
    }

    public void setShareTo(List<Long> shareTo) {
        this.shareTo = shareTo;
    }

    public List<Long> getWriteTo() {
        return writeTo;
    }

    public void setWriteTo(List<Long> writeTo) {
        this.writeTo = writeTo;
    }

    public List<Long> getFreebusyTo() {
        return freebusyTo;
    }

    public void setFreebusyTo(List<Long> freebusyTo) {
        this.freebusyTo = freebusyTo;
    }

    public List<Long> getReadTo() {
        return readTo;
    }

    public void setReadTo(List<Long> readTo) {
        this.readTo = readTo;
    }

    public List<Long> getCreateTo() {
        return createTo;
    }

    public void setCreateTo(List<Long> createTo) {
        this.createTo = createTo;
    }
}
