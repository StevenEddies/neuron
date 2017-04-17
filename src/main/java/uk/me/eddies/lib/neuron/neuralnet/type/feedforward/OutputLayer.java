/* Copyright Steven Eddies, 2017. See the LICENCE file in the project root. */

package uk.me.eddies.lib.neuron.neuralnet.type.feedforward;

import static java.util.Objects.requireNonNull;

import java.util.Collection;
import java.util.Optional;

import uk.me.eddies.lib.neuron.neuralnet.ActivationFunction;
import uk.me.eddies.lib.neuron.neuralnet.ComputedNeuron;
import uk.me.eddies.lib.neuron.neuralnet.InterfaceNeurons;

/**
 * {@link LayerConfiguration} for an input layer.
 */
public class OutputLayer extends LayerConfiguration<ComputedNeuron> {

	public OutputLayer(int size, ActivationFunction activationFunction) {
		super(size, false,
				givenActivationFunction(ComputedNeuron::makeWithConnections, activationFunction));
		requireNonNull(activationFunction);
	}
	
	@Override
	protected Optional<InterfaceNeurons> createOutputInterface(Collection<ComputedNeuron> neurons) {
		return Optional.of(new InterfaceNeurons(neurons));
	}
}
