/* Copyright Steven Eddies, 2017. See the LICENCE file in the project root. */

package uk.me.eddies.lib.neuron.neuralnet;

/**
 * Represents a one-directional connection to another neuron. The
 * {@link Connection} refers to the connectee, and the connector may refer to
 * many connections.
 */
public class Connection {

	private final Neuron connectee;
	private double weight = 0d;
	
	public Connection(Neuron connectee) {
		this.connectee = connectee;
	}
	
	public double getWeight() {
		return weight;
	}
	
	public void setWeight(double weight) {
		this.weight = weight;
	}
	
	public double getWeightedConnecteeValue() {
		return connectee.getValue() * getWeight();
	}
}
