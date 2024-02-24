class InsertionSort implements SortingAlgorithm {
    @Override
    public void sort(int[] arr, ArrayUpdater arrayUpdater) {
        int n = arr.length;
        for (int i = 1; i < n; ++i) {
            int key = arr[i];
            int j = i - 1;

            while (j >= 0 && arr[j] > key) {
                arr[j + 1] = arr[j];
                j = j - 1;
                arrayUpdater.updateArray(arr); // Update array in visualizer
            }
            arr[j + 1] = key;
        }
    }
}