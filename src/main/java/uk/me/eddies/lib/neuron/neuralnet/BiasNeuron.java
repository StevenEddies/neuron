/* Copyright Steven Eddies, 2017. See the LICENCE file in the project root. */

package uk.me.eddies.lib.neuron.neuralnet;

/**
 * Represents a bias neuron for a network. Its value is fixed at 1.
 */
public class BiasNeuron implements Neuron {

	@Override
	public double getValue() {
		return 1d;
	}
}
