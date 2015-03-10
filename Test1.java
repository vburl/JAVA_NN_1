public class Test1 {
	public static void main(String[] args) {
		/*int[] a = {4};
		float[][] I = new float[17][1];
		float[][] D = new float[17][1];
		int cycles = 10000;
		NeuralNetwork neu = new NeuralNetwork(1,1,a,1);
		for(int j=0; j < cycles; j++) {
			for(int i=0; i < 17; i++) {
				I[i][0] = (float)((i*22.5)/360);
				D[i][0] = (float)(0.1+(0.8*(Math.sin(Math.toRadians(i*22.5))+1)/2));
				neu.learn(I,D,(float)0.5);
				//System.out.println("Input " + i + " = " + I[i][0] + "\nOutput " + i + " = " + D[i][0]);
			}
			System.out.println(((float)j)*100/cycles + "%");
		}
		for(int i=0; i < 17; i++) {
			float[] In = {(float)((i*22.5)/360)};
			float[] Out = {(float)(0.1+(0.8*(Math.sin(Math.toRadians(i*22.5))+1)/2))};
			neu.setInputs(In);
			neu.updateNeuronValues();
			System.out.println("Input = " + In[0] + "\nY = " + neu.getOutput()[0] +"\nMax error = " + neu.maxError(Out));
		}
		System.out.println(neu);*/
		int[] a = {8,10,6,8,9,7};
		NeuralNetwork neu = new NeuralNetwork(7,6,a,9);
		int[] padd = {100,70,70,50};
		GUI gui = new GUI(neu, padd,25);
		for(int i=0; i < 1000000; i++) {
			for(int j=0; j <= 10; j++) {
				float[] I = {(float)(((((float)(j))/10)*0.6)+0.2),(float)0.5,1-(float)(((((float)(j))/10)*0.6)+0.2)};
				float[] D = {1-(float)(((((float)(j))/10)*0.6)+0.2),(float)(((((float)(j))/10)*0.6)+0.2),(float)0.5};
				neu.setInputs(I);
				neu.updateNeuronValues();
				neu.modifyWeights(D,(float)0.5);
				neu.updateNeuronValues();
			}
		}
		for(int i=0; i <= 10; i++) {
			float[] I = {(float)(((((float)(i))/10)*0.6)+0.2),(float)0.5,1-(float)(((((float)(1))/10)*0.6)+0.2)};
			neu.setInputs(I);
			neu.updateNeuronValues();
			System.out.println("In: "+I[0]+", "+I[1]+", "+I[2]+"  Out: "+neu.getOutput()[0]+", "+neu.getOutput()[1]+", "+neu.getOutput()[2]);
		}
		/*for(int i=0; i < 4000000; i++)
			for(int j=0; j < 15; j++) {
				float[] in = {(float)((((float)(j))/18.75)+0.1)};
				float[] out = {(float)((int)(j/8)-0.5), (float)((int)((j%8)/4)-0.5), (float)((int)(((j%8)%4)/2)-0.5), (float)((j%2)-0.5)};
				neu.setInputs(in);
				neu.updateNeuronValues();
				neu.modifyWeights(out,(float)0.5);
				neu.updateNeuronValues();
			}
		for(int j=0; j < 15; j++) {
			float[] in = {(float)((((float)(j))/18.75)+0.1)};
			float[] out = {(float)(int)(j/8), (float)(int)((j%8)/4), (float)(int)(((j%8)%4)/2), (float)(j%2)};
			System.out.println(out[0] + "  " + out[1] + "  " + out[2] + "  " + out[3]);
			neu.setInputs(in);
			neu.updateNeuronValues();
			System.out.println("In = " + in[0] + "   out = " + neu.getOutput()[0] + " " + neu.getOutput()[1] + " " + neu.getOutput()[2] + " " + neu.getOutput()[3]);
		}*/
	}
}