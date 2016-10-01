public class Test2 {
	public static void main(String[] args) {
		int[] a = new int[]{4};
		NeuralNetwork neu = new NeuralNetwork(1,1,a,1);
		int[] padd = new int[]{100,70,70,50};
		GUI gui = new GUI(neu,padd,50);
		float[] I = new float[17];
		float[] D = new float[17];
		int cycles = 100000;
		for(int i=0; i < 17; i++) {
			I[i] = (float)((((i*22.5)/360)*0.8)+0.1);
			D[i] = (float)(0.1+(0.8*(Math.sin(Math.toRadians(i*22.5))+1)/2));
		}
		for(int j=0; j < cycles; j++) {
			for(int i=0; i < 17; i++) {
				neu.setInputs(new float[]{I[i]});
				neu.updateNeuronValues();
				//System.out.println("In: "+I[i]+" Out: "+D[i]+ "   NOut: "+ neu.getOutput()[0]);
				neu.modifyWeights(new float[]{D[i]},(float)0.5);
				neu.updateNeuronValues();
				//System.out.println("NOut: "+ neu.getOutput()[0]);
			}
		}
		for(int i=0; i < 17; i++) {
			neu.setInputs(new float[]{I[i]});
			neu.updateNeuronValues();
			System.out.println("In: "+I[i]+"  ExpectedOut: " + D[i] + "  Out: " + neu.getOutput()[0]);
		}
	}
}