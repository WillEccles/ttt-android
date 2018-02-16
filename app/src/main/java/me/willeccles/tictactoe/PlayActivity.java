package me.willeccles.tictactoe;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class PlayActivity extends AppCompatActivity {

	private TicTacToe TTT;
	int currentState = TicTacToe.PLAYING;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_play);
		String name = getIntent().getStringExtra("name");
		TextView tv = (TextView)findViewById(R.id.name);
		tv.setText(String.format("Hello, %s!", name));
		TTT = new TicTacToe();
		for (int i = 0; i < 9; i++) {
			((ImageButton) findViewById(R.id.button0 + i)).setBackgroundResource(0);
		}
		Toast.makeText(this, "Your turn!", Toast.LENGTH_SHORT).show();
	}

	// sets each button to the right thing after each turn
	void displayBoard() {
		// loop through the buttons, since the IDs are in order
		for (int i = 0; i < 9; i++) {
			switch (TTT.getCell(i)) {
				case ITicTacToe.CROSS:
					((ImageButton)findViewById(R.id.button0 + i)).setBackgroundResource(R.mipmap.x);
					((ImageButton)findViewById(R.id.button0 + i)).setEnabled(false);
					break;
				case ITicTacToe.NOUGHT:
					((ImageButton)findViewById(R.id.button0 + i)).setBackgroundResource(R.mipmap.o);
					((ImageButton)findViewById(R.id.button0 + i)).setEnabled(false);
					break;
				case ITicTacToe.EMPTY:
					((ImageButton)findViewById(R.id.button0 + i)).setBackgroundResource(0);
					((ImageButton)findViewById(R.id.button0 + i)).setEnabled(true);
					break;
			}
		}
	}

	void doAITurn() {
		// get the AI's turn and then display the buttons and check for a winner
		TTT.setMove(ITicTacToe.NOUGHT, TTT.getComputerMove());
		displayBoard();
		currentState = TTT.checkForWinner();
		switch(currentState) {
			case ITicTacToe.NOUGHT_WON:
				Toast.makeText(this, "You lose!", Toast.LENGTH_LONG).show();
				break;
			case ITicTacToe.TIE:
				Toast.makeText(this, "Draw!", Toast.LENGTH_LONG).show();
				break;
			case ITicTacToe.PLAYING:
				Toast.makeText(this, "Your turn!", Toast.LENGTH_SHORT).show();
				break;
		}
	}

	// when a box is clicked, they all go here
	protected void boxClicked(View v) {
		// v is the button that was clicked
		if (currentState != ITicTacToe.PLAYING) return;
		TTT.setMove(ITicTacToe.CROSS, v.getId() - R.id.button0);
		v.setEnabled(false);
		displayBoard();
		currentState = TTT.checkForWinner();
		switch(currentState) {
			case ITicTacToe.NOUGHT_WON:
				Toast.makeText(this, "You lose!", Toast.LENGTH_LONG).show();
				break;
			case ITicTacToe.TIE:
				Toast.makeText(this, "Draw!", Toast.LENGTH_LONG).show();
				break;
			case ITicTacToe.PLAYING:
				doAITurn();
				break;
		}
	}

	// when the reset button is clicked
	protected void resetClicked(View v) {
		TTT.clearBoard();
		currentState = TTT.checkForWinner();
		displayBoard();
		Toast.makeText(this, "Your turn!", Toast.LENGTH_SHORT).show();
	}
}
