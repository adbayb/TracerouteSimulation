package controller;

import java.io.IOException;
import java.io.OutputStream;

import javafx.scene.control.TextArea;
//TODO: à supprimer si inutile (classe interne dans view.Window.java):
//Class to redirect standard output (for example console log) to a TextArea javafx chart:
public class ConsoleDebug extends OutputStream {
	private TextArea text;
	
	public ConsoleDebug(TextArea text) {
		this.text = text;
	}

	@Override
	public void write(int arg0) throws IOException {
		// redirects data to the text area javafx component:
		this.text.appendText(String.valueOf((char)arg0));
		
	}
}
