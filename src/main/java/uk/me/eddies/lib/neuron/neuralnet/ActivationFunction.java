/* Copyright Steven Eddies, 2017. See the LICENCE file in the project root. */

package uk.me.eddies.lib.neuron.neuralnet;

/**
 * Represents an activation function between a neuron's input and output value.
 */
public interface ActivationFunction {

	public double applyFunction(double input);

	public boolean hasGradient();

	public double applyGradient(double input) throws UnsupportedOperationException;
}
