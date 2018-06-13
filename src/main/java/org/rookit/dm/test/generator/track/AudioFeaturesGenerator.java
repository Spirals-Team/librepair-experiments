package org.rookit.dm.test.generator.track;

import static org.rookit.test.utils.GeneratorUtils.randomlyConsume;

import com.google.inject.Inject;

import org.rookit.api.dm.factory.RookitFactory;
import org.rookit.api.dm.key.Key;
import org.rookit.api.dm.track.audio.AudioFeature;
import org.rookit.api.dm.track.audio.AudioFeatureFactory;
import org.rookit.test.generator.AbstractGenerator;
import org.rookit.test.generator.Generator;

class AudioFeaturesGenerator extends AbstractGenerator<AudioFeature> {

    private final AudioFeatureFactory audioFeaturesFactory;
    private final Generator<Boolean> booleanGenerator;
    private final Generator<Short> shortGenerator;
    private final Generator<Double> doubleGenerator;
    private final Generator<com.kekstudio.musictheory.Key> keyGenerator;

    @Inject
    private AudioFeaturesGenerator(AudioFeatureFactory audioFeaturesFactory,
            Generator<Boolean> booleanGenerator,
            Generator<Short> shortGenerator,
            Generator<Double> doubleGenerator,
            Generator<com.kekstudio.musictheory.Key> keyGenerator) {
        this.audioFeaturesFactory = audioFeaturesFactory;
        this.booleanGenerator = booleanGenerator;
        this.shortGenerator = shortGenerator;
        this.doubleGenerator = doubleGenerator;
        this.keyGenerator = keyGenerator;
    }



    @Override
    public AudioFeature createRandom() {
        final AudioFeature audioFeature = this.audioFeaturesFactory.createEmpty();

        randomlyConsume(audioFeature::setAcoustic, this.booleanGenerator);
        randomlyConsume(audioFeature::setInstrumental, this.booleanGenerator);
        randomlyConsume(audioFeature::setLive, this.booleanGenerator);
        randomlyConsume(audioFeature::setBpm, this.shortGenerator);
        randomlyConsume(audioFeature::setDanceability, this.doubleGenerator);
        randomlyConsume(audioFeature::setEnergy, this.doubleGenerator);
        randomlyConsume(audioFeature::setValence, this.doubleGenerator);
        randomlyConsume(audioFeature::setTrackKey, this.keyGenerator);
        
        return audioFeature;
    }

}
