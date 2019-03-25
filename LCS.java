import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Scanner;
import java.util.Set;

public class LCS {

	static int counter = 0;
	static Set<Character> chars = new LinkedHashSet<>();

	public LCS() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {

		String fs = null;
		String ss = null;

		Scanner sc = new Scanner(System.in);
		System.out.println("Enter first string: ");
//		String fs = sc.nextLine();
		System.out.println("Enter second string: ");
//		String ss = sc.nextLine();

		fs = "AGGTABOO";
		ss = "GXTXAYB";

//		fs = "gvinaynay";
//		ss = "gixnxaxynay";

		int l1 = fs.length();
		int l2 = ss.length();
		int cache[][] = new int[l1][l2];

		for (int[] row : cache)
			Arrays.fill(row, -1);

		System.out.println(fs);
		System.out.println(ss);
		int op = new LCS().getLCS(fs, ss, l1, l2, cache);
		System.out.println(op);
		System.out.println(counter);
		System.out.println(chars);
	}

	private int getLCS(String f, String s, int m, int n, int cache[][]) {
		counter += 1;
		if (m == 0 || n == 0)
			return 0;
		if (cache[m - 1][n - 1] != -1)
			return cache[m - 1][n - 1];
		else if (f.charAt(m - 1) == s.charAt(n - 1)) {
			cache[m - 1][n - 1] = 1 + getLCS(f, s, m - 1, n - 1, cache);
			chars.add(s.charAt(n - 1));
			return cache[m - 1][n - 1];
		} else {
			cache[m - 1][n - 1] = Math.max(getLCS(f, s, m - 1, n, cache), getLCS(f, s, m, n - 1, cache));
			return cache[m - 1][n - 1];
		}
	}

}
