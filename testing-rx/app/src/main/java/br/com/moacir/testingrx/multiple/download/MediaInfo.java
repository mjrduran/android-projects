package br.com.moacir.testingrx.multiple.download;

import java.io.Serializable;

/**
 * Created by moacir on 22/10/16.
 */

public class MediaInfo implements Serializable {

    public String name;
    public String url;
    public String filePath;
    public boolean downloading = true;

    public MediaInfo(String name, String url) {
        this.name = name;
        this.url = url;
    }
}
