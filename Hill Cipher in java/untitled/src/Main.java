public class Main {

    // Function to calculate the modular multiplicative inverse
    static int modInverse(int a, int m) {
        a = a % m;
        for (int x = -m; x < m; x++)
            if ((a * x) % m == 1)
                return x;
        return 1;
    }

    // Function to get the cofactor of a matrix
    static void getCofactor(int A[][], int temp[][], int p, int q, int n) {
        int i = 0, j = 0;
        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {
                if (row != p && col != q) {
                    // Exclude the specified row and column to get the cofactor matrix
                    temp[i][j++] = A[row][col];
                    if (j == n - 1) {
                        j = 0;
                        i++;
                    }
                }
            }
        }
    }

    // Function to calculate the determinant of a matrix
    static int determinant(int A[][], int n) {
        int D = 0;
        if (n == 1)
            return A[0][0];
        int temp[][] = new int[n][n];
        int sign = 1;
        for (int f = 0; f < n; f++) {
            // Calculate the cofactor and recursively compute the determinant
            getCofactor(A, temp, 0, f, n);
            D += sign * A[0][f] * determinant(temp, n - 1);
            sign = -sign; // Alternate the sign for each term in the expansion
        }
        return D;
    }

    // Function to calculate the adjoint of a matrix
    static void adjoint(int A[][], int adj[][], int N) {
        if (N == 1) {
            // Special case for a 1x1 matrix, where the adjoint is simply 1
            adj[0][0] = 1;
            return;
        }
        int sign = 1;
        int temp[][] = new int[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                // Calculate the cofactor matrix and transpose it to get the adjoint
                getCofactor(A, temp, i, j, N);
                sign = ((i + j) % 2 == 0) ? 1 : -1;
                adj[j][i] = (sign) * (determinant(temp, N - 1));
            }
        }
    }

    // Function to calculate the inverse of a matrix
    static boolean inverse(int A[][], int[][] inverse, int N) {
        int det = determinant(A, N);
        if (det == 0) {
            System.out.println("Inverse does not exist");
            return false;
        }
        int invDet = modInverse(det, 26);
        int adj[][] = new int[N][N];
        adjoint(A, adj, N);

        // Calculate the inverse matrix using the adjoint and the modular inverse of the determinant
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                inverse[i][j] = (adj[i][j] * invDet) % 26;
        return true;
    }

    public static void main(String[] args) {
        int n = 3; // size of the key matrix
        int[][] a = {{6, 24, 1}, {13, 16, 10}, {20, 17, 15}}; // key matrix
        int[][] inv = new int[n][n];

        if (inverse(a, inv, n)) {
            System.out.println("Inverse matrix exists");
        }

        String s = "atik";
        System.out.println("Original message : " + s);

        // Pad the message with 'x' to make its length a multiple of n
        int temp = (n - s.length() % n) % n;
        for (int i = 0; i < temp; i++) {
            s += 'x';
        }

        int k = 0;
        String encrypted = "";

        // Encrypt the message using the key matrix
        while (k < s.length()) {
            for (int i = 0; i < n; i++) {
                int sum = 0;
                int tempK = k;
                for (int j = 0; j < n; j++) {
                    // Multiply and sum each element of the key matrix with the corresponding letter in the message
                    sum += (a[i][j] % 26 * (s.charAt(tempK++) - 'a') % 26) % 26;
                    sum = sum % 26;
                }
                encrypted += (char) (sum + 'a');
            }
            k += n;
        }

        System.out.println("Encrypted message: " + encrypted);

        System.out.print("Decrypted message: ");
        s = encrypted;
        k = 0;
        String decrypted = "";

        // Decrypt the message using the inverse key matrix
        while (k < s.length()) {
            for (int i = 0; i < n; i++) {
                int sum = 0;
                int tempK = k;
                for (int j = 0; j < n; j++) {
                    // Multiply and sum each element of the inverse key matrix with the corresponding letter in the encrypted message
                    sum += ((inv[i][j] + 26) % 26 * (s.charAt(tempK++) - 'a') % 26) % 26;
                    sum = sum % 26;
                }
                decrypted += (char) (sum + 'a');
            }
            k += n;
        }

        // Remove the padded 'x' characters from the decrypted message
        int f = decrypted.length() - 1;
        while (decrypted.charAt(f) == 'x') {
            f--;
        }

        // Print the decrypted message
        for (int i = 0; i <= f; i++) {
            System.out.print(decrypted.charAt(i));
        }
        System.out.println();
    }
}
