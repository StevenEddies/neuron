/* Copyright Steven Eddies, 2017. See the LICENCE file in the project root. */

package uk.me.eddies.lib.neuron.neuralnet.training.supervised;

import static org.hamcrest.Matchers.contains;
import static org.junit.Assert.assertThat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class SupervisedTrainingRecordTest {

	private SupervisedTrainingRecord systemUnderTest;

	@Before
	public void setUp() {
		systemUnderTest = new SupervisedTrainingRecord(Arrays.asList(1d, 2d), Arrays.asList(3d, 4d));
	}

	@Test
	public void shouldPublishInputs() {
		assertThat(systemUnderTest.getInputs(), contains(1d, 2d));
	}

	@Test
	public void shouldPublishExpectedOutputs() {
		assertThat(systemUnderTest.getExpectedOutputs(), contains(3d, 4d));
	}

	@Test(expected = UnsupportedOperationException.class)
	public void shouldFailToModifyReturnedInputs() {
		systemUnderTest.getInputs().clear();
	}

	@Test(expected = UnsupportedOperationException.class)
	public void shouldFailToModifyReturnedExpectedOutputs() {
		systemUnderTest.getExpectedOutputs().clear();
	}

	@Test
	public void shouldInsulateFromInputInputCollectionChanges() {
		List<Double> inputs = new ArrayList<>(Arrays.asList(1d, 2d));
		systemUnderTest = new SupervisedTrainingRecord(inputs, Arrays.asList(3d, 4d));
		inputs.clear();
		assertThat(systemUnderTest.getInputs(), contains(1d, 2d));
	}

	@Test
	public void shouldInsulateFromInputExpectedOutputCollectionChanges() {
		List<Double> outputs = new ArrayList<>(Arrays.asList(3d, 4d));
		systemUnderTest = new SupervisedTrainingRecord(Arrays.asList(1d, 2d), outputs);
		outputs.clear();
		assertThat(systemUnderTest.getExpectedOutputs(), contains(3d, 4d));
	}

	@Test(expected = NullPointerException.class)
	public void shouldFailToConstructWithNullInputs() {
		new SupervisedTrainingRecord(null, Arrays.asList(3d, 4d));
	}

	@Test(expected = NullPointerException.class)
	public void shouldFailToConstructWithNullInputValue() {
		new SupervisedTrainingRecord(Arrays.asList(1d, null), Arrays.asList(3d, 4d));
	}

	@Test(expected = NullPointerException.class)
	public void shouldFailToConstructWithNullExpectedOutputs() {
		new SupervisedTrainingRecord(Arrays.asList(1d, 2d), null);
	}

	@Test(expected = NullPointerException.class)
	public void shouldFailToConstructWithNullExpectedOutputValue() {
		new SupervisedTrainingRecord(Arrays.asList(1d, 2d), Arrays.asList(null, 4d));
	}
}
