package com.hanjinliang.keeprunning.net;


import com.hanjinliang.keeprunning.entity.ResultEntity;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * 接口
 */
public interface Api {

    @GET("log/saveLog")
    Observable<ResultEntity<String>> saveLog(@Query("info") String info, @Query("extra") String extra );

}
