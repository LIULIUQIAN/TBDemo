package com.example.taobaodemo.widget;

import android.content.Context;
import android.text.InputType;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.widget.TintTypedArray;

import com.example.taobaodemo.R;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

public class NumberAddSubView extends LinearLayout implements View.OnClickListener {

    private TextView textNum;
    private Button btnAdd;
    private Button btnSub;

    private OnNumChangeListener onNumChangeListener;
    private LayoutInflater mInflater;
    private int value;
    private int minValue = 0;
    private int maxValue = 10000;

    public NumberAddSubView(Context context) {
        this(context, null);
    }

    public NumberAddSubView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NumberAddSubView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mInflater = LayoutInflater.from(context);
        initView();

        if (attrs != null) {

            final TintTypedArray a = TintTypedArray.obtainStyledAttributes(getContext(), attrs, R.styleable.NumberAddSubView, defStyleAttr, 0);
            int val = a.getInt(R.styleable.NumberAddSubView_value, 0);
            setValue(val);

            int maxVal = a.getInt(R.styleable.NumberAddSubView_maxValue, 0);
            if (maxVal > 0) {
                setMaxValue(maxVal);
            }

            int minVal = a.getInt(R.styleable.NumberAddSubView_minValue,0);
            setMinValue(minVal);

        }
    }

    private void initView() {
        View view = mInflater.inflate(R.layout.widet_num_add_sub, this, true);
        textNum = view.findViewById(R.id.text_num);
        textNum.setKeyListener(null);

        btnAdd = view.findViewById(R.id.btn_add);
        btnSub = view.findViewById(R.id.btn_sub);

        btnAdd.setOnClickListener(this);
        btnSub.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {

        getValue();
        if (v.getId() == R.id.btn_add) {

            if (this.value <= maxValue) {
                this.value += 1;
            }

        } else if (v.getId() == R.id.btn_sub) {
            if (this.value > minValue) {
                this.value -= 1;
            }
        }
        if (onNumChangeListener != null) {
            onNumChangeListener.OnNumChange(v, this.value);
        }
        textNum.setText(value + "");

    }

    public void setOnNumChangeListener(OnNumChangeListener onNumChangeListener) {
        this.onNumChangeListener = onNumChangeListener;

    }

    public int getValue() {
        String str = textNum.getText().toString();
        if (str != null && !str.equals("")) {
            value = Integer.parseInt(str);
        }
        return value;
    }

    public void setValue(int value) {
        textNum.setText(value + "");
        this.value = value;
    }

    public int getMinValue() {
        return minValue;
    }

    public void setMinValue(int minValue) {
        this.minValue = minValue;
    }

    public int getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(int maxValue) {
        this.maxValue = maxValue;
    }

    public interface OnNumChangeListener {
        public void OnNumChange(View view, int value);
    }
}
