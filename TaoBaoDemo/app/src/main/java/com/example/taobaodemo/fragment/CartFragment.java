package com.example.taobaodemo.fragment;


import android.content.Intent;
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

import com.example.taobaodemo.Contants;
import com.example.taobaodemo.CreateOrderActivity;
import com.example.taobaodemo.R;
import com.example.taobaodemo.adapter.CartAdapter;
import com.example.taobaodemo.bean.User;
import com.example.taobaodemo.http.BaseCallback;
import com.example.taobaodemo.http.OkHttpHelper;
import com.example.taobaodemo.http.SpotsCallBack;
import com.example.taobaodemo.utils.CartProvider;
import com.example.taobaodemo.widget.CnToolbar;

import java.util.HashMap;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import cn.smssdk.gui.RegisterPage;
import okhttp3.Request;
import okhttp3.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class CartFragment extends BaseFragment implements View.OnClickListener {

    private View rootView;
    private RecyclerView mRecyclerView;
    private CheckBox mCheckBox;
    private TextView mTextTotal;
    private Button mBtnOrder;
    private Button mBtnDel;
    protected CnToolbar mToolbar;

    private CartAdapter cartAdapter;
    private CartProvider cartProvider;
    private boolean isEditState = false;

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
        mToolbar = rootView.findViewById(R.id.toolbar);

        cartProvider = new CartProvider(getContext());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        cartAdapter = new CartAdapter(getContext(), cartProvider.getAll(), mCheckBox, mTextTotal);
        mRecyclerView.setAdapter(cartAdapter);

        mToolbar.setRightButtonOnClickListener(this);
        mBtnDel.setOnClickListener(this);
        mBtnOrder.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.toolbar_rightButton) {
            isEditState = !isEditState;
            mToolbar.setRightButtonTitle(isEditState ? "完成" : "编辑");
            mBtnOrder.setVisibility(isEditState ? View.GONE : View.VISIBLE);
            mBtnDel.setVisibility(isEditState ? View.VISIBLE : View.GONE);
            mTextTotal.setVisibility(isEditState ? View.GONE : View.VISIBLE);
        } else if (v.getId() == R.id.btn_del) {
            cartAdapter.delCart();
        } else if (v.getId() == R.id.btn_order) {

//            OkHttpHelper httpHelper = OkHttpHelper.getInstance();
//            httpHelper.post(Contants.API.USER_DETAIL, null, new SpotsCallBack<String>(getContext()) {
//
//                @Override
//                public void onSuccess(Response response, String s) {
//                    System.out.println("返回结果" + s);
//                }
//            });
//            super.startActivity(new Intent(getActivity(), CreateOrderActivity.class),true);


            RegisterPage page = new RegisterPage();
            //如果使用我们的ui，没有申请模板编号的情况下需传null
            page.setTempCode(null);
            page.setRegisterCallback(new EventHandler() {
                public void afterEvent(int event, int result, Object data) {
                    if (result == SMSSDK.RESULT_COMPLETE) {
                        // 处理成功的结果
                        HashMap<String,Object> phoneMap = (HashMap<String, Object>) data;
                        // 国家代码，如“86”
                        String country = (String) phoneMap.get("country");
                        // 手机号码，如“13800138000”
                        String phone = (String) phoneMap.get("phone");
                        // TODO 利用国家代码和手机号码进行后续的操作
                        System.out.println("利用国家代码和手机号码进行后续的操作="+country+phone);
                    } else{
                        // TODO 处理错误的结果

                        System.out.println("处理错误的结果");
                    }
                }
            });
            page.show(getContext());

        }
    }
}
