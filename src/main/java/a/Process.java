package a;

/**
 *
 * @author nikolai
 */
import java.util.Random;
import java.util.Vector;

public class Process {

    //int swarmSize = 5;
    //int swarmSize = 10;
    int swarmSize = 10;
    int maxIteration = 30;
    int dimension = 2;
    double c1 = 2.0;
    double c2 = 2.0;
    double upperbound = 1.0;
    double lowerbound = 0.0;

    private Vector<Particle> swarm = new Vector<Particle>();
    private double[] pBest = new double[swarmSize];
    private Vector<Location> pBestLocation = new Vector<Location>();
    private double gBest;
    private Location gBestLocation;
    private double[] fitnessValueList = new double[swarmSize];

    Random ranGen = new Random();

    public void executeSwarm() {
        initSwarm();
        updateFitnessList();

        for (int i = 0; i < swarmSize; i++) {
            pBest[i] = fitnessValueList[i];
            pBestLocation.add(swarm.get(i).getLocation());
        }

        int t = 0;
        double w;
        double err = 9999;

        while (t < maxIteration && err > Problem.ERR_TOLERANCE) {
//            System.out.println("t: "+t);
//            System.out.println("maxIteration: "+maxIteration);
//            
//            System.out.println("err: "+err);
            System.out.println("Problem: "+Problem.ERR_TOLERANCE);
            for (int i = 0; i < swarmSize; i++) {
                if (fitnessValueList[i] < pBest[i]) {
                    pBest[i] = fitnessValueList[i];
                    pBestLocation.set(i, swarm.get(i).getLocation());
                }
            }

            int bestParticleIndex = Utility.getMinPos(fitnessValueList);
            if (t == 0 || fitnessValueList[bestParticleIndex] < gBest) {
                gBest = fitnessValueList[bestParticleIndex];
                gBestLocation = swarm.get(bestParticleIndex).getLocation();
            }

            w = upperbound - (((double) t) / maxIteration) * (upperbound - lowerbound);

            for (int i = 0; i < swarmSize; i++) {
                double r1 = ranGen.nextDouble();
                double r2 = ranGen.nextDouble();

                Particle p = swarm.get(i);

                double[] newVel = new double[dimension];
                newVel[0] = (w * p.getVelocity().getPos()[0])
                        + (r1 * c1) * (pBestLocation.get(i).getLoca()[0] - p.getLocation().getLoca()[0])
                        + (r2 * c2) * (gBestLocation.getLoca()[0] - p.getLocation().getLoca()[0]);
                newVel[1] = (w * p.getVelocity().getPos()[1])
                        + (r1 * c1) * (pBestLocation.get(i).getLoca()[1] - p.getLocation().getLoca()[1])
                        + (r2 * c2) * (gBestLocation.getLoca()[1] - p.getLocation().getLoca()[1]);
                Velocity vel = new Velocity(newVel);
                p.setVelocity(vel);

                double[] newLoc = new double[dimension];
                newLoc[0] = p.getLocation().getLoca()[0] + newVel[0];
                newLoc[1] = p.getLocation().getLoca()[1] + newVel[1];
                Location loc = new Location(newLoc);
                p.setLocation(loc);
            }

            err = Problem.evaluate(gBestLocation) - 0; 

            System.out.println("Iteration" + t + ": ");
            System.out.println("     Best Location X: " + gBestLocation.getLoca()[0]);
            System.out.println("     Best Location Y: " + gBestLocation.getLoca()[1]);
            System.out.println("     Value: " + Problem.evaluate(gBestLocation));

            t++;
            updateFitnessList();
        }
        
        

        System.out.println("\nSolution found at iteration " + (t - 1) + ", the solutions is:");
        System.out.println("     Best Location X: " + gBestLocation.getLoca()[0]);
        System.out.println("     Best Location Y: " + gBestLocation.getLoca()[1]);
    }

    public void initSwarm() {
        Particle p;
        for (int i = 0; i < swarmSize; i++) {
            p = new Particle();

            double[] loc = new double[dimension];
            loc[0] = Problem.LOC_X_LOW + ranGen.nextDouble() * (Problem.LOC_X_HIGH - Problem.LOC_X_LOW);
            loc[1] = Problem.LOC_Y_LOW + ranGen.nextDouble() * (Problem.LOC_Y_HIGH - Problem.LOC_Y_LOW);
            Location location = new Location(loc);

            double[] vel = new double[dimension];
            vel[0] = Problem.VEL_LOW + ranGen.nextDouble() * (Problem.VEL_HIGH - Problem.VEL_LOW);
            vel[1] = Problem.VEL_LOW + ranGen.nextDouble() * (Problem.VEL_HIGH - Problem.VEL_LOW);
            Velocity velocity = new Velocity(vel);

            p.setLocation(location);
            p.setVelocity(velocity);
            swarm.add(p);
        }
    }

    public void updateFitnessList() {
        for (int i = 0; i < swarmSize; i++) {
            fitnessValueList[i] = swarm.get(i).getFitnessValue();
        }
    }
}
