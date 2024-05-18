package io.avaje.modules.internal;

import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.ElementFilter;
import javax.tools.Diagnostic.Kind;

import io.avaje.spi.ServiceProvider;

@ServiceProvider(Processor.class)
@SupportedAnnotationTypes("io.avaje.modules.ReadJavadoc")
public final class JavadocReader extends AbstractProcessor {

  @Override
  public synchronized void init(ProcessingEnvironment env) {
    super.init(env);
  }

  @Override
  public SourceVersion getSupportedSourceVersion() {
    return SourceVersion.latest();
  }

  @Override
  public boolean process(Set<? extends TypeElement> tes, RoundEnvironment renv) {

    tes.stream().findFirst().stream()
        .map(renv::getElementsAnnotatedWith)
        .flatMap(Set::stream)
        .forEach(this::readJavadoc);

    return false;
  }

  private void readJavadoc(Element e) {
    var messager = processingEnv.getMessager();
    
    messager.printMessage(Kind.NOTE, "Reading javadocs for fields of Element %s".formatted(e));
    messager.printMessage(
        Kind.NOTE,
        " javadocs of Element %s".formatted(processingEnv.getElementUtils().getDocComment(e)));
    ElementFilter.fieldsIn(e.getEnclosedElements())
        .forEach(
            f ->
                messager.printMessage(
                    Kind.NOTE,
                    "Read Javadoc for field %s is: `%s`"
                        .formatted(f, processingEnv.getElementUtils().getDocComment(f))));
  }
}
