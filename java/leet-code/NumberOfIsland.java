
public class NumberOfIsland {

	int row;
	int column;

	void dfs(char[][] grid, int r, int c) {

		if (r < 0 || c < 0 || r >= row || c >= column || grid[r][c] == '0') {
			return;
		}

		grid[r][c] = '0';
		dfs(grid, r - 1, c);
		dfs(grid, r + 1, c);
		dfs(grid, r, c - 1);
		dfs(grid, r, c + 1);
	}

	public int numIslands(char[][] grid) {
		if (grid == null || grid.length == 0) {
			return 0;
		}

		row = grid.length;
		column = grid[0].length;

		int numOfIsland = 0;
		for (int currentRow = 0; currentRow < row; ++currentRow) {
			for (int currentColumn = 0; currentColumn < column; ++currentColumn) {
				if (grid[currentRow][currentColumn] == '1') {
					numOfIsland++;
					dfs(grid, currentRow, currentColumn);
				}
			}
		}

		return numOfIsland;
	}
}