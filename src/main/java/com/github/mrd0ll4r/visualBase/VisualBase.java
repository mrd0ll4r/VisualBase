package com.github.mrd0ll4r.visualBase;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PGraphics;

public abstract class VisualBase extends PApplet {
    public static final int DPI = 720;
    public static final float[] paperDimensions = {11.7f, 16.54f};
    //public static final int[] offScreenDimensions = {11906, 8419};
    public static final int[] offScreenDimensions = {12000, 8500};

    private final int w;
    private final String renderer;

    private boolean controlPressed;
    private boolean shiftPressed;
    private boolean altPressed;
    private boolean spacePressed;

    protected PGraphics offScreen;
    protected PGraphics offScreen2;
    protected final float scaleF;


    protected VisualBase(String renderer, int w) {
        super();
        this.renderer = renderer;
        scaleF = (float) offScreenDimensions[0] / w;
        this.w = w;
    }

    @Override
    public void settings() {
        super.settings();
        size(this.w, (int) (offScreenDimensions[1] / scaleF), P2D);
    }

    private void initialize() {
        offScreen = createGraphics(offScreenDimensions[0], offScreenDimensions[1], this.renderer);
        offScreen.smooth(8);
        offScreen2 = createGraphics(offScreenDimensions[0], offScreenDimensions[1], P2D);
        offScreen2.noSmooth();
        frameRate(30);

        offScreen.beginDraw();
        init(offScreen);
        offScreen.endDraw();
    }

    protected void init(PGraphics g) {
    }

    public void run() {
        String[] args = {this.getClass().getName()};
        PApplet.runSketch(args, this);
    }

    private boolean firstRun = true;
    private boolean captureNextFrame = false;

    @Override
    public void draw() {
        if (firstRun) {
            initialize();
            firstRun = false;
        }

        if (captureNextFrame) {
            offScreen2.save(String.format("frame-%d.png", frameCount));
            System.out.printf("exported to frame-%d.png%n",frameCount);
            captureNextFrame = false;
        }

        offScreen.beginDraw();
        doDraw(offScreen);
        offScreen.endDraw();

        offScreen2.beginDraw();
        offScreen2.blendMode(PConstants.BLEND);
        offScreen2.image(offScreen, 0, 0);
        doPost(offScreen2);
        offScreen2.endDraw();

        image(offScreen2, 0, 0, width, height);
    }

    abstract void doPost(PGraphics g);

    abstract void doDraw(PGraphics g);

    @Override
    public void keyPressed() {
        super.keyPressed();
        switch (key) {
            case CODED:
                switch (keyCode) {
                    case CONTROL:
                        controlPressed = !controlPressed;
                        break;
                    case ALT:
                        altPressed = true;
                        break;
                    case SHIFT:
                        shiftPressed = !shiftPressed;
                        break;
                }
                break;
            case ' ':
                spacePressed = true;
                break;
            case 'c':
                captureNextFrame = true;
                break;
        }
    }

    @Override
    public void keyReleased() {
        super.keyReleased();
        switch (key) {
            case CODED:
                switch (keyCode) {
                    case ALT:
                        System.out.println("alt released");
                        altPressed = false;
                        break;
                }
                break;
            case ' ':
                spacePressed = false;
                break;
        }
    }

    protected boolean isControlPressed() {
        return controlPressed;
    }

    protected boolean isShiftPressed() {
        return shiftPressed;
    }

    protected boolean isAltPressed() {
        return altPressed;
    }

    protected boolean isSpacePressed() {
        return spacePressed;
    }
}
