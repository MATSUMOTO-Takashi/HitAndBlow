package main;

import java.io.BufferedReader;
import java.io.Console;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main {

	private static final boolean DEBUG_MODE = true;
	private static final long DEBUG_SEED = 1234;

	private static final int DIGITS = 4;

	private static final Random seed;

	static {
		if (DEBUG_MODE) {
			seed = new Random(DEBUG_SEED);
		} else {
			seed = new Random(System.currentTimeMillis());
		}

		// 初回の乱数は捨てる
		seed.nextInt();
	}

	public static void main(String[] args) {
		CommandReader reader = new CommandReader();
		String line = null;

		final String expected = createAnswer();
		if (DEBUG_MODE) {
			System.out.println("DEBUG: 正解： " + expected);
		}

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

	private static String createAnswer() {
		List<String> numbers = IntStream.rangeClosed(0, 9).mapToObj(Integer::toString).collect(Collectors.toList());
		List<String> list = new ArrayList<>();

		for (int i = 0; i < DIGITS; i++) {
			int index = seed.nextInt(numbers.size());
			list.add(numbers.remove(index));
		}

		return String.join("", list);
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
