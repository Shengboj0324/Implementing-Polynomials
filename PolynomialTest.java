public class PolynomialTest {
    static int pass = 0, total = 0;

    public static void main(String[] args) {
        testComplex(); testConstruction(); testAdd(); testSub(); testMult(); testDiv(); testEval(); testEdge();
        System.out.println("\n" + (pass == total ? "✓ All " : "✗ ") + pass + "/" + total + " tests passed");
    }

    static void testComplex() {
        Complex a = new Complex(1, 2), b = new Complex(3, 4);
        test("C+", a.add(b).equals(new Complex(4, 6)));
        test("C-", a.subtract(b).equals(new Complex(-2, -2)));
        test("C*", a.mult(b).equals(new Complex(-5, 10)));
        test("C/", b.div(a).equals(new Complex(2.2, -0.4)));
    }

    static void testConstruction() {
        Complex[] c = {new Complex(1,2), new Complex(3,4), new Complex(-1,0), new Complex(0,-1.1)};
        test("P construct", new Polynomial(c).getDegree() == 3);
        test("P trim zeros", new Polynomial(new Complex[]{c[0], c[1], new Complex(0,0)}).getDegree() == 1);
    }

    static void testAdd() {
        Complex[] c = {new Complex(1,2), new Complex(3,4), new Complex(-1,0), new Complex(0,-1.1)};
        Polynomial p = new Polynomial(c);
        Complex[] e = {new Complex(2,4), new Complex(6,8), new Complex(-2,0), new Complex(0,-2.2)};
        test("P+P", p.add(p).equals(new Polynomial(e)));
    }

    static void testSub() {
        Polynomial p = new Polynomial(new Complex[]{new Complex(5,0), new Complex(3,0), new Complex(1,0)});
        test("P-P=0", p.subtract(p).isZero());
    }

    static void testMult() {
        Polynomial p = new Polynomial(new Complex[]{new Complex(1,0), new Complex(1,0)});
        Complex[] e = {new Complex(1,0), new Complex(2,0), new Complex(1,0)};
        test("(x+1)²", p.mult(p).equals(new Polynomial(e)));
    }

    static void testDiv() {
        Polynomial p1 = new Polynomial(new Complex[]{new Complex(2,0), new Complex(3,0), new Complex(1,0)});
        Polynomial p2 = new Polynomial(new Complex[]{new Complex(1,0), new Complex(1,0)});
        Polynomial[] r = p1.div(p2);
        test("P/P quotient", r[0].equals(new Polynomial(new Complex[]{new Complex(2,0), new Complex(1,0)})));
        test("P/P remainder", r[1].isZero());
    }

    static void testEval() {
        Polynomial p = new Polynomial(new Complex[]{new Complex(1,0), new Complex(2,0), new Complex(1,0)});
        test("P.eval(2)", p.eval(new Complex(2,0)).equals(new Complex(9,0)));
        Polynomial p2 = new Polynomial(new Complex[]{new Complex(1,0), new Complex(0,0), new Complex(1,0)});
        test("P.eval(i)", p2.eval(new Complex(0,1)).equals(new Complex(0,0)));
    }

    static void testEdge() {
        Polynomial z = new Polynomial(new Complex[]{new Complex(0,0)});
        Polynomial p = new Polynomial(new Complex[]{new Complex(1,0), new Complex(2,0)});
        test("Zero", z.isZero());
        test("0+P=P", z.add(p).equals(p));
        test("P*0=0", p.mult(z).isZero());
    }

    static void test(String name, boolean ok) {
        total++;
        if (ok) { pass++; System.out.println("✓ " + name); }
        else System.out.println("✗ " + name);
    }
}

