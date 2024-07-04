import java.util.Arrays;
import java.util.Hashtable;

public class NotesGenerator {

  public static void main(String[] args) {
    String[] flatChromatic  = {"c", "db", "d", "eb", "e", "f", "gb", "g", "ab", "a", "bb", "b"};
    String[] sharpChromatic = {"c", "c#", "d", "d#", "e", "f", "f#", "g", "g#", "a", "a#", "b"};

    int[] ionianMode = {0, 2, 4, 5, 7, 9, 11};

    if(args.length < 2) {
      System.out.println("Usage examples:");
      System.out.println("- C ionian");
      System.out.println("- Bb dorian");
      System.out.println("- Eb phrygian");
      System.exit(0);
    }

    String key = "C";
    String scaleType = "major";
    if(args.length == 2) {
      key = args[0].toLowerCase();
      scaleType = args[1].toLowerCase();
    }

    /*if(args.length == 3) {
      key = args[0].toLowerCase();
      sharpOrFlat = args[1].toLowerCase();
      scaleType = args[2].toLowerCase();
    }*/

    String[] chromaticParent = null;
    int[] mode = null;
    int startingNote = 0;

    if(Arrays.asList(flatChromatic).contains(key)) {
      chromaticParent = flatChromatic;
      startingNote = Arrays.asList(flatChromatic).indexOf(key);
    } else {
      chromaticParent = sharpChromatic;
      startingNote = Arrays.asList(sharpChromatic).indexOf(key);
    }

    StringBuilder parentScale = new StringBuilder();
    int chromaticIndex = 0; 
    for(int i = 0; i < ionianMode.length; i++) {
      chromaticIndex = ionianMode[i] + startingNote;
      if(chromaticIndex >= chromaticParent.length) {
        chromaticIndex = (ionianMode[i] + startingNote) - chromaticParent.length;
      }

      parentScale.append(chromaticParent[chromaticIndex]);
    }

    //System.out.print(chromaticParent[startingNote] +"\n");

    switch(scaleType) {
      case "major" :   generateMode(parentScale, ionianMode, 0);
                       break;
      case "ionian":   generateMode(parentScale, ionianMode, 0);
                       break;
      case "dorian":   generateMode(parentScale, ionianMode, 1);
                       break;
      case "phrygian": generateMode(parentScale, ionianMode, 2);
                       break;
      case "lydian": generateMode(parentScale, ionianMode, 3);
                       break;
      case "mixolydian": generateMode(parentScale, ionianMode, 4);
                       break;
      case "aeolian": generateMode(parentScale, ionianMode, 5);
                       break;
      case "locrian": generateMode(parentScale, ionianMode, 6);
                       break;
    }

    System.out.println("key: "+ key +", starting note: "+ startingNote +", scale type: "+ scaleType);
 
  }

  static void generateMode(StringBuilder notes, int[] formula, int offset) {
    for(int i = offset, j = 0; j < formula.length; i++, j++) {
      if(i == formula.length) {
        i = 0;
      }

      System.out.print(notes.charAt(i) +", ");
    }

    System.out.print(notes.charAt(offset) +"\n");
  }
}
