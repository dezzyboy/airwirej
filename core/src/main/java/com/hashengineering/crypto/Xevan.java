package com.hashengineering.crypto;

import org.coin2playj.core.Sha256Hash;
import com.hashengineering.crypto.*;
import fr.cryptohash.*;
import java.util.Arrays;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * Created by Hash Engineering on 4/24/14 for the X11 algorithm
 * updated by madzebra on 10/11/2016 for the Xevan algorithm
 */
public class Xevan {
    public static byte[] XevanDigest(byte[] input, int offset, int length)
    {
        byte [] buf = new byte[length];
        for(int i = 0; i < length; ++i)
        {
            buf[i] = input[offset + i];
        }
        return XevanDigest(buf);
    }

    public static byte[] XevanDigest(byte[] input) {
        try {
            return XEVAN(input);
        } catch (Exception e) {
            return null;
        }
    }

    static byte [] XEVAN(byte header[])
    {
        //Initialize
        Sha512Hash[] hash = new Sha512Hash[34];

        BLAKE512 blake = new BLAKE512();
        BMW512 bmw = new BMW512();
        Groestl512 groestl = new Groestl512();
        Skein512 skein = new Skein512();
        JH512 jh = new JH512();
        Keccak512 keccak = new Keccak512();
        Luffa512 luffa = new Luffa512();
        CubeHash512 cubehash = new CubeHash512();
        SHAvite512 shavite = new SHAvite512();
        SIMD512 simd = new SIMD512();
        ECHO512 echo = new ECHO512();
        Hamsi512 hamsi = new Hamsi512();
        HAVAL256_5 haval = new HAVAL256_5();
        SHA512 sha512 = new SHA512();
        Whirlpool whirlpool = new Whirlpool();
        Shabal512 shabal = new Shabal512();
        Fugue512 figue = new Fugue512();

        // Part 1
        hash[0] = new Sha512Hash(blake.digest(header));
        hash[1] = new Sha512Hash(bmw.digest(expand128(hash, 0)));
        hash[2] = new Sha512Hash(groestl.digest(expand128(hash, 1)));
        hash[3] = new Sha512Hash(skein.digest(expand128(hash, 2)));
        hash[4] = new Sha512Hash(jh.digest(expand128(hash, 3)));
        hash[5] = new Sha512Hash(keccak.digest(expand128(hash, 4)));
        hash[6] = new Sha512Hash(luffa.digest(expand128(hash, 5)));
        hash[7] = new Sha512Hash(cubehash.digest(expand128(hash, 6)));
        hash[8] = new Sha512Hash(shavite.digest(expand128(hash, 7)));
        hash[9] = new Sha512Hash(simd.digest(expand128(hash, 8)));
        hash[10] = new Sha512Hash(echo.digest(expand128(hash, 9)));
        hash[11] = new Sha512Hash(hamsi.digest(expand128(hash, 10)));
        hash[12] = new Sha512Hash(figue.digest(expand128(hash, 11)));
        hash[13] = new Sha512Hash(shabal.digest(expand128(hash, 12)));
        hash[14] = new Sha512Hash(whirlpool.digest(expand128(hash, 13)));
        hash[15] = new Sha512Hash(sha512.digest(expand128(hash, 14)));
        hash[16] = new Sha512Hash(expand64(haval.digest(expand128(hash, 15))));

        //  Part 2
        hash[17] = new Sha512Hash(blake.digest(expand128(hash, 16)));
        hash[18] = new Sha512Hash(bmw.digest(expand128(hash, 17)));
        hash[19] = new Sha512Hash(groestl.digest(expand128(hash, 18)));
        hash[20] = new Sha512Hash(skein.digest(expand128(hash, 19)));
        hash[21] = new Sha512Hash(jh.digest(expand128(hash, 20)));
        hash[22] = new Sha512Hash(keccak.digest(expand128(hash, 21)));
        hash[23] = new Sha512Hash(luffa.digest(expand128(hash, 22)));
        hash[24] = new Sha512Hash(cubehash.digest(expand128(hash, 23)));
        hash[25] = new Sha512Hash(shavite.digest(expand128(hash, 24)));
        hash[26] = new Sha512Hash(simd.digest(expand128(hash, 25)));
        hash[27] = new Sha512Hash(echo.digest(expand128(hash, 26)));
        hash[28] = new Sha512Hash(hamsi.digest(expand128(hash, 27)));
        hash[29] = new Sha512Hash(figue.digest(expand128(hash, 28)));
        hash[30] = new Sha512Hash(shabal.digest(expand128(hash, 29)));
        hash[31] = new Sha512Hash(whirlpool.digest(expand128(hash, 30)));
        hash[32] = new Sha512Hash(sha512.digest(expand128(hash, 31)));
        hash[33] = new Sha512Hash(expand64(haval.digest(expand128(hash, 32))));

        return hash[33].trim256().getBytes();
    }

    /**
     * Expands array 32 bytes long to array 64 bytes long.
     */
    private static byte [] expand64(byte [] input) {
        return expand(input, 32, 64);
    }

    /**
     * Expands array 64 bytes long to array 128 bytes long.
     */
    private static byte [] expand128(Sha512Hash [] hashes, int pos) {
        checkArgument(pos >= 0 && pos < 33);
        return expand(hashes[pos].getBytes(), 64, 128);
    }

    private static byte [] expand(byte [] input, int original_length, int new_length) {
        checkArgument(input.length == original_length);

        byte [] result = new byte[new_length];
        for (int i = 0; i < original_length; i++)
            result[i] = input[i];

        return result;
    }
}
