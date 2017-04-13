/* Copyright Steven Eddies, 2017. See the LICENCE file in the project root. */

package uk.me.eddies.lib.neuron.neuralnet;

import static java.util.Collections.unmodifiableCollection;

import java.util.Collection;
import java.util.LinkedHashSet;

/**
 * Represents a neuron, either hidden or output, whose value is computed from
 * connections to other neurons.
 */
public class ComputedNeuron implements Neuron {

	private final Collection<Connection> connections;
	private final ActivationFunction activationFunction;
	
	public ComputedNeuron(Collection<Connection> connections,
			ActivationFunction activationFunction) {
		this.connections = unmodifiableCollection(
				new LinkedHashSet<>(connections));
		this.activationFunction = activationFunction;
	}
	
	@Override
	public double getValue() {
		double inputValue = connections.stream()
				.mapToDouble(Connection::getWeightedConnecteeValue)
				.sum();
		return activationFunction.applyAsDouble(inputValue);
	}
}
