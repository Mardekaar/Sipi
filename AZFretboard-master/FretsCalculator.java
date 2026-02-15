public class FretsCalculator {
    private static final int NUM_POSITIONS = 51; // Positions 0-50
    private static final int NUM_RED_RECTS = 25; // Red rectangles 0-24
    private static final int NUM_FRETS = 24;     // Frets 1-24

    private int[] positions;     // All 51 positions (x-coordinates)
    private int[] redRectLeftEdges;
    private int[] redRectWidths;
    private int[] redRectCenters;
    private int[] fretCenters;

    public FretsCalculator() {
        positions = new int[NUM_POSITIONS];
        redRectLeftEdges = new int[NUM_RED_RECTS];
        redRectWidths = new int[NUM_RED_RECTS];
        redRectCenters = new int[NUM_RED_RECTS];
        fretCenters = new int[NUM_FRETS + 1]; // Index 0 unused

        calculateAllPositions();
    }

    private void calculateAllPositions() {
        // New – shift right by 20 pixels:
        positions[0] = 20;   // Nut left edge
        positions[1] = 51;   // Nut center
        positions[2] = 83;   // Nut right edge

        // Target position for the last fret (fret24 center)
        int targetLastPos = 1180;
        int intervals = NUM_POSITIONS - 3; // 48 intervals from position 2 to 50
        double totalDistance = targetLastPos - positions[2]; // Should be 1117

        // Geometric decay factor
        // r=0.99 gives first spacing ~29 and last spacing ~18
        // This provides a natural decay that fits within bounds
        double r = 0.99;

        // Pre-calculate r^intervals
        double r_pow = Math.pow(r, intervals); // r^48
        double denom = 1 - r_pow; // 1 - r^48

        // Generate positions using geometric progression formula
        for (int i = 3; i < NUM_POSITIONS; i++) {
            int t = i - 2; // t from 1 to 48
            double fraction = (1 - Math.pow(r, t)) / denom;
            positions[i] = positions[2] + (int)(totalDistance * fraction);
        }

        // Ensure the last position is exactly the target
        positions[NUM_POSITIONS - 1] = targetLastPos;

        // Map positions to red rectangles and frets
        // Red rectangle centers are at odd positions (1, 3, 5, ..., 49)
        for (int i = 0; i < NUM_RED_RECTS; i++) {
            int posIndex = 2 * i + 1;
            redRectCenters[i] = positions[posIndex];
        }

        // Fret centers are at even positions (4, 6, 8, ..., 50)
        for (int fret = 1; fret <= NUM_FRETS; fret++) {
            int posIndex = 2 * fret + 2;
            fretCenters[fret] = positions[posIndex];
        }

        // Calculate red rectangle widths (80% of distance between surrounding positions)
        for (int i = 0; i < NUM_RED_RECTS; i++) {
            int posIndex = 2 * i + 1;

            if (i == 0) {
                // Red rect 0 (nut) – make narrower to leave space for tuning dropdown
                int distance = positions[2] - positions[0];
                redRectWidths[i] = (int)(distance * 0.6);   // was 0.8
            } else {
                // Other red rects: between position before and after
                int beforePos = positions[posIndex - 1];
                int afterPos = positions[posIndex + 1];
                int distance = afterPos - beforePos;
                redRectWidths[i] = (int)(distance * 0.8);
            }

            // Ensure minimum width of 20
            redRectWidths[i] = Math.max(redRectWidths[i], 20);

            // Calculate left edge
            redRectLeftEdges[i] = redRectCenters[i] - redRectWidths[i] / 2;
        }
    }



    public int[] getRedRectLeftEdges() {
        return redRectLeftEdges;
    }

    public int[] getRedRectWidths() {
        return redRectWidths;
    }

    // Get specific positions
    public int getNutLeftEdge() {
        return positions[0];
    }

    public int getNutRightEdge() {
        return positions[2];
    }

    // Get red rectangle center by index
    public int getRedRectCenter(int index) {
        if (index < 0 || index >= NUM_RED_RECTS) return 0;
        return redRectCenters[index];
    }

    // Get fret center by fret number (1-24)
    public int getFretCenter(int fret) {
        if (fret < 1 || fret > NUM_FRETS) return 0;
        return fretCenters[fret];
    }

    // Print all positions for debugging
    public void printAllPositions() {
        System.out.println("=== All 51 Positions ===");
        for (int i = 0; i < NUM_POSITIONS; i++) {
            String label = "";
            if (i == 0) label = "Nut left edge";
            else if (i == 1) label = "Nut center, RedRect00 center";
            else if (i == 2) label = "Nut right edge";
            else if (i % 2 == 1) label = "RedRect" + String.format("%02d", (i-1)/2) + " center";
            else label = "Fret" + String.format("%02d", (i-2)/2) + " center";

            String spacing = (i == 0) ? "" : String.format(" (+%3d)", positions[i] - positions[i-1]);
            System.out.println(String.format("Pos %2d: %4d%s - %s", i, positions[i], spacing, label));
        }
        System.out.println("=== End ===");
    }
}