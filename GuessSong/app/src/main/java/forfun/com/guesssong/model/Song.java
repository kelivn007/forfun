/*
 * Copyright (C) 2016 Baidu, Inc. All Rights Reserved.
 */
package forfun.com.guesssong.model;

/**
 * Created by huangwei05 on 16/7/22.
 */
public class Song {
    private String mName;
    private String mPath;

    public Song(String path, String name) {
        this.mName = name;
        this.mPath = path;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        this.mName = name;
    }

    public String getPath() {
        return mPath;
    }

    public void setPath(String path) {
        this.mPath = path;
    }
}
