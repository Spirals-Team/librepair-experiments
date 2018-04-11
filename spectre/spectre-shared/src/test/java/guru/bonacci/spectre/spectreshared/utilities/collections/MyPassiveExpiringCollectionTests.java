package guru.bonacci.spectre.spectreshared.utilities.collections;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Stream.of;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import guru.bonacci.spectre.spectreshared.utilities.collections.MyPassiveExpiringCollection;


@RunWith(SpringRunner.class)
public class MyPassiveExpiringCollectionTests {

	@Test
	public void shouldBeEmpty() {
		MyPassiveExpiringCollection<String> c = new MyPassiveExpiringCollection<>();
		assertThat(c.values(), hasSize(0));
	}
	
	@Test
	public void shouldBeEqualToCollectionSize() {
		MyPassiveExpiringCollection<String> c = new MyPassiveExpiringCollection<>();
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
		MyPassiveExpiringCollection<String> c = new MyPassiveExpiringCollection<>();
		c.addAll(of("dd","ee").collect(toList()));
		c.clear();
		assertThat(c.size(), is(equalTo(0)));
	}

	@Test
	public void shouldReturnTrueWhenEmpty() {
		MyPassiveExpiringCollection<String> c = new MyPassiveExpiringCollection<>();
		c.addAll(of("dd","ee").collect(toList()));
		assertThat(c.isEmpty(), is(false));
		c.clear();
		assertThat(c.isEmpty(), is(true));
	}

	@Test
	public void shouldContainElement() {
		MyPassiveExpiringCollection<String> c = new MyPassiveExpiringCollection<>();
		String el1 = "abc", el2 = "def";
		c.add(el1);
		assertThat(c.contains(el1), is(true));
		assertThat(c.contains(el2), is(false));
	}

	// now the more interesting test cases
	@Test
	public void shouldRemoveElementAfterExpiring() throws InterruptedException {
		MyPassiveExpiringCollection<String> c = new MyPassiveExpiringCollection<>(100);
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
		MyPassiveExpiringCollection<String> c = new MyPassiveExpiringCollection<>(1000);
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

