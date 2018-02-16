package me.willeccles.tictactoe;
import java.util.ArrayList;

/**
 * TicTacToe class implements the interface
 * @author relkharboutly
 * @date 1/5/2017
 */
public class TicTacToe implements ITicTacToe {

	// The game board and the game status
	private static final int ROWS = 3, COLS = 3; // number of rows and columns
	private int[] board = new int[ROWS*COLS]; // game board in 1D array, 0 being top left, 2 being top right, and so on until 8 is the bottom right
	// this is important for the minimax algorithm (okay, maybe not really, but it makes it a lot easier)

	/**
	 * clear board and set current player   
	 */
	public TicTacToe() {

	}
	@Override
	public void clearBoard() {
		for (int i = 0; i < ROWS*COLS; i++) board[i] = EMPTY;
	}

	@Override
	public void setMove(int player, int location) {
		board[location] = player;
	}

	@Override
	public int getComputerMove() {
		// this is where we implement the minimax algorithm given the current board state
		Move bestMove = minimax(board, NOUGHT);
		return bestMove.index;
	}

	@Override
	public int checkForWinner() {
		if (checkWin(board, CROSS))
			return CROSS_WON;
		else if (checkWin(board, NOUGHT))
			return NOUGHT_WON;
		else if (emptyIndicies().length == 0)
			return TIE;
		else
			return PLAYING;
	}

	private boolean checkWin(int[] _board, int player) {
		return ((_board[0] == player && _board[1] == player && _board[2] == player) ||
				(_board[3] == player && _board[4] == player && _board[5] == player) ||
				(_board[6] == player && _board[7] == player && _board[8] == player) ||
				(_board[0] == player && _board[3] == player && _board[6] == player) ||
				(_board[1] == player && _board[4] == player && _board[7] == player) ||
				(_board[2] == player && _board[5] == player && _board[8] == player) ||
				(_board[0] == player && _board[4] == player && _board[8] == player) ||
				(_board[2] == player && _board[4] == player && _board[6] == player));
	}

	/**
	 * @return array of indicies that are available to use
	 */
	private int[] emptyIndicies() {
		// this is a terribly inefficient implementation but I can fix it another time
		int count = 0;
		for (int i = 0; i < ROWS*COLS; i++) {
			if (board[i] == EMPTY)
				count++;
		}
		int[] avail = new int[count];
		for (int i = 0, a = 0; i < ROWS*COLS; i++) {
			if (board[i] == EMPTY) {
				avail[a] = i;
				a++;
			}
		}
		return avail;
	}

	/**
	 *  Print the game board 
	 */
	public void printBoard() {
		for (int row = 0; row < ROWS; ++row) {
			for (int col = 0; col < COLS; ++col) {
				printCell(board[col + (row * 3)]); // print each of the cells
				if (col != COLS - 1) {
					System.out.print("|");   // print vertical partition
				}
			}
			System.out.println();
			if (row != ROWS - 1) {
				System.out.println("-----------"); // print horizontal partition
			}
		}
		System.out.println();
	}

	/**
	 * Print a cell with the specified "content" 
	 * @param content either CROSS, NOUGHT or EMPTY
	 */
	public void printCell(int content) {
		switch (content) {
			case EMPTY:  System.out.print("   "); break;
			case NOUGHT: System.out.print(" o "); break;
			case CROSS:  System.out.print(" x "); break;
		}
	}

	public int getCell(int i) {
		return board[i];
	}
	
	/**
	 * Checks if a given spot is empty.
	 * @param index The index to check at.
	 * @return True if empty, false if not.
	 */
	public boolean isEmpty(int index) {
		return board[index] == EMPTY;
	}

	/**
	 * Runs the minimax algorithm on the current board state recursively, returning the best possible move.
	 * @param currentBoard The current board state.
	 * @param player Which player is playing at the moment (should always set to AI).
	 * @return Best move the AI can make.
	 */
	private Move minimax(int[] currentBoard, int player) {
		int[] available = emptyIndicies(); 
		if (checkWin(currentBoard, CROSS)) {
			Move m = new Move();
			m.score = -10;
			return m;
		} else if (checkWin(currentBoard, NOUGHT)) {
			Move m = new Move();
			m.score = 10;
			return m;
		} else if (available.length == 0) {
			Move m = new Move();
			m.score = 0;
			return m;
		}

		ArrayList<Move> moves = new ArrayList<Move>();
		for (int i = 0; i < available.length; i++) {
			Move m = new Move();
			m.index = available[i];
			int[] newBoard = currentBoard;
			newBoard[available[i]] = player;
			if (player == NOUGHT) {
				Move result = minimax(newBoard, CROSS);
				m.score = result.score;
			} else {
				Move result = minimax(newBoard, NOUGHT);
				m.score = result.score;
			}

			newBoard[available[i]] = EMPTY;

			moves.add(m);
		}

		// if computer's turn
		int bestMove = -1;
		if (player == NOUGHT) {
			int bestScore = -10000;
			for (int i = 0; i < moves.size(); i++) {
				if (moves.get(i).score > bestScore) {
					bestScore = moves.get(i).score;
					bestMove = i;
				}
			}
		} else {
			int bestScore = 10000;
			for (int i = 0; i < moves.size(); i++) {
				if (moves.get(i).score < bestScore) {
					bestScore = moves.get(i).score;
					bestMove = i;
				}
			}
		}

		return moves.get(bestMove);
	}
	
	class Move {
		public int score = 0;
		public int index = 0;
		public Move() {}
	}
}
