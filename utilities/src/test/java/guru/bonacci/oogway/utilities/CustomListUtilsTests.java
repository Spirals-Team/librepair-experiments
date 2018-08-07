package guru.bonacci.oogway.utilities;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.not;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public class CustomListUtilsTests {

	@Test
	public void shouldReturnDifferentElements() {
		String s1 = "this is one string";
		String s2 = "this is another string";
		String s3 = "and this string is not even needed for this test";

		Set<String> results = new HashSet<>();
		for (int i=0; i<10; i++) 
			results.add(CustomListUtils.random(asList(s1, s2, s3)).get());

		assertThat(results.size(), greaterThan(1));
	}
	
	@Test
	public void shouldReturnNoElementForEmptyCollection() {
		List<String> input = emptyList();
		Optional<String> result = CustomListUtils.random(input);
		assertThat(true, is(not(result.isPresent())));
	}

	@Test
	public void shouldReturnNoElementForNull() {
		Optional<String> result = CustomListUtils.random(null);
		assertThat(true, is(not(result.isPresent())));
	}
}

