# NeuralNetwork
Simple Java program that simulates simple Neural Networks

This Java application provides classes to create feedforward neural networks of virtually any size, and allows the user to train them using the supervised paradigm.

This is achieved by repeatedly feeding the network a set of different inputs and the correspondent outputs, called the training set, until the network output differs from the original output by less than a given acceptable rate of error.

The network is composed of an input layer, and output layer and as many hidden layers as desired.
Each layer can include any number of neurons, each of which follows the Rosenblatt's Perceptron model with a sigmoid activation function.

The learning process uses the backpropagation, which propagates the error on the ouput layer to the previous layers, altering the threshold and the weights of each synapsis.
