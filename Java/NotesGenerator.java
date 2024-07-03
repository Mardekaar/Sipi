import java.util.Arrays;
import java.util.Hashtable;

public class NotesGenerator {

  public static void main(String[] args) {
    String[] flatChromatic  = {"c", "db", "d", "eb", "e", "f", "gb", "g", "ab", "a", "bb", "b"};
    String[] sharpChromatic = {"c", "c#", "d", "d#", "e", "f", "f#", "g", "g#", "a", "a#", "b"};

    int[] ionianMode = {0, 2, 4, 5, 7, 9, 11};
    String[] ionianModeFlatKeys  = {"c", "db", "eb", "f", "gb", "ab", "bb"};

    int[] dorianMode = {0, 2, 3, 5, 7, 9, 10};
    String[] dorianModeFlatKeys  = {"c", "db", "eb", "f", "gb", "g", "ab", "bb"};

    int[] phrygianMode = {0, 1, 3, 5, 7, 8, 10};
    String[] phrygianModeFlatKeys  = {"c", "db", "d", "eb", "f", "gb", "g", "ab", "a", "bb"};

    int[] lydianMode = {0, 2, 4, 6, 7, 9, 11};
    String[] lydianModeFlatKeys = {"db", "eb", "f", "gb", "ab", "bb"};

    int[] mixolydianMode = {0, 2, 4, 5, 7, 9, 10};
    String[] mixolydianModeFlatKeys = {"c", "db", "eb", "f", "gb", "ab", "bb"};

    int[] aeolianMode = {0, 2, 3, 5, 7, 8, 10};
    String[] aeolianModeFlatKeys = {"c", "db", "d", "eb", "f", "gb", "g", "ab", "bb"};

    int[] locrianMode = {0, 1, 3, 5, 6, 8, 10};
    String[] locrianModeFlatKeys = flatChromatic;

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

    // these three lists have to be kept in sync and order
    String[] listOfScales = {"ionian", "dorian", "phrygian", "lydian", "mixolydian", "aeolian", "locrian"};
    int[][] listOfModes = {ionianMode, dorianMode, phrygianMode, lydianMode, 
                           mixolydianMode, aeolianMode, locrianMode};
    String[][] listOfFlatModes = {ionianModeFlatKeys, dorianModeFlatKeys, phrygianModeFlatKeys, 
                                  lydianModeFlatKeys, mixolydianModeFlatKeys, aeolianModeFlatKeys,
                                  locrianModeFlatKeys};

    Hashtable<String, int[]> scaleTypeToMode = new Hashtable<>();
    Hashtable<int[], String[]> modeToFlatMode = new Hashtable<>();

    for(int i = 0; i < listOfScales.length; i++) {
      scaleTypeToMode.put(listOfScales[i], listOfModes[i]);
      modeToFlatMode.put(listOfModes[i], listOfFlatModes[i]);
    }

    if(Arrays.asList(listOfScales).contains(scaleType)) {
      mode = scaleTypeToMode.get(scaleType);
      if(Arrays.asList(modeToFlatMode.get(mode)).contains(key)) {
        chromaticParent = flatChromatic;
        startingNote = Arrays.asList(flatChromatic).indexOf(key);
      } else {
        chromaticParent = sharpChromatic;
        startingNote = Arrays.asList(sharpChromatic).indexOf(key);
      }
    }

    int chromaticIndex = 0; 
    for(int i = 0; i < mode.length; i++) {
      chromaticIndex = mode[i] + startingNote;
      if(chromaticIndex >= chromaticParent.length) {
        chromaticIndex = (mode[i] + startingNote) - chromaticParent.length;
      }

      System.out.print(chromaticParent[chromaticIndex] +", ");
    }

    System.out.print(chromaticParent[startingNote] +"\n");

    System.out.println("key: "+ key +", starting note: "+ startingNote +", scale type: "+ scaleType);
 
  }
}
