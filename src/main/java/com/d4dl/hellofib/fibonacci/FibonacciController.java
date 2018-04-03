package com.d4dl.hellofib.fibonacci;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigInteger;

/**
 * This class exposes a single REST endpoint used to request the nth iteration of a Fibonacci sequence.
 *
 * Two parameters can be provided describing the desired timeComplexity calculation used as well as the
 * iteration of the desired Fibonacci number.
 */
@RestController
public class FibonacciController {

    /**
     * Default constructor
     */
    public FibonacciController() {
    }

    /**
     * Calculates and returns the nth Fibonacci number.
     * @param timeComplexity One of three values used to determine the
     * @param iterationCount the nth value to return
     * @return the nth iteration of the Fibonacci series calculated according to the specified time complexity.  If
     * none is specified the recursive Fibonacci algorithm is used.
     */
    @GetMapping("/fibonacci")
    public BigInteger calculateFibonacci(@RequestParam(required = false) FibonacciFinderFactory.TimeComplexity timeComplexity, @RequestParam int iterationCount) {
        return new FibonacciFinderFactory(timeComplexity).findIntegerAtIteration(iterationCount);
    }
}
