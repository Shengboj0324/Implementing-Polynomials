import java.util.Random;

/**
 * Performance analysis for polynomial operations.
 * Conducts empirical testing and validates asymptotic complexity.
 */
public class PerformanceAnalysis {
    private static final Random random = new Random(42);
    
    public static void main(String[] args) {
        System.out.println("Polynomial Operations Performance Analysis");
        System.out.println("=" .repeat(70));
        System.out.println();
        
        analyzeAddition();
        analyzeMultiplication();
        analyzeDivision();
        analyzeEvaluation();
        
        System.out.println("\nPerformance analysis complete.");
    }
    
    /**
     * Analyzes addition performance.
     * Expected: O(max(n, m)) time complexity
     */
    private static void analyzeAddition() {
        System.out.println("ADDITION PERFORMANCE ANALYSIS");
        System.out.println("-".repeat(70));
        System.out.println("Expected Time Complexity: O(max(n, m))");
        System.out.println("Expected Space Complexity: O(max(n, m))");
        System.out.println();
        
        int[] sizes = {100, 200, 400, 800, 1600, 3200};
        System.out.printf("%-10s %-15s %-15s %-15s%n", "Size", "Time (ms)", "Time/n (μs)", "Ratio");
        System.out.println("-".repeat(70));
        
        long prevTime = 0;
        for (int size : sizes) {
            Polynomial p1 = generateRandomPolynomial(size);
            Polynomial p2 = generateRandomPolynomial(size);
            
            // Warm up
            for (int i = 0; i < 10; i++) {
                p1.add(p2);
            }
            
            // Measure
            long startTime = System.nanoTime();
            int iterations = 1000;
            for (int i = 0; i < iterations; i++) {
                p1.add(p2);
            }
            long endTime = System.nanoTime();
            long avgTime = (endTime - startTime) / iterations;
            
            double timeMs = avgTime / 1_000_000.0;
            double timePerN = avgTime / (double) size / 1000.0; // microseconds
            double ratio = prevTime > 0 ? (double) avgTime / prevTime : 0;
            
            System.out.printf("%-10d %-15.6f %-15.6f %-15.2f%n", 
                size, timeMs, timePerN, ratio);
            
            prevTime = avgTime;
        }
        
        System.out.println("\nAnalysis: Ratio should be approximately 2.0 for O(n) complexity.");
        System.out.println("Time/n should remain relatively constant.\n");
    }
    
    /**
     * Analyzes multiplication performance.
     * Expected: O(n * m) time complexity using naive algorithm
     */
    private static void analyzeMultiplication() {
        System.out.println("MULTIPLICATION PERFORMANCE ANALYSIS");
        System.out.println("-".repeat(70));
        System.out.println("Expected Time Complexity: O(n * m) [Naive Algorithm]");
        System.out.println("Expected Space Complexity: O(n + m)");
        System.out.println();
        
        int[] sizes = {50, 100, 200, 400, 800};
        System.out.printf("%-10s %-15s %-15s %-15s%n", "Size", "Time (ms)", "Time/n² (ns)", "Ratio");
        System.out.println("-".repeat(70));
        
        long prevTime = 0;
        for (int size : sizes) {
            Polynomial p1 = generateRandomPolynomial(size);
            Polynomial p2 = generateRandomPolynomial(size);
            
            // Warm up
            for (int i = 0; i < 5; i++) {
                p1.mult(p2);
            }
            
            // Measure
            long startTime = System.nanoTime();
            int iterations = 100;
            for (int i = 0; i < iterations; i++) {
                p1.mult(p2);
            }
            long endTime = System.nanoTime();
            long avgTime = (endTime - startTime) / iterations;
            
            double timeMs = avgTime / 1_000_000.0;
            double timePerN2 = avgTime / (double) (size * size); // nanoseconds
            double ratio = prevTime > 0 ? (double) avgTime / prevTime : 0;
            
            System.out.printf("%-10d %-15.6f %-15.6f %-15.2f%n", 
                size, timeMs, timePerN2, ratio);
            
            prevTime = avgTime;
        }
        
        System.out.println("\nAnalysis: Ratio should be approximately 4.0 for O(n²) complexity.");
        System.out.println("Time/n² should remain relatively constant.");
        System.out.println("Note: FFT-based multiplication could achieve O(n log n) but adds complexity.\n");
    }
    
    /**
     * Analyzes division performance.
     * Expected: O((n - m + 1) * m) time complexity
     */
    private static void analyzeDivision() {
        System.out.println("DIVISION PERFORMANCE ANALYSIS");
        System.out.println("-".repeat(70));
        System.out.println("Expected Time Complexity: O((n - m + 1) * m)");
        System.out.println("Expected Space Complexity: O(n)");
        System.out.println();
        
        int[] dividendSizes = {100, 200, 400, 800, 1600};
        int divisorSize = 50;
        
        System.out.printf("%-15s %-15s %-15s %-15s%n", 
            "Dividend Size", "Time (ms)", "Time/n (μs)", "Ratio");
        System.out.println("-".repeat(70));
        
        long prevTime = 0;
        for (int size : dividendSizes) {
            Polynomial dividend = generateRandomPolynomial(size);
            Polynomial divisor = generateRandomPolynomial(divisorSize);
            
            // Warm up
            for (int i = 0; i < 5; i++) {
                dividend.div(divisor);
            }
            
            // Measure
            long startTime = System.nanoTime();
            int iterations = 100;
            for (int i = 0; i < iterations; i++) {
                dividend.div(divisor);
            }
            long endTime = System.nanoTime();
            long avgTime = (endTime - startTime) / iterations;
            
            double timeMs = avgTime / 1_000_000.0;
            double timePerN = avgTime / (double) size / 1000.0; // microseconds
            double ratio = prevTime > 0 ? (double) avgTime / prevTime : 0;
            
            System.out.printf("%-15d %-15.6f %-15.6f %-15.2f%n", 
                size, timeMs, timePerN, ratio);
            
            prevTime = avgTime;
        }
        
        System.out.println("\nAnalysis: For fixed divisor size m, complexity is O(n).");
        System.out.println("Ratio should be approximately 2.0 when doubling dividend size.\n");
    }
    
    /**
     * Analyzes evaluation performance using Horner's method.
     * Expected: O(n) time complexity
     */
    private static void analyzeEvaluation() {
        System.out.println("EVALUATION PERFORMANCE ANALYSIS");
        System.out.println("-".repeat(70));
        System.out.println("Expected Time Complexity: O(n) [Horner's Method]");
        System.out.println("Expected Space Complexity: O(1)");
        System.out.println();
        
        int[] sizes = {100, 200, 400, 800, 1600, 3200, 6400};
        Complex evalPoint = new Complex(1.5, 0.5);
        
        System.out.printf("%-10s %-15s %-15s %-15s%n", "Size", "Time (μs)", "Time/n (ns)", "Ratio");
        System.out.println("-".repeat(70));
        
        long prevTime = 0;
        for (int size : sizes) {
            Polynomial p = generateRandomPolynomial(size);
            
            // Warm up
            for (int i = 0; i < 100; i++) {
                p.eval(evalPoint);
            }
            
            // Measure
            long startTime = System.nanoTime();
            int iterations = 10000;
            for (int i = 0; i < iterations; i++) {
                p.eval(evalPoint);
            }
            long endTime = System.nanoTime();
            long avgTime = (endTime - startTime) / iterations;
            
            double timeUs = avgTime / 1000.0;
            double timePerN = avgTime / (double) size; // nanoseconds
            double ratio = prevTime > 0 ? (double) avgTime / prevTime : 0;
            
            System.out.printf("%-10d %-15.6f %-15.6f %-15.2f%n", 
                size, timeUs, timePerN, ratio);
            
            prevTime = avgTime;
        }
        
        System.out.println("\nAnalysis: Ratio should be approximately 2.0 for O(n) complexity.");
        System.out.println("Time/n should remain relatively constant.");
        System.out.println("Horner's method is optimal for polynomial evaluation.\n");
    }
    
    /**
     * Generates a random polynomial of given degree with complex coefficients.
     */
    private static Polynomial generateRandomPolynomial(int degree) {
        Complex[] coefs = new Complex[degree + 1];
        for (int i = 0; i <= degree; i++) {
            double real = random.nextDouble() * 10 - 5; // Range [-5, 5]
            double imag = random.nextDouble() * 10 - 5;
            coefs[i] = new Complex(real, imag);
        }
        // Ensure leading coefficient is non-zero
        if (degree > 0) {
            coefs[degree] = new Complex(random.nextDouble() * 5 + 1, random.nextDouble() * 5);
        }
        return new Polynomial(coefs);
    }
}

