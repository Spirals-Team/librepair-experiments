package com.may.apimanagementsystem.service;

import com.github.pagehelper.PageHelper;
import com.may.apimanagementsystem.dao.InterfaceMapper;
import com.may.apimanagementsystem.exception.ParameterException;
import com.may.apimanagementsystem.exception.ServerException;
import com.may.apimanagementsystem.po.Interfaces;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

import static com.may.apimanagementsystem.constant.ExceptionMessage.*;

@Service
public class InterfaceService {

    @Resource
    private InterfaceMapper interfaceMapper;

    public List<Interfaces> getInterfaces(int pageNum, int pageSize, int projectId) throws Exception {
        PageHelper.startPage(pageNum, pageSize);
        List<Interfaces> interfaceList = interfaceMapper.getInterfaceList(projectId);
        return interfaceList;
    }

    public boolean addInterface(Interfaces interfaces) {
        boolean flag = false;
        checkAddInterfaceParameter(interfaces);
        boolean result = interfaceMapper.insertInterface(interfaces);
        if(!result) {
            throw new ServerException();
        }
        flag = true;
        return flag;
    }

    public boolean updateInterface(Interfaces interfaces) {
        boolean flag = false;
        checkUpdateInterfaceParameter(interfaces);
        boolean result = interfaceMapper.updateInterface(interfaces);
        if(!result) {
            throw new ServerException();
        }
        flag =  true;
        return flag;
    }

    private void checkUpdateInterfaceParameter(Interfaces interfaces) {
        if(interfaces.getInterfaceName() != null && interfaces.getDescription() !=null) {
            checkInterfaceName(interfaces.getInterfaceName());
            checkInterfaceDescription(interfaces.getDescription());
        }else{
            throw new ParameterException(PARAMETER_CANNOT_NULL);
        }
    }

    public boolean removeInterface(int interfaceId) {
        boolean flag = false;
        boolean result = interfaceMapper.deleteInterface(interfaceId);
        if(!result) {
            throw new ServerException();
        }
        flag = true;
        return flag;
    }

    public Interfaces getInterfaceByInterfaceId(int interfaceId) {
        Interfaces interfaces = interfaceMapper.findInterfaceByInterfaceId(interfaceId);
        Objects.requireNonNull(interfaces);
        return interfaces;
    }

    private void checkAddInterfaceParameter(Interfaces interfaces){
        if(interfaces.getInterfaceName() != null && interfaces.getDescription() !=null) {
            checkInterfaceName(interfaces.getInterfaceName());
            checkInterfaceDescription(interfaces.getDescription());
        }else{
            throw new ParameterException(PARAMETER_CANNOT_NULL);
        }
    }

    private void checkInterfaceName(String interfaceName){
        if (interfaceName.length() > 20)
            throw new ParameterException(INTERFACE_NAME_IS_TOO_LONG);
    }

    private void checkInterfaceDescription(String projectDescription){
        if(projectDescription.length()>255)
            throw new ParameterException(INTERFACE_DESCRIPTION_IS_TOO_LONG);
    }

//    private void checkUpdateProjectParameter(Interfaces interfaces){
//        if(interfaces.getInterfaceName() != null){
//            checkInterfaceName(interfaces.getInterfaceName());
//        }
//        if(interfaces.getDescription() != null){
//            checkInterfaceDescription(interfaces.getDescription());
//        }
//    }



//    public String downloadInterface(HttpServletRequest request, HttpServletResponse response) {
//        String fileName = "aim_test.txt";// 设置文件名，根据业务需要替换成要下载的文件名
//        if (fileName != null) {
//            //设置文件路径
//            String realPath = "D://api//";
//            File file = new File(realPath, fileName);
//            if (file.exists()) {
//                response.setContentType("application/force-download");// 设置强制下载不打开
//                response.addHeader("Content-Disposition", "attachment;fileName=" + fileName);// 设置文件名
//                byte[] buffer = new byte[1024];
//                FileInputStream fis = null;
//                BufferedInputStream bis = null;
//                try {
//                    fis = new FileInputStream(file);
//                    bis = new BufferedInputStream(fis);
//                    OutputStream os = response.getOutputStream();
//                    int i = bis.read(buffer);
//                    while (i != -1) {
//                        os.write(buffer, 0, i);
//                        i = bis.read(buffer);
//                    }
//                    System.out.println("success");
//                } catch (Exception e) {
//                    e.printStackTrace();
//                } finally {
//                    if (bis != null) {
//                        try {
//                            bis.close();
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                    if (fis != null) {
//                        try {
//                            fis.close();
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }
//            }
//        }
//        return null;
//    }

}
