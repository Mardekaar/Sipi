import java.util.Scanner;
import java.util.Arrays;
import java.lang.Integer;
import java.lang.String;
import java.lang.Character;

public class mode_naming {
	public static void main(String args[]) {

		int root_int;
		int accidental;
		int rotation_point;
		char root_note;
		char key_type;
		String output_mode_label = new String();
		String root_full = new String();
		String starting_note = new String();
		String note_set[] = {"", "", "", "", "", "", "", "", "", "", "", ""};
		String note_set_updated[] = {"", "", "", "", "", "", "", "", "", "", "", ""};
		int mode_chosen[] = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		

		// CIRCLE OF FIFTHS SEPARATED BY SHARPS/FLATS & MAJOR/MINOR
		String circle_of_sharps_minor[] = {"A", "E", "B", "F#", "C#", "G#", "D#"};
		String circle_of_sharps_major[] = {"C", "G", "D", "A", "E", "B", "F#"};
		String circle_of_flats_minor[] = {"A", "D", "G", "C", "F", "Bb", "Eb"};
		String circle_of_flats_major[] = {"C", "F", "Bb", "Eb", "Ab", "Db", "Gb"};

		// MODES MATRICES
		int lydian[] =     {1, 0, 1, 0, 1, 0, 1, 1, 0, 1, 0, 1};
		int ionian[] =     {1, 0, 1, 0, 1, 1, 0, 1, 0, 1, 0, 1};
		int mixolydian[] = {1, 0, 1, 0, 1, 1, 0, 1, 0, 1, 1, 0};
		int dorian[] =     {1, 0, 1, 1, 0, 1, 0, 1, 0, 1, 1, 0};
		int aeolian[] =    {1, 0, 1, 1, 0, 1, 0, 1, 1, 0, 1, 0};
		int phrygian[] =   {1, 1, 0, 1, 0, 1, 0, 1, 1, 0, 1, 0};
		int locrian[] =     {1, 1, 0, 1, 0, 1, 1, 0, 1, 0, 1, 0};

		// For sharp keys, letters from the sharp array will be used. For flat keys - from the flats.
		String regular_sharps[] =   {"A", "A#", "B", "C", "C#", "D", "D#", "E", "F", "F#", "G", "G#"};
		String regular_flats[] =    {"A", "Bb", "B", "C", "Db", "D", "Eb", "E", "F", "Gb", "G", "Ab"};


		Scanner scanner = new Scanner(System.in);

		// Getting the root:
		System.out.print("Enter root note: ");
		root_note = scanner.next().charAt(0);
		if (root_note > 96 && root_note < 123) { // Conversion to uppercase using ASCII values.
			root_note -= 32;
		}
		// System.out.println(root_note);

		root_int = root_note + 0; // Tricking the char to get converted to int by adding 0.
		// System.out.println(root_int);

		if (root_int < 65 || root_int > 71) { // Checking the valid range using ASCII values.
				System.out.println("Invalid input");
				System.exit(0);
		}

		// Getting the accidental:
		System.out.print("Enter accidental (2 - sharp, 1 - flat, 0 - none): ");
		accidental = scanner.nextInt();


		// Generating the full root note name with accidental:
		if (accidental == 2) {
			root_full = Character.toString(root_note) + "#";
		} else if (accidental == 1) {
			root_full = Character.toString(root_note) + "b";
		} else if (accidental == 0) {
			root_full = Character.toString(root_note);
		} else {
			System.out.println("Invalid input");
			System.exit(0);
		}

		// Major or minor:

		System.out.print("Choose type (d = major, m = minor: ");
		key_type = scanner.next().charAt(0);
		// System.out.println(key_type);
		if (key_type > 64 && key_type < 96) { // Conversion to lowercase using ASCII values.
			key_type += 32;
		}
		// System.out.println(key_type);

		scanner.close();

		if (key_type == 'd') {
			output_mode_label = " major";
			mode_chosen = ionian;

		} else if (key_type == 'm') {
			output_mode_label = " minor";
			mode_chosen = aeolian;
		} else {
			System.out.println("Invalid input");
			System.exit(0);
		}
		
		System.out.println(root_full + output_mode_label);
		

		// Parsing mode notes:
		if (mode_chosen == ionian) {
			for (int i = 0; i <= 6; i++) {
				if (root_full.equals(circle_of_sharps_major[i])) {
					for (int i_half_step = 0; i_half_step < 12; i_half_step++) {
						if (ionian[i_half_step] == 1) {
							note_set[i_half_step] = regular_sharps[i_half_step]; 
						}
					}
				} 
				else if (root_full.equals(circle_of_flats_major[i])) {
					for (int i_half_step = 0; i_half_step < 12; i_half_step++) {
						if (ionian[i_half_step] == 1) {
							note_set[i_half_step] = regular_flats[i_half_step]; 
						}
					}
				}
			}
		} 
		else if (mode_chosen == aeolian) {
			for (int j = 0; j <= 6; j++) {
				if (root_full.equals(circle_of_sharps_minor[j])) {
					for (int j_half_step = 0; j_half_step < 12; j_half_step++) {
						if (aeolian[j_half_step] == 1) {
							note_set[j_half_step] = regular_sharps[j_half_step]; 
						}
					}
				} 
				else if (root_full.equals(circle_of_flats_minor[j])) {
					for (int j_half_step = 0; j_half_step < 12; j_half_step++) {
						if (aeolian[j_half_step] == 1) {
							note_set[j_half_step] = regular_flats[j_half_step]; 
						}
					}
				}
			}
		}


		// Generating a new note set starting from the chosen root:
		for (rotation_point = 0; rotation_point < note_set.length; rotation_point++) {
			if (root_full.equals(note_set[rotation_point])) {
				starting_note = note_set[rotation_point];
				break;
			}
		}
		for (int k = 0; k < note_set.length; k++) {
			if ((k + rotation_point) > note_set.length - 1) {
				rotation_point = 0;
			}
			note_set_updated[k] = note_set[k + rotation_point];
		}
		
		// Printing out the updated note set:
		for (int note = 0; note < note_set.length; note++) {

			System.out.print(note_set[note] + " ");	
		}
		System.out.println();
	}
}