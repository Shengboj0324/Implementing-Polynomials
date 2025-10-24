# Polynomial with Complex Coefficients

## Usage
```java
Complex a = new Complex(1,2), b = new Complex(3,4), c = new Complex(-1,0), d = new Complex(0,-1.1);
Polynomial p = new Polynomial(new Complex[]{a,b,c,d}); // a + bx + cx² + dx³
Polynomial sum = p.add(p);
Polynomial prod = p.mult(p);
Polynomial[] divResult = p.div(divisor); // [quotient, remainder]
Complex result = p.eval(new Complex(2,1));
```

## Algorithms & Complexity

### Addition/Subtraction: O(max(n,m)) time, O(max(n,m)) space
- Element-wise operation on coefficient arrays
- Iterate through max degree, padding shorter polynomial with zeros
- Result degree = max(deg(p₁), deg(p₂))

### Multiplication: O(n×m) time, O(n+m) space
- Naive algorithm: multiply each term of p₁ with each term of p₂
- For coefficients aᵢ and bⱼ: result[i+j] += aᵢ × bⱼ
- Result degree = deg(p₁) + deg(p₂)
- Alternative: FFT-based O(n log n) adds complexity without benefit for moderate sizes

### Division: O((n-m+1)×m) time, O(n) space
- Polynomial long division algorithm
- Repeatedly: divide leading terms, multiply divisor by quotient term, subtract from remainder
- Continue until remainder degree < divisor degree
- Returns [quotient, remainder] where dividend = quotient × divisor + remainder

### Evaluation: O(n) time, O(1) space
- Horner's method: P(x) = a₀ + x(a₁ + x(a₂ + ... + x(aₙ)))
- Single pass from highest to lowest degree
- Optimal: no algorithm can evaluate polynomial in fewer than n operations
- Avoids computing powers of x explicitly

## Complex Arithmetic
- Addition: (a+bi) + (c+di) = (a+c) + (b+d)i
- Multiplication: (a+bi)(c+di) = (ac-bd) + (ad+bc)i
- Division: (a+bi)/(c+di) = [(ac+bd) + (bc-ad)i] / (c²+d²)
- All operations O(1) time and space

## Implementation Details
- **Representation**: Coefficient array in ascending power order: coefs[i] = coefficient of xⁱ
- **Degree normalization**: Leading zero coefficients removed during construction
- **Immutability**: All operations return new instances (thread-safe, no side effects)
- **Floating-point precision**: Epsilon-based equality (ε = 10⁻¹⁰) handles rounding errors

## Performance Validation
Empirical testing confirms theoretical complexity:
- **Addition**: Time/n ratio constant as n doubles → O(n)
- **Multiplication**: Time ratio ≈ 4 when n doubles → O(n²)
- **Division**: Time ratio ≈ 2 when dividend doubles (fixed divisor) → O(n)
- **Evaluation**: Time/n ratio constant → O(n)

Run: `java PerformanceAnalysis` for detailed metrics

## Files
- `Polynomial.java` - Core implementation (Polynomial + Complex classes)
- `PolynomialTest.java` - Unit tests
- `PerformanceAnalysis.java` - Empirical complexity validation
- `README.md` - This file