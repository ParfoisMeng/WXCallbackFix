package com.parfoismeng.processor;

import com.google.auto.service.AutoService;
import com.parfoismeng.annotation.WXCallbackFix;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;

/**
 * author : ParfoisMeng
 * time   : 2020-01-07
 * desc   : WXCallbackFix 注解的代码注入类. 根据 WXCallbackFix 注解生成对应包名路径的 WXCallback 类.
 */
@AutoService(Processor.class)
public class WXCallbackFixFixAnnotationProcessor extends AbstractProcessor {
    private Elements elementUtils;
    private Filer filer;

    /**
     * 初始化操作
     */
    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        elementUtils = processingEnvironment.getElementUtils();
        filer = processingEnvironment.getFiler();
    }

    /**
     * 返回此 Processor 可以处理的注解操作
     */
    @Override
    public Set<String> getSupportedOptions() {
        return super.getSupportedOptions();
    }

    /**
     * 返回此 Processor 支持的注释类型的名称。
     * 结果元素可能是某一受支持注释类型的规范（完全限定）名称。
     * 它也可能是 " name.*" 形式的名称，表示所有以 " name." 开头的规范名称的注释类型集合。
     * 最后， "*" 自身表示所有注释类型的集合，包括空集。
     * 注意，Processor 不应声明 "*"，除非它实际处理了所有文件；声明不必要的注释可能导致在某些环境中的性能下降。
     */
    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> set = new LinkedHashSet<>();
        set.add(WXCallbackFix.class.getCanonicalName());
        return set;
    }

    /**
     * 返回此注释 Processor 支持的最新的源版本
     * 该方法可以通过注解@SupportedSourceVersion指定
     */
    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    /**
     * 注解处理器的核心方法，处理具体的注解
     */
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnvironment) {
        // 获取所有使用 @MyAnnotation 修饰的类
        Set<? extends Element> annotatedElements = roundEnvironment.getElementsAnnotatedWith(WXCallbackFix.class);
        for (Element element : annotatedElements) {
            // 获取包名
            String packageName = elementUtils.getPackageOf(element).getQualifiedName().toString();
            // 获取类名
            String className = element.getSimpleName().toString();
            // 获取注解元素的值
            String applicationId = element.getAnnotation(WXCallbackFix.class).value();

            TypeSpec typeBuilder = TypeSpec.classBuilder(className)
                    .superclass(ClassName.get(packageName, className))
                    .addModifiers(Modifier.PUBLIC)
                    .build();
            JavaFile javaFile = JavaFile.builder(applicationId, typeBuilder)
                    .addFileComment("Generated code from WXCallbackFix. Do not modify!")
                    .build();
            try {
                javaFile.writeTo(filer);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
}
