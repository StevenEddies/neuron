/* Copyright Steven Eddies, 2017-18. See the LICENCE file in the project root. */

package uk.me.eddies.lib.neuron.neuralnet.training.supervised;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

public class SupervisedTrainingSetTest {

	@Mock private SupervisedTrainingRecord record1;
	@Mock private SupervisedTrainingRecord record2;

	private SupervisedTrainingSet systemUnderTest;

	@Before
	public void setUp() {
		initMocks(this);
		systemUnderTest = new SupervisedTrainingSet(Arrays.asList(record1, record2));
	}

	@Test
	public void shouldPublishRecords() {
		assertThat(systemUnderTest.getRecords(), contains(record1, record2));
	}

	@Test(expected = UnsupportedOperationException.class)
	public void shouldFailToModifyReturnedRecords() {
		systemUnderTest.getRecords().clear();
	}

	@Test
	public void shouldInsulateFromInputRecordCollectionChanges() {
		List<SupervisedTrainingRecord> records = new ArrayList<>(Arrays.asList(record1, record2));
		systemUnderTest = new SupervisedTrainingSet(records);
		records.clear();
		assertThat(systemUnderTest.getRecords(), contains(record1, record2));
	}

	@Test(expected = NullPointerException.class)
	public void shouldFailToConstructWithNullRecords() {
		new SupervisedTrainingSet(null);
	}

	@Test(expected = NullPointerException.class)
	public void shouldFailToConstructWithNullRecordValue() {
		new SupervisedTrainingSet(Arrays.asList(record1, null));
	}

	@Test
	public void shouldCollectInputs() {
		when(record1.getInputs()).thenReturn(Arrays.asList(5D, 6D));
		when(record2.getInputs()).thenReturn(Arrays.asList(7D, 8D));
		assertThat(systemUnderTest.getInputs(),
				contains(Arrays.asList(equalTo(record1.getInputs()), equalTo(record2.getInputs()))));
	}

	@Test
	public void shouldCollectExpectedOutputs() {
		when(record1.getExpectedOutputs()).thenReturn(Arrays.asList(2D, 4D));
		when(record2.getExpectedOutputs()).thenReturn(Arrays.asList(3D, 5D));
		assertThat(systemUnderTest.getExpectedOutputs(),
				contains(Arrays.asList(equalTo(record1.getExpectedOutputs()), equalTo(record2.getExpectedOutputs()))));
	}
}
