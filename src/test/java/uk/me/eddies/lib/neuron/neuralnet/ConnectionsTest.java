/* Copyright Steven Eddies, 2017. See the LICENCE file in the project root. */

package uk.me.eddies.lib.neuron.neuralnet;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

public class ConnectionsTest {

	private static final double WEIGHT_1 = 37d;
	private static final double WEIGHT_2 = 73d;

	@Mock private Connection connection1;
	@Mock private Connection connection2;

	private Connections systemUnderTest;

	@Before
	public void setUp() {
		initMocks(this);
		systemUnderTest = new Connections(Arrays.asList(connection1, connection2));
	}

	@Test
	public void shouldRetrieveWeightsOfAllConnections() {
		when(connection1.getWeight()).thenReturn(WEIGHT_1);
		when(connection2.getWeight()).thenReturn(WEIGHT_2);

		assertThat(systemUnderTest.getWeights(), contains(Arrays.asList(
				equalTo(WEIGHT_1), equalTo(WEIGHT_2))));
	}

	@Test
	public void shouldRetrieveWeightsRegardlessOfChangesToInputCollection() {
		Collection<Connection> inputCollection = new ArrayList<>(
				Arrays.asList(connection1, connection2));
		systemUnderTest = new Connections(inputCollection);
		inputCollection.clear();

		shouldRetrieveWeightsOfAllConnections();
	}

	@Test
	public void shouldSetWeightsOfAllConnections() {
		systemUnderTest.setWeights(Arrays.asList(WEIGHT_1, WEIGHT_2));

		verify(connection1).setWeight(WEIGHT_1);
		verify(connection2).setWeight(WEIGHT_2);
	}

	@Test
	public void shouldSetWeightsRegardlessOfChangesToInputCollection() {
		Collection<Connection> inputCollection = new ArrayList<>(
				Arrays.asList(connection1, connection2));
		systemUnderTest = new Connections(inputCollection);
		inputCollection.clear();

		shouldSetWeightsOfAllConnections();
	}

	@Test
	public void shouldReportCountOfConnections() {
		assertThat(systemUnderTest.getCount(), equalTo(2));
	}

	@Test(expected = NullPointerException.class)
	public void shouldFailToSetWeightsWithNullCollection() {
		systemUnderTest.setWeights(null);
	}

	@Test(expected = NullPointerException.class)
	public void shouldFailToSetWeightsWithNullWeight() {
		systemUnderTest.setWeights(Arrays.asList(WEIGHT_1, null));
	}

	@Test(expected = IllegalStateException.class)
	public void shouldFailToSetWeightsWithWrongWeightCount() {
		systemUnderTest.setWeights(Arrays.asList(WEIGHT_1));
	}

	@Test(expected = NullPointerException.class)
	public void shouldFailToConstructWithoutConnectionCollection() {
		new Connections(null);
	}

	@Test(expected = NullPointerException.class)
	public void shouldFailToConstructWithNullConnection() {
		new Connections(Arrays.asList(connection1, null));
	}
}
