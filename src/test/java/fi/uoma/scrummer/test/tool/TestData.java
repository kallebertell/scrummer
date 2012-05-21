package fi.uoma.scrummer.test.tool;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Test annotation to indicate used test data for either whole class or method
 * 
 * For the format of test data see http://www.dbunit.org/components.html
 * <br/><br/>
 * verboseFormat=true assumes the file is XmlDataSet format <br/>
 * verboseFormat=false (default) assumes the file is in FlaxXmlDataSet format<br/>
 */
@Target( { ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface TestData {
	public String filename();
	public String path() default "./src/test/resources/testdata/";
	public boolean verboseFormat() default false;
}