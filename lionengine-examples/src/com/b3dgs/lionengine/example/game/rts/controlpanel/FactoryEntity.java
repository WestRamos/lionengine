/*
 * Copyright (C) 2013 Byron 3D Games Studio (www.b3dgs.com) Pierre-Alexandre (contact@b3dgs.com)
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
package com.b3dgs.lionengine.example.game.rts.controlpanel;

import com.b3dgs.lionengine.core.Media;
import com.b3dgs.lionengine.game.FactoryObjectGame;
import com.b3dgs.lionengine.game.SetupSurfaceGame;

/**
 * Factory entity implementation.
 * 
 * @author Pierre-Alexandre (contact@b3dgs.com)
 * @see com.b3dgs.lionengine.example.game.factory
 */
final class FactoryEntity
        extends FactoryObjectGame<EntityType, SetupSurfaceGame, Entity>
{
    /** Map reference. */
    private final Map map;

    /**
     * Constructor.
     * 
     * @param map The map reference.
     */
    FactoryEntity(Map map)
    {
        super(EntityType.class, EntityType.values(), "");
        this.map = map;
        load();
    }

    /*
     * FactoryEntityGame
     */

    @Override
    public <E extends Entity> E create(EntityType type)
    {
        return create(type, this, map);
    }

    @Override
    protected SetupSurfaceGame createSetup(EntityType key, Media config)
    {
        return new SetupSurfaceGame(config);
    }
}
