public class Neuron {
	private float[] w;
	private float bias;
	private float value;

	public Neuron(int nW) {
		w = new float[nW];
	}

	//initializes the weights at random values
	public void initializeWeights() {
		for(int i=0; i < w.length; i++)
			w[i] = (float)Math.random();
		bias = (float)Math.random();
	}

	public void alterWeight(int i, float val) {
		w[i] += val;
	}

	public void alterBias(float val) {
		bias += val;
	}

	public float getValue() {
		return value;
	}

	public float getBias() {
		return bias;
	}

	public float getW(int i) {
		return w[i];
	}

	public float[] getWeights() {
		return w;
	}

	//ATTENTION this function should be used only to set the value of the input neurons
	public void setValue(float val) {
		value = val;
	}

	public String toString() {
		String out = "Bias = " + bias + "\n";
		for(int i=0; i < w.length; i++)
			out += "w[" + i + "] = " +w[i] + "\n";
		out += "value = " + value + "\n";
		return out;
	}
}