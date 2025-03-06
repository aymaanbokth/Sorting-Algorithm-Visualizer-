package com.aymaan.sortingvisualizer.sorting_visualizer;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.aymaan.sortingvisualizer.sorting_visualizer.service.SortingService;

@CrossOrigin(origins = "*") // Allow all origins
@RestController  // Marks this class as a REST controller
@RequestMapping("/api")  // Base path for API endpoints
public class SortingController {

    private final SortingService sortingService;

    @GetMapping("/")
    public String home() {
        return "Sorting Visualizer API is running!";
    }

    public SortingController(SortingService sortingService) {
        this.sortingService = sortingService;
    }

    @GetMapping("/bubble-sort")
    public Map<String, Object> getBubbleSort(@RequestParam List<Integer> numbers) {
        int[] array = numbers.stream().mapToInt(i -> i).toArray();
        return sortingService.bubbleSort(array);
    }

    @GetMapping("/selection-sort")
    public Map<String, Object> getSelectionSort(@RequestParam List<Integer> numbers) {
        int[] array = numbers.stream().mapToInt(i -> i).toArray();
        return sortingService.selectionSort(array);
    }

    @GetMapping("/insertion-sort")
    public Map<String, Object> getInsertionSort(@RequestParam List<Integer> numbers) {
        int[] array = numbers.stream().mapToInt(i -> i).toArray();
        return sortingService.insertionSort(array);
    }

    @GetMapping("/merge-sort")
    public Map<String, Object> getMergeSort(@RequestParam List<Integer> numbers) {
        int[] array = numbers.stream().mapToInt(i -> i).toArray();
        return sortingService.mergeSort(array);
    }

    @GetMapping("/quick-sort")
    public Map<String, Object> getQuickSort(@RequestParam List<Integer> numbers) {
        int[] array = numbers.stream().mapToInt(i -> i).toArray();
        return sortingService.quickSort(array);
    }

    @GetMapping("/heap-sort")
    public Map<String, Object> getHeapSort(@RequestParam List<Integer> numbers) {
        int[] array = numbers.stream().mapToInt(i -> i).toArray();
        return sortingService.heapSort(array);
    }

    @GetMapping("/radix-sort")
    public Map<String, Object> getRadixSort(@RequestParam List<Integer> numbers) {
        int[] array = numbers.stream().mapToInt(i -> i).toArray();
        return sortingService.radixSort(array);
    }

    @GetMapping("/counting-sort")
    public Map<String, Object> getCountingSort(@RequestParam List<Integer> numbers) {
        int[] array = numbers.stream().mapToInt(i -> i).toArray();
        return sortingService.countingSort(array);
    }

    @GetMapping("/shell-sort")
    public Map<String, Object> getShellSort(@RequestParam List<Integer> numbers) {
        int[] array = numbers.stream().mapToInt(i -> i).toArray();
        return sortingService.shellSort(array);
    }

    @GetMapping("/bucket-sort")
    public Map<String, Object> getBucketSort(@RequestParam List<Integer> numbers) {
        int[] array = numbers.stream().mapToInt(i -> i).toArray();
        return sortingService.bucketSort(array);
    }
}
