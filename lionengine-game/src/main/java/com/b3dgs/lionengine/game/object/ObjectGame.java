/*
 * Copyright (C) 2013-2016 Byron 3D Games Studio (www.b3dgs.com) Pierre-Alexandre (contact@b3dgs.com)
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
package com.b3dgs.lionengine.game.object;

import com.b3dgs.lionengine.Check;
import com.b3dgs.lionengine.LionEngineException;
import com.b3dgs.lionengine.game.Configurer;
import com.b3dgs.lionengine.game.Services;
import com.b3dgs.lionengine.game.feature.Featurable;
import com.b3dgs.lionengine.game.feature.FeaturableModel;
import com.b3dgs.lionengine.game.feature.Feature;
import com.b3dgs.lionengine.game.handler.Handlable;
import com.b3dgs.lionengine.game.handler.Handler;
import com.b3dgs.lionengine.game.handler.Identifiable;
import com.b3dgs.lionengine.game.handler.IdentifiableListener;
import com.b3dgs.lionengine.game.handler.IdentifiableModel;

/**
 * Object minimal representation. Defined by a unique ID, the object is designed to be handled by a {@link Handler} . To
 * remove it from the handler, a simple call to {@link #destroy()} is needed.
 * <p>
 * An object can also be externally configured by using a {@link Configurer}, filled from an XML file.
 * </p>
 * <p>
 * They are also designed to be created by a {@link Factory}. In that case, they must have at least a constructor with a
 * single argument, which must be a type of {@link Setup}.
 * </p>
 * <p>
 * It is possible to retrieve external {@link Services} when object is being constructed.
 * They cannot be used before a call to {@link #prepareFeatures(Handlable, Services)} (called when created with a
 * {@link Factory}).
 * </p>
 * <p>
 * Instead of using traditional interface implementation, it is possible to use {@link Feature} system, in order to
 * reduce
 * class complexity. The {@link Handler} is designed to work well with that system.
 * </p>
 * 
 * @see Configurer
 * @see Factory
 * @see Handler
 * @see Feature
 * @see Setup
 * @see Services
 */
public class ObjectGame implements Handlable
{
    /** Identifiable model. */
    private final Identifiable identifiable = new IdentifiableModel();
    /** Featurable model. */
    private final Featurable featurable = new FeaturableModel();
    /** Configurer reference. */
    private final Configurer configurer;

    /**
     * Create an object.
     * 
     * @param setup The setup reference (resources sharing entry point).
     * @param services The services reference (external services provider).
     * @throws LionEngineException If setup or service is <code>null</code>, or no free ID available.
     */
    public ObjectGame(Setup setup, Services services)
    {
        Check.notNull(setup);
        Check.notNull(services);

        configurer = setup.getConfigurer();
    }

    /**
     * Add a feature.
     * 
     * @param <T> The feature type.
     * @param feature The feature to add.
     * @return The added feature (same as source).
     */
    public final <T extends Feature> T addFeatureAndGet(T feature)
    {
        addFeature(feature);
        return feature;
    }

    /**
     * Get the configurer reference.
     * 
     * @return The configurer reference.
     */
    public final Configurer getConfigurer()
    {
        return configurer;
    }

    /**
     * Called when features are prepared and can be used. Does nothing by default.
     */
    protected void onPrepared()
    {
        // Nothing by default
    }

    /*
     * Identifiable
     */

    @Override
    public void addListener(IdentifiableListener listener)
    {
        identifiable.addListener(listener);
    }

    @Override
    public void removeListener(IdentifiableListener listener)
    {
        identifiable.removeListener(listener);
    }

    @Override
    public Integer getId()
    {
        return identifiable.getId();
    }

    @Override
    public void destroy()
    {
        identifiable.destroy();
    }

    @Override
    public void notifyDestroyed()
    {
        identifiable.notifyDestroyed();
    }

    /*
     * Featurable
     */

    @Override
    public void prepareFeatures(Handlable owner, Services services)
    {
        featurable.prepareFeatures(owner, services);
        onPrepared();
    }

    @Override
    public void addFeature(Feature feature)
    {
        featurable.addFeature(feature);
    }

    @Override
    public boolean hasFeature(Class<? extends Feature> feature)
    {
        return featurable.hasFeature(feature);
    }

    @Override
    public <C extends Feature> C getFeature(Class<C> feature)
    {
        return featurable.getFeature(feature);
    }

    @Override
    public Iterable<Feature> getFeatures()
    {
        return featurable.getFeatures();
    }

    @Override
    public Iterable<Class<? extends Feature>> getFeaturesType()
    {
        return featurable.getFeaturesType();
    }
}
