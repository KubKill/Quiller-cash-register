package model.usefull;

public class Range {

    private int lowerBound;

    private int upperBound;

    public Range(int lowerBound, int upperBound) {
        if(lowerBound >= upperBound)
            throw new IllegalArgumentException("Can't accept arguments of the same " +
                    "value or lowerBound value being bigger then upperBond value.");

        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
    }

    public boolean checkIfInRange(int number) {
        return lowerBound <= number && upperBound >= number;
    }

    public int getLowerBound() {
        return lowerBound;
    }

    public int getUpperBound() {
        return upperBound;
    }
}
