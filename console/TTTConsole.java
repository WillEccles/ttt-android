package src;
import java.util.Scanner;
/**
 * Tic-Tac-Toe: Two-player console, non-graphics
 * @author relkharboutly
 * @date 1/5/2017
 */
public class TTTConsole  {

	public static Scanner in = new Scanner(System.in); // the input Scanner

	public static TicTacToe TTTboard = new TicTacToe();
	/** The entry main method (the program starts here) */
	public static void main(String[] args) {

		int currentState = TicTacToe.PLAYING;
		String userInput = "";
		//game loop
		do {
			TTTboard.printBoard();
			// Print message if game-over
			currentState = TTTboard.checkForWinner();
			
			if (currentState == ITicTacToe.PLAYING) {
				// get player input
				userInput = in.nextLine();
				int pMove = -1;
				pMove = Integer.parseInt(userInput);
				while (pMove > 8 || pMove < 0 || !TTTboard.isEmpty(pMove)) {
					System.out.println("Please enter a valid number from 0-8, corresponding to an empty square.");
					userInput = in.nextLine();
					if (userInput.equals("q"))
						break;
					pMove = Integer.parseInt(userInput);
				}
				
				// set the player's move
				TTTboard.setMove(ITicTacToe.CROSS, pMove);
				TTTboard.printBoard();
			}
			
			if (currentState == ITicTacToe.PLAYING) {
				// do computer move
				System.out.println("Doing computer move...");
				TTTboard.setMove(ITicTacToe.NOUGHT, TTTboard.getComputerMove());
			}

			if (currentState == ITicTacToe.CROSS_WON) {
				System.out.println("'X' won! Bye!");
			} else if (currentState == ITicTacToe.NOUGHT_WON) {
				System.out.println("'O' won! Bye!");
			} else if (currentState == ITicTacToe.TIE) {
				System.out.println("It's a TIE! Bye!");
			}
		} while ((currentState == ITicTacToe.PLAYING) && (!userInput.equals("q"))); // repeat if not game-over
	}


}
