package summer.zy.com.http.response;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

import summer.zy.com.http.response.bean.DataCenterNews;

/**
 * 数据中心 - 爆料列表
 *
 * @author xt
 * @date 2016/5/23
 */
public class DataNewsResponse implements Parcelable {
    public String matchId = "";
    public String homeId = "";//主队id
    public String awayId = "";//客队id
    public String homeLogo = "";//主队队徽地址
    public String awayLogo = "";//客队队徽地址
    public ArrayList<DataCenterNews> news = new ArrayList<DataCenterNews>();

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.matchId);
        dest.writeString(this.homeId);
        dest.writeString(this.awayId);
        dest.writeString(this.homeLogo);
        dest.writeString(this.awayLogo);
        dest.writeList(this.news);
    }

    public DataNewsResponse() {
    }

    protected DataNewsResponse(Parcel in) {
        this.matchId = in.readString();
        this.homeId = in.readString();
        this.awayId = in.readString();
        this.homeLogo = in.readString();
        this.awayLogo = in.readString();
        this.news = new ArrayList<DataCenterNews>();
        in.readList(this.news, DataCenterNews.class.getClassLoader());
    }

    public static final Parcelable.Creator<DataNewsResponse> CREATOR = new Parcelable.Creator<DataNewsResponse>() {
        @Override
        public DataNewsResponse createFromParcel(Parcel source) {
            return new DataNewsResponse(source);
        }

        @Override
        public DataNewsResponse[] newArray(int size) {
            return new DataNewsResponse[size];
        }
    };
}
