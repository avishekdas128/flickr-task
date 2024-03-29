package com.orange.flickrtask.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.recyclerview.widget.DiffUtil;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "photo")
public class Photo extends BaseObservable implements Parcelable
{
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("owner")
    @Expose
    private String owner;
    @ColumnInfo(name = "secret")
    @SerializedName("secret")
    @Expose
    private String secret;
    @ColumnInfo(name = "server")
    @SerializedName("server")
    @Expose
    private String server;
    @ColumnInfo(name = "farm")
    @SerializedName("farm")
    @Expose
    private Integer farm;
    @ColumnInfo(name = "title")
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("ispublic")
    @Expose
    private Integer ispublic;
    @SerializedName("isfriend")
    @Expose
    private Integer isfriend;
    @SerializedName("isfamily")
    @Expose
    private Integer isfamily;
    public final static Parcelable.Creator<Photo> CREATOR = new Creator<Photo>() {
        @SuppressWarnings({
                "unchecked"
        })
        public Photo createFromParcel(Parcel in) {
            return new Photo(in);
        }

        public Photo[] newArray(int size) {
            return (new Photo[size]);
        }

    };

    protected Photo(Parcel in) {
        this.id = ((String) in.readValue((String.class.getClassLoader())));
        this.owner = ((String) in.readValue((String.class.getClassLoader())));
        this.secret = ((String) in.readValue((String.class.getClassLoader())));
        this.server = ((String) in.readValue((String.class.getClassLoader())));
        this.farm = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.title = ((String) in.readValue((String.class.getClassLoader())));
        this.ispublic = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.isfriend = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.isfamily = ((Integer) in.readValue((Integer.class.getClassLoader())));
    }

    public Photo() {
    }

    @Bindable
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Bindable
    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    @Bindable
    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    @Bindable
    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    @Bindable
    public Integer getFarm() {
        return farm;
    }

    public void setFarm(Integer farm) {
        this.farm = farm;
    }

    @Bindable
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Bindable
    public Integer getIspublic() {
        return ispublic;
    }

    public void setIspublic(Integer ispublic) {
        this.ispublic = ispublic;
    }

    @Bindable
    public Integer getIsfriend() {
        return isfriend;
    }

    public void setIsfriend(Integer isfriend) {
        this.isfriend = isfriend;
    }

    @Bindable
    public Integer getIsfamily() {
        return isfamily;
    }

    public void setIsfamily(Integer isfamily) {
        this.isfamily = isfamily;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(owner);
        dest.writeValue(secret);
        dest.writeValue(server);
        dest.writeValue(farm);
        dest.writeValue(title);
        dest.writeValue(ispublic);
        dest.writeValue(isfriend);
        dest.writeValue(isfamily);
    }

    public int describeContents() {
        return 0;
    }

    public static final DiffUtil.ItemCallback<Photo> CALLBACK = new DiffUtil.ItemCallback<Photo>() {
        @Override
        public boolean areItemsTheSame(@NonNull Photo oldItem, @NonNull Photo newItem) {
            return oldItem.id == newItem.id;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Photo oldItem, @NonNull Photo newItem) {
            return true;
        }
    };
}