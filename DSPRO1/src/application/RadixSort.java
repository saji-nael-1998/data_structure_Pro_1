package application;

public class RadixSort {

	public void sort(LinkedList array) {
		int maxValue = Integer.parseInt(((Student) array.get(0)).getStId());
		for (int x = 0; x < array.size(); x++) {
			int id = Integer.parseInt(((Student) array.get(x)).getStId());
			if (id > maxValue)
				maxValue = id;
		}

		for (int placeValue = 1; (maxValue / placeValue) > 0; placeValue *= 10) {
			countSort(array, placeValue);
		}

	}

	private void countSort(LinkedList array, int placeValue) {
		Student[] arr = new Student[array.size()];
		for (int x = 0; x < array.size(); x++) {
			arr[x] = (Student) array.get(x);
		}
		Student[] output = new Student[array.size()];
		int frequency[] = new int[10];
		for (int x = 0; x < array.size(); x++) {
			int id = Integer.parseInt(arr[x].getStId());
			int value = (id / placeValue) % 10;
			frequency[value]++;
		}
		for (int x = 1; x < 10; x++) {
			frequency[x] += frequency[x - 1];
		}

		for (int x = array.size() - 1; x >= 0; x--) {
			int id = Integer.parseInt(arr[x].getStId());
			int value = (id / placeValue) % 10;
			output[frequency[value] - 1] = arr[x];
			frequency[value]--;
		}
		for (int x = 0; x < array.size(); x++) {
			array.delete(x);
			array.insertAfter(x, output[x]);
		}

	}
}