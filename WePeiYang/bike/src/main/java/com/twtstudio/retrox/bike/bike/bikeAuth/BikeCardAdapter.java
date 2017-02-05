package com.twtstudio.retrox.bike.bike.bikeAuth;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.twtstudio.retrox.bike.R;
import com.twtstudio.retrox.bike.R2;
import com.twtstudio.retrox.bike.common.ui.BaseAdapter;
import com.twtstudio.retrox.bike.common.ui.BaseViewHolder;
import com.twtstudio.retrox.bike.model.BikeCard;
import com.twtstudio.retrox.bike.utils.BikeStationUtils;
import com.twtstudio.retrox.bike.utils.PrefUtils;

import butterknife.BindView;


/**
 * Created by jcy on 2016/8/21.
 */

public class BikeCardAdapter extends BaseAdapter<BikeCard> {

    private static final int ITEM_NORMAL = 0;
    private static final int ITEM_FOOTER = 1;

    private BikeCardViewController mViewController;

    static class sCardItemHolder extends BaseViewHolder {
        @BindView(R2.id.bike_card_number)
        TextView mNumberText;
        @BindView(R2.id.bike_card_lastpostion)
        TextView mLastPostText;
        @BindView(R2.id.bike_card_cardview)
        CardView mCardView;


        public sCardItemHolder(View itemView) {
            super(itemView);
        }
    }

    public BikeCardAdapter(Context context, BikeCardViewController viewController) {
        super(context);
        mViewController = viewController;
    }

    static class FooterHolder extends BaseViewHolder {

        @BindView(R2.id.pb_footer)
        ProgressBar mFooter;

        public FooterHolder(View itemView) {
            super(itemView);
        }
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        if (viewType == ITEM_FOOTER) {
            return new FooterHolder(inflater.inflate(R.layout.footer, parent, false));
        } else {
            return new sCardItemHolder(inflater.inflate(R.layout.item_bike_card, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        int type = getItemViewType(position);
        if (type == ITEM_NORMAL) {
            sCardItemHolder itemHolder = (sCardItemHolder) holder;
            final BikeCard item = mDataSet.get(position);
            itemHolder.mNumberText.setText(item.id);
            itemHolder.mLastPostText.setText("最后一次存放:" + BikeStationUtils.getInstance().queryId(item.record.arr).name);
            itemHolder.mCardView.setOnClickListener(view -> {
                PrefUtils.setCardId(item.id);
                PrefUtils.setCardSign(item.sign);
                mViewController.onCardSelected();
//                BikeActivity.actionStart(mContext);
            });
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == mDataSet.size()) {
            return ITEM_FOOTER;
        } else {
            return ITEM_NORMAL;
        }
    }
}
