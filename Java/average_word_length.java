// Calculates average word length from user input.

import java.util.Scanner;

public class average_word_length {
	public static void main(String[] args) {
		/*
		 * input
		 * 
		 * count characters
		 * count spaces
		 * word count = spaces + 1
		 * average = characters / words
		 */

		int chars = 0, spaces = 0, word_count = 0;
		float average = 0.0f;
		String user_input;
		Scanner scanner = new Scanner(System.in);

		System.out.print("Enter a sentence: ");

		user_input = scanner.nextLine();
		for (int i = 0; i < user_input.length(); i++) {
			if (user_input.charAt(i) == ' ') {
				spaces += 1;
			} else {
				chars += 1;
			}
		}
		word_count = spaces + 1;
		average = (float) chars / (float) word_count;

		System.out.println("chars: " + chars);
		System.out.println("spaces: " + spaces);
		System.out.println("word count: " + word_count);
		System.out.println(String.format("Average word count: %.1f", average));

	}
}