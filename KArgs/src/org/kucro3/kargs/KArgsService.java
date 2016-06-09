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
					String property = string.substring(2);
					if(ctx.cotainsProperty(property))
					{
						if(checkDuplication)
							if(ctx.getProperty(property) != null)
								throw new IllegalArgumentException("Property duplicated: " + string);
						String[] exps = SPLIT.split(string, 2);
						if(exps.length != 2)
							throw new IllegalArgumentException("Illegal property: " + string);
						ctx.properties.put(property, new KArgsContext.Property(exps[0], exps[1]));
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