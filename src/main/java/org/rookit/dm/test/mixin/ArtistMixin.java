package org.rookit.dm.test.mixin;

import org.rookit.api.dm.artist.GroupArtist;
import org.rookit.api.dm.artist.TypeArtist;
import org.rookit.api.dm.artist.factory.GroupArtistFactory;
import org.rookit.api.dm.artist.key.ArtistKey;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SuppressWarnings("javadoc")
public interface ArtistMixin {
    
    default ArtistKey createGroupArtistKey(final String groupName) {
        final ArtistKey mockedKey = mock(ArtistKey.class);
        when(mockedKey.type()).thenReturn(TypeArtist.GROUP);
        when(mockedKey.name()).thenReturn(groupName);
        
        return mockedKey;
    }
    
    default GroupArtist createGroupArtist(final String groupName) {
        final ArtistKey artistKey = createGroupArtistKey(groupName);
        
        return getGroupArtistFactory().create(artistKey);
    }
    
    GroupArtistFactory getGroupArtistFactory();

}
