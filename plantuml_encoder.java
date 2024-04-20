package com.izhxxx.utils;

import java.util.Base64;
import java.util.zip.Deflater;

/**
 * @author ：zhanghang(izhxxx@163.com)
 */
public class PlantUmlUtilTest {
    public static String encodePlantUml(String plantumlText) {
        // Encode text in UTF-8
        byte[] utf8Encoded = plantumlText.getBytes();
        // Compress using Deflate algorithm
        Deflater deflater = new Deflater();
        deflater.setInput(utf8Encoded);
        deflater.finish();
        byte[] compressed = new byte[utf8Encoded.length];
        int compressedLength = deflater.deflate(compressed);
        deflater.end();
        // Reencode in base64
        byte[] compressedData = new byte[compressedLength];
        System.arraycopy(compressed, 0, compressedData, 0, compressedLength);
        byte[] reencoded = Base64.getEncoder().encode(compressedData);

        String reencodedString = new String(reencoded);
        return translateBase64(reencodedString);
    }
    private static String translateBase64(String reencodedString) {
        // Define mapping arrays
        char[] base64Mapping = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".toCharArray();
        char[] plantUmlMapping = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz-_".toCharArray();

        StringBuilder translated = new StringBuilder();
        for (char c : reencodedString.toCharArray()) {
            // Find index of character in base64 mapping
            int index = indexOf(base64Mapping, c);
            // If character found in base64 mapping, replace it with corresponding character from PlantUML mapping
            if (index != -1) {
                translated.append(plantUmlMapping[index]);
            } else {
                // If character not found in base64 mapping, keep it as it is
                translated.append(c);
            }
        }
        return translated.toString();
    }

    // Helper method to find index of character in array
    private static int indexOf(char[] array, char c) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] == c) {
                return i;
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        // Example usage
        String plantUMLText = "@startuml\n" +
                "Alice -> Bob: Authentication Request\n" +
                "Bob --> Alice: Authentication Response\n" +
                "@enduml";
        String encodedPlantUML = encodePlantUml(plantUMLText);
        System.out.println(encodedPlantUML);
    }
}


