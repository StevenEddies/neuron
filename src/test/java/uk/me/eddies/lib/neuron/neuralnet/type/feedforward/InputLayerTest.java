/* Copyright Steven Eddies, 2017. See the LICENCE file in the project root. */

package uk.me.eddies.lib.neuron.neuralnet.type.feedforward;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertThat;
import static org.mockito.MockitoAnnotations.initMocks;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;

import uk.me.eddies.lib.neuron.neuralnet.BiasNeuron;
import uk.me.eddies.lib.neuron.neuralnet.InputNeuron;
import uk.me.eddies.lib.neuron.neuralnet.Neuron;

public class InputLayerTest {

	private InputLayer systemUnderTest;
	
	private Layer result;
	
	@Before
	public void setUp() {
		initMocks(this);
		
		systemUnderTest = new InputLayer(2);
		result = systemUnderTest.buildLayer(Optional.empty());
	}
	
	@Test(expected=NullPointerException.class)
	public void shouldFailWithNullPreviousLayerContainer() {
		systemUnderTest.buildLayer(null);
	}
	
	@Test
	public void shouldCreateNeurons() {
		assertThat(result.getNeurons(), contains(Arrays.asList(
				instanceOf(InputNeuron.class),
				instanceOf(InputNeuron.class),
				instanceOf(BiasNeuron.class))));
	}
	
	@Test
	public void shouldCreateNoConnections() {
		assertThat(result.getConnections(), empty());
	}
	
	@Test
	public void shouldHaveNoOutputInterface() {
		assertThat(result.getOutputInterface(), equalTo(Optional.empty()));
	}
	
	@Test
	public void shouldHaveInputInterfaceLinkedToNeurons() {
		result.getInputInterface().get().setValues(Arrays.asList(5d, 10d));
		
		List<Neuron> neurons = new ArrayList<>(result.getNeurons());
		assertThat(neurons.get(0).getValue(), equalTo(5d));
		assertThat(neurons.get(1).getValue(), equalTo(10d));
	}
}
