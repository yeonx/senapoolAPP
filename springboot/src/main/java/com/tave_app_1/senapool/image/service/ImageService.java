package com.tave_app_1.senapool.image.service;

import com.tave_app_1.senapool.util.FileUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ImageService {

    private final FileUtil fileUtil;

    public String getFilePath(String type) {
        if(type.equals("plant")) return FileUtil.plantFolderPath;
        else if(type.equals("user")) return FileUtil.userFolderPath;
        else if(type.equals("diary")) return FileUtil.diaryFolderPath;
        else return "bad request";
    }
}
