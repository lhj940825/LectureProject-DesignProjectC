package DataStructure;
import java.util.Arrays;
import java.util.Collections;

public class PartialSort {

	private Researcher[] data;

	public PartialSort(Researcher[] lst) {
		data = lst;		
	}

	public void printArray(Researcher[] data) {
		for (int i = 0; i < data.length; i++) {
			System.out.print(data[i] + "\t");
		}
		System.out.print("\n");
	}

	private void swap(Researcher[] lst, int p, int q) {
		Researcher temp = lst[p];
		lst[p].setresearchCnt(lst[q].getresearchCnt());
		lst[q] = temp;
	}
	
	public int partition(Researcher[] lst, int start, int end) {
		Researcher x;
		x = lst[start];
		int i = start;
		for (int j = start + 1; j <= end; j++) {
			if (lst[j].getresearchCnt() > x.getresearchCnt() ) { // compares lst[j] greater than x
				i = i + 1;
				swap(lst, i, j);
			}
		}
		swap(lst, start, i);
		return i;
	}

	public void quickSort(Researcher[] lst, int start, int end) {
		if (start < end) {
			int index = partition(lst, start, end);
			quickSort(lst, start, index - 1);
			quickSort(lst, index + 1, end);
		}
	}

	public Researcher[] partialSortTopK(int k) {
		Researcher x;
		int index, rank;
		int start = 0;
		int end = data.length - 1;
		while (end > start) {
			x = data[start];
			index = partition(data, start, end);
			rank = index + 1;
			if (rank >= k) {
				end = index - 1;
			} else if ((index - start) > (end - index)) {
				quickSort(data, index + 1, end);
				end = index - 1;
			} else {
				quickSort(data, start, index - 1);
				start = index + 1;
			}
		}
		return data;
	}

/*
	public static void main(String[] args) {
		int n = 50;
		int k = 10;
		Double[] data = new Double[n];
				for (int i = 0; i < n; i++) {
			data[i] = i + 1.0;
		}
		Collections.shuffle(Arrays.asList(data));
		PartialSort<Double> partialSort = new PartialSort<Double>(data);
		System.out.print("Original Array: \n");
		partialSort.printArray(data);
		partialSort.partialSortTopK(k);

		System.out.print("Partially sorted Array: \n");
		for (int i = 0; i < data.length; i++) {
			System.out.print(data[i] + "\t");
		}

	}*/
}