package ru.job4j.model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author Hincu Andrei (andreih1981@gmail.com)on 18.03.2018.
 * @version $Id$.
 * @since 0.1.
 */
@MappedSuperclass
public abstract class Persistent implements Serializable {

     @Id @GeneratedValue(strategy = GenerationType.AUTO)
     @Column(name = "id", updatable = false, nullable = false)
     private Long id;
     private static final long serialVersionUID = 5569468962915075760L;

     public Persistent(Long id) {
          this.id = id;
     }

     public Persistent() {
     }

     public Long getId() {
          return id;
     }

     public void setId(Long id) {
          this.id = id;
     }
}
