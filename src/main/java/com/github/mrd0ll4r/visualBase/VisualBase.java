package com.github.mrd0ll4r.visualBase;

import hype.H;
import hype.HColors;
import hype.extended.colorist.HColorPool;
import hype.extended.colorist.HGroupColorPool;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PGraphics;

public abstract class VisualBase extends PApplet {
    public static final int DPI = 720;
    public static final float[] paperDimensions = {11.7f, 16.54f};
    //public static final int[] offScreenDimensions = {11906, 8419};
    public static final int[] offScreenDimensions = {12000, 8500};
    public static final HColorPool standardColors = new HColorPool(0xffFFFFFF, 0xffF7F7F7, 0xffECECEC, 0xff333333, 0xff0095a8, 0xff00616f, 0xffFF3300, 0xffFF6600);
    public static final HGroupColorPool colorPools = new HGroupColorPool(
            new HColorPool(0xffFFFFFF, 0xffF7F7F7, 0xffECECEC, 0xff333333, 0xff0095a8, 0xff00616f, 0xffFF3300, 0xffFF6600),
            new HColorPool(0xffCCCCCC, 0xff333333, 0xff0095a8, 0xff00616f, 0xffFF3300, 0xffFF6600, 0xff008B60),
            new HColorPool(0xffCC0000, 0xffDD0000, 0xffEE0000, 0xffFF0000, 0xffFF3333, 0xffFF6666, 0xffFF8888, 0xffFFAAAA, 0xffFFBBBB),
            new HColorPool(0xff000000, 0xff555555, 0xff666666, 0xff777777, 0xff888888),
            new HColorPool(0xffDA4B61, 0xffF05246, 0xffE1AC57, 0xff6CAC90, 0xff377874),
            new HColorPool(0xffEA1830, 0xff5869FF, 0xffC61212, 0xffFF4C0F, 0xff7E8AE8),
            new HColorPool(0xff333333, 0xff5869FF, 0xffC61212, 0xffFF4C0F, 0xff7E8AE8),
            new HColorPool(0xff333333, 0xffE0E0E0, 0xffF0F0F0, 0xffFFFFFF, 0xff0000FF, 0xffFF0000),
            new HColorPool(0xff333333, 0xffE0E0E0, 0xffF0F0F0, 0xffFFFFFF, 0xff8569FF, 0xffC61212, 0xffFF4C0F, 0xff7E8AE8),
            new HColorPool(0xffFF6600, 0xffFF3300, 0xff00616F, 0xff0095A8, 0xff333333, 0xffECECEC, 0xffF7F7F7, 0xffFFFFFF),
            new HColorPool(0xff000000, 0xff555555, 0xff666666, 0xff777777, 0xff888888),
            new HColorPool(0xff111111, 0xff666666, 0xffFF6600, 0xff22AA22, 0xff3333DD, 0xffFF4444),
            new HColorPool(0xff808080, 0xff247bf4, 0xff469e38, 0xff43c9e8, 0xff888888, 0xfff2596b),
            new HColorPool(0xffdd4f75, 0xfff2712b, 0xfff49e24, 0xffce101d, 0xff9e24f4, 0xffc63d66),
            new HColorPool(0xff8a32c5, 0xffecb4bf, 0xffbfffe6, 0xffff10ec, 0xffaaaaaa, 0xffc63d66),
            new HColorPool(0xff8795e8, 0xff47d1d5, 0xffa7e4ae, 0xff94d0ff, 0xffff3dfb, 0xfff2596b),
            new HColorPool(0xffdd4f75, 0xfff2712b, 0xfff49e24, 0xff9e24f4, 0xffaaaaaa, 0xffc63d66),
            new HColorPool(0xf0606060, 0xff247bf4, 0xff469e38, 0xff888888, 0xffff3dfb, 0xfff2596b),
            new HColorPool(0xffff0000, 0xff00ff00, 0xff0000ff, 0xffffffff, 0xffaaaaaa, 0xff333333),
            new HColorPool(0xff333333, 0xffdd3333, 0xff33dd33, 0xff3333dd, 0xffaa3377, 0xff7733aa)
    );

    /*
    // Raw color pools
    {0xffFFFFFF,0xffF7F7F7,0xffECECEC,0xff333333,0xff0095a8,0xff00616f,0xffFF3300,0xffFF6600};
    {0xffCCCCCC,0xff333333,0xff0095a8,0xff00616f,0xffFF3300,0xffFF6600,0xff008B60};
    {0xffCC0000,0xffDD0000,0xffEE0000,0xffFF0000,0xffFF3333,0xffFF6666,0xffFF8888,0xffFFAAAA,0xffFFBBBB};
    {0xff000000,0xff555555,0xff666666,0xff777777,0xff888888};

    {0xffDA4B61,0xffF05246,0xffE1AC57,0xff6CAC90,0xff377874};
    {0xffEA1830,0xff5869FF,0xffC61212,0xffFF4C0F,0xff7E8AE8};
    {0xff333333,0xff5869FF,0xffC61212,0xffFF4C0F,0xff7E8AE8};
    {0xff333333,0xffE0E0E0,0xffF0F0F0,0xffFFFFFF,0xff0000FF,0xffFF0000};
    {0xff333333,0xffE0E0E0,0xffF0F0F0,0xffFFFFFF,0xff8569FF,0xffC61212,0xffFF4C0F,0xff7E8AE8};
    {0xffFF6600,0xffFF3300,0xff00616F,0xff0095A8,0xff333333,0xffECECEC,0xffF7F7F7,0xffFFFFFF};
    {0xff000000,0xff555555,0xff666666,0xff777777,0xff888888};
    {0xff111111,0xff666666,0xffFF6600,0xff22AA22,0xff3333DD,0xffFF4444};

    {0xff808080,0xff247bf4,0xff469e38,0xff43c9e8,0xff888888,0xfff2596b};
    {0xffdd4f75,0xfff2712b,0xfff49e24,0xffce101d,0xff9e24f4,0xffc63d66};
    {0xff8a32c5,0xffecb4bf,0xffbfffe6,0xffff10ec,0xffaaaaaa,0xffc63d66};
    {0xff8795e8,0xff47d1d5,0xffa7e4ae,0xff94d0ff,0xffff3dfb,0xfff2596b};
    {0xffdd4f75,0xfff2712b,0xfff49e24,0xff9e24f4,0xffaaaaaa,0xffc63d66};
    {0xf0606060,0xff247bf4,0xff469e38,0xff888888,0xffff3dfb,0xfff2596b};
    {0xffff0000,0xff00ff00,0xff0000ff,0xffffffff,0xffaaaaaa,0xff333333};
    {0xff333333,0xffdd3333,0xff33dd33,0xff3333dd,0xffaa3377,0xff7733aa};
     */

    private final int w;
    private final String renderer;

    private boolean controlPressed;
    private boolean shiftPressed;
    private boolean altPressed;
    private boolean spacePressed;

    private final boolean usePost;

    protected PGraphics offScreen;
    protected PGraphics offScreen2;
    protected final float scaleF;
    protected HColorPool colorPool = colorPools.getNextColorPool();

    private boolean doColorSetup = true;

    protected VisualBase(String renderer, boolean usePost, boolean doColorSetup, int w) {
        super();
        this.renderer = renderer;
        this.usePost = usePost;
        this.doColorSetup = doColorSetup;
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
        if (usePost) {
            offScreen2 = createGraphics(offScreenDimensions[0], offScreenDimensions[1], P2D);
            offScreen2.noSmooth();
        }

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
    private boolean hInited = false;

    protected int makeNoiseColor(int seed) {
        return HColors.merge(255,
                (int) (noise(0.2f, 0.3f, seed * 0.01f) * 255),
                (int) (noise(0.01f, 0.01f, seed * 0.1f) * 255f),
                (int) (noise(0.3f, 0.4f, seed * 0.02f) * 255f)
        );
    }

    @Override
    public void draw() {
        if (!hInited) {
            H.init(this);
            frameRate(30);
            hInited = true;
            colorPools.add(new HColorPool(
                    makeNoiseColor(1),
                    makeNoiseColor(2),
                    makeNoiseColor(3),
                    makeNoiseColor(4),
                    makeNoiseColor(5)
            ));
            colorPools.add(new HColorPool(
                    makeNoiseColor(10),
                    makeNoiseColor(20),
                    makeNoiseColor(30),
                    makeNoiseColor(40),
                    makeNoiseColor(50)
            ));
            colorPools.add(new HColorPool(
                    makeNoiseColor(100),
                    makeNoiseColor(200),
                    makeNoiseColor(300),
                    makeNoiseColor(400),
                    makeNoiseColor(500)
            ));
            colorPools.add(new HColorPool(
                    makeNoiseColor(1000),
                    makeNoiseColor(2000),
                    makeNoiseColor(3000),
                    makeNoiseColor(4000),
                    makeNoiseColor(5000)
            ));
            colorPools.add(new HColorPool(
                    makeNoiseColor(139),
                    makeNoiseColor(85),
                    makeNoiseColor(3158),
                    makeNoiseColor(815),
                    makeNoiseColor(91231)
            ));
            colorPools.add(new HColorPool(
                    makeNoiseColor(1001),
                    makeNoiseColor(1002),
                    makeNoiseColor(1003),
                    makeNoiseColor(1004),
                    makeNoiseColor(1005)
            ));
            colorPools.add(new HColorPool(
                    makeNoiseColor(2001),
                    makeNoiseColor(2002),
                    makeNoiseColor(2003),
                    makeNoiseColor(2004),
                    makeNoiseColor(2005)
            ));
            colorPools.add(new HColorPool(
                    makeNoiseColor(3001),
                    makeNoiseColor(3003),
                    makeNoiseColor(3005),
                    makeNoiseColor(3007),
                    makeNoiseColor(3009)
            ));
            colorPools.add(new HColorPool(
                    makeNoiseColor(4001),
                    makeNoiseColor(4003),
                    makeNoiseColor(4005),
                    makeNoiseColor(4007),
                    makeNoiseColor(4009)
            ));
            colorPools.add(new HColorPool(
                    makeNoiseColor(4000),
                    makeNoiseColor(4005),
                    makeNoiseColor(4010),
                    makeNoiseColor(4015),
                    makeNoiseColor(4020)
            ));
            colorPools.add(new HColorPool(
                    makeNoiseColor(4000),
                    makeNoiseColor(4008),
                    makeNoiseColor(4016),
                    makeNoiseColor(4024),
                    makeNoiseColor(4032)
            ));
        }

        if (doColorSetup) {
            background(255);
            translate(width / 2, height / 2);
            noStroke();
            for (int i = 0; i < colorPool.size(); i++) {
                fill(colorPool.getColorAt(i));
                rect((i - (int) (colorPool.size() / 2)) * 100f, -100f, 90f, 200f);
            }
            return;
        }

        if (firstRun) {
            initialize();
            firstRun = false;
        }

        if (captureNextFrame) {
            if (usePost)
                offScreen2.save(String.format("frame-%d.png", frameCount));
            else
                offScreen.save(String.format("frame-%d.png", frameCount));

            System.out.printf("exported to frame-%d.png%n", frameCount);
            captureNextFrame = false;
        }

        offScreen.beginDraw();
        doDraw(offScreen);
        offScreen.endDraw();

        if (usePost) {
            offScreen2.beginDraw();
            offScreen2.blendMode(PConstants.BLEND);
            offScreen2.image(offScreen, 0, 0);
            doPost(offScreen2);
            offScreen2.endDraw();

            image(offScreen2, 0, 0, width, height);
        } else {
            image(offScreen, 0, 0, width, height);
        }

        if (frameCount % 30 == 0) {
            System.out.printf("frameRate: %f%n", frameRate);
        }
    }

    protected abstract void doPost(PGraphics g);

    protected abstract void doDraw(PGraphics g);

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
            case 'n':
                colorPool = colorPools.getNextColorPool();
                break;
            case 'p':
                colorPool = colorPools.getPrevColorPool();
                break;
            case 'r':
                colorPool = colorPools.getRandomColorPool();
                break;
            case 'd':
                doColorSetup = false;
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
