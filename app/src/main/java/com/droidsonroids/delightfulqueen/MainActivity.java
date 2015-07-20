package com.droidsonroids.delightfulqueen;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.droidsonroids.awesomeprogressbar.AwesomeProgressBar;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends Activity {

    @Bind(R.id.super_awesome_progress_bar)
    AwesomeProgressBar mSuperAwesomeProgressBar;
//    @Bind(R.id.activity_main_btn_alpha)
//    Button mBtnAlpha;
//    @Bind(R.id.activity_main_btn_rotate)
//    Button mBtnRotate;
//    @Bind(R.id.activity_main_btn_scale)
//    Button mBtnScale;
//    @Bind(R.id.activity_main_btn_translate)
//    Button mBtnTranslate;
//    @Bind(R.id.activity_main_btn_move)
//    Button mBtnMove;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_custom_view);
        ButterKnife.bind(this);
        mSuperAwesomeProgressBar.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                mSuperAwesomeProgressBar.play();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}
