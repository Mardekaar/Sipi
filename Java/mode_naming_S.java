import java.util.Scanner;
import java.util.Arrays;
import java.lang.Integer;
import java.lang.String;
import java.lang.Character;

public class mode_naming {
    public static void main(String args[]) {

        int i = 0;
        int accidental;
        char root_note;
        char key_type;
        String output_mode_label = new String();
        String root_full = new String();
        String starting_note = new String();

        int mode_chosen[] = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };

        // Sipi: define all 12 notes, 0=half note, 1=whole note, starting from C.
        String note_names[] = { "C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B" };
        String note_names_generated[] = { "", "", "", "", "", "", "", "", "", "", "", "" };
        // Sipi: define which are full=1 or half=0 notes in the original note_names.
        int note_full_or_half[] = { 1, 0, 1, 0, 1, 1, 0, 1, 0, 1, 0, 1 };
        // Sipi: define the whole/half steps in the modes.
        int lydian[] = { 1, 0, 1, 0, 1, 0, 1, 1, 0, 1, 0, 1 };
        int ionian[] = { 1, 0, 1, 0, 1, 1, 0, 1, 0, 1, 0, 1 };
        int mixolydian[] = { 1, 0, 1, 0, 1, 1, 0, 1, 0, 1, 1, 0 };
        int dorian[] = { 1, 0, 1, 1, 0, 1, 0, 1, 0, 1, 1, 0 };
        int aeolian[] = { 1, 0, 1, 1, 0, 1, 0, 1, 1, 0, 1, 0 };
        int phrygian[] = { 1, 1, 0, 1, 0, 1, 0, 1, 1, 0, 1, 0 };
        int locrian[] = { 1, 1, 0, 1, 0, 1, 1, 0, 1, 0, 1, 0 };
        // Sipi: now we have all scales in all modes defined, we just need to shift the
        // elements of these arrays, according to the selected root note.

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

        // Sipi: find out the position of the selected root note and generate note names
        // from this position
        i = 0;
        while (i <= 11) {
            if (note_names[i] == root_full) { // we have to regenerate the note name list, using position i of
                                              // note_names
                for (int j = 0; j <= 11; j++) {
                    note_names_generated[j] = note_names[i];
                    j++;
                    if (i + 1 > 11) {
                        i = 0;
                    } else {
                        i++;
                    }

                }
            } else {
                i++;
            }
        }

    }
}
