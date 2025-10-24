// Polynomial with complex coefficients: coefs[i] = coefficient of x^i
public class Polynomial {
    private Complex[] coefficients;
    private int degree;
    public Polynomial(Complex[] coefs) {
        if (coefs == null || coefs.length == 0) {
            coefficients = new Complex[]{new Complex(0, 0)};
            degree = 0;
        } else {
            int d = coefs.length - 1;
            while (d > 0 && coefs[d].isZero()) d--;
            degree = d;
            coefficients = new Complex[d + 1];
            System.arraycopy(coefs, 0, coefficients, 0, d + 1);
        }
    }

    public int getDegree() { return degree; }

    // O(max(n,m)) time and space
    public Polynomial add(Polynomial other) {
        int maxDegree = Math.max(this.degree, other.degree);
        Complex[] resultCoefs = new Complex[maxDegree + 1];
        
        for (int i = 0; i <= maxDegree; i++) {
            Complex thisCoef = (i <= this.degree) ? this.coefficients[i] : new Complex(0, 0);
            Complex otherCoef = (i <= other.degree) ? other.coefficients[i] : new Complex(0, 0);
            resultCoefs[i] = thisCoef.add(otherCoef);
        }
        
        return new Polynomial(resultCoefs);
    }

    // O(max(n,m)) time and space
    public Polynomial subtract(Polynomial other) {
        int maxDegree = Math.max(this.degree, other.degree);
        Complex[] resultCoefs = new Complex[maxDegree + 1];
        
        for (int i = 0; i <= maxDegree; i++) {
            Complex thisCoef = (i <= this.degree) ? this.coefficients[i] : new Complex(0, 0);
            Complex otherCoef = (i <= other.degree) ? other.coefficients[i] : new Complex(0, 0);
            resultCoefs[i] = thisCoef.subtract(otherCoef);
        }
        
        return new Polynomial(resultCoefs);
    }

    // O(n*m) time, O(n+m) space
    public Polynomial mult(Polynomial other) {
        int resultDegree = this.degree + other.degree;
        Complex[] resultCoefs = new Complex[resultDegree + 1];
        
        // Initialize all coefficients to zero
        for (int i = 0; i <= resultDegree; i++) {
            resultCoefs[i] = new Complex(0, 0);
        }
        
        // Multiply each term of this polynomial with each term of other
        for (int i = 0; i <= this.degree; i++) {
            for (int j = 0; j <= other.degree; j++) {
                Complex product = this.coefficients[i].mult(other.coefficients[j]);
                resultCoefs[i + j] = resultCoefs[i + j].add(product);
            }
        }
        
        return new Polynomial(resultCoefs);
    }

    // O((n-m+1)*m) time, O(n) space - polynomial long division
    public Polynomial[] div(Polynomial divisor) {
        if (divisor.isZero()) {
            throw new ArithmeticException("Division by zero polynomial");
        }
        
        // If dividend degree < divisor degree, quotient is 0 and remainder is dividend
        if (this.degree < divisor.degree) {
            return new Polynomial[]{
                new Polynomial(new Complex[]{new Complex(0, 0)}),
                new Polynomial(this.coefficients)
            };
        }
        
        // Copy dividend coefficients to remainder
        Complex[] remainder = new Complex[this.coefficients.length];
        for (int i = 0; i < this.coefficients.length; i++) {
            remainder[i] = this.coefficients[i];
        }
        
        int quotientDegree = this.degree - divisor.degree;
        Complex[] quotient = new Complex[quotientDegree + 1];
        for (int i = 0; i <= quotientDegree; i++) {
            quotient[i] = new Complex(0, 0);
        }
        
        Complex leadingCoef = divisor.coefficients[divisor.degree];
        
        // Perform long division
        for (int i = this.degree; i >= divisor.degree; i--) {
            if (remainder[i].isZero()) {
                continue;
            }
            
            // Calculate quotient term
            Complex quotientTerm = remainder[i].div(leadingCoef);
            int quotientIndex = i - divisor.degree;
            quotient[quotientIndex] = quotientTerm;
            
            // Subtract divisor * quotientTerm from remainder
            for (int j = 0; j <= divisor.degree; j++) {
                Complex subtractValue = divisor.coefficients[j].mult(quotientTerm);
                remainder[i - divisor.degree + j] = remainder[i - divisor.degree + j].subtract(subtractValue);
            }
        }
        
        return new Polynomial[]{
            new Polynomial(quotient),
            new Polynomial(remainder)
        };
    }

    // O(n) time, O(1) space - Horner's method
    public Complex eval(Complex x) {
        Complex result = coefficients[degree];
        for (int i = degree - 1; i >= 0; i--)
            result = result.mult(x).add(coefficients[i]);
        return result;
    }

    public boolean isZero() {
        return degree == 0 && coefficients[0].isZero();
    }
    public boolean equals(Object obj) {
        if (!(obj instanceof Polynomial)) return false;
        Polynomial other = (Polynomial) obj;
        if (degree != other.degree) return false;
        for (int i = 0; i <= degree; i++)
            if (!coefficients[i].equals(other.coefficients[i])) return false;
        return true;
    }
}

class Complex {
    private final double real, imag;
    private static final double EPSILON = 1e-10;

    public Complex(double real, double imag) {
        this.real = real;
        this.imag = imag;
    }

    public Complex add(Complex o) { return new Complex(real + o.real, imag + o.imag); }
    public Complex subtract(Complex o) { return new Complex(real - o.real, imag - o.imag); }
    public Complex mult(Complex o) { return new Complex(real*o.real - imag*o.imag, real*o.imag + imag*o.real); }
    public Complex div(Complex o) {
        double d = o.real*o.real + o.imag*o.imag;
        if (Math.abs(d) < EPSILON) throw new ArithmeticException("Division by zero");
        return new Complex((real*o.real + imag*o.imag)/d, (imag*o.real - real*o.imag)/d);
    }
    public Complex negate() { return new Complex(-real, -imag); }
    public boolean isZero() { return Math.abs(real) < EPSILON && Math.abs(imag) < EPSILON; }
    public boolean isNegative() { return real < -EPSILON && Math.abs(imag) < EPSILON; }
    public boolean equals(Object obj) {
        if (!(obj instanceof Complex)) return false;
        Complex o = (Complex) obj;
        return Math.abs(real - o.real) < EPSILON && Math.abs(imag - o.imag) < EPSILON;
    }
    public String toString() {
        if (Math.abs(imag) < EPSILON) return String.format("%.2f", real);
        if (Math.abs(real) < EPSILON) return String.format("%.2fi", imag);
        return String.format("%.2f%+.2fi", real, imag);
    }
}

