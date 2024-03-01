public class Main {

    static int modInverse(int a, int m) {
        a = a % m;
        for (int x = 1; x < m; x++)
            if ((a * x) % m == 1)
                return x;
        return 1;
    }

    static void getCofactor(int A[][], int temp[][], int p, int q, int n) {
        int i = 0, j = 0;
        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {
                if (row != p && col != q) {
                    temp[i][j++] = A[row][col];
                    if (j == n - 1) {
                        j = 0;
                        i++;
                    }
                }
            }
        }
    }

    static int determinant(int A[][], int n) {
        int D = 0;
        if (n == 1)
            return A[0][0];
        int temp[][] = new int[n][n];
        int sign = 1;
        for (int f = 0; f < n; f++) {
            getCofactor(A, temp, 0, f, n);
            D += sign * A[0][f] * determinant(temp, n - 1);
            sign = -sign;
        }
        return D;
    }

    static void adjoint(int A[][], int adj[][], int N) {
        if (N == 1) {
            adj[0][0] = 1;
            return;
        }
        int sign = 1;
        int temp[][] = new int[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                getCofactor(A, temp, i, j, N);
                sign = ((i + j) % 2 == 0) ? 1 : -1;
                adj[j][i] = (sign) * (determinant(temp, N - 1));
            }
        }
    }

    static boolean inverse(int A[][], int[][] inverse, int N) {
        int det = determinant(A, N);
        if (det == 0) {
            System.out.println("Inverse does not exist");
            return false;
        }
        int invDet = modInverse(det, 50); // Modulo 50 for Bengali
        int adj[][] = new int[N][N];
        adjoint(A, adj, N);

        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                inverse[i][j] = (adj[i][j] * invDet) % 50;
        return true;
    }

    public static void main(String[] args) {
        int n = 3; // size of the key matrix
        int[][] a = {{6, 24, 1}, {13, 16, 10}, {20, 17, 15}}; // key matrix
        int[][] inv = new int[n][n];

        if (inverse(a, inv, n)) {
            System.out.println("Inverse matrix exists");
        }

        String s = "কলম";
        System.out.println("Original message : " + s);

        int temp = (n - s.length() % n) % n;
        for (int i = 0; i < temp; i++) {
            s += 'অ';
        }

        int k = 0;
        StringBuilder encrypted = new StringBuilder();

        while (k < s.length()) {
            for (int i = 0; i < n; i++) {
                int sum = 0;
                int tempK = k;
                for (int j = 0; j < n; j++) {
                    sum += (a[i][j] % 50 * (s.charAt(tempK++) - 'অ') % 50) % 50;
                    sum = sum % 50;
                }
                encrypted.append((char) (sum + 'অ'));
            }
            k += n;
        }

        System.out.println("Encrypted message: " + encrypted);

        System.out.print("Decrypted message: ");
        s = encrypted.toString();
        k = 0;
        StringBuilder decrypted = new StringBuilder();

        while (k < s.length()) {
            for (int i = 0; i < n; i++) {
                int sum = 0;
                int tempK = k;
                for (int j = 0; j < n; j++) {
                    sum += ((inv[i][j] + 50) % 50 * (s.charAt(tempK++) - 'অ') % 50) % 50;
                    sum = sum % 50;
                }
                decrypted.append((char) (sum + 'অ'));
            }
            k += n;
        }

        int f = decrypted.length() - 1;
        while (decrypted.charAt(f) == 'অ') {
            f--;
        }

        for (int i = 0; i <= f; i++) {
            System.out.print(decrypted.charAt(i));
        }
        System.out.println();
    }
}
