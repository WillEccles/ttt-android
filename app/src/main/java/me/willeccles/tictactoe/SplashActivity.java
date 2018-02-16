package me.willeccles.tictactoe;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class SplashActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
	}

	protected void beginClicked(View v) {
		EditText et = (EditText)findViewById(R.id.editText);
		Intent intent = new Intent(this, PlayActivity.class);
		intent.putExtra("name", et.getText().toString());
		startActivity(intent);
	}
}
