/* Copyright Steven Eddies, 2017. See the LICENCE file in the project root. */

package uk.me.eddies.lib.neuron.neuralnet.management.distribution;

import uk.me.eddies.lib.neuron.neuralnet.management.ResetDistribution;

/**
 * {@link ResetDistribution} which consists of a single possible value.
 */
public class FixedDistribution implements ResetDistribution {

	private double fixedValue;

	public FixedDistribution(double fixedValue) {
		this.fixedValue = fixedValue;
	}

	@Override
	public double getAsDouble() {
		return fixedValue;
	}
}
