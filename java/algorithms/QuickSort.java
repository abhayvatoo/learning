
public class QuickSort {

	public static void main(String[] args) {
		int[] input = { 2, 5, 8, 4, 1, 3 };

		int[] sortedInput = doQuickSort(input);

		for (int i : sortedInput) {
			System.out.print(i + " ");
		}
	}

	private static int[] doQuickSort(int[] input) {
		doQuickSort(input, 0, input.length - 1);
		return input;
	}

	private static void doQuickSort(int[] input, int leftIndex, int rightIndex) {
		if (leftIndex >= rightIndex) {
			return;
		}
		
		int index = partition(input, leftIndex, rightIndex);
		doQuickSort(input, leftIndex, index - 1);
		doQuickSort(input, index + 1, rightIndex);
	}

	private static int partition(int[] input, int leftIndex, int rightIndex) {

		int pivotElement = input[(leftIndex + rightIndex) / 2];
		
		while (leftIndex <= rightIndex) {

			// left
			while (input[leftIndex] < pivotElement) {
				leftIndex++;
			}

			// right
			while (input[rightIndex] > pivotElement) {
				rightIndex--;
			}

			// swap
			if (leftIndex <= rightIndex) {
				int temp = input[leftIndex];
				input[leftIndex] = input[rightIndex];
				input[rightIndex] = temp;
				leftIndex++;
				rightIndex--;
			}
		}
		return leftIndex;
	}

}
