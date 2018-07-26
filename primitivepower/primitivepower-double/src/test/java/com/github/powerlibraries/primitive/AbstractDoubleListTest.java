package com.github.powerlibraries.primitive;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;

public class AbstractDoubleListTest {

	@Test
	public void randomTest() {
		Random r = new Random(7);
		List<Double> expected = new ArrayList<>();
		SimpleDoubleList list = new SimpleDoubleList();
		
		for(int i=0; i<10000; i++) {
			//adding a value
			if(r.nextFloat()<0.7) {
				double v = r.nextDouble();
				assertThat(list.add(v))
						.isEqualTo(expected.add(v));
			}
			else {
				if(r.nextBoolean()) {
					double v = r.nextDouble();
					assertThat(list.remove((Double)v))
						.isEqualTo(expected.remove((Double)v));
				}
			}
			
			
			assertThat(list.size()).isEqualTo(expected.size());
			assertThat(list.toString()).isEqualTo(expected.toString());
			assertThat(list.toArray()).isEqualTo(expected.toArray());
		}
	}
}