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
package com.b3dgs.lionengine.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import com.b3dgs.lionengine.Constant;
import com.b3dgs.lionengine.LionEngineException;

/**
 * SHA-256 based checksum manipulation.
 * <p>
 * This class is Thread-Safe.
 * </p>
 */
public final class UtilChecksum
{
    /** Instance error message. */
    private static final String ERROR_ALGORITHM = "Unable to create algorithm: ";
    /** Message digest instance. */
    private static final MessageDigest SHA256 = create("SHA-256");
    /** Maximum length. */
    private static final int MAX_LENGTH = 87;

    /**
     * Compare a checksum with its supposed original value.
     * 
     * @param value The original value.
     * @param signature The checksum value.
     * @return <code>true</code> if corresponding (checksum of value is equal to its signature), <code>false</code>
     *         else.
     */
    public static boolean checkSha256(String value, String signature)
    {
        return Arrays.equals(getSha256(value).getBytes(Constant.UTF_8), signature.getBytes(Constant.UTF_8));
    }

    /**
     * Compare a checksum with its supposed original value.
     * 
     * @param value The original value.
     * @param signature The checksum value.
     * @return <code>true</code> if corresponding (checksum of value is equal to its signature), <code>false</code>
     *         else.
     */
    public static boolean checkSha256(int value, String signature)
    {
        return Arrays.equals(getSha256(value).getBytes(Constant.UTF_8), signature.getBytes(Constant.UTF_8));
    }

    /**
     * Get the SHA-256 signature of the input bytes.
     * 
     * @param bytes The input bytes.
     * @return The bytes signature.
     */
    public static String getSha256(byte[] bytes)
    {
        final byte[] v = SHA256.digest(bytes);
        final StringBuilder builder = new StringBuilder(MAX_LENGTH);
        for (final byte b : v)
        {
            builder.append(0xFF & b);
        }
        return builder.toString();
    }

    /**
     * Get the SHA-256 signature of the input integer.
     * 
     * @param i The input integer.
     * @return The integer signature.
     */
    public static String getSha256(int i)
    {
        return getSha256(UtilConversion.intToByteArray(i));
    }

    /**
     * Get the SHA-256 signature of the input string.
     * 
     * @param str The input string.
     * @return The string signature.
     */
    public static String getSha256(String str)
    {
        return getSha256(str.getBytes(Constant.UTF_8));
    }

    /**
     * Create digest.
     * 
     * @param algorithm The algorithm name.
     * @return The created digest.
     * @throws LionEngineException If algorithm is invalid.
     */
    private static MessageDigest create(String algorithm)
    {
        try
        {
            return MessageDigest.getInstance(algorithm);
        }
        catch (final NoSuchAlgorithmException exception)
        {
            throw new LionEngineException(exception, ERROR_ALGORITHM + algorithm);
        }
    }

    /**
     * Private constructor.
     */
    private UtilChecksum()
    {
        throw new LionEngineException(LionEngineException.ERROR_PRIVATE_CONSTRUCTOR);
    }
}
