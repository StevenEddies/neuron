/* Copyright Steven Eddies, 2017. See the LICENCE file in the project root. */

package uk.me.eddies.lib.neuron.neuralnet.management;

import uk.me.eddies.lib.neuron.neuralnet.NetworkConfiguration;

/**
 * Builder for {@link NetworkConfiguration}s.
 */
public interface ConfigurationBuilder {

	public NetworkConfiguration build();
}
