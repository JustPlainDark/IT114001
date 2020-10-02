import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

public class NumberGuesserPart4 {
	private int level = 1;
	private int strikes = 0;
	private int maxStrikes = 5;
	private int number = 0;
	private int streak = 0;
	private boolean isRunning = false;
	private boolean hasLoaded = false;
	final String saveFile = "numberGuesserSave.txt";

	/***
	 * Gets a random number between 1 and level.
	 * 
	 * @param level (level to use as upper bounds)
	 * @return number between bounds
	 */
	public static int getNumber(int level) {
		int range = 9 + ((level - 1) * 5);
		System.out.println("I picked a random number between 1-" + (range + 1) + ", let's see if you can guess.");
		return new Random().nextInt(range) + 1;
		
	}

	private void win() {
		System.out.println("That's right!");
		level++;// level up!
		streak++;
		strikes = 0;
		saveData();
		System.out.println("Welcome to level " + level);
		number = getNumber(level);
	}

	private void lose() {
		System.out.println("Uh oh, looks like you need to get some more practice.");
		System.out.println("The correct number was " + number);
		strikes = 0;
		streak = 0;
		level--;
		if (level < 1) {
			level = 1;
		}
		number = getNumber(level);
		saveData();
	}

	private void processCommands(String message) {
		if (message.equalsIgnoreCase("quit") || message.equalsIgnoreCase("exit")) {
			System.out.println("Tired of playing? No problem, see you next time.");
			isRunning = false;
		}
		if (message.equalsIgnoreCase("reset") || message.equalsIgnoreCase("restart")) {
			File file = new File(saveFile);
			if(file.delete()) {
				System.out.println("The file has been reset, please run again!");
				isRunning = false;
			}
			else {
				System.out.println("There is no file to reset");
			}
		}
	}

	private void processGuess(int guess) {
		if (guess < 0) {
			return;
		}
		System.out.println("You guessed " + guess);
		if (guess == number) {
			win();
		} else {
			System.out.println("That's wrong");
			strikes++;
			saveData();
			if (strikes >= maxStrikes) {
				lose();
			} else {
				int remainder = maxStrikes - strikes;
				System.out.println("You have " + remainder + "/" + maxStrikes + " attempts remaining");
				if (guess > number) {
					System.out.println("Lower");
				} else if (guess < number) {
					System.out.println("Higher");
				}
			}
		}
	}

	private int getGuess(String message) {
		int guess = -1;
		try {
			guess = Integer.parseInt(message);
		} catch (NumberFormatException e) {
			System.out.println("You didn't enter a number, please try again");

		}
		return guess;
	}

	private void saveData() {
		try (FileWriter fw = new FileWriter(saveFile)) {
			fw.write(number + " " + strikes + " " + streak + " " + level);// here we need to convert it to a String to record correctly
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private boolean loadData() {
		File file = new File(saveFile);
		if (!file.exists() || hasLoaded == true) {
			hasLoaded = true;
			return false;
		}
		try (Scanner reader = new Scanner(file)) {
			while (reader.hasNextLine()) {	
				int _guess = reader.nextInt();
				number = _guess;
				int _strikes = reader.nextInt();
				if (_strikes > 0) {
					strikes = _strikes;
				}
				int _streak = reader.nextInt();
				streak = _streak;
				int _level = reader.nextInt();
				if (_level > 1) {
					level = _level;
					break;
				}
				else {
					break;
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		} catch (Exception e2) {
			e2.printStackTrace();
			return false;
		}
		return level > 1;
	}

	void run() {
		try (Scanner input = new Scanner(System.in);) {
			System.out.println("Welcome to Number Guesser 4.0!");
			System.out.println("I'll ask you to guess a number between a range, and you'll have " + maxStrikes
					+ " attempts to guess.");
			if (!loadData()) {
				number = getNumber(level);
			}
			if (loadData()) {
				System.out.println("Successfully loaded level " + level);
				if (strikes > 0) {
					System.out.println("Attempts Remaining: " + (maxStrikes - strikes));
				}
				if (streak > 0) {
					System.out.println("Your current streak is " + streak);
				}
				System.out.println("I picked a random number between 1-" + ((9 + ((level - 1) * 5)) + 1) + ", let's see if you can guess.");
				hasLoaded = true;
			}
			isRunning = true;
			while (input.hasNext()) {
				String message = input.nextLine();
				processCommands(message);
				if (!isRunning) {
					break;
				}
				int guess = getGuess(message);
				processGuess(guess);
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}

	public static void main(String[] args) {
		NumberGuesserPart4 guesser = new NumberGuesserPart4();
		guesser.run();
	}
}