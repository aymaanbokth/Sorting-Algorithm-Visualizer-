import React, { useState, useEffect } from "react"; // <-- Add useEffect here
import axios from "axios";


const API_BASE_URL = "http://sorting-visualizer-api-env.eba-qx4pdxgg.us-east-1.elasticbeanstalk.com/"; // Spring Boot API URL

const algorithms = [
  "bubble-sort",
  "selection-sort",
  "insertion-sort",
  "merge-sort",
  "quick-sort",
  "heap-sort",
  "radix-sort",
  "counting-sort",
  "shell-sort",
  "bucket-sort",
];

  const algorithmDescriptions: { [key: string]: string } = {
    "bubble-sort": "Bubble Sort repeatedly swaps adjacent elements if they are in the wrong order.",
    "selection-sort": "Selection Sort selects the smallest element and places it in the correct position.",
    "insertion-sort": "Insertion Sort builds a sorted list by inserting elements at the correct position.",
    "merge-sort": "Merge Sort divides the array into halves and merges them in sorted order.",
    "quick-sort": "Quick Sort picks a pivot and partitions the array around the pivot.",
    "heap-sort": "Heap Sort converts the array into a heap structure and extracts elements in order.",
    "radix-sort": "Radix Sort sorts numbers by processing individual digits.",
    "counting-sort": "Counting Sort counts occurrences of elements to sort them efficiently.",
    "shell-sort": "Shell Sort improves insertion sort by comparing distant elements first.",
    "bucket-sort": "Bucket Sort distributes elements into buckets and sorts each bucket individually.",
  };


const App: React.FC = () => {
  const [selectedAlgorithm, setSelectedAlgorithm] = useState("bubble-sort");
  const generateArray = (size: number = 50) => {
    return Array.from({ length: size }, () => Math.floor(Math.random() * 100) + 1);
  };
  const [array, setArray] = useState<number[]>(generateArray());
  const [steps, setSteps] = useState<number[][]>([]);
  const [timeTaken, setTimeTaken] = useState<number | null>(null);
  const [currentStep, setCurrentStep] = useState(0);

  const [isPlaying, setIsPlaying] = useState(false);
  const [playInterval, setPlayInterval] = useState<NodeJS.Timeout | null>(null);
  const [speed, setSpeed] = useState(100); // Default speed
  const [darkMode, setDarkMode] = useState(false);




  const fetchSortingSteps = async () => {
    try {
      console.log("Fetching sorting steps...");
      const response = await axios.get(`${API_BASE_URL}/${selectedAlgorithm}`, {
        params: { numbers: array.join(",") },
      });
  
      console.log("API Response:", response.data); // Debugging log
  
      if (response.data.steps && response.data.steps.length > 0) {
        setSteps(response.data.steps);
        setCurrentStep(0);
      } else {
        console.warn("No steps received from API.");
        setSteps([]); // Prevent crashes if empty response
      }
  
      setTimeTaken(response.data.timeTaken || 0);
    } catch (error) {
      console.error("Error fetching sorting steps:", error);
      setSteps([]); // Reset steps in case of error
    }
  };
  

  const nextStep = () => {
    if (currentStep < steps.length - 1) {
      setCurrentStep(currentStep + 1);
    }
  };

  const playUntilEnd = () => {
    if (!isPlaying) {
      setIsPlaying(true);
      let index = currentStep;
  
      const interval = setInterval(() => {
        if (index < steps.length - 1) {
          setCurrentStep(++index);
        } else {
          clearInterval(interval);
          setIsPlaying(false);
        }
      }, 510 - speed); // Invert the speed logic (max value 500 -> min delay 10ms)
  
      setPlayInterval(interval);
    } else {
      if (playInterval) clearInterval(playInterval);
      setIsPlaying(false);
    }
  };
  
  
  // Automatically restart interval when speed changes
  useEffect(() => {
    if (isPlaying) {
      playUntilEnd();
    }
  }, [speed]);
  

  
  const stepBackward = () => {
    if (currentStep > 0) {
      setCurrentStep(currentStep - 1);
    }
  };

  const toggleDarkMode = () => {
    setDarkMode((prevMode) => !prevMode);
  };
  
  

  return (
    <div className={`flex flex-col items-center justify-center h-screen transition-colors duration-500 ${darkMode ? "bg-gray-900 text-white" : "bg-gray-100 text-black"}`}>
      <h1 className="text-3xl font-bold mb-4">Sorting Algorithm Visualizer</h1>
      
      {/* Algorithm Selection Dropdown */}
      <div className="mb-2">
        <label className="mr-2 font-semibold">Select Algorithm:</label>
        <select
          className={`p-2 border rounded transition-colors duration-300 ${
            darkMode ? "bg-gray-800 text-white border-gray-600" : "bg-white text-black border-gray-300"
          }`}
          value={selectedAlgorithm}
          onChange={(e) => setSelectedAlgorithm(e.target.value)}
        >
          {algorithms.map((algo) => (
            <option key={algo} value={algo}>
              {algo.replace("-", " ").toUpperCase()}
            </option>
          ))}
        </select>
      </div>

      {/* Algorithm Description Box - Modern Styling */}
      <div className={`mb-6 p-5 border rounded-2xl shadow-lg text-center max-w-xl transition-colors duration-500 ${darkMode ? "bg-gray-800 text-white border-gray-600" : "bg-blue-100 border-blue-300 text-black"}`}>
        <h2 className="text-lg font-semibold text-blue-600 mb-2">
          {selectedAlgorithm.replace("-", " ").toUpperCase()}
        </h2>
        <p className="text-gray-600 leading-relaxed">
          {algorithmDescriptions[selectedAlgorithm]}
        </p>
      </div>


      {/* Start Sorting Button */}
      <button
        className="bg-blue-500 text-white px-4 py-2 rounded hover:bg-blue-700"
        onClick={fetchSortingSteps}
      >
        Start Sorting
      </button>

      {/* Sorting Bars */}
      <div className="flex mt-6 space-x-1">
        {steps.length > 0 && steps[currentStep] ? (
          steps[currentStep].map((value, index) => {
            const isSwapped =
              currentStep > 0 &&
              steps[currentStep - 1] &&
              steps[currentStep - 1][index] !== value;

            return (
              <div
                key={index}
                className={`w-3 ${isSwapped ? "bg-red-500" : "bg-blue-500"}`}
                style={{ height: `${value * 6}px` }}
              ></div>
            );
          })
        ) : (
          <p className="text-gray-600 mt-4">No sorting data available. Click "Start Sorting".</p>
        )}
      </div>


    {/* Speed Control Slider */}
    <div className="mt-4">
      <label className="font-semibold mr-2">Speed:</label>
      <input
        type="range"
        min="10"
        max="500"
        value={speed}
        onChange={(e) => setSpeed(Number(e.target.value))}
        className="w-40"
      />
    </div>

    {/* Light/Dark Mode Toggle Button */}
    <button
      onClick={toggleDarkMode}
      className="absolute top-5 right-5 bg-gray-300 dark:bg-gray-800 text-black dark:text-white px-4 py-2 rounded-lg shadow-md transition-transform duration-300 hover:scale-110"
    >
      {darkMode ? "☀️ Light Mode" : "🌙 Dark Mode"}
    </button>


    {/* Buttons for controlling sorting */}
    <div className="mt-6 flex space-x-4">
      {/* Step Backward Button */}
      <button
        className="bg-gray-500 text-white px-4 py-2 rounded hover:bg-gray-700"
        onClick={stepBackward}
      >
        Step Backward
      </button>

      {/* Play/Pause Button */}
      <button
        className={`px-4 py-2 rounded text-white ${isPlaying ? "bg-red-500 hover:bg-red-700" : "bg-purple-500 hover:bg-purple-700"}`}
        onClick={playUntilEnd}
      >
        {isPlaying ? "Pause" : "Play Until End"}
      </button>

      {/* Step Forward Button */}
      <button
        className="bg-green-500 text-white px-4 py-2 rounded hover:bg-green-700"
        onClick={nextStep}
      >
        Step Forward
      </button>
    </div>

    {/* Execution Time Display */}
    {timeTaken !== null && (
      <p className="mt-4 text-gray-700">
        Backend Execution Time: <span className="font-semibold">{timeTaken.toFixed(2)} ms</span>
      </p>
    )}

    </div>
  );
};

export default App;
