public class NoteCalculator {
    private String[] sharpNotes = {"C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B"};
    private String[] flatNotes  = {"C", "Db", "D", "Eb", "E", "F", "Gb", "G", "Ab", "A", "Bb", "B"};

    // Exact names for dropdown menus (sharp/flat combined)
    public static final String[] DROPDOWN_NOTES = {
            "A", "A#/Bb", "B", "C", "C#/Db", "D", "D#/Eb", "E", "F", "F#/Gb", "G", "G#/Ab"
    };

    public static final int[] DROPDOWN_TO_CHROMATIC = {
            9,  // A
            10, // A#/Bb
            11, // B
            0,  // C
            1,  // C#/Db
            2,  // D
            3,  // D#/Eb
            4,  // E
            5,  // F
            6,  // F#/Gb
            7,  // G
            8   // G#/Ab
    };

    private int[] tuning = {4, 11, 7, 2, 9, 4, 11, 6, 1, 8}; // E,B,G,D,A,E,B,F#,C#,G#
    private boolean useSharps = true;

    public String getNoteName(int stringIndex, int fret) {
        if (stringIndex < 0 || stringIndex >= tuning.length) return "";
        int noteIndex = (tuning[stringIndex] + fret) % 12;
        return useSharps ? sharpNotes[noteIndex] : flatNotes[noteIndex];
    }

    public void setTuning(int stringIndex, int noteIndex) {
        if (stringIndex >= 0 && stringIndex < tuning.length)
            tuning[stringIndex] = noteIndex;
    }

    public void toggleSharpFlat() { useSharps = !useSharps; }
    public boolean isUsingSharps() { return useSharps; }
}