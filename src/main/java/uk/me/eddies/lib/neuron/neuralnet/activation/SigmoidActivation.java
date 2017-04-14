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
	public double applyAsDouble(double input) {
		double denominator = 1 + Math.exp(-input);
		return (polarisation.getRange() / denominator) + polarisation.getMinimum();
	}
}
