package Model;

import java.io.IOException;

import org.json.JSONException;

public class Translate {
	public static String Translate(String s) throws IOException, JSONException {
		if (s.equals("")) {
			return "";
		}
		return GoogleTranslate.translate("vi", s);
	}
}
