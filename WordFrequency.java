//@author: Tanveer Ahmad Baba
//@created: 28/08/2018
//@subject: N most frequent and N least frequent words occurring in the text file along with their frequencies.
import java.io.*;
import java.util.TreeMap;
import java.util.ArrayList;
import java.util.Map;
import java.util.Collections;
import java.util.Comparator;

class Word {
	String w;
	public Integer count;

	// PARAMETERIZED CONSTRUCTOR
	Word(Integer count, String w) {
		this.w = w;
		this.count = count;
	}

	//Define the getters
	public Integer getCount() {
		return this.count;
	}

	public String getWord() {
		return this.w;
	}

	//Overrided toString Method
	@Override
	public String toString(){
		return "(" + this.getWord() + ":" +
			    this.getCount() + ")";
	}
}

class WordFrequency {
	// VARIABLE STATIC WILL CONTAIN VALUE N FROM THE PROGRAM
	static int N;

	// MAIN METHOD 
	public static void main(String[] args) throws FileNotFoundException, IOException {
		// STATIC VARIABLE N INITIALIZED
		WordFrequency.N = Integer.parseInt(args[1]);

		display(readFile(args[0]));

	}

	// READ FILE FROM SYSTEM
	public static ArrayList<Word> readFile(String fileName) throws FileNotFoundException, IOException {
		FileReader 	fileReader = new FileReader(fileName);
		BufferedReader in = new BufferedReader(fileReader);

		TreeMap<String, Integer> myWords = new TreeMap<>();

		while(true) {
			String s= in.readLine();
			if(s == null) break;
			s = s.toLowerCase();
			s = s.replaceAll("[^a-zA-Z0-9 \n]", " ");
			s = s.replaceAll("( )+", " ");
			CountWordsAndFrequency(s.split(" "),myWords);
		}

		return putToArrayList(myWords);
	}

	// METHOD COUNTS WORDS AND THEIR FREQUENCY
	public static void CountWordsAndFrequency(String []s, TreeMap<String, Integer> myWords) {
		for(String str : s) {

			if(myWords.containsKey(str)) {
				Integer key = myWords.get(str);
				myWords.put(str,++key);
			}
			else {
				myWords.put(str,1);
			}
		}
	}

	//METHODS PUTS THE TREEMAP(VALUES) INTO THE ARRAY LIST TO SORT BY FREQUENCY 
	public static ArrayList<Word> putToArrayList(TreeMap<String, Integer> myWords) {
		ArrayList<Word> myList = new ArrayList<>();
		for (Map.Entry<String, Integer> entry : myWords.entrySet()) {
			myList.add(new Word(entry.getValue(), entry.getKey()));
		}

		return myList;
	}

	// DISPLAY THE LIST
	public static void display(ArrayList<Word> myList) {	
		Collections.sort(myList, new SortByCount());	
		System.out.println("Most Frequent");
		for(int count = 1; count <= WordFrequency.N; count++) {
			if(count <= WordFrequency.N) {
				System.out.print(myList.get(myList.size()-count));
				if(count!=(WordFrequency.N))System.out.print(", ");
			}
		}

		int count = 0;
		System.out.println("\nLeast Frequent");
		for(Object o : myList) {
			if(count < WordFrequency.N) {

				System.out.print(o);
				count++;
				if(count!=WordFrequency.N)System.out.print(", ");
			}
		}		
	}
} // CLASS END

// TYPE USED TO SORT WORDS BY THEIR FREQUENCY
class SortByCount implements Comparator<Word>{
	@Override
	public int compare(Object ref1, Object ref2) {
		if(ref1 instanceof Word && ref2 instanceof Word) {
			return ((Word)ref1).getCount().compareTo(((Word)ref2).getCount());
		}
		else {
			return -1;
		}
	}
}
