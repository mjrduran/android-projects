package br.com.moacir.testingrx.multiple.download;

import java.io.Serializable;
import java.util.List;

/**
 * Created by moacir on 22/10/16.
 */

public class MediaCollection implements Serializable {

    public final List<MediaInfo> files;

    public MediaCollection(List<MediaInfo> mediaInfoList) {
        this.files = mediaInfoList;
    }
}
