package fragmentbasics.moacir.com.fragmentbasics;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class DynamicFragmentsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dynamic_fragments);

        Button buttonA = (Button) findViewById(R.id.button_a);
        buttonA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyFirstFragment myFirstFragment = new MyFirstFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.container, myFirstFragment).commit();
            }
        });


        Button buttonB = (Button) findViewById(R.id.button_b);
        buttonB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MySecondFragment mySecondFragment = new MySecondFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.container, mySecondFragment).commit();

            }
        });

    }
}
