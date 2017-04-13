/* Copyright Steven Eddies, 2017. See the LICENCE file in the project root. */

package uk.me.eddies.lib.neuron.neuralnet;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

public class ConnectionTest {
	
	private static final double SET_WEIGHT = 94d;
	private static final double CONNECTEE_VALUE = 12d;

	@Mock private Neuron connectee;
	
	private Connection systemUnderTest;
	
	@Before
	public void setUp() {
		initMocks(this);
		systemUnderTest = new Connection(connectee);
	}
	
	@Test
	public void shouldInitiallyHaveZeroWeight() {
		assertThat(systemUnderTest.getWeight(), equalTo(0d));
	}
	
	@Test
	public void shouldRetainSetWeight() {
		systemUnderTest.setWeight(SET_WEIGHT);
		assertThat(systemUnderTest.getWeight(), equalTo(SET_WEIGHT));
	}
	
	@Test
	public void shouldComputeWeightedValueOfConnectee() {
		when(connectee.getValue()).thenReturn(CONNECTEE_VALUE);
		systemUnderTest.setWeight(SET_WEIGHT);
		assertThat(systemUnderTest.getWeightedConnecteeValue(),
				equalTo(SET_WEIGHT * CONNECTEE_VALUE));
	}
	
	@Test(expected=NullPointerException.class)
	public void shouldFailToConstructWithoutConnectee() {
		new Connection(null);
	}
}
