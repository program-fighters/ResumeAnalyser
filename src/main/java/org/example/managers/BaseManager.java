package org.example.managers;

import org.example.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;

public class BaseManager {
   @Autowired protected FileService fileService;

}
