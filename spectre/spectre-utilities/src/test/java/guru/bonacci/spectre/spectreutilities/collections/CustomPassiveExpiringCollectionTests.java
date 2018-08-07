package guru.bonacci.spectre.spectreutilities.collections;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Stream.of;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import guru.bonacci.spectre.spectreshared.collections.CustomPassiveExpiringCollection;


@ExtendWith(SpringExtension.class)
public class CustomPassiveExpiringCollectionTests {

	@Test
	public void shouldBeEmpty() {
		CustomPassiveExpiringCollection<String> c = new CustomPassiveExpiringCollection<>();
		assertThat(c.values(), hasSize(0));
	}
	
	@Test
	public void shouldBeEqualToCollectionSize() {
		CustomPassiveExpiringCollection<String> c = new CustomPassiveExpiringCollection<>();
		assertThat(c.size(), is(equalTo(c.values().size())));
		c.add("aa");
		assertThat(c.size(), is(equalTo(c.values().size())));
		c.add("bb");
		assertThat(c.size(), is(equalTo(c.values().size())));
		c.addAll(of("dd","ee").collect(toList()));
		assertThat(c.size(), is(equalTo(c.values().size())));
	}
	
	@Test
	public void shouldEmptyCollection() {
		CustomPassiveExpiringCollection<String> c = new CustomPassiveExpiringCollection<>();
		c.addAll(of("dd","ee").collect(toList()));
		c.clear();
		assertThat(c.size(), is(equalTo(0)));
	}

	@Test
	public void shouldReturnTrueWhenEmpty() {
		CustomPassiveExpiringCollection<String> c = new CustomPassiveExpiringCollection<>();
		c.addAll(of("dd","ee").collect(toList()));
		assertThat(c.isEmpty(), is(false));
		c.clear();
		assertThat(c.isEmpty(), is(true));
	}

	@Test
	public void shouldContainElement() {
		CustomPassiveExpiringCollection<String> c = new CustomPassiveExpiringCollection<>();
		String el1 = "abc", el2 = "def";
		c.add(el1);
		assertThat(c.contains(el1), is(true));
		assertThat(c.contains(el2), is(false));
	}

	// now the more interesting test cases
	@Test
	public void shouldRemoveElementAfterExpiring() throws InterruptedException {
		CustomPassiveExpiringCollection<String> c = new CustomPassiveExpiringCollection<>(100);
		String e = "abc";
		c.add(e);
		assertThat(c.contains(e), is(true));
		assertThat(c.isEmpty(), is(false));
		Thread.sleep(100); //little nap
		assertThat(c.contains(e), is(false));
		assertThat(c.isEmpty(), is(true));
	}

	@Test
	public void shouldRemoveElementsAfterExpiring() throws InterruptedException {
		CustomPassiveExpiringCollection<String> c = new CustomPassiveExpiringCollection<>(1000);
		c.addAll(of("aa","bb").collect(toList()));
		assertThat(c.size(), is(equalTo(2)));
		Thread.sleep(600); //little nap
		c.add("cc");
		assertThat(c.size(), is(equalTo(3)));
		Thread.sleep(600); //little nap
		assertThat(c.size(), is(equalTo(1)));
		Thread.sleep(600); //little nap
		assertThat(c.size(), is(equalTo(0)));
	}
}

