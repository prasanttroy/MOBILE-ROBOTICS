import java.rmi.server.ExportException;
import java.text.DecimalFormat;
import java.util.Random;


public class Main {

	private static int numInputs=3;
	private static int numHidden=30;
	private static int numOutput=4;
	
	
	private static double alpha=0.4;	//alpha is the learning rate. Used in backprop
	private static double noiseFactor=0.45;
	private static int trainingCycles=10000;
	
	private static double wIh[][] = new double[numInputs + 1][numHidden];	//weights comming into hidden layer
	private static double whO[][] = new double[numHidden + 1][numOutput];	//weights going out of hidden layer
	
	private static double[] inputs = new double[numInputs]; //input neuron array
	private static double[] hidden=new double[numHidden];	//hidden neuron array
	private static double[] expected=new double[numOutput];	//expected output array
	private static double[] predicted=new double[numOutput];//actuall predicted array
	
	private static double[] errForOP=new double[numOutput]; //error of output
	private static double[] errForH=new double[numHidden]; //error of hidden layer
	
	private static int samples=28;
	
	private static double trainInputs[][] = new double[][] {{0.9,0.9,0.9},
		{0.9,0.6,0.9},
		{0.9,0.4,0.9},
		{0.9,0.9,0.6},
		{0.9,0.9,0.4},
		{0.9,0.9,0.3},
		{0.9,0.9,0.2},
		{0.9,0.9,0.1},
		{0.9,0.9,0.05},
		{0.6,0.9,0.9},
		{0.4,0.9,0.9},
		{0.3,0.9,0.9},
		{0.2,0.9,0.9},
		{0.1,0.9,0.9},
		{0.05,0.9,0.9},
		{0.6,0.6,0.9},
		{0.4,0.4,0.9},
		{0.3,0.3,0.9},
		{0.2,0.2,0.9},
		{0.1,0.1,0.9},
		{0.9,0.6,0.6},
		{0.9,0.4,0.4},
		{0.9,0.3,0.3},
		{0.9,0.2,0.2},
		{0.9,0.1,0.1},
	//adding for front sensor
		{0.9,0.3,0.9},
		{0.9,0.2,0.9},
		{0.9,0.1,0.9},
	};
		//{{0.9},{0.7},{0.6},{0.5},{0.4},{0.2}};
		/*{{1, 1, 1, 0}, 
		{1, 1, 0, 0}, 
		{0, 1, 1, 0}, 
		{1, 0, 1, 0}, 
		{1, 0, 0, 0}, 
		{0, 1, 0, 0}, 
		{0, 0, 1, 0}, 
		{1, 1, 1, 1}, 
		{1, 1, 0, 1}, 
		{0, 1, 1, 1}, 
		{1, 0, 1, 1}, 
		{1, 0, 0, 1}, 
		{0, 1, 0, 1}, 
		{0, 0, 1, 1}};
	*/
	private static double trainOutput[][] = new double[][] {{0.60,1,0.60,1},
		{0.55,1,0.55,1},
		{0.55,1,0.55,1},
		{0.55,1,0.55,1},
		{0.55,1,0.55,1},
		{0.20,1,0.55,1},
		{0.40,0,0.55,1},
		{0.44,0,0.55,1},
		{0.48,0,0.55,1},
		{0.55,1,0.55,1},
		{0.55,1,0.55,1},
		{0.55,1,0.20,1},
		{0.55,1,0.40,0},
		{0.55,1,0.44,0},
		{0.55,1,0.48,0},
		{0.55,1,0.55,1},
		{0.55,1,0.20,1},
		{0.55,1,0.35,0},
		{0.55,1,0.40,0},
		{0.55,1,0.45,0},
		{0.55,1,0.55,1},
		{0.20,1,0.55,1},
		{0.35,0,0.55,1},
		{0.40,0,0.55,1},
		{0.45,0,0.55,1},
		//
		{0.40,0,0.20,0},
		{0.45,0,0.30,1},
		{0.40,0,0.40,1},
		};// {{0.60,1,0.60,1},{0.60,1,0.45,1},{0.60,1,0.4,0},{0.57,1,0.57,0},{0.6,1,0.6,0},{0.62,1,0.62,0}};
	  /*                                             {{1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, 
	                                                {0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, 
	                                                {0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, 
	                                                {0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, 
	                                                {0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0}, 
	                                                {0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0}, 
	                                                {0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0}, 
	                                                {0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0}, 
	                                                {0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0}, 
	                                                {0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0}, 
	                                                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0}, 
	                                                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0}, 
	                                                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0}, 
	                                                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1}};*/
	private static double testInputs[][] = new double[][]  {
		{0.9,0.9,0.4},
		{0.9,0.9,0.3},
		{0.9,0.9,0.2},
		{0.9,0.9,0.1},
		{0.9,0.9,0.05},
		{0.6,0.9,0.9},
		{0.4,0.9,0.9},
		{0.3,0.9,0.9},
		{0.2,0.9,0.9},
		{0.1,0.9,0.9},
		{0.05,0.9,0.9},
		{0.6,0.6,0.9},
		{0.9,0.1,0.9}};//{{0.82},{0.65},{0.48},{0.27}};
	
	public static void main(String[] args) {
		int currSample=0;
		assignRandWeights();
		for(int epoch=0;epoch<trainingCycles;epoch++){
			currSample++;
			
			if(currSample==samples){
				currSample=0;
			}
			for(int i=0;i<numInputs;i++){
				inputs[i]=trainInputs[currSample][i];
			}
			for(int i=0;i<numOutput;i++){
				expected[i]=trainOutput[currSample][i];
				//System.out.println("Expected"+expected[i]);
			}
			feedForward();
			backProp();
		}
		
		//getAccuracy(trainInputs,trainOutput);
		
        System.out.println("\nTest network against original input:");
        testNetwork(testInputs);
		
        
       // System.out.println("\nTest network against noisy input:");
       // double[][] noised=generateNoiseData();
        //getAccuracy(noised, trainOutput);
        //testNetwork(noised);
        
	}
	
	//Get the accuracy for inputs when the expected output is given
	private static void getAccuracy(double[][] input,double[][] trainOutput2){
		double sum=0.0;
		for(int i=0;i<samples;i++){
			for(int j=0;j<numInputs;j++){
				inputs[j]=input[i][j];
			}
			for(int j=0;j<numOutput;j++){
				expected[j]=trainOutput2[i][j];
				System.out.println("Expected"+expected[j]);
			}
			feedForward();
			if(maximumIdx(predicted)==maximumIdx(expected)){
				sum++;
			}else{
                System.out.println(inputs[0] + "\t" + inputs[1] + "\t" + inputs[2] + "\t" + inputs[3]);
                System.out.println(maximumIdx(predicted) + "\t" + maximumIdx(expected));
              }
		}
		System.out.println("Accuracy for training "+ sum*100/samples);
	}
	
	//This is to generate some noisy data for testing algorithm Accuracy
	private static double[][] generateNoiseData(){
		
		double[][] noised=new double[samples][numInputs];
		for(int i=0;i<samples;i++){
			for(int j=0;j<numInputs;j++){
				
				noised[i][j]= trainInputs[i][j] + new Random().nextDouble()*noiseFactor;
			}
			
		}
		return noised;
	}
	
	//test network is testing test data for expected and predicted
	private static void testNetwork(double[][] testInputs){
		DecimalFormat dfm = new java.text.DecimalFormat("###0.0");
		System.out.println();
		for(int i=0;i<testInputs.length;i++){
			for(int j=0;j<numInputs;j++){
				inputs[j]=testInputs[i][j];
			}
			feedForward();
			
			for(int j = 0; j < numInputs; j++)
            {
                System.out.print(dfm.format(inputs[j]) + "\t");

                
            } // j
			for(int k = 0 ; k < numOutput; k++ )
			{
				System.out.print("  Output: " + predicted[k] );
			}
			
			System.out.print("\n");
		}
	}
	
	private static void assignRandWeights(){
		for(int i=0;i<=numInputs;i++){
			for(int j=0;j<numHidden;j++){
				wIh[i][j]=(new Random().nextDouble())-0.5;
			}
		}
		
		for(int i=0;i<=numHidden;i++){
			for(int j=0;j<numOutput;j++){
				whO[i][j]=(new Random().nextDouble())-0.5;
			}
		}
	}
	
	//This is acctually the predicting part. Depending on the weights of the neurons
	//it tries to come up with a output that is the best match for given inputs.
	private static void feedForward(){
		double sum=0.0;
		for(int i=0;i<numHidden;i++){
			sum=0.0;
			for(int j=0;j<numInputs;j++){
				sum+=wIh[j][i]*inputs[j];
			}
			sum+=wIh[numInputs][i];
			hidden[i]=sigmoid(sum);
		}
		
		for(int i=0;i<numOutput;i++){
			sum=0.0;
			for(int j=0;j<numHidden;j++){
				sum+=hidden[j]*whO[j][i];
			}
			sum+=whO[numHidden][i];
			predicted[i]=sigmoid(sum);
		}
	}
	
	//Back prop is similar to learning from mistakes. We adjust the neuron weights depending on the
	//difference between the predicted and expected outputs.
	private static void backProp(){
		for(int i=0;i<numOutput;i++){
			errForOP[i]=(expected[i]-predicted[i])*sigmoidDerivative(predicted[i]);
		}
		
		for(int i=0;i<numHidden;i++){
			errForH[i]=0.0;
			for(int j=0;j<numOutput;j++){
				errForH[i]+=errForOP[j]*whO[i][j];
			}
			errForH[i]*=sigmoidDerivative(hidden[i]);
		}
		
		for(int i=0;i<numOutput;i++){
			for(int j=0;j<numHidden;j++){
				whO[j][i]+=(alpha*errForOP[i]*hidden[j]);
			}
			whO[numHidden][i]+=(alpha*errForOP[i]);
		}
		
		for(int i=0;i<numHidden;i++){
			for(int j=0;j<numInputs;j++){
				wIh[j][i]+=(alpha*errForH[i]*inputs[j]);
			}
			wIh[numInputs][i]+=(alpha*errForH[i]);
		}
		
		
	}
	
	//this is called the sigmoid function
	private static double sigmoid(double x){
		return 1.0/(1.0+Math.exp(-x));
	}
	//this is the derivate of the sigmoid function
	private static double sigmoidDerivative(double x){
		return x*(1.0-x);
	}
	
	private static int maximumIdx(final double[] vector)
    {
        // This function returns the index of the maximum of vector().
        int sel = 0;
        double max = vector[sel];

        for(int index = 0; index < numOutput; index++)
        {
            if(vector[index] > max){
                max = vector[index];
                sel = index;
            }
        }
        return sel;
    }

}
