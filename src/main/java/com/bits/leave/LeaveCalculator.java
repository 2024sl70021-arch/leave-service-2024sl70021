
package com.bits.leave;

public class LeaveCalculator {

    // Calculates the remaining leave balance
    public int calculateRemainingLeave(int totalLeave, int leaveTaken) {

        if (totalLeave < 0 || leaveTaken < 0) {
            throw new IllegalArgumentException(
                "Leave values cannot be negative"
            );
        }

        if (leaveTaken > totalLeave) {
            throw new IllegalArgumentException(
                "Leave taken cannot exceed total leave"
            );
        }

        return totalLeave - leaveTaken;
    }

    // Calculates the leave encashment amount
    public double calculateEncashment(
            int remainingLeave, double dailyRate) {

        if (remainingLeave < 0 || dailyRate < 0) {
            throw new IllegalArgumentException(
                "Leave and daily rate cannot be negative"
            );
        }

        return remainingLeave * dailyRate;
    }
}
