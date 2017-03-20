package summer.zy.com.http.response.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 数据中心 - 爆料
 *
 * @author xt
 * @date 2016/5/23
 */
public class DataCenterNews implements Parcelable {
    public String id;
    public String titleS = ""; //短标题
    public String titleL = ""; //长标题
    public String content = ""; //爆料内容
    public String reportTime = ""; //爆料时间
    public String authorId = ""; //爆料员id
    public String authorName = ""; //爆料员姓名
    public String tag = ""; //标签
    public String picUrl = ""; //爆料图片地址
    public String teamType = ""; //1：主队 2：客队
    public String teamTypeName = ""; //主客名称

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.titleS);
        dest.writeString(this.titleL);
        dest.writeString(this.content);
        dest.writeString(this.reportTime);
        dest.writeString(this.authorId);
        dest.writeString(this.authorName);
        dest.writeString(this.tag);
        dest.writeString(this.picUrl);
        dest.writeString(this.teamType);
        dest.writeString(this.teamTypeName);
    }

    public DataCenterNews() {
    }

    protected DataCenterNews(Parcel in) {
        this.id = in.readString();
        this.titleS = in.readString();
        this.titleL = in.readString();
        this.content = in.readString();
        this.reportTime = in.readString();
        this.authorId = in.readString();
        this.authorName = in.readString();
        this.tag = in.readString();
        this.picUrl = in.readString();
        this.teamType = in.readString();
        this.teamTypeName = in.readString();
    }

    public static final Parcelable.Creator<DataCenterNews> CREATOR = new Parcelable.Creator<DataCenterNews>() {
        @Override
        public DataCenterNews createFromParcel(Parcel source) {
            return new DataCenterNews(source);
        }

        @Override
        public DataCenterNews[] newArray(int size) {
            return new DataCenterNews[size];
        }
    };
}
