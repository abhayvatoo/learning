import java.util.HashMap;
import java.util.Map;

public class LengthOfLongestSubstring {

	public static void main(String[] args) {
		System.out.println(lengthOfLongestSubstring("bbbbb"));
	}

	public static int lengthOfLongestSubstring(String s) {

		int ans = 0;
		int temp = 0;

		Map<Character, Integer> charPos = new HashMap<Character, Integer>();

		for (int i = 0; i < s.length(); i++) {

			char current = s.charAt(i);
			if (charPos.containsKey(current)) {
				i = charPos.get(current);
				charPos.clear();
				temp = 0;
			} else {
				charPos.put(current, i);
				temp += 1;
				ans = Math.max(temp, ans);
			}
		}
		return ans;
	}

}
