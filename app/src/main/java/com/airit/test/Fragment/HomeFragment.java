package com.airit.test.Fragment;


import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.airit.test.Adapters.HomeDetailsAdapter;
import com.airit.test.Common.Utils;
import com.airit.test.Models.HomeModel;
import com.airit.test.R;
import com.airit.test.ViewModels.HomeViewModel;
import java.util.List;
import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private  RecyclerView mHomeRecyclerView;
    private HomeDetailsAdapter homeDetailsAdapter;
    private ImageView noNetwork;


    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_home, container,  false);
        initViews(view);
        homeViewModel.getAllData().observe(getActivity(), new Observer<List<HomeModel>>() {
            @Override
            public void onChanged(List<HomeModel> homeModels) {
                if(homeModels !=null && homeModels.size()!=0){
                    noNetwork.setVisibility(View.GONE);
                    BindView(homeModels);
                }
                else {
                    if(Utils.isOnline(getActivity()))
                    {
                        getDataFromWebAPI();
                        noNetwork.setVisibility(View.GONE);
                    }
                    else {
                        noNetwork.setVisibility(View.VISIBLE);
                        Toast.makeText(getActivity(), R.string.internet_connection, Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        swipToDelete();
        return  view;
    }

    private void swipToDelete() {
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT| ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                homeViewModel.delete(homeDetailsAdapter.getItemAt(viewHolder.getAdapterPosition()));
                Toast.makeText(getActivity(), "deleted successfully.", Toast.LENGTH_LONG).show();
            }
        }).attachToRecyclerView(mHomeRecyclerView);
    }


    private void getDataFromWebAPI() {
        homeViewModel.getHomeDetails().observe(Objects.requireNonNull(getActivity()), new Observer<List<HomeModel>>() {
            @Override
            public void onChanged(List<HomeModel> homeModels) {
                if(homeModels!=null){
                    BindView(homeModels);
                }
            }
        });
    }

    public void BindView(List<HomeModel> homeModel){
       homeDetailsAdapter = new HomeDetailsAdapter(homeModel);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        mHomeRecyclerView.setLayoutManager(mLayoutManager);
        mHomeRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mHomeRecyclerView.setAdapter(homeDetailsAdapter);
    }

    private void initViews(View view) {
        mHomeRecyclerView = view.findViewById(R.id.rv_home);
        noNetwork = view.findViewById(R.id.img_no_network);
        homeViewModel = ViewModelProviders.of(this.getActivity()).get(HomeViewModel.class);
        homeViewModel.init(getActivity());
    }
}
