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
package com.b3dgs.lionengine.core;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.b3dgs.lionengine.Config;
import com.b3dgs.lionengine.Filter;
import com.b3dgs.lionengine.LionEngineException;
import com.b3dgs.lionengine.Resolution;
import com.b3dgs.lionengine.Version;
import com.b3dgs.lionengine.mock.FactoryGraphicMock;
import com.b3dgs.lionengine.mock.FactoryMediaMock;
import com.b3dgs.lionengine.mock.SequenceArgumentsMock;
import com.b3dgs.lionengine.mock.SequenceFailMock;
import com.b3dgs.lionengine.mock.SequenceSingleMock;
import com.b3dgs.lionengine.mock.SequenceStartMock;
import com.b3dgs.lionengine.mock.SequenceWaitMock;

/**
 * Test the loader class.
 * 
 * @author Pierre-Alexandre (contact@b3dgs.com)
 */
public class LoaderTest
{
    /** Uncaught flag. */
    static boolean uncaught = false;
    /** Output. */
    private static final Resolution OUTPUT = new Resolution(640, 480, 60);
    /** Config. */
    private static final Config CONFIG = new Config(LoaderTest.OUTPUT, 16, true);

    /**
     * Prepare the test.
     */
    @BeforeClass
    public static void prepareTest()
    {
        EngineCore.start("Test", Version.create(1, 0, 0), Verbose.CRITICAL, new FactoryGraphicMock(),
                new FactoryMediaMock());
        System.out.println("*********************************** SEQUENCE VERBOSE ***********************************");
        System.out.flush();
    }

    /**
     * Clean up test.
     */
    @AfterClass
    public static void cleanUp()
    {
        EngineCore.terminate();
        System.out.println("****************************************************************************************");
        System.out.flush();
    }

    /**
     * Test the loader with no config.
     */
    @Test(expected = LionEngineException.class)
    public void testLoaderFailConfig()
    {
        Assert.assertNotNull(new Loader(null));
    }

    /**
     * Test the loader with no sequence.
     */
    @Test(expected = LionEngineException.class)
    public void testLoaderFailSequence()
    {
        final Loader loader = new Loader(LoaderTest.CONFIG);
        loader.start(null);
    }

    /**
     * Test the loader with wrong sequence.
     */
    @Test
    public void testLoaderFailSequenceConstructor()
    {
        final Loader loader = new Loader(LoaderTest.CONFIG);

        final Thread.UncaughtExceptionHandler handler = new Thread.UncaughtExceptionHandler()
        {
            @Override
            public void uncaughtException(Thread t, Throwable exception)
            {
                LoaderTest.uncaught = true;
            }
        };
        loader.getRenderer().setUncaughtExceptionHandler(handler);
        loader.start(SequenceFailMock.class);
        Assert.assertNull(loader.getRenderer().getNextSequence());
        try
        {
            loader.getRenderer().join();
        }
        catch (final InterruptedException exception)
        {
            Assert.fail();
        }
        Assert.assertTrue(LoaderTest.uncaught);
        LoaderTest.uncaught = false;
    }

    /**
     * Test the loader already started.
     */
    @Test(expected = LionEngineException.class)
    public void testLoaderStarted()
    {
        final Loader loader = new Loader(LoaderTest.CONFIG);
        loader.start(SequenceSingleMock.class);
        loader.start(SequenceSingleMock.class);
    }

    /**
     * Test the loader with a sequence that fail during the load internal.
     */
    @Test(expected = LionEngineException.class)
    public void testLoaderSequenceFailLoadInternal()
    {
        final Loader loader = new Loader(LoaderTest.CONFIG);
        final Sequence sequence = Loader.createSequence(SequenceSingleMock.class, loader);
        sequence.loadInternal();
        sequence.loadInternal();
    }

    /**
     * Test the loader with a single sequence.
     */
    @Test
    public void testLoaderSequenceSingle()
    {
        final Loader loader = new Loader(LoaderTest.CONFIG);
        loader.start(SequenceSingleMock.class);
        try
        {
            loader.getRenderer().join();
        }
        catch (final InterruptedException exception)
        {
            Assert.fail();
        }
    }

    /**
     * Test the loader with a sequence that have arguments.
     */
    @Test
    public void testLoaderSequenceArgument()
    {
        final Loader loader = new Loader(LoaderTest.CONFIG);
        loader.start(SequenceArgumentsMock.class, new Object());
        try
        {
            loader.getRenderer().join();
        }
        catch (final InterruptedException exception)
        {
            Assert.fail();
        }
    }

    /**
     * Test the loader with a single sequence.
     */
    @Test
    public void testLoaderSequenceWait()
    {
        final Loader loader = new Loader(LoaderTest.CONFIG);
        loader.start(SequenceWaitMock.class);
        try
        {
            loader.getRenderer().join();
        }
        catch (final InterruptedException exception)
        {
            Assert.fail();
        }
    }

    /**
     * Test the loader with linked sequences.
     */
    @Test
    public void testLoaderSequenceLinked()
    {
        final Loader loader = new Loader(LoaderTest.CONFIG);
        loader.start(SequenceStartMock.class);
        try
        {
            loader.getRenderer().join();
        }
        catch (final InterruptedException exception)
        {
            Assert.fail();
        }
    }

    /**
     * Test the loader with a bilinear filter.
     */
    @Test
    public void testLoaderFilterBilinear()
    {
        final Resolution output = new Resolution(320, 240, 0);
        final Config config = new Config(output, 16, true, Filter.BILINEAR);
        final Loader loader = new Loader(config);
        loader.start(SequenceSingleMock.class);
        try
        {
            loader.getRenderer().join();
        }
        catch (final InterruptedException exception)
        {
            Assert.fail();
        }
    }

    /**
     * Test the loader with a hq2x filter.
     */
    @Test
    public void testLoaderFilterHq2x()
    {
        final Resolution output = new Resolution(640, 480, 0);
        final Config config = new Config(output, 16, false, Filter.HQ2X);
        final Loader loader = new Loader(config);
        loader.start(SequenceSingleMock.class);
        try
        {
            loader.getRenderer().join();
        }
        catch (final InterruptedException exception)
        {
            Assert.fail();
        }
    }

    /**
     * Test the loader with a hq3x filter.
     */
    @Test
    public void testLoaderFilterHq3x()
    {
        final Resolution output = new Resolution(960, 720, 60);
        final Config config = new Config(output, 16, false, Filter.HQ3X);
        final Loader loader = new Loader(config);
        loader.start(SequenceSingleMock.class);
        try
        {
            loader.getRenderer().join();
        }
        catch (final InterruptedException exception)
        {
            Assert.fail();
        }
    }
}
