package main;

import java.io.BufferedReader;
import java.io.Console;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

	public static void main(String[] args) {
		CommandReader reader = new CommandReader();
		String line = null;

		System.out.println("以下のコマンドを入力してください。");
		System.out.println("ゲームを終了する： exit");
		System.out.println("ゲームを開始する： 任意のキー");

		line = reader.readLine();

		while (!(line == null || line.equals("exit"))) {
			System.out.println("入力してください。");
			line = reader.readLine();

		}

		System.out.println("お疲れ様でした。");
	}

}

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
