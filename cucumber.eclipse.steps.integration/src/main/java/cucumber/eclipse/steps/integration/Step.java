package cucumber.eclipse.steps.integration;

import java.util.List;
import java.util.Locale;
import java.util.regex.PatternSyntaxException;

import org.eclipse.core.resources.IResource;

import io.cucumber.cucumberexpressions.Argument;
import io.cucumber.cucumberexpressions.CucumberExpressionException;
import io.cucumber.cucumberexpressions.Expression;
import io.cucumber.cucumberexpressions.ExpressionFactory;
import io.cucumber.cucumberexpressions.ParameterTypeRegistry;

public class Step {

	private String text;
	private IResource source;
	private int lineNumber;
	private String lang;
	private Expression expression;
	
	//Added By Girija
	//For Reading Steps from External-ClassPath-JAR
	private String sourceName;
	private String packageName;
	
	//private String java8CukeSource;
	
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
		Locale locale = this.lang == null ? Locale.getDefault() : new Locale(this.lang);
		try {
			this.expression = new ExpressionFactory(new ParameterTypeRegistry(locale)).createExpression(text);
		}
		catch (CucumberExpressionException e) {
			// TODO All this exceptions should be trapped and add a marker in eclipse
			// the cucumber expression have a custom parameter type
			// without definition.
			// For example, "I have a {color} ball" 
			// But the "color" parameter type was not register 
			// thanks to a TypeRegistryConfigurer.
			this.expression = null;
		}
		catch (PatternSyntaxException e) {
			// This fix #286
			// the regular expression is wrong
			// we do not expect to match something with it
			// but we do not want to crash the F3
			this.expression = null;
		}
	}
	public IResource getSource() {
		return source;
	}
	public void setSource(IResource source) {
		this.source = source;
	}
	public int getLineNumber() {
		return lineNumber;
	}
	public void setLineNumber(int lineNumber) {
		this.lineNumber = lineNumber;
	}
	
	public boolean matches(String s) {
		if(this.expression == null)
			return false;
		List<Argument<?>> match = this.expression.match(s);
		return match != null;
	}
	
	public String getLang() {
		return lang;
	}
	public void setLang(String lang) {
		this.lang = lang;
	}
	
	
	/*//For Java8-Cuke-Step-Definition file
	public void setJava8CukeSource(String java8CukeSource) {
		this.java8CukeSource = java8CukeSource;
	}
	
	public String getJava8CukeSource() {
		return java8CukeSource;
	}*/
	
	
	
	
	//Added By Girija
	//Newly Added Below Methods For Reading Steps from External-ClassPath-JAR
	//Set SourceName
	public void setSourceName(String sourceName) {
		this.sourceName = sourceName;
	}
	
	//Get SourceName
	public String getSourceName() {
		return sourceName;
	}
	
	//Set PackageName
	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}
	
	//Get PackageName
	public String getPackageName() {
		return packageName;
	}
	
	
	@Override
	public String toString() {
		
		//For Steps from Current-Project
		if(lineNumber != 0)
				return "Step [text=" + text + ", source=" + source + ", lineNumber="+ lineNumber +"]";
			
		//For Steps From External-ClassPath JAR
		else		
			return "Step [text=" + text + ", source=" + sourceName+", package="+ packageName +"]";
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((lang == null) ? 0 : lang.hashCode());
		result = prime * result + lineNumber;
		result = prime * result + ((packageName == null) ? 0 : packageName.hashCode());
		result = prime * result + ((sourceName == null) ? 0 : sourceName.hashCode());
		result = prime * result + ((text == null) ? 0 : text.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Step other = (Step) obj;
		if (lang == null) {
			if (other.lang != null)
				return false;
		} else if (!lang.equals(other.lang))
			return false;
		if (lineNumber != other.lineNumber)
			return false;
		if (packageName == null) {
			if (other.packageName != null)
				return false;
		} else if (!packageName.equals(other.packageName))
			return false;
		if (sourceName == null) {
			if (other.sourceName != null)
				return false;
		} else if (!sourceName.equals(other.sourceName))
			return false;
		if (text == null) {
			if (other.text != null)
				return false;
		} else if (!text.equals(other.text))
			return false;
		return true;
	}
	
}
