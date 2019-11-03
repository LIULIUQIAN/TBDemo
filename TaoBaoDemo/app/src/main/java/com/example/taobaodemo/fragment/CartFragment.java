package com.example.taobaodemo.fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.taobaodemo.R;
import com.example.taobaodemo.adapter.CartAdapter;
import com.example.taobaodemo.utils.CartProvider;

/**
 * A simple {@link Fragment} subclass.
 */
public class CartFragment extends Fragment {

    private View rootView;
    private RecyclerView mRecyclerView;
    private CheckBox mCheckBox;
    private TextView mTextTotal;
    private Button mBtnOrder;
    private Button mBtnDel;


    private CartAdapter cartAdapter;
    private CartProvider cartProvider;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_cart, container, false);
        initView();
        return rootView;
    }

    private void initView() {
        mRecyclerView = rootView.findViewById(R.id.cart_recyclerview);
        mCheckBox = rootView.findViewById(R.id.checkbox_all);
        mTextTotal = rootView.findViewById(R.id.txt_total);
        mBtnOrder = rootView.findViewById(R.id.btn_order);
        mBtnDel = rootView.findViewById(R.id.btn_del);

        cartProvider = new CartProvider(getContext());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL));
        cartAdapter = new CartAdapter(getContext(),cartProvider.getAll(),mCheckBox,mTextTotal);
        mRecyclerView.setAdapter(cartAdapter);



    }

}
