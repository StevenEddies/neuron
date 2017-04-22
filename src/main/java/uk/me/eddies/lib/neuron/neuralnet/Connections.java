/* Copyright Steven Eddies, 2017. See the LICENCE file in the project root. */

package uk.me.eddies.lib.neuron.neuralnet;

import static java.util.Collections.unmodifiableList;
import static java.util.stream.Collectors.toList;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 * Represents a set of connections, whose weights can be got and set in bulk.
 */
public class Connections {

	private final List<Connection> connections;

	public Connections(Collection<? extends Connection> connections) {
		this.connections = unmodifiableList(new ArrayList<>(connections));
		this.connections.forEach(Objects::requireNonNull);
	}

	public List<Double> getWeights() {
		return connections.stream()
				.map(Connection::getWeight)
				.collect(toList());
	}

	public void setWeights(List<Double> weights) {
		if (weights.size() != connections.size()) {
			throw new IllegalStateException(String.format("Wrong number of weights. Was %d, should be %d.",
					weights.size(), connections.size()));
		}
		for (int i = 0; i < connections.size(); i++) {
			connections.get(i).setWeight(weights.get(i));
		}
	}

	public int getCount() {
		return connections.size();
	}
}
