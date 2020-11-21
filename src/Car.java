/*
MODIFY THIS CLASS TO MATE TWO CARS AND MUTATE CARS

Your code will go in methods BREED() and MUTATE().  Find the TODO lines.
  you will call these methods from your code in GeneticCars

A "Car" is a collection of balls and links
*/

public class Car
{
	//how many balls in the car
	int nodes;
	//position of balls
	int[] balls_x;
	int[] balls_y;
	//for every ball i,j  true if there's a link between them
	boolean[][] linkmatrix;

	//these are set by the setScore function after a simulated race
	double score_position;		//how far did the car get
	double score_iterations;	//how long did it take the car to reach the end

	//the simulated world the car is running in.  null until the car is raced.
	World world;

	//construct a car with nodes balls and random links
	//every ball is placed between (5,5) and (50,50)

	public Car(int nodes)
	{
		this.world=null;
		this.nodes=nodes;

		balls_x=new int[nodes];
		balls_y=new int[nodes];
		linkmatrix=new boolean[nodes][nodes];

		//randomly place balls between (5,5 and 50,50)
		for(int i=0; i<nodes; i++)
		{
			balls_x[i]=randint(5,50);
			balls_y[i]=randint(5,50);
		}

		//assign a link between two balls with probability 1/3
		for(int i=0; i<nodes; i++)
		{
			for(int j=0; j<nodes; j++)
			{
				if(randint(1,3)==1)
					linkmatrix[i][j]=true;
			}
		}
	}

	//return the average x position of the nodes
	//this is called only after the car has been raced
	public double getPosition()
	{
		int sum=0;
		for(int i=0; i<nodes; i++)
			sum+=world.getBall(i).position.x;
		return sum/nodes;
	}

	//set the car's score
	//this is called once the race simulation is done
		//don't call it before then or you'll get a nullpointerexception
	public void setScore(int iterations)
	{
		score_position=getPosition();
		if(score_position>world.WIDTH)
			score_position=world.WIDTH;
		score_iterations=iterations;
	}

	//build the car into the world: create its balls and links
	//call this when you're ready to start racing
	public void constructCar(World world)
	{
		this.world=world;
		for(int i=0; i<nodes; i++)
		{
			world.makeBall(balls_x[i],balls_y[i]);
		}
		for(int i=0; i<nodes; i++)
			for(int j=0; j<nodes; j++)
				if(linkmatrix[i][j])
					world.makeLink(i,j);
	}

	//returns a random integer between [a,b]
	private int randint(int a, int b)
	{
		return (int)(Math.random()*(b-a+1)+a);
	}

	//TODO
	//YOU WRITE THIS FUNCTION
	//It should return a "child" car that is the crossover between this car and parameter car c
	public Car breed(Car c)
	{
		Car child=new Car(nodes);
		//Choose a random crossover point.  Also choose a car to go first
		int cPoint = randint(0,nodes);
		// copy the balls from the first car's balls_x and balls_y to the child
		for(int i=0;i<cPoint;i++) {
            child.balls_x[i] = this.balls_x[i];
            child.balls_y[i] = this.balls_y[i];
        }
        for(int j = cPoint; j < nodes; j++) {
            // after the crossover, copy the balls_x and balls_y from the second car to the child
            child.balls_x[j] = c.balls_x[j];
            child.balls_y[j] = c.balls_y[j];
        }
		//pick a new crossover point, then do the same with the linkmatrix
        int lMatrixCPoint = randint(0,nodes);
        for(int i=0;i<lMatrixCPoint;i++) {
            for(int j=0;j<lMatrixCPoint;j++) {
                child.linkmatrix[i][j] = this.linkmatrix[i][j];
            }
        }
        for(int i=lMatrixCPoint;i<nodes;i++) {
            for(int j=lMatrixCPoint;j<nodes;j++) {
                child.linkmatrix[i][j] = c.linkmatrix[i][j];
            }
        }

		return child;
	}

	//TODO
	//YOU WRITE THIS FUNCTION
	//It should return a car "newcar" that is identical to the current car, except with mutations
	public Car mutate(double probability)
	{
		Car newcar=new Car(nodes);

		//YOUR WORK HERE
		//  You should copy over the car's balls_x and balls_y to newcar
		newcar.balls_x = this.balls_x;
		newcar.balls_y = this.balls_y;
		//with probability "probability", change the balls_x and balls_y to a random number from 5 to 50
		for(int i=0; i<newcar.nodes; i++){
			if (Math.random() < probability){
				newcar.balls_x[i]=randint(5,50);
				newcar.balls_y[i]=randint(5,50);
			}
		}
		//  Then copy over the links
		//	with probability "probability", set the link to true/false (50/50 chance)
		for(int i=0;i<nodes;i++) {
			for(int j=0;j<nodes;j++) {
				if (Math.random() < probability){
					if(newcar.linkmatrix[i][j]){
						newcar.linkmatrix[i][j] = false;
					}else{
						newcar.linkmatrix[i][j] = true;
					}
				}
			}
		}

		return newcar;
	}
}
