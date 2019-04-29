package com.swp.culturehomestay.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.File;

public class PostFileBody {

    @SerializedName("file")
    @Expose
    private File file;
    @SerializedName("filePath")
    @Expose
    private String filePath;

    public PostFileBody() {
    }

    public PostFileBody(File file, String filePath) {
        this.file = file;
        this.filePath = filePath;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}
