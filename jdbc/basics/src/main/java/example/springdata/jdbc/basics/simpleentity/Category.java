/*
 * Copyright 2017-2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package example.springdata.jdbc.basics.simpleentity;

import example.springdata.jdbc.basics.aggregate.AgeGroup;
import example.springdata.jdbc.basics.aggregate.LegoSet;
import lombok.Data;
import lombok.Setter;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;

/**
 * Coarse classification for {@link LegoSet}s, like "Car", "Plane", "Building" and so on.
 *
 * @author Jens Schauder
 */
@Data
public class Category {

	private final @Id Long id;
	private String name, description;
	private LocalDateTime created = LocalDateTime.now();
	private @Setter long inserted;
	private AgeGroup ageGroup;

	public Category(String name, String description, AgeGroup ageGroup) {

		this.id = null;
		this.name = name;
		this.description = description;
		this.ageGroup = ageGroup;
	}

	public void timeStamp() {

		if (inserted == 0) {
			inserted = System.currentTimeMillis();
		}
	}
}
