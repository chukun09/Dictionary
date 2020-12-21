package TTS;

import javax.speech.Central;
import javax.speech.synthesis.SynthesizerModeDesc;
import javax.speech.synthesis.Synthesizer;
import java.util.Locale;

class TexttoSpeech {
	public void Texttospeech(String s) {
		try {
			// Set property as Kevin Dictionary
			System.setProperty("freetts.voices", "com.sun.speech.freetts.en.us" + ".cmu_us_kal.KevinVoiceDirectory");
			Central.registerEngineCentral("com.sun.speech.freetts" + ".jsapi.FreeTTSEngineCentral");
			Synthesizer synthesizer = Central.createSynthesizer(new SynthesizerModeDesc(Locale.ENGLISH));
			synthesizer.allocate();
			synthesizer.resume();
			synthesizer.speakPlainText(s, null);
			synthesizer.waitEngineState(Synthesizer.QUEUE_EMPTY);
			synthesizer.deallocate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		TexttoSpeech a = new TexttoSpeech();
		a.Texttospeech("Hello");
	}
}