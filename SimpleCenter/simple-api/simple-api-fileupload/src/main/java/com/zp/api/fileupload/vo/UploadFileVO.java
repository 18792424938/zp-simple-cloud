package com.zp.api.fileupload.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UploadFileVO {
    private String id;
    private String name;
    private String url;
}
