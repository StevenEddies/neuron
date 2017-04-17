/* Copyright Steven Eddies, 2017. See the LICENCE file in the project root. */

package uk.me.eddies.lib.neuron.neuralnet.type.feedforward;

import static java.util.stream.Collectors.toCollection;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.sameInstance;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import uk.me.eddies.lib.neuron.neuralnet.ActivationFunction;
import uk.me.eddies.lib.neuron.neuralnet.ComputedNeuron;
import uk.me.eddies.lib.neuron.neuralnet.Connection;
import uk.me.eddies.lib.neuron.neuralnet.Neuron;

public class OutputLayerTest {

	@Mock private Layer previousLayer;
	@Mock private Neuron prevNeuron1;
	@Mock private Neuron prevNeuron2;
	@Mock private ActivationFunction activationFunction;
	
	private OutputLayer systemUnderTest;
	
	private Layer result;

	@Before
	public void setUp() {
		initMocks(this);
		when(previousLayer.getNeurons()).thenReturn(Arrays.asList(prevNeuron1, prevNeuron2));
		
		systemUnderTest = new OutputLayer(2, activationFunction);
		result = systemUnderTest.buildLayer(Optional.of(previousLayer));
	}
	
	@Test(expected=NullPointerException.class)
	public void shouldFailWithNullPreviousLayerContainer() {
		systemUnderTest.buildLayer(null);
	}

	@Test(expected=NullPointerException.class)
	public void shouldFailWithNullActivationFunction() {
		new OutputLayer(2, null);
	}
	
	@Test
	public void shouldCreateNeurons() {
		assertThat(result.getNeurons(), contains(Arrays.asList(
				instanceOf(ComputedNeuron.class),
				instanceOf(ComputedNeuron.class))));
	}
	
	@Test
	public void shouldCreateNeuronsLinkedToPreviousNeurons() {
		List<Neuron> neurons = new ArrayList<>(result.getNeurons());
		List<Connection> neuron1Connections = new ArrayList<>(neurons.get(0).getConnections());
		List<Connection> neuron2Connections = new ArrayList<>(neurons.get(1).getConnections());
		
		assertThat(neuron1Connections, hasSize(2));
		assertThat(neuron1Connections.get(0).getConnectee(), sameInstance(prevNeuron1));
		assertThat(neuron1Connections.get(1).getConnectee(), sameInstance(prevNeuron2));
		assertThat(neuron2Connections, hasSize(2));
		assertThat(neuron2Connections.get(0).getConnectee(), sameInstance(prevNeuron1));
		assertThat(neuron2Connections.get(1).getConnectee(), sameInstance(prevNeuron2));
	}
	
	@Test
	public void shouldConsolidateConnectionsFromAllNeurons() {
		Collection<Connection> expected = result.getNeurons().stream()
				.flatMap(n -> n.getConnections().stream())
				.collect(toCollection(LinkedHashSet::new));
		assertThat(expected, hasSize(4));
		
		assertThat(new LinkedHashSet<>(result.getConnections()), equalTo(expected));
	}
	
	@Test
	public void shouldHaveNoInputInterface() {
		assertThat(result.getInputInterface(), equalTo(Optional.empty()));
	}
	
	@Test
	public void shouldHaveOutputInterfaceLinkedToNeurons() {
		when(prevNeuron1.getValue()).thenReturn(5d);
		when(prevNeuron2.getValue()).thenReturn(10d);
		when(activationFunction.applyAsDouble(5d)).thenReturn(6d);
		when(activationFunction.applyAsDouble(10d)).thenReturn(11d);
		Iterator<Connection> connectionIt = result.getNeurons().stream()
				.flatMap(n -> n.getConnections().stream()).iterator();
		connectionIt.next().setWeight(1d); // 1 -> 1
		connectionIt.next().setWeight(0d); // 2 -> 1
		connectionIt.next().setWeight(0d); // 1 -> 2
		connectionIt.next().setWeight(1d); // 2 -> 2
		
		List<Double> results = result.getOutputInterface().get().getValues();
		
		assertThat(results, contains(Arrays.asList(equalTo(6d), equalTo(11d))));
	}
}
