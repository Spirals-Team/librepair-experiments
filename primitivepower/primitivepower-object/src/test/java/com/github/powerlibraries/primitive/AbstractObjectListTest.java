package com.github.powerlibraries.primitive;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;

public class AbstractObjectListTest<E> {

	@Test
	public void randomTest() {
		Random r = new Random(7);
		List<E> expected = new ArrayList<>();
		SimpleObjectList list = new SimpleObjectList();
		
		for(int i=0; i<10000; i++) {
			//adding a value
			if(r.nextFloat()<0.7) {
				E v = (E)TimeUnit.values()[r.nextInt(100)];
				assertThat(list.add(v))
						.isEqualTo(expected.add(v));
			}
			else {
				if(r.nextBoolean()) {
					E v = (E)TimeUnit.values()[r.nextInt(100)];
					assertThat(list.remove((E)v))
						.isEqualTo(expected.remove((E)v));
				}
			}
			
			
			assertThat(list.size()).isEqualTo(expected.size());
			assertThat(list.toString()).isEqualTo(expected.toString());
			assertThat(list.toArray()).isEqualTo(expected.toArray());
		}
	}
}