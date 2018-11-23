package com.github.mrd0ll4r.visualBase;

import hype.H;
import hype.HColors;
import hype.extended.colorist.HColorPool;
import hype.extended.colorist.HGroupColorPool;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PGraphics;

import java.util.Arrays;

public abstract class VisualBase extends PApplet {
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

    private final String renderer;
    private final int targetFPS;
    protected final Dimensions offScreenDimensions;
    protected final Dimensions onScreenDimensions;
    private final boolean usePost;
    protected final float scaleFactor;

    private boolean controlPressed;
    private boolean shiftPressed;
    private boolean altPressed;
    private boolean spacePressed;

    private boolean captureNextFrame = false;
    private boolean captureNextFrameLowRes = false;
    private boolean recordLowRes = false;
    private boolean recordHighRes = false;
    private int recordStartLowRes = 0;
    private int recordStartHighRes = 0;

    protected PGraphics offScreen;
    protected PGraphics offScreen2;
    protected HColorPool colorPool = colorPools.getNextColorPool();

    private boolean doColorSetup = true;

    protected VisualBase(String renderer, Dimensions offScreenDimensions, int targetFPS, boolean usePost, boolean doColorSetup, int onScreenWidth) {
        super();
        this.renderer = renderer;
        this.offScreenDimensions = offScreenDimensions;
        this.targetFPS = targetFPS;
        this.usePost = usePost;
        this.doColorSetup = doColorSetup;
        this.scaleFactor = (float) offScreenDimensions.getWidth() / onScreenWidth;
        this.onScreenDimensions = Dimensions.Custom(onScreenWidth, (int) (offScreenDimensions.getHeight() / scaleFactor));
    }

    @Override
    public void settings() {
        super.settings();
        size(this.onScreenDimensions.getWidth(), this.onScreenDimensions.getHeight(), P2D);
    }

    private void initialize() {
        offScreen.beginDraw();
        init(offScreen);
        offScreen.endDraw();
    }

    protected void init(PGraphics g) {
    }

    protected void run() {
        String[] args = {this.getClass().getName()};
        PApplet.runSketch(args, this);
    }

    protected int makeNoiseColor(int seed) {
        return HColors.merge(255,
                (int) (noise(0.2f, 0.3f, seed * 0.01f) * 255),
                (int) (noise(0.01f, 0.01f, seed * 0.1f) * 255f),
                (int) (noise(0.3f, 0.4f, seed * 0.02f) * 255f)
        );
    }

    protected int millisFromFrameCount() {
        return (int) ((1000f / targetFPS) * frameCount);
    }

    protected float cnoise(float x) {
        return noise(x / offScreenDimensions.getWidth());
    }

    protected float cnoise(float x, float y) {
        return noise(x / offScreenDimensions.getWidth(), y / offScreenDimensions.getHeight());
    }

    protected float cnoise(float x, float y, float t) {
        return noise(x / offScreenDimensions.getWidth(), y / offScreenDimensions.getHeight(), t);
    }

    private void addNoiseColor(int... seeds) {
        colorPools.add(new HColorPool(
                Arrays.stream(seeds).map(this::makeNoiseColor).toArray()
        ));
    }

    private void addNoiseColors() {
        addNoiseColor(1, 2, 3, 4, 5);
        addNoiseColor(10, 20, 30, 40, 50);
        addNoiseColor(100, 200, 300, 400, 500);
        addNoiseColor(1000, 2000, 3000, 4000, 5000);
        addNoiseColor(139, 85, 3158, 815, 91231);
        addNoiseColor(1001, 1002, 1003, 1004, 1005);
        addNoiseColor(2001, 2002, 2003, 2004, 2005);
        addNoiseColor(3001, 3003, 3005, 3007, 3009);
        addNoiseColor(4001, 4003, 4005, 4007, 4009);
        addNoiseColor(4000, 4005, 4010, 4015, 4020);
        addNoiseColor(4000, 4008, 4016, 4024, 4032);
    }

    private boolean firstRun = true;
    private boolean hInited = false;
    private boolean offScreenInitialized = false;

    private void initializeOffScreen() {
        offScreen = createGraphics(offScreenDimensions.getWidth(), offScreenDimensions.getHeight(), this.renderer);
        offScreen.smooth(8);
        offScreen.hint(PConstants.ENABLE_ASYNC_SAVEFRAME);
        if (usePost) {
            offScreen2 = createGraphics(offScreenDimensions.getWidth(), offScreenDimensions.getHeight(), P2D);
            offScreen2.noSmooth();
            offScreen2.hint(PConstants.ENABLE_ASYNC_SAVEFRAME);
        }
    }

    @Override
    public void draw() {
        if (!offScreenInitialized) {
            initializeOffScreen();
            frameRate(targetFPS);
            offScreenInitialized = true;

            return;
        }

        if (doColorSetup) {
            background(255);
            translate(width / 2f, height / 2f);
            noStroke();
            for (int i = 0; i < colorPool.size(); i++) {
                fill(colorPool.getColorAt(i));
                stroke(50);
                rect((i - colorPool.size() / 2f) * onScreenDimensions.getWidth() / 10f, -1f * onScreenDimensions.getWidth() / 10f,
                        onScreenDimensions.getWidth() / 11f, onScreenDimensions.getWidth() / 5f);
            }
            return;
        }

        if (!hInited) {
            PGraphics oldG = this.g;
            this.width = this.offScreenDimensions.getWidth();
            this.height = this.offScreenDimensions.getHeight();
            offScreen.beginDraw();
            this.g = offScreen;

            H.init(this);

            this.g = oldG;
            offScreen.endDraw();
            this.width = this.onScreenDimensions.getWidth();
            this.height = this.onScreenDimensions.getHeight();

            addNoiseColors();

            hInited = true;
        }

        if (firstRun) {
            initialize();
            firstRun = false;
        }

        if (recordHighRes) {
            String p = String.format("records/high/%d/frame-%d.png", recordStartHighRes, frameCount);
            if (usePost)
                offScreen2.save(p);
            else
                offScreen.save(p);

            int numFrames = frameCount - recordStartHighRes;
            if (numFrames % (targetFPS / 2) == 0) {
                System.out.printf("recorded %.2fs of high-res frames%n", numFrames / (targetFPS * 1f));
            }
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

        if (recordLowRes) {
            String p = String.format("records/low/%d/frame-%d.png", recordStartLowRes, frameCount);
            save(p);

            int numFrames = frameCount - recordStartLowRes;
            if (numFrames % (targetFPS / 2) == 0) {
                System.out.printf("recorded %.2fs of low-res frames%n", numFrames / (targetFPS * 1f));
            }
        }

        if (captureNextFrameLowRes) {
            save(String.format("frame-%d-lowres.png", frameCount));

            System.out.printf("exported to frame-%d-lowres.png%n", frameCount);
            captureNextFrameLowRes = false;
        }

        if (frameCount % targetFPS == 0) {
            System.out.printf("frameRate: %f%n", frameRate);
        }
    }

    protected abstract void doPost(PGraphics g);

    protected abstract void doDraw(PGraphics g);

    protected void onColorPoolChange() {
    }

    ;

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
            case 'x':
                captureNextFrameLowRes = true;
                break;
            case 'n':
                colorPool = colorPools.getNextColorPool();
                onColorPoolChange();
                break;
            case 'p':
                colorPool = colorPools.getPrevColorPool();
                onColorPoolChange();
                break;
            case 'r':
                colorPool = colorPools.getRandomColorPool();
                onColorPoolChange();
                break;
            case 'd':
                doColorSetup = false;
                break;
            case 'q':
                if (recordLowRes) {
                    System.out.printf("done recording, recorded %d frames, that's approx %.2fs.%n", frameCount - recordStartLowRes, (frameCount - recordStartLowRes) / (targetFPS * 1f));
                } else {
                    recordStartLowRes = frameCount;
                    System.out.printf("beginning recording to records/low/%d/%n", frameCount);
                }
                recordLowRes = !recordLowRes;
                break;
            case 'w':
                if (recordHighRes) {
                    System.out.printf("done recording, recorded %d frames, that's approx %.2fs.%n", frameCount - recordStartHighRes, (frameCount - recordStartHighRes) / (targetFPS * 1f));
                } else {
                    recordStartHighRes = frameCount;
                    System.out.printf("beginning recording to records/high/%d/.%n", frameCount);
                }
                recordHighRes = !recordHighRes;
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
