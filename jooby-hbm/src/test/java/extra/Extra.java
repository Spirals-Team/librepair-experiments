package extra;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
// @EntityListeners(BeerListener.class)
public class Extra {
  @Id
  @GeneratedValue
  public Long id;

  public String name;
}
