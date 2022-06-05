package com.valb3r.idr.networks;

import org.nd4j.autodiff.samediff.SDVariable;
import org.nd4j.autodiff.samediff.SameDiff;
import org.nd4j.linalg.api.buffer.DataType;
import org.nd4j.weightinit.impl.XavierInitScheme;

public class Issue {

    public static void main(String[] args) {
        SameDiff sd = SameDiff.create();
        //Create input and label variables
        SDVariable sdfPoint = sd.placeHolder("point", DataType.FLOAT, -1, 3); //Shape: [?, 3]
        SDVariable ray = sd.placeHolder("ray", DataType.FLOAT, -1, 3); //Shape: [?, 3]
        SDVariable expectedColor = sd.placeHolder("expected-color", DataType.FLOAT, -1, 3); //Shape: [?, 3]

        SDVariable sdfInput = denseLayer(sd, 10, 3, sdfPoint);
        SDVariable sdf = denseLayer(sd, 3, 10, sdfInput);
        sdf.markAsLoss();

        SDVariable idrRenderGradient = sd.grad(sdfPoint.name());
        SDVariable dotGrad = idrRenderGradient.dot(ray); // org.nd4j.autodiff.util.SameDiffUtils.validateDifferentialFunctionSameDiff(SameDiffUtils.java:134)

        sd.loss().meanSquaredError(expectedColor, dotGrad, null);
    }

    private static SDVariable denseLayer(SameDiff sd, int nOut, int nIn, SDVariable input) {
        SDVariable w = sd.var(input.name() + "-w1", new XavierInitScheme('c', nIn, nOut), DataType.FLOAT, nIn, nOut);
        SDVariable b = sd.zero(input.name() + "-b1", 1, nOut);
        SDVariable z = input.mmul(w).add(b);
        return sd.nn().tanh(z);
    }
}
