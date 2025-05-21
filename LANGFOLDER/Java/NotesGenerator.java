import java.util.Arrays;

public class NotesGenerator {

  public static void main(String[] args) {

    if(args.length < 2) {
      System.out.println("Usage examples:");
      System.out.println("- C ionian");
      System.out.println("- Bb dorian");
      System.out.println("- F# lydian");
      System.exit(0);
    }

    int cycle = 0;
    int degree = 0;
    String accidental = null;
    String origin = null;
    String key = args[0].toLowerCase();
    if(key.substring(1, key.length()).contains("b") || key.equals("f") || key.equals("c")) {
      cycle = 3;
      degree = 3;
      accidental = "b";
      origin = "flat";
    } else {
      cycle = 4;
      degree = 6;
      accidental = "#";
      origin = "sharp";
    }

    String mode = args[1].toLowerCase();

    String[] result = createIonian(key, mode, cycle, accidental, degree);
    switch(mode) {
      case "ionian"     : printResult(result);
                          break;
      case "dorian"     : createOther(mode, origin, accidental, result, new int[] {2, 6});
                          break;
      case "phrygian"   : createOther(mode, origin, accidental, result, new int[] {1, 2, 5, 6});
                          break;
      case "lydian"     : createOther(mode, origin, accidental, result, new int[] {3});
                          break;
      case "mixolydian" : createOther(mode, origin, accidental, result, new int[] {6});
                          break;
      case "aeolian"    : createOther(mode, origin, accidental, result, new int[] {2, 5, 6});
                          break;
      case "locrian"    : createOther(mode, origin, accidental, result, new int[] {1, 2, 4, 5, 6});
                          break;
    }
  }

  static String[] createIonian(String key, String mode, int cycle, String accidental, int degree) {
    String[] notes = {"c", "d", "e", "f", "g", "a", "b"};
    String[] result = new String[notes.length];

    if(!Arrays.asList(notes).contains(key.substring(0, 1))) {
      System.out.println("No match found");
      System.exit(0);
    }

    int storedIndex = 0;
    for(int i = 0, j = 0, resultIndex = 0; j < notes.length * 15; i++, j++, resultIndex++) {
      if(i >= notes.length) {
        i = 0;
      }

      if(resultIndex >= notes.length) {
        resultIndex = 0;

        if(result[0].equals(key))
          break;
      }

      if(j % notes.length == cycle) {
        storedIndex = i;
      }

      if(j % notes.length == 0) {
        i = storedIndex;
      }

      if(j > notes.length && j % notes.length == degree) {
        notes[i] = notes[i].concat(accidental); 
      }

      result[resultIndex] = notes[i];
    }

    return result;
  }

  static void createOther(String mode, String origin, String accidental,
                                        String[] result, int[] indices) {

    if(mode.equals("dorian") && origin.equals("sharp") && result[0].equals("g")) {
        result[2] = result[2].concat("b");
        result[6] = result[6].substring(0, 1);
        printResult(result);
        return;
    } 

    if(mode.equals("lydian")) {
      if(origin.equals("flat") && !result[0].equals("c")) {
        result[3] = result[3].substring(0, result[3].length() - 1);
      } else if(result[0].equals("c")) {
        result[3] = result[3].concat("#");
      } else {
        result[3] = result[3].concat(accidental);
      }

      printResult(result);
      return;
    }

    if(mode.equals("aeolian") && origin.equals("sharp") && 
      (result[0].equals("d") || result[0].equals("g"))) {        
      if(result[0].equals("d")) {
        result[2] = result[2].substring(0, result[2].length() - 1);
        result[5] = result[5].concat("b");
        result[6] = result[6].substring(0, result[6].length() - 1);
      } else if(result[0].equals("g")) {
        result[2] = result[2].concat("b");
        result[5] = result[5].concat("b");
        result[6] = result[6].substring(0, result[6].length() - 1);
      }

      printResult(result);
      return;
    }

    if(mode.equals("locrian") && origin.equals("sharp") &&
    (result[0].equals("d") | result[0].equals("e") | result[0].equals("g") | result[0].equals("a"))) {
      if(result[0].equals("d")) {
        result[1] = result[1].concat("b");
        result[2] = result[2].substring(0, result[2].length() - 1);
        result[4] = result[4].concat("b");
        result[5] = result[5].concat("b");
        result[6] = result[6].substring(0, result[6].length() - 1);
      } else if(result[0].equals("e")) {
        result[1] = result[1].substring(0, result[1].length() - 1);
        result[2] = result[2].substring(0, result[2].length() - 1);
        result[4] = result[4].concat("b");
        result[5] = result[5].substring(0, result[5].length() - 1);
        result[6] = result[6].substring(0, result[6].length() - 1);
      } else if(result[0].equals("g")) {
        result[1] = result[1].concat("b");
        result[2] = result[2].concat("b");
        result[4] = result[4].concat("b");
        result[5] = result[5].concat("b");
        result[6] = result[6].substring(0, result[6].length() - 1);
      } else if(result[0].equals("a")) {
        result[1] = result[1].concat("b");
        result[2] = result[2].substring(0, result[2].length() - 1);
        result[4] = result[4].concat("b");
        result[5] = result[5].substring(0, result[5].length() - 1);
        result[6] = result[6].substring(0, result[6].length() - 1);
      }

      printResult(result);
      return;
    }

    for(int index : indices) {
      if(origin.equals("flat")) {
        result[index] = result[index].concat(accidental);
      } else {
        result[index] = result[index].substring(0, result[index].length() - 1);
      }
    }

    printResult(result);
  }

  static void printResult(String[] result) {
    for(String s : result) 
      System.out.print(s.substring(0, 1).toUpperCase() + s.substring(1) +", ");
  }
}

