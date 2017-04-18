/* Copyright Steven Eddies, 2017. See the LICENCE file in the project root. */

package uk.me.eddies.lib.neuron.neuralnet;

import static java.util.Collections.unmodifiableCollection;
import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.toList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Objects;

/**
 * Represents a neuron, either hidden or output, whose value is computed from connections to other neurons.
 */
public class ComputedNeuron implements Neuron {

	private final Collection<Connection> connections;
	private final ActivationFunction activationFunction;

	public ComputedNeuron(Collection<Connection> connections, ActivationFunction activationFunction) {
		this.connections = unmodifiableCollection(new LinkedHashSet<>(connections));
		this.connections.forEach(Objects::requireNonNull);
		this.activationFunction = requireNonNull(activationFunction);
	}

	public static ComputedNeuron makeWithConnections(Collection<Neuron> connectedNeurons,
			ActivationFunction activationFunction) {
		Collection<Connection> connections = connectedNeurons.stream()
				.map(Connection::new)
				.collect(toList());
		return new ComputedNeuron(connections, activationFunction);
	}

	@Override
	public double getValue() {
		double inputValue = connections.stream()
				.mapToDouble(Connection::getWeightedConnecteeValue)
				.sum();
		return activationFunction.applyAsDouble(inputValue);
	}

	@Override
	public Collection<Connection> getConnections() {
		return connections;
	}
}
