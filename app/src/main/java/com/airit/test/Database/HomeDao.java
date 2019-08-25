package com.airit.test.Database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import com.airit.test.Models.HomeModel;
import java.util.List;

/**
 * ROOM: DAO , for manupulating the quries
 */
@Dao
public interface HomeDao {

    /**
     * insert the data into database
     *
     * @param homeModel
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<HomeModel> homeModel);

    /**
     * Delete the city from database
     *
     * @param homeModel
     */
    @Delete
    void delete(HomeModel homeModel);

    /**
     * get list of data
     *
     * @return
     */
    @Query("SELECT * FROM home")
    public LiveData<List<HomeModel>> getHomesData();
}
