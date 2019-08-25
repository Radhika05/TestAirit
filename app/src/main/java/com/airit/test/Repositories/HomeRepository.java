package com.airit.test.Repositories;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.airit.test.Database.HomeDao;
import com.airit.test.Database.HomeDatabase;
import com.airit.test.Interfaces.HomeAPI;
import com.airit.test.Models.HomeModel;
import com.airit.test.Networking.RetrofitService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * HomeRepository:Communicate with HomeViewModel and Fragment
 */
public class HomeRepository {
    private static HomeRepository homeepository;
    private HomeAPI homeAPI;
    private static HomeDao homeDao;
    private LiveData<List<HomeModel>> allHomeData;

    /**
     * Create singletone initialization
     * @param context
     * @return
     */
    public static HomeRepository getInstance(Context context) {
        if (homeepository == null) {
            homeepository = new HomeRepository(context);
        }
        return homeepository;
    }

    /**
     * Constructor
     * @param context
     */
    private HomeRepository(Context context) {
        homeAPI = RetrofitService.createService(HomeAPI.class);
        HomeDatabase homeDatabase = HomeDatabase.getInstance(context);
        homeDao = homeDatabase.homeDao();
        allHomeData = homeDao.getHomesData();
    }


    /**
     * get API respose and insert the data into database
     * @return
     */
    public MutableLiveData<List<HomeModel>> getHomeDetails() {
        final MutableLiveData<List<HomeModel>> homeData = new MutableLiveData<>();
        homeAPI.getData().enqueue(new Callback<List<HomeModel>>() {
            @Override
            public void onResponse(@NonNull Call<List<HomeModel>> call, @NonNull Response<List<HomeModel>> response) {
                if (response.isSuccessful()) {
                    homeData.setValue(response.body());
                    insert(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<HomeModel>> call, @NonNull Throwable t) {
                homeData.setValue(null);
            }
        });
        return homeData;
    }

    /***
     * Asynctask - Inserting data
     * @param homeModel
     */
    private void insert(List<HomeModel> homeModel){
        new InsertHomeAsync(homeDao).execute(homeModel);
    }

    /**
     * Asynctask - delete specific data
     * @param homeModel
     */
    public void delete(HomeModel homeModel){
        new DeleteHomeAsync(homeDao).execute(homeModel);
    }

    /**
     * return livedata object
     * @return
     */
    public LiveData<List<HomeModel>> getAllData(){
        return allHomeData;
    }

    /**
     * insert data into database using background thread
     */
    public static class InsertHomeAsync extends AsyncTask<List<HomeModel>, Void, Void> {
        HomeDao homeDao;
        private InsertHomeAsync(HomeDao homeDao){
            this.homeDao = homeDao;
        }
        @SafeVarargs
        @Override
        protected final Void doInBackground(List<HomeModel>... homeModels) {
            homeDao.insert(homeModels[0]);
            return null;
        }
    }

    /**
     * delete data into database using background thread
     */
    public static class DeleteHomeAsync extends AsyncTask<HomeModel, Void, Void>{
        HomeDao homeDao;
        private DeleteHomeAsync(HomeDao homeDao){
            this.homeDao = homeDao;
        }
        @Override
        protected Void doInBackground(HomeModel... homeModels) {
            homeDao.delete(homeModels[0]);
            return null;
        }
    }
}
