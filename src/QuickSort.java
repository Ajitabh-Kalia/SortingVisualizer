class QuickSort implements SortingAlgorithm {
    @Override
    public void sort(int[] arr, ArrayUpdater arrayUpdater) {
        quickSort(arr, 0, arr.length - 1, arrayUpdater);
    }

    private int partition(int[] arr, int low, int high, ArrayUpdater arrayUpdater) {
        int pivot = arr[high];
        int i = (low - 1);
        for (int j = low; j < high; j++) {
            if (arr[j] < pivot) {
                i++;
                int temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
                arrayUpdater.updateArray(arr); // Update array in visualizer
            }
        }
        int temp = arr[i + 1];
        arr[i + 1] = arr[high];
        arr[high] = temp;
        arrayUpdater.updateArray(arr); // Update array in visualizer
        return i + 1;
    }

    private void quickSort(int[] arr, int low, int high, ArrayUpdater arrayUpdater) {
        if (low < high) {
            int pi = partition(arr, low, high, arrayUpdater);

            quickSort(arr, low, pi - 1, arrayUpdater);
            quickSort(arr, pi + 1, high, arrayUpdater);
        }
    }
}