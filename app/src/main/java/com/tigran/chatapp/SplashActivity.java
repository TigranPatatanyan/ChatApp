package com.tigran.chatapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashActivity extends AppCompatActivity {

    private FirebaseUser user;
    TextView textView1;
    TextView textView2;
    TextView textView3;
    TextView textView4;
    Thread currentThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        init();
        splash();
        goToApp();
    }

    private void splash() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                textView1.setText("C");
            }
        }, 500);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                textView2.setText("H");
            }
        }, 700);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                textView3.setText("A");
            }
        }, 900);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                textView4.setText("T");
            }
        }, 1100);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                textView1.setText("");
                textView2.setText("");
                textView3.setText("");
                textView4.setText("");
            }
        }, 1600);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                textView1.setText("A");
            }
        }, 1800);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                textView2.setText("P");
            }
        }, 2000);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                textView3.setText("P");
            }
        }, 2200);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                textView1.setText("");
                textView2.setText("");
                textView3.setText("");
            }
        }, 2700);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                textView2.setText("by");
            }
        }, 3200);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                textView1.setText("T");
                textView2.setText("");
            }
        }, 3700);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                textView2.setText("I");
            }
        }, 3900);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                textView3.setText("K");
            }
        }, 4100);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                textView4.setText("O");
            }
        }, 4300);

    }

    private void goToApp() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (user == null) {
                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                    finish();
                } else {
                    startActivity(new Intent(SplashActivity.this, ChatActivity.class).putExtra("user",user));
                    finish();
                }
            }
        }, 4800);

    }

    private void init() {
        user = FirebaseAuth.getInstance().getCurrentUser();
        textView1 = findViewById(R.id.tv_splash_1);
        textView2 = findViewById(R.id.tv_splash_2);
        textView3 = findViewById(R.id.tv_splash_3);
        textView4 = findViewById(R.id.tv_splash_4);
        currentThread = Thread.currentThread();
    }
}
