package guru.bonacci.spectre.spectreshared.persistence;

import java.io.IOException;

import org.springframework.stereotype.Repository;

@Repository
public interface SpecRepositoryCustom {
	
	void addData(String key, Object nestedObject, Spec spec) throws IOException;
}
