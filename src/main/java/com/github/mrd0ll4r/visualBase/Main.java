package com.github.mrd0ll4r.visualBase;

import hype.H;
import hype.HColors;
import hype.HRect;
import processing.core.PApplet;
import processing.core.PGraphics;
import processing.opengl.PShader;

public class Main extends VisualBase {
    private static final int NUM_CAKE_MODES = 2;
    private static final float[] POSSIBLE_REPEAT_ANGLES = new float[]{1f, 2f, 3f, 4f, 5f, 10f, 15f, 20f, 30f, 36f, 45f, 60f, 90f, 180f};

    private PShader cakeShader;
    private PShader verticalOffsetShader;
    private int cakeMode = 0;
    private boolean cakeActive = false;
    private int repeatAngleIndex = 0;
    private HRect rect1;
    private GradientCircle circle;
    private GradientRing ring;

    private Main() {
        super(PApplet.P2D, Dimensions.A3Horizontal, 40, true, false, 1280);
    }

    @Override
    protected void init(PGraphics g) {
        cakeShader = loadShader("cake.glsl");
        verticalOffsetShader = loadShader("verticalStripeOffset.glsl");

        g.background(255);

        offScreen2.beginDraw();
        offScreen2.background(255);
        offScreen2.endDraw();

        rect1 = new HRect(100 * scaleFactor);
        rect1.rounding(10 * scaleFactor); // set corner rounding
        rect1.fill(0xffFF6600); // set fill color
        rect1.stroke(0xff000000, 150); // set stroke color and alpha
        rect1.strokeWeight(6 * scaleFactor); // set stroke weight
        rect1.anchorAt(H.CENTER); // set where anchor point is / key point for rotation and positioning
        rect1.rotation(45); // set rotation of the rect
        rect1.loc((onScreenDimensions.getWidth() / 2f + onScreenDimensions.getWidth() / 2f * sin(frameCount * 0.01f)) * scaleFactor,
                offScreenDimensions.getHeight() / 2f); // set x and y location
        H.add(rect1);

        H.autoClears(false);

        circle = new GradientCircle(
                HColors.merge(0, 240, 240, 240),
                HColors.merge(230, 240, 240, 240),
                200 * scaleFactor, 200);
        ring = new GradientRing(
                HColors.merge(230, 20, 20, 20),
                HColors.merge(0, 20, 20, 20),
                200 * scaleFactor, 220 * scaleFactor, 200);
    }

    @Override
    protected void doDraw(PGraphics g) {
        if (frameCount % 400 == 0)
            g.background(255);

        if (isSpacePressed())
            g.fill(noise(3.2f, 5.3f, frameCount * 0.14f) > 0.5 ? 255 : 0);
        else
            g.fill(
                    noise(0.2f, 0.3f, frameCount * 0.01f) * 255,
                    noise(0.01f, 0.01f, frameCount * 0.1f) * 255f,
                    noise(0.3f, 0.4f, frameCount * 0.02f) * 255f
            );
        g.noStroke();

        g.pushMatrix();
        {
            g.translate(g.width / 2f, g.height / 2f);

            g.pushMatrix();
            drawOffset(-300, -300, g);
            g.popMatrix();

            g.pushMatrix();
            drawOffset(0, 0, g);
            g.pushMatrix();
            drawOffset(200, 0, g);
            g.popMatrix();
            g.pushMatrix();
            drawOffset(-200, 0, g);
            g.popMatrix();
            g.popMatrix();

            g.pushMatrix();
            drawOffset(300, 300, g);
            g.popMatrix();
        }
        g.popMatrix();

        if (frameCount % 1 == 0) {
            g.pushMatrix();
            {
                g.translate(random(g.width), random(g.height));
                circle.drawOn(g);
            }
            g.popMatrix();

            g.pushMatrix();
            {
                g.translate(random(g.width), random(g.height));
                ring.drawOn(g);
            }
            g.popMatrix();
        }

        rect1.loc((onScreenDimensions.getWidth() / 2f + onScreenDimensions.getWidth() / 2f * sin(frameCount * 0.04f)) * scaleFactor,
                offScreenDimensions.getHeight() / 2f);

        H.drawStage();
    }

    @Override
    protected void doPost(PGraphics g) {
        cakeShader.set("u_resolution", (float) g.width, (float) g.height);
        cakeShader.set("u_mode", cakeMode);
        cakeShader.set("u_time", millis() / 1000f);
        cakeShader.set("u_repeatAngle", POSSIBLE_REPEAT_ANGLES[repeatAngleIndex]);
        cakeShader.set("u_bgColor", 0f, 0f, 0f, 1f);

        g.filter(verticalOffsetShader);

        if (cakeActive)
            g.filter(cakeShader);
    }

    private void drawOffset(float x, float y, PGraphics g) {
        g.translate(x * scaleFactor, y * scaleFactor);

        g.translate(200 * scaleFactor * sin(frameCount * 0.02f), 200 * scaleFactor * cos(frameCount * 0.02f));

        pushMatrix();
        g.rotate(frameCount * 0.13f);

        g.rect(100 * scaleFactor * sin(frameCount * 0.01f), 0, 100 * scaleFactor, 100 * scaleFactor);
        popMatrix();
    }

    @Override
    public void keyPressed() {
        super.keyPressed();
        switch (key) {
            case '2':
                if (isAltPressed())
                    cakeMode = (cakeMode + 1) % NUM_CAKE_MODES;
                else
                    cakeActive = !cakeActive;
                break;

            case 'e':
                repeatAngleIndex = (repeatAngleIndex + 1) % POSSIBLE_REPEAT_ANGLES.length;
                break;
        }
    }

    public static void main(String[] args) {
        new Main().run();
    }
}
