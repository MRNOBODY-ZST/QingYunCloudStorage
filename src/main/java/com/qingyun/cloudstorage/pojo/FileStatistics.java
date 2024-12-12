package com.qingyun.cloudstorage.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FileStatistics {
    private String id;
    private String user_id;
    private String file_num;
    private String folder_num;
    private String size;
}
