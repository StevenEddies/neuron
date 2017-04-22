/* Copyright Steven Eddies, 2017. See the LICENCE file in the project root. */

package uk.me.eddies.lib.neuron.neuralnet.management;

import java.util.function.DoubleSupplier;

/**
 * Represents a distribution of values to set a network to. Supplies values from that distribution.
 */
@FunctionalInterface
public interface ResetDistribution extends DoubleSupplier {

	// Inherits the method
}
