package VoiceRegonizer;
import edu.cmu.sphinx.api.AbstractSpeechRecognizer;
import edu.cmu.sphinx.api.Configuration;
import edu.cmu.sphinx.frontend.util.StreamDataSource;
import java.io.IOException;

public class RegconizerExtention extends AbstractSpeechRecognizer {
    private final MicroPhoneExtention microphone;

    public RegconizerExtention(Configuration configuration) throws IOException {
        super(configuration);
        microphone = new  MicroPhoneExtention(16000, 16, true, false);
        this.context.getInstance(StreamDataSource.class).setInputStream(this.microphone.getStream());
    }

    public void startRecognition(boolean clear) {
        this.recognizer.allocate();
        this.microphone.startRecording();
    }

    public void stopRecognition() {
        this.microphone.stopRecording();
        this.recognizer.deallocate();
    }

    public void closeRecognitionLine(){
        microphone.closeLine();
    }
}