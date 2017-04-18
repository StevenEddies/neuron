/* Copyright Steven Eddies, 2017. See the LICENCE file in the project root. */

package uk.me.eddies.lib.neuron.neuralnet;

import static java.util.Collections.emptySet;
import java.util.Collection;

/**
 * Represents an input neuron for a network. Its value is set manually.
 */
public class InputNeuron implements Neuron {

	private double value = 0d;

	@Override
	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	@Override
	public Collection<Connection> getConnections() {
		return emptySet();
	}
}
