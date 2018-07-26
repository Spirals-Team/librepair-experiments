package com.github.powerlibraries.primitive;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;

public class AbstractLongListTest {

	@Test
	public void randomTest() {
		Random r = new Random(7);
		List<Long> expected = new ArrayList<>();
		SimpleLongList list = new SimpleLongList();
		
		for(int i=0; i<10000; i++) {
			//adding a value
			if(r.nextFloat()<0.7) {
				long v = r.nextLong();
				assertThat(list.add(v))
						.isEqualTo(expected.add(v));
			}
			else {
				if(r.nextBoolean()) {
					long v = r.nextLong();
					assertThat(list.remove((Long)v))
						.isEqualTo(expected.remove((Long)v));
				}
			}
			
			
			assertThat(list.size()).isEqualTo(expected.size());
			assertThat(list.toString()).isEqualTo(expected.toString());
			assertThat(list.toArray()).isEqualTo(expected.toArray());
		}
	}
}