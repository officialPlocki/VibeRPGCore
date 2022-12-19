package eu.viberpg.core.custom;

import eu.viberpg.core.util.TransformUtil;

import java.util.Arrays;
import java.util.stream.Collectors;

public class JavaSoundMeasurer {

    public static void main(String[] args) {
        String[] strings = {"a", "b", "c"};
        String json = new TransformUtil().toJsonArray(Arrays.stream(strings).collect(Collectors.toList()));
        System.out.println(json);
        System.out.println(Arrays.toString(new TransformUtil().fromJsonArray(json)));
    }

}
