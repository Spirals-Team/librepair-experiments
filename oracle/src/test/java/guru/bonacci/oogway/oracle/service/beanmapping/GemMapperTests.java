package guru.bonacci.oogway.oracle.service.beanmapping;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.Test;

import guru.bonacci.oogway.oracle.service.persistence.Gem;
import guru.bonacci.oogway.shareddomain.GemCarrier;

public class GemMapperTests {

    @Test
    public void shouldConvertToGem() {
        GemCarrier carrier = new GemCarrier("Merry", "Christmas");
        Gem gem = GemMapper.MAPPER.toGem(carrier);

        assertThat(gem.getSaying()).isEqualTo("Merry");
        assertThat(gem.getAuthor()).isEqualTo("Christmas");
    }

    @Test
    public void shouldConvertToGemCarrier() {
        Gem gem = new Gem("Holy", "Maria");
        GemCarrier carrier = GemMapper.MAPPER.fromGem(gem);

        assertThat(carrier.getSaying()).isEqualTo("Holy");
        assertThat(carrier.getAuthor()).isEqualTo("Maria");
    }
    
    @Test
    public void shouldConvertToGemCarrierFromOptional() {
        Optional<Gem> gem = Optional.of(new Gem("Holy", "Maria"));
		GemCarrier carrier = gem.map(GemMapper.MAPPER::fromGem).orElse(null);

        assertThat(carrier.getSaying()).isEqualTo("Holy");
        assertThat(carrier.getAuthor()).isEqualTo("Maria");
    }
}
