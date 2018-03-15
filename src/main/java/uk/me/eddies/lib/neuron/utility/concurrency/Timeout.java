/* Copyright Steven Eddies, 2018. See the LICENCE file in the project root. */

package uk.me.eddies.lib.neuron.utility.concurrency;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.TimeoutException;

/**
 * Provides the ability to monitor a task for taking too long, and time it out.
 */
public class Timeout {

	private final Clock clock;
	private final Instant terminationTime;

	public Timeout(Clock clock, Duration timeout) {
		this.clock = clock;
		this.terminationTime = clock.instant().plus(timeout);
	}

	public void monitor() throws TimeoutException {
		if (clock.instant().isAfter(terminationTime)) {
			throw new TimeoutException();
		}
	}
}
