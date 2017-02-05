package com.twtstudio.retrox.bike.bike;

import android.content.Context;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;
import com.twtstudio.retrox.bike.api.BikeApiClient;
import com.twtstudio.retrox.bike.api.BikeApiSubscriber;
import com.twtstudio.retrox.bike.api.OnNextListener;
import com.twtstudio.retrox.bike.common.BikePresenter;
import com.twtstudio.retrox.bike.model.BikeStation;

import java.util.List;


/**
 * Created by jcy on 2016/8/11.
 */

public class BikeFragPresenter extends BikePresenter {
    private BikeViewController mViewController;
    private List<BikeStation> mCachedStatusList;

    public BikeFragPresenter(Context context, BikeViewController viewController) {
        super(context);
        mViewController = viewController;
    }

    public void getStationStatus(String id) {
        BikeApiClient.getInstance().getStationStatus(mContext, new BikeApiSubscriber(mContext, mStationListener), id);
    }

    protected OnNextListener<List<BikeStation>> mStationListener = new OnNextListener<List<BikeStation>>() {
        @Override
        public void onNext(List<BikeStation> bikeStations) {
            // TODO: 2016/8/12 更新底部图片的逻辑
            BikeStation detail = bikeStations.get(0);
            mViewController.setStationDetail(detail);
        }
    };

    public void cacheStationStatus() {
        BikeApiClient.getInstance().cacheStationStatus(mContext, new BikeApiSubscriber<>(mContext, mCacheListener));
    }

    protected OnNextListener<List<BikeStation>> mCacheListener = new OnNextListener<List<BikeStation>>() {
        @Override
        public void onNext(List<BikeStation> bikeStations) {
            mCachedStatusList = bikeStations;
        }
    };

    public void queryCachedStatus(String id){
        if (mCachedStatusList != null) {
            List<BikeStation> list = Stream.of(mCachedStatusList)
                    .filter(value -> value.id.equals(id))
                    .collect(Collectors.toList());
            mViewController.setStationDetail(list.get(0));
        }
    }
}
