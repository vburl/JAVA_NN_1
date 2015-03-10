public class Layer {
	private Neuron[] neurons;

	public Layer(int n) {
		neurons = new Neuron[n];
	}

	public void initializeNeurons(int nW) {
		for(int i=0; i < neurons.length; i++) {
			neurons[i] = new Neuron(nW);
			neurons[i].initializeWeights();
		}
	}

	public int neuronsNumber() {
		return neurons.length;
	}

	public float[] neuronsValues() {
		float[] values = new float[neuronsNumber()];
		for(int i=0; i < values.length; i++)
			values[i] = neurons[i].getValue();
		return values;
	}

	public Neuron neuron(int i) {
		return neurons[i];
	}

	public String toString() {
		String out = "";
		for(int i=0; i < neurons.length; i++)
			out += "neuron[" + i +"] = \n" + neurons[i] + "\n";
		return out;
	}
}