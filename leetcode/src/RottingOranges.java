import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;

public class RottingOranges {
	int[] dr = new int[] { -1, 0, 1, 0 };
	int[] dc = new int[] { 0, -1, 0, 1 };

	class Co_ord {
		int row;
		int col;

		Co_ord(int r, int c) {
			this.row = r;
			this.col = c;
		}
	}

	public int orangesRotting(int[][] grid) {

		int R = grid.length, C = grid[0].length;

		// queue : all starting cells with rotten oranges
		Queue<Co_ord> queue = new ArrayDeque<>();
		Map<Co_ord, Integer> depth = new HashMap<>();

		for (int r = 0; r < R; ++r) {
			for (int c = 0; c < C; ++c) {
				if (grid[r][c] == 2) {
					Co_ord xy = new Co_ord(r, c);
					queue.add(xy);
					depth.put(xy, 0);
				}
			}
		}

		int ans = 0;
		while (!queue.isEmpty()) {
			Co_ord currentXY = queue.remove();
			int currentRow = currentXY.row, currentColumn = currentXY.col;
			for (int k = 0; k < 4; ++k) {
				int nr = currentRow + dr[k];
				int nc = currentColumn + dc[k];
				if (0 <= nr && nr < R && 0 <= nc && nc < C && grid[nr][nc] == 1) {
					grid[nr][nc] = 2;
					Co_ord newXY = new Co_ord(nr, nc);
					queue.add(newXY);
					depth.put(newXY, depth.get(currentXY) + 1);
					ans = depth.get(newXY);
				}
			}
		}

		for (int[] row : grid)
			for (int v : row)
				if (v == 1)
					return -1;
		return ans;

	}
}
