/* Copyright Steven Eddies, 2017. See the LICENCE file in the project root. */

package uk.me.eddies.lib.neuron.neuralnet.type.feedforward;

import java.util.Collection;
import java.util.Optional;

import uk.me.eddies.lib.neuron.neuralnet.InputNeuron;
import uk.me.eddies.lib.neuron.neuralnet.MutableInterfaceNeurons;

/**
 * {@link LayerConfiguration} for an input layer.
 */
public class InputLayer extends LayerConfiguration<InputNeuron> {

	public InputLayer(int size) {
		super(size, true, prev -> new InputNeuron());
	}
	
	@Override
	protected Optional<MutableInterfaceNeurons> createInputInterface(Collection<InputNeuron> neurons) {
		return Optional.of(new MutableInterfaceNeurons(neurons));
	}
}
