package com.aymaan.sortingvisualizer.sorting_visualizer.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public class SortingService {

    // Bubble Sort ✅
    public Map<String, Object> bubbleSort(int[] array) {
        long startTime = System.nanoTime();
        List<int[]> sortingSteps = new ArrayList<>();
        int[] arr = array.clone();
        int n = arr.length;

        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (arr[j] > arr[j + 1]) {
                    swap(arr, j, j + 1);
                    sortingSteps.add(arr.clone());
                }
            }
        }
        return createResult(sortingSteps, startTime);
    }

    // Selection Sort ✅
    public Map<String, Object> selectionSort(int[] array) {
        long startTime = System.nanoTime();
        List<int[]> sortingSteps = new ArrayList<>();
        int[] arr = array.clone();
        int n = arr.length;

        for (int i = 0; i < n - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < n; j++) {
                if (arr[j] < arr[minIndex]) {
                    minIndex = j;
                }
            }
            swap(arr, i, minIndex);
            sortingSteps.add(arr.clone());
        }

        return createResult(sortingSteps, startTime);
    }

    // Insertion Sort ✅
    public Map<String, Object> insertionSort(int[] array) {
        long startTime = System.nanoTime();
        List<int[]> sortingSteps = new ArrayList<>();
        int[] arr = array.clone();
        int n = arr.length;

        for (int i = 1; i < n; i++) {
            int key = arr[i];
            int j = i - 1;

            while (j >= 0 && arr[j] > key) {
                arr[j + 1] = arr[j];
                j--;
                sortingSteps.add(arr.clone());
            }
            arr[j + 1] = key;
            sortingSteps.add(arr.clone());
        }

        return createResult(sortingSteps, startTime);
    }

    // **Shell Sort ✅**
    public Map<String, Object> shellSort(int[] array) {
        long startTime = System.nanoTime();
        List<int[]> sortingSteps = new ArrayList<>();
        int[] arr = array.clone();
        int n = arr.length;

        for (int gap = n / 2; gap > 0; gap /= 2) {
            for (int i = gap; i < n; i++) {
                int temp = arr[i];
                int j = i;

                while (j >= gap && arr[j - gap] > temp) {
                    arr[j] = arr[j - gap];
                    j -= gap;
                    sortingSteps.add(arr.clone());
                }
                arr[j] = temp;
                sortingSteps.add(arr.clone());
            }
        }

        return createResult(sortingSteps, startTime);
    }

    // **Counting Sort ✅**
    public Map<String, Object> countingSort(int[] array) {
        long startTime = System.nanoTime();
        List<int[]> sortingSteps = new ArrayList<>();
        int[] arr = array.clone();
        int max = Arrays.stream(arr).max().orElse(0);
        int[] count = new int[max + 1];

        for (int num : arr) {
            count[num]++;
        }

        int index = 0;
        for (int i = 0; i <= max; i++) {
            while (count[i] > 0) {
                arr[index++] = i;
                count[i]--;
                sortingSteps.add(arr.clone());
            }
        }

        return createResult(sortingSteps, startTime);
    }

    // **Radix Sort ✅**
    public Map<String, Object> radixSort(int[] array) {
        long startTime = System.nanoTime();
        List<int[]> sortingSteps = new ArrayList<>();
        int[] arr = array.clone();
        int max = Arrays.stream(arr).max().orElse(0);

        for (int exp = 1; max / exp > 0; exp *= 10) {
            countingSortForRadix(arr, exp, sortingSteps);
        }

        return createResult(sortingSteps, startTime);
    }

    private void countingSortForRadix(int[] arr, int exp, List<int[]> sortingSteps) {
        int n = arr.length;
        int[] output = new int[n];
        int[] count = new int[10];

        for (int i = 0; i < n; i++) {
            count[(arr[i] / exp) % 10]++;
        }

        for (int i = 1; i < 10; i++) {
            count[i] += count[i - 1];
        }

        for (int i = n - 1; i >= 0; i--) {
            output[count[(arr[i] / exp) % 10] - 1] = arr[i];
            count[(arr[i] / exp) % 10]--;
        }

        System.arraycopy(output, 0, arr, 0, n);
        sortingSteps.add(arr.clone());
    }

    // **Bucket Sort ✅**
    public Map<String, Object> bucketSort(int[] array) {
        long startTime = System.nanoTime();
        List<int[]> sortingSteps = new ArrayList<>();
        int[] arr = array.clone();
        int max = Arrays.stream(arr).max().orElse(0);
        int bucketCount = (int) Math.sqrt(arr.length);
        List<Integer>[] buckets = new List[bucketCount];

        for (int i = 0; i < bucketCount; i++) {
            buckets[i] = new ArrayList<>();
        }

        for (int num : arr) {
            int index = (num * bucketCount) / (max + 1);
            buckets[index].add(num);
        }

        for (List<Integer> bucket : buckets) {
            Collections.sort(bucket);
        }

        int index = 0;
        for (List<Integer> bucket : buckets) {
            for (int num : bucket) {
                arr[index++] = num;
                sortingSteps.add(arr.clone());
            }
        }

        return createResult(sortingSteps, startTime);
    }

    // Helper Methods
    private void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    private Map<String, Object> createResult(List<int[]> sortingSteps, long startTime) {
        long endTime = System.nanoTime();
        double executionTimeMs = (endTime - startTime) / 1_000_000.0;
    
        Map<String, Object> result = new HashMap<>();
        result.put("steps", sortingSteps); // Ensure sortingSteps is not empty
        result.put("timeTaken", executionTimeMs);
        return result;
    }
    


    public Map<String, Object> quickSort(int[] array) {
        long startTime = System.nanoTime();
        List<int[]> sortingSteps = new ArrayList<>();
        int[] arr = array.clone();
        quickSortHelper(arr, 0, arr.length - 1, sortingSteps);
        return createResult(sortingSteps, startTime);
    }
    
    private void quickSortHelper(int[] arr, int low, int high, List<int[]> sortingSteps) {
        if (low < high) {
            int pi = partition(arr, low, high, sortingSteps);
            quickSortHelper(arr, low, pi - 1, sortingSteps);
            quickSortHelper(arr, pi + 1, high, sortingSteps);
        }
    }
    
    private int partition(int[] arr, int low, int high, List<int[]> sortingSteps) {
        int pivot = arr[high];
        int i = low - 1;
    
        for (int j = low; j < high; j++) {
            if (arr[j] < pivot) {
                swap(arr, ++i, j);
                sortingSteps.add(arr.clone());
            }
        }
        swap(arr, i + 1, high);
        sortingSteps.add(arr.clone());
    
        return i + 1;
    }

    public Map<String, Object> heapSort(int[] array) {
        long startTime = System.nanoTime();
        List<int[]> sortingSteps = new ArrayList<>();
        int[] arr = array.clone();
        int n = arr.length;
    
        for (int i = n / 2 - 1; i >= 0; i--) heapify(arr, n, i, sortingSteps);
        for (int i = n - 1; i > 0; i--) {
            swap(arr, 0, i);
            sortingSteps.add(arr.clone());
            heapify(arr, i, 0, sortingSteps);
        }
    
        return createResult(sortingSteps, startTime);
    }
    
    private void heapify(int[] arr, int n, int i, List<int[]> sortingSteps) {
        int largest = i, left = 2 * i + 1, right = 2 * i + 2;
        if (left < n && arr[left] > arr[largest]) largest = left;
        if (right < n && arr[right] > arr[largest]) largest = right;
        if (largest != i) {
            swap(arr, i, largest);
            sortingSteps.add(arr.clone());
            heapify(arr, n, largest, sortingSteps);
        }
    }

    public Map<String, Object> mergeSort(int[] array) {
        long startTime = System.nanoTime();
        List<int[]> sortingSteps = new ArrayList<>();
        int[] arr = array.clone();
        mergeSortHelper(arr, 0, arr.length - 1, sortingSteps);
        return createResult(sortingSteps, startTime);
    }
    
    private void mergeSortHelper(int[] arr, int left, int right, List<int[]> sortingSteps) {
        if (left < right) {
            int mid = left + (right - left) / 2;
            mergeSortHelper(arr, left, mid, sortingSteps);
            mergeSortHelper(arr, mid + 1, right, sortingSteps);
            merge(arr, left, mid, right, sortingSteps);
        }
    }
    
    private void merge(int[] arr, int left, int mid, int right, List<int[]> sortingSteps) {
        int[] temp = Arrays.copyOf(arr, arr.length);
        int i = left, j = mid + 1, k = left;
    
        while (i <= mid && j <= right) {
            if (temp[i] <= temp[j]) {
                arr[k++] = temp[i++];
            } else {
                arr[k++] = temp[j++];
            }
            sortingSteps.add(arr.clone());
        }
    
        while (i <= mid) arr[k++] = temp[i++];
        while (j <= right) arr[k++] = temp[j++];
    }
    
}
