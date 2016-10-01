public class NeuralNetwork {
	private Layer InputLayer;
	private Layer[] HiddenLayers;
	private Layer OutputLayer;
	
	//constructor
	public NeuralNetwork(int nInputNeurons, int nHiddenLayers, int[] nHiddenNeurons, int nOutputNeurons) {
		//input layer initialization
		InputLayer = new Layer(nInputNeurons);
		InputLayer.initializeNeurons(0);
		//hidden layer initialization
		HiddenLayers = new Layer[nHiddenLayers];
		for(int i=0; i < nHiddenLayers; i++) {
			HiddenLayers[i] = new Layer(nHiddenNeurons[i]);
			if(i==0)
				HiddenLayers[i].initializeNeurons(nInputNeurons);
			else
				HiddenLayers[i].initializeNeurons(nHiddenNeurons[i-1]);
		}
		//output layer initialization
		OutputLayer = new Layer(nOutputNeurons);
		OutputLayer.initializeNeurons(nHiddenNeurons[nHiddenNeurons.length-1]);
	}

	//updates the values of the outputs of each neuron
	public void updateNeuronValues() {
		for(int i=0; i < HiddenLayers.length; i++) {
			if(i > 0)
				for(int j=0; j < HiddenLayers[i].neuronsNumber(); j++) {
					float A = HiddenLayers[i].neuron(j).getBias();
					for(int k=0; k < HiddenLayers[i-1].neuronsNumber(); k++)
						A += HiddenLayers[i-1].neuron(k).getValue()*HiddenLayers[i].neuron(j).getW(k);
					HiddenLayers[i].neuron(j).setValue((float)(1/(1+Math.exp(-A))));
				}
			else
				for(int j=0; j < HiddenLayers[i].neuronsNumber(); j++) {
					float A = HiddenLayers[i].neuron(j).getBias();
					for(int k=0; k < InputLayer.neuronsNumber(); k++)
						A += InputLayer.neuron(k).getValue()*HiddenLayers[i].neuron(j).getW(k);
					HiddenLayers[i].neuron(j).setValue((float)(1/(1+Math.exp(-A))));
				}
		}
		for(int i=0; i < OutputLayer.neuronsNumber(); i++) {
			float A = OutputLayer.neuron(i).getBias();
			for(int k=0; k < HiddenLayers[HiddenLayers.length-1].neuronsNumber(); k++)
				A += HiddenLayers[HiddenLayers.length-1].neuron(k).getValue()*OutputLayer.neuron(i).getW(k);
			OutputLayer.neuron(i).setValue((float)(1/(1+Math.exp(-A))));
		}
	}
	
	//sets the inputs to a specified value
	public void setInputs(float[] values) {
		for(int i=0; i < InputLayer.neuronsNumber(); i++)
			InputLayer.neuron(i).setValue(values[i]);
	}
	
// alters the values of each weight
	public void modifyWeights(float[] D, float Eps) {
		float[] DeltaPrev;
		float[] Delta;
		//output layer weigths modification
		DeltaPrev = new float[OutputLayer.neuronsNumber()];
		for(int i=0; i < OutputLayer.neuronsNumber(); i++) {
			DeltaPrev[i] = (D[i]-OutputLayer.neuron(i).getValue())*OutputLayer.neuron(i).getValue()*(1-OutputLayer.neuron(i).getValue());
			OutputLayer.neuron(i).alterBias(DeltaPrev[i]*Eps);
			for(int j=0; j < HiddenLayers[HiddenLayers.length-1].neuronsNumber(); j++)
				OutputLayer.neuron(i).alterWeight(j, DeltaPrev[i]*Eps*HiddenLayers[HiddenLayers.length-1].neuron(j).getValue());
		}
		//backpropagation
		for(int i=HiddenLayers.length-1; i >= 0 ; i--) {
			Delta = new float[HiddenLayers[i].neuronsNumber()];
			for(int j=0; j < HiddenLayers[i].neuronsNumber(); j++) {
				float Err = 0;
				if(i==HiddenLayers.length-1)
					for(int k=0; k < OutputLayer.neuronsNumber(); k++)
						Err += DeltaPrev[k]*OutputLayer.neuron(k).getW(j);
				else
					for(int k=0; k < HiddenLayers[i+1].neuronsNumber(); k++)
						Err += DeltaPrev[k]*HiddenLayers[i+1].neuron(k).getW(j);
				Delta[j] = Err*HiddenLayers[i].neuron(j).getValue()*(1-HiddenLayers[i].neuron(j).getValue());
				if(i>0)
					for(int l=0; l < HiddenLayers[i-1].neuronsNumber(); l++)
						HiddenLayers[i].neuron(j).alterWeight(l, Delta[j]*Eps*HiddenLayers[i-1].neuron(l).getValue());
				else
					for(int l=0; l < InputLayer.neuronsNumber(); l++)
						HiddenLayers[i].neuron(j).alterWeight(l, Delta[j]*Eps*InputLayer.neuron(l).getValue());
				DeltaPrev = new float[Delta.length];
				for(int m=0; m < Delta.length; m++)
					DeltaPrev[m] = Delta[m];
			}
		}
	}

	public float maxError(float[] D) {
		float Err = 0;
		for(int i=0; i < D.length; i++)
			Err = (Math.abs(D[i]-OutputLayer.neuron(i).getValue())>Err)?Math.abs(D[i]-OutputLayer.neuron(i).getValue()):Err;
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
		out += "Input = \n" + InputLayer + "\n";
		for(int i=0; i < HiddenLayers.length; i++)
			out += "H" + (i+1) + " = \n" + HiddenLayers[i] + "\n";
		out += "Output = \n" + OutputLayer + "\n";
		return out;
	}

	public float[] getOutput() {
		float[] out = new float[OutputLayer.neuronsNumber()];
		for(int i=0; i < out.length; i++)
			out[i] = OutputLayer.neuron(i).getValue();
		return out;
	}

	public Layer getXLayer() {
		return InputLayer;
	}

	public Layer getYLayer() {
		return OutputLayer;
	}

	public Layer[] getHLayer() {
		return HiddenLayers;
	}
}