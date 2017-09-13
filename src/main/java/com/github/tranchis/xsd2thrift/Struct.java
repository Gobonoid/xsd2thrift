/*
 * ============================================================================
 * GNU Lesser General Public License
 * ============================================================================
 *
 * XSD2Thrift
 * 
 * Copyright (C) 2009 Sergio Alvarez-Napagao http://www.sergio-alvarez.com
 * 
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 * 
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307, USA.
 */
package com.github.tranchis.xsd2thrift;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import com.sun.xml.xsom.XmlString;

public class Struct implements Comparable<Struct>
{
	private Map<String,Field>	map;
	private Set<String>			types;
	private List<Field>			orderedFields;
	private String				name;
	private String				namespace;
	private String				parent;
	private String				documentation;

	public Struct(String name,String namespace, String documentation)
	{
		this.name = name;
		this.namespace = namespace;
		map = new TreeMap<String, Field>();
		types = new TreeSet<String>();
		orderedFields = new LinkedList<Field>();
		if (documentation != null && !documentation.equals("")) {
			this.documentation = documentation;
		}
	}
    public void addField(String name, String type, boolean required, boolean repeat, String documentation, XmlString def, Map<String, String> xsdMapping){
        addField(name, null, type, required, repeat, documentation, def, xsdMapping);
    }
	public void addField(String name, String namespace, String type, boolean required, boolean repeat, String documentation, XmlString def, Map<String, String> xsdMapping)
	{
		Field	f;
		
		if(map.get(name) == null)
		{
			if(type == null)
			{
				type = new String(name);
			}
			else
			{
				if(xsdMapping.containsKey(type))
				{
					type = xsdMapping.get(type);
				}
				else if(type.equals(this.name))
				{
					type = "binary";
				}
			}
			f = new Field(name,NamespaceConverter.convertFromSchema(namespace), type, repeat, documentation, def, required);
			map.put(name, f);
			orderedFields.add(f);
			if(!type.equals(this.name))
			{
				types.add(type);
			}
		}
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public List<Field> getFields()
	{
		return orderedFields;
	}

	public Collection<String> getTypes()
	{
		return types;
	}

	public void setParent(String parent)
	{
		this.parent = parent;
	}

	public String getParent()
	{
		return parent;
	}
	
	public String toString() {
	    return "Struct[name=" + name + "]";
	}

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
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
        Struct other = (Struct) obj;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        return true;
    }

	public String getNamespace() {
		return namespace;
	}

	@Override
	public int compareTo(Struct s) {
		return name.compareTo(s.name);
	}
	
	public String getDoucumetation() {
		return this.documentation;
	}
}
