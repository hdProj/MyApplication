package com.hd.hedong.myapplication;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import rx.Subscription;

/**
 * Created by hedong on 16-11-9.
 */
public class MyActivity extends AppCompatActivity {

    @BindView(R.id.left_list)
    RecyclerView leftListView;
    @BindView(R.id.right_list)
    RecyclerView rightListView;

    private MostLeftAdapter leftAdapter;
    private MostRightAdapter rightAdapter;
    List<ChoiceLeftBean> mPriceList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.left_right_layout);
        ButterKnife.bind(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        leftListView.setLayoutManager(layoutManager);
        leftAdapter = new MostLeftAdapter(this);
        leftListView.setAdapter(leftAdapter);

        LinearLayoutManager layoutManager1 = new LinearLayoutManager(this);
        layoutManager1.setOrientation(LinearLayoutManager.VERTICAL);
        rightListView.setLayoutManager(layoutManager1);
        rightAdapter = new MostRightAdapter(this);
        rightListView.setAdapter(rightAdapter);
        rightAdapter.setOnItemClickListener(new MostRightAdapter.MyItemClickListener() {
            @Override
            public void onItemClick(View view, int postion) {

            }
        });
        getLeftName();
    }

    private void requestRightData(int position) {
        //这里为了方便，直接更改左边的数据为右边加载的数据了，实际开发中改为所传参数即可
        String category = leftAdapter.getList().get(position);
        List<MostSeriesBean> list = new ArrayList<>();
        MostSeriesBean beans = new MostSeriesBean();
        beans.setName(category);
        list.add(beans);

        getRightData(list);

    }

    private void getRightData(List<MostSeriesBean> beans) {
        rightAdapter.setList(beans);
        rightAdapter.notifyDataSetChanged();
    }

    //设置左边数据源
    private void getLeftName() {

        for (int i = 0; i < 5; i++) {
            ChoiceLeftBean bean = new ChoiceLeftBean();
            if (i == 1) {
                bean.setName("宝马");
            }
            if (i == 2) {
                bean.setName("奔驰");
            }
            if (i == 3) {
                bean.setName("凯迪拉克");
            }
            if (i == 4) {
                bean.setName("现代");
            }
            if (i == 0) {
                bean.setName("SUV");
            }
            mPriceList.add(bean);
        }

        List<String> prices = new ArrayList<>();
        for (ChoiceLeftBean priceBean : mPriceList) {
            prices.add(priceBean.getName());
        }
        leftAdapter.setList(prices);
        //默认根据left的第一项数据去加载右边得数据
        requestRightData(0);
        leftAdapter.notifyDataSetChanged();
        leftAdapter.setOnItemClickListener(new MostLeftAdapter.MyItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                //设置position，根据position的状态刷新
                leftAdapter.setPosition(position);
                leftAdapter.notifyDataSetChanged();
                requestRightData(position);
            }
        });

    }

}
