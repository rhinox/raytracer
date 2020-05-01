package org.rhx.graphics.jaytracer;

import org.rhx.graphics.jaytracer.model.Hitable;
import org.rhx.graphics.jaytracer.model.Ray;
import org.rhx.graphics.jaytracer.model.Vec3;
import org.rhx.graphics.jaytracer.model.material.Material;
import org.rhx.graphics.jaytracer.model.util.HitRecord;

import static java.lang.Math.sqrt;
import static org.rhx.graphics.jaytracer.model.Vec3.div;
import static org.rhx.graphics.jaytracer.model.Vec3.dot;
import static org.rhx.graphics.jaytracer.model.Vec3.sub;

public class MovingSphere implements Hitable {

    private final Vec3 center0;
    private final Vec3 center1;
    private final float time0;
    private final float time1;
    private final float radius;
    private final Material material;

    private MovingSphere(Vec3 center0, Vec3 center1, float time0, float time1, float radius, Material material) {
        this.center0 = center0;
        this.center1 = center1;
        this.material = material;
        this.time0 = time0;
        this.time1 = time1;
        this.radius = radius;
    }

    public static MovingSphere of(Vec3 center0, Vec3 center1, float time0, float time1, float radius, Material material) {
        return new MovingSphere(center0, center1, time0, time1, radius, material);
    }

    @Override
    public HitRecord hit(Ray r, float tMin, float tMax) {
        Vec3 oc = sub(r.orig, center(r.time));
        float a = dot(r.dir, r.dir);
        float b = dot(oc, r.dir);
        float c = dot(oc, oc) - radius*radius;

        float discriminant = b * b - a * c;

        if (discriminant > 0) {
            float temp = (-b - (float) sqrt(discriminant)) / a;
            if (tMin < temp && temp < tMax) {
                Vec3 pap = Ray.pap(temp, r);
                return HitRecord.of(temp, pap, div(sub(pap, center(r.time)), radius), material);
            }
            temp = (-b + (float) sqrt(discriminant)) / a;
            if (tMin < temp && temp < tMax) {
                Vec3 pap = Ray.pap(temp, r);
                return HitRecord.of(temp, pap, div(sub(pap, center(r.time)), radius), material);
            }
        }
        return null;
    }

    @Override
    public Material getMaterial() {
        return material;
    }

    public Vec3 center(float time) {
        return Vec3.add(
                center0,
                Vec3.mul(
                    ((time - time0)/(time1-time0)),
                    Vec3.sub(center1, center0)
                )
        );
    }

}
