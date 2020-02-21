import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;

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

	// my implementation
	/*
	 * x<y negative x appears first (what gale said)
	 * 
	 * 
	 */
	public static void main(String[] args) {
		int[][] points = { { 1, 0 }, { 0, 1 }, { 2, 2 }, { 1, -2 }, { 1, 2 } };

		int[][] ans = kClosestNew(points, 2);
		for (int[] xy : ans) {
			System.out.println("x: " + xy[0] + " y: " + xy[1]);
		}
	}

	public static int[][] kClosestNew(int[][] points, int K) {

		Queue<Point> pointsWithDistance = new PriorityQueue<>(new MyComparator());

		for (int i = 0; i < points.length; i++) {
			pointsWithDistance.add(new Point(dist(points[i]), points[i][0], points[i][1]));
		}

		int[][] ans = new int[K][2];

		for (int j = 0; j < K; j++) {
			int[] xy = new int[2];
			Point xyPoint = pointsWithDistance.remove();
			xy[0] = xyPoint.x;
			xy[1] = xyPoint.y;
			ans[j] = xy;
		}
		return ans;
	}

	static class Point {
		int distance;
		int x;
		int y;

		Point(int distance, int x, int y) {
			this.distance = distance;
			this.x = x;
			this.y = y;
		}
	}

	static class MyComparator implements Comparator<Point> {

		@Override
		public int compare(Point arg0, Point arg1) {
			if (arg0.distance < arg1.distance) {
				System.out.println("arg0 distance: " + arg0.distance + " is less than arg1 distance: " + arg1.distance);
				System.out.println("returning negative...");
				return -1;
			} else if (arg0.distance > arg1.distance) {
				System.out.println("arg0 distance: " + arg0.distance + " is more than arg1 distance: " + arg1.distance);
				System.out.println("returning positive...");
				return 1;
			}
			return 0;
		}

	}

}
