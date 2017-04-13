/* Copyright Steven Eddies, 2017. See the LICENCE file in the project root. */

package uk.me.eddies.lib.neuron.neuralnet;

import java.util.function.DoubleUnaryOperator;

/**
 * Represents an activation function between a neuron's input and output value.
 */
@FunctionalInterface
public interface ActivationFunction extends DoubleUnaryOperator {

	// Inherits the method
}
