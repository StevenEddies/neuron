/* Copyright Steven Eddies, 2017. See the LICENCE file in the project root. */

package uk.me.eddies.lib.neuron.neuralnet;

import static java.util.Collections.unmodifiableList;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * {@link InterfaceNeurons} which are {@link InputNeuron}s and therefore values can be set.
 */
public class MutableInterfaceNeurons extends InterfaceNeurons {

	private final List<InputNeuron> neurons;

	public MutableInterfaceNeurons(Collection<? extends InputNeuron> neurons) {
		super(neurons);
		this.neurons = unmodifiableList(new ArrayList<>(neurons));
	}

	public void setValues(List<Double> values) {
		if (values.size() != neurons.size()) {
			throw new IllegalStateException(String.format("Wrong number of values. Was %d, should be %d.",
					values.size(), neurons.size()));
		}
		for (int i = 0; i < neurons.size(); i++) {
			neurons.get(i).setValue(values.get(i));
		}
	}
}
