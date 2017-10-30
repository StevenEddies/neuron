/* Copyright Steven Eddies, 2017. See the LICENCE file in the project root. */

package uk.me.eddies.lib.neuron.neuralnet;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.sameInstance;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

public class ComputedNeuronTest {

	private static final double CONNECTION_1_VALUE = 98d;
	private static final double CONNECTION_2_VALUE = 45d;
	private static final double ACTIVATED_VALUE = 10d;

	@Mock private Connection connection1;
	@Mock private Connection connection2;
	@Mock private ActivationFunction activationFunction;

	private ComputedNeuron systemUnderTest;

	@Before
	public void setUp() {
		initMocks(this);
		systemUnderTest = new ComputedNeuron(
				Arrays.asList(connection1, connection2), activationFunction);
	}

	@Test
	public void shouldComputeCorrectValue() {
		when(connection1.getWeightedConnecteeValue()).thenReturn(CONNECTION_1_VALUE);
		when(connection2.getWeightedConnecteeValue()).thenReturn(CONNECTION_2_VALUE);
		when(activationFunction.applyFunction(CONNECTION_1_VALUE + CONNECTION_2_VALUE))
				.thenReturn(ACTIVATED_VALUE);

		assertThat(systemUnderTest.getValue(), equalTo(ACTIVATED_VALUE));
	}

	@Test
	public void shouldFunctionRegardlessOfChangesToInputCollection() {
		Collection<Connection> inputCollection = new ArrayList<>(
				Arrays.asList(connection1, connection2));
		systemUnderTest = new ComputedNeuron(inputCollection, activationFunction);
		inputCollection.clear();

		shouldComputeCorrectValue();
	}

	@Test
	public void shouldMakeConnectionsAvailable() {
		assertThat(systemUnderTest.getConnections(), contains(Arrays.asList(
				sameInstance(connection1), sameInstance(connection2))));
	}

	@Test(expected = UnsupportedOperationException.class)
	public void shouldDisallowChangesToReturnedConnections() {
		systemUnderTest.getConnections().clear();
	}

	@Test(expected = NullPointerException.class)
	public void shouldFailToConstructWithoutConnectionCollection() {
		new ComputedNeuron(null, activationFunction);
	}

	@Test(expected = NullPointerException.class)
	public void shouldFailToConstructWithoutActivationFunction() {
		new ComputedNeuron(Arrays.asList(connection1, connection2), null);
	}

	@Test(expected = NullPointerException.class)
	public void shouldFailToConstructWithNullConnection() {
		new ComputedNeuron(Arrays.asList(connection1, null), activationFunction);
	}

	@Test
	public void shouldConstructConnectionsForGivenConnecteeNeurons() {
		Neuron neuron1 = mock(Neuron.class);
		Neuron neuron2 = mock(Neuron.class);
		systemUnderTest = ComputedNeuron.makeWithConnections(
				Arrays.asList(neuron1, neuron2), activationFunction);

		systemUnderTest.getConnections().forEach(c -> c.setWeight(1.0));
		when(neuron1.getValue()).thenReturn(CONNECTION_1_VALUE);
		when(neuron2.getValue()).thenReturn(CONNECTION_2_VALUE);
		when(activationFunction.applyFunction(CONNECTION_1_VALUE + CONNECTION_2_VALUE))
				.thenReturn(ACTIVATED_VALUE);

		assertThat(systemUnderTest.getValue(), equalTo(ACTIVATED_VALUE));
	}
}
