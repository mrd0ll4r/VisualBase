package com.github.mrd0ll4r.visualBase;

import processing.core.PApplet;
import processing.core.PGraphics;

public class GradientCircle {
    private int innerFill;
    private int outerFill;
    private float radius;
    private int resolution;

    public GradientCircle(int innerFill, int outerFill, float radius, int resolution) {
        this.innerFill = innerFill;
        this.outerFill = outerFill;
        this.radius = radius;
        this.resolution = resolution;
    }

    public int getInnerFill() {
        return innerFill;
    }

    public void setInnerFill(int innerFill) {
        this.innerFill = innerFill;
    }

    public int getOuterFill() {
        return outerFill;
    }

    public void setOuterFill(int outerFill) {
        this.outerFill = outerFill;
    }

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }

    public int getResolution() {
        return resolution;
    }

    public void setResolution(int resolution) {
        this.resolution = resolution;
    }

    public void drawOn(PGraphics g) {
        g.noStroke();
        g.beginShape(PApplet.TRIANGLE_FAN);

        g.fill(innerFill);
        g.vertex(0, 0);

        g.fill(outerFill);
        for (int i = 0; i < resolution + 1; i++) {
            float angle = PApplet.TWO_PI * ((i * 1f) / resolution);
            g.vertex(PApplet.cos(angle) * radius, PApplet.sin(angle) * radius);
        }
        g.endShape();
    }
}
