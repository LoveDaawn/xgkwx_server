package com.yuxi.xgkwx.domain.usermng;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.swing.*;
import java.util.List;

@Data
@Accessors(chain = true)
public class UserCharacter {
    private String characterRecordId;
    private String userId;
    private String characterId;
    private List<String> characterAsset;
    private String acquiredTime;
    private String lastModified;
}
