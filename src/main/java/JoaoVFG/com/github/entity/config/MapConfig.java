package JoaoVFG.com.github.entity.config;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@Entity
public class MapConfig implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private String nameKey;
	
	private String value;

	public MapConfig(Integer id, String nameKey, String value) {
		super();
		this.id = id;
		this.nameKey = nameKey;
		this.value = value;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("MapConfig [id=");
		builder.append(id);
		builder.append(", nameKey=");
		builder.append(nameKey);
		builder.append(", valor=");
		builder.append(value);
		builder.append("]");
		return builder.toString();
	}
	
	
	
}
