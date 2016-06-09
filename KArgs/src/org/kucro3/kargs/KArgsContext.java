package org.kucro3.kargs;

import java.util.Map;
import java.util.Objects;
import java.util.HashMap;

public class KArgsContext {
	public KArgsContext()
	{
		this.options = new HashMap<>();
		this.optionDescriptions = new HashMap<>();
		this.properties = new HashMap<>();
		this.propertyDescriptions = new HashMap<>();
	}
	
	KArgsContext(Map<String, String> arg0, Map<String, String[]> arg1,
			Map<String, Property> arg2, Map<String, String[]> arg3)
	{
		this();
		this.options.putAll(arg0);
		this.optionDescriptions.putAll(arg1);
		this.properties.putAll(arg2);
		this.propertyDescriptions.putAll(arg3);
	}
	
	final KArgsContext copy()
	{
		return new KArgsContext(options, optionDescriptions, properties, propertyDescriptions);
	}
	
	public KArgsContext addOption(String name)
	{
		return addOption(name, null);
	}
	
	public KArgsContext addOption(String name, String[] description)
	{
		Objects.requireNonNull(name);
		
		options.put(name, OPTION_NULL);
		if(description != null)
			optionDescriptions.put(name, description);
		return this;
	}
	
	public KArgsContext addProperty(String name)
	{
		return addProperty(name, null);
	}
	
	public KArgsContext addProperty(String name, String[] description)
	{	
		Objects.requireNonNull(name);
		
		properties.put(name, PROPERTY_NULL);
		if(description != null)
			propertyDescriptions.put(name, description);
		return this;
	}
	
	public KArgsContext removeOption(String name)
	{
		options.remove(name);
		return this;
	}
	
	public KArgsContext removeProperty(String name)
	{
		properties.remove(name);
		return this;
	}
	
	public String getOption(String name)
	{
		String opt = options.get(name);
		if(opt == OPTION_NULL)
			opt = null;
		return opt;
	}
	
	public Property getProperty(String name)
	{
		Property prop = properties.get(name);
		if(prop == PROPERTY_NULL)
			prop = null;
		return prop;
	}
	
	public boolean containsOption(String name)
	{
		return options.containsKey(name);
	}
	
	public boolean cotainsProperty(String name)
	{
		return properties.containsKey(name);
	}
	
	public String[] getOptionDescription(String name)
	{
		return optionDescriptions.get(name);
	}
	
	public String[] getPropertyDescription(String name)
	{
		return propertyDescriptions.get(name);
	}
	
	public boolean containsOptionDescription(String name)
	{
		return optionDescriptions.containsKey(name);
	}
	
	public boolean containsPropertyDescription(String name)
	{
		return propertyDescriptions.containsKey(name);
	}
	
	private static final Property PROPERTY_NULL = new Property(null, null);
	
	private static final String OPTION_NULL = new String();
	
	private final Map<String, String[]> optionDescriptions;
	
	final Map<String, String> options;
	
	private final Map<String, String[]> propertyDescriptions;
	
	final Map<String, Property> properties;
	
	public static class Property
	{
		public Property(String key, String value)
		{
			this.key = key;
			this.value = value;
		}
		
		public String getKey()
		{
			return key;
		}
		
		public String getValue()
		{
			return value;
		}
		
		private final String key;
		
		private final String value;
	}
}