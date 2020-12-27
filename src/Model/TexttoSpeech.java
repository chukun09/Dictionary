package Model;

import javax.speech.Central;
import javax.speech.synthesis.SynthesizerModeDesc;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.advanced.AdvancedPlayer;

import javax.speech.synthesis.Synthesizer;

import java.io.IOException;
import java.util.Locale;

public class TexttoSpeech {
	public void ManVoice(String s) {
		try {
			System.setProperty("freetts.voices", "com.sun.speech.freetts.en.us" + ".cmu_us_kal.KevinVoiceDirectory");
			Central.registerEngineCentral("com.sun.speech.freetts" + ".jsapi.FreeTTSEngineCentral");
			Synthesizer synthesizer = Central.createSynthesizer(new SynthesizerModeDesc(Locale.ENGLISH));
			synthesizer.allocate();
			synthesizer.resume();
			synthesizer.speakPlainText(s, null);
			synthesizer.waitEngineState(Synthesizer.QUEUE_EMPTY);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	SynthesiserV2 synthesizer = new SynthesiserV2("AIzaSyBOti4mM-6x9WDnZIjIeyEU21OpBXqWBgw");

	public void WomenVoice(String text) {
		Thread thread = new Thread(() -> {
			try {
				AdvancedPlayer player = new AdvancedPlayer(synthesizer.getMP3Data(text));
				player.play();
			} catch (IOException | JavaLayerException e) {

				e.printStackTrace();
			}
		});
		thread.setDaemon(false);
		thread.start();
	}
}