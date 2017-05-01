/* Copyright Steven Eddies, 2017. See the LICENCE file in the project root. */

package uk.me.eddies.lib.neuron.utility.system;

/**
 * Provides information about the running system.
 */
public class SystemInfo {

	public int getProcessorCount() {
		return Runtime.getRuntime().availableProcessors();
	}
}
