/* Copyright Steven Eddies, 2018. See the LICENCE file in the project root. */

package uk.me.eddies.lib.neuron.utility.concurrency;

import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.TimeoutException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

public class TimeoutTest {

	private static final Instant START = Instant.ofEpochMilli(1000);
	private static final Duration TIMEOUT = Duration.of(500, ChronoUnit.MILLIS);
	private static final Instant MONITOR_INSIDE = Instant.ofEpochMilli(1400);
	private static final Instant MONITOR_BOUNDARY = Instant.ofEpochMilli(1500);
	private static final Instant MONITOR_OUTSIDE = Instant.ofEpochMilli(1600);

	@Mock private Clock clock;

	private Timeout systemUnderTest;

	@Before
	public void setUp() {
		initMocks(this);

		when(clock.instant()).thenReturn(START);

		systemUnderTest = new Timeout(clock, TIMEOUT);
	}

	@Test
	public void shouldDoNothingMonitoringWithinTimeout() throws TimeoutException {
		when(clock.instant()).thenReturn(MONITOR_INSIDE);
		systemUnderTest.monitor();
	}

	@Test
	public void shouldDoNothingMonitoringAtExactTimeout() throws TimeoutException {
		when(clock.instant()).thenReturn(MONITOR_BOUNDARY);
		systemUnderTest.monitor();
	}

	@Test(expected = TimeoutException.class)
	public void shouldExceptionMonitoringAfterTimeout() throws TimeoutException {
		when(clock.instant()).thenReturn(MONITOR_OUTSIDE);
		systemUnderTest.monitor();
	}
}
