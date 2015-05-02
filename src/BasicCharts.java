import processing.core.PApplet;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Created by darryl on 1-5-15.
 */
public class BasicCharts extends PApplet {
    private Map<String, Integer> barChartData = new HashMap<>();

    private Map<String, Float> pieChartData = new HashMap<>();
    private float[] angles;
    private String[] names;
    private Random random = new Random();

    @Override
    public void setup() {
        size(640, 960);
        background(255);
        smooth();
        noLoop();

        pieChartSetup();
        barChartSetup();
    }

    @Override
    public void draw() {
        pieChart(300, angles);
        barChart();
    }

    private void barChartSetup() {
        barChartData.put("Kwartaal 1", 120);
        barChartData.put("Kwartaal 2", 150);
        barChartData.put("Kwartaal 3", 180);
        barChartData.put("Kwartaal 4", 60);
    }

    private void pieChartSetup() {
        pieChartData.put("Antarctica", 13661000.0f);
        pieChartData.put("Europa", 10498000.0f);
        pieChartData.put("Oceanië", 8468300.0f);
        pieChartData.put("Azië", 43608000.0f);
        pieChartData.put("Afrika", 30335000.0f);
        pieChartData.put("Noord-Amerika", 25349000.0f);
        pieChartData.put("Zuid-Amerika", 21069501.0f);

        int i = 0;
        angles = new float[pieChartData.size()];
        names = new String[pieChartData.size()];
        float total = 0f;
        for (Float val : pieChartData.values()) {
            total += val;
        }
        for (Map.Entry entry : pieChartData.entrySet()) {
            angles[i] = ((float) entry.getValue() / total) * 360;
            names[i] = (String) entry.getKey();
            i++;
        }
    }

    private void pieChart(float diameter, float[] data) {
        float lastAngle = 0;
        for (int i = 0; i < data.length; i++) {
            int v1 = random.nextInt(255);
            int v2 = random.nextInt(255);
            int v3 = random.nextInt(255);
            fill(v1, v2, v3);
            arc(width/3, (height / 7) * 5, diameter, diameter, lastAngle, lastAngle+radians(angles[i]));
            lastAngle += radians(angles[i]);
            drawLegend(names[i], new int[]{v1, v2, v3}, i);
        }
    }

    private void drawLegend(String text, int[] color, int base) {
        int legendHeight = ((height / 5) * 3) + (base * 30);
        fill(0);
        text(text, (width / 7) * 5, legendHeight);
        fill(color[0], color[1], color[2]);
        rect(((width / 7) * 5) - 18, legendHeight - 10, 10, 10);
    }

    private void barChart() {
        int baseHeight = (height / 5) * 2;
        for (int i = 0; i <= 200; i += 20) {
            int chartHeight = baseHeight - i;
            fill(0, 0, 0);
            text(i, 70, chartHeight);
            line(100, chartHeight, 535, chartHeight);
        }

        int margin = 100;
        int pos = 0;

        for (String key : barChartData.keySet()) {
            Integer current = barChartData.get(key);
            fill(0, 0, 205);
            rect(130 + (pos * margin), baseHeight - current, 70, current);
            fill(0, 0, 0);
            text(key, 135 + (pos * margin), baseHeight + 15);
            pos++;
        }
    }
}