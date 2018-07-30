package com.jvho.warehouse;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.jvho.warehouse.ui.enter.EnterActivity;
import com.jvho.warehouse.ui.query.QueryActivity;

public class MainActivity extends AppCompatActivity {

    public static void gotoMainActivity(Context context){
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn_enter = (Button) findViewById(R.id.btn_enter);
        Button btn_query = (Button) findViewById(R.id.btn_query);

        btn_query.setOnClickListener(new ButtonListener());
        btn_enter.setOnClickListener(new ButtonListener());

    }

    private class ButtonListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {

                case R.id.btn_enter:
                    Intent intent = new Intent(MainActivity.this, EnterActivity.class);
                    startActivity(intent);
                    break;
                case R.id.btn_query:
                    Intent intent1 = new Intent(MainActivity.this, QueryActivity.class);
                    startActivity(intent1);
                    break;
                default:
                    break;
            }
        }
    }
}
