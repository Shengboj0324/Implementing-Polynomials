import java.util.Random;

public class PerformanceAnalysis {
    static Random rand = new Random(42);

    public static void main(String[] args) {
        System.out.println("Polynomial Performance Analysis\n" + "=".repeat(70) + "\n");
        analyze("ADDITION", new int[]{100,200,400,800,1600,3200}, 1000, "O(n)", (p1,p2)->p1.add(p2));
        analyze("MULTIPLICATION", new int[]{50,100,200,400,800}, 100, "O(n²)", (p1,p2)->p1.mult(p2));
        analyzeDivision();
        analyzeEval();
        System.out.println("\nAnalysis complete.");
    }

    static void analyze(String name, int[] sizes, int iter, String complexity, Op op) {
        System.out.println(name + " - Expected: " + complexity);
        System.out.printf("%-10s %-15s %-15s%n", "Size", "Time (ms)", "Ratio");
        System.out.println("-".repeat(50));
        long prev = 0;
        for (int s : sizes) {
            Polynomial p1 = genPoly(s), p2 = genPoly(s);
            for (int i = 0; i < 5; i++) op.run(p1, p2); // warmup
            long t = System.nanoTime();
            for (int i = 0; i < iter; i++) op.run(p1, p2);
            long avg = (System.nanoTime() - t) / iter;
            System.out.printf("%-10d %-15.6f %-15.2f%n", s, avg/1e6, prev>0 ? (double)avg/prev : 0);
            prev = avg;
        }
        System.out.println();
    }

    static void analyzeDivision() {
        System.out.println("DIVISION - Expected: O((n-m+1)*m)");
        System.out.printf("%-15s %-15s %-15s%n", "Dividend Size", "Time (ms)", "Ratio");
        System.out.println("-".repeat(50));
        long prev = 0;
        int divisorSize = 50;
        for (int s : new int[]{100,200,400,800,1600}) {
            Polynomial dividend = genPoly(s), divisor = genPoly(divisorSize);
            for (int i = 0; i < 5; i++) dividend.div(divisor);
            long t = System.nanoTime();
            for (int i = 0; i < 100; i++) dividend.div(divisor);
            long avg = (System.nanoTime() - t) / 100;
            System.out.printf("%-15d %-15.6f %-15.2f%n", s, avg/1e6, prev>0 ? (double)avg/prev : 0);
            prev = avg;
        }
        System.out.println();
    }

    static void analyzeEval() {
        System.out.println("EVALUATION - Expected: O(n) [Horner's Method]");
        System.out.printf("%-10s %-15s %-15s%n", "Size", "Time (μs)", "Ratio");
        System.out.println("-".repeat(50));
        Complex x = new Complex(1.5, 0.5);
        long prev = 0;
        for (int s : new int[]{100,200,400,800,1600,3200,6400}) {
            Polynomial p = genPoly(s);
            for (int i = 0; i < 100; i++) p.eval(x);
            long t = System.nanoTime();
            for (int i = 0; i < 10000; i++) p.eval(x);
            long avg = (System.nanoTime() - t) / 10000;
            System.out.printf("%-10d %-15.6f %-15.2f%n", s, avg/1e3, prev>0 ? (double)avg/prev : 0);
            prev = avg;
        }
        System.out.println();
    }

    static Polynomial genPoly(int deg) {
        Complex[] c = new Complex[deg + 1];
        for (int i = 0; i <= deg; i++) c[i] = new Complex(rand.nextDouble()*10-5, rand.nextDouble()*10-5);
        if (deg > 0) c[deg] = new Complex(rand.nextDouble()*5+1, rand.nextDouble()*5);
        return new Polynomial(c);
    }

    interface Op { Polynomial run(Polynomial p1, Polynomial p2); }
}

