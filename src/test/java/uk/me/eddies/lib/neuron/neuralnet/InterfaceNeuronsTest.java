/* Copyright Steven Eddies, 2017. See the LICENCE file in the project root. */

package uk.me.eddies.lib.neuron.neuralnet;

import static org.hamcrest.Matchers.contains;
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

public class InterfaceNeuronsTest {
	
	private static final double NEURON_1_VALUE = 37d;
	private static final double NEURON_2_VALUE = 73d;

	@Mock private Neuron neuron1;
	@Mock private Neuron neuron2;
	
	private InterfaceNeurons systemUnderTest;
	
	@Before
	public void setUp() {
		initMocks(this);
		systemUnderTest = new InterfaceNeurons(Arrays.asList(neuron1, neuron2));
	}
	
	@Test
	public void shouldRetrieveValuesOfAllNeurons() {
		when(neuron1.getValue()).thenReturn(NEURON_1_VALUE);
		when(neuron2.getValue()).thenReturn(NEURON_2_VALUE);
		
		assertThat(systemUnderTest.getValues(), contains(Arrays.asList(
				equalTo(NEURON_1_VALUE), equalTo(NEURON_2_VALUE))));
	}
	
	@Test
	public void shouldFunctionRegardlessOfChangesToInputCollection() {
		Collection<Neuron> inputCollection = new ArrayList<>(
				Arrays.asList(neuron1, neuron2));
		systemUnderTest = new InterfaceNeurons(inputCollection);
		inputCollection.clear();
		
		shouldRetrieveValuesOfAllNeurons();
	}
	
	@Test(expected=NullPointerException.class)
	public void shouldFailToConstructWithoutNeuronCollection() {
		new InterfaceNeurons(null);
	}
	
	@Test(expected=NullPointerException.class)
	public void shouldFailToConstructWithNullNeuron() {
		new InterfaceNeurons(Arrays.asList(neuron1, null));
	}
}
