import processing.core.PApplet;
import processing.core.PImage;

/**
 * Created by darryl on 1-5-15.
 */
public class GeoCharts extends PApplet {
    private static final int SCALE = 3;
    private static final int maxXCoord = 26;
    private static final int maxYCoord = 67;
    private static final boolean displayGrid = false;
    private final int[] severity = {
            color(0, 255, 228),
            color(53, 204, 8),
            color(153, 145, 1),
            color(204, 75, 8),
            color(255, 23, 7)
    };
    private PImage icelandMap;
    private String earthQuakeData[];
    private int MAPWIDTH;
    private int MAPHEIGHT;
    private int gridXSize;
    private int gridYSize;

    @Override
    public void setup() {
        icelandMap = loadImage("data/iceland-map.png");
        earthQuakeData = loadStrings("data/earthquake-data.csv");
        MAPWIDTH = icelandMap.width / SCALE;
        MAPHEIGHT = icelandMap.height / SCALE;
        gridXSize = MAPWIDTH / 14;
        gridYSize = MAPHEIGHT / 4;

        size(MAPWIDTH, MAPHEIGHT);
        smooth();
        noLoop();
    }

    @Override
    public void draw() {
        renderMap();
        if (displayGrid) {
            renderGrid();
        }
        renderLegend();
        drawDataPoints();

    }

    private void renderMap() {
        image(icelandMap, 0, 0, MAPWIDTH, MAPHEIGHT);
    }

    private void drawDataPoints() {
        for (String s : earthQuakeData) {
            String[] dataPoints = split(s, ";");
            int x = Integer.parseInt(dataPoints[5]);
            int y = Integer.parseInt(dataPoints[2]);
            double richter = Double.parseDouble(dataPoints[8]);
            int duration = (int) (Double.parseDouble(dataPoints[9]) / 61);
            drawSingleDataPoint(x, y, richter, duration);
        }
    }

    private void renderLegend() {
        fill(255);
        rect((MAPWIDTH / 5) * 3, (MAPHEIGHT / 12) * 10, (float) (MAPWIDTH / 5.25), (float) ((MAPHEIGHT / 12) * 1.75));
    }

    private void drawSingleDataPoint(int x, int y, double richter, int duration) {
        fill(severity[duration]);
        richter = richter * 4;
        int xPos = (maxXCoord - x) * gridXSize;
        int yPos = (maxYCoord - y) * gridYSize;
        ellipse(xPos, yPos, (float) richter, (float) richter);
    }

    private void renderGrid() {
        stroke(255, 0, 0);
        fill(255, 0, 0);

        int axisX = maxXCoord;
        for (int i = 0; i <= MAPWIDTH; i += gridXSize) {
            if (i % 2 == 0) {
                line(i, 0, i, MAPHEIGHT);
                text(axisX, i + 5, MAPHEIGHT - 5);
            }
            axisX -= 1;
        }

        int axisY = maxYCoord;
        for (int i = 0; i <= MAPHEIGHT; i += gridYSize) {
            line(0, i, MAPWIDTH, i);
            text(axisY, MAPWIDTH - 25, i + 15);
            axisY -= 1;
        }
    }
}