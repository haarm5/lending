package com.tmb.oneapp.lendingservice.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SFTPStoreFileInfo {
    private String rootPath;
    private String dstDir;
    private String srcFile;
}
