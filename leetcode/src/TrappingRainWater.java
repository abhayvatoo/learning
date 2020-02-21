
public class TrappingRainWater {

	public static void main(String[] args) {
		int[] height = { 0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1 };

		System.out.println(bruteForceTrap(height));
		System.out.println(trap(height));

	}

	static public int bruteForceTrap(int[] height) {
		int ans = 0;

		for (int i = 0; i < height.length; i++) {
			int leftMax = 0;
			int rightMax = 0;

			int j = i;
			while (j >= 0) {
				leftMax = Math.max(leftMax, height[j]);
				j--;
			}

			int k = i;
			while (k < height.length) {
				rightMax = Math.max(rightMax, height[k]);
				k++;
			}

			ans += Math.min(leftMax, rightMax) - height[i];

		}

		return ans;
	}

	static public int trap(int[] height) {
		int ans = 0;

		for (int i = 0; i < height.length; i++) {
			int leftMax = 0;
			int rightMax = 0;

			int j = i;
			while (j >= 0) {
				leftMax = Math.max(leftMax, height[j]);
				j--;
			}

			int k = i;
			while (k < height.length) {
				rightMax = Math.max(rightMax, height[k]);
				k++;
			}

			ans += Math.min(leftMax, rightMax) - height[i];

		}

		return ans;
	}
}
