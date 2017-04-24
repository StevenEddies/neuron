/* Copyright Steven Eddies, 2017. See the LICENCE file in the project root. */

package uk.me.eddies.lib.neuron.neuralnet.management.distribution;

import static java.util.Objects.requireNonNull;
import java.util.Random;
import uk.me.eddies.lib.neuron.neuralnet.management.ResetDistribution;

/**
 * {@link ResetDistribution} which produces a normal distribution.
 */
public class NormalDistribution implements ResetDistribution {

	private final Random random;
	private final double mean;
	private final double stdDev;

	public NormalDistribution(double mean, double variance) {
		this(new Random(), mean, variance);
	}

	NormalDistribution(Random random, double mean, double variance) {
		this.random = requireNonNull(random);
		this.mean = mean;
		this.stdDev = Math.sqrt(variance);
	}

	@Override
	public double getAsDouble() {
		return mean + random.nextGaussian() * stdDev;
	}
}
