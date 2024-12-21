package com.yuxi.xgkwx.domain.usermng;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class UserAsset {
    private String assetRecordId;
    private String userId;
    private String assetId;
    private Integer quantity;
    private String acquiredTime;
    private String lastModified;
}
