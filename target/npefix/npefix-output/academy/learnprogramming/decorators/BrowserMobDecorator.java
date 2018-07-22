package academy.learnprogramming.decorators;


import academy.learnprogramming.AutomatedBrowser;
import academy.learnprogramming.decoratorbase.AutomatedBrowserBase;
import academy.learnprogramming.exceptions.SaveException;
import fr.inria.spirals.npefix.resi.CallChecker;
import fr.inria.spirals.npefix.resi.context.ConstructorContext;
import fr.inria.spirals.npefix.resi.context.MethodContext;
import fr.inria.spirals.npefix.resi.context.TryContext;
import fr.inria.spirals.npefix.resi.exception.ForceReturn;
import io.netty.handler.codec.http.DefaultHttpResponse;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;
import java.io.File;
import java.io.IOException;
import java.util.EnumSet;
import java.util.regex.Pattern;
import net.lightbody.bmp.BrowserMobProxy;
import net.lightbody.bmp.BrowserMobProxyServer;
import net.lightbody.bmp.core.har.Har;
import net.lightbody.bmp.filters.RequestFilter;
import net.lightbody.bmp.filters.ResponseFilter;
import net.lightbody.bmp.proxy.CaptureType;
import net.lightbody.bmp.util.HttpMessageContents;
import net.lightbody.bmp.util.HttpMessageInfo;
import org.apache.http.HttpHeaders;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;


public class BrowserMobDecorator extends AutomatedBrowserBase {
    private BrowserMobProxy proxy;

    public BrowserMobDecorator(final AutomatedBrowser automatedBrowser) {
        super(automatedBrowser);
        ConstructorContext _bcornu_methode_context1 = new ConstructorContext(BrowserMobDecorator.class, 25, 904, 1011);
        try {
        } finally {
            _bcornu_methode_context1.methodEnd();
        }
    }

    @Override
    public DesiredCapabilities getDesiredCapabilities() {
        MethodContext _bcornu_methode_context1 = new MethodContext(DesiredCapabilities.class, 30, 1018, 1588);
        try {
            CallChecker.varInit(this, "this", 30, 1018, 1588);
            CallChecker.varInit(this.proxy, "proxy", 30, 1018, 1588);
            proxy = new BrowserMobProxyServer();
            CallChecker.varAssign(this.proxy, "this.proxy", 31, 1094, 1129);
            if (CallChecker.beforeDeref(proxy, BrowserMobProxy.class, 32, 1139, 1143)) {
                proxy = CallChecker.beforeCalled(proxy, BrowserMobProxy.class, 32, 1139, 1143);
                CallChecker.isCalled(proxy, BrowserMobProxy.class, 32, 1139, 1143).start(0);
            }
            final DesiredCapabilities desiredCapabilities = CallChecker.varInit(CallChecker.isCalled(getAutomatedBrowser(), AutomatedBrowser.class, 34, 1212, 1232).getDesiredCapabilities(), "desiredCapabilities", 34, 1164, 1258);
            final Proxy seleniumProxy = CallChecker.varInit(new Proxy(), "seleniumProxy", 36, 1269, 1308);
            proxy = CallChecker.beforeCalled(proxy, BrowserMobProxy.class, 37, 1357, 1361);
            final String proxyStr = CallChecker.varInit(("localhost:" + (CallChecker.isCalled(proxy, BrowserMobProxy.class, 37, 1357, 1361).getPort())), "proxyStr", 37, 1318, 1372);
            if (CallChecker.beforeDeref(seleniumProxy, Proxy.class, 39, 1383, 1395)) {
                CallChecker.isCalled(seleniumProxy, Proxy.class, 39, 1383, 1395).setHttpProxy(proxyStr);
            }
            if (CallChecker.beforeDeref(seleniumProxy, Proxy.class, 40, 1429, 1441)) {
                CallChecker.isCalled(seleniumProxy, Proxy.class, 40, 1429, 1441).setSslProxy(proxyStr);
            }
            if (CallChecker.beforeDeref(desiredCapabilities, DesiredCapabilities.class, 42, 1475, 1493)) {
                CallChecker.isCalled(desiredCapabilities, DesiredCapabilities.class, 42, 1475, 1493).setCapability(CapabilityType.PROXY, seleniumProxy);
            }
            return desiredCapabilities;
        } catch (ForceReturn _bcornu_return_t) {
            return ((DesiredCapabilities) (_bcornu_return_t.getDecision().getValue()));
        } finally {
            _bcornu_methode_context1.methodEnd();
        }
    }

    @Override
    public void destroy() {
        MethodContext _bcornu_methode_context2 = new MethodContext(void.class, 48, 1595, 1743);
        try {
            CallChecker.varInit(this, "this", 48, 1595, 1743);
            CallChecker.varInit(this.proxy, "proxy", 48, 1595, 1743);
            if (CallChecker.beforeDeref(getAutomatedBrowser(), AutomatedBrowser.class, 49, 1641, 1661)) {
                CallChecker.isCalled(getAutomatedBrowser(), AutomatedBrowser.class, 49, 1641, 1661).destroy();
            }
            if ((proxy) != null) {
                proxy.stop();
            }
        } catch (ForceReturn _bcornu_return_t) {
            _bcornu_return_t.getDecision().getValue();
            return;
        } finally {
            _bcornu_methode_context2.methodEnd();
        }
    }

    @Override
    public void captureHarFile() {
        MethodContext _bcornu_methode_context3 = new MethodContext(void.class, 56, 1750, 1823);
        try {
            CallChecker.varInit(this, "this", 56, 1750, 1823);
            CallChecker.varInit(this.proxy, "proxy", 56, 1750, 1823);
            if (CallChecker.beforeDeref(proxy, BrowserMobProxy.class, 57, 1803, 1807)) {
                proxy = CallChecker.beforeCalled(proxy, BrowserMobProxy.class, 57, 1803, 1807);
                CallChecker.isCalled(proxy, BrowserMobProxy.class, 57, 1803, 1807).newHar();
            }
        } catch (ForceReturn _bcornu_return_t) {
            _bcornu_return_t.getDecision().getValue();
            return;
        } finally {
            _bcornu_methode_context3.methodEnd();
        }
    }

    @Override
    public void captureCompleteHarFile() {
        MethodContext _bcornu_methode_context4 = new MethodContext(void.class, 61, 1830, 2182);
        try {
            CallChecker.varInit(this, "this", 61, 1830, 2182);
            CallChecker.varInit(this.proxy, "proxy", 61, 1830, 2182);
            final EnumSet<CaptureType> captureTypes = CallChecker.varInit(CaptureType.getAllContentCaptureTypes(), "captureTypes", 62, 1891, 1972);
            if (CallChecker.beforeDeref(captureTypes, EnumSet.class, 63, 1982, 1993)) {
                CallChecker.isCalled(captureTypes, EnumSet.class, 63, 1982, 1993).addAll(CaptureType.getHeaderCaptureTypes());
            }
            if (CallChecker.beforeDeref(captureTypes, EnumSet.class, 64, 2048, 2059)) {
                CallChecker.isCalled(captureTypes, EnumSet.class, 64, 2048, 2059).addAll(CaptureType.getCookieCaptureTypes());
            }
            if (CallChecker.beforeDeref(proxy, BrowserMobProxy.class, 65, 2114, 2118)) {
                proxy = CallChecker.beforeCalled(proxy, BrowserMobProxy.class, 65, 2114, 2118);
                CallChecker.isCalled(proxy, BrowserMobProxy.class, 65, 2114, 2118).setHarCaptureTypes(captureTypes);
            }
            if (CallChecker.beforeDeref(proxy, BrowserMobProxy.class, 66, 2162, 2166)) {
                proxy = CallChecker.beforeCalled(proxy, BrowserMobProxy.class, 66, 2162, 2166);
                CallChecker.isCalled(proxy, BrowserMobProxy.class, 66, 2162, 2166).newHar();
            }
        } catch (ForceReturn _bcornu_return_t) {
            _bcornu_return_t.getDecision().getValue();
            return;
        } finally {
            _bcornu_methode_context4.methodEnd();
        }
    }

    @Override
    public void saveHarFile(final String file) {
        MethodContext _bcornu_methode_context5 = new MethodContext(void.class, 70, 2189, 2410);
        try {
            CallChecker.varInit(this, "this", 70, 2189, 2410);
            CallChecker.varInit(file, "file", 70, 2189, 2410);
            CallChecker.varInit(this.proxy, "proxy", 70, 2189, 2410);
            TryContext _bcornu_try_context_1 = new TryContext(1, BrowserMobDecorator.class, "java.io.IOException");
            try {
                if (CallChecker.beforeDeref(proxy, BrowserMobProxy.class, 72, 2274, 2278)) {
                    proxy = CallChecker.beforeCalled(proxy, BrowserMobProxy.class, 72, 2274, 2278);
                    if (CallChecker.beforeDeref(CallChecker.isCalled(proxy, BrowserMobProxy.class, 72, 2274, 2278).getHar(), Har.class, 72, 2274, 2287)) {
                        proxy = CallChecker.beforeCalled(proxy, BrowserMobProxy.class, 72, 2274, 2278);
                        CallChecker.isCalled(CallChecker.isCalled(proxy, BrowserMobProxy.class, 72, 2274, 2278).getHar(), Har.class, 72, 2274, 2287).writeTo(new File(file));
                    }
                }
            } catch (final IOException ex) {
                _bcornu_try_context_1.catchStart(1);
                throw new SaveException(ex);
            } finally {
                _bcornu_try_context_1.finallyStart(1);
            }
        } catch (ForceReturn _bcornu_return_t) {
            _bcornu_return_t.getDecision().getValue();
            return;
        } finally {
            _bcornu_methode_context5.methodEnd();
        }
    }

    @Override
    public void blockRequestTo(String url, int responseCode) {
        MethodContext _bcornu_methode_context6 = new MethodContext(void.class, 79, 2417, 3018);
        try {
            CallChecker.varInit(this, "this", 79, 2417, 3018);
            CallChecker.varInit(responseCode, "responseCode", 79, 2417, 3018);
            CallChecker.varInit(url, "url", 79, 2417, 3018);
            CallChecker.varInit(this.proxy, "proxy", 79, 2417, 3018);
            if (CallChecker.beforeDeref(proxy, BrowserMobProxy.class, 80, 2498, 2502)) {
                proxy = CallChecker.beforeCalled(proxy, BrowserMobProxy.class, 80, 2498, 2502);
                CallChecker.isCalled(proxy, BrowserMobProxy.class, 80, 2498, 2502).addRequestFilter(( request, contents, messageInfo) -> {
                    if (Pattern.compile(url).matcher(messageInfo.getOriginalUrl()).matches()) {
                        final HttpResponse response = new DefaultHttpResponse(request.getProtocolVersion(), HttpResponseStatus.valueOf(responseCode));
                        response.headers().add(HttpHeaders.CONNECTION, "Close");
                        return response;
                    }
                    return null;
                });
            }
            if (CallChecker.beforeDeref(getAutomatedBrowser(), AutomatedBrowser.class, 90, 2957, 2977)) {
                CallChecker.isCalled(getAutomatedBrowser(), AutomatedBrowser.class, 90, 2957, 2977).blockRequestTo(url, responseCode);
            }
        } catch (ForceReturn _bcornu_return_t) {
            _bcornu_return_t.getDecision().getValue();
            return;
        } finally {
            _bcornu_methode_context6.methodEnd();
        }
    }

    @Override
    public void alterResponseFrom(final String url, final int responseCode, final String responseBody) {
        MethodContext _bcornu_methode_context7 = new MethodContext(void.class, 94, 3025, 3546);
        try {
            CallChecker.varInit(this, "this", 94, 3025, 3546);
            CallChecker.varInit(responseBody, "responseBody", 94, 3025, 3546);
            CallChecker.varInit(responseCode, "responseCode", 94, 3025, 3546);
            CallChecker.varInit(url, "url", 94, 3025, 3546);
            CallChecker.varInit(this.proxy, "proxy", 94, 3025, 3546);
            if (CallChecker.beforeDeref(proxy, BrowserMobProxy.class, 95, 3148, 3152)) {
                proxy = CallChecker.beforeCalled(proxy, BrowserMobProxy.class, 95, 3148, 3152);
                CallChecker.isCalled(proxy, BrowserMobProxy.class, 95, 3148, 3152).addResponseFilter(( response, contents, messageInfo) -> {
                    if (Pattern.compile(url).matcher(messageInfo.getOriginalUrl()).matches()) {
                        contents.setTextContents(responseBody);
                        response.setStatus(HttpResponseStatus.valueOf(responseCode));
                    }
                });
            }
            if (CallChecker.beforeDeref(getAutomatedBrowser(), AutomatedBrowser.class, 102, 3468, 3488)) {
                CallChecker.isCalled(getAutomatedBrowser(), AutomatedBrowser.class, 102, 3468, 3488).alterResponseFrom(url, responseCode, responseBody);
            }
        } catch (ForceReturn _bcornu_return_t) {
            _bcornu_return_t.getDecision().getValue();
            return;
        } finally {
            _bcornu_methode_context7.methodEnd();
        }
    }
}

