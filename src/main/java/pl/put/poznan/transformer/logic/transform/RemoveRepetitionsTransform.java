package pl.put.poznan.transformer.logic.transform;

import java.util.ArrayList;
import java.util.List;

public class RemoveRepetitionsTransform extends TransformDecorator {
    public RemoveRepetitionsTransform(Transform decorated) {
        super(decorated);
    }

    @Override
    public String apply(String text) {
        return removeRepetitions(decorated.apply(text));
    }

    private String removeRepetitions(String text){
        List<String> outputWordsList = new ArrayList<>();

        for (String i: text.replaceAll("^ +| +$|( )+", " ").split(" ")){
            if (outputWordsList != null && !outputWordsList.isEmpty()) {
                if (!outputWordsList.get(outputWordsList.size()-1).trim().equals(i.trim())) {
                    outputWordsList.add(i);
                }
            }
            else{
                outputWordsList.add(i);
            }
        }
        return String.join(" ", outputWordsList);
    }
}
