package org.rookit.dm.test.mixin;

import org.rookit.api.dm.artist.Artist;
import org.rookit.api.dm.track.Track;
import org.rookit.api.dm.track.TypeTrack;
import org.rookit.api.dm.track.TypeVersion;
import org.rookit.api.dm.track.VersionTrack;
import org.rookit.api.dm.track.factory.TrackFactory;
import org.rookit.api.dm.track.factory.VersionTrackFactory;
import org.rookit.api.dm.track.key.TrackKey;

import java.util.Collection;
import java.util.Optional;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SuppressWarnings("javadoc")
public interface TrackMixin {
    
    default TrackKey createOriginalTrackKey(final String title, final Collection<Artist> mainArtists) {
        final TrackKey mockedKey = mock(TrackKey.class);
        when(mockedKey.type()).thenReturn(TypeTrack.ORIGINAL);
        when(mockedKey.mainArtists()).thenReturn(mainArtists);
        when(mockedKey.title()).thenReturn(title);
        
        return mockedKey;
    }
    
    default TrackKey createVersionTrackKey(final Track original,
            final TypeVersion versionType,
            final Collection<Artist> versionArtists,
            final String versionToken) {
        final TrackKey mockedKey = mock(TrackKey.class);
        when(mockedKey.original()).thenReturn(original);
        when(mockedKey.type()).thenReturn(TypeTrack.VERSION);
        when(mockedKey.getVersionType()).thenReturn(versionType);
        when(mockedKey.getVersionArtists()).thenReturn(versionArtists);
        when(mockedKey.versionToken()).thenReturn(Optional.of(versionToken));
        
        return mockedKey;
    }
    
    default TrackKey createVersionTrackKey(final Track original,
            final TypeVersion versionType,
            final Collection<Artist> versionArtists) {
        final TrackKey mockedKey = mock(TrackKey.class);
        when(mockedKey.original()).thenReturn(original);
        when(mockedKey.type()).thenReturn(TypeTrack.VERSION);
        when(mockedKey.getVersionType()).thenReturn(versionType);
        when(mockedKey.getVersionArtists()).thenReturn(versionArtists);
        
        return mockedKey;
    }
    
    default Track createOriginalTrack(final String title, final Collection<Artist> mainArtists) {
        final TrackKey trackKey = createOriginalTrackKey(title, mainArtists);
        return getTrackFactory().create(trackKey);
    }
    
    default VersionTrack createVersionTrack(final Track original,
            final TypeVersion versionType,
            final Collection<Artist> versionArtists,
            final String versionToken) {
        final TrackKey trackKey = createVersionTrackKey(original, versionType, versionArtists, versionToken);
        return getVersionTrackFactory().create(trackKey);
    }
    
    default VersionTrack createVersionTrack(final Track original,
            final TypeVersion versionType,
            final Collection<Artist> versionArtists) {
        final TrackKey trackKey = createVersionTrackKey(original, versionType, versionArtists);
        return getVersionTrackFactory().create(trackKey);
    }
    
    TrackFactory getTrackFactory();

    VersionTrackFactory getVersionTrackFactory();

}
