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
package com.b3dgs.lionengine.game.tile;

import com.b3dgs.lionengine.LionEngineException;

/**
 * List of tile group types.
 */
public enum TileGroupType
{
    /** Plain tiles. */
    PLAIN,
    /** Transition tiles. */
    TRANSITION,
    /** Circuits tiles. */
    CIRCUIT,
    /** No type defined. */
    NONE;

    /** Error circuit name. */
    private static final String ERROR_TYPE_NAME = "Unknown tile group type: ";

    /**
     * Convert circuit part name to its enum value.
     * 
     * @param name The circuit part name.
     * @return The circuit part enum value.
     * @throws LionEngineException If invalid name.
     */
    public static TileGroupType from(String name)
    {
        try
        {
            return TileGroupType.valueOf(name);
        }
        catch (final IllegalArgumentException exception)
        {
            throw new LionEngineException(exception, ERROR_TYPE_NAME, name);
        }
    }
}