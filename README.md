# Polynomial with Complex Coefficients

## Complex Numbers
- Form: z = a + bi where a, b ∈ ℝ, i² = -1
- Addition: (a+bi) + (c+di) = (a+c) + (b+d)i
- Multiplication: (a+bi)(c+di) = (ac-bd) + (ad+bc)i
- Division: (a+bi)/(c+di) = [(ac+bd) + (bc-ad)i] / (c²+d²)

## Polynomial Representation
- P(x) = a₀ + a₁x + a₂x² + ... + aₙxⁿ where aᵢ ∈ ℂ
- Stored as array: coefs[i] = aᵢ (coefficient of xⁱ)
- Degree: highest power with non-zero coefficient

## Addition: O(max(n,m))
- Add corresponding coefficients: (P+Q)[i] = P[i] + Q[i]
- Pad shorter polynomial with zeros
- Result degree ≤ max(deg(P), deg(Q))

## Multiplication: O(n×m)
- Distribute each term: (Σaᵢxⁱ)(Σbⱼxʲ) = Σ(aᵢbⱼ)xⁱ⁺ʲ
- For each pair (i,j): result[i+j] += aᵢ × bⱼ
- Result degree = deg(P) + deg(Q)

## Division: O((n-m+1)×m)
- Polynomial long division (like decimal long division)
- Repeatedly: divide leading terms, multiply back, subtract
- Stop when remainder degree < divisor degree
- Returns [quotient, remainder] where P = Q×quotient + remainder

## Evaluation: O(n)
- Horner's method: P(x) = a₀ + x(a₁ + x(a₂ + ... + x(aₙ)))
- Start from highest degree, multiply by x and add next coefficient
- Avoids computing powers of x separately