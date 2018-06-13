
package org.rookit.dm.test.generator.track;

import com.google.inject.Inject;
import com.kekstudio.musictheory.Key;
import com.kekstudio.musictheory.Music;
import com.kekstudio.musictheory.Note;

import java.util.Random;

import org.rookit.test.generator.AbstractGenerator;

class KeyGenerator extends AbstractGenerator<Key> {

    private static final String[] NOTES = Music.Alphabet;
    private static final String[] MODES = new String[]{"major", "minor"};

    private final Random random;

    @Inject
    private KeyGenerator(final Random random) {
        super();
        this.random = random;
    }

    @Override
    public Key createRandom() {
        final String mode = MODES[this.random.nextInt(MODES.length)];
        final Note note = new Note(NOTES[this.random.nextInt(NOTES.length)]);

        return new Key(note, mode);
    }

}
