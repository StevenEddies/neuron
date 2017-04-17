/* Copyright Steven Eddies, 2017. See the LICENCE file in the project root. */

package uk.me.eddies.lib.neuron.neuralnet;

import java.util.Collection;

/**
 *Represents a neuron as part of a neural network.
 */
public interface Neuron {

	public double getValue();
	
	public Collection<Connection> getConnections();
}
