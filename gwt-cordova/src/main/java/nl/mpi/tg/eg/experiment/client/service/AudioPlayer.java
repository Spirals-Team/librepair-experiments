/*
 * Copyright (C) 2015 Language In Interaction
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package nl.mpi.tg.eg.experiment.client.service;

import com.google.gwt.dom.client.AudioElement;
import com.google.gwt.media.client.Audio;
import com.google.gwt.safehtml.shared.SafeUri;
import nl.mpi.tg.eg.experiment.client.exception.AudioException;
import nl.mpi.tg.eg.experiment.client.listener.AudioEventListner;
import nl.mpi.tg.eg.experiment.client.listener.AudioExceptionListner;

/**
 * @since Jan 6, 2015 10:27:57 AM (creation date)
 * @author Peter Withers <p.withers@psych.ru.nl>
 */
public class AudioPlayer {

    private Audio audioPlayer;
    private AudioEventListner audioEventListner = null;
    final private AudioExceptionListner audioExceptionListner;

    public AudioPlayer(AudioExceptionListner audioExceptionListner) throws AudioException {
        this.audioExceptionListner = audioExceptionListner;
    }

    public Audio getAudioPlayer() {
        audioPlayer.setVisible(true);
        return audioPlayer;
    }

    private void createPlayer() throws AudioException {
        audioPlayer = Audio.createIfSupported();
        if (audioPlayer == null) {
            throw new AudioException("audio not supportered");
        }
        final AudioElement audioElement = audioPlayer.getAudioElement();
        onEndedSetup(audioElement);
    }

    private native void onEndedSetup(final AudioElement audioElement) /*-{
     var audioPlayer = this;
     audioElement.addEventListener("ended", function(){
     audioPlayer.@nl.mpi.tg.eg.experiment.client.service.AudioPlayer::onEndedAction()();
     }, false);
     audioElement.addEventListener("canplaythrough", function(){
     audioPlayer.@nl.mpi.tg.eg.experiment.client.service.AudioPlayer::onLoadedAction()();
     }, false);
     audioElement.addEventListener("error", function(){
     audioPlayer.@nl.mpi.tg.eg.experiment.client.service.AudioPlayer::onAudioFailed()();
     }, false);
     }-*/;

    public void onEndedAction() {
        if (audioEventListner != null) {
            audioEventListner.audioEnded();
        }
    }

    public void onAudioFailed() {
        if (audioEventListner != null) {
            audioEventListner.audioFailed();
        }
    }

    public void onLoadedAction() {
        if (audioEventListner != null) {
            audioEventListner.audioLoaded();
            audioPlayer.play();
        }
    }

    public void setOnEndedListener(AudioEventListner audioEventListner) {
        this.audioEventListner = audioEventListner;
    }

//    public void playSampleAudio(RoundSample roundSample) {
//        final String[] soundFiles = roundSample.getLanguageSample().getSoundFiles();
//        playSample(soundFiles[roundSample.getSampleIndex()]);
//    }
    @Deprecated
    private void playSample(String sample) {
        if (audioPlayer == null) {
            try {
                createPlayer();
            } catch (AudioException audioException) {
                audioExceptionListner.audioExceptionFired(audioException);
                return;
            }
        }
        audioPlayer.setSrc(sample);
        //audioPlayer.setCurrentTime(0); // on android the if the ready state is not correct then this will fail and audio will not play
        audioPlayer.play();
    }

    public void playSample(SafeUri ogg, SafeUri mp3) {
        if (audioPlayer == null) {
            try {
                createPlayer();
            } catch (AudioException audioException) {
                audioExceptionListner.audioExceptionFired(audioException);
                return;
            }
        }
        if (ogg != null) {
            audioPlayer.addSource(ogg.asString(), AudioElement.TYPE_OGG);
        }
        if (mp3 != null) {
            audioPlayer.addSource(mp3.asString(), AudioElement.TYPE_MP3);
        }
        //audioPlayer.setCurrentTime(0); // on android the if the ready state is not correct then this will fail and audio will not play
        audioPlayer.load();
    }

    public double getCurrentTime() {
        return audioPlayer.getCurrentTime();
    }

    public void stopAll() {
        if (audioPlayer != null) {
            audioPlayer.pause();
//            audioPlayer.setSrc("");
            audioPlayer.removeFromParent();
            audioPlayer = null;
        }
        //onEndedAction();
        audioEventListner = null;
    }
}
