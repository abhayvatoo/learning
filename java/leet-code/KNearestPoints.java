import java.util.Arrays;

public class KNearestPoints {

	public static int[][] kClosest(int[][] points, int K) {
		int N = points.length;
		int[] dists = new int[N];
		for (int i = 0; i < N; ++i)
			dists[i] = dist(points[i]);

		Arrays.sort(dists);
		int distK = dists[K - 1];

		int[][] ans = new int[K][2];
		int t = 0;
		for (int i = 0; i < N; ++i)
			if (dist(points[i]) <= distK)
				ans[t++] = points[i];
		return ans;
	}

	public static int dist(int[] point) {
		return point[0] * point[0] + point[1] * point[1];
	}

	public static void main(String[] args) {
		int[][] points = { { 1, 0 }, { 0, 1 } };

		int[][] ans = kClosest(points, 2);
		for (int[] xy : ans) {
			System.out.println("x: " + xy[0] + " y: " + xy[1]);
		}
	}

}
