package com.fffz.badgeview.sample;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import com.fffz.badgeview.BadgeView;

public class MainActivity extends Activity implements View.OnClickListener {

    private TextView mTv1, mTv2;
    private BadgeView mBv1, mBv2;
    private CheckBox mCbKeepTargetCenter, mCbCircle, mCbSemicircle;
    private EditText mEt;
    private SeekBar mSbTextSize, mSbOffsetX, mSbOffsetY, mSbCornerRadius, mSbHPadding, mSbVPadding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn_sample).setOnClickListener(this);
        mTv1 = (TextView) findViewById(R.id.tv1);
        mTv2 = (TextView) findViewById(R.id.tv2);
        mBv1 = new BadgeView(this);
        mBv1.setBackgroundColor(Color.RED);
        mBv2 = new BadgeView(this);
        mBv2.setTextColor(Color.RED);
        mBv2.setBackgroundResource(R.drawable.android);
        findViewById(R.id.btn_attach).setOnClickListener(this);
        findViewById(R.id.btn_detach).setOnClickListener(this);
        mCbKeepTargetCenter = (CheckBox) findViewById(R.id.cb_keep_target_center);
        mCbCircle = (CheckBox) findViewById(R.id.cb_circle);
        mCbSemicircle = (CheckBox) findViewById(R.id.cb_semicircle);
        mEt = (EditText) findViewById(R.id.et);
        mSbTextSize = (SeekBar) findViewById(R.id.sb_text_size);
        mSbOffsetX = (SeekBar) findViewById(R.id.sb_offset_x);
        mSbOffsetY = (SeekBar) findViewById(R.id.sb_offset_y);
        mSbCornerRadius = (SeekBar) findViewById(R.id.sb_corner_radius);
        mSbHPadding = (SeekBar) findViewById(R.id.sb_h_padding);
        mSbVPadding = (SeekBar) findViewById(R.id.sb_v_padding);
    }

    public int dp2px(float dp) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btn_sample:
                startActivity(new Intent(this, SampleActivity.class));
                break;
            case R.id.btn_attach:
                setupBadgeView(mBv1);
                setupBadgeView(mBv2);
                mBv1.attach(mTv1);
                mBv2.attach(mTv2);
                break;
            case R.id.btn_detach:
                mBv1.detach();
                mBv2.detach();
        }
    }

    private void setupBadgeView(BadgeView badgeView) {
        badgeView.setText(mEt.getText().toString());
        badgeView.setTextSize(dp2px(mSbTextSize.getProgress()));
        badgeView.setKeepTargetCenter(mCbKeepTargetCenter.isChecked());
        badgeView.setCircle(mCbCircle.isChecked());
        badgeView.setSemicircle(mCbSemicircle.isChecked());
        badgeView.setOffset(dp2px(mSbOffsetX.getProgress()), dp2px(mSbOffsetY.getProgress()));
        badgeView.setCornerRadius(dp2px(mSbCornerRadius.getProgress()));
        int hPadding = dp2px(mSbHPadding.getProgress());
        int vPadding = dp2px(mSbVPadding.getProgress());
        badgeView.setPadding(hPadding, vPadding, hPadding, vPadding);
    }

}