/* Copyright Steven Eddies, 2017. See the LICENCE file in the project root. */

package uk.me.eddies.lib.neuron.neuralnet.training.supervised;

import static java.util.Collections.unmodifiableList;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Represents a single data record of supervised training data.
 */
public class SupervisedTrainingRecord {

	private final List<Double> inputs;
	private final List<Double> expectedOutputs;

	public SupervisedTrainingRecord(List<Double> inputs, List<Double> expectedOutputs) {
		this.inputs = unmodifiableList(new ArrayList<>(inputs));
		this.inputs.forEach(Objects::requireNonNull);
		this.expectedOutputs = unmodifiableList(new ArrayList<>(expectedOutputs));
		this.expectedOutputs.forEach(Objects::requireNonNull);
	}

	public List<Double> getInputs() {
		return inputs;
	}

	public List<Double> getExpectedOutputs() {
		return expectedOutputs;
	}
}
