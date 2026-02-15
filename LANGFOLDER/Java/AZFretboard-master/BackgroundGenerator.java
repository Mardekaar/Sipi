import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.util.Random;

public class BackgroundGenerator {
    // Maple colors
    private static final Color[] MAPLE_COLORS = {
            new Color(205, 175, 140),
            new Color(200, 170, 135),
            new Color(195, 165, 130),
            new Color(190, 160, 125),
            new Color(185, 155, 120)
    };

    // Fiber colors
    private static final Color[] FIBER_COLORS = {
            new Color(180, 150, 120),
            new Color(170, 140, 110),
            new Color(160, 130, 100),
            new Color(150, 120, 90),
            new Color(140, 110, 80)
    };

    private static final Color MOTHER_OF_PEARL_LIGHT = new Color(255, 255, 255);
    private static final Color MOTHER_OF_PEARL_DARK = new Color(100, 80, 150);

//    private static final int[] FRET_X = {6, 70, 132, 190, 246, 301, 357, 410, 462, 513,
//            564, 612, 660, 708, 755, 799, 843, 886, 926, 967,
//            1007, 1045, 1082, 1119, 1154};
//    private static final int[] FRET_WIDTH = {50, 42, 36, 35, 32, 31, 29, 27, 27, 25,
//            23, 23, 22, 22, 21, 21, 21, 20, 21, 21,
//            20, 20, 20, 20, 20};
    private int dotsMode;
    private int[] fretXArray;
    private int[] fretWidthArray;
//    private static final int[] STRING_Y_6 = {12, 52, 90, 129, 169, 208};
    private static final int[] STRING_Y_10 = {12, 42, 72, 102, 132, 162, 192, 222, 252, 282};
    private static final int RECT_HEIGHT = 20;
    private static final int FRET_THICKNESS = 6;
    private FretsCalculator fretsCalculator;

    public BackgroundGenerator(FretsCalculator calculator, int dotsMode) {
        this.fretsCalculator = calculator;
        this.dotsMode = dotsMode;
    }

    public BufferedImage generateFretboard(int numStrings, int numFrets, int width, int height) {
        if (fretsCalculator == null) {
            fretsCalculator = new FretsCalculator();
        }

        // Get red rectangle positions for other methods
        fretXArray = fretsCalculator.getRedRectLeftEdges();
        fretWidthArray = fretsCalculator.getRedRectWidths();

        BufferedImage baseImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D baseG2d = baseImage.createGraphics();

        baseG2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        baseG2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

        drawMapleBackground(baseG2d, width, height);

        baseG2d.dispose();

        BufferedImage blurredImage = applyBlur(baseImage, 2.5f);

        BufferedImage finalImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = finalImage.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

        g2d.drawImage(blurredImage, 0, 0, null);

        int[] stringYPositions = calculateStringPositions(numStrings);
        int[] stringHeights = calculateStringHeights(numStrings);

        drawFretMarkers(g2d, numFrets, numStrings, stringYPositions);
        drawAllFrets(g2d, numFrets, height);

        for (int i = 0; i < numStrings; i++) {
            drawString(g2d, stringYPositions[i], stringHeights[i], width);
        }

        g2d.dispose();
        return finalImage;
    }

    public int calculateFretboardHeight(int numStrings) {
        int topMargin = 20;        // from top edge to first rectangle top
        int rectHeight = 20;       // height of each red rectangle
        int spacing = 40;         // between rectangle tops
        int bottomMargin = 20;    // from last rectangle bottom to panel edge
        return topMargin + (numStrings - 1) * spacing + rectHeight + bottomMargin;
    }

    private BufferedImage applyBlur(BufferedImage image, float blurRadius) {
        if (blurRadius <= 0) return image;

        int size = (int)(blurRadius * 2 + 1);
        if (size < 3) size = 3;

        float[] matrix = new float[size * size];
        float sigma = blurRadius / 2.0f;
        float twoSigmaSquare = 2.0f * sigma * sigma;
        float sigmaRoot = (float)Math.sqrt(twoSigmaSquare * Math.PI);
        float total = 0.0f;

        int center = size / 2;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                float x = center - i;
                float y = center - j;
                float distance = x * x + y * y;
                matrix[i * size + j] = (float)Math.exp(-distance / twoSigmaSquare) / sigmaRoot;
                total += matrix[i * size + j];
            }
        }

        for (int i = 0; i < size * size; i++) {
            matrix[i] /= total;
        }

        Kernel kernel = new Kernel(size, size, matrix);
        ConvolveOp convolve = new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP, null);
        return convolve.filter(image, null);
    }

    private void drawMapleBackground(Graphics2D g2d, int width, int height) {
        g2d.setColor(MAPLE_COLORS[2]);
        g2d.fillRect(0, 0, width, height);

        Random random = new Random(42);

        for (int i = 0; i < 2000; i++) {
            drawSingleFiber(g2d, width, height, random, false);
        }

        for (int i = 0; i < 500; i++) {
            drawSingleFiber(g2d, width, height, random, true);
        }
    }

    private void drawSingleFiber(Graphics2D g2d, int width, int height, Random random, boolean thick) {
        int startX = random.nextInt(width);
        int startY = random.nextInt(height);

        int fiberLength = random.nextInt(80) + 40;

        float strokeWidth;
        int alpha;

        if (thick) {
            strokeWidth = 1.2f + random.nextFloat() * 0.8f;
            alpha = 80 + random.nextInt(80);
        } else {
            strokeWidth = 0.5f + random.nextFloat() * 0.7f;
            alpha = 40 + random.nextInt(60);
        }

        g2d.setStroke(new BasicStroke(strokeWidth, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));

        Color fiberColor = FIBER_COLORS[random.nextInt(FIBER_COLORS.length)];
        fiberColor = new Color(
                fiberColor.getRed(),
                fiberColor.getGreen(),
                fiberColor.getBlue(),
                alpha
        );

        g2d.setColor(fiberColor);

        float angle = (float) (random.nextDouble() * Math.PI / 30 - Math.PI / 60);

        int endX = startX + (int)(fiberLength * Math.cos(angle));
        int endY = startY + (int)(fiberLength * Math.sin(angle));

        endX = Math.max(0, Math.min(width, endX));
        endY = Math.max(0, Math.min(height, endY));

        g2d.drawLine(startX, startY, endX, endY);

        g2d.setStroke(new BasicStroke(1));
    }

    private int[] calculateStringPositions(int numStrings) {
        int[] positions = new int[numStrings];
        int topMargin = 20;
        int spacing = 40;
        int rectHeight = 20;
        for (int i = 0; i < numStrings; i++) {
            positions[i] = topMargin + (i * spacing) + rectHeight / 2; // center = top + 10
        }
        return positions;
    }

    private int[] calculateStringHeights(int numStrings) {
        int[] heights = new int[numStrings];
        // Very subtle: from 2px to 4px for 10 strings
        float increment = 2.0f / Math.max(1, numStrings - 1);

        for (int i = 0; i < numStrings; i++) {
            heights[i] = 2 + Math.round(i * increment);
        }
        return heights;
    }

    private void drawAllFrets(Graphics2D g2d, int numFrets, int height) {
        drawNut(g2d, height);

        for (int fret = 1; fret < numFrets; fret++) {
            drawFret(g2d, fret, height);
        }
    }

    private void drawNut(Graphics2D g2d, int height) {
        // Get nut position from FretsCalculator
        int nutLeftEdge = fretsCalculator.getNutLeftEdge();
        int nutRightEdge = fretsCalculator.getNutRightEdge();

        // Draw nut from left edge to right edge
        drawFretAtPosition(g2d, nutLeftEdge, nutRightEdge, height);
    }

    private void drawFret(Graphics2D g2d, int fretNum, int height) {
        // Get fret center from FretsCalculator
        int fretCenterX = fretsCalculator.getFretCenter(fretNum);
        int fretLeftX = fretCenterX - FRET_THICKNESS / 2;
        int fretRightX = fretLeftX + FRET_THICKNESS;

        drawFretAtPosition(g2d, fretLeftX, fretRightX, height);
    }

    private void drawFretAtPosition(Graphics2D g2d, int leftX, int rightX, int height) {
        int fretWidth = rightX - leftX;

        g2d.setColor(Color.BLACK);
        g2d.fillRect(leftX, 0, fretWidth, height);

        GradientPaint fretGradient = new GradientPaint(
                leftX, 0, Color.BLACK,
                rightX, 0, Color.WHITE
        );
        g2d.setPaint(fretGradient);
        g2d.fillRect(leftX + 1, 1, fretWidth - 2, height - 2);
    }

    private void drawString(Graphics2D g2d, int y, int height, int panelWidth) {
        int stringY = y - height/2;

        g2d.setColor(Color.BLACK);
        g2d.fillRect(0, stringY, panelWidth, height);

        GradientPaint stringGradient = new GradientPaint(
                0, stringY, Color.WHITE,
                0, stringY + height, Color.BLACK
        );
        g2d.setPaint(stringGradient);
        g2d.fillRect(1, stringY + 1, panelWidth - 2, height - 2);
    }

    private void drawFretMarkers(Graphics2D g2d, int numFrets, int numStrings, int[] stringY) {
        int dotDiameter = 25;

        // Determine which frets get dots based on current mode
        int[] markerFrets;
        if (dotsMode == 0) { // 0 = Guitar/Bass
            markerFrets = new int[]{3, 5, 7, 9, 12, 15, 17, 19, 21, 24};
        } else if (dotsMode == 1) { // 1 = Mandolin/Banjo/Ukulele
            markerFrets = new int[]{3, 5, 7, 10, 12, 15, 17, 19, 22, 24};
        } else { // 2 = Off
            markerFrets = new int[0];
        }

        System.out.println("=== Dot Position Calculations ===");

        for (int fret : markerFrets) {
            if (fret <= numFrets) {
                int redRectCenter = fretsCalculator.getRedRectCenter(fret);
                System.out.println("Fret " + fret + ": Center=" + redRectCenter);

                if (fret == 12 || fret == 24) {
                    int topY = stringY[0];
                    int bottomY = stringY[numStrings - 1];
                    drawPearlDot(g2d, redRectCenter, topY, dotDiameter);
                    drawPearlDot(g2d, redRectCenter, bottomY, dotDiameter);
                } else {
                    int middleY = (stringY[0] + stringY[numStrings - 1]) / 2;
                    drawPearlDot(g2d, redRectCenter, middleY, dotDiameter);
                }
            }
        }

        System.out.println("=== End Dot Calculations ===");
    }

    private void drawPearlDot(Graphics2D g2d, int x, int y, int diameter) {
        g2d.setColor(Color.BLACK);
        g2d.fillOval(x - diameter/2, y - diameter/2, diameter, diameter);

        GradientPaint pearlGradient = new GradientPaint(
                x, y - diameter/2, MOTHER_OF_PEARL_LIGHT,
                x, y + diameter/2, MOTHER_OF_PEARL_DARK
        );
        g2d.setPaint(pearlGradient);
        g2d.fillOval(x - diameter/2 + 1, y - diameter/2 + 1, diameter - 2, diameter - 2);
    }

    private void drawHighlightedRedRect(Graphics2D g2d, int fret, int stringY, Color highlightColor) {
        if (fret < 0 || fret >= fretXArray.length) return;

        int x = fretXArray[fret];
        int width = fretWidthArray[fret];
        int y = stringY - RECT_HEIGHT/2;

        g2d.setColor(highlightColor);
        g2d.fillRect(x, y, width, RECT_HEIGHT);
    }

}
