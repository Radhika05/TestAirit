package com.airit.test.ViewModels;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.airit.test.Models.HomeModel;
import com.airit.test.Repositories.HomeRepository;

import java.util.List;

public class HomeViewModel extends ViewModel {
    private LiveData<List<HomeModel>> allData;
    private HomeRepository homeRepository;

    public void init(Context context) {
        homeRepository = HomeRepository.getInstance(context);
        allData = homeRepository.getAllData();
    }

    public MutableLiveData<List<HomeModel>> getHomeDetails() {
        return homeRepository.getHomeDetails();
    }

    public void delete(HomeModel maps){
        homeRepository.delete(maps);
    }

    public LiveData<List<HomeModel>> getAllData() {
        return allData;
    }
}
