class MergeSort implements SortingAlgorithm {
    @Override
    public void sort(int[] arr, ArrayUpdater arrayUpdater) {
        mergeSort(arr, 0, arr.length - 1, arrayUpdater);
    }

    private void merge(int[] arr, int l, int m, int r, ArrayUpdater arrayUpdater) {
        int n1 = m - l + 1;
        int n2 = r - m;

        int[] L = new int[n1];
        int[] R = new int[n2];

        System.arraycopy(arr, l, L, 0, n1);
        System.arraycopy(arr, m + 1, R, 0, n2);

        int i = 0, j = 0;
        int k = l;
        while (i < n1 && j < n2) {
            if (L[i] <= R[j]) {
                arr[k] = L[i];
                i++;
            } else {
                arr[k] = R[j];
                j++;
            }
            arrayUpdater.updateArray(arr); // Update array in visualizer
            k++;
        }

        while (i < n1) {
            arr[k] = L[i];
            i++;
            k++;
            arrayUpdater.updateArray(arr); // Update array in visualizer
        }

        while (j < n2) {
            arr[k] = R[j];
            j++;
            k++;
            arrayUpdater.updateArray(arr); // Update array in visualizer
        }
    }

    private void mergeSort(int[] arr, int l, int r, ArrayUpdater arrayUpdater) {
        if (l < r) {
            int m = l + (r - l) / 2;

            mergeSort(arr, l, m, arrayUpdater);
            mergeSort(arr, m + 1, r, arrayUpdater);

            merge(arr, l, m, r, arrayUpdater);
        }
    }
}