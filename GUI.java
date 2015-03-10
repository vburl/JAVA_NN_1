import javax.swing.JFrame;

public class GUI extends JFrame {
	private int height;
	private int width;
	private NeuralNetwork nn;
	private NetworkPanel np;

	public GUI(NeuralNetwork n, int[] paddings, int neuHeight) {
		nn = n;
		int maxNeuronsNumber = nn.getXLayer().neuronsNumber();
		maxNeuronsNumber = (nn.getYLayer().neuronsNumber() > maxNeuronsNumber)?nn.getYLayer().neuronsNumber():maxNeuronsNumber;
		for(int i=0; i < nn.getHLayer().length; i++)
			maxNeuronsNumber = (nn.getHLayer()[i].neuronsNumber() > maxNeuronsNumber)?nn.getHLayer()[i].neuronsNumber():maxNeuronsNumber;
		NPoint firstXCoord, firstYCoord;
		int pHeight = (2*maxNeuronsNumber-1)*neuHeight + paddings[0] + paddings[2];
		int pWidth = ((1+nn.getHLayer().length)*3+1)*neuHeight + paddings[3] + paddings[1];
		np = new NetworkPanel(this,n,paddings,neuHeight, pWidth, pHeight);
		getContentPane().add(np);
		width = pWidth;
		height = pHeight;
		setSize(width,height);
		setTitle("Neural network");
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}
}