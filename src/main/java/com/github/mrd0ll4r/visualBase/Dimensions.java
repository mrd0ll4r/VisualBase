package com.github.mrd0ll4r.visualBase;

public final class Dimensions {
    private final int width;
    private final int height;

    public static final Dimensions A3Horizontal = new Dimensions(12000, 8500);
    public static final Dimensions A3Vertical = new Dimensions(8500, 12000);
    public static final Dimensions A3Square = new Dimensions(8500, 8500);

    public static final Dimensions A3PreciseHorizontal = new Dimensions(11908, 8420);
    public static final Dimensions A3PreciseVertical = new Dimensions(8420, 11908);
    public static final Dimensions A3PreciseSquare = new Dimensions(8420, 8420);

    public static final Dimensions A3BinaryHorizontal = new Dimensions(11585, 8192);
    public static final Dimensions A3BinaryVertical = new Dimensions(8192, 11585);
    public static final Dimensions A3BinarySquare = new Dimensions(8192, 8192);

    public static final Dimensions InstagramHorizontal = new Dimensions(1800, 945);
    public static final Dimensions InstagramVertical = new Dimensions(864, 1080);
    public static final Dimensions InstagramSquare = new Dimensions(1080, 1080);
    public static final Dimensions InstagramStory = new Dimensions(1080, 1920);

    private Dimensions(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public static Dimensions Custom(int width, int height) {
        return new Dimensions(width, height);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
