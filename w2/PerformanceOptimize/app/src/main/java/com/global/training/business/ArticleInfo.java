package com.global.training.business;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ArticleInfo  implements Serializable {

    @SerializedName("apkLink")
    @Expose
    private String apkLink;
    @SerializedName("audit")
    @Expose
    private Integer audit;
    @SerializedName("author")
    @Expose
    private String author;
    @SerializedName("canEdit")
    @Expose
    private Boolean canEdit;
    @SerializedName("chapterId")
    @Expose
    private Integer chapterId;
    @SerializedName("chapterName")
    @Expose
    private String chapterName;
    @SerializedName("collect")
    @Expose
    private Boolean collect;
    @SerializedName("courseId")
    @Expose
    private Integer courseId;
    @SerializedName("desc")
    @Expose
    private String desc;
    @SerializedName("descMd")
    @Expose
    private String descMd;
    @SerializedName("envelopePic")
    @Expose
    private String envelopePic;
    @SerializedName("fresh")
    @Expose
    private Boolean fresh;
    @SerializedName("host")
    @Expose
    private String host;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("link")
    @Expose
    private String link;
    @SerializedName("niceDate")
    @Expose
    private String niceDate;
    @SerializedName("niceShareDate")
    @Expose
    private String niceShareDate;
    @SerializedName("origin")
    @Expose
    private String origin;
    @SerializedName("prefix")
    @Expose
    private String prefix;
    @SerializedName("projectLink")
    @Expose
    private String projectLink;
    @SerializedName("publishTime")
    @Expose
    private Long publishTime;
    @SerializedName("realSuperChapterId")
    @Expose
    private Integer realSuperChapterId;
    @SerializedName("selfVisible")
    @Expose
    private Integer selfVisible;
    @SerializedName("shareDate")
    @Expose
    private Long shareDate;
    @SerializedName("shareUser")
    @Expose
    private String shareUser;
    @SerializedName("superChapterId")
    @Expose
    private Integer superChapterId;
    @SerializedName("superChapterName")
    @Expose
    private String superChapterName;
    @SerializedName("tags")
    @Expose
    private List<Tag> tags = null;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("type")
    @Expose
    private Integer type;
    @SerializedName("userId")
    @Expose
    private Integer userId;
    @SerializedName("visible")
    @Expose
    private Integer visible;
    @SerializedName("zan")
    @Expose
    private Integer zan;

    public String getApkLink() {
        return apkLink;
    }

    public void setApkLink(String apkLink) {
        this.apkLink = apkLink;
    }

    public Integer getAudit() {
        return audit;
    }

    public void setAudit(Integer audit) {
        this.audit = audit;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Boolean getCanEdit() {
        return canEdit;
    }

    public void setCanEdit(Boolean canEdit) {
        this.canEdit = canEdit;
    }

    public Integer getChapterId() {
        return chapterId;
    }

    public void setChapterId(Integer chapterId) {
        this.chapterId = chapterId;
    }

    public String getChapterName() {
        return chapterName;
    }

    public void setChapterName(String chapterName) {
        this.chapterName = chapterName;
    }

    public Boolean getCollect() {
        return collect;
    }

    public void setCollect(Boolean collect) {
        this.collect = collect;
    }

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getDescMd() {
        return descMd;
    }

    public void setDescMd(String descMd) {
        this.descMd = descMd;
    }

    public String getEnvelopePic() {
        return envelopePic;
    }

    public void setEnvelopePic(String envelopePic) {
        this.envelopePic = envelopePic;
    }

    public Boolean getFresh() {
        return fresh;
    }

    public void setFresh(Boolean fresh) {
        this.fresh = fresh;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getNiceDate() {
        return niceDate;
    }

    public void setNiceDate(String niceDate) {
        this.niceDate = niceDate;
    }

    public String getNiceShareDate() {
        return niceShareDate;
    }

    public void setNiceShareDate(String niceShareDate) {
        this.niceShareDate = niceShareDate;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getProjectLink() {
        return projectLink;
    }

    public void setProjectLink(String projectLink) {
        this.projectLink = projectLink;
    }

    public Long getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(Long publishTime) {
        this.publishTime = publishTime;
    }

    public Integer getRealSuperChapterId() {
        return realSuperChapterId;
    }

    public void setRealSuperChapterId(Integer realSuperChapterId) {
        this.realSuperChapterId = realSuperChapterId;
    }

    public Integer getSelfVisible() {
        return selfVisible;
    }

    public void setSelfVisible(Integer selfVisible) {
        this.selfVisible = selfVisible;
    }

    public Long getShareDate() {
        return shareDate;
    }

    public void setShareDate(Long shareDate) {
        this.shareDate = shareDate;
    }

    public String getShareUser() {
        return shareUser;
    }

    public void setShareUser(String shareUser) {
        this.shareUser = shareUser;
    }

    public Integer getSuperChapterId() {
        return superChapterId;
    }

    public void setSuperChapterId(Integer superChapterId) {
        this.superChapterId = superChapterId;
    }

    public String getSuperChapterName() {
        return superChapterName;
    }

    public void setSuperChapterName(String superChapterName) {
        this.superChapterName = superChapterName;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getVisible() {
        return visible;
    }

    public void setVisible(Integer visible) {
        this.visible = visible;
    }

    public Integer getZan() {
        return zan;
    }

    public void setZan(Integer zan) {
        this.zan = zan;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(ArticleInfo.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("apkLink");
        sb.append('=');
        sb.append(((this.apkLink == null)?"<null>":this.apkLink));
        sb.append(',');
        sb.append("audit");
        sb.append('=');
        sb.append(((this.audit == null)?"<null>":this.audit));
        sb.append(',');
        sb.append("author");
        sb.append('=');
        sb.append(((this.author == null)?"<null>":this.author));
        sb.append(',');
        sb.append("canEdit");
        sb.append('=');
        sb.append(((this.canEdit == null)?"<null>":this.canEdit));
        sb.append(',');
        sb.append("chapterId");
        sb.append('=');
        sb.append(((this.chapterId == null)?"<null>":this.chapterId));
        sb.append(',');
        sb.append("chapterName");
        sb.append('=');
        sb.append(((this.chapterName == null)?"<null>":this.chapterName));
        sb.append(',');
        sb.append("collect");
        sb.append('=');
        sb.append(((this.collect == null)?"<null>":this.collect));
        sb.append(',');
        sb.append("courseId");
        sb.append('=');
        sb.append(((this.courseId == null)?"<null>":this.courseId));
        sb.append(',');
        sb.append("desc");
        sb.append('=');
        sb.append(((this.desc == null)?"<null>":this.desc));
        sb.append(',');
        sb.append("descMd");
        sb.append('=');
        sb.append(((this.descMd == null)?"<null>":this.descMd));
        sb.append(',');
        sb.append("envelopePic");
        sb.append('=');
        sb.append(((this.envelopePic == null)?"<null>":this.envelopePic));
        sb.append(',');
        sb.append("fresh");
        sb.append('=');
        sb.append(((this.fresh == null)?"<null>":this.fresh));
        sb.append(',');
        sb.append("host");
        sb.append('=');
        sb.append(((this.host == null)?"<null>":this.host));
        sb.append(',');
        sb.append("id");
        sb.append('=');
        sb.append(((this.id == null)?"<null>":this.id));
        sb.append(',');
        sb.append("link");
        sb.append('=');
        sb.append(((this.link == null)?"<null>":this.link));
        sb.append(',');
        sb.append("niceDate");
        sb.append('=');
        sb.append(((this.niceDate == null)?"<null>":this.niceDate));
        sb.append(',');
        sb.append("niceShareDate");
        sb.append('=');
        sb.append(((this.niceShareDate == null)?"<null>":this.niceShareDate));
        sb.append(',');
        sb.append("origin");
        sb.append('=');
        sb.append(((this.origin == null)?"<null>":this.origin));
        sb.append(',');
        sb.append("prefix");
        sb.append('=');
        sb.append(((this.prefix == null)?"<null>":this.prefix));
        sb.append(',');
        sb.append("projectLink");
        sb.append('=');
        sb.append(((this.projectLink == null)?"<null>":this.projectLink));
        sb.append(',');
        sb.append("publishTime");
        sb.append('=');
        sb.append(((this.publishTime == null)?"<null>":this.publishTime));
        sb.append(',');
        sb.append("realSuperChapterId");
        sb.append('=');
        sb.append(((this.realSuperChapterId == null)?"<null>":this.realSuperChapterId));
        sb.append(',');
        sb.append("selfVisible");
        sb.append('=');
        sb.append(((this.selfVisible == null)?"<null>":this.selfVisible));
        sb.append(',');
        sb.append("shareDate");
        sb.append('=');
        sb.append(((this.shareDate == null)?"<null>":this.shareDate));
        sb.append(',');
        sb.append("shareUser");
        sb.append('=');
        sb.append(((this.shareUser == null)?"<null>":this.shareUser));
        sb.append(',');
        sb.append("superChapterId");
        sb.append('=');
        sb.append(((this.superChapterId == null)?"<null>":this.superChapterId));
        sb.append(',');
        sb.append("superChapterName");
        sb.append('=');
        sb.append(((this.superChapterName == null)?"<null>":this.superChapterName));
        sb.append(',');
        sb.append("tags");
        sb.append('=');
        sb.append(((this.tags == null)?"<null>":this.tags));
        sb.append(',');
        sb.append("title");
        sb.append('=');
        sb.append(((this.title == null)?"<null>":this.title));
        sb.append(',');
        sb.append("type");
        sb.append('=');
        sb.append(((this.type == null)?"<null>":this.type));
        sb.append(',');
        sb.append("userId");
        sb.append('=');
        sb.append(((this.userId == null)?"<null>":this.userId));
        sb.append(',');
        sb.append("visible");
        sb.append('=');
        sb.append(((this.visible == null)?"<null>":this.visible));
        sb.append(',');
        sb.append("zan");
        sb.append('=');
        sb.append(((this.zan == null)?"<null>":this.zan));
        sb.append(',');
        if (sb.charAt((sb.length()- 1)) == ',') {
            sb.setCharAt((sb.length()- 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }

}