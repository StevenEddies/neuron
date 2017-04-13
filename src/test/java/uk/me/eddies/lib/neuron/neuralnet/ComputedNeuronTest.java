/* Copyright Steven Eddies, 2017. See the LICENCE file in the project root. */

package uk.me.eddies.lib.neuron.neuralnet;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
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
		when(activationFunction.applyAsDouble(CONNECTION_1_VALUE + CONNECTION_2_VALUE))
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
}
