package org.kucro3.kargs;

import java.util.regex.Pattern;

public class KArgsService {
	public static void parse(KArgsContext ctx, String[] args, boolean checkDuplication)
	{
		String string;
		for(int i = 0; i < args.length; i++)
		{
			string = args[i];
			if(string.startsWith("-"))
				if(string.startsWith("--"))
				{
					String property = string.charAt(2) + "";
					String value = string.substring(3);
					if(ctx.cotainsProperty(property))
					{
						String[] exps = SPLIT.split(value, 2);
						if(exps.length != 2)
							throw new IllegalArgumentException("Illegal property: " + string);
						KArgsContext.Property prop;
						if((prop = ctx.getProperty(property)) == null)
							ctx.properties.put(property, prop = new KArgsContext.Property());
						prop.set(exps[0], exps[1]);
					}
					else
						throw new IllegalArgumentException("Invalid property argument: " + string);
				}
				else
				{
					String option = string.substring(1);
					if(ctx.containsOption(option))
					{
						if(checkDuplication)
							if(ctx.getOption(option) != null)
								throw new IllegalArgumentException("Option duplicated: " + string);
						if(!ctx.needArgument(option))
							continue;
						i++;
						if(i < args.length)
							ctx.options.put(option, args[i]);
						else
							throw new IllegalArgumentException("Need more argument.");
					}
					else
						throw new IllegalArgumentException("Invalid option argument: " + string);
				}
			else
				throw new IllegalArgumentException("Unknown value: " + string);
		}
	}
	
	public static KArgsContext copy(KArgsContext ctx)
	{
		return ctx.copy();
	}	
	
	private static final Pattern SPLIT = Pattern.compile("=");
}