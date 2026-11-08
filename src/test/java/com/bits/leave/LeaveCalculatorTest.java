
package com.bits.leave;

import org.junit.Test;
import static org.junit.Assert.*;

public class LeaveCalculatorTest {

    LeaveCalculator calculator = new LeaveCalculator();

    // Test 1: Calculate remaining leave balance
    @Test
    public void testRemainingLeave() {

        int result =
            calculator.calculateRemainingLeave(24, 9);

        assertEquals(15, result);
    }

    // Test 2: Calculate leave encashment
    @Test
    public void testLeaveEncashment() {

        double result =
            calculator.calculateEncashment(15, 1000.0);

        assertEquals(15000.0, result, 0.01);
    }

    // Test 3: Calculate encashment when no leave remains
    @Test
    public void testZeroLeaveEncashment() {

        double result =
            calculator.calculateEncashment(0, 1000.0);

        assertEquals(0.0, result, 0.01);
    }
}
