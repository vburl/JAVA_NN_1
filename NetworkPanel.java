import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.*;
import java.awt.Rectangle;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JTable;

public class NetworkPanel extends JPanel {
	private int topPadding;
	private int bottomPadding;
	private int leftPadding;
	private int rightPadding;
	private int neuronHeight;
	private int height;
	private int width;
	private NeuralNetwork nn;
	private GUI gui;

	public NetworkPanel(GUI g, NeuralNetwork n, int[] paddings, int neuHeight, int w, int h){
		gui = g;
		nn = n;
		topPadding = paddings[0];
		rightPadding = paddings[1];
		bottomPadding = paddings[2];
		leftPadding = paddings[3];
		neuronHeight = neuHeight;
		width = w;
		height = h;
	}

	public void paintComponent(Graphics g) {
	Graphics2D g2 = (Graphics2D)g;
		g2.setColor(Color.BLACK);
		int maxNeuronsNumber = nn.getXLayer().neuronsNumber();
		maxNeuronsNumber = (nn.getYLayer().neuronsNumber() > maxNeuronsNumber)?nn.getYLayer().neuronsNumber():maxNeuronsNumber;
		for(int i=0; i < nn.getHLayer().length; i++)
			maxNeuronsNumber = (nn.getHLayer()[i].neuronsNumber() > maxNeuronsNumber)?nn.getHLayer()[i].neuronsNumber():maxNeuronsNumber;
		NPoint firstXCoord, firstYCoord;
		NPoint[] firstHCoord = new NPoint[nn.getHLayer().length];
		firstXCoord = new NPoint(leftPadding,(maxNeuronsNumber-nn.getXLayer().neuronsNumber())*neuronHeight+topPadding);
		firstYCoord = new NPoint(width-rightPadding-neuronHeight,(maxNeuronsNumber-nn.getYLayer().neuronsNumber())*neuronHeight+topPadding);
		for(int i=0; i < nn.getHLayer().length; i++)
			firstHCoord[i] = new NPoint(leftPadding+(i+1)*3*neuronHeight,(maxNeuronsNumber-nn.getHLayer()[i].neuronsNumber())*neuronHeight+topPadding);
		g2.setFont(new Font("Sanserif", Font.BOLD, 16));
		g2.drawString("X", firstXCoord.getX()+(neuronHeight/2), topPadding/2);
		g2.drawString("Y", firstYCoord.getX()+(neuronHeight/2), topPadding/2);
		for(int i=0; i < firstHCoord.length; i++)
		g2.drawString("H"+(i+1), firstHCoord[i].getX()+(neuronHeight/2), topPadding/2);
		for(int i=0; i < nn.getXLayer().neuronsNumber(); i++)
			for(int j=0; j < nn.getHLayer()[0].neuronsNumber(); j++)
				g2.draw(new Line2D.Double(firstXCoord.getX()+(neuronHeight/2), firstXCoord.getY()+(neuronHeight/2)+i*2*neuronHeight, firstHCoord[0].getX()+(neuronHeight/2),firstHCoord[0].getY()+(neuronHeight/2)+j*2*neuronHeight));
		for(int i=0; i < nn.getHLayer().length; i++)
			for(int j=0; j < nn.getHLayer()[i].neuronsNumber(); j++)
				if(i == nn.getHLayer().length-1)
					for(int k=0; k < nn.getYLayer().neuronsNumber(); k++)
						g2.draw(new Line2D.Double(firstHCoord[i].getX()+(neuronHeight/2), firstHCoord[i].getY()+(neuronHeight/2)+j*2*neuronHeight, firstYCoord.getX()+(neuronHeight/2),firstYCoord.getY()+(neuronHeight/2)+k*2*neuronHeight));
				else
					for(int k=0; k < nn.getHLayer()[i+1].neuronsNumber(); k++)
						g2.draw(new Line2D.Double(firstHCoord[i].getX()+(neuronHeight/2), firstHCoord[i].getY()+(neuronHeight/2)+j*2*neuronHeight, firstHCoord[i+1].getX()+(neuronHeight/2),firstHCoord[i+1].getY()+(neuronHeight/2)+k*2*neuronHeight));
		g2.setColor(Color.WHITE);
		for(int i=0; i < nn.getXLayer().neuronsNumber(); i++)
			g2.fill(new Ellipse2D.Double(firstXCoord.getX(),firstXCoord.getY()+i*2*neuronHeight,neuronHeight,neuronHeight));
		for(int i=0; i < nn.getYLayer().neuronsNumber(); i++)
			g2.fill(new Ellipse2D.Double(firstYCoord.getX(),firstYCoord.getY()+i*2*neuronHeight,neuronHeight,neuronHeight));
		for(int j=0; j < nn.getHLayer().length; j++)
			for(int i=0; i < nn.getHLayer()[j].neuronsNumber(); i++)
				g2.fill(new Ellipse2D.Double(firstHCoord[j].getX(),firstHCoord[j].getY()+i*2*neuronHeight,neuronHeight,neuronHeight));
		g2.setColor(Color.BLACK);
		for(int i=0; i < nn.getXLayer().neuronsNumber(); i++)
			g2.draw(new Ellipse2D.Double(firstXCoord.getX(),firstXCoord.getY()+i*2*neuronHeight,neuronHeight,neuronHeight));
		for(int i=0; i < nn.getYLayer().neuronsNumber(); i++)
			g2.draw(new Ellipse2D.Double(firstYCoord.getX(),firstYCoord.getY()+i*2*neuronHeight,neuronHeight,neuronHeight));
		for(int j=0; j < nn.getHLayer().length; j++)
			for(int i=0; i < nn.getHLayer()[j].neuronsNumber(); i++)
				g2.draw(new Ellipse2D.Double(firstHCoord[j].getX(),firstHCoord[j].getY()+i*2*neuronHeight,neuronHeight,neuronHeight));
		int columnsNumber = 0;
		columnsNumber += nn.getXLayer().neuronsNumber();
		for(int i=0; i < nn.getHLayer().length; i++)
			if(i==0)
				columnsNumber += (nn.getHLayer()[i].neuronsNumber())*(2+nn.getXLayer().neuronsNumber());
			else
				columnsNumber += (nn.getHLayer()[i].neuronsNumber())*(2+nn.getHLayer()[i-1].neuronsNumber());
		columnsNumber += (nn.getYLayer().neuronsNumber())*(2+nn.getHLayer()[nn.getHLayer().length-1].neuronsNumber());
		Object[][] tableData = new Object[1][columnsNumber];
		String[] columnsName = new String[columnsNumber];
		int index = 0;
		for(index=0; index < nn.getXLayer().neuronsNumber(); index++)
			columnsName[index] = "X" + (index+1);
		for(int j=0; j < nn.getHLayer().length; j++)
			for(int i=0; i<nn.getHLayer()[j].neuronsNumber(); i++) {
				columnsName[index++] = "H" + (j+1) + "-" + (i+1);
				columnsName[index++] = "Bias";
				for(int k=0; k < nn.getHLayer()[j].neuron(i).getWeights().length; k++)
					columnsName[index++] = "w" + (k+1);
			}
		for(int i=0; i < nn.getYLayer().neuronsNumber();i++) {
			columnsName[index++] = "Y" + (i+1);
			columnsName[index++] = "Bias";
			for(int k=0; k < nn.getYLayer().neuron(i).getWeights().length; k++)
				columnsName[index++] = "w" + (k+1);
		}
	}
}