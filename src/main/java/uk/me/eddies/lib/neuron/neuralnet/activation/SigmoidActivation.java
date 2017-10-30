/* Copyright Steven Eddies, 2017. See the LICENCE file in the project root. */

package uk.me.eddies.lib.neuron.neuralnet.activation;

import static java.util.Objects.requireNonNull;
import uk.me.eddies.lib.neuron.neuralnet.ActivationFunction;

/**
 * Represents a sigmoid activation function.
 */
public class SigmoidActivation implements ActivationFunction {

	private final Polarisation polarisation;

	public SigmoidActivation(Polarisation polarisation) {
		this.polarisation = requireNonNull(polarisation);
	}

	public Polarisation getPolarisation() {
		return polarisation;
	}

	@Override
	public double applyFunction(double input) {
		double denominator = 1 + Math.exp(-input);
		return (polarisation.getRange() / denominator) + polarisation.getMinimum();
	}

	@Override
	public boolean hasGradient() {
		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public double applyGradient(double input) {
		double numerator = polarisation.getRange() * Math.exp(input);
		double denominator = Math.pow(Math.exp(input) + 1, 2);
		return numerator / denominator;
	}
}
