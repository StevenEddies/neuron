/* Copyright Steven Eddies, 2017. See the LICENCE file in the project root. */

package uk.me.eddies.lib.neuron.neuralnet;

/**
 * Represents a configuration of neural network.
 */
public interface NetworkConfiguration {

	public NeuralNetwork create();
}
