/* Copyright Steven Eddies, 2017. See the LICENCE file in the project root. */

package uk.me.eddies.lib.neuron.neuralnet.activation;

import uk.me.eddies.lib.neuron.neuralnet.ActivationFunction;

/**
 * Represents an identity activation function.
 */
public class LinearActivation implements ActivationFunction {
	
	@Override
	public double applyAsDouble(double input) {
		return input;
	}
}
