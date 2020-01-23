import java.util.HashMap;
import java.util.Map;

public class TwoSum {

	public static void main(String[] args) {
		int[] nums = { 2, 7, 1, 15 };
		int target = 9;

		int[] ans = twoSum(nums, target);
		System.out.println(ans[0] + " " + ans[1]);
	}

	public static int[] twoSum(int[] nums, int target) {
		int[] ans = new int[2];

		Map<Integer, Integer> alreadySeen = new HashMap<>();

		for (int i = 0; i < nums.length; i++) {
			int num = nums[i];

			if (alreadySeen.containsKey(num)) {
				ans[0] = alreadySeen.get(num);
				ans[1] = i;
				break;
			} else {
				int complement = target - num;
				alreadySeen.put(complement, i);
			}

		}
		return ans;
	}
}
