package com.d4dl.hellofib.greeting;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * A hello world entity that has attributes for the name of the world and the name
 * of the greeter.  Greetings are stored so a greeting from one greeter to some world can be tracked.
 */
@Entity
public class Greeting {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    public String worldName = "Earth";
    public String greeterName;

    /**
     * Default constructor
     */
    public Greeting() {
    }

    /**
     * Convenience constructor for a Greeting.
     * @param greeterName the name of the greeter sennding the greeting.
     * @param worldName the name of the world that received the greeting.
     */
    public Greeting(String greeterName, String worldName) {
        this.greeterName = greeterName;
        this.worldName = worldName;
    }

    /**
     * Retrieve the unique database id of this entity.
     * @return an id used for unique retrieval of this entity.
     */
    public Long getId() {
        return id;
    }

    /**
     * Set the unique database id of this entity. To be used for serialization. Otherwise it should be considered
     * immutable
     * @param id the id of the actual entity being deserialized
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * The name of the world the greeter greeted.
     * @return any world name, imagined or real that was greeted.
     */
    public String getWorldName() {
        return worldName;
    }

    /**
     *
     * The name of the world the greeter greeted.
     * @param worldName any world name, imagined or real that was greeted.
     */
    public void setWorldName(String worldName) {
        this.worldName = worldName;
    }

    /**
     * The name of the greeter that greeted the world
     * @return the name of the greeter at the time this greeting occurred.
     */
    public String getGreeterName() {
        return greeterName;
    }

    /**
     * The name of the greeter that greeted the world
     * @param greeterName the name of the greeter that should be used to identify the person that greeted the world
     */
    public void setGreeterName(String greeterName) {
        this.greeterName = greeterName;
    }
}
