import java.util.Scanner;
import java.util.Arrays;
import java.lang.Integer;
import java.lang.String;
import java.lang.Character;

public class note_generator {
    public static void main(String args[]) {

        int i = 0;
        int j = 0;
        int accidental;
        char root_note;
        char key_type;
        String output_mode_label = new String();
        String root_full = new String();
        int mode_chosen[] = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
        String note_names[] = { "C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B" };
        int note_full_or_half[] = { 1, 0, 1, 0, 1, 1, 0, 1, 0, 1, 0, 1 };
        String note_names_generated[] = { "", "", "", "", "", "", "", "", "", "", "", "" };
        int note_full_or_half_generated[] = { 1, 0, 1, 0, 1, 1, 0, 1, 0, 1, 0, 1 };
        // int lydian[] = { 1, 0, 1, 0, 1, 0, 1, 1, 0, 1, 0, 1 };
        int ionian[] = { 1, 0, 1, 0, 1, 1, 0, 1, 0, 1, 0, 1 };
        // int mixolydian[] = { 1, 0, 1, 0, 1, 1, 0, 1, 0, 1, 1, 0 };
        // int dorian[] = { 1, 0, 1, 1, 0, 1, 0, 1, 0, 1, 1, 0 };
        int aeolian[] = { 1, 0, 1, 1, 0, 1, 0, 1, 1, 0, 1, 0 };
        // int phrygian[] = { 1, 1, 0, 1, 0, 1, 0, 1, 1, 0, 1, 0 };
        // int locrian[] = { 1, 1, 0, 1, 0, 1, 1, 0, 1, 0, 1, 0 };

        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter root note: ");
        root_note = scanner.next().charAt(0);
        if (root_note > 96 && root_note < 123) { // Conversion to uppercase using ASCII values.
            root_note -= 32;
        } else {
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
        if (key_type > 64 && key_type < 96) { // Conversion to lowercase using ASCII values.
            key_type += 32;
        }

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
        scanner.close();

        // correct the accidentals
        if (accidental == 1) { // change # to b for flat scales
            for (i = 0; i <= 11; i++) {
                if (note_names[i].indexOf("#") < 0) {
                    note_names[i].replace("#", "b");
                }
            }
        } else if (accidental == 0) { // if no accidental specified, use circle of fifth

        }
        for (i = 0; i <= 11; i++) {
            if (note_names[i].equals(root_full)) {
                break; // i shows which is the starting position/note of the new scale
            }
        }
        for (j = 0; j <= 11; j++) { // generate new note list from position i
            note_names_generated[j] = note_names[i];
            note_full_or_half_generated[j] = note_full_or_half[i];
            i++;
            if (i > 11) {
                i = 0;
            }
        }
        // delete those notes which are not part of the scale
        for (i = 0; i <= 11; i++) {
            if (mode_chosen[i] == 0) {
                note_names_generated[i] = "";
            }
        }

        // print out the new scale
        for (i = 0; i <= 11; i++) {
            System.out.println(note_names_generated[i]);
        }

    }
}