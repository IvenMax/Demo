package com.iven.app.bean;

/**
 * @auth Iven
 * 2017/1/15 10:43
 * @desc 版本更新的实体类
 */

public class UpdateVersionBean {
    private String versionCode;
    private String versionName;
    private String apkUrl;
    private String updateTitle;
    private String changeLog;//更新日志

    public String getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(String versionCode) {
        this.versionCode = versionCode;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public String getApkUrl() {
        return apkUrl;
    }

    public void setApkUrl(String apkUrl) {
        this.apkUrl = apkUrl;
    }

    public String getUpdateTitle() {
        return updateTitle;
    }

    public void setUpdateTitle(String updateTitle) {
        this.updateTitle = updateTitle;
    }

    public String getChangeLog() {
        return changeLog;
    }

    public void setChangeLog(String changeLog) {
        this.changeLog = changeLog;
    }

    @Override
    public String toString() {
        return "UpdateVersionBean{" +
                "versionCode='" + versionCode + '\'' +
                ", versionName='" + versionName + '\'' +
                ", apkUrl='" + apkUrl + '\'' +
                ", updateTitle='" + updateTitle + '\'' +
                ", changeLog='" + changeLog + '\'' +
                '}';
    }
}
