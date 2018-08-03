package com.github.mrd0ll4r.visualBase;

import processing.core.PApplet;
import processing.core.PGraphics;
import processing.opengl.PShader;

public class Main {
    public static void main(String[] args) {
        new Main().doStuff();
    }

    private void doStuff() {
        VisualBase vis = new VisualBase(PApplet.P2D, 1280) {

            PShader cakeShader;
            PShader verticalOffsetShader;
            int cakeMode = 0;
            final int numCakeModes = 2;
            boolean cakeActive = false;
            int repeatAngleIndex = 0;
            final float[] possibleRepeatAngles = new float[]{1f, 2f, 3f, 4f, 5f, 10f, 15f, 20f, 30f, 36f, 45f, 60f, 90f, 180f};

            @Override
            protected void init(PGraphics g) {
                cakeShader = loadShader("cake.glsl");
                verticalOffsetShader = loadShader("verticalStripeOffset.glsl");

                g.background(255);

                offScreen2.beginDraw();
                offScreen2.background(255);
                offScreen2.endDraw();
            }

            @Override
            void doDraw(PGraphics g) {

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

                if (frameCount % 30 == 0) {
                    System.out.printf("frameRate: %f%n", frameRate);
                }
            }

            @Override
            void doPost(PGraphics g) {
                cakeShader.set("u_resolution", (float) g.width, (float) g.height);
                cakeShader.set("u_mode", cakeMode);
                cakeShader.set("u_time", millis() / 1000f);
                cakeShader.set("u_repeatAngle", possibleRepeatAngles[repeatAngleIndex]);
                cakeShader.set("u_bgColor", 0f, 0f, 0f, 1f);

                g.filter(verticalOffsetShader);

                if (cakeActive)
                    g.filter(cakeShader);
            }

            void drawOffset(float x, float y, PGraphics g) {
                g.translate(x * scaleF, y * scaleF);

                g.translate(200 * scaleF * sin(frameCount * 0.02f), 200 * scaleF * cos(frameCount * 0.02f));

                pushMatrix();
                g.rotate(frameCount * 0.13f);

                g.rect(100 * scaleF * sin(frameCount * 0.01f), 0, 100 * scaleF, 100 * scaleF);
                popMatrix();
            }

            @Override
            public void keyPressed() {
                super.keyPressed();
                switch (key) {
                    case '2':
                        if (isAltPressed())
                            cakeMode = (cakeMode + 1) % numCakeModes;
                        else
                            cakeActive = !cakeActive;
                        break;

                    case 'e':
                        repeatAngleIndex = (repeatAngleIndex + 1) % possibleRepeatAngles.length;
                        break;
                }
            }
        };


        vis.run();
    }
}
