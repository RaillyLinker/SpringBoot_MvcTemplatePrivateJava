package com.raillylinker.springboot_mvc_template_private_java.util_components.impls;

import com.raillylinker.springboot_mvc_template_private_java.classes.Pair;
import com.raillylinker.springboot_mvc_template_private_java.util_components.MapCoordinateUtil;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Component;

import java.util.List;

import static java.lang.Math.*;

// [지도 좌표계 관련 유틸]
@Component
public class MapCoordinateUtilImpl implements MapCoordinateUtil {
    // (지도 좌표 1 에서 지도 좌표 2 까지의 거리 (미터) 반환, 하버사인 공식)
    @Override
    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    public Double getDistanceMeterBetweenTwoLatLngCoordinateHarversine(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Pair<@Valid @NotNull Double, @Valid @NotNull Double> latlng1,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Pair<@Valid @NotNull Double, @Valid @NotNull Double> latlng2
    ) {
        double r = 6371e3;  // 지구 반지름 (미터 단위)
        double lat1Rad = Math.toRadians(latlng1.first()); // deg to rad
        double lat2Rad = Math.toRadians(latlng2.first()); // deg to rad
        double deltaLat = Math.toRadians(latlng2.first() - latlng1.first()); // deg to rad
        double deltaLon = Math.toRadians(latlng2.second() - latlng1.second()); // deg to rad

        double a = sin(deltaLat / 2) * sin(deltaLat / 2) +
                cos(lat1Rad) * cos(lat2Rad) *
                        sin(deltaLon / 2) * sin(deltaLon / 2);

        double c = 2 * atan2(sqrt(a), sqrt(1 - a));

        return r * c;
    }

    // (지도 좌표 1 에서 지도 좌표 2 까지의 거리 (미터) 반환, Vincenty 공식)
    // Vincenty 공식은 타원체 위에서 두 좌표 사이의 거리를 계산하는 방법으로, 지구를 완전한 구가 아닌 타원체로 간주하기 때문에 더 정확한 결과를 제공합니다.
    // 좌표계는 WGS84 를 사용합니다.
    @Override
    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    public Double getDistanceMeterBetweenTwoLatLngCoordinateVincenty(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Pair<@Valid @NotNull Double, @Valid @NotNull Double> latlng1,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Pair<@Valid @NotNull Double, @Valid @NotNull Double> latlng2
    ) {
        double a = 6378137.0; // WGS-84 기준 타원체의 장반경 (미터)
        double b = 6356752.314245; // 단반경
        double f = 0.0033528106647756; // WGS-84 타원체의 편평률

        double lat1 = Math.toRadians(latlng1.first());
        double lat2 = Math.toRadians(latlng2.first());
        double lon1 = Math.toRadians(latlng1.second());
        double lon2 = Math.toRadians(latlng2.second());

        double U1 = atan((1 - f) * tan(lat1));
        double U2 = atan((1 - f) * tan(lat2));
        double L = lon2 - lon1;
        double lambda = L;

        int maxIterations = 200;
        double tolerance = 1e-12;
        double cosSqAlpha;
        double sinSigma;
        double cos2SigmaM;
        double cosSigma;
        double sigma;
        double lambdaP;
        int iter = 0;

        do {
            double sinLambda = sin(lambda);
            double cosLambda = cos(lambda);
            sinSigma = sqrt(
                    (cos(U2) * sinLambda) * (cos(U2) * sinLambda) +
                            (cos(U1) * sin(U2) - sin(U1) * cos(U2) * cosLambda) *
                                    (cos(U1) * sin(U2) - sin(U1) * cos(U2) * cosLambda)
            );

            if (sinSigma == 0.0) return 0.0; // 두 좌표가 동일함

            cosSigma = sin(U1) * sin(U2) + cos(U1) * cos(U2) * cosLambda;
            sigma = atan2(sinSigma, cosSigma);
            double sinAlpha = cos(U1) * cos(U2) * sinLambda / sinSigma;
            cosSqAlpha = 1 - sinAlpha * sinAlpha;
            cos2SigmaM = (cosSqAlpha != 0.0) ? cosSigma - 2 * sin(U1) * sin(U2) / cosSqAlpha : 0.0;
            double C = f / 16 * cosSqAlpha * (4 + f * (4 - 3 * cosSqAlpha));
            lambdaP = lambda;
            lambda = L + (1 - C) * f * sinAlpha * (sigma + C * sinSigma * (
                    cos2SigmaM + C * cosSigma * (-1 + 2 * cos2SigmaM * cos2SigmaM)));
        } while (abs(lambda - lambdaP) > tolerance && ++iter < maxIterations);

        if (iter >= maxIterations) return Double.NaN; // 수렴 실패

        double uSq = cosSqAlpha * (a * a - b * b) / (b * b);
        double A = 1 + uSq / 16384 * (4096 + uSq * (-768 + uSq * (320 - 175 * uSq)));
        double B = uSq / 1024 * (256 + uSq * (-128 + uSq * (74 - 47 * uSq)));
        double deltaSigma = B * sinSigma * (
                cos2SigmaM + B / 4 * (cosSigma * (-1 + 2 * cos2SigmaM * cos2SigmaM) -
                        B / 6 * cos2SigmaM * (-3 + 4 * sinSigma * sinSigma) *
                                (-3 + 4 * cos2SigmaM * cos2SigmaM)));

        return b * A * (sigma - deltaSigma); // 거리 (미터)
    }

    // (여러 지도 좌표들의 중심 좌표(Latitude, Longitude) 반환)
    @Override
    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    public Pair<Double, Double> getCenterLatLngCoordinate(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            List<@Valid @NotNull Pair<@Valid @NotNull Double, @Valid @NotNull Double>> latLngList
    ) {
        if (latLngList.isEmpty()) {
            throw new IllegalArgumentException("The list must not be empty");
        }

        double xSum = 0.0;
        double ySum = 0.0;
        double zSum = 0.0;

        for (Pair<Double, Double> latLng : latLngList) {
            double latRad = Math.toRadians(latLng.first());
            double lonRad = Math.toRadians(latLng.second());

            xSum += cos(latRad) * cos(lonRad);
            ySum += cos(latRad) * sin(lonRad);
            zSum += sin(latRad);
        }

        int total = latLngList.size();

        double avgX = xSum / total;
        double avgY = ySum / total;
        double avgZ = zSum / total;

        double centralLon = atan2(avgY, avgX);
        double hypotenuse = sqrt(avgX * avgX + avgY * avgY);
        double centralLat = atan2(avgZ, hypotenuse);

        return new Pair<>(centralLat * 180 / PI, centralLon * 180 / PI);
    }
}
