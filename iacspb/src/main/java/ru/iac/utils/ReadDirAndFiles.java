package ru.iac.utils;

import ru.iac.domain.ListTable;
import ru.iac.domain.PathTable;
import java.io.File;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 */
public class ReadDirAndFiles {
    public boolean isDir(String path) {
        File dir = new File(path);
        return dir.isDirectory();
    }

    public List<ListTable> getAll(PathTable pathTable) {
        File dir = new File(pathTable.getPath());
        List<ListTable> list = new LinkedList<>();
        int countDirs = 0;
        int countFiles = 0;
        long sumSizeFiles = 0;
        for (File item: dir.listFiles()) {
            ListTable listTable = new ListTable();
            listTable.setPathTable(pathTable);
            listTable.setName(item.getName());
            if (item.isDirectory()) {
                listTable.setSize("DIR");
                countDirs++;
            } else {
                listTable.setSize(ConvertByte.conv(item.length()));
                countFiles++;
                sumSizeFiles += item.length();
            }
            list.add(listTable);
        }
        pathTable.setCountDir(countDirs);
        pathTable.setCountFile(countFiles);
        pathTable.setSumFile(ConvertByte.conv(sumSizeFiles));
        return list;
    }
}
