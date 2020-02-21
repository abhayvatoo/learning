
public class LongestPalindrome {

	public static void main(String[] args) {
		String ans = longestPalindrome("babad");
		System.out.println(ans);
	}

	static String longestPalindrome(String s) {

		String ans = "";
		for (int i = 0; i < s.length(); i++) {
			String s1 = expandWindow(s, i, i);
			String s2 = expandWindow(s, i, i + 1);

			int l1 = s1.length();
			int l2 = s2.length();

			if (l1 >= l2 && l1 >= ans.length()) {
				ans = s1;
			} else if (l2 > ans.length()) {
				ans = s2;
			}
		}
		return ans;

	}

	private static String expandWindow(String s, int index1, int index2) {
		while (index1 >= 0 && index2 < s.length() && s.charAt(index1) == s.charAt(index2)) {
			index1--;
			index2++;
		}
		return (s.substring(index1 + 1, index2));
	}
}
