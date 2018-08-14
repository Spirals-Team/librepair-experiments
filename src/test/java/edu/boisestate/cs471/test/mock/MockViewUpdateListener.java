package edu.boisestate.cs471.test.mock;

import java.util.ArrayList;
import java.util.List;

import edu.boisestate.cs471.util.interfaces.IViewUpdateListener;

public class MockViewUpdateListener implements IViewUpdateListener {

    public int callCountDynamicTextChanged = 0;
    public int callCountButtonsChanged = 0;
    public int callCountSortDataUpdated = 0;
    public int callCountShowSampleSizeDialog = 0;
    public List<String> localizationChagnes = new ArrayList<>();
    public List<Integer> sortDatRangeChanges = new ArrayList<>();

    @Override
    public void onDynamicTextChanged() {
        callCountDynamicTextChanged++;
    }

    @Override
    public void onLocalizationChanged(String newLanguage) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onButtonsChanged() {
        callCountButtonsChanged++;
    }

    @Override
    public void onSortDataRangeChanged(int newSize) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onSortDataUpdated() {
        callCountSortDataUpdated++;
    }

    @Override
    public void showSampleSizeDialog() {
        callCountShowSampleSizeDialog++;
    }

}
