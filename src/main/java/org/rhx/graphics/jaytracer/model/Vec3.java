package org.rhx.graphics.jaytracer.model;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import static java.lang.Math.sqrt;

/**
 * This object represents a value in R3. It could be a cartesian vector or RGB color value.
 */
public class Vec3 {

    private static final Random rand = ThreadLocalRandom.current();

    public static Vec3 ZERO = new Vec3(0.0f, 0.0f, 0.0f);

    public static Vec3 ONES = new Vec3(1.0f, 1.0f, 1.0f);

    private final float e0;
    private final float e1;
    private final float e2;

    private Vec3(final float e0, final float e1, final float e2) {
        this.e0 = e0;
        this.e1 = e1;
        this.e2 = e2;
    }

    public float x() {
        return e0;
    }

    public float y() {
        return e1;
    }

    public float z() {
        return e2;
    }

    public float r() {
        return e0;
    }

    public float g() {
        return e1;
    }

    public float b() {
        return e2;
    }

    @Override
    public String toString() {
        return String.format("(%.2f, %.2f, %.2f)", e0, e1, e2);
    }

    public static Vec3 of(final double e0, final double e1, final double e2) {
        return new Vec3((float)e0, (float)e1, (float)e2);
    }
    public static Vec3 of(final float e0, final float e1, final float e2) {
        return new Vec3(e0, e1, e2);
    }

    public static Vec3 add(final Vec3 u, final Vec3 v) {
        return new Vec3(u.e0 + v.e0, u.e1 + v.e1, u.e2 + v.e2);
    }

//    public static Vec3 add(final Vec3 u, final Vec3 v, final Vec3 w) {
//        return new Vec3(u.e0 + v.e0 + w.e0, u.e1 + v.e1 + w.e1, u.e2 + v.e2 + w.e2);
//    }
//
//    public static Vec3 add(final Vec3 u, final Vec3 v, final Vec3 w, final Vec3 z) {
//        return new Vec3(
//                u.e0 + v.e0 + w.e0 + z.e0,
//                u.e1 + v.e1 + w.e1 + z.e1,
//                u.e2 + v.e2 + w.e2 + z.e2
//        );
//    }

    public static Vec3 add(final Vec3 ... us) {
        float e0 = 0.0f, e1 = 0.0f, e2 = 0.0f;
        for (Vec3 v : us) {
            e0 += v.e0;
            e1 += v.e1;
            e2 += v.e2;
        }
        return new Vec3(e0, e1, e2);
    }

    public static Vec3 sub(final Vec3 u, final Vec3 v) {
        return new Vec3(u.e0 - v.e0, u.e1 - v.e1, u.e2 - v.e2);
    }

    public static Vec3 sub(final Vec3 u, final Vec3 ... us) {
        float e0 = u.e0, e1 = u.e1, e2 = u.e2;
        for (Vec3 v : us) {
            e0 -= v.e0;
            e1 -= v.e1;
            e2 -= v.e2;
        }
        return new Vec3(e0, e1, e2);
    }

    public static Vec3 mul(final Vec3 u, final Vec3 v) {
        return new Vec3(u.e0 * v.e0, u.e1 * v.e1, u.e2 * v.e2);
    }

    public static Vec3 mul(final Vec3 ... us) {
        float e0 = 1f, e1 = 1f, e2 = 1f;
        for (Vec3 v : us) {
            e0 *= v.e0;
            e1 *= v.e1;
            e2 *= v.e2;
        }
        return new Vec3(e0, e1, e2);
    }

    public static Vec3 div(final Vec3 u, final Vec3 v) {
        return new Vec3(u.e0 / v.e0, u.e1 / v.e1, u.e2 / v.e2);
    }

    public static Vec3 mul(final float t, final Vec3 u) {
        return new Vec3(u.e0 * t, u.e1 * t, u.e2 * t);
    }

    public static Vec3 mul(final float t, final Vec3 ... us) {
        return mul(t, mul(us));
    }

    public static Vec3 div(final Vec3 u, final float v) {
        float oneOverV = 1 / v;
        return new Vec3(u.e0 * oneOverV, u.e1 * oneOverV, u.e2 * oneOverV);
    }

    public static float len(final Vec3 u) {
        return (float) sqrt(u.e0 * u.e0 + u.e1 * u.e1 + u.e2 * u.e2);
    }

    /**
     * Length squared.
     * @param u vector
     * @return length
     */
    public static float lensq(final Vec3 u) {
        return u.e0 * u.e0 + u.e1 * u.e1 + u.e2 * u.e2;
    }

    public static Vec3 unit(final Vec3 u) {
        return div(u, len(u));
    }

    public static float dot(final Vec3 u, final Vec3 v) {
        return u.e0 * v.e0 + u.e1 * v.e1 + u.e2 * v.e2;
    }

    public static Vec3 cross(final Vec3 u, final Vec3 v) {
        return new Vec3(
                u.e1 * v.e2 - u.e2 * v.e1,
                u.e2 * v.e0 - u.e0 * v.e2,
                u.e0 * v.e1 - u.e1 * v.e0
        );
    }

    public static Vec3 neg(final Vec3 v) {
        return new Vec3(-v.e0, -v.e1, -v.e2);
    }

    /**
     * Random vector in unit radius sphere. Generate sphere of radius 2 and shift it by -ONES vector.
     * @return {@link Vec3}
     */
    public static Vec3 rvius() {
        Vec3 p;
        do {
            p = new Vec3(2f * rand.nextFloat() - 1f, 2f * rand.nextFloat() - 1f, 2f * rand.nextFloat() - 1f);
        } while (Vec3.lensq(p) >= 1f);
        return p;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Vec3 vec3 = (Vec3) o;

        if (Float.compare(vec3.e0, e0) != 0) return false;
        if (Float.compare(vec3.e1, e1) != 0) return false;
        return Float.compare(vec3.e2, e2) == 0;
    }

    @Override
    public int hashCode() {
        int result = (e0 != +0.0f ? Float.floatToIntBits(e0) : 0);
        result = 31 * result + (e1 != +0.0f ? Float.floatToIntBits(e1) : 0);
        result = 31 * result + (e2 != +0.0f ? Float.floatToIntBits(e2) : 0);
        return result;
    }
}
