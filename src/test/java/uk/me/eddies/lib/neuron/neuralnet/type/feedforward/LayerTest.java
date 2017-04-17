/* Copyright Steven Eddies, 2017. See the LICENCE file in the project root. */

package uk.me.eddies.lib.neuron.neuralnet.type.feedforward;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.sameInstance;
import static org.junit.Assert.assertThat;
import static org.mockito.MockitoAnnotations.initMocks;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import uk.me.eddies.lib.neuron.neuralnet.Connection;
import uk.me.eddies.lib.neuron.neuralnet.InterfaceNeurons;
import uk.me.eddies.lib.neuron.neuralnet.MutableInterfaceNeurons;
import uk.me.eddies.lib.neuron.neuralnet.Neuron;

public class LayerTest {

	@Mock private Neuron neuron1;
	@Mock private Neuron neuron2;
	@Mock private Connection connection1;
	@Mock private Connection connection2;
	@Mock private InterfaceNeurons outputInterface;
	@Mock private MutableInterfaceNeurons inputInterface;
	
	private Layer systemUnderTest;
	
	@Before
	public void setUp() {
		initMocks(this);
		systemUnderTest = new Layer(
				Arrays.asList(neuron1, neuron2),
				Arrays.asList(connection1, connection2),
				Optional.of(outputInterface),
				Optional.of(inputInterface));
	}
	
	@Test
	public void shouldMakeNeuronsAccessible() {
		assertThat(systemUnderTest.getNeurons(), contains(Arrays.asList(
				sameInstance(neuron1), sameInstance(neuron2))));
	}
	
	@Test(expected=UnsupportedOperationException.class)
	public void shouldFailToModifyReturnedNeuronsCollection() {
		systemUnderTest.getNeurons().clear();
	}
	
	@Test
	public void shouldKeepNeuronsIndependentOfInputCollection() {
		Collection<Neuron> original = new ArrayList<>(Arrays.asList(neuron1, neuron2));
		systemUnderTest = new Layer(
				original,
				Arrays.asList(connection1, connection2),
				Optional.of(outputInterface),
				Optional.of(inputInterface));
		
		original.clear();
		
		assertThat(systemUnderTest.getNeurons(), contains(Arrays.asList(
				sameInstance(neuron1), sameInstance(neuron2))));
	}
	
	@Test
	public void shouldMakeConnectionsAccessible() {
		assertThat(systemUnderTest.getConnections(), contains(Arrays.asList(
				sameInstance(connection1), sameInstance(connection2))));
	}
	
	@Test(expected=UnsupportedOperationException.class)
	public void shouldFailToModifyReturnedConnectionsCollection() {
		systemUnderTest.getConnections().clear();
	}
	
	@Test
	public void shouldKeepConnectionsIndependentOfInputCollection() {
		Collection<Connection> original = new ArrayList<>(Arrays.asList(connection1, connection2));
		systemUnderTest = new Layer(
				Arrays.asList(neuron1, neuron2),
				original,
				Optional.of(outputInterface),
				Optional.of(inputInterface));
		
		original.clear();
		
		assertThat(systemUnderTest.getConnections(), contains(Arrays.asList(
				sameInstance(connection1), sameInstance(connection2))));
	}
	
	@Test
	public void shouldMakeOutputInterfaceAccessible() {
		assertThat(systemUnderTest.getOutputInterface().get(), sameInstance(outputInterface));
	}
	
	@Test
	public void shouldWorkWithoutOutputInterface() {
		systemUnderTest = new Layer(
				Arrays.asList(neuron1, neuron2),
				Arrays.asList(connection1, connection2),
				Optional.empty(),
				Optional.of(inputInterface));
		
		assertThat(systemUnderTest.getOutputInterface().isPresent(), equalTo(false));
	}
	
	@Test
	public void shouldMakeInputInterfaceAccessible() {
		assertThat(systemUnderTest.getInputInterface().get(), sameInstance(inputInterface));
	}
	
	@Test
	public void shouldWorkWithoutInputInterface() {
		systemUnderTest = new Layer(
				Arrays.asList(neuron1, neuron2),
				Arrays.asList(connection1, connection2),
				Optional.of(outputInterface),
				Optional.empty());
		
		assertThat(systemUnderTest.getInputInterface().isPresent(), equalTo(false));
	}
	
	@Test(expected=NullPointerException.class)
	public void shouldFailToConstructWithNullNeuronCollection() {
		new Layer(
				null,
				Arrays.asList(connection1, connection2),
				Optional.of(outputInterface),
				Optional.of(inputInterface));
	}
	
	@Test(expected=NullPointerException.class)
	public void shouldFailToConstructWithNullNeuron() {
		new Layer(
				Arrays.asList(neuron1, null),
				Arrays.asList(connection1, connection2),
				Optional.of(outputInterface),
				Optional.of(inputInterface));
	}
	
	@Test(expected=NullPointerException.class)
	public void shouldFailToConstructWithNullConnectionCollection() {
		new Layer(
				Arrays.asList(neuron1, neuron2),
				null,
				Optional.of(outputInterface),
				Optional.of(inputInterface));
	}
	
	@Test(expected=NullPointerException.class)
	public void shouldFailToConstructWithNullConnection() {
		new Layer(
				Arrays.asList(neuron1, neuron2),
				Arrays.asList(connection1, null),
				Optional.of(outputInterface),
				Optional.of(inputInterface));
	}
	
	@Test(expected=NullPointerException.class)
	public void shouldFailToConstructWithNullOutputInterfaceContainer() {
		new Layer(
				Arrays.asList(neuron1, neuron2),
				Arrays.asList(connection1, connection2),
				null,
				Optional.of(inputInterface));
	}
	
	@Test(expected=NullPointerException.class)
	public void shouldFailToConstructWithNullInputInterfaceContainer() {
		new Layer(
				Arrays.asList(neuron1, neuron2),
				Arrays.asList(connection1, connection2),
				Optional.of(outputInterface),
				null);
	}
}
