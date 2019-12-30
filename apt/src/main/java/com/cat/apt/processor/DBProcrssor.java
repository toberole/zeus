package com.cat.apt.processor;

import com.cat.anno.DB;
import com.google.auto.service.AutoService;

import java.util.HashSet;
import java.util.Set;

import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.TypeElement;

@AutoService(Processor.class)
public class DBProcrssor extends BaseProcessor {
    public static final String TAG = DBProcrssor.class.getSimpleName();

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> ret = new HashSet<>();
        ret.add(DB.class.getCanonicalName());
        return ret;
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        log(TAG, "process ************************************************");
        return false;
    }
}
