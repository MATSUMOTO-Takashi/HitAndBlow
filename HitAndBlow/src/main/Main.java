package main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main {

	private static final long DEBUG_SEED = 1234;
	private static final int DIGITS = 4;

	private static boolean DEBUG_MODE = false;
	private static Random GEN;

	public static void main(String[] args) {
		System.out.println("以下のコマンドを入力してください。");
		System.out.println("ゲームを開始する： 任意のキー");
		System.out.println("ゲームを終了する： exit");

		CommandReader reader = new CommandReader();
		String line = reader.readLine();

		if (line.equals("DEBUG")) {
			DEBUG_MODE = true;
		}
		init();

		final String expected = createAnswer();
		if (DEBUG_MODE) {
			System.out.println("DEBUG: 正解： " + expected);
		}

		while (line != null && !line.equals("exit")) {
			System.out.println("質問する数値を入力してください。");
			line = reader.readLine();

			if (line.length() != DIGITS) {
				System.out.println("入力桁数が" + DIGITS + "ではありません。");
				continue;
			}

			if (!line.matches("[0-9]+")) {
				System.out.println("数値以外が含まれています。");
				continue;
			}

			if (new HashSet<>(Arrays.asList(line.split(""))).size() != DIGITS) {
				System.out.println("重複した数値が含まれています。");
				continue;
			}

			int[] result = checkTheAnswer(expected, line);
			System.out.println("入力値 " + line + " は、" + result[0] + "ヒット、" + result[1] + "ブローです。");

			// ゲームクリア
			if (result[0] == DIGITS) {
				System.out.println("正解です！");
				break;
			}
		}

		System.out.println("お疲れ様でした。");
	}

	private static void init() {
		if (DEBUG_MODE) {
			GEN = new Random(DEBUG_SEED);
		} else {
			GEN = new Random(System.currentTimeMillis());
		}

		// 初回の乱数は捨てる
		GEN.nextInt();
	}

	private static String createAnswer() {
		List<String> numbers = IntStream.rangeClosed(0, 9).mapToObj(Integer::toString).collect(Collectors.toList());
		List<String> list = new ArrayList<>();

		for (int i = 0; i < DIGITS; i++) {
			int index = GEN.nextInt(numbers.size());
			list.add(numbers.remove(index));
		}

		return String.join("", list);
	}

	private static int[] checkTheAnswer(String expected, String actual) {
		if (expected.length() != actual.length()) {
			throw new IllegalArgumentException("桁数が異なります。: expected: " + expected.length() + ", actual: " + actual.length());
		}

		char[] splitExpected = expected.toCharArray();
		char[] splitActual = actual.toCharArray();

		// [0]=Hit, [1]=Blow
		int[] result = new int[2];

		for (int i = 0; i < splitExpected.length; i++) {
			if (splitExpected[i] == splitActual[i]) {
				result[0]++;
			}
		}

		for (int i = 0; i < splitExpected.length; i++) {
			for (int j = 0; j < splitActual.length; j++) {
				if (i == j) {
					continue;
				}
				if (splitExpected[i] == splitActual[j]) {
					result[1]++;
				}
			}
		}

		return result;
	}

}
