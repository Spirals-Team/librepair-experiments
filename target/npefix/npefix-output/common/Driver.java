package common;

import fr.inria.spirals.npefix.resi.CallChecker;
import fr.inria.spirals.npefix.resi.context.ConstructorContext;
import fr.inria.spirals.npefix.resi.context.MethodContext;
import fr.inria.spirals.npefix.resi.context.TryContext;
import fr.inria.spirals.npefix.resi.exception.AbnormalExecutionError;
import fr.inria.spirals.npefix.resi.exception.ForceReturn;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.PosixFilePermission;
import java.util.HashSet;
import java.util.Set;
import org.apache.commons.lang.SystemUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public abstract class Driver {
    private static WebDriver instance;

    public Driver() {
        ConstructorContext _bcornu_methode_context1 = new ConstructorContext(Driver.class, 27, 764, 799);
        try {
        } finally {
            _bcornu_methode_context1.methodEnd();
        }
    }

    public static void setup(WebDriver webdriver) {
        MethodContext _bcornu_methode_context1 = new MethodContext(void.class, 32, 810, 959);
        try {
            CallChecker.varInit(webdriver, "webdriver", 32, 810, 959);
            CallChecker.varInit(Driver.instance, "common.Driver.instance", 32, 810, 959);
            Driver.instance = webdriver;
            CallChecker.varAssign(Driver.instance, "Driver.instance", 34, 870, 897);
            Common.Setting.setup(Driver.instance);
        } catch (ForceReturn _bcornu_return_t) {
            _bcornu_return_t.getDecision().getValue();
            return ;
        } finally {
            _bcornu_methode_context1.methodEnd();
        }
    }

    public static void quit() {
        MethodContext _bcornu_methode_context2 = new MethodContext(void.class, 39, 970, 1038);
        try {
            CallChecker.varInit(Driver.instance, "common.Driver.instance", 39, 970, 1038);
            if (CallChecker.beforeDeref(Driver.instance, WebDriver.class, 41, 1010, 1024)) {
                Driver.instance = CallChecker.beforeCalled(Driver.instance, WebDriver.class, 41, 1010, 1024);
                CallChecker.isCalled(Driver.instance, WebDriver.class, 41, 1010, 1024).quit();
            }
        } catch (ForceReturn _bcornu_return_t) {
            _bcornu_return_t.getDecision().getValue();
            return ;
        } finally {
            _bcornu_methode_context2.methodEnd();
        }
    }

    protected static WebDriver getLinuxDriver(String driverName, String property) {
        MethodContext _bcornu_methode_context3 = new MethodContext(WebDriver.class, 44, 1049, 1194);
        try {
            CallChecker.varInit(property, "property", 44, 1049, 1194);
            CallChecker.varInit(driverName, "driverName", 44, 1049, 1194);
            CallChecker.varInit(Driver.instance, "common.Driver.instance", 44, 1049, 1194);
            return Driver.getDriver("linux", driverName, property);
        } catch (ForceReturn _bcornu_return_t) {
            return ((WebDriver) (_bcornu_return_t.getDecision().getValue()));
        } finally {
            _bcornu_methode_context3.methodEnd();
        }
    }

    protected static WebDriver getWindowsDriver(String driverName, String property) {
        MethodContext _bcornu_methode_context4 = new MethodContext(WebDriver.class, 49, 1205, 1354);
        try {
            CallChecker.varInit(property, "property", 49, 1205, 1354);
            CallChecker.varInit(driverName, "driverName", 49, 1205, 1354);
            CallChecker.varInit(Driver.instance, "common.Driver.instance", 49, 1205, 1354);
            return Driver.getDriver("windows", driverName, property);
        } catch (ForceReturn _bcornu_return_t) {
            return ((WebDriver) (_bcornu_return_t.getDecision().getValue()));
        } finally {
            _bcornu_methode_context4.methodEnd();
        }
    }

    protected static WebDriver getMacDriver(String driverName, String property) {
        MethodContext _bcornu_methode_context5 = new MethodContext(WebDriver.class, 54, 1365, 1506);
        try {
            CallChecker.varInit(property, "property", 54, 1365, 1506);
            CallChecker.varInit(driverName, "driverName", 54, 1365, 1506);
            CallChecker.varInit(Driver.instance, "common.Driver.instance", 54, 1365, 1506);
            return Driver.getDriver("mac", driverName, property);
        } catch (ForceReturn _bcornu_return_t) {
            return ((WebDriver) (_bcornu_return_t.getDecision().getValue()));
        } finally {
            _bcornu_methode_context5.methodEnd();
        }
    }

    private static WebDriver getDriver(String driverPath, String driverName, String property) {
        MethodContext _bcornu_methode_context6 = new MethodContext(WebDriver.class, 59, 1517, 2066);
        try {
            CallChecker.varInit(property, "property", 59, 1517, 2066);
            CallChecker.varInit(driverName, "driverName", 59, 1517, 2066);
            CallChecker.varInit(driverPath, "driverPath", 59, 1517, 2066);
            CallChecker.varInit(Driver.instance, "common.Driver.instance", 59, 1517, 2066);
            String driverLocation = CallChecker.varInit(((driverPath + "/") + driverName), "driverLocation", 61, 1621, 1674);
            if (!(Driver.isResourceInChildProject(driverLocation))) {
                Driver.copyResourceFileToChildProject(driverLocation);
                if (SystemUtils.IS_OS_LINUX) {
                    Driver.setExecutePermission(driverLocation);
                }
            }
            System.setProperty(property, driverLocation);
            return Driver.getDriver(property);
        } catch (ForceReturn _bcornu_return_t) {
            return ((WebDriver) (_bcornu_return_t.getDecision().getValue()));
        } finally {
            _bcornu_methode_context6.methodEnd();
        }
    }

    private static boolean isResourceInChildProject(String pathname) {
        MethodContext _bcornu_methode_context7 = new MethodContext(boolean.class, 78, 2077, 2196);
        try {
            CallChecker.varInit(pathname, "pathname", 78, 2077, 2196);
            CallChecker.varInit(Driver.instance, "common.Driver.instance", 78, 2077, 2196);
            if (CallChecker.beforeDeref(new File(pathname), File.class, 80, 2163, 2180)) {
                return CallChecker.isCalled(new File(pathname), File.class, 80, 2163, 2180).isFile();
            }else
                throw new AbnormalExecutionError();
            
        } catch (ForceReturn _bcornu_return_t) {
            return ((Boolean) (_bcornu_return_t.getDecision().getValue()));
        } finally {
            _bcornu_methode_context7.methodEnd();
        }
    }

    private static WebDriver getDriver(String property) {
        MethodContext _bcornu_methode_context8 = new MethodContext(WebDriver.class, 83, 2207, 2800);
        try {
            CallChecker.varInit(property, "property", 83, 2207, 2800);
            CallChecker.varInit(Driver.instance, "common.Driver.instance", 83, 2207, 2800);
            WebDriver webDriver = CallChecker.varInit(null, "webDriver", 85, 2273, 2299);
            if (CallChecker.beforeDeref(property, String.class, 87, 2321, 2328)) {
                property = CallChecker.beforeCalled(property, String.class, 87, 2321, 2328);
                if (CallChecker.isCalled(property, String.class, 87, 2321, 2328).contains("chrome")) {
                    webDriver = new ChromeDriver(Driver.getChromeOptions());
                    CallChecker.varAssign(webDriver, "webDriver", 89, 2372, 2420);
                }else
                    if (CallChecker.beforeDeref(property, String.class, 91, 2448, 2455)) {
                        property = CallChecker.beforeCalled(property, String.class, 91, 2448, 2455);
                        if (CallChecker.isCalled(property, String.class, 91, 2448, 2455).contains("gecko")) {
                            webDriver = new org.openqa.selenium.firefox.FirefoxDriver();
                            CallChecker.varAssign(webDriver, "webDriver", 93, 2498, 2529);
                        }else
                            if (CallChecker.beforeDeref(property, String.class, 95, 2557, 2564)) {
                                property = CallChecker.beforeCalled(property, String.class, 95, 2557, 2564);
                                if (CallChecker.isCalled(property, String.class, 95, 2557, 2564).contains("ie")) {
                                    webDriver = new org.openqa.selenium.ie.InternetExplorerDriver();
                                    CallChecker.varAssign(webDriver, "webDriver", 97, 2604, 2644);
                                }else
                                    if (CallChecker.beforeDeref(property, String.class, 99, 2672, 2679)) {
                                        property = CallChecker.beforeCalled(property, String.class, 99, 2672, 2679);
                                        if (CallChecker.isCalled(property, String.class, 99, 2672, 2679).contains("edge")) {
                                            webDriver = new org.openqa.selenium.edge.EdgeDriver();
                                            CallChecker.varAssign(webDriver, "webDriver", 101, 2721, 2749);
                                        }
                                    }
                                
                            }
                        
                    }
                
            }
            return webDriver;
        } catch (ForceReturn _bcornu_return_t) {
            return ((WebDriver) (_bcornu_return_t.getDecision().getValue()));
        } finally {
            _bcornu_methode_context8.methodEnd();
        }
    }

    private static ChromeOptions getChromeOptions() {
        MethodContext _bcornu_methode_context9 = new MethodContext(ChromeOptions.class, 107, 2811, 3163);
        try {
            CallChecker.varInit(Driver.instance, "common.Driver.instance", 107, 2811, 3163);
            ChromeOptions options = CallChecker.varInit(new ChromeOptions(), "options", 109, 2873, 2916);
            String property = CallChecker.varInit(System.getProperty("headlessChrome"), "property", 111, 2935, 2989);
            if ((property != null) && (property.equals("true"))) {
                if (CallChecker.beforeDeref(options, ChromeOptions.class, 115, 3082, 3088)) {
                    options = CallChecker.beforeCalled(options, ChromeOptions.class, 115, 3082, 3088);
                    CallChecker.isCalled(options, ChromeOptions.class, 115, 3082, 3088).addArguments("headless");
                }
            }
            return options;
        } catch (ForceReturn _bcornu_return_t) {
            return ((ChromeOptions) (_bcornu_return_t.getDecision().getValue()));
        } finally {
            _bcornu_methode_context9.methodEnd();
        }
    }

    private static void copyResourceFileToChildProject(String filename) {
        MethodContext _bcornu_methode_context10 = new MethodContext(void.class, 121, 3170, 4051);
        try {
            CallChecker.varInit(filename, "filename", 121, 3170, 4051);
            CallChecker.varInit(Driver.instance, "common.Driver.instance", 121, 3170, 4051);
            TryContext _bcornu_try_context_1 = new TryContext(1, Driver.class, "java.lang.Exception");
            try {
                Path temp = CallChecker.varInit(Files.createTempFile("temp", ".tmp"), "temp", 125, 3278, 3326);
                if (CallChecker.beforeDeref(Driver.class.getClassLoader(), ClassLoader.class, 126, 3351, 3379)) {
                    Files.copy(CallChecker.isCalled(Driver.class.getClassLoader(), ClassLoader.class, 126, 3351, 3379).getResourceAsStream(filename), temp, StandardCopyOption.REPLACE_EXISTING);
                }
                FileInputStream inputStream = CallChecker.init(FileInputStream.class);
                if (CallChecker.beforeDeref(temp, Path.class, 127, 3518, 3521)) {
                    temp = CallChecker.beforeCalled(temp, Path.class, 127, 3518, 3521);
                    inputStream = new FileInputStream(CallChecker.isCalled(temp, Path.class, 127, 3518, 3521).toFile());
                    CallChecker.varAssign(inputStream, "inputStream", 127, 3518, 3521);
                }
                byte[] buffer = CallChecker.init(byte[].class);
                if (CallChecker.beforeDeref(inputStream, FileInputStream.class, 129, 3584, 3594)) {
                    inputStream = CallChecker.beforeCalled(inputStream, FileInputStream.class, 129, 3584, 3594);
                    buffer = new byte[CallChecker.isCalled(inputStream, FileInputStream.class, 129, 3584, 3594).available()];
                    CallChecker.varAssign(buffer, "buffer", 129, 3584, 3594);
                }
                if (CallChecker.beforeDeref(inputStream, FileInputStream.class, 130, 3622, 3632)) {
                    inputStream = CallChecker.beforeCalled(inputStream, FileInputStream.class, 130, 3622, 3632);
                    CallChecker.isCalled(inputStream, FileInputStream.class, 130, 3622, 3632).read(buffer);
                }
                File file = CallChecker.varInit(new File(filename), "file", 132, 3673, 3703);
                if (CallChecker.beforeDeref(file, File.class, 133, 3717, 3720)) {
                    file = CallChecker.beforeCalled(file, File.class, 133, 3717, 3720);
                    if (CallChecker.beforeDeref(CallChecker.isCalled(file, File.class, 133, 3717, 3720).getParentFile(), File.class, 133, 3717, 3736)) {
                        file = CallChecker.beforeCalled(file, File.class, 133, 3717, 3720);
                        CallChecker.isCalled(CallChecker.isCalled(file, File.class, 133, 3717, 3720).getParentFile(), File.class, 133, 3717, 3736).mkdirs();
                    }
                }
                OutputStream outputStream = CallChecker.varInit(new FileOutputStream(file), "outputStream", 134, 3760, 3814);
                if (CallChecker.beforeDeref(outputStream, OutputStream.class, 135, 3828, 3839)) {
                    outputStream = CallChecker.beforeCalled(outputStream, OutputStream.class, 135, 3828, 3839);
                    CallChecker.isCalled(outputStream, OutputStream.class, 135, 3828, 3839).write(buffer);
                }
                if (CallChecker.beforeDeref(outputStream, OutputStream.class, 136, 3868, 3879)) {
                    outputStream = CallChecker.beforeCalled(outputStream, OutputStream.class, 136, 3868, 3879);
                    CallChecker.isCalled(outputStream, OutputStream.class, 136, 3868, 3879).flush();
                }
                if (CallChecker.beforeDeref(outputStream, OutputStream.class, 137, 3902, 3913)) {
                    outputStream = CallChecker.beforeCalled(outputStream, OutputStream.class, 137, 3902, 3913);
                    CallChecker.isCalled(outputStream, OutputStream.class, 137, 3902, 3913).close();
                }
                if (CallChecker.beforeDeref(inputStream, FileInputStream.class, 138, 3936, 3946)) {
                    inputStream = CallChecker.beforeCalled(inputStream, FileInputStream.class, 138, 3936, 3946);
                    CallChecker.isCalled(inputStream, FileInputStream.class, 138, 3936, 3946).close();
                }
            } catch (Exception e) {
                _bcornu_try_context_1.catchStart(1);
                e.printStackTrace();
            } finally {
                _bcornu_try_context_1.finallyStart(1);
            }
        } catch (ForceReturn _bcornu_return_t) {
            _bcornu_return_t.getDecision().getValue();
            return ;
        } finally {
            _bcornu_methode_context10.methodEnd();
        }
    }

    private static void setExecutePermission(String driver) {
        MethodContext _bcornu_methode_context11 = new MethodContext(void.class, 146, 4062, 4176);
        try {
            CallChecker.varInit(driver, "driver", 146, 4062, 4176);
            CallChecker.varInit(Driver.instance, "common.Driver.instance", 146, 4062, 4176);
            Driver.setExecutePermission(new File(driver));
        } catch (ForceReturn _bcornu_return_t) {
            _bcornu_return_t.getDecision().getValue();
            return ;
        } finally {
            _bcornu_methode_context11.methodEnd();
        }
    }

    private static void setExecutePermission(File file) {
        MethodContext _bcornu_methode_context12 = new MethodContext(void.class, 151, 4187, 4549);
        try {
            CallChecker.varInit(file, "file", 151, 4187, 4549);
            CallChecker.varInit(Driver.instance, "common.Driver.instance", 151, 4187, 4549);
            Set<PosixFilePermission> perms = CallChecker.varInit(new HashSet<>(), "perms", 153, 4253, 4301);
            if (CallChecker.beforeDeref(perms, Set.class, 154, 4311, 4315)) {
                perms = CallChecker.beforeCalled(perms, Set.class, 154, 4311, 4315);
                CallChecker.isCalled(perms, Set.class, 154, 4311, 4315).add(PosixFilePermission.OWNER_EXECUTE);
            }
            TryContext _bcornu_try_context_2 = new TryContext(2, Driver.class, "java.io.IOException");
            try {
                if (CallChecker.beforeDeref(file, File.class, 158, 4430, 4433)) {
                    file = CallChecker.beforeCalled(file, File.class, 158, 4430, 4433);
                    Files.setPosixFilePermissions(CallChecker.isCalled(file, File.class, 158, 4430, 4433).toPath(), perms);
                }
            } catch (IOException e) {
                _bcornu_try_context_2.catchStart(2);
                e.printStackTrace();
            } finally {
                _bcornu_try_context_2.finallyStart(2);
            }
        } catch (ForceReturn _bcornu_return_t) {
            _bcornu_return_t.getDecision().getValue();
            return ;
        } finally {
            _bcornu_methode_context12.methodEnd();
        }
    }
}

