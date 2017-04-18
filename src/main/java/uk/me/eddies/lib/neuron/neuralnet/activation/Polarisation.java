/* Copyright Steven Eddies, 2017. See the LICENCE file in the project root. */

package uk.me.eddies.lib.neuron.neuralnet.activation;

/**
 * Represents the difference between unipolar and bipolar activation functions.
 */
public enum Polarisation {

	UNIPOLAR(0d, 1d),
	BIPOLAR(-1d, 1d);

	private final double minimum;
	private final double maximum;

	private Polarisation(double minimum, double maximum) {
		this.minimum = minimum;
		this.maximum = maximum;
	}

	public double getMinimum() {
		return minimum;
	}

	public double getMaximum() {
		return maximum;
	}

	public double getRange() {
		return getMaximum() - getMinimum();
	}
}
