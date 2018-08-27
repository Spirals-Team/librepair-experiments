package JoaoVFG.com.github.service.route;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Distancia {
	private String cepOrigem;
	private String cepDestino;
	private int distanciaInMeters;
	private int timeInSeconds;
	
	
	public Distancia(String cepOrigem, String cepDestino, int distancia, int timeInSeconds) {
		super();
		this.cepOrigem = cepOrigem;
		this.cepDestino = cepDestino;
		this.distanciaInMeters = distancia;
		this.timeInSeconds = timeInSeconds;
	}
	
	
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Distancia [cepOrigem=");
		builder.append(cepOrigem);
		builder.append(", cepDestino=");
		builder.append(cepDestino);
		builder.append(", distanciaInMeters=");
		builder.append(distanciaInMeters);
		builder.append(", timeInSeconds=");
		builder.append(timeInSeconds);
		builder.append("]");
		return builder.toString();
	}
	
	

}
