package org.example;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class BMPToBinaryArray {

    public static int[][] getArray(String fileName) {
        try {
            File file = new File(fileName);
            BufferedImage image = ImageIO.read(file);

            if (image == null) {
                throw new IOException("Cannot load the image file");
            }

            int width = image.getWidth();
            int height = image.getHeight();

            int[][] binaryArray = new int[height][width];

            // Convert pixels to binary (1 for white, 0 for black)
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    int rgb = image.getRGB(x, y);
                    int brightness = (rgb >> 16) & 0xFF; // Red channel as brightness
                    binaryArray[y][x] = (brightness < 128) ? 0 : 1;
                }
            }

            return binaryArray;
        } catch (IOException e) {
            System.out.println("Error while reading the image: " + e.getMessage());
        }
        return null;
    }

    public static void saveSolution(String originalFile, String outputFile, List<State> path) {
        try {
            File file = new File(originalFile);
            BufferedImage image = ImageIO.read(file);

            if (image == null) {
                throw new IOException("Cannot load the image file");
            }

            // Create a new image with 24-bit depth and copy original pixels
            int width = image.getWidth();
            int height = image.getHeight();
            BufferedImage colorImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    int rgb = image.getRGB(x, y);
                    int brightness = (rgb >> 16) & 0xFF; // Use red channel for brightness
                    colorImage.setRGB(x, y, (brightness < 128) ? 0x000000 : 0xFFFFFF); // Black or white
                }
            }

            // Draw the path on the new image with red color
            for (State state : path) {
                if (state.x >= 0 && state.x < height && state.y >= 0 && state.y < width) {
                    colorImage.setRGB(state.y, state.x, 0xFF0000); // Set pixel to red
                }
            }

            // Save the modified image to a new file
            File output = new File(outputFile);
            ImageIO.write(colorImage, "bmp", output);

            System.out.println("Solved maze saved as: " + outputFile);
        } catch (IOException e) {
            System.out.println("Error while saving the solution: " + e.getMessage());
        }
    }
}
