import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GroupAnagrams {

	public static void main(String[] args) {
		String[] strs = { "eat", "tea", "tan", "ate", "nat", "bat" };

		List<List<String>> ans = groupAnagrams(strs);
		System.out.println(ans.toString());
	}

	public static List<List<String>> groupAnagrams(String[] strs) {
		List<List<String>> ans = new ArrayList<>();

		Map<String, List<String>> lookup = new HashMap<>();

		for (String str : strs) {

			// key
			char[] charArray = str.toCharArray();
			Arrays.sort(charArray);
			String key = new String(charArray);

			if (lookup.containsKey(key)) {
				lookup.get(key).add(str);
			} else {
				List<String> newStr = new ArrayList<String>();
				newStr.add(str);
				lookup.put(key, newStr);
			}
		}

		for (List<String> group : lookup.values()) {
			ans.add(group);
		}
		return ans;
	}

}
