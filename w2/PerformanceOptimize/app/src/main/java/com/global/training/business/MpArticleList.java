package com.global.training.business;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MpArticleList implements Serializable {

    @SerializedName("curPage")
    @Expose
    private Integer curPage;
    @SerializedName("datas")
    @Expose
    private List<ArticleInfo> datas = null;
    @SerializedName("offset")
    @Expose
    private Integer offset;
    @SerializedName("over")
    @Expose
    private Boolean over;
    @SerializedName("pageCount")
    @Expose
    private Integer pageCount;
    @SerializedName("size")
    @Expose
    private Integer size;
    @SerializedName("total")
    @Expose
    private Integer total;

    public Integer getCurPage() {
        return curPage;
    }

    public void setCurPage(Integer curPage) {
        this.curPage = curPage;
    }

    public List<ArticleInfo> getDatas() {
        return datas;
    }

    public void setDatas(List<ArticleInfo> datas) {
        this.datas = datas;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public Boolean getOver() {
        return over;
    }

    public void setOver(Boolean over) {
        this.over = over;
    }

    public Integer getPageCount() {
        return pageCount;
    }

    public void setPageCount(Integer pageCount) {
        this.pageCount = pageCount;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(MpArticleList.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("curPage");
        sb.append('=');
        sb.append(((this.curPage == null)?"<null>":this.curPage));
        sb.append(',');
        sb.append("datas");
        sb.append('=');
        sb.append(((this.datas == null)?"<null>":this.datas));
        sb.append(',');
        sb.append("offset");
        sb.append('=');
        sb.append(((this.offset == null)?"<null>":this.offset));
        sb.append(',');
        sb.append("over");
        sb.append('=');
        sb.append(((this.over == null)?"<null>":this.over));
        sb.append(',');
        sb.append("pageCount");
        sb.append('=');
        sb.append(((this.pageCount == null)?"<null>":this.pageCount));
        sb.append(',');
        sb.append("size");
        sb.append('=');
        sb.append(((this.size == null)?"<null>":this.size));
        sb.append(',');
        sb.append("total");
        sb.append('=');
        sb.append(((this.total == null)?"<null>":this.total));
        sb.append(',');
        if (sb.charAt((sb.length()- 1)) == ',') {
            sb.setCharAt((sb.length()- 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }

}