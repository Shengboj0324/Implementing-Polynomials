// Polynomial: coefs[i] = coefficient of x^i
public class Polynomial {
    private Complex[] coefficients;
    private int degree;
    public Polynomial(Complex[] coefs) {
        if (coefs == null || coefs.length == 0) { coefficients = new Complex[]{new Complex(0,0)}; degree = 0; }
        else {
            int d = coefs.length - 1;
            while (d > 0 && coefs[d].isZero()) d--;
            degree = d;
            coefficients = new Complex[d + 1];
            System.arraycopy(coefs, 0, coefficients, 0, d + 1);
        }
    }
    public int getDegree() { return degree; }
    // O(max(n,m))
    public Polynomial add(Polynomial o) {
        int m = Math.max(degree, o.degree);
        Complex[] r = new Complex[m + 1];
        for (int i = 0; i <= m; i++)
            r[i] = (i <= degree ? coefficients[i] : new Complex(0,0)).add(i <= o.degree ? o.coefficients[i] : new Complex(0,0));
        return new Polynomial(r);
    }
    // O(max(n,m))
    public Polynomial subtract(Polynomial o) {
        int m = Math.max(degree, o.degree);
        Complex[] r = new Complex[m + 1];
        for (int i = 0; i <= m; i++)
            r[i] = (i <= degree ? coefficients[i] : new Complex(0,0)).subtract(i <= o.degree ? o.coefficients[i] : new Complex(0,0));
        return new Polynomial(r);
    }

    // O(n*m)
    public Polynomial mult(Polynomial o) {
        int d = degree + o.degree;
        Complex[] r = new Complex[d + 1];
        for (int i = 0; i <= d; i++) r[i] = new Complex(0,0);
        for (int i = 0; i <= degree; i++)
            for (int j = 0; j <= o.degree; j++)
                r[i+j] = r[i+j].add(coefficients[i].mult(o.coefficients[j]));
        return new Polynomial(r);
    }

    // O((n-m+1)*m) - polynomial long division
    public Polynomial[] div(Polynomial d) {
        if (d.isZero()) throw new ArithmeticException("Division by zero");
        if (degree < d.degree) return new Polynomial[]{new Polynomial(new Complex[]{new Complex(0,0)}), new Polynomial(coefficients)};
        Complex[] rem = new Complex[coefficients.length];
        System.arraycopy(coefficients, 0, rem, 0, coefficients.length);
        int qd = degree - d.degree;
        Complex[] q = new Complex[qd + 1];
        for (int i = 0; i <= qd; i++) q[i] = new Complex(0,0);
        Complex lead = d.coefficients[d.degree];
        for (int i = degree; i >= d.degree; i--) {
            if (!rem[i].isZero()) {
                Complex qt = rem[i].div(lead);
                q[i - d.degree] = qt;
                for (int j = 0; j <= d.degree; j++)
                    rem[i - d.degree + j] = rem[i - d.degree + j].subtract(d.coefficients[j].mult(qt));
            }
        }
        return new Polynomial[]{new Polynomial(q), new Polynomial(rem)};
    }

    // O(n) - Horner's method
    public Complex eval(Complex x) {
        Complex r = coefficients[degree];
        for (int i = degree - 1; i >= 0; i--) r = r.mult(x).add(coefficients[i]);
        return r;
    }
    public boolean isZero() { return degree == 0 && coefficients[0].isZero(); }
    public boolean equals(Object obj) {
        if (!(obj instanceof Polynomial)) return false;
        Polynomial o = (Polynomial) obj;
        if (degree != o.degree) return false;
        for (int i = 0; i <= degree; i++) if (!coefficients[i].equals(o.coefficients[i])) return false;
        return true;
    }
}

class Complex {
    private final double real, imag;
    private static final double EPSILON = 1e-10;
    public Complex(double real, double imag) { this.real = real; this.imag = imag; }
    public Complex add(Complex o) { return new Complex(real + o.real, imag + o.imag); }
    public Complex subtract(Complex o) { return new Complex(real - o.real, imag - o.imag); }
    public Complex mult(Complex o) { return new Complex(real*o.real - imag*o.imag, real*o.imag + imag*o.real); }
    public Complex div(Complex o) {
        double d = o.real*o.real + o.imag*o.imag;
        if (Math.abs(d) < EPSILON) throw new ArithmeticException("Division by zero");
        return new Complex((real*o.real + imag*o.imag)/d, (imag*o.real - real*o.imag)/d);
    }
    public boolean isZero() { return Math.abs(real) < EPSILON && Math.abs(imag) < EPSILON; }
    public boolean equals(Object obj) {
        if (!(obj instanceof Complex)) return false;
        Complex o = (Complex) obj;
        return Math.abs(real - o.real) < EPSILON && Math.abs(imag - o.imag) < EPSILON;
    }
}

