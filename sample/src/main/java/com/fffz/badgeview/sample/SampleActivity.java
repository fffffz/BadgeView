package com.fffz.badgeview.sample;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.fffz.badgeview.BadgeView;

public class SampleActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample);

        int padding = dp2px(1);

        View tv1 = findViewById(R.id.tv1);
        BadgeView bv1 = new BadgeView(this);
        bv1.setPadding(padding, padding, padding, padding);
        bv1.setBackgroundColor(Color.RED);
        bv1.setTextColor(Color.WHITE);
        bv1.setTextSize(dp2px(12));
        bv1.setCircle(true);
        bv1.setText("1");
        bv1.attach(tv1);

        View tv2 = findViewById(R.id.tv2);
        BadgeView bv2 = new BadgeView(this);
        bv2.setPadding(padding, padding, padding, padding);
        bv2.setBackgroundColor(Color.RED);
        bv2.setTextColor(Color.WHITE);
        bv2.setTextSize(dp2px(12));
        bv2.setCircle(true);
        bv2.setText("99");
        bv2.attach(tv2);

        View tv3 = findViewById(R.id.tv3);
        BadgeView bv3 = new BadgeView(this);
        bv3.setPadding(dp2px(2), dp2px(1), dp2px(2), dp2px(1));
        bv3.setCircle(false);
        bv3.setSemicircle(true);
        bv3.setBackgroundColor(Color.RED);
        bv3.setTextColor(Color.WHITE);
        bv3.setTextSize(dp2px(12));
        bv3.setText("666");
        bv3.attach(tv3);

        View tv4 = findViewById(R.id.tv4);
        BadgeView bv4 = new BadgeView(this);
        bv4.setPadding(padding, padding, padding, padding);
        bv4.setBackgroundColor(Color.parseColor("#00CC66"));
        bv4.setTextColor(Color.WHITE);
        bv4.setTextSize(dp2px(12));
        bv4.setText("绿色食品");
        bv4.attach(tv4);

        View tv5 = findViewById(R.id.tv5);
        BadgeView bv5 = new BadgeView(this);
        bv5.setPadding(padding, padding, padding, padding);
        bv5.setBackgroundColor(Color.parseColor("#00CC66"));
        bv5.setTextColor(Color.WHITE);
        bv5.setTextSize(dp2px(12));
        bv5.setText("绿色食品");
        bv5.setCornerRadius(dp2px(2));
        bv5.attach(tv5);

        View tv6 = findViewById(R.id.tv6);
        BadgeView bv6 = new BadgeView(this);
        bv6.setPadding(padding, padding, padding, padding);
        bv6.setBackgroundColor(Color.parseColor("#00CC66"));
        bv6.setTextColor(Color.WHITE);
        bv6.setTextSize(dp2px(12));
        bv6.setText("绿色食品");
        bv6.setOffset(0, 0);
        bv6.attach(tv6);

        View tv7 = findViewById(R.id.tv7);
        BadgeView bv7 = new BadgeView(this);
        bv7.setPadding(padding, padding, padding, padding);
        bv7.setBackgroundColor(Color.parseColor("#00CC66"));
        bv7.setTextColor(Color.WHITE);
        bv7.setTextSize(dp2px(12));
        bv7.setWidth(dp2px(30));
        bv7.setHeight(dp2px(30));
        bv7.setCornerRadius(dp2px(5));
        bv7.setOffset(dp2px(10), dp2px(10));
        bv7.setText("绿色");
        bv7.attach(tv7);
    }

    public int dp2px(float dp) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }
}
