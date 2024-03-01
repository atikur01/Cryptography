public class HillCipher {
    private int[][] keyMatrix;
    private int[][] inverseKeyMatrix;
    private int modulus = 26; // Change this to 50 for Bangla
    private String alphabet = "abcdefghijklmnopqrstuvwxyz"; // Change this to the Bangla alphabet

    public HillCipher(int[][] keyMatrix) {
        this.keyMatrix = keyMatrix;
        this.inverseKeyMatrix = invertMatrix(keyMatrix, modulus);
    }

    public String encrypt(String plaintext) {
        int[] input = new int[plaintext.length()];
        for (int i = 0; i < plaintext.length(); i++) {
            input[i] = alphabet.indexOf(plaintext.charAt(i));
        }

        int[] encrypted = multiplyMatrixByVector(keyMatrix, input, modulus);
        StringBuilder ciphertext = new StringBuilder(encrypted.length);
        for (int i : encrypted) {
            ciphertext.append(alphabet.charAt(i));
        }

        return ciphertext.toString();
    }

    public String decrypt(String ciphertext) {
        int[] input = new int[ciphertext.length()];
        for (int i = 0; i < ciphertext.length(); i++) {
            input[i] = alphabet.indexOf(ciphertext.charAt(i));
        }

        int[] decrypted = multiplyMatrixByVector(inverseKeyMatrix, input, modulus);
        StringBuilder plaintext = new StringBuilder(decrypted.length);
        for (int i : decrypted) {
            plaintext.append(alphabet.charAt(i));
        }

        return plaintext.toString();
    }

    private int[][] invertMatrix(int[][] matrix, int modulus) {
        // Implement matrix inversion here, taking into account the modulus
        // This is a complex operation that is beyond the scope of this example
        return new int[][]{{}};
    }

    private int[] multiplyMatrixByVector(int[][] matrix, int[] vector, int modulus) {
        int[] result = new int[vector.length];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                result[i] += matrix[i][j] * vector[j];
            }
            result[i] %= modulus;
        }
        return result;
    }
}