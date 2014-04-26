/*
 * Copyright (C) 2013-2014 Byron 3D Games Studio (www.b3dgs.com) Pierre-Alexandre (contact@b3dgs.com)
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA.
 */
package com.b3dgs.lionengine;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import org.junit.Assert;
import org.junit.Test;

/**
 * Test the strings class.
 * 
 * @author Pierre-Alexandre (contact@b3dgs.com)
 */
public class StringsTest
{
    /**
     * Test the core class.
     * 
     * @throws NoSuchMethodException If error.
     * @throws IllegalAccessException If error.
     * @throws InstantiationException If error.
     * @throws InvocationTargetException If success.
     */
    @Test(expected = InvocationTargetException.class)
    public void testStringsClass() throws NoSuchMethodException, InstantiationException, IllegalAccessException,
            InvocationTargetException
    {
        final Constructor<Strings> strings = Strings.class.getDeclaredConstructor();
        strings.setAccessible(true);
        final Strings clazz = strings.newInstance();
        Assert.assertNotNull(clazz);
    }

    /**
     * Test the strings functions.
     */
    @Test
    public void testStrings()
    {
        final String str = "test";
        final String str1 = Strings.getStringRef(str);
        final String str2 = Strings.getStringRef(str);
        Assert.assertTrue(!Strings.getStringsRefs().isEmpty());
        Assert.assertTrue(str1 == str2);
        Assert.assertEquals(str1, str2);
        for (final String string : Strings.getStringsRefs())
        {
            Assert.assertTrue(string == str);
        }
        Strings.removeStringRef(str);
        Strings.clearStringsRef();
        Assert.assertTrue(Strings.getStringsRefs().isEmpty());
    }
}
