package com.project.toy_log_validator.service;

import java.io.InputStream;
import java.io.IOException;

public interface GoogleStorageService {
    InputStream download(String fileId, String uuid) throws IOException;
}
