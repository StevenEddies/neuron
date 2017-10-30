/* Copyright Steven Eddies, 2017. See the LICENCE file in the project root. */

package uk.me.eddies.lib.neuron.neuralnet.activation;

import uk.me.eddies.lib.neuron.neuralnet.ActivationFunction;

/**
 * Represents an identity activation function.
 */
public class LinearActivation implements ActivationFunction {

	@Override
	public double applyFunction(double input) {
		return input;
	}

	@Override
	public boolean hasGradient() {
		return true;
	}

	@Override
	public double applyGradient(double input) {
		return 1d;
	}
}
