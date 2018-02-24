package me.maxwu.genre.selenide;

import org.junit.rules.TestWatcher;
import org.junit.runner.Description;

import static com.codeborne.selenide.Selenide.screenshot;

/**
 * Created by maxwu on 3/16/17.
 */
public class BillboardTop100Watcher extends TestWatcher {

    @Override
    protected void failed(Throwable e, Description description) {
        screenshot(
                new java.text.SimpleDateFormat("yyyy-MM-dd-HH_mm_ss")
                        .format(new java.util.Date())
        );
    }
}
