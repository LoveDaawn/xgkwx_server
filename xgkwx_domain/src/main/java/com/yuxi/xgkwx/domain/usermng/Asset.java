package com.yuxi.xgkwx.domain.usermng;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Base64;

@Data
@Accessors(chain = true)
public class Asset {
    private String assetId;
    private String assetType;
    private String assetName;
    private Base64 assetIcon;
    private String insertTime;
    private String updateTime;
}
