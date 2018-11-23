package com.github.mrd0ll4r.visualBase;

import processing.core.PApplet;
import processing.core.PGraphics;

public class GradientRing {
    private int innerFill;
    private int outerFill;
    private float innerRadius;
    private float outerRadius;
    private int resolution;

    public GradientRing(int innerFill, int outerFill, float innerRadius, float outerRadius, int resolution) {
        this.innerFill = innerFill;
        this.outerFill = outerFill;
        this.innerRadius = innerRadius;
        this.outerRadius = outerRadius;
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

    public float getInnerRadius() {
        return innerRadius;
    }

    public void setInnerRadius(float innerRadius) {
        this.innerRadius = innerRadius;
    }

    public float getOuterRadius() {
        return outerRadius;
    }

    public void setOuterRadius(float outerRadius) {
        this.outerRadius = outerRadius;
    }

    public int getResolution() {
        return resolution;
    }

    public void setResolution(int resolution) {
        this.resolution = resolution;
    }

    public void drawOn(PGraphics g) {
        g.noStroke();
        g.beginShape(PApplet.TRIANGLE_STRIP);

        for (int i = 0; i < resolution + 1; i++) {
            float angle = PApplet.TWO_PI * ((i * 1f) / resolution);

            g.fill(innerFill);
            g.vertex(PApplet.cos(angle) * innerRadius, PApplet.sin(angle) * innerRadius);

            g.fill(outerFill);
            g.vertex(PApplet.cos(angle) * outerRadius, PApplet.sin(angle) * outerRadius);
        }
        g.endShape();
    }
}
