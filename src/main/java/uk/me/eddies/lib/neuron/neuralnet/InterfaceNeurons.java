/* Copyright Steven Eddies, 2017. See the LICENCE file in the project root. */

package uk.me.eddies.lib.neuron.neuralnet;

import static java.util.Collections.unmodifiableList;
import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 * Represents a set of neurons which act as an interface between the network and
 * the outside world. Provides access to the values in numeric form.
 */
public class InterfaceNeurons {
	
	private final List<Neuron> neurons;
	
	public InterfaceNeurons(Collection<? extends Neuron> neurons) {
		this.neurons = unmodifiableList(new ArrayList<>(neurons));
		this.neurons.forEach(Objects::requireNonNull);
	}
	
	public List<Double> getValues() {
		return neurons.stream()
				.map(Neuron::getValue)
				.collect(toList());
	}
}
