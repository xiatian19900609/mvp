package summer.zy.com.mvp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import summer.zy.com.http.response.bean.DataCenterNews;
import summer.zy.com.mvp.R;

/**
 * Created on 2017/3/17 14:23
 *
 * @author summer
 */

public class HomeAdpter extends RecyclerView.Adapter<HomeAdpter.MyAdapter> {
    private ArrayList<DataCenterNews> news;
    private Context mContext;

    public HomeAdpter(Context context) {
        this.mContext = context;
        news = new ArrayList<DataCenterNews>();
    }

    public void setData(ArrayList<DataCenterNews> news) {
        this.news.clear();
        this.news.addAll(news);
        notifyDataSetChanged();
    }

    @Override
    public MyAdapter onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(), R.layout.data_news_item, null);
        return new MyAdapter(view);
    }

    @Override
    public void onBindViewHolder(MyAdapter holder, int position) {
        DataCenterNews obj = news.get(position);

        //大图
        if (obj.picUrl.isEmpty() && obj.picUrl.equals("")) {
            holder.icon.setVisibility(View.GONE);
        } else {
            holder.icon.setVisibility(View.VISIBLE);
            Glide.with(mContext)
                    .load(obj.picUrl)
                    .placeholder(R.drawable.ic_news_default)
                    .error(R.drawable.ic_news_default)
                    .into(holder.icon);
        }

        holder.context.setText(obj.content);
        holder.time.setText(obj.reportTime.substring(5, 16) + " · " + obj.tag);
        holder.name.setText("爆料员: " + obj.authorName);
        holder.title.setText(obj.titleL);
    }

    @Override
    public int getItemCount() {
        return news.size();
    }

    public class MyAdapter extends RecyclerView.ViewHolder {
        TextView time, title, context, name;
        ImageView icon;

        public MyAdapter(View itemView) {
            super(itemView);
            initView(itemView);
        }

        private void initView(View itemView) {
            time = (TextView) itemView.findViewById(R.id.tv_time);
            title = (TextView) itemView.findViewById(R.id.tv_title);
            context = (TextView) itemView.findViewById(R.id.tv_context);
            name = (TextView) itemView.findViewById(R.id.tv_name);
            icon = (ImageView) itemView.findViewById(R.id.iv_icon);
        }
    }
}
