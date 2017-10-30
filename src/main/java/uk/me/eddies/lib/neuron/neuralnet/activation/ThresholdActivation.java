/* Copyright Steven Eddies, 2017. See the LICENCE file in the project root. */

package uk.me.eddies.lib.neuron.neuralnet.activation;

import static java.util.Objects.requireNonNull;
import uk.me.eddies.lib.neuron.neuralnet.ActivationFunction;

/**
 * Represents a threshold activation function, with a transition at 0.
 */
public class ThresholdActivation implements ActivationFunction {

	private final Polarisation polarisation;

	public ThresholdActivation(Polarisation polarisation) {
		this.polarisation = requireNonNull(polarisation);
	}

	public Polarisation getPolarisation() {
		return polarisation;
	}

	@Override
	public double applyFunction(double input) {
		return (input > 0) ? polarisation.getMaximum() : polarisation.getMinimum();
	}
}
