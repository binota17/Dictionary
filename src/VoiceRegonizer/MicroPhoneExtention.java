package VoiceRegonizer;

import javax.sound.sampled.*;
import java.io.InputStream;

/**
 * extention cho micro của sphinx.
 */
public class MicroPhoneExtention {
    private final TargetDataLine line;
    private final InputStream inputStream;

    public MicroPhoneExtention(float sampleRate, int sampleSize, boolean signed, boolean bigEndian) {
        AudioFormat format = new AudioFormat(sampleRate, sampleSize, 1, signed, bigEndian);

        try {
            this.line = AudioSystem.getTargetDataLine(format);
            this.line.open();
        } catch (LineUnavailableException var7) {
            throw new IllegalStateException(var7);
        }

        this.inputStream = new AudioInputStream(this.line);
    }

    public void startRecording() {
        this.line.start();
    }

    public void stopRecording() {
        this.line.stop();
    }

    public InputStream getStream() {
        return this.inputStream;
    }

    public void closeLine(){
        line.close();
    }
}
