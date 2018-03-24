/*
 * Copyright (C) 2013-2017 Byron 3D Games Studio (www.b3dgs.com) Pierre-Alexandre (contact@b3dgs.com)
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
package com.b3dgs.lionengine.audio;

import java.io.IOException;
import java.util.Arrays;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.b3dgs.lionengine.LionEngineException;
import com.b3dgs.lionengine.core.Medias;
import com.b3dgs.lionengine.graphic.GraphicTest;
import com.b3dgs.lionengine.util.UtilTests;

/**
 * Test {@link AudioFactory}.
 */
public final class AudioFactoryTest
{
    /**
     * Prepare test.
     * 
     * @throws IOException If error.
     */
    @BeforeClass
    public static void setUp() throws IOException
    {
        Medias.setLoadFromJar(GraphicTest.class);
    }

    /**
     * Clean up test.
     */
    @AfterClass
    public static void cleanUp()
    {
        Medias.setLoadFromJar(null);
    }

    /**
     * Reset formats.
     */
    @After
    public void clean()
    {
        AudioFactory.clearFormats();
    }

    /**
     * Test constructor.
     * 
     * @throws Exception If error.
     */
    @Test(expected = LionEngineException.class)
    public void testConstructor() throws Exception
    {
        UtilTests.testPrivateConstructor(AudioFactory.class);
    }

    /**
     * Test load audio.
     */
    @Test
    public void testLoadAudio()
    {
        AudioFactory.addFormat(new AudioVoidFormat(Arrays.asList("png")));

        Assert.assertNotNull(AudioFactory.loadAudio(Medias.create("image.png")));
        Assert.assertNotNull(AudioFactory.loadAudio(Medias.create("image.png"), Audio.class));
        Assert.assertNotNull(AudioFactory.loadAudio(Medias.create("image.png"), AudioVoid.class));
    }

    /**
     * Test load audio invalid cast.
     */
    @Test(expected = LionEngineException.class)
    public void testLoadAudioInvalidCast()
    {
        AudioFactory.addFormat(new AudioVoidFormat(Arrays.asList("png")));
        try
        {
            Assert.assertNull(AudioFactory.loadAudio(Medias.create("image.png"), MyAudio.class));
        }
        catch (final LionEngineException exception)
        {
            Assert.assertEquals(ClassCastException.class, exception.getCause().getClass());
            throw exception;
        }
    }

    /**
     * Test load audio invalid type.
     */
    @Test(expected = LionEngineException.class)
    public void testLoadAudioInvalidType()
    {
        Assert.assertNull(AudioFactory.loadAudio(Medias.create("image.wav"), MyAudio.class));
    }

    /**
     * Test clear formats.
     */
    @Test
    public void testClearFormats()
    {
        AudioFactory.addFormat(new AudioVoidFormat(Arrays.asList("png")));

        Assert.assertNotNull(AudioFactory.loadAudio(Medias.create("image.png")));

        AudioFactory.clearFormats();

        try
        {
            Assert.assertNull(AudioFactory.loadAudio(Medias.create("image.png")));
            Assert.fail();
        }
        catch (final LionEngineException exception)
        {
            Assert.assertNotNull(exception);
        }
    }

    /**
     * Test void format.
     */
    @Test
    public void testVoid()
    {
        try
        {
            Assert.assertNull(AudioFactory.loadAudio(Medias.create("image.png")));
            Assert.fail();
        }
        catch (final LionEngineException exception)
        {
            Assert.assertNotNull(exception);
        }

        final AudioFormat format = new AudioVoidFormat(Arrays.asList("png"));
        AudioFactory.addFormat(format);

        final Audio audio = AudioFactory.loadAudio(Medias.create("image.png"));
        audio.setVolume(100);
        audio.play();
        audio.stop();

        format.close();
    }

    /**
     * Mock audio
     */
    private static interface MyAudio extends Audio
    {
        // Mock
    }
}
