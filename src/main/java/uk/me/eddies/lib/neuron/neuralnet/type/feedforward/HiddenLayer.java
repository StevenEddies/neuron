/* Copyright Steven Eddies, 2017. See the LICENCE file in the project root. */

package uk.me.eddies.lib.neuron.neuralnet.type.feedforward;

import static java.util.Objects.requireNonNull;

import uk.me.eddies.lib.neuron.neuralnet.ActivationFunction;
import uk.me.eddies.lib.neuron.neuralnet.ComputedNeuron;

/**
 * {@link LayerConfiguration} for an input layer.
 */
public class HiddenLayer extends LayerConfiguration<ComputedNeuron> {

	public HiddenLayer(int size, ActivationFunction activationFunction) {
		super(size, true,
				givenActivationFunction(ComputedNeuron::makeWithConnections, activationFunction));
		requireNonNull(activationFunction);
	}
}
