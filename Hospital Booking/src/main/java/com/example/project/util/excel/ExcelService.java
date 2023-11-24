package com.example.project.util.excel;

import java.io.IOException;
import java.util.List;

public interface ExcelService {
    byte[] exportXlsx(List<?> dataList, Class<?> dataClass) throws IOException, IllegalAccessException;
}
