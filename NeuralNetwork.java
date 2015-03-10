public class NeuralNetwork {
	private Layer X;
	private Layer[] H;
	private Layer Y;
	
	//constructor
	public NeuralNetwork(int nInputNeurons, int nHiddenLayers, int[] nHiddenNeurons, int nOutputNeurons) {
		//input layer initialization
		X = new Layer(nInputNeurons);
		X.initializeNeurons(0);
		//hidden layer initialization
		H = new Layer[nHiddenLayers];
		for(int i=0; i < nHiddenLayers; i++) {
			H[i] = new Layer(nHiddenNeurons[i]);
			if(i==0)
				H[i].initializeNeurons(nInputNeurons);
			else
				H[i].initializeNeurons(nHiddenNeurons[i-1]);
		}
		//output layer initialization
		Y = new Layer(nOutputNeurons);
		Y.initializeNeurons(nHiddenNeurons[nHiddenNeurons.length-1]);
	}

	//updates the values of the outputs of each neuron
	public void updateNeuronValues() {
		for(int i=0; i < H.length; i++) {
			if(i > 0)
				for(int j=0; j < H[i].neuronsNumber(); j++) {
					float A = H[i].neuron(j).getBias();
					for(int k=0; k < H[i-1].neuronsNumber(); k++)
						A += H[i-1].neuron(k).getValue()*H[i].neuron(j).getW(k);
					H[i].neuron(j).setValue((float)(1/(1+Math.exp(-A))));
				}
			else
				for(int j=0; j < H[i].neuronsNumber(); j++) {
					float A = H[i].neuron(j).getBias();
					for(int k=0; k < X.neuronsNumber(); k++)
						A += X.neuron(k).getValue()*H[i].neuron(j).getW(k);
					H[i].neuron(j).setValue((float)(1/(1+Math.exp(-A))));
				}
		}
		for(int i=0; i < Y.neuronsNumber(); i++) {
			float A = Y.neuron(i).getBias();
			for(int k=0; k < H[H.length-1].neuronsNumber(); k++)
				A += H[H.length-1].neuron(k).getValue()*Y.neuron(i).getW(k);
			Y.neuron(i).setValue((float)(1/(1+Math.exp(-A))));
		}
	}
	
	//sets the inputs to a specified value
	public void setInputs(float[] values) {
		for(int i=0; i < X.neuronsNumber(); i++)
			X.neuron(i).setValue(values[i]);
	}
	
// alters the values of each weight
	public void modifyWeights(float[] D, float Eps) {
		float[] DeltaPrev;
		float[] Delta;
		//output layer weigths modification
		DeltaPrev = new float[Y.neuronsNumber()];
		for(int i=0; i < Y.neuronsNumber(); i++) {
			DeltaPrev[i] = (D[i]-Y.neuron(i).getValue())*Y.neuron(i).getValue()*(1-Y.neuron(i).getValue());
			Y.neuron(i).alterBias(DeltaPrev[i]*Eps);
			for(int j=0; j < H[H.length-1].neuronsNumber(); j++)
				Y.neuron(i).alterWeight(j, DeltaPrev[i]*Eps*H[H.length-1].neuron(j).getValue());
		}
		//backpropagation
		for(int i=H.length-1; i >= 0 ; i--) {
			Delta = new float[H[i].neuronsNumber()];
			for(int j=0; j < H[i].neuronsNumber(); j++) {
				float Err = 0;
				if(i==H.length-1)
					for(int k=0; k < Y.neuronsNumber(); k++)
						Err += DeltaPrev[k]*Y.neuron(k).getW(j);
				else
					for(int k=0; k < H[i+1].neuronsNumber(); k++)
						Err += DeltaPrev[k]*H[i+1].neuron(k).getW(j);
				Delta[j] = Err*H[i].neuron(j).getValue()*(1-H[i].neuron(j).getValue());
				if(i>0)
					for(int l=0; l < H[i-1].neuronsNumber(); l++)
						H[i].neuron(j).alterWeight(l, Delta[j]*Eps*H[i-1].neuron(l).getValue());
				else
					for(int l=0; l < X.neuronsNumber(); l++)
						H[i].neuron(j).alterWeight(l, Delta[j]*Eps*X.neuron(l).getValue());
				DeltaPrev = new float[Delta.length];
				for(int m=0; m < Delta.length; m++)
					DeltaPrev[m] = Delta[m];
			}
		}
	}

	public float maxError(float[] D) {
		float Err = 0;
		for(int i=0; i < D.length; i++)
			Err = (Math.abs(D[i]-Y.neuron(i).getValue())>Err)?Math.abs(D[i]-Y.neuron(i).getValue()):Err;
		return Err;
	}

	public int learn(float[] I, float[] D, float Eps, float maxErr) {
		int cycles = 0;
		while(maxErr <= maxError(D)) {
			cycles++;
			setInputs(I);
			updateNeuronValues();
			modifyWeights(D,Eps);
			updateNeuronValues();
		}
		return cycles;
	}

	public void learn(float[][] I, float[][] D, float Eps) {
		for(int i=0; i < I.length; i++) {
			setInputs(I[i]);
			updateNeuronValues();
			modifyWeights(D[i],Eps);
		}
	}

	public String toString() {
		String out = "";
		out += "X = \n" + X + "\n";
		for(int i=0; i < H.length; i++)
			out += "H" + (i+1) + " = \n" + H[i] + "\n";
		out += "Y = \n" + Y + "\n";
		return out;
	}

	public float[] getOutput() {
		float[] out = new float[Y.neuronsNumber()];
		for(int i=0; i < out.length; i++)
			out[i] = Y.neuron(i).getValue();
		return out;
	}

	public Layer getXLayer() {
		return X;
	}

	public Layer getYLayer() {
		return Y;
	}

	public Layer[] getHLayer() {
		return H;
	}
}