import nxtfuzzylogic.*;
import lejos.nxt.*;
import java.util.Random;


public class NNwall {

	private static int numInputs=3;
	private static int numHidden=25;
	private static int numOutput=4;
	
	
	private static double alpha=0.3;	//alpha is the learning rate. Used in backprop
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
	
	private static double trainInputs[][] = new double[][]{{0.7,0.7,0.7},
		{0.7,0.6,0.7},
		{0.7,0.4,0.7},
		{0.7,0.3,0.7},
		{0.7,0.2,0.7},
		{0.7,0.1,0.7},
		{0.7,0.7,0.6},
		{0.7,0.7,0.4},
		{0.7,0.7,0.3},
		{0.7,0.7,0.2},
		{0.7,0.7,0.1},
		{0.7,0.7,0.05},
		{0.6,0.7,0.7},
		{0.4,0.7,0.7},
		{0.3,0.7,0.7},
		{0.2,0.7,0.7},
		{0.1,0.7,0.7},
		{0.05,0.7,0.7},
		{0.6,0.6,0.7},
		{0.4,0.4,0.7},
		{0.3,0.3,0.7},
		{0.2,0.2,0.7},
		{0.1,0.1,0.7},
		{0.7,0.6,0.6},
		{0.7,0.4,0.4},
		{0.7,0.3,0.3},
		{0.7,0.2,0.2},
		{0.7,0.1,0.1}};
	
	private static double trainOutput[][] = new double[][]{{0.50,1,0.50,1},
		{0.55,1,0.55,1},
		{0.55,1,0.55,1},
		{0.20,1,0.55,1},
		{0.20,0,0.55,1},//comment
		{0.40,0,0.55,1},
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
		{0.45,0,0.55,1}
		};
			//{{0.60,1,0.60,1},{0.60,1,0.45,1},{0.60,1,0.4,0},{0.57,1,0.57,0},{0.6,1,0.6,0},{0.62,1,0.62,0}};
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
	private static double testInputs[][] = new double[][] {{0.82,0.65,0.48}};
	
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
        NXTMotor mA = new NXTMotor(MotorPort.A);
    	NXTMotor mB = new NXTMotor(MotorPort.B);
    	UltrasonicSensor us1 =new UltrasonicSensor(SensorPort.S1);
    	UltrasonicSensor us2 =new UltrasonicSensor(SensorPort.S2);
    	UltrasonicSensor us3 =new UltrasonicSensor(SensorPort.S3);
    	LCD.drawInt(100,1,5);
    	int dist1,dist2,dist3;
    	Button.ENTER.waitForPress();
    	   while (!Button.ESCAPE.isPressed())
    	   {	
    		  // LCD.refresh();
    		   dist1 = us1.getDistance();
    		   dist2 = us2.getDistance();
    		   dist3 = us3.getDistance();
    		  // LCD.drawInt(dist1,1,5);
    		   testInputs[0][0]=dist1+5/100;
    		   testInputs[0][1]=dist2+5/100;
    		   testInputs[0][2]=dist3+5/100;
    		   testNetwork(testInputs);
    		   mA.setPower((int)(predicted[0]*100) - 10);
    		  // LCD.drawInt((int)(predicted[0]*100),1,6);
    		   if(predicted[1] >= 0.5)
    			   mA.forward();
    		   else
    			   mA.backward();
    		   mB.setPower((int)(predicted[2]*100) - 10);
    		 //  LCD.drawInt((int)(predicted[2]*100),1,7);
    		   if(predicted[3] >= 0.5)
    			   mB.forward();
    		   else
    			   mB.backward();
    	       
    	   }
        
		
        
       // System.out.println("\nTest network against noisy input:");
       // double[][] noised=generateNoiseData();
        //getAccuracy(noised, trainOutput);
        //testNetwork(noised);
        
	}
	
	//Get the accuracy for inputs when the expected output is given

	//test network is testing test data for expected and predicted
	private static void testNetwork(double[][] testInputs){
		//DecimalFormat dfm = new java.text.DecimalFormat("###0.0");
		System.out.println();
		for(int i=0;i<1;i++){
			for(int j=0;j<numInputs;j++){
				inputs[j]=testInputs[i][j];
			}
			feedForward();
			
			
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
