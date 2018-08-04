package ru.job4j.model.repository.queries;

import ru.job4j.model.pojo.MusicType;

/**
 * @author Yury Matskevich
 */
public class UsersByMusicTypeSqlSpecification implements SqlSpecification {
	private MusicType music;

	public UsersByMusicTypeSqlSpecification(MusicType music) {
		this.music = music;
	}

	@Override
	public String toSqlQuery() {
		return String.format(
				"WHERE '%s' = ANY(music_u)",
				music
		);
	}
}
