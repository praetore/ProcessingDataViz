import processing.core.PApplet;
import processing.core.PImage;

/**
 * Created by darryl on 1-5-15.
 */
public class GeoCharts extends PApplet {
    private PImage icelandMap;
    private String earthQuakeData[];
    private static final int SCALE = 3;
    private int MAPWIDTH;
    private int MAPHEIGHT;
    private int gridXSize;
    private int gridYSize;
    private int maxXCoord = 26;
    private int maxYCoord = 67;

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
        renderGrid();
        drawDataPoints(earthQuakeData);
    }

    private void renderMap() {
        image(icelandMap, 0, 0, MAPWIDTH, MAPHEIGHT);
    }

    private void drawDataPoints(String[] data) {
        for (String s : data) {
            String[] dataPoints = split(s, ";");
            int x = Integer.parseInt(dataPoints[5]);
            int y = Integer.parseInt(dataPoints[2]);
            drawSingleDataPoint(x, y);
        }
    }

    private void drawSingleDataPoint(int x, int y) {
        int xPos = (maxXCoord - x) * gridXSize;
        int yPos = (maxYCoord - y) * gridYSize;
        ellipse(xPos, yPos, 10, 10);
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