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

public class MutableInterfaceNeuronsTest {

	private static final double NEURON_1_VALUE = 37d;
	private static final double NEURON_2_VALUE = 73d;

	@Mock private InputNeuron neuron1;
	@Mock private InputNeuron neuron2;

	private MutableInterfaceNeurons systemUnderTest;

	@Before
	public void setUp() {
		initMocks(this);
		systemUnderTest = new MutableInterfaceNeurons(Arrays.asList(neuron1, neuron2));
	}

	@Test
	public void shouldRetrieveValuesOfAllNeurons() {
		when(neuron1.getValue()).thenReturn(NEURON_1_VALUE);
		when(neuron2.getValue()).thenReturn(NEURON_2_VALUE);

		assertThat(systemUnderTest.getValues(), contains(Arrays.asList(
				equalTo(NEURON_1_VALUE), equalTo(NEURON_2_VALUE))));
	}

	@Test
	public void shouldRetrieveValuesRegardlessOfChangesToInputCollection() {
		Collection<InputNeuron> inputCollection = new ArrayList<>(
				Arrays.asList(neuron1, neuron2));
		systemUnderTest = new MutableInterfaceNeurons(inputCollection);
		inputCollection.clear();

		shouldRetrieveValuesOfAllNeurons();
	}

	@Test
	public void shouldSetValuesOfAllNeurons() {
		systemUnderTest.setValues(Arrays.asList(NEURON_1_VALUE, NEURON_2_VALUE));

		verify(neuron1).setValue(NEURON_1_VALUE);
		verify(neuron2).setValue(NEURON_2_VALUE);
	}

	@Test
	public void shouldSetValuesRegardlessOfChangesToInputCollection() {
		Collection<InputNeuron> inputCollection = new ArrayList<>(
				Arrays.asList(neuron1, neuron2));
		systemUnderTest = new MutableInterfaceNeurons(inputCollection);
		inputCollection.clear();

		shouldSetValuesOfAllNeurons();
	}

	@Test(expected = NullPointerException.class)
	public void shouldFailToSetValuesWithNullCollection() {
		systemUnderTest.setValues(null);
	}

	@Test(expected = NullPointerException.class)
	public void shouldFailToSetValuesWithNullValue() {
		systemUnderTest.setValues(Arrays.asList(NEURON_1_VALUE, null));
	}

	@Test(expected = IllegalStateException.class)
	public void shouldFailToSetValuesWithWrongValueCount() {
		systemUnderTest.setValues(Arrays.asList(NEURON_1_VALUE));
	}

	@Test(expected = NullPointerException.class)
	public void shouldFailToConstructWithoutNeuronCollection() {
		new MutableInterfaceNeurons(null);
	}

	@Test(expected = NullPointerException.class)
	public void shouldFailToConstructWithNullNeuron() {
		new MutableInterfaceNeurons(Arrays.asList(neuron1, null));
	}
}
