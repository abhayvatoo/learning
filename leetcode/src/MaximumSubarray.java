public class MaximumSubarray {

	public static void main(String[] args) {
		int[] nums = { 8, -19, 5, -4, 20 };
		int ans = maxSubArray(nums);
		System.out.println(ans);
	}

	static int maxSubArray(int[] nums) {

		if (nums.length == 1) {
			return nums[0];
		}

		int maxSum = nums[0];
		int currSum = nums[0];

		int j = 1;

		while (j < nums.length) {
			currSum = Math.max(nums[j], currSum + nums[j]);
			maxSum = Math.max(maxSum, currSum);
			j += 1;

		}

		return maxSum;
	}

}
