package com.orange.flickrtask.model;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PhotoDBResponse implements Parcelable
{
    @SerializedName("page")
    @Expose
    private Integer page;
    @SerializedName("pages")
    @Expose
    private Integer pages;
    @SerializedName("perpage")
    @Expose
    private Integer perpage;
    @SerializedName("total")
    @Expose
    private String total;
    @SerializedName("photo")
    @Expose
    private List<Photo> photo = null;
    public final static Parcelable.Creator<PhotoDBResponse> CREATOR = new Creator<PhotoDBResponse>() {
        @SuppressWarnings({
                "unchecked"
        })
        public PhotoDBResponse createFromParcel(Parcel in) {
            return new PhotoDBResponse(in);
        }

        public PhotoDBResponse[] newArray(int size) {
            return (new PhotoDBResponse[size]);
        }

    };

    protected PhotoDBResponse(Parcel in) {
        this.page = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.pages = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.perpage = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.total = ((String) in.readValue((String.class.getClassLoader())));
        in.readList(this.photo, (com.orange.flickrtask.model.Photo.class.getClassLoader()));
    }

    public PhotoDBResponse() {
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getPages() {
        return pages;
    }

    public void setPages(Integer pages) {
        this.pages = pages;
    }

    public Integer getPerpage() {
        return perpage;
    }

    public void setPerpage(Integer perpage) {
        this.perpage = perpage;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public List<Photo> getPhoto() {
        return photo;
    }

    public void setPhoto(List<Photo> photo) {
        this.photo = photo;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(page);
        dest.writeValue(pages);
        dest.writeValue(perpage);
        dest.writeValue(total);
        dest.writeList(photo);
    }

    public int describeContents() {
        return 0;
    }

}
