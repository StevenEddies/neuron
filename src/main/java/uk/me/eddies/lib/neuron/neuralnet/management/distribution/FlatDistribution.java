/* Copyright Steven Eddies, 2017. See the LICENCE file in the project root. */

package uk.me.eddies.lib.neuron.neuralnet.management.distribution;

import static java.util.Objects.requireNonNull;
import java.util.Random;
import uk.me.eddies.lib.neuron.neuralnet.management.ResetDistribution;

/**
 * {@link ResetDistribution} which results in equal probability over a given range.
 */
public class FlatDistribution implements ResetDistribution {

	private final Random random;
	private final double minimum;
	private final double maximum;

	public FlatDistribution(double minimum, double maximum) {
		this(new Random(), minimum, maximum);
	}

	FlatDistribution(Random random, double minimum, double maximum) {
		this.random = requireNonNull(random);
		if (minimum > maximum) throw new IllegalArgumentException("Mimimum must be less than maximum.");
		this.minimum = minimum;
		this.maximum = maximum;
	}

	@Override
	public double getAsDouble() {
		return random.nextDouble() * (maximum - minimum) + minimum;
	}
}
