package main;

import java.io.BufferedReader;
import java.io.Console;
import java.io.IOException;
import java.io.InputStreamReader;

class CommandReader {

	private Console console;
	private BufferedReader reader;

	public CommandReader() {
		console = System.console();
		if (console == null) {
			reader = new BufferedReader(new InputStreamReader(System.in));
		}
	}

	public String readLine() {
		if (console != null) {
			return console.readLine();
		}

		try {
			return reader.readLine();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}