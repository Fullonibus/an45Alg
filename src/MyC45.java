import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.lang.Math;

public class MyC45 {

	public static void main(String[] args) throws IOException {
		// .csv data sets
		String[] files = {"data_sets/sport2.csv"};
		Scanner scan;
		
		// start loop for all files HERE
		scan = new Scanner(new File(files[0]));
		String headerLine = scan.nextLine();
		String[] headers = headerLine.split(",");
		
		// class index is assumed to be the last column
		int classIndex    = headers.length - 1;
		int numAttributes = headers.length - 1;
		
		// store data set attributes
		Attribute[] attributes = new Attribute[numAttributes];
		for(int x = 0; x < numAttributes; x++) {
			attributes[x] = new Attribute(headers[x]);
		}
		
		// for storing classes and class count
		List<String>  classes      = new ArrayList<String>();
		List<Integer> classesCount = new ArrayList<Integer>();
		
		// store are values into respected attributes
		// along with respected classes
		while(scan.hasNextLine()){
			Val data = null;
			String inLine = scan.nextLine();
			String[] lineData = inLine.split(",");
			
			// insert class into classes List
			if(classes.isEmpty()){
				classes.add(lineData[classIndex]);
				classesCount.add(classes.indexOf(lineData[classIndex]), 1);
			}
			else{
				if(!classes.contains(lineData[classIndex])){
					classes.add(lineData[classIndex]);
					classesCount.add(classes.indexOf(lineData[classIndex]), 1);
				}
				else {
					classesCount.set(classes.indexOf(lineData[classIndex]),
							classesCount.get(classes.indexOf(lineData[classIndex])) + 1);
				}
			}
			
			// insert data into attributes
			for(int x = 0; x < numAttributes; x++){
				data = new Val(lineData[x], lineData[classIndex]);
				attributes[x].insertVal(data);
			}
		}
		int totalNumClasses = 0;
		for(int i : classesCount){
			totalNumClasses += i;
		}
		double IofD = calcIofD(classesCount); // Set information criteria


		List<Integer> testCount = new ArrayList<Integer>();
		testCount.add(1);
		testCount.add(3);
		testCount.add(2);
		testCount.add(1);

		List<Integer> seasonCount = new ArrayList<Integer>();
		seasonCount.add(2);
		seasonCount.add(5);

		List<Integer> forceUseCount = new ArrayList<Integer>();
		forceUseCount.add(1);
		forceUseCount.add(4);
		forceUseCount.add(2);

		List<Integer> confrontationalCount = new ArrayList<Integer>();
		confrontationalCount.add(2);
		confrontationalCount.add(1);
		confrontationalCount.add(3);
		confrontationalCount.add(1);

		List<Integer> teamsCount = new ArrayList<Integer>();
		teamsCount.add(4);
		teamsCount.add(3);

		List<Integer> timeCount = new ArrayList<Integer>();
		timeCount.add(1);
		timeCount.add(5);
		timeCount.add(1);

		List<Integer> olympicCount = new ArrayList<Integer>();
		olympicCount.add(1);
		olympicCount.add(6);

		List<Integer> nameCount = new ArrayList<Integer>();
		nameCount.add(1);
		nameCount.add(1);
		nameCount.add(1);
		nameCount.add(1);
		nameCount.add(1);
		nameCount.add(1);
		nameCount.add(1);
		List<List<Integer>> classesList = new ArrayList<>();
		classesList.add(nameCount);
		classesList.add(seasonCount);
		classesList.add(forceUseCount);
		classesList.add(confrontationalCount);
		classesList.add(teamsCount);
		classesList.add(timeCount);
		classesList.add(olympicCount);

		double testIofD = calcIofD(testCount);
		double secondTestIofD = computeIof(testCount);

		for(Attribute a : attributes){
			System.out.println(a.toString());
			a.setGain(testIofD,7);
			System.out.println("I of D: " + testIofD);

			switch (a.name) {
				case "season" -> printRes(a.name, seasonCount);
				case "name" -> printRes(a.name, nameCount);
				case "force use" -> printRes(a.name, forceUseCount);
				case "confrontational" -> printRes(a.name, confrontationalCount);
				case "teams" -> printRes(a.name, teamsCount);
				case "time" -> printRes(a.name, timeCount);
				case "olympic" -> printRes(a.name, olympicCount);
			}

			System.out.println("\t \t gain: " + a.gain);

		}
		System.out.println();
	}
	public static void printRes(String name, List<Integer> list){
		double info = calcIofD(list);
		double hov = calcHofD(list);
		double igr = calcIGR(info, hov);
		System.out.println("I of " + name + ": " + info + "\n HoV: " + hov + "\n \t IGR: " + igr);
	}

	public static double calcIGR(double info, double hoV){
		double igr = info / hoV;
		return igr;
	}


	public static double computeIof(List<Integer> classesCount){
		double iof = 0.0;
		int totalNumClasses = 0;
		for (int i : classesCount){
			totalNumClasses += i;
		}
		for (double i : classesCount){
			iof -= (i / totalNumClasses) * Math.log((i / totalNumClasses));
		}

		return iof;
	}
	public static double calcIofD(List<Integer> classesCount){
		double IofD = 0.0;
		double temp = 0.0;
		
		int totalNumClasses = 0;
		for(int i : classesCount){
			totalNumClasses += i;
		}
		
		for(double d : classesCount){
			temp = (-1 * (d/totalNumClasses)) * (Math.log((d/totalNumClasses)) / Math.log(2));
			IofD += temp;
		}
		return IofD;
	}
	public static double calcHofD(List<Integer> classesCount){
		double hofD = 0.0;
		double temp = 0.0;

		int totalNumClasses = 0;
		for (int i : classesCount){
			totalNumClasses += i;
		}
		for (double i : classesCount){
			hofD -= (i / (double)totalNumClasses) * Math.log((i / (double)totalNumClasses));
		}


		return hofD;
	}
}
