package com.cat.apt.processor;

import com.cat.anno.ZeusAPTBindView;
import com.cat.apt.processor.utils.ElementUtils;
import com.cat.apt.processor.utils.StringUtils;
import com.google.auto.service.AutoService;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;

@AutoService(javax.annotation.processing.Processor.class)
public class ZeusBindViewProcessor extends BaseProcessor {
    public static final String TAG = ZeusBindViewProcessor.class.getSimpleName();

    @Override
    /**
     * 指定AbstractProcessor支持的目标注解对象
     */
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> set = new HashSet<>();
        set.add(ZeusAPTBindView.class.getCanonicalName());
        log(TAG, "getSupportedAnnotationTypes");
        return set;
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        log(TAG, "process *********************");

        printInfo(set);
        // 获取所有包含BindView注解的元素
        Set<? extends Element> elements = roundEnvironment.getElementsAnnotatedWith(ZeusAPTBindView.class);
        Map<TypeElement, Map<Integer, VariableElement>> typeElementMapMap = new HashMap<>();

        // Element 用于代表程序的一个元素，
        // 这个元素可以是：包、类、接口、变量、方法等多种概念。
        for (Element e : elements) {
            // ZeusAPTBindView作用于FIELD
            // 所以Element可以直接转化为VariableElement
            VariableElement variableElement = (VariableElement) e;

            //getEnclosingElement 方法返回封装此 Element 的最里层元素
            //如果 Element 直接封装在另一个元素的声明中，则返回该封装元素
            //此处表示的即 Activity 类对象
            TypeElement typeElement = (TypeElement) variableElement.getEnclosingElement();

            Map<Integer, VariableElement> variableElementMap = typeElementMapMap.get(typeElement);
            if (variableElementMap == null) {
                variableElementMap = new HashMap<>();
                typeElementMapMap.put(typeElement, variableElementMap);
            }

            // 获取注解值 viewId
            ZeusAPTBindView zeusAPTBindView = variableElement.getAnnotation(ZeusAPTBindView.class);
            int viewID = zeusAPTBindView.value();
            variableElementMap.put(viewID, variableElement);
        }

        for (TypeElement key : typeElementMapMap.keySet()) {
            try {
                Map<Integer, VariableElement> elementMap = typeElementMapMap.get(key);
                String packageName = ElementUtils.getPackageName(elementUtils, key);
                JavaFile javaFile = JavaFile.builder(packageName, generateCodeByPoet(key, elementMap)).build();
                javaFile.writeTo(processingEnv.getFiler());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return true;
    }

    /**
     * 生成Java类
     *
     * @param key 注解对象上层元素对象 即Activity
     */
    private TypeSpec generateCodeByPoet(TypeElement key, Map<Integer, VariableElement> elementMap) {
        // 自动生成的文件名Activity+Viewbinding
        return TypeSpec.classBuilder(ElementUtils.getEnclosingClassName(key) + "ViewBnding")
                .addModifiers(Modifier.PUBLIC)
                .addMethod(generateMethodByPoet(key, elementMap))
                .build();
    }


    /**
     * 生成方法
     * MethodSpec 是 JavaPoet 提供的一个概念，用于抽象出生成一个函数时需要的基础元素
     *
     * @param typeElement        注解对象上层元素对象
     * @param variableElementMap 包含的注解对象及注解的目标对象
     * @return
     */
    /*
        public class MainActivityViewBinding {
            public static void bind(MainActivity _mainActivity) {
                _mainActivity.btn_serializeSingle = (android.widget.Button) (_mainActivity.findViewById(2131165221));
                _mainActivity.tv_hint = (android.widget.TextView) (_mainActivity.findViewById(2131165333));
                _mainActivity.btn_serializeAll = (android.widget.Button) (_mainActivity.findViewById(2131165220));
                _mainActivity.btn_remove = (android.widget.Button) (_mainActivity.findViewById(2131165219));
                _mainActivity.btn_print = (android.widget.Button) (_mainActivity.findViewById(2131165218));
                _mainActivity.et_userName = (android.widget.EditText) (_mainActivity.findViewById(2131165246));
                _mainActivity.et_userAge = (android.widget.EditText) (_mainActivity.findViewById(2131165245));
                _mainActivity.et_singleUserName = (android.widget.EditText) (_mainActivity.findViewById(2131165244));
                _mainActivity.et_bookName = (android.widget.EditText) (_mainActivity.findViewById(2131165243));
            }
        }
    */
    private MethodSpec generateMethodByPoet(TypeElement typeElement, Map<Integer, VariableElement> variableElementMap) {
        ClassName className = ClassName.bestGuess(typeElement.getQualifiedName().toString());

        // 方法参数名字
        String parameter = "_" + StringUtils.toLowerCaseFirstChar(className.simpleName());

        // MethodSpec 是 JavaPoet 提供的一个组件，用于抽象出生成一个函数时需要的基础元素
        MethodSpec.Builder methodBuild = MethodSpec.methodBuilder("bind")
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .returns(void.class)
                .addParameter(className, parameter);


        for (int viewId : variableElementMap.keySet()) {
            VariableElement element = variableElementMap.get(viewId);
            // 获取字段名称
            String fieldName = element.getSimpleName().toString();
            // 获取字段类型全名称
            String fieldType = element.asType().toString();
            // 字符串占位符写法
            String str = "{0}.{1} = ({2})({3}.findViewById({4}));";
            methodBuild.addCode(MessageFormat.format(str, parameter, fieldName, fieldType, parameter, String.valueOf(viewId)));
        }


        return methodBuild.build();
    }


    private void printInfo(Set set) {
        System.out.println("\n*****************printInfo****************\n");
        for (Object o : set) {
            System.out.println("printInfo: " + o);
        }
    }
}
