/*******************************************************************************
 * Copyright (C) 2017 Joao Sousa
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 ******************************************************************************/

package org.rookit.dm.test;

import com.google.common.base.MoreObjects;
import com.google.inject.Inject;
import com.google.inject.Module;
import com.google.inject.util.Modules;
import org.rookit.api.dm.album.Album;
import org.rookit.api.dm.album.TypeRelease;
import org.rookit.api.dm.artist.Artist;
import org.rookit.api.dm.artist.GroupArtist;
import org.rookit.api.dm.artist.Musician;
import org.rookit.api.dm.genre.Genre;
import org.rookit.api.dm.play.Playlist;
import org.rookit.api.dm.track.Track;
import org.rookit.api.dm.track.TypeTrack;
import org.rookit.api.dm.track.TypeVersion;
import org.rookit.api.dm.track.VersionTrack;
import org.rookit.api.dm.track.title.Title;
import org.rookit.dm.test.generator.RookitGeneratorModule;
import org.rookit.dm.test.generator.album.AlbumGeneratorModule;
import org.rookit.dm.test.generator.artist.ArtistGeneratorModule;
import org.rookit.dm.test.generator.genre.GenreGeneratorModule;
import org.rookit.dm.test.generator.play.PlaylistGeneratorModule;
import org.rookit.dm.test.generator.track.TrackGeneratorModule;
import org.rookit.test.generator.BaseGeneratorModule;
import org.rookit.test.generator.Generator;

import javax.annotation.Generated;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

@SuppressWarnings("javadoc")
public final class DataModelTestFactory {

    public static final Path TEST_RESOURCE = Paths.get("testStore");
    public static final Path TRACK_RESOURCE = TEST_RESOURCE.resolve("tracks").resolve("unparsed");
    public static final Path FORMATS = TRACK_RESOURCE.getParent().resolve("testFormats");

    public static final Module MODULE = Modules.combine(
            new TrackGeneratorModule(),
            new ArtistGeneratorModule(),
            new GenreGeneratorModule(),
            new PlaylistGeneratorModule(),
            new AlbumGeneratorModule(),
            new RookitGeneratorModule(),
            new BaseGeneratorModule());

    public static final Module getModule() {
        return MODULE;
    }

    private final Generator<Track> originalTracks;
    private final Generator<VersionTrack> versionTracks;
    private final Generator<TypeVersion> typeVersions;
    private final Generator<TypeTrack> typeTracks;
    private final Generator<Title> trackTitles;

    private final Generator<Genre> genres;
    private final Generator<GroupArtist> groupArtists;
    private final Generator<Musician> musicians;
    private final Generator<Artist> artists;
    private final Generator<Album> albums;
    private final Generator<Playlist> playlists;
    private final Generator<TypeRelease> releaseTypes;

    @Inject
    private DataModelTestFactory(final Generator<VersionTrack> versionTracks,
            final Generator<Track> originalTracks,
            final Generator<TypeVersion> typeVersions,
            final Generator<TypeTrack> typeTracks,
            final Generator<Genre> genres,
            final Generator<GroupArtist> groupArtists,
            final Generator<Musician> musicians,
            final Generator<Artist> artists,
            final Generator<Album> albums,
            final Generator<Playlist> playlists,
            final Generator<Title> trackTitles,
            final Generator<TypeRelease> releaseTypes) {
        this.releaseTypes = releaseTypes;
        this.genres = genres;
        this.versionTracks = versionTracks;
        this.originalTracks = originalTracks;
        this.typeVersions = typeVersions;
        this.typeTracks = typeTracks;
        this.groupArtists = groupArtists;
        this.artists = artists;
        this.albums = albums;
        this.playlists = playlists;
        this.trackTitles = trackTitles;
        this.musicians = musicians;
    }

    public Generator<Album> albums() {
        return this.albums;
    }

    public Generator<Artist> artists() {
        return this.artists;
    }

    @Override
    @Generated(value = "GuavaEclipsePlugin")
    public boolean equals(final Object object) {
        if (object instanceof DataModelTestFactory) {
            final DataModelTestFactory that = (DataModelTestFactory) object;
            return Objects.equals(this.originalTracks, that.originalTracks)
                    && Objects.equals(this.versionTracks, that.versionTracks)
                    && Objects.equals(this.typeVersions, that.typeVersions)
                    && Objects.equals(this.typeTracks, that.typeTracks)
                    && Objects.equals(this.trackTitles, that.trackTitles)
                    && Objects.equals(this.genres, that.genres)
                    && Objects.equals(this.groupArtists, that.groupArtists)
                    && Objects.equals(this.musicians, that.musicians)
                    && Objects.equals(this.artists, that.artists)
                    && Objects.equals(this.albums, that.albums)
                    && Objects.equals(this.playlists, that.playlists)
                    && Objects.equals(this.releaseTypes, that.releaseTypes);
        }
        return false;
    }

    public Generator<Genre> genres() {
        return this.genres;
    }

    public Generator<GroupArtist> groupArtists() {
        return this.groupArtists;
    }

    @Override
    @Generated(value = "GuavaEclipsePlugin")
    public int hashCode() {
        return Objects.hash(this.originalTracks, this.versionTracks, this.typeVersions,
                this.typeTracks, this.trackTitles,
                this.genres, this.groupArtists, this.musicians, this.artists, this.albums, this.playlists,
                this.releaseTypes);
    }

    public Generator<Musician> musicians() {
        return this.musicians;
    }

    public Generator<Track> originalTracks() {
        return this.originalTracks;
    }

    public Generator<Playlist> playlists() {
        return this.playlists;
    }

    public Generator<TypeRelease> releaseTypes() {
        return this.releaseTypes;
    }

    @Override
    @Generated(value = "GuavaEclipsePlugin")
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("super", super.toString())
                .add("originalTracks", this.originalTracks)
                .add("versionTracks", this.versionTracks)
                .add("typeVersions", this.typeVersions)
                .add("typeTracks", this.typeTracks)
                .add("trackTitles", this.trackTitles)
                .add("genres", this.genres)
                .add("groupArtists", this.groupArtists)
                .add("musicians", this.musicians)
                .add("artists", this.artists)
                .add("albums", this.albums)
                .add("playlists", this.playlists)
                .add("releaseTypes", this.releaseTypes)
                .toString();
    }

    public Generator<Title> trackTitles() {
        return this.trackTitles;
    }

    public Generator<TypeTrack> typeTracks() {
        return this.typeTracks;
    }

    public Generator<TypeVersion> typeVersions() {
        return this.typeVersions;
    }

    public Generator<VersionTrack> versionTracks() {
        return this.versionTracks;
    }

}
